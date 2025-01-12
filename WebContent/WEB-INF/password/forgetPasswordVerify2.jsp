<!DOCTYPE html>
<html lang="en" >
<head>
<meta charset="ISO-8859-1"> 
<meta charset="UTF-8">
<!-- <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"> -->
<title>ResetPassword</title>
<link rel="stylesheet" href="loading/style.css">
<!--Loading Page-->
<script src="loading/jquery-1.7.2.min.js"></script>
<link type='text/css' href='loading/basic.css' rel='stylesheet' media='screen' />
<script type='text/javascript' src='loading/jquery.simplemodal.js'></script>
<script type='text/javascript' src='loading/basic.js'></script>
<!--End Loading Page-->
</head>
<body style="/* background-image: url('img/bg.jpg'); */background-color:">

<div class="rerun"></div>

<div class="container">

  
<h1 align="center">New Password Process form</h1>
  <div class="card">
    <form action="${pageContext.request.contextPath}/submitPassChange" method="POST">
      <div class="input-container">
        <input type="text" name="txtResetCode" id="txtResetCode"  required="required"/>
        <label for="">Reset Code</label>
        <div class="bar"></div>
      </div>
      <div class="input-container">
        <input type="password" name="txtPassword1" id="txtPassword1" required="required"/>
        <label for="">New Password</label>
        <div class="bar"></div>
      </div>
      <div class="input-container">
        <input type="password" name="txtPassword2" id="txtPassword2" required="required"/>
        <label for="">Retype New Password</label>
        <div class="bar"></div>
      </div>
      <%-- <% String message=(String)request.getAttribute("message"); %>
      <div><% out.print(message); %></div> --%>
      <div align="center" class="button-container">
      	<!-- class="btnLogin" --> 
      	<button class="btn btn-success">Submit</button>
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
	$('.btnsubmitPassChange').click(function(){
		var resetcode = $('#txtResetCode').val();
		var passwrod1 = $('#txtPassword1').val();
		var password2 = $('#txtPassword2').val();
		if(resetcode.trim() == "" || passwrod1.trim() == "" || passwrod2.trim()==""){
			return false;
		}else{
			$('.loading').click();
			return true;
		}
	});
});
</script>
<!-- detect idle time -->
<script type="text/javascript">
var idleTime = 0;
$(document).ready(function () {
    //Increment the idle time counter every minute.
    var idleInterval = setInterval(timerIncrement, 60000); // 1 minute
    //Zero the idle timer on mouse movement.
    $(this).mousemove(function (e) {
        idleTime = 0;
    });
    $(this).keypress(function (e) {
        idleTime = 0;
    });
});

function timerIncrement() {
    idleTime = idleTime + 1;
    if ( idleTime > 1 ) { /* 20 minutes */
        //alert('time out');
    	sessionStorage.clear(); 
        location.replace("/princebank-mis");
    	//window.location.reload();
    }
}
</script>
<!-- end detect idle time --> 