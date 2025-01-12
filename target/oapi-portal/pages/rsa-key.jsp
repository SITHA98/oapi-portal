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
	String GR_ID = (String)session.getAttribute("PRINCE_GR_ID");
	String GR_NAME = (String)session.getAttribute("PRINCE_GR_NAME");
	String pId = (String) request.getParameter("id");
	String flag = (String) request.getParameter("flag");
	RsaKeyDa rd=(RsaKeyDa)context.getBean("rsaKeyDa");
	if(pId==null){
		pId="0";
	}
	if(flag==null){
		flag="New";
	}
	RsaKeyInfo r=rd.getInfo(Integer.parseInt(pId));
%>

<input type="hidden" id="txt_partner_id_rsa_key" name="txt_partner_id_rsa_key" value="<% out.print(GR_ID); %>" />
	
    <div class="toppanel">
    	<button id="btn_save_rsa_key" style="height: 35px;width: 100px;">
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
         	<td colspan="4"><textarea rows="4" cols="80" style="width: 100%;" id="txt_public_key_rsa_key" name="txt_public_key_rsa_key"></textarea></td>  
        <tr>     
	</table>
</div>

<script src="assets/alert/dialogbox.js"></script>
<script>
$(document).ready(function() {
	$('.preloading').fadeOut(500,function(){
		$('.content').fadeIn(500);
	});
} );
</script>

<script type="text/javascript">
$(function(){
	<%  
    	if(pId!=null && Integer.parseInt(pId)>0){%>
    		$('#txt_public_key_rsa_key').val("<%out.print(r.getPublic_key());%>");	
	<%}%>
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

$('#btn_save_rsa_key').click(function(e){
	if( $('#txt_public_key_rsa_key').val().trim() == ""){
 		userdailog('Please enter RSA Key');
 		$('#txt_public_key_rsa_key').focus();
 		return false;
 	}		
 	
 	$.ajax({
		cache: false,
	    url : "${pageContext.request.contextPath}/partnership/rsa-key",
	    type : "POST",
	    data:{
		   	pID:<%out.print(pId);%>,
	       	partner_id:<%out.print(GR_ID);%>,
	       	partner_name:"<%out.print(GR_NAME);%>",
	       	public_key:$('#txt_public_key_rsa_key').val(),
		    created_by:<%out.print(LOGIN_USER_ID);%>
		},     
	    success : function(data){        
	       	if(data>0){	                		
		       	userdailog('Sucessfull');
		       	$('#txt_public_key_rsa_key').val("");	        
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