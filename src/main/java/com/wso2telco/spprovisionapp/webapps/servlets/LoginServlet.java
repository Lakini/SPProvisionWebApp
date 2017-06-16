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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String responseApiLogin, step, messageToUser, status, responseApi, environment, appName,userName;
        userName = request.getParameter("userName");
        appName = request.getParameter("appName");
        environment = request.getParameter("environment");
        RequestDispatcher dispatcher;

        this.getServletConfig().getServletContext().setAttribute("environment", environment);
        this.getServletConfig().getServletContext().setAttribute("appName", appName);

        step = (String) this.getServletConfig().getServletContext().getAttribute("stepNumber");

        if (Integer.valueOf(step) < 6) {
            ApiCallsInAM apisAM = new ApiCallsInAM(environment);
            responseApiLogin = apisAM.loginToAm(userName);

            try {
                if (convertStringToJson(responseApiLogin).equalsIgnoreCase("false")) {
                    dispatcher = request.getServletContext()
                            .getRequestDispatcher("/WEB-INF/views/step" + step + ".jsp");
                } else {
                    messageToUser = "Couldn't login to the API Manager!!You can not proceed to Step " + step + ".";
                    status = "0";
                    responseApi = responseApiLogin;
                    request.setAttribute("output", responseApi);
                    request.setAttribute("message", messageToUser);
                    request.setAttribute("status", status);
                    dispatcher = request.getServletContext()
                            .getRequestDispatcher("/WEB-INF/views/homeView.jsp");
                }
            } catch (JSONException ex) {
                messageToUser = "Couldn't login to the API Manager!!" + ex.toString() + "\n You can not proceed to Step " + step + ".";
                status = "0";
                responseApi = responseApiLogin;
                request.setAttribute("output", responseApi);
                request.setAttribute("message", messageToUser);
                request.setAttribute("status", status);
                dispatcher = request.getServletContext()
                        .getRequestDispatcher("/WEB-INF/views/homeView.jsp");
            }
        } else {
            dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/step" + step + ".jsp");
        }

        dispatcher.forward(request, response);
    }

    private String convertStringToJson(String value) throws JSONException {
        JSONObject jObject = new JSONObject(value);
        JSONObject output = jObject.getJSONObject("output");
        String errorMessage = output.getString("error");
        return errorMessage;
    }
}
