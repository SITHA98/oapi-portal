<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="sitha.rupp.service.PrincebankComponent"%>
<% 	
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	int LOGIN_USER_ID = component.USER_ID(request);
	if (LOGIN_USER_ID == 0){
		out.print("Session timeout. Please login again !");
		return;
	}
	String GR_ID = (String)session.getAttribute("PRINCE_GR_ID");
	String GR_NAME = (String)session.getAttribute("PRINCE_GR_NAME");
%>
<div class="preloading" style="left: 55%;margin-right: -45%;position: absolute;margin: 0px;transfrom:translate(-50%,-50%); ">
    <img src="loading/img/processing.gif" width="" />
</div>
<div class="col-xs-12 content" style="margin-top:0px; display: none;" >	
    <div style="height: 15px;"></div> 
	<table class="form">
	    <tr>
	        <td align="right">Partner ID<span style="color: red;"> * </span> : </td>
	       	<td ><input disabled="disabled" type="text" id="txt_partner_id" name="txt_partner_id" value="<%out.print(GR_ID);%>"/></td>
	    </tr>
	    <tr>
	    	<td align="right">Partner Name<span style="color: red;"> * </span> : </td>                	
	        <td><input disabled="disabled" type="text" style="width: 400px;" id="txt_partner_name" name="txt_partner_name" value="<%out.print(GR_NAME);%>"/></td>
	    </tr>
	    <tr>
	    	<td align="right">Secret Key<span style="color: red;"> * </span> : </td>                	
	        <td ><input disabled="disabled" type="text" style="width: 100%;" id="txt_secret_key" name="txt_secret_key" value="<%out.print("abc");%>"/></td>
	    </tr>
	</table>
</div>
<script>
$(document).ready(function() {
	$('.preloading').fadeOut(500,function(){
		$('.content').fadeIn(500);
	});
} );
</script>