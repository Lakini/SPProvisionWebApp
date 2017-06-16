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

@WebServlet(urlPatterns = {"/loginAndSignUp"})
public class SignUpToAmServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String userName = request.getParameter("userName");
            String appName = request.getParameter("appName");
            String appShortName = request.getParameter("appShortName");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String developerEmail = request.getParameter("devEmail");
            String responseApi;
            String messageToUser;
            String status;
            RequestDispatcher dispatcher;
            this.getServletConfig().getServletContext().setAttribute("appName", appName);
            this.getServletConfig().getServletContext().setAttribute("userName", appName);
            String environment = (String) this.getServletConfig().getServletContext().getAttribute("environment");
            ApiCallsInAM apisAM = new ApiCallsInAM(environment);
            responseApi = apisAM.createNewUserInAm(userName, firstName, lastName, developerEmail);

            dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/step2.jsp");
            request.setAttribute("output", responseApi);
            convertStringToJson(responseApi);

            if (convertStringToJson(responseApi).equalsIgnoreCase("false")) {
                messageToUser = "User Account for the application:" + appName + " has created successfully!!You can proceed to Step 2.";
                status = "1";
            } else {
                messageToUser = "Couldn't create a user Account for the application:" + appName + "!!You can't proceed to Step 2.";
                status = "0";
            }

            request.setAttribute("message", messageToUser);
            request.setAttribute("status", status);

            System.out.println("Response API:" + responseApi);
            System.out.println("messageToUser:" + messageToUser);
            System.out.println("status:" + status);

            dispatcher.forward(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(SignUpToAmServlet.class.getName()).log(Level.SEVERE, "JSONException occured:" + ex.getMessage(), ex);
        }
    }

    private String convertStringToJson(String value) throws JSONException {
        JSONObject jObject = new JSONObject(value);
        JSONObject output = jObject.getJSONObject("output");
        String errorMessage = output.getString("error");
        return errorMessage;
    }
}
