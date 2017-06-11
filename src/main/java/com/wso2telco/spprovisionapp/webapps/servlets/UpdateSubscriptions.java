/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wso2telco.spprovisionapp.webapps.servlets;

import com.wso2telco.spprovisionapp.webapps.conn.ApimgtConnectionUtil;
import com.wso2telco.spprovisionapp.webapps.conn.AxiatadbConnectionUtil;
import com.wso2telco.spprovisionapp.webapps.utils.DataBaseFunction;
import java.io.IOException;
import java.io.PrintWriter;
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

/**
 *
 * @author lakini
 */
@WebServlet(urlPatterns = { "/updateSubscriptions"})
public class UpdateSubscriptions extends HttpServlet {

   
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String status;
        RequestDispatcher dispatcher;
        
        try {
            Connection conn = ApimgtConnectionUtil.getConnection();
            status = DataBaseFunction.updateSubscriptions(conn);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ActivateApplication.class.getName()).log(Level.SEVERE, null, ex);
            status = "Exception:"+ex.getMessage();
        } catch (SQLException ex) {
            Logger.getLogger(ActivateApplication.class.getName()).log(Level.SEVERE, null, ex);
            status = "Exception:"+ex.getMessage();
        }
        
        request.setAttribute("status", status);

        dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/step7.jsp");
            
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
        return "Short description";
    }// </editor-fold>

}
