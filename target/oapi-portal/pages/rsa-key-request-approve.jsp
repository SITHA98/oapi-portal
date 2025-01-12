<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="sitha.rupp.service.PrincebankComponent" %>
<%@page import="sitha.rupp.model.RsaKeyList"%>
<%@page import="sitha.rupp.service.RsaKeyDa"%>
<%@page import="java.util.List"%>

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
	String phone_number = (String)session.getAttribute("PHONE_NUMBER");
%>

<script>
$(document).ready(function() {
	$('.preloading').fadeOut(500,function(){
		$('.content').fadeIn(500);
	});
});
</script>
	
<div class="toppanel">
    <button id="btn_refresh_rsa_key_request_approve" style="height: 35px;width: 100px;">
       	<i class="fa fa-refresh"></i> Refresh
    </button>
</div>

<div class="preloading" style="left: 55%;margin-right: -45%;position: absolute;margin: 0px;transfrom:translate(-50%,-50%); ">
    <img src="loading/img/processing.gif" width="" />
</div>

<div class="col-xs-12 content" style="margin-top:0px; display: none;" >	
	<table id="dg-rsa-key-request_approve" class="table table-bordered table-hover display">
		<thead>
			<tr>
				<th align="center">Partner Name</th>
				<th align="center">Public Key</th>
				<th align="center">Action</th>
			</tr>
		</thead>
		<tbody>
			<%
              	List<RsaKeyList> ls=rsa.getRequest(Integer.parseInt(GR_ID));
              	for(int i=0;i<ls.size();i++){
              		RsaKeyList r=(RsaKeyList)ls.get(i);
	             %>
				<tr>
	                <td><% out.print(r.getPartner_name());%></td>
	                <td><% out.print(r.getPublic_key());%></td>
	                <td align="center">
	                  	<a href="#"onclick="rsa_key_approve(<%out.print(r.getPartner_id());%>);">
		            		<i class="fa fa-edit"></i> Approve</a>  
	                </td>
	           	</tr>
			<%}%>
		</tbody>
	</table>
</div>
<script type="text/javascript">
	$('#dg-rsa-key-request_approve').DataTable();
	
	$('#btn_refresh_rsa_key_request_approve').click(function(){
		$("#rsakeyrequestapprove").load("pages/rsa-key-request-approve.jsp");		
	});
	
	function rsa_key_approve(GR_ID){
		<%-- <%rsa.sendOTP(phone_number,Integer.parseInt(GR_ID),LOGIN_USER_ID);%> --%>	
	 	tabs.addTab({			
			title: 'RSA Key confirm OTP',
			id: 'rsa-key-confirm-otp',
			ajaxUrl: 'pages/rsa-key-confirm-otp.jsp?flag=A&id='+GR_ID
		});		
	 	$("#rsakeyrequestapprove2").load('pages/rsa-key-confirm-otp.jsp?flag=A&id='+GR_ID);
	} 
</script>
