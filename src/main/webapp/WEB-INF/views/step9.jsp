<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Scope Configurations for the application</title>
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
                <p style="font-size:170%;">Step 09: <small>Scope Configurations for the application </small></p><br>
                <form style="font-size:120%;" method="GET" action="scopeConfigurations">
                    Consumer Key:<br>
                    <input style="width: 400px;" type="text" name="consumerKey" value= "${app.consumerKey}" />
                    <br><br>
                    Client Secret Key:<br>
                    <input style="width: 400px;" type="text" name="secretKey" value= "${app.secretKey}" />
                    <br>
                    <br>
                    If you wish to proceed,Please click "Proceed button"<br><br>
                    <input style="width: 200px;" type="submit" value="Proceed"/>

                    <jsp:include page="_footer.jsp"></jsp:include>
                </form> 
            </div>
            <div class="col-sm-1"></div>
        </div>
    </body>
</html>

