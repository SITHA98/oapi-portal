<%@page import="sitha.rupp.service.SYS_DATEService"%>
<%@page import="sitha.rupp.configuration.Application_Properties"%>
<%@page import="sitha.rupp.service.PrincebankComponent"%>
<%@page import="java.util.List"%>
<%@page import="sitha.rupp.helper.ClsHelper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	ApplicationContext context=new ClassPathXmlApplicationContext("Spring_Bean.xml");
	//RPTNewFAService rptNewFAService=(RPTNewFAService)context.getBean("rptNewFAService");
	//rpt_0003_policyServices rptService = (rpt_0003_policyServices)context.getBean("rpt_0003_policyServices");
	PrincebankComponent component=PrincebankComponent.getInstance();
	String BRANCH_ID = (String) request.getParameter("BRANCH_ID");
	String USER_ID = component.USER_ID(request)+"";
	
	String fromDate = (String) request.getParameter("FromDate");
	String toDate = (String) request.getParameter("ToDate");
	String ProductCode=(String)request.getParameter("productCode");
	
	if(fromDate==null){
		fromDate = component.getSYSTEMDATE();
		//fromDate = "01-Jan-2020";
	}
	else{
		fromDate = (String) request.getParameter("FromDate");
	}
	if(toDate==null){
		toDate = component.getSYSTEMDATE();
	}
	else{
		toDate = (String) request.getParameter("ToDate");
	}
	if(ProductCode == null){
		ProductCode = "ALL";
	}
	else{
		ProductCode = "ALL";
	}	
	
	//out.print(fromDate + "---"+ toDate);
	
	
	if(ProductCode == null){
		ProductCode = "ALL";
	}
	else{
		ProductCode = "ALL";
	}
%>

<script src="assets/alert/dialogbox.js"></script>
<script src="assets/choosen/chosen.jquery.js" type="text/javascript"></script>

<div style="height: 15px;"></div>
<table>
	<tr align="center">
		<td align="left" style="text-align:right; height: 50px; font-size:14pt;" >From date :</td>
		<td align="left" width="250px">
	        <div class="input-group datepicker-demo date rpt_0012_borderaux_PA_HC_txtReportDate" data-date=""
				data-date-format="dd-M-yyyy">
		        <input type="text" style="width: 300px; height: 50px; font-size:18pt; text-align: right;" value="<%out.print(fromDate); %>" 
		        id="rpt_0012_borderaux_PA_HC_txtDateFrom" name="rpt_0012_borderaux_PA_HC_txtDateFrom" class="form-control" placeholder="dd-MM-yyyy" data-date-format="dd-M-yyyy">
		        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		    </div>
		</td>			
	</tr>
	<tr>
		<td align="left" style="text-align:right; height: 50px; font-size:14pt;">To date :</td>
		<td align="left" width="250px">
	        <div class="input-group datepicker-demo date rpt_0012_borderaux_PA_HC_txtReportDate" data-date=""
				data-date-format="dd-M-yyyy">
		        <input type="text" style="width: 300px; height: 50px; font-size:18pt; text-align: right;" value="<%out.print(toDate); %>" 
		        id="rpt_0012_borderaux_PA_HC_txtDateTo" name="rpt_0012_borderaux_PA_HC_txtDateTo" class="form-control" placeholder="dd-MM-yyyy" data-date-format="dd-M-yyyy">
		        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		    </div>
		</td>
	</tr>
	<tr>
		<td></td>
		<td align="left" style="padding-left:3px; ">
			 <button class="btn btn-primary" id="rpt_0012_borderaux_PA_HC_view_pdf" style="width: 340px; height: 50px; font-size:18pt;">
				<i class="fa fa-search"></i> VIEW PDF
			</button>
		</td>
		
	</tr>
	<tr>
		<td></td>
		<td align="left" style="padding-left:3px; ">
			 <button class="btn btn-primary" id="rpt_0012_borderaux_PA_HC_export_excel" style="width: 340px; height: 50px; font-size:18pt;">
				<i class="fa fa-search"></i> EXPORT EXCEL
			</button>
		</td>
	</tr>
</table>  
<hr>

<div class="preloading" style="left: 55%;margin-right: -45%;position: absolute;margin: 0px;transfrom:translate(-50%,-50%); ">
    <img src="loading/img/processing.gif" width="" />
</div>

<script>
  $(function() {
	$('.chosen-select').chosen();
  });
</script>

<script type="text/javascript">
// START CODE FOR BASIC DATA TABLE 
$(document).ready(function() {
	$('#rpt_0012_borderaux_PA_HC_datatable').DataTable();
	$('.preloading').fadeOut(500,function(){
		$('.content').fadeIn(500);
	});	
} );
$('#rpt_0012_borderaux_PA_HC_view_pdf').click(function(){
	//alert('hello');
	var productCode = $('#rpt_0012_borderaux_PA_HC_productCode').val();
	var vFromDate = $('#rpt_0012_borderaux_PA_HC_txtDateFrom').val();
	var vToDate = $('#rpt_0012_borderaux_PA_HC_txtDateTo').val();	
	window.open("rpt0012Borderaux_PAHC.htm?fromdate="+vFromDate+"&todate="+vToDate+ "&reportType=1");
});

$('#rpt_0012_borderaux_PA_HC_export_excel').click(function(){
	//alert('hello');
	var productCode = $('#rpt_0012_borderaux_PA_HC_productCode').val();
	var vFromDate = $('#rpt_0012_borderaux_PA_HC_txtDateFrom').val();
	var vToDate = $('#rpt_0012_borderaux_PA_HC_txtDateTo').val();	
	window.open("rpt0012Borderaux_PAHC.htm?fromdate="+vFromDate+"&todate="+vToDate+ "&reportType=2");
});
// END CODE FOR BASIC DATA TABLE
/*datetime picker*/
 $('.rpt_0012_borderaux_PA_HC_txtReportDate').datetimepicker({
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


