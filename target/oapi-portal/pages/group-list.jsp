<!--Author      : @arboshiki-->
<%@page import="sitha.rupp.model.MTGroup"%>
<%@page import="sitha.rupp.helper.ClsHelper"%>
<%@page import="sitha.rupp.service.GroupDa"%>
<%@page import="sitha.rupp.service.MenuDa"%>
<%@page import="java.util.List"%>
<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="sitha.rupp.service.PrincebankComponent"%>
<%
	ApplicationContext context=new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	GroupDa grDa=(GroupDa)context.getBean("groupDa");
	MenuDa me_da=(MenuDa)context.getBean("menuDa");
	int LOGIN_USER_ID = component.USER_ID(request);
	if (LOGIN_USER_ID == 0){
		out.print("Session timeout. Please login again !");
		return;
	}
%>
	
    <div class="toppanel">
    	<button id="group-list_btn_new_group" style="height: 35px;width: 100px;">
        	<i class="fa fa-plus-circle"></i> New
        </button>
        <button id="group-list_btn_refresh" style="height: 35px;width: 100px;">
        	<i class="fa fa-refresh"></i> Refresh
        </button>
        <!-- <a class="btntoppanel" id="btn_new_group"><i class="fa fa-plus-circle"></i> New</a> 
        <a class="btntoppanel" id="btn_refresh_group" style="border-left: #6a6b6b 1px solid;padding-left: 5px;"> <i class="fa fa-refresh"></i> Refresh</a> -->
    </div>
    <div class="preloading" style="left: 55%;margin-right: -45%;position: absolute;margin: 0px;transfrom:translate(-50%,-50%); ">
    <img src="loading/img/processing.gif" width="" />
</div>
<div class="col-xs-12 content" style="margin-top:0px; display: none;" >	
    <table id="dg-group-listing" class="table table-bordered table-hover display">
		<thead>
			<tr>
				<th>No.</th>
				<th>Role</th>
				<th>Created By</th>
				<th>Created Date</th>
				<th align="center">Status</th>
				<th align="center">Action</th>
			</tr>
		</thead>										
		<tbody>
			<%  
               	List<MTGroup> grl=grDa.getGroupList();
               	for(int i=0;i<grl.size();i++){
               		MTGroup gr=(MTGroup)grl.get(i);
            %>
            <tr>
            	<td width="20px"><% out.print(i+1);%></td>
            	<td><% out.print(gr.getGr_Name());%></td>
            	<td><% out.print(gr.getCreated_by());%></td>
            	<td><% out.print(gr.getCreated_date());%></td>
            	<td align="center">
            		<% if(gr.getGr_Status()==0){                        	
                        	out.print("Active");
                        }else{
                        	out.print("Close");
                        } 
                        %>
            	</td>
           		<td align="center">
                	<input type="hidden" id="txtGrName" class="txtGrName" value="<%out.print(gr.getGr_Name()); %>" />                	
                	<!-- <a href="#" class="group-list_btn_edit_group"> -->
					<a href="#" onclick="Edit_Group_Role(<%out.print(gr.getGr_Id()); %>);">
                	<input type="hidden" id="txtGrId" class="txtGrId" value="<%out.print(gr.getGr_Id()); %>" />
                	<i class="fa fa-edit"></i> Edit</a> 
                	<%-- <%if(gr.getGr_Status()==0){ %>
                	<a class="btn_grD" href="javascript:void(0)"><i class="fa fa-eye"></i> Disable</a>    
                	<%}else{ %>
                	<a class="btn_grE" href="javascript:void(0)"><i class="fa fa-eye-slash"></i> Enable</a>
                	<%} %>    --%>             	
                </td>
            </tr>
            <% } %>
		</tbody>
	</table>
</div>
<script type="text/javascript">
// START PREPAIR LOADING 
$(document).ready(function() {
	$('.preloading').fadeOut(500,function(){
		$('.content').fadeIn(500);
	});
} );
// END PREPAIR LOADING
</script>
<script type="text/javascript">
	$('#dg-group-listing').DataTable();
</script>
<script type="text/javascript">
	$('#group-list_btn_new_group').click(function(){
		$('#grouprole').load('pages/group-role.jsp');
		tabs.addTab({			
			title: 'Group',
			id: 'grouprole',
			ajaxUrl: 'pages/group-role.jsp'
		});			
	});
	$('#group-list_btn_refresh').click(function(){
		$("#grouplist").load("pages/group-list.jsp");		
	});
</script>
<script type="text/javascript">
function Edit_Group_Role(id){	
	$('#grouprole').load('pages/group-role.jsp?id'+id);
 	tabs.addTab({			
		title: 'Group',
		id: 'grouprole',
		ajaxUrl: 'pages/group-role.jsp?id='+id
	});		
 	/*$("#grouprole").load('pages/group-role.jsp?id='+id);*/
}
	$('.group-list_btn_edit_group').click(function(){
		var id = $(this).parent().parent().find('.txtGrId').val();
		/* alert(id);
		return false; */
		tabs.addTab({			
			title: 'Group',
			id: 'grouprole',
			ajaxUrl: 'pages/group-role.jsp?id='+id
		});
		$("#grouprole").load('pages/group-role.jsp?id='+id);
	});
</script>
<script src="assets/alert/dialogbox.js"></script>
<script type="text/javascript">
	$('.btn_grD').click(function(){
		var name=$(this).parent().parent().find('.txtGrName').val();
		var id=$(this).parent().parent().find('.txtGrId').val();	
		$.dialogbox({
		  type:'msg',
		  width:500,
		  title:'Information',
		  content:'Are You Sure?',
		  closeBtn:true,
		  btn:['Yes','No'],
		  call:[
		    function(){
		    	$.ajax({
					cache: false,
			        url : "${pageContext.request.contextPath}/group/disableGroup",
			        type : "POST",
			        data:{
			        	id:id		        	
				 	},
			        success : function(data){
			        	if(data==1){		
			        		$.dialogbox.close();
			        		$("#grouplist").load("pages/group-list.jsp");
			        	}		        	 
			        },
			        error : function(request, status, error) {
			        	$.dialogbox.close();
			        }
				});
		    },
		    function(){
		      $.dialogbox.close(); 
		    }
		  ]
		});
	});
	$('.btn_grE').click(function(){
		var name=$(this).parent().parent().find('.txtGrName').val();
		var id=$(this).parent().parent().find('.txtGrId').val();
		$.dialogbox({
		  type:'msg',
		  width:500,
		  title:'Information',
		  content:'Are You Sure?',
		  closeBtn:true,
		  btn:['Yes','No'],
		  call:[
		    function(){
		    	$.ajax({
					cache: false,
			        url : "${pageContext.request.contextPath}/group/enableGroup",
			        type : "POST",
			        data:{
			        	id:id		        	
				 	},
			        success : function(data){
			        	$.dialogbox.close();
			        	$("#grouplist").load("pages/group-list.jsp");		        	 
			        },
			        error : function(request, status, error) {
			        	$.dialogbox.close(); 
			        }
				});
		        $.dialogbox.close(); 
		    },
		    function(){
		      $.dialogbox.close(); 
		    }
		  ]
		});
	});
</script>
	