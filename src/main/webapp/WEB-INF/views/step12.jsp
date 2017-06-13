<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test the updated application by getting Authorize code,Token and UserInfo </title>
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

                    if (statusVal.equalsIgnoreCase("Success")) {
                %>
                <p style="font-size:170%;">Step 11: <small>Test the updated application by getting Authorize code,Token and UserInfo</small></p><br>
                <form style="font-size:120%;" method="GET" action="updateTestApp">
                    <div style="width: 200px;" class="form-group">
                        <label for="comment">Comment:</label>
                        <textarea style="background-color:#C6D2E7" class="form-control" rows="5" id="comment"></textarea>
                        <label for="comment">Comment:</label>
                        <textarea style="background-color:#C6D2E7" class="form-control" rows="5" id="comment"></textarea>
                        <label for="comment">Comment:</label>
                        <textarea style="background-color:#C6D2E7" class="form-control" rows="5" id="comment"></textarea>
                    </div> 

                    <br>
                    If you wish to Complete the Service Provider Provisioning,Please test the above commands and if they success, click "Complete"<br>
                    <input style="width: 200px;" type="submit" value="Complete"/>

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