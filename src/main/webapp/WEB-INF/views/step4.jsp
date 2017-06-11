<%-- 
    Document   : step4
    Created on : Jun 9, 2017, 4:17:43 PM
    Author     : lakini
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Application to the approved States</title>
    </head>
    <body>
        <jsp:include page="_header.jsp"></jsp:include>
            <br>
            <h3>STEP 04</h3><br>
            <div style="background: #E0E0E0;">
                <h1>${status}</h1>
        </div>
       
        <%
            String statusVal = request.getAttribute("status").toString();
           
            if (statusVal.equalsIgnoreCase("Success")) {
        %>
         <form method="GET" action="updateApplication">
            Next: Update application to the approved state
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