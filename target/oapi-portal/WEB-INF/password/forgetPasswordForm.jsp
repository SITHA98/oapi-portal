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
  <h2 align="center">Request forget password form</h2>
  <div class="card">
  	<div style="width:100%;border:#CCC 0px solid;text-align:center;margin-top: -40px;">
    	<img src="img/prince_logo.png" width="150" />
    </div>
    <div style="width:100%;border:#30C 0px solid;text-align:center;font-size:16px;margin:10px 0px 25px 0px;">
    	<b>Simply Innovative</b>
    </div>
    <form action="${pageContext.request.contextPath}/forgetPasswordProcess" method="POST">
      <div class="input-container">
        <input type="text" name="txtUsername" id="txtUsername"  required="required"/>
        <label for="">Username</label>
        <div class="bar"></div>
      </div>
      <div class="input-container">
        <input type="text" name="txtEmail" id="txtEmail" required="required"/>
        <label for="">Email</label>
        <div class="bar"></div>
      </div>
      <div class="input-container">
        <input type="text" name="txtPhone" id="txtPhone" required="required"/>
        <label for="">Phone</label>
        <div class="bar"></div>
      </div>
      <%-- <% String message=(String)request.getAttribute("message"); %>
      <div><% out.print(message); %></div> --%>
      <div align="center" class="button-container">
      	<!-- class="btnLogin" --> 
      	<button class="btn btn-success" style="background-color: #BE955B;">Reset Password</button>
      	<br><br>
      	
      </div>
    </form>
  </div>
</div>
<a href="javascript:void(0)" class="loading"></a>
</body>
</html>
<script type="text/javascript">
$(document).ready(function(){
	$('.btnResetPassword').click(function(){
		var username = $('#txtUsername').val();
		var email = $('#txtEmail').val();
		var phone = $('#txtPhone').val();
		if(username.trim() == "" || email.trim() == ""){
			return false;
		}else{
			$('.loading').click();
			return true;
		}
	});
});
</script>