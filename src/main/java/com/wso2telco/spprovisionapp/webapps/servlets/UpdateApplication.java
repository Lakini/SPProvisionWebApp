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

import com.wso2telco.spprovisionapp.webapps.conn.AxiatadbConnectionUtil;
import com.wso2telco.spprovisionapp.webapps.utils.DataBaseFunction;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/updateApplication"})
public class UpdateApplication extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String status;
        RequestDispatcher dispatcher;
        String environment = (String) this.getServletConfig().getServletContext().getAttribute("environment");

        try {
            Connection conn = AxiatadbConnectionUtil.getConnection(environment);
            status = DataBaseFunction.updateApplication(conn);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ActivateApplication.class.getName()).log(Level.SEVERE, "Class Not Found Exception occured!" + ex.getMessage(), ex);
            status = "Exception:" + ex.getMessage();
        } catch (SQLException ex) {
            Logger.getLogger(ActivateApplication.class.getName()).log(Level.SEVERE, "SQLException occured!" + ex.getMessage(), ex);
            status = "Exception:" + ex.getMessage();
        }
        request.setAttribute("status", status);

        dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/step5.jsp");

        dispatcher.forward(request, response);
    }
}
