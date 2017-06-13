<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign up as a new user</title>
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
                <p style="font-size:170%;">Step 01: <small>Create a new API Manager user for the new application </small></p>
                <form style="font-size:120%;" method="GET" action="loginAndSignUp">
                    Application Name:<br>
                    <input style="width: 200px;" type="text" name="appName" value= "${app.appName}" />
                    <br>
                    Application Short Name:<br>
                    <input style="width: 200px;" type="text" name="appShortName" value= "${app.appShortName}" />
                    <br>
                    First Name:<br>
                    <input style="width: 200px;" type="text" name="firstName" value= "${app.firstName}" />
                    <br>
                    Last Name:<br>
                    <input style="width: 200px;" type="text" name="lastName" value= "${app.lastName}" />
                    <br>
                    Developer Email:<br>
                    <input style="width: 200px;" type="text" name="devEmail" value= "${app.devEmail}" />
                    <br><br>
                    If you wish to proceed,Please click "Proceed button"<br>
                    <input style="width: 200px;" type="submit" value="Proceed"/>

                    <jsp:include page="_footer.jsp"></jsp:include>
                </form> 
            </div>
            <div class="col-sm-1"></div>
        </div>
    </body>
</html>
