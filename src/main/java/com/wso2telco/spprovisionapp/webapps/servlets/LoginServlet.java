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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String responseApiLogin, step, messageToUser, status, responseApi,environment,appName;
        appName = request.getParameter("appName");
        environment = request.getParameter("environment");
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher;

        ApiCallsInAM apisAM = new ApiCallsInAM();
        responseApiLogin = apisAM.loginToAm(appName);
        session.setAttribute("appName", appName);
        step = (String) session.getAttribute("step");
        session.setAttribute("environment", environment);
        
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

        dispatcher.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Login to the AM";
    }

    private String convertStringToJson(String value) throws JSONException {
        JSONObject jObject = new JSONObject(value);
        JSONObject output = jObject.getJSONObject("output");
        String errorMessage = output.getString("error");
        return errorMessage;
    }
}
