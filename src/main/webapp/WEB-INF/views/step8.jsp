<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Generate client key and client secret </title>
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
                <p style="font-size:170%;">Step 08: <small>Generate client key and client secret</small></p><br>
                <form style="font-size:120%;" method="GET" action="generateClientkeyAndSecret">
                    <br>
                    If you wish to proceed,Please click the Generate button and then click "Proceed button"<br>
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

