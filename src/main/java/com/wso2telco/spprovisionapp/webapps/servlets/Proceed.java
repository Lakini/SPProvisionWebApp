/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wso2telco.spprovisionapp.webapps.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(urlPatterns = {"/go"})
public class Proceed extends HttpServlet {

        public Proceed() {
        super();
    }

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
        
        RequestDispatcher dispatcher;
        
       String step = request.getParameter("stepNumber");
        
        
        switch (step) {
            case "1":
                dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/step1.jsp");
                dispatcher.forward(request, response);
                break;
            case "2": 
                dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/step2.jsp");
                dispatcher.forward(request, response);
                break;
            case "3":
                dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/step3.jsp");
                dispatcher.forward(request, response);
            case "4": 
                dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/step4.jsp");
                dispatcher.forward(request, response);
            case "5":
                dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/step5.jsp");
                dispatcher.forward(request, response);
            case "6":
                dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/step6.jsp");
                dispatcher.forward(request, response);
            case "7":
                dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/step7.jsp");
                dispatcher.forward(request, response);
            case "8":
                dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/step8.jsp");
                dispatcher.forward(request, response);
            case "9":
                dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/step9.jsp");
                dispatcher.forward(request, response);
            case "10":
                dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/step10.jsp");
                dispatcher.forward(request, response);
            case "11":
                dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/step11.jsp");
                dispatcher.forward(request, response);
            case "12":
                dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/step12.jsp");
                dispatcher.forward(request, response);
            case "13":
                dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/step13.jsp");
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
