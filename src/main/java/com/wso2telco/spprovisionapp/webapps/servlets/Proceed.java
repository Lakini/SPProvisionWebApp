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

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/go"})
public class Proceed extends HttpServlet {

    public Proceed() {
        super();
    }

    //redirects to the correct Web Page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher;
        String step = request.getParameter("stepNumber");
        String environment = request.getParameter("environment");
        HttpSession session = request.getSession();

        session.setAttribute("environment", environment);
        session.setAttribute("stepNumber", step);
        
        switch (step) {
            case "1":
                dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/step1.jsp");
                dispatcher.forward(request, response);
                break;
            case "2":
                dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/step2.jsp");
                dispatcher.forward(request, response);
                break;
            case "3":case "4":case "5":case "6":case "7":case "8":case "9":case "10":case "11":case "12":case "13":
                dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp");
                dispatcher.forward(request, response);
            default:
                dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/homeView.jsp");
                dispatcher.forward(request, response);
        }
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
        return "This redirects the user to correct step to complete the Service Provider Provisioning!!";
    }// </editor-fold>

}
