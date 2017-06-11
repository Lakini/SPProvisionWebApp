<%-- 
    Document   : step3
    Created on : Jun 9, 2017, 4:17:33 PM
    Author     : lakini
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Activated the newly created application</title>
    </head>
    <body>
        <jsp:include page="_header.jsp"></jsp:include>
            <br>
            <h3>STEP 03</h3><br>
            <div style="background: #E0E0E0;">
                <h1>${output}</h1>
        </div>
        <br><br>
        <h2>${message}</h2>
        <br>

        <%
            String statusVal = request.getAttribute("status").toString();
            System.out.println("Status in JSP "+statusVal );
            
           
            if (statusVal.equals("1")) {
        %>
         <form method="GET" action="activateApplication">
            Application Name:<br>
            <input type="text" name="appName" value= "${app.appName}" />
            <br>
            Next: Activate Application
            <br><br>
            If you wish to proceed,Please click "Proceed button"<br>
            <input type="submit" value="Proceed"/>

        </form> 
             <%
        } else {
        %>
        <h2>You can not proceed from here!!!</h2><br>
        <%
            }
        %>
       
        <br><br>
        <jsp:include page="_footer.jsp"></jsp:include>
    </body>
</body>
</html>
