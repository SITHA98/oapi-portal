<!--Author      : @arboshiki-->
<%@page import="sitha.rupp.model.MTUser"%>
<%@page import="sitha.rupp.service.PrincebankComponent"%>
<%@page import="sitha.rupp.service.UserDa"%>
<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.apache.commons.beanutils.converters.ClassConverter"%>
<%@page import="org.springframework.context.ApplicationContext"%>

<% 	
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	int USER_ID=component.USER_ID(request);
	String uId = (String) request.getParameter("id");
	String flag = (String) request.getParameter("flag");
	
	UserDa uda=(UserDa)context.getBean("userDa");
	if(uId==null){
		uId="0";
	}
	if(flag==null){
		flag="New";
	}
	MTUser u=uda.getUserInfo(Integer.parseInt(uId)); 
%>

<input type="hidden" id="user_txtUser_Id" name="user_txtUser_Id" value="<% out.print(Integer.parseInt(uId)); %>" />
<!-- <div><%out.print(session.getAttribute("mcb_user_id")); %></div>  -->
	
    <div class="toppanel">
    	<button id="user_btn_save_users" style="height: 35px;width: 100px;">
        	<i class="fa fa-save"></i> Save
        </button>
        <button id="user_btn_refresh" style="height: 35px;width: 100px;">
        	<i class="fa fa-refresh"></i> Refresh
        </button>
    </div> 
<div class="preloading" style="left: 55%;margin-right: -45%;position: absolute;margin: 0px;transfrom:translate(-50%,-50%); ">
    <img src="loading/img/processing.gif" width="" />
</div>
<div class="col-xs-12 content" style="margin-top:0px; display: none;" >	
    <div style="height: 15px;"></div> 
	<table class="form">
	    <tr>
	        <td align="right">Display Name<span class="danger"> * </span> : </td>
	       	<td ><input type="text" id="user_txtName" name="user_txtName" /></td>
	    </tr>
	    <tr>
	    	<td align="right">Role <span class="danger"> * </span> : </td>
	    	<td>
	        	<select id="user_selGroup" name="user_selGroup" style="width: 100%;">
					<option value="0"></option>
					<%
						if(flag.equalsIgnoreCase("staticstr")){
	                		out.print(component.createOptionValue("SELECT g.GR_ID ID,g.GR_NAME DESCR FROM GROUP_ROLE g WHERE g.STATUS=0 and g.GR_ID="+u.getGrId()+" ORDER BY g.GR_NAME", "ID", "DESCR", 0));
						}else{
							out.print(component.createOptionValue("SELECT g.GR_ID ID,g.GR_NAME DESCR FROM GROUP_ROLE g WHERE g.STATUS=0 ORDER BY g.GR_NAME", "ID", "DESCR", 0));
						}
	                %>
				</select>
	        </td> 
	    </tr>
	    <tr>
			<td align="right">Approval Role : </td>
			<td><input type="checkbox" id="user_approval_role" name="user_approval_role" /></td>
	    </tr>
	    <tr>
	    	<td align="right">User name<span style="color: red;"> * </span> : </td>                	
	        <td ><input type="text" id="user_txtUsername" name="user_txtUsername" /></td>
	    </tr>
	     <tr >
	    	<td align="right">New Password<span style="color: red;"> * </span> : </td>
	    	<td><input type="password" id="user_txtPassword" />
				<span id="password_strength"></span>
			</td>
	    </tr>
	    	
	    <tr>
	    	<td align="right">Confirm Password<span style="color: red;"> * </span> : </td>
	        <td ><input type="password" style="width: 100%;" id="user_txtConfirmPassword" name="user_txtConfirmPassword"  /></td>
	    </tr>
	   
	    <tr>
	    	<td align="right">Email<span style="color: red;"> * </span> : </td>
	        <td ><input type="text" style="width: 100%;" id="user_txtEmail" name="user_txtEmail"  /></td>
	    </tr>
	    <tr>
	    	<td align="right">Phone Number<span style="color: red;"> * </span> : </td>                	
	        <td ><input type="text" style="width: 100%;" id="user_phone_number" name="user_phone_number" /></td>
	    </tr>
	    
	    <tr>
	    	<td align="right">TILL ID : </td>
	        <td ><input type="text" style="width: 100%;" id="user_txtTill_ID" name="user_txtTill_ID"  /></td>
	    </tr>
	    <tr>
	    	<td align="right">Branch<span style="color: red;"> * </span> : </td>
			<td >
				<select id="user_selBranch" name="user_selBranch" style="width: 100%;">
					<option value="0"></option>
					<%
					 	out.print(component.createOptionValue("SELECT b.BRANCH_ID ID,to_char(b.BRANCH_ID,'000')||'-'||b.DESCRIPTION DESCR FROM BRANCH b where deleted='N'  ORDER BY b.BRANCH_ID", "ID", "DESCR", 0));
					 %>
				</select>
			</td>
	        <td style="display: none;">
	         	<select id="user_selUserTrn" name="user_selUserTrn" style="width: 100%;">
					<option value="0"></option>
					<%
	                  out.print(component.createOptionValue("SELECT ut.TRN_ID ID, ut.DESCRIPTION DESCR FROM USERS_TRN ut WHERE ut.DELETED='N' ", "ID", "DESCR", 0));
	                %>
				</select>
	        </td>
	    </tr>
	    <tr>
	    	<!-- <td align="right">Can approve<span style="color: red;"> * </span> : </td> -->
	        <td>
	         	<select id="user_selAprove" name="user_selAprove" style="width: 100%;display: none;">
					<option value="1">Yes</option>
					<option value="0">No</option>
				</select>
	        </td>
	    </tr>
	    <tr>
         	<td align="right" valign="top" >Remark<span style="color: red;"> </span> : </td> 
         	<td colspan="4"><textarea rows="4" cols="60" style="width: 100%;" id="branch_txtBrAddr" name="branch_txtBrAddr"></textarea></td>  
        <tr>
        <tr>
        	<td style="height:30px;"></td>
        </tr>
	</table>
	
	
	<div style="border: 1px #f9f9f9 solid;margin: 20px 0px;"></div>
	<div style="margin: 20px 0px;">
		<table>
			<tr>
				<td align="right">Created by : </td>
				<td>
					<input disabled="disabled" name="user_create_by" id="user_create_by"  type="text" value="<% out.print(component.USER_DISPLAY_NAME(USER_ID));%>" />
				</td>
				<td style="width: 10%;"></td>
				<td align="right">Authorized by : </td>
				<td>
					<input disabled="disabled" name="user_Authorized_by" id="user_Authorized_by"  type="text" value="<% out.print(component.USER_DISPLAY_NAME(USER_ID));%>" />
				</td>
			</tr>
			<tr>
				<td align="right">Created date : </td>
				<td>
					<input disabled="disabled" name="user_Created_date" id="user_Created_date"  type="text" />
				</td>
				<td style="width: 10%;"></td>
				<td align="right">Authorized date : </td>
				<td>
					<input disabled="disabled" name="user_Authorized_date" id="user_Authorized_date"  type="text" />
				</td>
			</tr>
		</table>
	</div>
</div>



<!-- <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script> -->
<script type="text/javascript">
    $(function () {
        $("#user_txtPassword").bind("keyup", function () {
            //TextBox left blank.
            if ($(this).val().length == 0) {
                $("#password_strength").html("");
                return;
            }
 
            //Regular Expressions.
            var regex = new Array();
            regex.push("[A-Z]"); //Uppercase Alphabet.
            regex.push("[a-z]"); //Lowercase Alphabet.
            regex.push("[0-9]"); //Digit.
            regex.push("[$@$!%*#?&]"); //Special Character.
 
            var passed = 0;
 
            //Validate for each Regular Expression.
            for (var i = 0; i < regex.length; i++) {
                if (new RegExp(regex[i]).test($(this).val())) {
                	passed++;
                }
            }
 
 
            //Validate for length of Password.
            if (passed > 2 && $(this).val().length > 8) {
                passed++;
            }
 
            //Display status.
            var color = "";
            var strength = "";
            switch (passed) {
                case 0:
                case 1:
                    strength = "Weak";
                    color = "red";
                    break;
                case 2:
                    strength = "Good";
                    color = "darkorange";
                    break;
                case 3:
                case 4:
                    strength = "Strong";
                    color = "green";
                    break;
                case 5:
                    strength = "Very Strong";
                    color = "darkgreen";
                    break;
            }
            $("#password_strength").html(strength);
            $("#password_strength").css("color", color);
        });
    });
</script>
<script src="assets/alert/dialogbox.js"></script>
<script>
// START PREPAIR LOADING 
$(document).ready(function() {
	$('.preloading').fadeOut(500,function(){
		$('.content').fadeIn(500);
	});
} );
$('#user_btn_refresh').click(function(){
	$("#user").load("pages/user.jsp");		
});
// END PREPAIR LOADING
</script>
<script type="text/javascript">
$(function(){
	<%  
    	if(uId!=null && Integer.parseInt(uId)>0){%>
    		$('#user_txtName').val("<%out.print(u.getUserName());%>");
    		$('#user_txtUsername').val("<%if(u.getUser_Name()!=null){out.print(u.getUser_Name());}%>");
    		$('#user_txtEmail').val("<%out.print(u.getEmail());%>"); 
    		$('#user_txtTill_ID').val("<%if(u.getTill_id()==null){out.print("");}else{out.print(u.getTill_id());}%>");
    		$('#branch_txtBrAddr').val("<%if(u.getRemark()==null){out.print("");}else{out.print(u.getRemark());};%>"); 
      		$('#user_phone_number').val("<%if(u.getPhone()==null){out.print("");}else{out.print(u.getPhone());}%>");
    		$('#user_selBranch').val(<%out.print(u.getBrachId());%>);
    		$('#user_selGroup').val(<%out.print(u.getGrId());%>);
    		<%-- $('#user_txtPassword').val("<%out.print(u.getPassWord());%>"); --%>    		
    		$('#user_selUserTrn').val(<%out.print(u.getUserTrn());%>);
    		$('#user_selAprove').val(<%out.print(u.getUseraprove());%>);
    		$('#user_approval_role').prop("checked", <%if(u.getApproval_role()==1){%>true<%}%>)
	<%}%>
});
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
 	$('#user_btn_save_users').click(function(e){
 		if( $('#user_txtPassword').val().trim() != $('#user_txtConfirmPassword').val().trim() ){
 			userdailog('Invalid Confirm Password');
 			$('#user_txtConfirmPassword').focus();
 			return false;
 		}		
 		if(($('#user_txtName').val()).trim()==""){
 			$('#user_txtName').focus();
 			userdailog('Please, enter full name');			
 			return false;
 		}
 		if(($('#user_txtUsername').val())==0){
 			userdailog('Please, enter username');
 			$('#user_txtUsername').focus();
 			return false;
 		}
 		if(($('#user_txtPassword').val()).trim()==""){
 			userdailog('Please, enter password');
 			$('#user_txtPassword').focus();
 			return false;
 		}
 		if($("#password_strength").html()!='Very Strong'){
 			userdailog('Your Password is '+$("#password_strength").html());
 			return false;
 		}
 		if(($('#user_selBranch').val())==0){
 			userdailog('Please, select branch');
 			$('#user_selBranch').focus();
 			return false;
 		}
 		if(($('#user_phone_number').val()).trim()==""){
 			userdailog('Please enter phone number');
 			$('#user_phone_number').focus();
 			return false;
 		}
 		
 		var ap_role = 0;
 		if ($('#user_approval_role').is(":checked")){
 			ap_role = 1;
 		}
 		//alert(ap_role);
 		$.ajax({
			cache: false,
	        url : "${pageContext.request.contextPath}/user/insertUser",
	        type : "POST",
	        data:{
	        	username:$('#user_txtName').val(),
	        	user_name:$('#user_txtUsername').val(),
	        	selGroup:$('#user_selGroup').val(),
	        	password:$('#user_txtPassword').val(),
	        	branch:$('#user_selBranch').val(),
		        /*allowbusinessday:$('#user_selNoneBusinessDay').val(),*/
		        userId:$('#user_txtUser_Id').val(),
		        user_trn:$('#user_selUserTrn').val(),
		        user_selAprove:$('#user_selAprove').val(),
		        email:$('#user_txtEmail').val(),
		        till_id:$('#user_txtTill_ID').val(),
		        remark:$('#branch_txtBrAddr').val(),
		        approval_role:ap_role,
		        phone:$('#user_phone_number').val(),
		        created_by:<%out.print(USER_ID);%>
		 	},
	        //contentType: "application/json; charset=utf-8",
	        //dataType: "json",	       
	        success : function(data){
	        	//if(data=="555"){
        			//userdailog('This password already used');
        			//return false;
        		//}
	        	if(data>0){	        
	        		
	        		if(data>1){		        		
		        		if(data==3){
		        			userdailog('this username existing');
		        		}
		        		else{
		        			userdailog('Sucessfull');
		        			$('#user_txtName').val("");
			        		$('#user_txtPassword').val("");
			        		$('#user_txtUsername').val("");
			        		$('#user_selBranch').val(0);
			        		$('#user_selGroup').val(0);
			        		$('#user_selUserTrn').val(0);
			        		$('#user_selAprove').val(0);
			        		$('#user_txtTill_ID').val("");
			        		$('#branch_txtBrAddr').val("");
			        		$('#user_txtEmail').val("");
			        		$('#user_txtConfirmPassword').val("");
			        		$('#user_phone_number').val("");
			        		$("#password_strength").html("");
		        		}
	        		}
	        		else{
	        			userdailog('Sucessfull');
	        			$('#user_txtName').val("");
		        		$('#user_txtPassword').val("");
		        		$('#user_txtUsername').val("");
		        		$('#user_selBranch').val(0);
		        		$('#user_selGroup').val(0);
		        		$('#user_selUserTrn').val(0);
		        		$('#user_selAprove').val(0);
		        		$('#user_txtTill_ID').val("");
		        		$('#branch_txtBrAddr').val("");
		        		$('#user_txtEmail').val("");
		        		$('#user_txtConfirmPassword').val("");
		        		$('#user_phone_number').val("");
		        		$("#password_strength").html("");
	        		}
	        	}
	        },
	        error : function(request, status, error) {
	        	alert('Error');
	        }
		});		
	});
 </script>