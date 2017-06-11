<%-- 
    Document   : step2
    Created on : Jun 9, 2017, 3:35:11 PM
    Author     : lakini
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login And Sign To API Manager</title>
    </head>
    <body>
        <jsp:include page="_header.jsp"></jsp:include>
        <br>
        <h3>STEP 01</h3><br>
        <form method="GET" action="loginAndSignUp">
            Application Name:<br>
            <input type="text" name="appName" value= "${app.appName}" />
            <br>
            Application Short Name:<br>
            <input type="text" name="appShortName" value= "${app.appShortName}" />
            <br>
            Developer Email:<br>
            <input type="text" name="devEmail" value= "${app.devEmail}" />
            <br><br>
            If you wish to proceed,Please click "Proceed button"<br>
            <input type="submit" value="Proceed"/>
        </form> 
            <br><br>
        <jsp:include page="_footer.jsp"></jsp:include>
    </body>
</html>
