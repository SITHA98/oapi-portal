<%-- <%@page import="com.princebank.model.MTRecords"%>
<%@page import="com.princebank.service.PrincebankComponent"%>
<%@page import="com.princebank.service.RecordService"%>
<%@page import="com.princebank.helper.ClsHelper"%>
<%@page import="com.princebank.service.BranchDa"%>
<%@page import="java.util.List"%>
<%@page
	import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>

<%
	ApplicationContext context=new ClassPathXmlApplicationContext("Spring_Bean.xml");
	/* RecordService recordService=new RecordService(); */
	PrincebankComponent component=new PrincebankComponent();
%>
		
<div class="toppanel">   
	<!-- <button id="branch-list_btn_new" style="height: 35px;width: 100px;">
    	<i class="fa fa-plus-circle"></i> New
    </button>
    <button id="branch-list_btn_refresh" style="height: 35px;width: 100px;">
    	<i class="fa fa-refresh"></i> Refresh
    </button> -->
    <h4 align="center">Data Backup Monitoring</h4>
</div>
<div class="preloading" style="left: 55%;margin-right: -45%;position: absolute;margin: 0px;transfrom:translate(-50%,-50%); ">
    <img src="loading/img/processing.gif" width="" />
</div>
<div class="col-xs-12 content" style="margin-top:0px; display: none;">
    <table id="dg-branch-list" class="table table-bordered table-hover display">
		<thead>
			<tr>
				<th width="30px">No</th>
				<th width="30px">Records</th>
				<th style="text-align: center;">Backup Date</th>
				<th>Table Name</th>
				<th>Description</th>
				<th>Created by</th>
				<th style="text-align: center;">FC Date</th>
			</tr>
		</thead>										
		<tbody>
			<%
            	List<MTRecords> ls=recordService.initRecord();
            	for(int i=0;i<ls.size();i++){
            		MTRecords c=(MTRecords)ls.get(i);
            %>
			<tr>
                    <td><% out.print(i+1);%></td>
                    <td><% out.print(c.getRecord_num()) ;%></td>
                    <td><% out.print(c.getRecord_date());%></td> 
                    <td><% out.print(c.getTableName());%></td> 
                    <td><% out.print(c.getDescription());%></td> 
                    <td><% out.print(c.getCreated_by());%></td> 
                    <td><% out.print(component.getSYSTEMDATE());%></td> 
                </tr>
			<%} %>
		</tbody>
	</table>
</div>
<script src="assets/alert/dialogbox.js"></script>
<script type="text/javascript">
// START CODE FOR BASIC DATA TABLE 
$(document).ready(function() {
	$('#dg-branch-list').DataTable();
	$('.preloading').fadeOut(500,function(){
		$('.content').fadeIn(500);
	});
} );
// END CODE FOR BASIC DATA TABLE
$('#branch-list_btn_refresh').click(function(){
	$("#branchlist").load("pages/branch-list.jsp");		
});
//btn-new-customer
$('#branch-list_btn_new').click(function(){
		tabs.addTab({			
			title: 'Branch',
			id: 'branch',
			ajaxUrl: 'pages/branch.jsp'
		});
	});
</script>
<script type="text/javascript">
function Edit_branch_list(id){	
	tabs.addTab({			
		title: 'Branch',
		id: 'branch',
		ajaxUrl: 'pages/branch.jsp?id='+id
	});
	$('#branch').load('pages/branch.jsp?id='+id);
}
</script>
<script type="text/javascript">
/*disable branch*/
	function branch_list_close(id){
		$.dialogbox({
			  type:'msg',
			  width:500,
			  title:'Information',
			  content:'Are You Sure?',
			  closeBtn:true,
			  btn:['Confirm','Cancel'],
			  call:[
			    function(){
			    	$.dialogbox.close();
			    	$.ajax({
 						cache: false,
 				        url : "${pageContext.request.contextPath}/branch/closeBranch",
 				        type : "POST",
 				        data:{
 				        	id:id		        	
 					 	},       
 				        success : function(data){
 				        	if(data==1){
 				        		$("#branchlist").load("pages/branch-list.jsp");	
 				        		$.dialogbox({
 				     			  type:'msg',
 				     			  width:400,
 				     			  title:'Information',
 				     			  icon:1,
 				     			  content:'Success',
 				     			  btn:['Ok'],
 				     			  call:[
 				     			    function(){
 				     			      $.dialogbox.close(); 
 				     			    }
 				     			  ]
 				     			});
 				        	}		        	 
 				        },
 				        error : function(request, status, error) {
 				        	$.dialogbox({
				     			  type:'msg',
				     			  width:400,
				     			  title:'Information',
				     			  icon:1,
				     			  content:'Error',
				     			  btn:['Ok'],
				     			  call:[
				     			    function(){
				     			      $.dialogbox.close(); 
				     			    }
				     			  ]
				     			});
 				        }
 					});
			    },
			    function(){
			      $.dialogbox.close(); 
			    }
			  ]
			});
	}
	/*enable branch*/
	function branch_list_open(id){
		$.dialogbox({
			  type:'msg',
			  width:500,
			  title:'Information',
			  content:'Are You Sure?',
			  closeBtn:true,
			  btn:['Confirm','Cancel'],
			  call:[
			    function(){
			    	$.dialogbox.close();
			    	$.ajax({
 						cache: false,
 				        url : "${pageContext.request.contextPath}/branch/openBranch",
 				        type : "POST",
 				        data:{
 				        	id:id		        	
 					 	},     
 				        success : function(data){
 				        	if(data==1){
 				        		$("#branchlist").load("pages/branch-list.jsp");	
 				        		$.dialogbox({
 				     			  type:'msg',
 				     			  width:400,
 				     			  title:'Information',
 				     			  icon:1,
 				     			  content:'Success',
 				     			  btn:['Ok'],
 				     			  call:[
 				     			    function(){
 				     			      $.dialogbox.close(); 
 				     			    }
 				     			  ]
 				     			});
 				        	}		        	 
 				        },
 				        error : function(request, status, error) {
 				        	$.dialogbox({
				     			  type:'msg',
				     			  width:400,
				     			  title:'Information',
				     			  icon:1,
				     			  content:'Error',
				     			  btn:['Ok'],
				     			  call:[
				     			    function(){
				     			      $.dialogbox.close(); 
				     			    }
				     			  ]
				     			});
 				        }
 					});
			    },
			    function(){
			      $.dialogbox.close(); 
			    }
			  ]
			});
		}
</script>
 --%>