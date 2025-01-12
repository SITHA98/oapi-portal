<!--Author      : @arboshiki-->
<%@page import="sitha.rupp.helper.ClsHelper"%>
<%@page import="sitha.rupp.service.UserListModel"%>
<%@page import="sitha.rupp.service.UserDa"%>
<%@page import="java.util.List"%>
<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="sitha.rupp.service.PrincebankComponent"%>
<%
	ApplicationContext context=new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	UserDa uDa=(UserDa)context.getBean("userDa");
	int LOGIN_USER_ID = component.USER_ID(request);
	if (LOGIN_USER_ID == 0){
		out.print("Session timeout. Please login again !");
		return;
	}
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
    	<button id="btn_new_users" style="height: 35px;width: 100px;">
        	<i class="fa fa-plus-circle"></i> New
        </button>
        <button id="user_list_btn_refresh_users_list" style="height: 35px;width: 100px;">
        	<i class="fa fa-refresh"></i> Refresh
        </button>
        	
    </div>
<div class="preloading" style="left: 55%;margin-right: -45%;position: absolute;margin: 0px;transfrom:translate(-50%,-50%); ">
    <img src="loading/img/processing.gif" width="" />
</div>
<div class="col-xs-12 content" style="margin-top:0px; display: none;" >	
	<table id="dg-users-listing" class="table table-bordered table-hover display">
		<thead>
			<tr>
				<th align="center">No.</th>
				<th align="center">Full Name</th>
				<th align="center">Username</th>
				<th align="center">Branch</th>
				<th align="center">Role</th>
				<th align="center">Status</th>
				<th align="center">Action</th>
			</tr>
		</thead>
		<tbody>
			<%
              	String rowData="";
              	List<UserListModel> ulst=uDa.getUserId("");
              	for(int i=0;i<ulst.size();i++){
              		UserListModel u=(UserListModel)ulst.get(i);
              %>
			<tr>
                      <td><% out.print(i+1);%></td>
                      <td><% out.print(u.getUserName());%></td>
                      <td><% out.print(u.getUser_Name());%></td>
                      <td><% out.print(u.getUserBranch());%></td>
                      <td><% out.print(u.getUserRole());%></td>  
                      <td align="center"><% out.print(u.getUserDisable()); %></td>       
                                 
                     <td align="center">
                      	<a href="#"onclick="user_Edit(<% out.print(u.getUserId());%>);">
	             		<i class="fa fa-edit"></i> Edit</a>  
                      	<%-- <%if(u.getUserDisable().equalsIgnoreCase("Y")){ %>
                      	<a onclick="user_disable(<% out.print(u.getUserId());%>)"><i class="fa fa-eye"></i> Disabled</a>
                      	<%}else{ %>
                      	<a onclick="user_enable(<% out.print(u.getUserId());%>)"><i class="fa fa-eye-slash"></i> Enable</a>
                      	<%} %> --%>
                      </td>
                  </tr>
		<%} %>
		</tbody>
	</table>
</div>
<script type="text/javascript">
	$('#dg-users-listing').DataTable();
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
			ajaxUrl: 'pages/user.jsp?flag=A&id='+user_id
		});		
	 	$("#user").load('pages/user.jsp?flag=A&id='+user_id);
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