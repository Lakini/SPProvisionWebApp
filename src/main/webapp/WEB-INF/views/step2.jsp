<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login and create an application</title>
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

                <div style="font-size:170%;" class="well well-lg">
                    Output:<br>
                    <p>${output}</p>
                </div>
                <br>
                <div style="font-size:170%;" class="well well-lg">
                    Message:<br>
                    <p>${message}</p>
                </div>

                <%
                    String statusVal = request.getAttribute("status").toString();
                    System.out.println("Status in JSP " + statusVal);

                    if (statusVal.equals("1")) {
                %>
                <p style="font-size:170%;">Step 02: <small>Login to the API Manager and create a new application </small></p>
                <form style="font-size:120%;" method="GET" action="createApplication">
                    Application Name:<br>
                    <input style="width: 200px;" type="text" name="appName" value= "${app.appName}" />
                    <br>
                    Application Short Description:<br>
                    <input style="width: 200px;" type="text" name="appDesc" value= "${app.appDesc}" />
                    <br>
                    CallBack URL:<br>
                    <input style="width: 200px;" type="text" name="appCallBackUrl" value= "${app.appCallBackUrl}" />
                    <br>
                    Application Tier:<br>
                    <input style="width: 200px;" type="text" name="tier" value= "${app.tier}" />

                    <br><br>
                    If you wish to proceed,Please click "Proceed button"<br>
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
