<%@page import="sitha.rupp.service.PrincebankComponent"%>
<%@page import="sitha.rupp.service.RsaKeyDa"%>
<%@page import="sitha.rupp.model.RsaKeyInfo"%>
<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<% 	
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	int LOGIN_ID=component.USER_ID(request);
	if(LOGIN_ID==0){
		out.print("Session timeout. Please login again !");
		return;
	}
	String app_role = (String)session.getAttribute("APPROVAL_ROLE");
	if (!(app_role.equals("1"))){
		out.print("Sorry, you are not approval role !");
		return;
	}
	String G_ID = (String)session.getAttribute("PRINCE_GR_ID");
	String p_number = (String)session.getAttribute("PHONE_NUMBER");
	String pId = (String) request.getParameter("id");
	RsaKeyDa rd=(RsaKeyDa)context.getBean("rsaKeyDa");
	RsaKeyInfo r=rd.getInfo(Integer.parseInt(pId));
%>

<div class="toppanel">
    <button id="btn_rsa_key_confirm_otp" style="height: 35px;width: 100px;">
       	<i class="fa fa-save"></i> Save
    </button>
    <button id="btn_resend_otp" style="height: 35px;width: 100px;">
	   	<i class="fa fa-refresh"></i> Resend OTP
	</button>
</div> 
<div class="preloading" style="left: 55%;margin-right: -45%;position: absolute;margin: 0px;transfrom:translate(-50%,-50%); ">
    <img src="loading/img/processing.gif" width="" />
</div>
<div class="col-xs-12 content" style="margin-top:0px; display: none;" >	
    <div style="height: 20px;"></div> 
	<table class="form">   
       	<tr>
	    	<td align="right">Confirm OTP<span style="color: red;"> * </span> : </td>                	
	        <td ><input type="text" id="txt_confirm_otp" name="txt_confirm_otp" /></td>
        <tr>     
	</table>
</div>

<script>
$(document).ready(function() {
	$('.preloading').fadeOut(500,function(){
		$('.content').fadeIn(500);
	});
});
</script>
<script src="assets/alert/dialogbox.js"></script>
<script type="text/javascript">
$('#btn_resend_otp').click(function(){
	<%-- <%rd.sendOTP(p_number,Integer.parseInt(G_ID),LOGIN_ID);%> --%>		
});

function userdailog(a){
	$.dialogbox({
		type:'msg',
		width:400,
		title:'Information',
		icon:1,
		content:a,
		btn:['Ok'],
		call:[
			function(){
				$.dialogbox.close(); 
			}
		]
	});
}

$('#btn_rsa_key_confirm_otp').click(function(e){
	if( $('#txt_confirm_otp').val().trim() == ""){
 		userdailog('Please enter OTP');
 		$('#txt_confirm_otp').focus();
 		return false;
 	}
 	
 	$.ajax({
		cache: false,
	    url : "${pageContext.request.contextPath}/partnership/rsa-key-approve",
	    type : "POST",
	    data:{
	       	partner_id:<%out.print(G_ID);%>,
	       	otp:$('#txt_confirm_otp').val(),
	       	approval_by:<%out.print(LOGIN_ID);%>
		},     
	    success : function(data){        
	       	if(data==1){	                		
		       userdailog('Sucessfull');
		       $('#txt_confirm_otp').val('');
		       $('#txt_confirm_otp').attr('disabled', true);
		       $('#btn_rsa_key_confirm_otp').attr('disabled', true);
	       	}else if (data==2){
	       		userdailog('Your confirm code was expired.');
	       	}else{
	       		userdailog('Your confirm code is not match.');
	       	}	       
	    },
	    error : function() {
	       	userdailog('Error');
	    }
	});		
});
 </script>