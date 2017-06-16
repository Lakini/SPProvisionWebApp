<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Subscribe to the API s</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    </head>
    <body>
        <div class="jumbotron text-center">
            <h1>Service Provider Provisioning Tool</h1>
        </div>

        <div class="row">
            <div class="col-sm-1"></div>
            <div class="col-sm-10">
                <%
                    String statusVal = request.getAttribute("status").toString();

                    if (statusVal.equalsIgnoreCase("Success")) {
                %>
                <p style="font-size:170%;">Step 05: <small>Subscribe to the APIs </small></p><br>
                <form style="font-size:120%;" method="GET" action="subscribeToApis">

                    <!--                    <br>
                                        <input type="checkbox" name="api1" value="api1"> Subscribe to API1<br>
                                        <input type="checkbox" name="api2" value="api2"> Subscribe to API2<br>
                                        <input type="checkbox" name="api3" value="api3" checked> Subscribe to API3<br>
                                        <input type="checkbox" name="api4" value="api4" checked> Subscribe to API4<br>
                                        <input type="checkbox" name="api5" value="api5" checked> Subscribe to API5<br>
                                        <input type="checkbox" name="api6" value="api6" checked> Subscribe to API6<br>
                    
                                        <br>
                                        If you wish to proceed,Please click "Proceed button"<br>
                                        <input style="width: 200px;" type="submit" value="Proceed"/>-->

                    <br>
                    If you wish to proceed,Please subscribe to the APIs from the store and click "Proceed button"<br>
                    <input style="width: 200px;" type="submit" value="Proceed"/>

                    <jsp:include page="_footer.jsp"></jsp:include>
                    </form> 
                <%
                } else {
                %>
                <h2>You can not proceed from here!!!</h2><br>
                <%
                    }
                %>
            </div>
            <div class="col-sm-1"></div>
        </div>
    </body>
</html>