/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demusic.controllers;

import com.demusic.models.Boss;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.models.AuthorizationCodeCredentials;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.demusic.models.BossesManager;
import com.wrapper.spotify.exceptions.WebApiException;
import com.wrapper.spotify.models.Page;
import com.wrapper.spotify.models.SimplePlaylist;
import com.wrapper.spotify.models.User;
import java.util.List;
import javax.servlet.annotation.WebServlet;
/**
 *
 * @author gennaro
 */
@WebServlet(name = "RegisterBoss", urlPatterns = {"/RegisterBoss"})
public class RegisterBoss extends HttpServlet {

    private final BossesManager bossesManager = BossesManager.getBossesManager();
    
    
    
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

    try {
        
        /* New API instance to make request to authorize every bosses */
        Api api= Api.builder()
            .clientId(Authorize.clientId)
            .clientSecret(Authorize.clientSecret)
            .redirectURI(Authorize.redirectUri)
            .build();

        //Obtain user authorizations
        AuthorizationCodeCredentials authorizationCodeCredentials = api.authorizationCodeGrant(request.getParameter("code")).build().get();
        System.out.println("Successfully retrieved an access token! " + authorizationCodeCredentials.getAccessToken());
        System.out.println("The access token expires in " + authorizationCodeCredentials.getExpiresIn() + " seconds");
        System.out.println("Luckily, I can refresh it using this refresh token! " + authorizationCodeCredentials.getRefreshToken());
        
        Boss boss = new Boss();
        boss.setUserAuthorizationCredential(authorizationCodeCredentials); 
        api.setAccessToken(authorizationCodeCredentials.getAccessToken());
        api.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
        boss.setApi(api);
        

        boss.setUserProfile((User)api.getMe().build().get());
        bossesManager.addBoss(boss); 
        

        //Obtain and set user playlists
        Page<SimplePlaylist> pagePlaylists = api.getPlaylistsForUser(boss.getUserProfile().getId()).build().get();      

        //boss.setPlaylists(pagePlaylists.getItems());
        boss.setOwenedPlaylists(pagePlaylists.getItems());
        
        request.getSession().setAttribute("currentBoss", boss);
        request.getRequestDispatcher("/bossDashboard.jsp").forward(request, response);
    } catch (WebApiException ex) {
        System.out.println("Failed" + ex.toString());
    } catch (Exception ex) {
        System.out.println("Failed2" + ex.toString());
    }
    

    
    
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
