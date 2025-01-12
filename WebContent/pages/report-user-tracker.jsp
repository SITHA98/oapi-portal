<!--Author      : @arboshiki-->
<%@page import="sitha.rupp.model.MTUserTracker"%>
<%@page import="sitha.rupp.service.UserTrackerService"%>
<%@page import="sitha.rupp.helper.ClsHelper"%>
<%@page import="sitha.rupp.service.UserListModel"%>
<%@page import="sitha.rupp.service.UserDa"%>
<%@page import="java.util.List"%>
<%@page
	import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>

<%
	UserTrackerService trackerService=new UserTrackerService();
%>

<script>
// START PREPAIR LOADING 
/* $(document).ready(function() {
	
} ); */
// END PREPAIR LOADING
</script>
	
    <div class="toppanel">
        
        <h4 align="center"> USER TRACKING REPORT</h4>
        	
    </div>
<div class="preloading" style="left: 55%;margin-right: -45%;position: absolute;margin: 0px;transfrom:translate(-50%,-50%); ">
    <img src="loading/img/processing.gif" width="" />
</div>
<div class="col-xs-12 content" style="margin-top:0px; display: none;" >	
	<table id="dg-users-tracking" class="table table-bordered table-hover display">
		<thead>
			<tr>
				<th align="center">No</th>
<!-- 				<th align="center">Branch</th> -->
<!-- 				<th align="center">Branch Code</th> -->
				<th align="center">Display Name</th>
				<th align="center">Role</th>
				<th align="center">Date Time</th>
				<!-- <th align="center">Event Code</th> -->
				<th align="center">Event Description</th>
				<!-- <th align="center">Delete</th> -->
				<th align="center">Report Type</th>
			</tr>
		</thead>
		<tbody>
			<%
              	List<MTUserTracker> ls = trackerService.initLsTracker();
              	for(int i=0;i<ls.size();i++){
              		MTUserTracker u=(MTUserTracker)ls.get(i);
              %>
			<tr>
				<td><% out.print(u.getLID());%></td>
<%--                 <td><% out.print(u.getBRANCH_ID());%></td> --%>
<%--                 <td><% out.print(u.getBRANCH_CODE());%></td> --%>
                <td><% out.print(u.getDISPLAY_NAME());%></td>
                <td><% out.print(u.getROLE());%></td>
                <td align="center"><% out.print(u.getDATE_DONE());%></td>  
                <%-- <td><% out.print(u.getEVENT_CODE());%></td> --%>
                <td><% out.print(u.getEVENT_DESCRIPTION());%></td>
                <%-- <td><% out.print(u.getDELETED());%></td> --%>
                <td><% out.print(u.getRPT_TYPE());%></td>
                     
           </tr>
		<%} %>
		</tbody>
	</table>
</div>
<script type="text/javascript">
	// START CODE FOR BASIC DATA TABLE 
	$(document).ready(
			/* $('.preloading').fadeOut(500,function(){
				$('.content').fadeIn(500);
			}); */
			function() {
				/* var today = new Date();
				var dd = today.getDate();
				var mm = today.getMonth() + 1; //January is 0!
				var yyyy = today.getFullYear();
				if (dd < 10) {
					dd = '0' + dd
				}
				if (mm < 10) {
					mm = '0' + mm
				}
				//today = mm + '/' + dd + '/' + yyyy
				today = yyyy + mm + dd */

				$('#dg-users-tracking').DataTable(
						{
							"lengthMenu" : [ [ 100, 50, 25, 10, -1 ],[ 100, 50, 25, 10, "All" ] ],
							"order": [[0, "desc"]], //desc, asc Columns are ordered using 0 as first column on left. In a five column table the columns are numbered for sorting purposes like this: 0,1,2,3,4,5,6.
									
							//"search": { "search": "Fred" }
							/* "search" : {
								"search" : today
							} */
						});
				$('.preloading').fadeOut(500, function() {
					$('.content').fadeIn(500);
				});
			});
	// END CODE FOR BASIC DATA TABLE
</script>
<!-- <script type="text/javascript">
	$('#dg-users-tracking').DataTable();
</script> -->
<script type="text/javascript">
	$('#btn_new_users').click(function(){	
		tabs.addTab({			
			title: 'User',
			id: 'user',
			ajaxUrl: 'pages/user.jsp'
		});	
		$("#user").load("pages/user.jsp");
	});
	$('#user_list_btn_refresh_users_list').click(function(){
		$("#userlist").load("pages/user-list.jsp");		
	});
	function user_Edit(user_id){	
	 	tabs.addTab({			
			title: 'User',
			id: 'user',
			ajaxUrl: 'pages/user.jsp?id='+user_id
		});		
	 	$("#user").load('pages/user.jsp?id='+user_id);
	}
</script>
<script type="text/javascript">
/*disable user*/
	function user_disable(id){
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
 				        url : "${pageContext.request.contextPath}/user/deleteUser",
 				        type : "POST",
 				        data:{
 				        	id:id		        	
 					 	},
 				        //contentType: "application/json; charset=utf-8",
 				        //dataType: "json",	       
 				        success : function(data){
 				        	if(data==1){
 				        		$("#userlist").load("pages/user-list.jsp");	
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
	/*enable user*/
	function user_enable(id){
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
 				        url : "${pageContext.request.contextPath}/user/enableUser",
 				        type : "POST",
 				        data:{
 				        	id:id		        	
 					 	},
 				        //contentType: "application/json; charset=utf-8",
 				        //dataType: "json",	       
 				        success : function(data){
 				        	if(data==1){
 				        		$("#userlist").load("pages/user-list.jsp");	
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