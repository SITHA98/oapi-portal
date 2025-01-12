<%-- <!-- <link href="assets/css/d/bootstrap.min.css" rel="stylesheet" type="text/css" /> -->	
<%@page import="com.princebank.model.MTTill"%>
<%@page import="com.princebank.service.TillService"%>
<%@page import="com.princebank.service.PrincebankComponent"%>
<%@page import="com.princebank.service.SYS_DATEService"%>
<%@page import="com.princebank.model.Client_N_Loan"%>
<%@page import="com.princebank.service.DashboardService"%>
<%@page import="com.princebank.model.LoanDAB"%>
<%@page import="com.princebank.helper.ClsHelper"%>
<%@page import="com.princebank.model.CASADashboard"%>
<%@page import="java.util.List"%>
<div style="height: 20px;"></div>
	<!-- <div style="height: 20px;"></div> -->
	<div class="row">	
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-4">						
			<div class="card mb-3">
				<div class="card-header" style="font-size: 18px; background-color: #ccf0fb;color: #fa9205;">
					<i class="fa fa-fw fa-bell-o"></i> Customer Deposit
					<a style="float: right;" id="btn_refresh_Unauthorized" class="btn_refresh">
						<i class="fa fa-refresh" id="btn__refresh_loaders_dash_unauthorize"></i>
						<div class="loaders" id="loaders_dash_unauthorize" style="display: none;"></div>
					</a>
				</div>
					
				<div class="card-body" style="height: 650px;">
					<table class="table">
                    <tbody>
                    	<%  DashboardService casaDashboardService=new DashboardService(); 
	                    	SYS_DATEService dateService=new SYS_DATEService();
	                		String date=dateService.SYS_FCSRV_DATE();
                    		CASADashboard casa1=casaDashboardService.initClientNCASA("",date);
                    	%>
                    	<tr>
                    		<td align="center" style="height: 120px;border: none;">
                    			<img src="${pageContext.request.contextPath}/img/customer.png" width="100" />	                    				
                    			<div style="margin-top: -100px;">
                    				<h5><b># OF CUSTOMER</b></h5>
                    				<h4><span id="OF_CUSTOMER"><% out.print(casa1.getNo_of_client()); %></span></h4>
                    			</div>
                    		</td>
                    		<td align="center" style="border: none;">
                    			<img src="${pageContext.request.contextPath}/img/saving-amt.png" width="100" />	                    				
                    			<div style="margin-top: -100px;">
                    				<h5><b># OF CASA</b></h5>
                    				<h4><span id="OF_LOAN"><% out.print(casa1.getNo_of_casa()); %></span></h4>
                    			</div>
                    		</td>
                    	</tr>
                        <tr>                            
                        <table class="table">
	                    <thead>
	                        <tr>
	                        	<th># Account</th>
	                            <th>Account&nbsp;Type</th>
	                            <th>Currency</th>
	                            <th align="right">Balance</th>
	                        </tr>
	                    </thead>
	                    <tbody id="cash_position">
	                   		<% 
	                   			List<CASADashboard>ls=casaDashboardService.initlsCASADAB("",date);
	                   			
	                   			for(int i=0;i<ls.size();i++){
	                   				CASADashboard casa=ls.get(i);
	                   		%>
      				        <tr style="border-bottom: 1px solid #dddddd">          
      				        	<td align="center"><% out.print(  casa.getCounts() ); %></td>     
      				            <td align="left"><% out.print(  casa.getAccountType()); %></td>
      				            <td align="left"><% out.print(  casa.getCcy()); %></td>
      				            <td align="right"><% out.print( ClsHelper.formatNumber( casa.getBalance()) ); %></td>
      				        </tr>
    						<%  } %>
		            			
                    	</tbody>
                	</table>
                        </tr>
                    </tbody>
                </table>
				</div>							
				<!-- <div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div> -->
			</div><!-- end card-->					
		</div>
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-4">						
			<div class="card mb-3">
				<div class="card-header" style="font-size: 18px;background-color: #ccf0fb;color: #fa9205;">
					<i class="fa fa-money"></i> Cash Position 
					<a style="float: right;" id="btn_refresh_Cash_Position" class="btn_refresh">
						<i class="fa fa-refresh" ></i>
					</a>
				</div>
					
				<div class="card-body" style="height: 650px;">
					<table class="table">
					<%
						PrincebankComponent component=new PrincebankComponent();
						int USER_ID=component.USER_ID(request);
		        		String username=component.USER_DISPLAY_NAME(USER_ID);
					%>
	                    <thead>
	                        <tr>
	                            <th># Till Account</th>
	                            <th>Currency</th>
	                            <th>Balance</th>
	                        </tr>
	                    </thead>
	                    <tbody id="cash_position">
	                   		<%
	                   			TillService service=new TillService();
	                   			List<MTTill> tills= service.initTill(USER_ID);
	                   			if(tills.size()>0){
		                   			for(int i=0;i<tills.size();i++){
		                   				MTTill t=tills.get(i);
	                   		%>
      				        <tr style="border-bottom: 1px solid #dddddd">               
      				            <td align="left"><% out.print(t.getTILL_ID()); %></td>
      				            <td align="left"><% out.print(t.getCCY()); %></td>
      				            <td align="right"><% out.print(ClsHelper.formatNumber(t.getBALANCE())); %></td>
      				        </tr>
    						<% 		} 
    							}else{
    								%>
    	      				        <tr style="border-bottom: 1px solid #dddddd">               
    	      				            <td align="left"><% out.print("N/A"); %></td>
    	      				            <td align="left"><% out.print("USD"); %></td>
    	      				            <td align="right"><% out.print(ClsHelper.formatNumber(0.00)); %></td>
    	      				        </tr>
    	      				        <tr style="border-bottom: 1px solid #dddddd">               
    	      				            <td align="left"><% out.print("N/A"); %></td>
    	      				            <td align="left"><% out.print("KHR"); %></td>
    	      				            <td align="right"><% out.print(ClsHelper.formatNumber(0.00)); %></td>
    	      				        </tr>
    	    						<%
    							}
    						%>
    					   
                    	</tbody>
                	</table>
				</div>							
				<!-- <div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div> -->
			</div><!-- end card-->					
		</div>
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-4">						
			<div class="card mb-3">
				<div class="card-header" style="font-size: 18px;color: #fa9205;background-color: #ccf0fb;">
					<i class="fa fa-bank"></i> Branch Productivity 
					<a style="float: right;" id="btn_refresh_Branch_Productivity" class="btn_refresh">
						<i class="fa fa-refresh" ></i>
					</a>
				</div>
					
				<div class="card-body" style="height: 650px;">
					<table class="table">
					<%
						Client_N_Loan cnl=casaDashboardService.initClientNLoan("",date);
					%>
                    <tbody>
                    	<tr>
                    		<td align="center" style="height: 120px;border: none;">
                    			<img src="${pageContext.request.contextPath}/img/customer.png" width="100" />	                    				
                    			<div style="margin-top: -100px;">
                    				<h5><b># OF CUSTOMER</b></h5>
                    				<h4><span id="OF_CUSTOMER"><% out.print(cnl.getNO_CLIENT()); %></span></h4>
                    			</div>
                    		</td>
                    		<td align="center" style="border: none;">
                    			<img src="${pageContext.request.contextPath}/img/loan-disb.png" width="100" />	                    				
                    			<div style="margin-top: -100px;">
                    				<h5><b># OF LOAN</b></h5>
                    				<h4><span id="OF_LOAN"><% out.print(cnl.getNO_LOAN()); %></span></h4>
                    			</div>
                    		</td>
                    	</tr>
                        <tr>                            
                            <table class="table">
	                    <thead>
	                        <tr>
	                            <th align="center"># Account</th>
	                            <th align="center">Currency</th>
	                            <th align="center">Balance</th>
	                            <th align="center">Con. Balance</th>
	                        </tr>
	                    </thead>
	                    <tbody id="cash_position">
	                   		<%
	                   			List<LoanDAB>lsLoan=casaDashboardService.initlsLoan("",date);
	                   		for(int i=0;i<lsLoan.size();i++){
	                   			LoanDAB loan=lsLoan.get(i);
	                   		%>
      				        <tr style="border-bottom: 1px solid #dddddd">               
      				            <td align="center"><% out.print(loan.getCounts()); %></td>
      				            <td align="left"><% out.print(loan.getCurrency()); %></td>
      				            <td align="right"><% out.print( ClsHelper.formatNumber( loan.getBalance()) ); %></td>
      				            <td align="right"><% out.print( ClsHelper.formatNumber( loan.getConBalance()) ); %></td>
      				        </tr>
    					    <%} %>
                    	</tbody>
                	</table>
                        </tr>
                    </tbody>
                </table>
				</div>							
				<!-- <div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div> -->
			</div><!-- end card-->					
		</div>
	</div>
	
<script type="text/javascript">
$('').click(function(){
	$("#customerlist").load("pages/customer-list.jsp");		
});
function onClickRow(id){
	//alert(id);
	tabs.addTab({			
		title: 'Unauthorize Records',
		id: 'unauthorizerecords',
		ajaxUrl: 'pages/unauthorize-records.jsp?id='+id
	});
	$("#unauthorizerecords").load("pages/unauthorize-records.jsp?id="+id);	
}
</script>
<script>
// START CODE FOR BASIC DATA TABLE 
$(document).ready(function() {
	$('#tbl-users-online-dashboard').DataTable();
} );
// END CODE FOR BASIC DATA TABLE
/*Unauthorized*/
function getUnAuthRecord(){
	$.ajax({
		cache : false,
		url : "${pageContext.request.contextPath}/unAuthRecord/getUnAuthRecord",
		type : "POST",
		/* data : {
			
		}, */
		success : function(data) {
			//alert(data);
			//return false;
			if(data.trim()!=null || data.trim()!=""){
				//alert(data);
				$('#unauthorized_record').empty();
        		$('#unauthorized_record').append(data);
			}else{
				$('#unauthorized_record').empty();
        		$('#unauthorized_record').append('No Record');
			}
        	//stopAnimation();
		},error : function(data) {
			alert(data);
		}
	});
} 
/*start animation*/
$('#btn_refresh_Unauthorized').click(function(){
	startAnimation();
	getUnAuthRecord();
	stopAnimation();
});
function startAnimation(){
	document.getElementById("btn__refresh_loaders_dash_unauthorize").style.display = "none";
	document.getElementById("loaders_dash_unauthorize").style.display = "block";
	return false;
}
/*stop animation*/
function stopAnimation(){
	document.getElementById("btn__refresh_loaders_dash_unauthorize").style.display = "block";
	document.getElementById("loaders_dash_unauthorize").style.display = "none";
	return false;
}
/*end Unauthorized*/

$('#btn_refresh_Cash_Position').click(function(){
	//alert('Refrest Cash Position');
	//return false;
	$.ajax({
		cache : false,
		url : "${pageContext.request.contextPath}/cashmgt/Cash_Position",
		type : "POST",
		/* data : {
			
		}, */
		success : function(data) {
			//alert(data);
			//return false;
			$('#cash_position').empty();
        	$('#cash_position').append(data);
        	//stopAnimation();
		},error : function(data) {
			alert(data);
		}
	});
});
$('#btn_refresh_Branch_Productivity').click(function(){
	//alert('Refrest Branch_Productivity');
	//return false;
	$.ajax({
		cache : false,
		url : "${pageContext.request.contextPath}/Dashboard/getBranchProductivity",
		type : "POST",
		/* data : {
			
		}, */
		success : function(data) {
			//alert(data);
			var val = JSON.parse(data);
			$('#OF_CUSTOMER').text(val['OF_CLIENT']);
			$('#OF_LOAN').text(val['OF_LOAN']);
			$('#Loan_Portforio_USD').text(accounting.formatNumber(val['OS_USD'], 2, ","));
			$('#Loan_Portforio_KHR').text(accounting.formatNumber(val['OS_KHR'], 2, ","));
			//return false;
			/* $('#cash_position').empty();
        	$('#cash_position').append(data); */
        	//stopAnimation();
		},error : function(data) {
			alert(data);
		}
	});
});
$('#btn_refresh_Users_Online').click(function(){
	//alert('Refrest Users Online');
	return false;
});

</script>

<style type="text/css">
.loaders {
  border: 3px solid #f3f3f3;
  border-radius: 50%;
  border-top: 3px solid #015494;
  width: 20px;
  height: 20px;
  -webkit-animation: spin 1s linear infinite; /* Safari */
  animation: spin 1s linear infinite;
}

/* Safari */
@-webkit-keyframes spin {
  0% { -webkit-transform: rotate(0deg); }
  100% { -webkit-transform: rotate(360deg); }
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
.onRowClickForDetailUnauthorized:hover{
	cursor: pointer;
}
</style>
    
   --%>