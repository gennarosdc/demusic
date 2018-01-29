<%-- 
    Document   : party
    Created on : Sep 26, 2017, 9:30:01 PM
    Author     : gennaro
--%>
<%@page import="com.wrapper.spotify.models.Track"%>
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
        <title>Party</title>
        
    </head>
    <body>
        <h1>Joined to party <% Party party = (Party)request.getAttribute("party");
           out.print( party.getId() );
           %></h1>
           
           
           <br>
           List of party tracks : <br>
           <% List<Track> tracks = party.getTracksList();
              for(Track x : tracks){
                  
                out.print("track id: " + x.getId() + " name: " + x.getName());
                %>
                
                <button class="btn_vote" value="${pageContext.request.contextPath}/PartyController?action=vote&partyId=<%= party.getId()  %>&trackId=<%= x.getId()  %>" > Vote! </button>
                <br>
           <%                 
              }               
           %>
           <br>
           <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
           <script type="text/javascript" >
            $(document).ready(function() {
               $(".btn_vote").on("click",function(){
                  var query =  this.value;
                  $.post(this.value,function(){
                      console.log("Voto inviato " + query);
                  }); 
               });
               
           });   
           </script>    
           
           
    </body>
</html>
