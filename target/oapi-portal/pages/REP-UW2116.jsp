<%@page import="java.util.ArrayList"%>
<%@page import="sitha.rupp.service.PrincebankComponent"%>
<%@page import="sitha.rupp.helper.ClsHelper"%>
<%@page import="sitha.rupp.service.UserListModel"%>
<%@page import="sitha.rupp.service.UserDa"%>
<%@page import="java.util.List"%>
<%@page import="sitha.rupp.service.AccountServices" %>
<%@page import="sitha.rupp.model.Account" %>
<%@page
	import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>

<%
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component=PrincebankComponent.getInstance();
	
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
	AccountServices acService = new AccountServices();
	
	List<Account> acList=new ArrayList<Account>();
	//Account ac =new Account();
	
	acList=acService.getAllAccount();	
%>
<script src="assets/alert/dialogbox.js"></script>

<script src="assets/choosen/chosen.jquery.js" type="text/javascript"></script>
<div class="preloading" style="left: 55%;margin-right: -45%;position: absolute;margin: 0px;transfrom:translate(-50%,-50%); ">
    <img src="loading/img/processing.gif" width="" />
</div>
<div class="col-xs-12 content" style="margin-top:0px; display: none;" >
	 <!-- 
	 <div style="background-color: #ccffff;height: 100px;"> </div>	
 -->
<p></p>
<fieldset id="getFromDB">
<table>
	<%
		for(int i=0;i<acList.size();i++){
			Account ac=acList.get(i);
	%>
	<tr><td>id</td><td><input type="text" value=<%out.print(ac.getId()); %> disabled="disabled" class="bg-warning"></input></td><td></td></tr>
	<tr><td>name</td><td><input type="text" value=<%out.print(ac.getName());%> disabled="disabled" class="bg-warning"></input></td><td></td></tr>

	<%}%>
</table>
</fieldset>
<p></p>
	<fieldset class="form-group">
		<legend>JQuery Validator .js</legend>
		<div class="container">
			<!-- Horizonatal Form -->
			<div class="row">
				<div class="col-xs-6 col-xs-offset-1">
					<!-- <form id="form1" class="form-horizontal"> -->
					<form id="form1" class="form">
						<div>
							<p>https://www.youtube.com/watch?v=TehnRqQtKv8&list=PL5ze0DjYv5DaAm5eC2chbTK1Y6uoTUtZ9&index=2
							</p>
						</div>
						<div class="form-group">
							<label for="nameField" class="col-xs-2">Name</label>
							<div class="col-xs-10">
								<input type="text" class="form-control bg-warning" id="nameField" name="nameField"
									required="required" />
							</div>
						</div>

						<div class="form-group">
							<label for="emailField" class="col-xs-2">Email</label>
							<div class="col-xs-10">
								<input type="email" class="form-control" id="emailField" name="emailField"
									placeholder="Your Email" />
							</div>
						</div>

						<div class="form-group">
							<label for="phoneField" class="col-xs-2">Phone</label>
							<div class="col-xs-10">
								<input type="text" class="form-control" id="phoneField" name="phoneField"
									placeholder=" Your Phone Number" />
							</div>
						</div>

						<div class="form-group">
							<label for="descField" class="col-xs-2">Description </label>
							<div class="col-xs-10">
								<textarea type="text" class="form-control" id="descField" name="phoneField"
									placeholder="Your Comments"></textarea>
							</div>
						</div>
						<div id="TextBoxContainer">
							<!--Textboxes will be added here -->
						</div>

						<button type="button" id="buttonAdd" class="btn btn-primary">+</button>
						<div class="col-xs-10 col-xs-offset-2">
							<button type="button" id="btnSave" name="btnSave" class="btn btn-primary">Save</button> <button type="reset"
								class="btn btn-default">Reset</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</fieldset>
<input id="btnShowValues" type="button" value="Show Values" />
</div>
<fieldset class="form-group">
		<legend>JQuery Validator .js</legend>
		<input type="button" id="btnAddx" value="AddX"/>
	<fieldset class="vi_input">
			<div id="inputbox1">
				<input type="text" id="txtText" name="txtText"/>
			</div>
	</fieldset>
</fieldset>

<script type="text/javascript" src="assets/insured-js/REP-UW2116-M-OK.js"></script>

<script src="assets/alert/dialogbox.js"></script>
<script type="text/javascript">
// START PREPAIR LOADING 
$(document).ready(function() {
	$('.preloading').fadeOut(500,function(){
		$('.content').fadeIn(500);
	});    
});
// END PREPAIR LOADING
</script>

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