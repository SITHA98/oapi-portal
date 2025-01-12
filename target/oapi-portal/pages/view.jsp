<!--Author      : @arboshiki-->
<%@page import="sitha.rupp.model.Student"%>
<%@page import="sitha.rupp.service.ImportData"%>
<%@page import="sitha.rupp.helper.ClsHelper"%>
<%@page import="sitha.rupp.service.UserListModel"%>
<%@page import="sitha.rupp.service.UserDa"%>
<%@page import="java.util.List"%>
<%@page
	import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>

<%
	ApplicationContext context=new ClassPathXmlApplicationContext("Spring_Bean.xml");
	ImportData importData=(ImportData)context.getBean("importDataService");
	String user_login = (String)session.getAttribute("PRINCE_GR_ID");
%>

<script>
// START PREPAIR LOADING 
$(document).ready(function() {
	$('.preloading').fadeOut(500,function(){
		$('.content').fadeIn(500);
	});
} );
// END PREPAIR LOADING
</script>
	
 
<div class="preloading" style="left: 55%;margin-right: -45%;position: absolute;margin: 0px;transfrom:translate(-50%,-50%); ">
    <img src="loading/img/processing.gif" width="" />
</div>
<div class="col-xs-12 content" style="margin-top:0px; display: none;" >	
	<table id="dg-users-listing" class="table table-bordered table-hover display">
		<thead>
			<tr>
				<th align="center">No.</th>
				<th align="center">ID</th>
				<th align="center">Name</th>
				<th align="center">Amount</th>
			</tr>
		</thead>
		<tbody>
			<%
              	String rowData="";
              	List<Student> ulst=importData.getStudent(Integer.valueOf(user_login));
              	for(int i=0;i<ulst.size();i++){
              		Student u=(Student)ulst.get(i);
              %>
			<tr>
                      <td><% out.print(i+1);%></td>
                      <td><% out.print(u.getStudentId());%></td>
                      <td><% out.print(u.getStudentName());%></td>
                      <td><% out.print(u.getAmount());%></td>
                       
                  </tr>
		<%} %>
		</tbody>
	</table>
</div>
<script type="text/javascript">
	$('#dg-users-listing').DataTable(
		  //"pageLength": 100
		{
	        "lengthMenu": [[-1, 10, 25, 50], ["All",10, 25, 50]]
	    } 
	);
</script>
