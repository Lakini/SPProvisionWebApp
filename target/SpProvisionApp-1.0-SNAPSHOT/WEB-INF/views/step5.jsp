<%-- 
    Document   : step5
    Created on : Jun 9, 2017, 4:17:53 PM
    Author     : lakini
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Subscribe to the APIs</title>
    </head>
    <body>
        <jsp:include page="_header.jsp"></jsp:include>
            <br>
            <h3>STEP 05</h3><br>
            <div style="background: #E0E0E0;">
                <h1>${status}</h1>
        </div>
       
        <%
            String statusVal = request.getAttribute("status").toString();
           
            if (statusVal.equalsIgnoreCase("Success")) {
        %>
         <form method="GET" action="subscribeToApis">
            Application Name:<br>
            <input type="text" name="appName" value= "${app.appName}" />
            <br>
             <input type="checkbox" name="api1" value="api1"> Subscribe to API1<br>
             <input type="checkbox" name="api2" value="api2"> Subscribe to API2<br>
             <input type="checkbox" name="api3" value="api3" checked> Subscribe to API3<br>
             <input type="checkbox" name="api4" value="api4" checked> Subscribe to API4<br>
             <input type="checkbox" name="api5" value="api5" checked> Subscribe to API5<br>
             <input type="checkbox" name="api6" value="api6" checked> Subscribe to API6<br>

            <br>
            Next: Update the Subscriptions
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