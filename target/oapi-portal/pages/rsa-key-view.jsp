<%@page import="sitha.rupp.service.PrincebankComponent"%>
<%@page import="sitha.rupp.service.RsaKeyDa"%>
<%@page import="sitha.rupp.model.RsaKeyInfo"%>
<%@page import="sitha.rupp.model.RsaKeyView"%>
<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.apache.commons.beanutils.converters.ClassConverter"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<% 	
	ApplicationContext context=new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	RsaKeyDa rsa=(RsaKeyDa)context.getBean("rsaKeyDa");
	int LOGIN_USER_ID = component.USER_ID(request);
	if (LOGIN_USER_ID == 0){
		out.print("Session timeout. Please login again !");
		return;
	}
	String approval_role = (String)session.getAttribute("APPROVAL_ROLE");
	if (!(approval_role.equals("1"))){
		out.print("Sorry, you are not approval role !");
		return;
	}
	String GR_ID = (String)session.getAttribute("PRINCE_GR_ID");
	RsaKeyView publicKey = rsa.getPublicKey(Integer.parseInt(GR_ID));
%>
	
<div class="toppanel">
    <button id="btn_view_rsa_key" style="height: 35px;width: 100px;">
       	<i class="fa fa-refresh"></i> Refresh
    </button>
</div> 
<div class="preloading" style="left: 55%;margin-right: -45%;position: absolute;margin: 0px;transfrom:translate(-50%,-50%); ">
    <img src="loading/img/processing.gif" width="" />
</div>

<div class="col-xs-12 content" style="margin-top:0px; display: none;" >	
    <div style="height: 20px;"></div>   
    <table class="form">   
	    <tr>
         	<td align="right" valign="top" >Partner Public Key : </td> 
         	<td colspan="2">
	         	<%-- <div class="input-group">
			        <input type="password" class="form-control pwd" style="width: 600px" value="<%out.print(publicKey);%>">
					<span class="input-group-btn">
						<button class="btn btn-default reveal" type="button"><i class="glyphicon glyphicon-eye-open"></i></button>
					</span>
				</div> --%>
				<input type="password" id="password" style="width: 700px" value="<%out.print(publicKey.getPartner_public_key());%>">
				<button onclick="toggler(this)" type="button">Show</button>
			</td>  
        <tr>  
        
        <tr>
         	<td align="right" valign="top" >Prince Public Key : </td> 
         	<td colspan="2">
	         	<%-- <div class="input-group">
			        <input type="password" class="form-control pwd" style="width: 600px" value="<%out.print(publicKey);%>">
					<span class="input-group-btn">
						<button class="btn btn-default reveal" type="button"><i class="glyphicon glyphicon-eye-open"></i></button>
					</span>
				</div> --%>
				<input type="password" id="princePassword" style="width: 700px" value="<%out.print(publicKey.getPrince_public_key());%>">
				<button onclick="prince(this)" type="button">Show</button>
			</td>  
        <tr>   
	</table>
</div>

<script>
$(document).ready(function() {
	$('.preloading').fadeOut(500,function(){
		$('.content').fadeIn(500);
	});
} );

$('#btn_view_rsa_key').click(function(){
	$("#rsakeyview").load("pages/rsa-key-view.jsp");		
});
</script>

<script>
function toggler(e) {
    if( e.innerHTML == 'Show' ) {
        e.innerHTML = 'Hide'
        document.getElementById('password').type="text";
    } else {
        e.innerHTML = 'Show'
        document.getElementById('password').type="password";
    }
}

function prince(e) {
    if( e.innerHTML == 'Show' ) {
        e.innerHTML = 'Hide'
        document.getElementById('princePassword').type="text";
    } else {
        e.innerHTML = 'Show'
        document.getElementById('princePassword').type="password";
    }
}

/* $(".reveal").on('click',function() {
    var $pwd = $(".pwd");
    if ($pwd.attr('type') === 'password') {
        $pwd.attr('type', 'text');
    } else {
        $pwd.attr('type', 'password');
    }
}); */
</script>