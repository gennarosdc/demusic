<%-- 
    Document   : errorPage
    Created on : Sep 15, 2017, 4:08:58 PM
    Author     : gennaro
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Error</title>
        

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
	<script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>     
    </head>
    <body>
       
        
      

        <ol class="breadcrumb">
        <li><a class="active" href="${pageContext.request.contextPath}">Home</a></li>

        </ol>
        <div class="clear"></div>
        <div class="col-md-10">
                <h1><span class="fa fa-connectdevelop"></span> An Error has occurred! </h1>
        </div>
        <div class="col-md-2">
                <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-info col-md-12 col-xs-12"><span class="glyphicon"></span>Torna alla home</a>      
        </div>
        <div class="clear"></div>
        <hr/>

        <div class="col-md-12">
         <%String err = (String)request.getAttribute("ERR_MESSAGE"); %>
         <%= err%>                      

        </div>
        
        
        
        
        
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/ie10-viewport-bug-workaround.js"></script>       
    </body>
</html>
