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

import com.wso2telco.spprovisionapp.webapps.conn.ConnectdbConnectionUtil;
import com.wso2telco.spprovisionapp.webapps.utils.DataBaseFunction;
import java.io.IOException;
import java.sql.Connection;
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

@WebServlet(urlPatterns = {"/scopeConfigurations"})
public class ScopeConfigurationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String status, hostUrl;
        String oAuthRequest = null;
        String tokenRequest = null;
        String userInfo = null;
        RequestDispatcher dispatcher;
        String consumerKeyValue = request.getParameter("consumerKey");
        String consumerSecretValue = request.getParameter("secretKey");
        String appName = (String) this.getServletConfig().getServletContext().getAttribute("appName");
        String environment = (String) this.getServletConfig().getServletContext().getAttribute("environment");
        String callbackUrl = (String) this.getServletConfig().getServletContext().getAttribute("appCallBackUrl");
        this.getServletConfig().getServletContext().setAttribute("oldConsumerKey", consumerKeyValue);
        this.getServletConfig().getServletContext().setAttribute("oldSecretKey", consumerSecretValue);
        Properties popertiesFromPropertyFile;
        PropertyFileHandler propertyFileHandler = new PropertyFileHandler();
        System.out.println("CK:" + consumerKeyValue);
        System.out.println("CS:" + consumerSecretValue);

        try {
            popertiesFromPropertyFile = propertyFileHandler.popertiesFromPropertyFile();
            Connection conn = ConnectdbConnectionUtil.getConnection();
            status = DataBaseFunction.scopeCofiguration(conn, consumerKeyValue);
            status = "Success";
            System.out.println("status:" + status);

            if (environment.equals("preprod")) {
                hostUrl = popertiesFromPropertyFile.getProperty("host_preprod_IS");
            } else {
                hostUrl = popertiesFromPropertyFile.getProperty("host_prod_IS");
            }

            System.out.println("hostUrl:" + hostUrl);

            oAuthRequest = hostUrl + "/authorize/v1/spark/oauth2/authorize?"
                    + "client_id=" + consumerKeyValue + "&response_type=code&scope=openid"
                    + "&redirect_uri=" + callbackUrl + "&acr_values=2&state=state_33945636-d3b7-4b12-b7b6-288e5a9683a7"
                    + "&nonce=nonce_a75674c9-2007-4e36-afee-ccf7c865a25d";

            tokenRequest = "curl -v -X POST --user " + consumerKeyValue + ":" + consumerSecretValue + " "
                    + "-H \"Content-Type: application/x-www-form-urlencoded;charset=UTF-8\" -k -d "
                    + "\"grant_type=authorization_code&code=d3ce9ee75a5de3ca955b1798b39bf2&"
                    + "redirect_uri=" + callbackUrl + "\" " + hostUrl + "/token/v1/spark/oauth2/token";

            userInfo = "curl -i " + hostUrl + "/oauth2/userinfo?schema=openid -H \"Authorization: Bearer 9d55e77b3f823d84ae5fdff1d7135fcd\"";
            System.out.println("oAuthRequest:" + oAuthRequest);
            System.out.println("tokenRequest:" + tokenRequest);
            System.out.println("userInfo:" + userInfo);

        } catch (SQLException ex) {
            Logger.getLogger(ActivateApplication.class.getName()).log(Level.SEVERE, "SQL Exception occured:" + ex.getMessage(), ex);
            status = "Exception:" + ex.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ScopeConfigurationServlet.class.getName()).log(Level.SEVERE, "SClass Not Found Exception occured:" + ex.getMessage(), ex);
            status = "Exception:" + ex.getMessage();
        }

        request.setAttribute("status", status);
        System.out.println("status attribute" + status);
        request.setAttribute("oAuthRequest", oAuthRequest);
        request.setAttribute("tokenRequest", tokenRequest);
        request.setAttribute("userInfo", userInfo);

        dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/step10.jsp");

        dispatcher.forward(request, response);
    }

//For API clientKey,Secret update    
//        OauthAdminClient oauthAdminClient = new OauthAdminClient();
//
//        try {
//            consumerKey = ApiCallsInIS.getClientSecret(appName);
//            consumerKeyValue = consumerKey[0];
//            consumerSecretValue = consumerKey[1];
//            Connection conn = ConnectdbConnectionUtil.getConnection();
//            status = DataBaseFunction.scopeCofiguration(conn, consumerKeyValue);
//            status = "Success";
//            callbackUrl = oauthAdminClient.getOauthApplicationDataByConsumerKey(consumerKeyValue).getCallbackUrl();
//
//            if (environment.equals("preprod")) {
//                hostUrl = PropertyFileHandler.readFromPropertyFile("host_preprod_IS");
//                System.out.println("Host:" + hostUrl);
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
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(ActivateApplication.class.getName()).log(Level.SEVERE, null, ex);
//            status = "Exception:" + ex.getMessage();
//        } catch (SQLException ex) {
//            Logger.getLogger(ActivateApplication.class.getName()).log(Level.SEVERE, null, ex);
//            status = "Exception:" + ex.getMessage();
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
//                .getRequestDispatcher("/WEB-INF/views/step10.jsp");
//
//        dispatcher.forward(request, response);
}
