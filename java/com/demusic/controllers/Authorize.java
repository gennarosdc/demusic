package com.demusic.controllers;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.authentication.AuthorizationURLRequest;
import java.io.IOException;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

/**
 * Prima classe chiamata per login/signin business
 * 
 * @author gennaro
 */
@WebServlet(name = "Authorize", urlPatterns = {"/Authorize"})
public class Authorize extends HttpServlet {
        
        
        static final String clientId = "place client id Here";
        static final String clientSecret = "place client secret key here";
        /* Application details necessary to get an access token */
        //final String code = "<insert code>";
        static final String redirectUri = "http://localhost:8080/DemusicNew/RegisterBoss";
        
        
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        


        /* New API instance to make request to authorize every bosses */
        Api api= Api.builder()
            .clientId(clientId)
            .clientSecret(clientSecret)
            .redirectURI(redirectUri)
            .build();       

        //api.createAuthorizeURL("user-read-private user-read-email user-read-playback-state user-read-currently-playing");
        LinkedList<String> scopeAuthorizationList = new LinkedList<String>();
        scopeAuthorizationList.add("user-read-private");
        scopeAuthorizationList.add("user-read-email");
        scopeAuthorizationList.add("user-read-birthdate");  
        scopeAuthorizationList.add("playlist-modify-public");
        scopeAuthorizationList.add("playlist-modify-private");
        scopeAuthorizationList.add("user-modify-playback-state");
        scopeAuthorizationList.add("user-read-currently-playing");
        scopeAuthorizationList.add("user-read-playback-state");
                
        AuthorizationURLRequest req = api.createAuthorizeURL(scopeAuthorizationList).build();
        System.out.println(req.toStringWithQueryParameters()+ "---" + req.toString());
        response.sendRedirect(req.toStringWithQueryParameters());
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
        processRequest(request, response);
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
        processRequest(request, response);
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
