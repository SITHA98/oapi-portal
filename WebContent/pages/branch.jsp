<%@page import="sitha.rupp.model.MTBranch"%>
<%@page import="sitha.rupp.service.PrincebankComponent"%>
<%@page import="sitha.rupp.service.BranchDa"%>
<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<% 
	ApplicationContext context=new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	BranchDa br_da=(BranchDa)context.getBean("branchDa");	
	String brId = (String) request.getParameter("id");
	if(brId==null){
		brId="0";
	}	
	MTBranch br=br_da.getBranchInfo(Integer.parseInt(brId));
	//out.print(brId);
%>
<input type="hidden" id="branch_txtbr_Id" name="branch_txtbr_Id" value="<%out.print(Integer.parseInt(brId)); %>" />
		
    <div class="toppanel">
    	<button id="branch_btn_save" style="height: 35px;width: 100px;">
    		<!-- <i class="fa fa-save"></i> --> Save
	    </button>
	    <button id="branch_btn_refresh" style="height: 35px;width: 100px;">
	    	<!-- <i class="fa fa-refresh"></i> --> Refresh
	    </button>
    </div>
<div class="preloading" style="left: 55%;margin-right: -45%;position: absolute;margin: 0px;transfrom:translate(-50%,-50%); ">
    <img src="loading/img/processing.gif" width="" />
</div>
<div style="height: 40px;"></div>
<div class="col-xs-12 content" style="margin-top:0px; display: none;">
       <form action="#" method="post">
       	<table>
           	<tr>
           		<td align="right">Branch Code<span style="color: red;"> *</span> : </td>
                   <td ><input type="text" style="width: 100%;" id="branch_txtBrCode" name="branch_txtBrCode"  /></td>               	
               </tr>
               <tr>
               	<td align="right">Branch Name(KH)<span style="color: red;"> *</span> : </td>
                   <td ><input type="text" style="width: 100%;" id="branch_txtBrNameKH" name="branch_txtBrNameKH" /></td>
                   
               </tr>
               <tr>
               		<td align="right">Branch Name(EN)<span style="color: red;"> *</span> : </td>
                   	<td ><input type="text" style="width: 100%;" id="branch_txtBrNameEN" name="branch_txtBrNameEN" /></td>
               </tr>
               <tr>
               		<td align="right">Description(KH)<span style="color: red;"> *</span> : </td> 
               		<td ><input type="text" style="width: 100%;" id="branch_txtBrDesKH" name="branch_txtBrDesKH" /></td>                   
               </tr>              
               <tr>
               		<td align="right">Description(EN)<span style="color: red;"> *</span> : </td> 
               		<td ><input type="text" style="width: 100%;" id="branch_txtBrDesEN" name="branch_txtBrDesEN" /></td>  
               </tr>
               <tr>
               	<td align="right">Website<span style="color: red;"> *</span> : </td> 
               	<td colspan="4"><input type="text" style="width: 100%;" id="branch_txtBrWebsite" name="branch_txtBrWebsite" /></td>
               </tr>
               <tr>
               	<td align="right">E-mail<span style="color: red;"> *</span> : </td> 
               	<td colspan="4"><input type="text" style="width: 100%;" id="branch_txtBrEmail" name="branch_txtBrEmail" /></td>
               </tr>
               <tr>   
	               	<td align="right">Phone<span style="color: red;"> *</span> : </td> 
	               	<td colspan="4"><input type="text" style="width: 100%;" id="branch_txtBrPhone" name="branch_txtBrPhone" /></td>                                      
               </tr>
               <tr>
	               	<td align="right" valign="top" >Address<span style="color: red;"> *</span> : </td> 
	               	<td colspan="4"><textarea rows="4" cols="60" style="width: 100%;" id="branch_txtBrAddr" name="branch_txtBrAddr"></textarea></td>  
               <tr>
               
               <tr>
               	<td style="height:30px;"></td>
               </tr>
           </table>
       </form>
       <div style="border: 1px #f9f9f9 solid;margin: 20px 0px;"></div>
	<div style="margin: 20px 0px;">
		<table>
			<tr>
				<td align="right">Created by : </td>
				<td>
					<input disabled="disabled" name="user_create_by" id="user_create_by" value="" type="text" />
				</td>
				<td style="width: 10%;"></td>
				<td align="right">Authorized by : </td>
				<td>
					<input disabled="disabled" name="user_Authorized_by" id="user_Authorized_by" value="" type="text" />
				</td>
			</tr>
			<tr>
				<td align="right">Created date : </td>
				<td>
					<input disabled="disabled" name="user_Created_date" id="user_Created_date" value="" type="text" />
				</td>
				<td style="width: 10%;"></td>
				<td align="right">Authorized date : </td>
				<td>
					<input disabled="disabled" name="user_Authorized_date" id="user_Authorized_date" value="" type="text" />
				</td>
			</tr>
		</table>
	</div>
</div>
<script src="assets/alert/dialogbox.js"></script>
<script type="text/javascript">
// START CODE FOR BASIC DATA TABLE 
$(document).ready(function() {
	$('.preloading').fadeOut(500,function(){
		$('.content').fadeIn(500);
	});
} );
// END CODE FOR BASIC DATA TABLE
$('#branch_btn_refresh').click(function(){
	$("#branch").load("pages/branch.jsp");		
});
</script>  
<script type="text/javascript">
$(function(){  
	
	<%    
		
    	if( Integer.parseInt(brId) > 0){ out.print(br.getBranchNamekh());%>
    	$('#branch_txtBrCode').val("<%out.print(br.getBranchCode());%>");
    	$('#branch_txtBrNameKH').val("<%out.print(br.getBranchNamekh());%>");
    	$('#branch_txtBrNameEN').val("<%out.print(br.getBranchNameen());%>");
    	$('#branch_txtBrDesKH').val("<%out.print(br.getBranchDesKH());%>");
    	$('#branch_txtBrDesEN').val("<%out.print(br.getBranchDesen());%>");
    	$('#branch_txtBrAddr').val("<%out.print(br.getBranchAddress());%>");
    	$('#branch_txtBrPhone').val("<%out.print(br.getBranchPhone());%>");
    	$('#branch_txtBrWebsite').val("<%out.print(br.getBranchWebsite());%>");
    	$('#branch_txtBrEmail').val("<%out.print(br.getBranchEmail());%>");
	<%}%>
});

 	$('#branch_btn_save').click(function(e){
 		if(($('#branch_txtBrCode').val()).trim()==""){
 			$('#branch_txtBrCode').focus();
 			branch_Alert('Please enter branch code.');
 			return false;
 		}
 		if(($('#branch_txtBrNameKH').val()).trim()==""){
 			$('#branch_txtBrNameKH').focus();
 			branch_Alert('Please enter branch name (KH).');
 			return false;
 		}
 		if(($('#branch_txtBrNameEN').val()).trim()==""){
 			$('#branch_txtBrNameEN').focus();
 			branch_Alert('Please enter branch name(EN).');
 			return false;
 		} 		
 		if(($('#branch_txtBrDesKH').val()).trim()==""){
 			$('#branch_txtBrDesKH').focus(); 				
 			branch_Alert('Please enter branch description(KH).');
 			return false;
 		}
 		if(($('#branch_txtBrDesEN').val()).trim()==""){
 			$('#branch_txtBrDesEN').focus();
 			branch_Alert('Please enter branch description(EN).');
 			return false;
 		}
 		if(($('#branch_txtBrAddr').val()).trim()==""){
 			$('#branch_txtBrAddr').focus(); 			 	
 			branch_Alert('Please enter address.');
 			return false;
 		}
 		if(($('#branch_txtBrPhone').val()).trim()==""){
 			$('#branch_txtBrPhone').focus(); 			
 			branch_Alert('Please enter phone number.');
 			return false;
 		}
 		if(($('#branch_txtBrWebsite').val()).trim()==""){
 			$('#branch_txtBrWebsite').focus();
 			branch_Alert('Please enter website.');
 			return false;
 		}
 		if(($('#branch_txtBrEmail').val()).trim()==""){ 			
 			branch_Alert('Please enter E-mail address.');
 			return false;
 		} 		
 		//alert('Hello');
 		$.ajax({
			cache: false,
	        url : "${pageContext. request. contextPath}/branch/insertBranch",
	        type : "POST",
	        data:{
	        	txtBrCode:$('#branch_txtBrCode').val(),
	        	txtBrNameKH:$('#branch_txtBrNameKH').val(),
	        	txtBrNameEN:$('#branch_txtBrNameEN').val(),
	        	txtBrDesKH:$('#branch_txtBrDesKH').val(),
	        	txtBrDesEN:$('#branch_txtBrDesEN').val(),
	        	txtBrAddr:$('#branch_txtBrAddr').val(),
	        	txtBrPhone:$('#branch_txtBrPhone').val(),
	        	txtBrWebsite:$('#branch_txtBrWebsite').val(),
	        	txtBrEmail:$('#branch_txtBrEmail').val(),
	        	txtbr_Id:$('#branch_txtbr_Id').val()
	        	
		 	},  
	        success : function(data){	        	
	        	if(data>0){
	        		if(data>1){
		        		if(data==2){
		        			//alert(data);
		        			branch_Success('Branch has been updated sucessfully');
		        			$('#branch_txtBrCode').val("")
				        	$('#branch_txtBrNameKH').val("");
				        	$('#branch_txtBrNameEN').val("");
				        	$('#branch_txtBrDesKH').val("");
				        	$('#branch_txtBrDesEN').val("");
				        	$('#branch_txtBrAddr').val("");
				        	$('#branch_txtBrPhone').val("");
				        	$('#branch_txtBrWebsite').val("");
				        	$('#branch_txtBrEmail').val("");
				        	$('#branch_txtbr_Id').val("");
		        		}	        			
		        		if(data==3){
		        			branch_Error('This branch code is exist. Please, change branch code and try again.');
		        		}
		        		if(data==4){
		        			branch_Error('This branch name is exist. Please, change branch name and try again.');
		        		}
	        		}
	        		else{
	        			branch_Success('Branch has been saved successfully');
	        			$('#branch_txtBrCode').val("")
			        	$('#branch_txtBrNameKH').val("");
			        	$('#branch_txtBrNameEN').val("");
			        	$('#branch_txtBrDesKH').val("");
			        	$('#branch_txtBrDesEN').val("");
			        	$('#branch_txtBrAddr').val("");
			        	$('#branch_txtBrPhone').val("");
			        	$('#branch_txtBrWebsite').val("");
			        	$('#branch_txtBrEmail').val("");
			        	$('#branch_txtbr_Id').val("");
	        		}	        		
	        	}
	        },
	        error : function(request, status, error) {
	        	branch_Error('Error:'+request);
	        }
		});		
	}); 	
 </script>
 <script type="text/javascript">
function branch_Alert(content){
	$.dialogbox({
		  type:'msg',
		  width:450,
		  title:'Information',
		  icon:1,
		  content:content,
		  btn:['OK'],
		  call:[
		    function(){
		      $.dialogbox.close(); 
		    }
		  ]
		});
}
function branch_Success(content){
	$.dialogbox({
		  type:'msg',
		  width:450,
		  title:'Success',
		  icon:1,
		  content:content,
		  btn:['OK'],
		  call:[
		    function(){
		      $.dialogbox.close(); 
		    }
		  ]
		});
}
function branch_Error(content){
	$.dialogbox({
		  type:'msg',
		  width:450,
		  title:'Error',
		  icon:1,
		  content:content,
		  btn:['OK'],
		  call:[
		    function(){
		      $.dialogbox.close(); 
		    }
		  ]
		});
}
</script>
 