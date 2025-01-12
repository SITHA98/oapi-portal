<%@page import="sitha.rupp.service.PrincebankComponent"%>
<%@page import="sitha.rupp.service.RsaKeyDa"%>
<%@page import="sitha.rupp.model.RsaKeyInfo"%>
<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.apache.commons.beanutils.converters.ClassConverter"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<% 	
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	int LOGIN_USER_ID=component.USER_ID(request);
	if(LOGIN_USER_ID==0){
		out.print("Session timeout. Please login again !");
		return;
	}
	String approval_role = (String)session.getAttribute("APPROVAL_ROLE");
	if (!(approval_role.equals("1"))){
		out.print("Sorry, you are not approval role !");
		return;
	}
	String GR_ID = (String)session.getAttribute("PRINCE_GR_ID");
	String GR_NAME = (String)session.getAttribute("PRINCE_GR_NAME");
	String phone_number = (String)session.getAttribute("PHONE_NUMBER");
%>

<div class="toppanel">
	<button id="btn_save_rsa_key_new" style="height: 35px;width: 100px;">
       	<i class="fa fa-save"></i> Save
    </button>
</div> 
<div class="preloading" style="left: 55%;margin-right: -45%;position: absolute;margin: 0px;transfrom:translate(-50%,-50%); ">
    <img src="loading/img/processing.gif" width="" />
</div>
<div class="col-xs-12 content" style="margin-top:0px; display: none;" >	
    <div style="height: 20px;"></div> 
	<table class="form">   
	    <tr>
         	<td align="right" valign="top" >New Public Key<span style="color: red;"> * </span> : </td> 
         	<td colspan="4"><textarea rows="6" cols="80" style="width: 100%;" id="txt_public_key_rsa_key_new" name="txt_public_key_rsa_key_new"></textarea></td>  
        <tr>     
	</table>
</div>

<script>
$(document).ready(function() {
	$('.preloading').fadeOut(500,function(){
		$('.content').fadeIn(500);
	});
} );
</script>
<script src="assets/alert/dialogbox.js"></script>
<script type="text/javascript">   
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

$('#btn_save_rsa_key_new').click(function(e){
	if( $('#txt_public_key_rsa_key_new').val().trim() == ""){
 		userdailog('Please enter new RSA Key');
 		$('#txt_public_key_rsa_key_new').focus();
 		return false;
 	}
	
	$.ajax({
		cache: false,
	    url : "${pageContext.request.contextPath}/partnership/rsa-key-new",
	    type : "POST",
	    data:{
	       	partner_id:<%out.print(GR_ID);%>,
	       	partner_name:"<%out.print(GR_NAME);%>",
	       	p_number:"<%out.print(phone_number);%>",
	       	public_key:$('#txt_public_key_rsa_key_new').val(),
		    created_by:<%out.print(LOGIN_USER_ID);%>	    
		},     
	    success : function(data){        
	       	if(data>0){	                		
	       		$.dialogbox({
	       			type:'msg',
	       			width:400,
	       			title:'Please enter OTP code.',
	       			icon:1,
	       			content:'<input type="text" id="txt_rsa_key_new_confirm_otp" name="txt_rsa_key_new_confirm_otp" /><div id="msg-error" style="color:red;"></div>',
	       			closeBtn:true,
	       			btn:['Confirm','Cancel'],
	       			call:[
	       				function(){
	       					if ($('#txt_rsa_key_new_confirm_otp').val()==""){
	       						$('#txt_rsa_key_new_confirm_otp').focus();
	       						return;
	       					}
	       					$.ajax({
	       						cache: false,
	       					    url : "${pageContext.request.contextPath}/partnership/rsa-key-approve",
	       					    type : "POST",
	       					    data:{
	       					       	partner_id:<%out.print(GR_ID);%>,
	       					       	otp:$('#txt_rsa_key_new_confirm_otp').val(),
	       					       	approval_by:<%out.print(LOGIN_USER_ID);%>
	       						},     
	       					    success : function(data){        
	       					       	if(data==1){
	       								$('#txt_public_key_rsa_key_new').val('');
	       						       	userdailog('Sucessfull');
	       					       	}else if (data==2){
	       					       		$("#msg-error").text("Your confirm code was expired.");
	       					       		$('#txt_rsa_key_new_confirm_otp').focus();
	       					       	}else{
	       					       		$("#msg-error").text("Your confirm code is not match.");
		       					       	$('#txt_rsa_key_new_confirm_otp').focus();
	       					       	}	       
	       					    },
	       					    error : function() {
	       					       	userdailog('Error');
	       					    }
	       					});	
	       				     //$.dialogbox.close();
	       				},
	       				function(){
	       					$.dialogbox.close();
	       					$.ajax({
	       						cache: false,
	       					    url : "${pageContext.request.contextPath}/partnership/rsa-key-cancel",
	       					    type : "POST",
	       					 	data:{
	       					       	partner_id:<%out.print(GR_ID);%>,
	       					       	approval_by:<%out.print(LOGIN_USER_ID);%>
	       						},     
	       					    success : function(data){ }
	       					});
	       				}
	       			]
	       		});
	       	}else{
	       		userdailog('Error');
	       	}
	    },
	    error : function() {
	       	userdailog('Error');
	    }
	});		
});
 </script>