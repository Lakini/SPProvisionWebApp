/** *****************************************************************************
 * Copyright  (c) 2015-2017, WSO2.Telco Inc. (http://www.wso2telco.com) All Rights Reserved.
 *
 * WSO2.Telco Inc. licences this file to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************** */
package com.wso2telco.spprovisionapp.webapps.servlets;

import com.wso2telco.spprovisionapp.webapps.utils.DataBaseFunction;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/updateClientKeyandSecret"})
public class UpdateNewKeysServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String status, hostUrl;
        String oAuthRequest = null;
        String tokenRequest = null;
        String userInfo = null;
        RequestDispatcher dispatcher;
        String appName = (String) this.getServletConfig().getServletContext().getAttribute("appName");
        String environment = (String) this.getServletConfig().getServletContext().getAttribute("environment");
        String callbackUrl = (String) this.getServletConfig().getServletContext().getAttribute("appCallBackUrl");
        String clientKey = request.getParameter("newClientKey");
        String secretKey = request.getParameter("newClientSecret");
        Properties popertiesFromPropertyFile;
        PropertyFileHandler propertyFileHandler = new PropertyFileHandler();

        try {

            popertiesFromPropertyFile = propertyFileHandler.popertiesFromPropertyFile();
            String oldConsumerKey = (String) this.getServletConfig().getServletContext().getAttribute("oldConsumerKey");
            String oldSecretKey = (String) this.getServletConfig().getServletContext().getAttribute("oldSecretKey");
            status = DataBaseFunction.updateClientAndSecretKeys(environment,oldConsumerKey, clientKey, oldSecretKey, secretKey);

            if (environment.equals("preprod")) {
                hostUrl = popertiesFromPropertyFile.getProperty("host_preprod_IS");
            } else {
                hostUrl = popertiesFromPropertyFile.getProperty("host_prod_IS");
            }

            oAuthRequest = hostUrl + "/authorize/v1/spark/oauth2/authorize?"
                    + "client_id=" + clientKey + "&response_type=code&scope=openid"
                    + "&redirect_uri=" + callbackUrl + "&acr_values=2&state=state_33945636-d3b7-4b12-b7b6-288e5a9683a7"
                    + "&nonce=nonce_a75674c9-2007-4e36-afee-ccf7c865a25d";

            tokenRequest = "curl -v -X POST --user " + clientKey + ":" + secretKey + " "
                    + "-H \"Content-Type: application/x-www-form-urlencoded;charset=UTF-8\" -k -d "
                    + "\"grant_type=authorization_code&code=d3ce9ee75a5de3ca955b1798b39bf2&"
                    + "redirect_uri=" + callbackUrl + "\" " + hostUrl + "/token/v1/spark/oauth2/token";

            userInfo = "curl -i " + hostUrl + "/oauth2/userinfo?schema=openid -H \"Authorization: Bearer 9d55e77b3f823d84ae5fdff1d7135fcd\"";

        } catch (SQLException ex) {
            Logger.getLogger(UpdateNewKeysServlet.class.getName()).log(Level.SEVERE, null, ex);
            status = "Exception:" + ex.getMessage();
        }

        request.setAttribute("status", status);
        request.setAttribute("oAuthRequest", oAuthRequest);
        request.setAttribute("tokenRequest", tokenRequest);
        request.setAttribute("userInfo", userInfo);

        dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/step12.jsp");

        dispatcher.forward(request, response);

//Update client key and secret using API calls
//        String status, hostUrl, consumerKeyValue, consumerSecretValue, callbackUrl;
//        String oAuthRequest = null;
//        String tokenRequest = null;
//        String userInfo = null;
//        RequestDispatcher dispatcher;
//        String consumerKey[];
//        HttpSession session = request.getSession();
//        String appName = (String) session.getAttribute("appName");
//        OauthAdminClient oauthAdminClient = new OauthAdminClient();
//        String clientKey = request.getParameter("newClientKey");
//        String secretKey = request.getParameter("newClientSecret");
//
//        try {
//
//            consumerKey = ApiCallsInIS.getClientSecret(appName);
//            consumerKeyValue = consumerKey[0];
//            consumerSecretValue = consumerKey[1];
//            status = ApiCallsInIS.updateServceProvider(clientKey, secretKey, appName);
//            callbackUrl = oauthAdminClient.getOauthApplicationDataByConsumerKey(consumerKeyValue).getCallbackUrl();
//
//            if (session.getAttribute("environment").equals("preprod")) {
//                hostUrl = PropertyFileHandler.readFromPropertyFile("host_preprod_IS");
//            } else {
//                hostUrl = PropertyFileHandler.readFromPropertyFile("host_prod_IS");
//            }
//
//            oAuthRequest = hostUrl + "/authorize/v1/spark/oauth2/authorize?"
//                    + "client_id=" + consumerKeyValue + "&response_type=code&scope=openid"
//                    + "&redirect_uri=" + callbackUrl + "&acr_values=2&state=state_33945636-d3b7-4b12-b7b6-288e5a9683a7"
//                    + "&nonce=nonce_a75674c9-2007-4e36-afee-ccf7c865a25d";
//
//            tokenRequest = "curl -v -X POST --user " + consumerKeyValue + ":" + consumerSecretValue + " "
//                    + "-H \"Content-Type: application/x-www-form-urlencoded;charset=UTF-8\" -k -d "
//                    + "\"grant_type=authorization_code&code=d3ce9ee75a5de3ca955b1798b39bf2&"
//                    + "redirect_uri=" + callbackUrl + "\" " + hostUrl + "/token/v1/spark/oauth2/token";
//
//            userInfo = "curl -i " + hostUrl + "/oauth2/userinfo?schema=openid -H \"Authorization: Bearer 9d55e77b3f823d84ae5fdff1d7135fcd\"";
//
//        } catch (SpProvisionServiceException ex) {
//            Logger.getLogger(ScopeConfigurationServlet.class.getName()).log(Level.SEVERE, null, ex);
//            status = "Exception:" + ex.getMessage();
//        }
//
//        request.setAttribute("status", status);
//        request.setAttribute("oAuthRequest", oAuthRequest);
//        request.setAttribute("tokenRequest", tokenRequest);
//        request.setAttribute("userInfo", userInfo);
//
//        dispatcher = request.getServletContext()
//                .getRequestDispatcher("/WEB-INF/views/step12.jsp");
//
//        dispatcher.forward(request, response);
    }
}
