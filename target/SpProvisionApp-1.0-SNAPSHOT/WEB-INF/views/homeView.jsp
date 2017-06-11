<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home Page</title>
</head>
<body>
    
     <jsp:include page="_header.jsp"></jsp:include>
   
Please insert the step number in the given text box.If you start from No.1 the tool will start the provisioning from the begining.<br>
You can continue from the middle by entering the relevent step as well.The steps are described in the bellow.<br><br>

<form method="GET" action="go">
  Step Number:<br>
  <input type="text" name="stepNumber" value= "${step.stepNumber}" />
  <br><br>
  <input type="submit" value="Proceed"/>
</form> 
<br><br>

Steps:<br>

<ul>
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
</body>
</html>