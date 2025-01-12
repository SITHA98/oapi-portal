<!DOCTYPE html>
<html lang="en" >
<head>
<meta charset="ISO-8859-1"> 
<meta charset="UTF-8">
<!-- <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"> -->
<title>Login to Ly Hour Insurance Report</title>
<link rel="stylesheet" href="loading/style.css">
<!--Loading Page-->
<script src="loading/jquery-1.7.2.min.js"></script>
<link type='text/css' href='loading/basic.css' rel='stylesheet' media='screen' />
<script type='text/javascript' src='loading/jquery.simplemodal.js'></script>
<script type='text/javascript' src='loading/basic.js'></script>
<!--End Loading Page-->
</head>
<!-- 
<body style="/* background-image: url('img/bg.jpg'); */background-color:#bca302">
 -->
<body style="/* background-image: url('img/bg.jpg'); */background-color:#1f3d6d">

<div id="basic-modal-content">
    <img src="loading/img/processing.gif" width="" />
</div>
<div class="rerun"></div>
<div class="container">
  <div class="card" style="margin: top;100px"></div>
  <div class="card">
    <div style="width:100%;border:#CCC 0px solid;text-align:center;">
    	<img src="img/logo1.png" width="150" />
    </div>
    <div style="width:100%;border:#30C 0px solid;text-align:center;font-size:16px;margin:10px 0px 25px 0px;">
    	
    	<b>Simply Innovative</b>
    </div>
    	
    <form action="${pageContext.request.contextPath}/login" method="POST">
      <div class="input-container"> 
        <input type="text" name="txtUsername" id="txtUsername"  required="required"/>
        <label for="">Username</label>
        <div class="bar"></div>
      </div>
      <div class="input-container">
        <input type="password" name="txtPassword" id="txtPassword" required="required"/>
        <label for="">Password</label>
        <div class="bar"></div>
      </div>
       <%
      String log = (String) request.getParameter("log");  
	  if(log == null){
		  log = "1";
	  }
	  else{
		  log = (String) request.getParameter("log");
	  }
      if(Integer.parseInt(log)==0){%>
      <div style="width:100%;border:#30C 0px solid;text-align:center;font-size:18px;margin:-35px 0px 14px 0px;color: red;">
      	Invalid username and password
      </div>
     <% } 
     if(Integer.parseInt(log)==111){%>
      <div style="width:100%;border:#30C 0px solid;text-align:center;font-size:18px;margin:-35px 0px 14px 0px;color: red;">
      	User account was locked
      </div>
     <% } 
     if(Integer.parseInt(log)==222){%>
      <div style="width:100%;border:#30C 0px solid;text-align:center;font-size:18px;margin:-35px 0px 14px 0px;color: red;">
      	User account was suspense
      </div>
     <% } %>

      <div align="center" class="button-container">
      	<!-- class="btnLogin" --> 
      	<button>Login</button>
      	<br><br>
      	<!-- <a href="forgetPasswordForm">Forget Password</a>  -->
      	<p></p>
      	<p></p>
      	<!-- 
      		<a href="whistleblowing">Whistle blowing</a>
      	 -->
      	<p></p>
      	<span>Version 1.0.0.0</span>
      </div>
    </form>
  </div>
</div>
<a href="javascript:void(0)" class="loading"></a>
</body>
</html>
<script type="text/javascript">
$(document).ready(function(){
	$('.btnLogin').click(function(){
		var username = $('#txtUsername').val();
		var password = $('#txtPassword').val();
		if(username.trim() == "" || password.trim() == ""){
			return false;
		}else{
			$('.loading').click();
			return true;
		}
	});
});
</script>