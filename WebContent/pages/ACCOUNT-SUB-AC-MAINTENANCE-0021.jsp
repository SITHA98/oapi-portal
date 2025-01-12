<%@page import="java.util.ArrayList"%>
<%@page import="sitha.rupp.service.PrincebankComponent"%>
<%@page import="sitha.rupp.helper.ClsHelper"%>
<%@page import="sitha.rupp.service.UserListModel"%>
<%@page import="sitha.rupp.service.UserDa"%>
<%@page import="sitha.rupp.model.Account_sub_ac_maintenance"%>
<%@page import="sitha.rupp.service.Account_sub_ac_maintenanceService"%> 
<%@page import="java.util.List"%>
<%@page
	import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>

<%
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component=PrincebankComponent.getInstance();
	Account_sub_ac_maintenance acModel = new Account_sub_ac_maintenance();
	Account_sub_ac_maintenanceService acService = new Account_sub_ac_maintenanceService();
	
	int USER_ID=component.USER_ID(request);
	int BRANCH_ID=component.BRANCH_ID(USER_ID);
	//-- If Allow to see all records for all branches --//
	/* if(component.isFullAccess(USER_ID)){
		BRANCH_ID=-1;
	} */
	//-- If Allow to see all records in branch --//
	/* if(component.isPartitalAccess(USER_ID)){
		USER_ID=0;
	} */
%>
<script src="assets/alert/dialogbox.js"></script>
<script src="assets/choosen/chosen.jquery.js" type="text/javascript"></script>

    <div class="toppanel" style="width: 100%;" align="center">
        <button id="btnRefresh_Account_sub_ac_maintenance" style="width: 120px; height: 40px;"  class="btn btn-primary">
        	<i class="fa fa-refresh"></i> Refresh
        </button>
        <button style="width: 120px; height: 40px;" type="button" class="btn btn-primary" data-toggle="modal" data-target="#NewAccount_sub_ac_maintenance" data-whatever="@mdo">
        <i class="fa fa-plus-circle"></i> NEW</button>
    </div>
<div class="preloading" style="left: 55%;margin-right: -45%;position: absolute;margin: 0px;transfrom:translate(-50%,-50%); ">
    <img src="loading/img/processing.gif" width="" />
</div>
<div class="col-xs-12 content" style="margin-top:0px; display: none;" >
	<!-- <div style="background-color: #ccffff;height: 100px;">
	
	</div> -->	
	<table id="Test-Modal-List" class="table table-bordered table-hover display">
		<thead>
			<tr>
				<th align="center">BATCH_NUMBER</th>
				<th align="center">BATCH_DESC</th>
				<th align="center">ACCOUNT_CODE</th>
				<th align="center">ACCOUNT_DESC</th>
				<th align="center">ACCOUNT_TYPE</th>
				<th align="center">ACCOUNT_TYPE_DESC</th>
				<th align="center">SUB_TYPE_CODE</th>
				<th align="center">SUB_TYPE_DESC</th>
				<th align="center">CURRENCY</th>
				<th align="center">STATUS</th>
				<th align="center">CREATED_BY</th>
				<th align="center">CREATED_DATE</th>
				<th align="center">AUTHORIZED_BY</th>
				<th align="center">AUTHORIZED_DATE</th>
			</tr>
		</thead>
		<tbody>
			<%
              	String rowData="";
				List<Account_sub_ac_maintenance> alist = new ArrayList<Account_sub_ac_maintenance>();
				/* alist = Account_sub_ac_maintenanceService.getListAccount_sub_ac_maintenance(""); */
				alist = acService.getAllAccountMapping();
				/* out.print(alist.toString()); */
				String selectEditId="";
				for(int i=0;i<alist.size();i++){					
            %>
			<tr>
                      <td align="center"><%out.print(alist.get(i).getBatch_number()); %></td>
						<td align="center"><%out.print(alist.get(i).getBatch_desc()); %></td>
						<td align="center" >
							<a href="#" onclick="Test_Modal_Edit(<%out.print(alist.get(i).getAccount_code()); %>);" data-toggle="modal" data-target="#EditAccount_sub_ac_maintenance" data-whatever="@mdo">
								<b style="color:blue; text-decoration: underline;">
									<%out.print(alist.get(i).getAccount_code()); %>
								</b>
							</a>
						</td>
						<td align="center"><%out.print(alist.get(i).getAccount_Desc()); %></td>
						<td align="center"><%out.print(alist.get(i).getAccount_type()); %></td>
						<td align="center"><%out.print(alist.get(i).getAccount_type_desc()); %></td>
						<td align="center"><%out.print(alist.get(i).getSUB_TYPE_CODE()); %></td>
						<td align="center"><%out.print(alist.get(i).getSUB_TYPE_DESC()); %></td>
						<td align="center"><%out.print(alist.get(i).getCURRENCY()); %></td>
						<td align="center"><%out.print(alist.get(i).getSTATUS()); %></td>
						<td align="center"><%out.print(alist.get(i).getCREATED_BY()); %></td>
						<td align="center"><%out.print(alist.get(i).getCREATED_DATE()); %></td>
						<td align="center"><%out.print(alist.get(i).getAUTHORIZED_BY()); %></td>
						<td align="center"><%out.print(alist.get(i).getAUTHORIZED_DATE()); %></td>
						
						
						
                      <%-- <td align="center">
                      	<a href="#" onclick="Test_Modal_Edit(<%out.print(alist.get(i).getTest_id());%>);" data-toggle="modal" data-target="#EditAccount_sub_ac_maintenance" data-whatever="@mdo">
	             		<i class="fa fa-edit"></i>Edit</a>
	             		<a href="#" onclick="Test_Modal_DELETE(<%out.print(alist.get(i).getTest_id());%>);" data-toggle="modal" data-target="#DeleteAccount_sub_ac_maintenance" data-whatever="@mdo">
	             		<i class="fa fa-edit"></i>DELETE</a>
                      </td> --%>
            </tr>
		<%}%>
		</tbody>
	</table>
</div>

<!-- Create New -->
<div class="modal fade" id="NewAccount_sub_ac_maintenance" tabindex="-1" role="dialog" aria-labelledby="NewAccount_sub_ac_maintenanceLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content" align="center" style="width: 1000px; margin-left: -200px; margin-top: 200px;">
      <div class="modal-header">
        <h5 class="modal-title" id="NewAccount_sub_ac_maintenanceLabel">New message</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body" >
        <form class="form-inline">
		   <table >
		   		<tr>
		   			<td style="width:120px;">TEST ID</td>
		   			<td><input type=text class="form-control" id="test_id" required placeholder="" style="width: 820px;" ></td>
		   		</tr>
		   		<tr>
		   			<td style="width:120px;">TEST NAME</td>
		   			<td><input type=text class="form-control" id="test_name" required placeholder="" style="width: 820px;" ></td>
		   		</tr>
		   		<tr>
			    	<td align="right">Branch <span class="danger"> * </span> : </td>
			    	<td>
			        	<select id="user_selBranch" name="user_selBranch" style="width: 100%;">
							<option value="0"></option>
							<%
			                	out.print(component.createOptionValue("SELECT BRANCH_ID ID,CON_KEY DESCR FROM APIREPORT.STTM_BRANCH WHERE DELETED='N'", "ID", "DESCR", 0));
			                %>
						</select>
			        </td> 
			    </tr>
		   		<tr>
		   			<td style="width:120px;">VALUE DATE</td>
			   		<td align="left" width="250px">
				        <div class="input-group date value_date_timepicker" data-date="" data-date-format="dd-M-yyyy h:i:ss">
					        <input type="text" style="text-align: center; width: 800px;" value="" required
					        id="value_date" name="value_date" class="form-control" placeholder="dd-MM-yyyy" data-date-format="dd-M-yyyy">
					        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
					    </div>
					</td>
				</tr>
		   </table>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" id="btnClose" data-dismiss="modal" style="width: 120px; height: 40px;">Close</button>
        <button type="button" class="btn btn-primary" id="btnSave" style="width: 120px; height: 40px;">Save</button>
         
        <input type="hidden" id="userid" value="<% out.print(USER_ID); %>"/>
      </div>
    </div>
  </div>
</div>
<!-- END Create New -->

<!-- 
*************************
EDIT RECORD
*************************
-->
<div class="modal fade" id="EditAccount_sub_ac_maintenance" tabindex="-1" role="dialog" aria-labelledby="EditAccount_sub_ac_maintenanceLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content" align="center" style="width: 1000px; margin-left: -200px; margin-top: 200px;">
      <div class="modal-header">
        <h5 class="modal-title" id="EditAccount_sub_ac_maintenanceLabel">EDIT RECORD</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body" >
        <form class="form-inline">
		   <table>
		   		<tr>
		   			<td style="width:120px;">Batch Number</td>
		   			<td><input type=text class="form-control" id="batch_number"  placeholder="" style="width: 820px;"/></td>
		   		</tr>
		   		<tr>
		   			<td style="width:120px;">Batch Description</td>
		   			<td><input type=text class="form-control" id="batch_desc"  placeholder="" style="width: 820px;"/></td>
		   		</tr>
		   		<%-- <tr>
			    	<td align="right">Branch <span class="danger"> * </span> : </td>
			    	<td>
			        	<select id="user_selBranch" name="user_selBranch" style="width: 100%;">
							<option value="0"></option>
							<%
			                	out.print(component.createOptionValue("SELECT BRANCH_ID ID,CON_KEY DESCR FROM APIREPORT.STTM_BRANCH WHERE DELETED='N'", "ID", "DESCR", 0));
			                %>
						</select>
			        </td> 
			    </tr>
		   		<tr>
		   			<td style="width:120px;">VALUE DATE</td>
			   		<td align="left" width="250px">
				        <div class="input-group date value_date_timepicker" data-date="" data-date-format="dd-M-yyyy h:i:ss">
					        <input type="text" style="text-align: center; width: 800px;" value="" 
					        id="value_date" name="value_date" class="form-control" placeholder="dd-MM-yyyy" data-date-format="dd-M-yyyy">
					        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
					    </div>
					</td>
				</tr> --%>
		   		
		   </table>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" id="btnEditClose" data-dismiss="modal" style="width: 120px; height: 40px;">Close</button>
        <button type="button" class="btn btn-primary" id="btnEdit" style="width: 120px; height: 40px;">SAVE</button>
      </div>
    </div>
  </div>
</div>
<!-- 
*************************
END EDIT RECORD
*************************
-->

<!-- DELETE MODAL -->
<div class="modal fade" id="DeleteAccount_sub_ac_maintenance" tabindex="-1" role="dialog" aria-labelledby="DeleteAccount_sub_ac_maintenanceLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content" align="center" style="width: 1000px; margin-left: -200px; margin-top: 200px;">
      <div class="modal-header bg-primary">
        <h1 class="modal-title" id="DeleteAccount_sub_ac_maintenanceLabel">Delete</h1>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        	<span aria-hidden="true" class="text-light">&times;</span>
          	<span class="text-light">X</span>
          	<span aria-hidden="true" class="text-light">&times;</span>
        </button>
      </div>
      <div class="modal-body" >
        <form class="form-inline">
		    <table>
		   		<tr>
		   			<td style="width:120px;">TEST ID</td>
		   			<td><input type=text class="form-control" id="test_id"  placeholder="" style="width: 820px;" disabled="disabled"/></td>
		   		</tr>
		   		<tr>
		   			<td style="width:120px;">TEST NAME</td>
		   			<td><input type=text class="form-control" id="test_name"  placeholder="" style="width: 820px;" disabled="disabled"/></td>
		   		</tr>
		   		<tr>
			    	<td align="right">Branch <span class="danger"> * </span> : </td>
			    	<td>
			        	<select id="user_selBranch" name="user_selBranch" style="width: 100%;" disabled="disabled">
							<option value="0"></option>
							<%
			                	out.print(component.createOptionValue("SELECT BRANCH_ID ID,CON_KEY DESCR FROM APIREPORT.STTM_BRANCH WHERE DELETED='N'", "ID", "DESCR", 0));
			                %>
						</select>
			        </td> 
			    </tr>
		   		<tr>
		   			<td style="width:120px;">VALUE DATE</td>
			   		<td align="left" width="250px">
				        <div class="input-group date value_date_timepicker" data-date="" data-date-format="dd-M-yyyy h:i:ss">
					        <input type="text" style="text-align: center; width: 800px;" value="" 
					        id="value_date" name="value_date" class="form-control" placeholder="dd-MM-yyyy" data-date-format="dd-M-yyyy" disabled="disabled">
					        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
					    </div>
					</td>
				</tr>
		   </table>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" id="btnDeleteClose" data-dismiss="modal" style="width: 120px; height: 40px;">Close</button>
        <button type="button" class="btn btn-danger" id="btnDelete" style="width: 120px; height: 40px;">DELETE(Y)</button>
      </div>
    </div>
  </div>
</div>
<!-- END DELETE MODAL -->

<script src="assets/alert/dialogbox.js"></script>
<script type="text/javascript">
// START PREPAIR LOADING 
$(document).ready(function() {
	$('.preloading').fadeOut(500,function(){
		$('.content').fadeIn(500);
	});

	$('#btnRefresh_Account_sub_ac_maintenance').click(function(){
		//alert("btnRefresh_Account_sub_ac_maintenance");
		$("#accountsubacmaintenance0021").load("pages/ACCOUNT-SUB-AC-MAINTENANCE-0021.jsp"); //$(#jspfilename).load....#jsp file name small cap
	});
});
// END PREPAIR LOADING
</script>
<script type="text/javascript">
	// START CODE FOR BASIC DATA TABLE 
	$(document).ready(
			function() {
				$('#Test-Modal-List').DataTable(
						{
							//dom: 'lBfrtip',
							dom: 'Bfrtip',
							"lengthMenu" : [ 
								[ 100, 50, 25, 10, -1 ],
								[ '100 show', '50 show', '25 show', '10 show', "Show All" ],
							],
							"order": [[ 0, "desc" ]], //Columns are ordered using 0 as first column on left. In a five column table the columns are numbered for sorting purposes like this: 0,1,2,3,4,5,6.
							//dom: 'Bfrtip',
							 // Add to buttons the pageLength option.
					        buttons: [
					        	'pageLength', 'excel','print'
					        ]
						});
				$('.preloading').fadeOut(500, function() {
					$('.content').fadeIn(500);
				});
			});
	// END CODE FOR BASIC DATA TABLE
</script>

<!-- OPEN EDIT MODAL DIALOG -->
<script type="text/javascript">
		function Test_Modal_Edit(test_id) {
			$.ajax({
				cache: false,
		        url : "${pageContext.request.contextPath}/Account_sub_ac_maintenance/viewByAccountCode",
		        type : "POST",
		        data : {
		        	TestId:test_id,
		        },
		        success : function(data) {
			        /* sitha */
		        	 //alert(data.batch_number); 
		        	$('#EditAccount_sub_ac_maintenance #batch_number').val(data.batch_number);
		        	$('#EditAccount_sub_ac_maintenance #batch_desc').val(data.batch_desc);
		        	//$('#EditAccount_sub_ac_maintenance #value_date').val(data.ACCOUNT_CODE);
		        	//$('#EditAccount_sub_ac_maintenance #user_selBranch').val(data.ACCOUNT_DESC);
		        },
		        error : function(data){
		        	Error("System Error Accued."+data);
		        }
			});
			
    	}
</script>
<!-- END OPEN EDIT MODAL DIALOG -->

<!-- OPEN DELETE MODAL DIALOG -->
<script type="text/javascript">
		function Test_Modal_DELETE(test_id) {
			$.ajax({
				cache: false,
		        url : "${pageContext.request.contextPath}/Account_sub_ac_maintenance/findOneAccount_sub_ac_maintenance",
		        type : "POST",
		        data : {
		        	TestId:test_id,
		        },
		        success : function(data) {
			        /* sitha */
		        	/* alert(data.test_name); */
		        	$('#DeleteAccount_sub_ac_maintenance #test_id').val(data.test_id);
		        	$('#DeleteAccount_sub_ac_maintenance #test_name').val(data.test_name);
		        	$('#DeleteAccount_sub_ac_maintenance #value_date').val(data.value_date);
		        	$('#DeleteAccount_sub_ac_maintenance #user_selBranch').val(data.branch_code);
		        },
		        error : function(data){
		        	Error("System Error Accued."+data);
		        }
			});
			
    	}
</script>
<!-- END OPEN DELETE MODAL DIALOG -->


<script type="text/javascript">
function userdailog(a){
	$.dialogbox({
		  type:'msg',
		  width:400,
		  title:'Information',
		  icon:1,
		  content:a,
		  btn:['Ok'],
		  call:[
		    function(){
		    	$.dialogbox.close(); 
		    }
		  ]
	});
}

function clearControl(){
		$('#test_id').val("");
		$('#test_name').val("");
		$('#value_date').val("");
		$('#btnRefresh_Account_sub_ac_maintenance').trigger("click");
}

$('#btnSave').click(function(e){
	var msg = $('#test_id').val() +'-'+$('#test_name').val() +'-'+$('#value_date').val();
	/* alert(msg); */
	$.ajax({
		cache: false,
        url : "${pageContext.request.contextPath}/Account_sub_ac_maintenance/insertAccount_sub_ac_maintenance",
        type : "POST",
        data : {
        	TestId:$('#test_id').val(),
        	TestName:$('#test_name').val(),
        	ValueDate:$('#value_date').val(),
        	branch_code:$('#user_selBranch').val(),
        	userid:$('#userid').val()
        },
        success : function(data) {
        	if(data=="0"){
        		/* $('#NewAccount_sub_ac_maintenance').hide(); //ok is hide */
        		$("#btnClose").trigger("click");
        		userdailog('Sucessfull');
        		clearControl();
	  			
    		}else{
    			/* userdailog('This password already used' + data); */
    			alert("hi failed.");
        	}
        },
        error : function(data){
        	Error("System Error Accued."+data);
        }
	});
});

$('#btnEdit').click(function(e){
	var msg = $('#test_id').val() +'-'+$('#test_name').val() +'-'+$('#value_date').val();
	$.ajax({
		cache: false,
        url : "${pageContext.request.contextPath}/Account_sub_ac_maintenance/EditAccount_sub_ac_maintenance",
        type : "POST",
        data : {
        	TestId:$('#EditAccount_sub_ac_maintenance #test_id').val(),
        	TestName:$('#EditAccount_sub_ac_maintenance #test_name').val(),
        	ValueDate:$('#EditAccount_sub_ac_maintenance #value_date').val(),
        	branch_code:$('#EditAccount_sub_ac_maintenance #user_selBranch').val()
        },
        success : function(data) {
        	if(data=="0"){
        		/* $('#NewAccount_sub_ac_maintenance').hide(); //ok is hide */
        		$("#btnEditClose").trigger("click");
        		/* userdailog('Sucessfull'); */
        		clearControl();
	  			
    		}else{
    			$("#btnEditClose").trigger("click");
    			userdailog('Something wrong.' + data);
    			clearControl();
    			/* alert("hi failed."); */
        	}
        },
        error : function(data){
        	Error("System Error Accued."+data);
        }
	});
});

/* delete */
$('#btnDelete').click(function(e){
	/* alert("delete"); */
	$.ajax({
		cache: false,
        url : "${pageContext.request.contextPath}/Account_sub_ac_maintenance/deleteAccount_sub_ac_maintenance",
        type : "POST",
        data : {
        	TestId:$('#DeleteAccount_sub_ac_maintenance #test_id').val(),
        },
        success : function(data) {
        	if(data=="0"){
        		
        		$("#btnDeleteClose").trigger("click");
        		userdailog('Sucessfull');
        		clearControl();
	  			
    		}else{
    			$("#btnDeleteClose").trigger("click");
    			userdailog('Something wrong.' + data);
    			clearControl();
    			/* alert("hi failed."); */
        	}
        },
        error : function(data){
        	Error("System Error Accued."+data);
        }
	});
});
</script>
<script>
/*datetime picker*/
$('.value_date_timepicker').datetimepicker({
       language:  'en',
       weekStart: 1,
       todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
   }); 
</script>

