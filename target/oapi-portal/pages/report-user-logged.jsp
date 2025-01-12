<!--Author      : @arboshiki-->
<%@page import="sitha.rupp.model.MTLogged"%>
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
$(document).ready(function() {
	$('.preloading').fadeOut(500,function(){
		$('.content').fadeIn(500);
	});
} );
// END PREPAIR LOADING
</script>
	
    <div class="toppanel">
        
        <h4 align="center"> USER LOGGED REPORT</h4>
        	
    </div>
<div class="preloading" style="left: 55%;margin-right: -45%;position: absolute;margin: 0px;transfrom:translate(-50%,-50%); ">
    <img src="loading/img/processing.gif" width="" />
</div>
<div class="col-xs-12 content" style="margin-top:0px; display: none;" >	
	<table id="dg-users-logged" class="table table-bordered table-hover display">
		<thead>
			<tr>
				<th align="center">Branch</th>
				<th align="center">Branch Code</th>
				<th align="center">Display Name</th>
				<th align="center">Role</th>
				<th align="center">LogIn Date Time</th>
				<th align="center">LogOut Date TIme</th>
			</tr>
		</thead>
		<tbody>
			<%
              	List<MTLogged> ls = trackerService.initLsLogged();
              	for(int i=0;i<ls.size();i++){
              		MTLogged u=(MTLogged)ls.get(i);
              %>
			<tr>
                <td><% out.print(u.getBranchId());%></td>
                <td><% out.print(u.getBranchCode());%></td>
                <td><% out.print(u.getDisplayName());%></td>
                <td><% out.print(u.getRole());%></td>
                <td><% out.print(u.getLogin_DT());%></td>  
                <td><% out.print(u.getLogout_DT());%></td>
           </tr>
		<%} %>
		</tbody>
	</table>
</div>
<script type="text/javascript">
	$('#dg-users-logged').DataTable();
</script>
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