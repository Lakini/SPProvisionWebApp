<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login To API Manager</title>
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
                <form style="font-size:120%;" method="GET" action="login">
                    Environment:<br>
                    <select name= "environment" style="width: 300px;">
                        <option value="preprod">Pre Production</option>
                        <option value="prod">Production</option>
                    </select><br><br>
                    User Name:<br>
                    <input style="width: 200px;" type="text" name="userName" value= "${app.userName}" />
                    <br><br>
                    Application Name:<br>
                    <input style="width: 200px;" type="text" name="appName" value= "${app.appName}" />
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
