/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wso2telco.spprovisionapp.webapps.servlets;

import com.wso2telco.spprovisionapp.webapps.api.ApiCallsInAM;
import java.io.IOException;
import java.io.PrintWriter;
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

/**
 *
 * @author lakini
 */
@WebServlet(urlPatterns = { "/subscribeToApis"})
public class SubscribeToApisServlet extends HttpServlet {

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
                //try {
            //chnage input variables
            String appName = request.getParameter("appName");
            String appShortName = request.getParameter("appShortName");
            String developerEmail = request.getParameter("devEmail");
            String responseApi;
            String messageToUser;
            String status;
            RequestDispatcher dispatcher;
            dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/step6.jsp");
            
           /** ApiCallsInAM apisAM =new ApiCallsInAM();
            responseApi= apisAM.addSubscritions(appName, appName, appName, appName, appName);
            
            dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/step6.jsp");
            request.setAttribute("output", responseApi);
            convertStringToJson(responseApi);
            
            
            if(convertStringToJson(responseApi).equalsIgnoreCase("false"))
            {
                messageToUser = "User Account for the application:"+appName+" has created successfully!!You can proceed to Step 2.";
                status ="1";
            }
            else{
                messageToUser = "Couldn't create a user Account for the application:"+appName+"!!You can't proceed to Step 2.";
                status ="0";
            }
            
            request.setAttribute("message", messageToUser);
            request.setAttribute("status", status);
            
            System.out.println("Response API:"+responseApi);
            System.out.println("messageToUser:"+messageToUser);
            System.out.println("status:"+status);**/
            
            
            dispatcher.forward(request, response);
        //} 
          /**      catch (JSONException ex) {
            Logger.getLogger(LoginToAmServlet.class.getName()).log(Level.SEVERE, null, ex);
        }**/
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
    
    private String convertStringToJson( String value) throws JSONException{
    JSONObject jObject  = new JSONObject(value); 
    //JSONObject data = jObject.getJSONObject(value); 
    JSONObject output = jObject.getJSONObject("output");
    String errorMessage = output.getString("error");
    return errorMessage;
    }

}
