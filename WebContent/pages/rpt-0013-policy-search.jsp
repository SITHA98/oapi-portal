<%@page import="sitha.rupp.model.rpt_0013_policy_search_model"%>
<%@page import="sitha.rupp.service.rpt_0013_policy_searchServices" %>
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
	//rpt_0013_policy_searchServices rptService = (rpt_0013_policy_searchServices)context.getBean("rpt_0013_policy_searchServices");
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
	Application_Properties.SERIAL=1;
	rpt_0013_policy_searchServices rptPolicy = new rpt_0013_policy_searchServices();
	List<rpt_0013_policy_search_model> alist = new ArrayList<rpt_0013_policy_search_model>();
	alist = rptPolicy.getPolicyListALL();
%>

<script src="assets/alert/dialogbox.js"></script>
<script src="assets/choosen/chosen.jquery.js" type="text/javascript"></script>

<div style="height: 15px;"></div>
<table>
	
	<tr>
		<td></td>
		<td align="left" style="padding-left:3px; ">
			 <button class="btn btn-primary" id="rpt_0013_policy_search_refresh" style="width: 340px; height: 50px; font-size:18pt;">
				<i class="fa fa-search"></i> REFRESH
			</button>
		</td>
	</tr>
</table>  
<hr>

<div class="preloading" style="left: 55%;margin-right: -45%;position: absolute;margin: 0px;transfrom:translate(-50%,-50%); ">
    <img src="loading/img/processing.gif" width="" />
</div>

<div class="col-xs-12 content" style="margin-top:0px; display: none;">
     <table style="width: 100%;" id="rpt_0013_policy_search_datatable" class="table table-bordered table-hover display">                    	
        <thead>
           <tr>
               	 <th class="text-center">NO</th>
				<!--
				<th class="text-center">PRODUCT_CODE</th>
				<th class="text-center">POLICY_NUMBER</th>
				<th class="text-center">POLH_EXTNUM</th> -->
				<th class="text-center">DISPLAY_POLICY</th>
				<th class="text-center">INSURED_NAME</th>
				<th class="text-center">POLH_CLIENT</th>
				<th class="text-center">CLIENT_NAME</th>
				<th class="text-center">INTERMEDIARY_CODE</th>
				<th class="text-center">INTERMEDIARY_NAME</th>
				<th class="text-center">INVOICE</th>
				<th class="text-center">SUM_INSURED</th>
<!-- 				<th class="text-center">GROSS_PREMIUM</th> -->
<!-- 				<th class="text-center">NET_PREMIUM</th> -->
<!-- 				<th class="text-center">ADJUST_PREMIUM</th> -->
<!-- 				<th class="text-center">ADMIN_FEE</th> -->
				<th class="text-center">TOTAL</th>
				<th class="text-center">ISSUE_DATE</th>
				<th class="text-center">EFFECTIVE_DATE</th>
				<th class="text-center">EXPIRY_DATE</th>
				<th class="text-center">ISSUE_BY</th>
				<th class="text-center">USER_NAME</th>
				<th class="text-center">POLH_UWYR</th>
           </tr>
       <thead>    
       	   <tbody>
       		<% 
       			for(int i=0;i<alist.size();i++){
       				rpt_0013_policy_search_model mPolicy=alist.get(i);
       		%>
       		<tr>
				 <td align="center"><%out.print(mPolicy.getNO()); %></td>
				
				<%--<td align="center"><%out.print(mPolicy.getPRODUCT_CODE()); %></td>
				<td align="center"><%out.print(mPolicy.getPOLICY_NUMBER()); %></td>
				<td align="center"><%out.print(mPolicy.getPOLH_EXTNUM()); %></td> --%>
				<td align="center"><%out.print(mPolicy.getDISPLAY_POLICY()); %></td>
				<td align="center"><%out.print(mPolicy.getINSURED_NAME()); %></td>
				<td align="center"><%out.print(mPolicy.getPOLH_CLIENT()); %></td>
				<td align="center"><%out.print(mPolicy.getCLIENT_NAME()); %></td>
				<td align="center"><%out.print(mPolicy.getINTERMEDIARY_CODE()); %></td>
				<td align="center"><%out.print(mPolicy.getINTERMEDIARY_NAME()); %></td>
				<td align="center"><%out.print(mPolicy.getINVOICE()); %></td>
				<td align="center"><%out.print(ClsHelper.formatNumber(mPolicy.getSUM_INSURED())); %></td>
				<%-- <td align="center"><%out.print(ClsHelper.formatNumber(mPolicy.getGROSS_PREMIUM())); %></td>
				<td align="center"><%out.print(ClsHelper.formatNumber(mPolicy.getNET_PREMIUM())); %></td>
				<td align="center"><%out.print(ClsHelper.formatNumber(mPolicy.getADJUST_PREMIUM())); %></td>
				<td align="center"><%out.print(ClsHelper.formatNumber(mPolicy.getADMIN_FEE())); %></td> --%>
				<td align="center"><%out.print(ClsHelper.formatNumber(mPolicy.getTOTAL())); %></td>
				<td align="center"><%out.print(mPolicy.getISSUE_DATE()); %></td>
				<td align="center"><%out.print(mPolicy.getEFFECTIVE_DATE()); %></td>
				<td align="center"><%out.print(mPolicy.getEXPIRY_DATE()); %></td>
				<td align="center"><%out.print(mPolicy.getISSUE_BY()); %></td>
				<td align="center"><%out.print(mPolicy.getUSER_NAME()); %></td>
				<td align="center"><%out.print(mPolicy.getPOLH_UWYR()); %></td>
            </tr>
           <%}%>
           </tbody>
           <tfoot>
           </tfoot>
   </table>  
</div>
<input type="hidden" id="txt_rpt_loan_past_due_date" value="<%out.print(""); %>" />

<script type="text/javascript">
	var v_FromDate = $('#rpt_0013_policy_search_txtDateFrom').val();
	var v_ToDate = $('#rpt_0013_policy_search_txtDateTo').val();
	var v_delimiter='-';
	var v_title='Policy-Report'+v_delimiter+ v_FromDate +'-TO-'+ v_ToDate;
	$('#rpt_0013_policy_search_datatable').DataTable( {
		"lengthMenu" : [ [ 200, 50, 25, 10, -1 ],[ 100, 50, 25, 10, "All" ] ],
		"order": [[ 0, "desc" ]], //desc, asc Columns are ordered using 0 as first column on left. In a five column table the columns are numbered for sorting purposes like this: 0,1,2,3,4,5,6.
		
	} );
	
</script> 

<script>
  $(function() {
	$('.chosen-select').chosen();
  });
</script>

<script type="text/javascript">
// START CODE FOR BASIC DATA TABLE 
$(document).ready(function() {
	$('#rpt_0013_policy_search_datatable').DataTable();
	$('.preloading').fadeOut(500,function(){
		$('.content').fadeIn(500);
	});	
} );
$('#rpt_0013_policy_search_refresh').click(function(){
	var msg1 = "pages/rpt-0013-policy-search.jsp"
	//alert(msg1);
	$("#rpt0013policysearch").load(msg1);
});
// END CODE FOR BASIC DATA TABLE
/*datetime picker*/
 $('.rpt_0013_policy_search_txtReportDate').datetimepicker({
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


