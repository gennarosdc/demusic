<%-- 
    Document   : bossDashboard
    Created on : Sep 26, 2017, 9:30:01 PM
    Author     : gennaro
--%>
<%@page import="com.demusic.models.Party"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.wrapper.spotify.models.SimplePlaylist"%>
<%@page import="java.util.List"%>
<%@page import="com.demusic.models.Boss"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Boss Dashboard</title>
    </head>
    <body>
        <h1>Benvenuto <% Boss boss = (Boss)request.getSession().getAttribute("currentBoss");
           out.print(" id: " + boss.getUserProfile().getId() +" email: " + boss.getUserProfile().getEmail());
           %></h1>
           
           
           <br>
           Lista di playlist utente : <br>
           <% List<SimplePlaylist> playlists = boss.getPlaylists();
              for(SimplePlaylist x : playlists){
                  
                out.print("id: " + x.getId() + " nome: " + x.getName());
                %>
                <a href="${pageContext.request.contextPath}/PartyController?action=start&playlistId=<%= x.getId()  %>" >Start party using this playlist</a><br>
           <%                 
              }               
           %>
           <br>
           <%
               Party party = (Party)request.getSession().getAttribute("currentParty");
               if(party!=null){
                   out.print("Party enabled with code: " + party.getId());%>
                   
                  <br> 
                  <a href="${pageContext.request.contextPath}/PartyController?action=reorder&partyId=<%= party.getId()  %>" >Reorder </a>
                 <button class="btn_reorder" value="${pageContext.request.contextPath}/PartyController?action=reorder&partyId=<%= party.getId()  %>" > Reorder Ajax </button>

            <%   }
            %>
           
           <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
           <script type="text/javascript" >
            $(document).ready(function() {
               $(".btn_reorder").on("click",function(){
                  var query =  this.value;
                  $.post(this.value,function(){
                      
                  }); 
               });
               
           });   
           </script>            
           
    </body>
</html>
