<%-- 
    Document   : step10
    Created on : Jun 9, 2017, 4:18:45 PM
    Author     : lakini
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Updates the application credentials</title>
    </head>
    <body>
        <jsp:include page="_header.jsp"></jsp:include>
       <form method="GET" action="updateApp">
            
            Next: Update the application with user given client ID and Client secret.
            <br><br>
            If you wish to proceed,Please click "Proceed button"<br>
            <input type="submit" value="Proceed"/>

        </form> 
        <jsp:include page="_footer.jsp"></jsp:include>
    </body>