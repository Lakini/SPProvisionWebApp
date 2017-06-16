<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Home Page</title>
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
                <h3>Please insert the step number in the given text box and select the environment which you need to create the application.
                    If you start from step No.1 the tool will start the provisioning from the beginning.<br>
                    You can continue from the middle by entering the relevant step as well.The steps are described in the below.<br><br></h3>
                <form style="font-size:120%;" method="GET" action="go">
                    Environment:<br>
                    <select name= "environment" style="width: 200px;">
                        <option value="preprod">Pre Production</option>
                        <option value="prod">Production</option>
                    </select><br><br>
                    Step Number:<br>
                    <input style="width: 200px;" type="text" name="stepNumber" value= "${step.stepNumber}" autocomplete='off'/>
                    <br><br>
                    <input style="width: 200px;" type="submit" value="Proceed"/>
                </form> 
                <br><br>

                <p style="font-size:120%;">Steps:</p><br>

                <ul style="font-size:120%;">
                    <li>1. Login and Sign In to the API Manager</li>
                    <li>2. Create Application</li>
                    <li>3. Activate Application</li>
                    <li>4. Update application to the approved state</li>
                    <li>5. Subscribe to the APIs</li>
                    <li>6. Update Subscriptions</li>
                    <li>7. Populate Subscription Validator</li>
                    <li>8. Generate client key and client secret</li>
                    <li>9. Scope Configurations for the application</li>
                    <li>10. Test the application by getting Authorize code,Token and UserInfo</li>
                    <li>11. Update the client key and client secret</li>
                    <li>12. Test the updated application by getting Authorize code,Token and UserInfo</li>
                </ul>

                <jsp:include page="_footer.jsp"></jsp:include>
            </div>
            <div class="col-sm-1"></div>
        </div>
    </body>
</html>