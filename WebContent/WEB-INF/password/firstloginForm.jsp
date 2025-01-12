<!DOCTYPE html>
<html lang="en" >
<head>
<meta charset="ISO-8859-1"> 
<meta charset="UTF-8">
<!-- <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"> -->
<title>Prince Bank Report</title>
<link rel="stylesheet" href="loading/style.css">
<!--Loading Page-->
<script src="loading/jquery-1.7.2.min.js"></script>
<link type='text/css' href='loading/basic.css' rel='stylesheet' media='screen' />
<script type='text/javascript' src='loading/jquery.simplemodal.js'></script>
<script type='text/javascript' src='loading/basic.js'></script>
<!--End Loading Page-->
</head>
<body style="/* background-image: url('img/bg.jpg'); */background-color:">
<!-- <div class="rerun"></div> -->
<div class="container" style="background-color: #fcf7f8;">
  <span>&nbsp;</span>
  <h2 align="center">Request Password</h2>
  <div class="card">
  	<!-- <div style="width:50%;border:#CCC 0px solid;text-align:center;margin-top: -40px;">
    	<img src="img/prince_logo.png" width="50" />
    </div>
    <div style="width:100%;border:#30C 0px solid;text-align:center;font-size:16px;margin:10px 0px 25px 0px;">
    	<b>Simply Innovative</b>
    </div> -->
    <form action="${pageContext.request.contextPath}/resetpassword" method="POST">
      <div class="input-container">
        <input type="password" name="txtCurrentPWD" id="txtCurrentPWD"  required="required"/>
        <label for="">Current Password</label>
        <div class="bar"></div>
      </div>
      <div class="input-container">
        <input type="password" name="txtNewPWD" id="txtNewPWD" required="required"/><input type="text" id="password_strength" name="password_strength" style="border-color: white;"/>
        <label for="">New Password</label>
        
      </div>
      <div class="input-container">
        <input type="password" name="txtConfirm" id="txtConfirm" required="required"/>
        <label for="">Confirm</label>
        <div class="bar"></div>
      </div>
      
      <div align="center" style="color: red;" class="button-container"> ${message}</div>
      <div align="center" class="button-container">
      	<!-- class="btnLogin" --> 
      	<button class="btn btn-success" style="background-color: #BE955B;">Reset Password</button>
      	<br><br>
      	
      </div>
    </form>
  </div>
</div>
</body>
</html>
<script type="text/javascript">
    $(function () {
        $("#txtNewPWD").bind("keyup", function () {
            //TextBox left blank.
            if ($(this).val().length == 0) {
                $("#password_strength").html("");
                return;
            }
 
            //Regular Expressions.
            var regex = new Array();
            regex.push("[A-Z]"); //Uppercase Alphabet.
            regex.push("[a-z]"); //Lowercase Alphabet.
            regex.push("[0-9]"); //Digit.
            regex.push("[$@$!%*#?&]"); //Special Character.
 
            var passed = 0;
 
            //Validate for each Regular Expression.
            for (var i = 0; i < regex.length; i++) {
                if (new RegExp(regex[i]).test($(this).val())) {
                    passed++;
                }
            }
 
 
            //Validate for length of Password.
            if (passed > 2 && $(this).val().length > 8) {
                passed++;
            }
 
            //Display status.
            var color = "";
            var strength = "";
            switch (passed) {
                case 0:
                case 1:
                    strength = "Weak";
                    color = "red";
                    break;
                case 2:
                    strength = "Good";
                    color = "darkorange";
                    break;
                case 3:
                case 4:
                    strength = "Strong";
                    color = "green";
                    break;
                case 5:
                    strength = "Very Strong";
                    color = "darkgreen";
                    break;
            }
            $("#password_strength").val(strength);
            $("#password_strength").css("color", color);
        });
    });
</script>
