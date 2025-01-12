<%@page import="sitha.rupp.helper.ClsHelper"%>
<%@page import="sitha.rupp.model.RsaKeyList"%>
<%@page import="sitha.rupp.service.RsaKeyDa"%>
<%@page import="java.util.List"%>
<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="sitha.rupp.service.PrincebankComponent" %>

<%
	ApplicationContext context=new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	RsaKeyDa rsa=(RsaKeyDa)context.getBean("rsaKeyDa");
	int USER_ID = component.USER_ID(request);
	if (USER_ID == 0){
		out.print("Session timeout. Please login again !");
		return;
	}
	String GR_ID = (String)session.getAttribute("PRINCE_GR_ID");
%>

<script>
$(document).ready(function() {
	$('.preloading').fadeOut(500,function(){
		$('.content').fadeIn(500);
	});
});
</script>
	
<div class="toppanel">
   	<button id="btn_new_rsa_key_list" style="height:35px; width: 100px;">
       	<i class="fa fa-plus-circle"></i> New
    </button>
    <button id="btn_refresh_rsa_key_list" style="height: 35px;width: 100px;">
       	<i class="fa fa-refresh"></i> Refresh
    </button>
</div>

<div class="preloading" style="left: 55%;margin-right: -45%;position: absolute;margin: 0px;transfrom:translate(-50%,-50%); ">
    <img src="loading/img/processing.gif" width="" />
</div>

<div class="col-xs-12 content" style="margin-top:0px; display: none;" >	
	<table id="dg-rsa-key-listing" class="table table-bordered table-hover display">
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
	                  	<a href="#"onclick="rsa_key_Edit(<%out.print(r.getPartner_id());%>);">
		            		<i class="fa fa-edit"></i> Edit</a>  
	                </td>
	           	</tr>
			<%}%>
		</tbody>
	</table>
</div>
<script type="text/javascript">
	$('#dg-rsa-key-listing').DataTable();
</script>
<script type="text/javascript">
	$('#btn_new_rsa_key_list').click(function(){	
		tabs.addTab({			
			title: 'New RSA Key',
			id: 'rsa_key',
			ajaxUrl: 'pages/rsa-key.jsp'
		});	
		$("#rsakey").load("pages/rsa-key.jsp");
	});
	$('#btn_refresh_rsa_key_list').click(function(){
		$("#rsakeylist").load("pages/rsa-key-list.jsp");		
	});
	function rsa_key_Edit(GR_ID){	
	 	tabs.addTab({			
			title: 'New RSA Key',
			id: 'rsa-key',
			ajaxUrl: 'pages/rsa-key.jsp?flag=A&id='+GR_ID
		});		
	 	$("#rsakey").load('pages/rsa-key.jsp?flag=A&id='+GR_ID);
	} 
</script>