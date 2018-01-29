<%-- 
    Document   : party
    Created on : Sep 26, 2017, 9:30:01 PM
    Author     : gennaro
--%>

<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Demusic</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    
    
    <div class="container">
        <h1>Demusic</h1>
        
        <div class="row">
            
        
            <div class="col-md-6">
  
            </div>
            <div class="col-md-6">
                <form class="" action="Authorize">
                  <h2 class="">Sign in Business</h2>

                  <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in with Spotify</button>
                </form>

            </div>
            
        </div>
        
        <div class="col-md-12 ">
            <form action="PartyController?action=joinParty" method="GET">
                  <h2 class="">Accedi al party</h2>
                  
                  <input type="text" name="partyId" id="partyId" class="form-control" placeholder="Party code"  autofocus>
                  <input type="hidden" name="action" id="action" value="joinParty" >

                  <button class="btn btn-lg btn-primary btn-block" type="submit">Join in</button>
                </form>             
            
        </div>
        
        
    </div>
    
    
    
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
  </body>
</html>
