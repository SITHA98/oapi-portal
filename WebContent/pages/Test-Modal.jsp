<%@page import="java.util.ArrayList"%>
<%@page import="sitha.rupp.service.PrincebankComponent"%>
<%@page import="sitha.rupp.helper.ClsHelper"%>
<%@page import="sitha.rupp.service.UserListModel"%>
<%@page import="sitha.rupp.service.UserDa"%>
<%@page import="sitha.rupp.model.Test_Modal"%>
<%@page import="sitha.rupp.service.Test_ModalService"%> 
<%@page import="java.util.List"%>
<%@page
	import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>

<%
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component=PrincebankComponent.getInstance();
	Test_Modal testm = new Test_Modal();
	Test_ModalService testmodalService = new Test_ModalService();
	
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
<!--     	<button id="btnNew_TestModal" style="height: 35px;width: 100px;">
        	<i class="fa fa-plus-circle"></i> New
        </button> -->
        <button id="btnRefresh_TestModal" style="width: 120px; height: 40px;"  class="btn btn-primary">
        	<i class="fa fa-refresh"></i> Refresh
        </button>
        <button style="width: 120px; height: 40px;" type="button" class="btn btn-primary" data-toggle="modal" data-target="#NewTestModal" data-whatever="@mdo">
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
				<th align="center">Test-ID</th>
				<th align="center">Name</th>
				<th align="center">Branch Code</th>
				<th align="center">Date</th>
				<th align="center">Action</th>
			</tr>
		</thead>
		<tbody>
			<%
              	String rowData="";
				List<Test_Modal> alist = new ArrayList<Test_Modal>();
				/* alist = testmodalService.getListTestModal(""); */
				alist = testmodalService.getListTestModal(USER_ID);
				/* out.print(alist.toString()); */
				String selectEditId="";
				for(int i=0;i<alist.size();i++){					
            %>
			<tr>
                      <td align="center"><%out.print(alist.get(i).getTest_id()); %></td>
                      <td align="center"><%out.print(alist.get(i).getTest_name()); %></td>
                      <td align="center"><%out.print(alist.get(i).getBranch_code()); %></td>
                      <td align="center"><%out.print(alist.get(i).getValue_date()); %></td>
                      <td align="center">
                      	<a href="#" onclick="Test_Modal_Edit(<%out.print(alist.get(i).getTest_id());%>);" data-toggle="modal" data-target="#EditTestModal" data-whatever="@mdo">
	             		<i class="fa fa-edit"></i>Edit</a>
	             		<a href="#" onclick="Test_Modal_DELETE(<%out.print(alist.get(i).getTest_id());%>);" data-toggle="modal" data-target="#DeleteTestModal" data-whatever="@mdo">
	             		<i class="fa fa-edit"></i>DELETE</a>
                      </td>
            </tr>
		<%}%>
		</tbody>
	</table>
</div>

<!-- Create New -->
<div class="modal fade" id="NewTestModal" tabindex="-1" role="dialog" aria-labelledby="NewTestModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content" align="center" style="width: 1000px; margin-left: -200px; margin-top: 200px;">
      <div class="modal-header">
        <h5 class="modal-title" id="NewTestModalLabel">New message</h5>
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
<div class="modal fade" id="EditTestModal" tabindex="-1" role="dialog" aria-labelledby="EditTestModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content" align="center" style="width: 1000px; margin-left: -200px; margin-top: 200px;">
      <div class="modal-header">
        <h5 class="modal-title" id="EditTestModalLabel">EDIT RECORD</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body" >
        <form class="form-inline">
		   <table>
		   		<tr>
		   			<td style="width:120px;">TEST ID</td>
		   			<td><input type=text class="form-control" id="test_id"  placeholder="" style="width: 820px;"/></td>
		   		</tr>
		   		<tr>
		   			<td style="width:120px;">TEST NAME</td>
		   			<td><input type=text class="form-control" id="test_name"  placeholder="" style="width: 820px;"/></td>
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
					        <input type="text" style="text-align: center; width: 800px;" value="" 
					        id="value_date" name="value_date" class="form-control" placeholder="dd-MM-yyyy" data-date-format="dd-M-yyyy">
					        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
					    </div>
					</td>
				</tr>
		   		
		   </table>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" id="btnEditClose" data-dismiss="modal" style="width: 120px; height: 40px;">Close</button>
        <button type="button" class="btn btn-primary" id="btnEdit" style="width: 120px; height: 40px;">SAVE EDIT</button>
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
<div class="modal fade" id="DeleteTestModal" tabindex="-1" role="dialog" aria-labelledby="DeleteTestModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content" align="center" style="width: 1000px; margin-left: -200px; margin-top: 200px;">
      <div class="modal-header bg-primary">
        <h1 class="modal-title" id="DeleteTestModalLabel">Delete</h1>
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

	$('#btnRefresh_TestModal').click(function(){
		$("#testmodal").load("pages/Test-Modal.jsp");
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
							"lengthMenu" : [ [ 100, 50, 25, 10, -1 ],[ 100, 50, 25, 10, "All" ] ],
							"order": [[ 0, "desc" ]], //Columns are ordered using 0 as first column on left. In a five column table the columns are numbered for sorting purposes like this: 0,1,2,3,4,5,6.
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
		        url : "${pageContext.request.contextPath}/TestModal/findOneTestModal",
		        type : "POST",
		        data : {
		        	TestId:test_id,
		        },
		        success : function(data) {
			        /* sitha */
		        	/* alert(data.test_name); */
		        	$('#EditTestModal #test_id').val(data.test_id);
		        	$('#EditTestModal #test_name').val(data.test_name);
		        	$('#EditTestModal #value_date').val(data.value_date);
		        	$('#EditTestModal #user_selBranch').val(data.branch_code);
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
		        url : "${pageContext.request.contextPath}/TestModal/findOneTestModal",
		        type : "POST",
		        data : {
		        	TestId:test_id,
		        },
		        success : function(data) {
			        /* sitha */
		        	/* alert(data.test_name); */
		        	$('#DeleteTestModal #test_id').val(data.test_id);
		        	$('#DeleteTestModal #test_name').val(data.test_name);
		        	$('#DeleteTestModal #value_date').val(data.value_date);
		        	$('#DeleteTestModal #user_selBranch').val(data.branch_code);
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
		$('#btnRefresh_TestModal').trigger("click");
}

$('#btnSave').click(function(e){
	var msg = $('#test_id').val() +'-'+$('#test_name').val() +'-'+$('#value_date').val();
	/* alert(msg); */
	$.ajax({
		cache: false,
        url : "${pageContext.request.contextPath}/TestModal/insertTestModal",
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
        		/* $('#NewTestModal').hide(); //ok is hide */
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
        url : "${pageContext.request.contextPath}/TestModal/EditTestModal",
        type : "POST",
        data : {
        	TestId:$('#EditTestModal #test_id').val(),
        	TestName:$('#EditTestModal #test_name').val(),
        	ValueDate:$('#EditTestModal #value_date').val(),
        	branch_code:$('#EditTestModal #user_selBranch').val()
        },
        success : function(data) {
        	if(data=="0"){
        		/* $('#NewTestModal').hide(); //ok is hide */
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
        url : "${pageContext.request.contextPath}/TestModal/deleteTestModal",
        type : "POST",
        data : {
        	TestId:$('#DeleteTestModal #test_id').val(),
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

