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
@WebServlet(urlPatterns = { "/createApplication"})
public class CreateApplicationInAmServlet extends HttpServlet {

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
        
        try {
            String appName = request.getParameter("appName");
            String appDescription = request.getParameter("appDesc");
            String appCallbackurl = request.getParameter("appCallBackUrl");
            String responseApiLogin,responseApiCreateApp,responseApi;
            String messageToUser;
            String status;
            RequestDispatcher dispatcher;
            
            ApiCallsInAM apisAM =new ApiCallsInAM();
            responseApiLogin= apisAM.loginToAm(appName);
            
            if(convertStringToJson(responseApiLogin).equalsIgnoreCase("false"))
            {  
                responseApiCreateApp= apisAM.createApplication(appName, appDescription, appCallbackurl);
                request.setAttribute("output", responseApiCreateApp);
                
                if(convertStringToJson(responseApiCreateApp).equalsIgnoreCase("false")){
                    messageToUser = "Application:"+appName+" has created successfully in API Manager!!You can proceed to Step 3.";
                    status ="1";
                    responseApi = responseApiCreateApp;
                }
                else{
                    messageToUser = "Couldn't create the application in API Manager:"+appName+"!!You can't proceed to Step 3.";
                    status ="0";
                    responseApi = responseApiCreateApp;
                }
            }
            else{
                messageToUser = "Couldn't login to the API Manager!!You can not proceed to Step 3.";
                status ="0";
                responseApi = responseApiLogin;
            }
            
            request.setAttribute("output", responseApi);
            request.setAttribute("message", messageToUser);
            request.setAttribute("status", status);
            
            System.out.println("Response API:"+responseApi);
            System.out.println("messageToUser:"+messageToUser);
            System.out.println("status:"+status);
            
            
            dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/step3.jsp");
            
            dispatcher.forward(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(SignUpToAmServlet.class.getName()).log(Level.SEVERE, null, ex);
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
