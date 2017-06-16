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

import com.wso2telco.spprovisionapp.webapps.api.ApiCallsInAM;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(urlPatterns = {"/createApplication"})
public class CreateApplicationInAmServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String appName = (String) this.getServletConfig().getServletContext().getAttribute("appName");
            String userName = (String)this.getServletConfig().getServletContext().getAttribute("userName"); 
            String appDescription = request.getParameter("appDesc");
            String appCallbackurl = request.getParameter("appCallBackUrl");
            String appTier = request.getParameter("tier");
            String responseApiLogin, responseApiCreateApp, responseApi;
            String messageToUser;
            String status;
            String environment = (String) this.getServletConfig().getServletContext().getAttribute("environment");
            this.getServletConfig().getServletContext().setAttribute("appCallBackUrl", appCallbackurl);
            RequestDispatcher dispatcher;

            ApiCallsInAM apisAM = new ApiCallsInAM(environment);
            responseApiLogin = apisAM.loginToAm(userName);

            if (convertStringToJson(responseApiLogin).equalsIgnoreCase("false")) {
                responseApiCreateApp = apisAM.createApplication(appName, appDescription, appCallbackurl, appTier);
                request.setAttribute("output", responseApiCreateApp);

                if (convertStringToJson(responseApiCreateApp).equalsIgnoreCase("false")) {
                    messageToUser = "Application:" + appName + " has created successfully in API Manager!!You can proceed to Step 3.";
                    status = "1";
                    responseApi = responseApiCreateApp;
                } else {
                    messageToUser = "Couldn't create the application in API Manager:" + appName + "!!You can't proceed to Step 3.";
                    status = "0";
                    responseApi = responseApiCreateApp;
                }
            } else {
                messageToUser = "Couldn't login to the API Manager!!You can not proceed to Step 3.";
                status = "0";
                responseApi = responseApiLogin;
            }

            request.setAttribute("output", responseApi);
            request.setAttribute("message", messageToUser);
            request.setAttribute("status", status);

            System.out.println("Response API:" + responseApi);
            System.out.println("messageToUser:" + messageToUser);
            System.out.println("status:" + status);
            
            this.getServletConfig().getServletContext().setAttribute("appName",userName+"_"+appName);

            dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/step3.jsp");

            dispatcher.forward(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(SignUpToAmServlet.class.getName()).log(Level.SEVERE, "Error occured when reading JSON object." + ex.getMessage(), ex);
        }
    }

    private String convertStringToJson(String value) throws JSONException {
        JSONObject jObject = new JSONObject(value);
        JSONObject output = jObject.getJSONObject("output");
        String errorMessage = output.getString("error");
        return errorMessage;
    }
}
