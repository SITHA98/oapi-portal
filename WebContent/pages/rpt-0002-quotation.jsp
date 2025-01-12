<%@page import="sitha.rupp.model.rpt_0002_quotation_model"%>
<%@page import="sitha.rupp.service.SYS_DATEService"%>
<%@page import="sitha.rupp.configuration.Application_Properties"%>
<%@page import="sitha.rupp.service.PrincebankComponent"%>
<%@page import="java.util.List"%>
<%@page import="sitha.rupp.helper.ClsHelper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="sitha.rupp.service.rpt_0002_quotationServices" %>
<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	ApplicationContext context=new ClassPathXmlApplicationContext("Spring_Bean.xml");
	//RPTNewFAService rptNewFAService=(RPTNewFAService)context.getBean("rptNewFAService");
	PrincebankComponent component=PrincebankComponent.getInstance();
	String BRANCH_ID = (String) request.getParameter("BRANCH_ID");
	String USER_ID = component.USER_ID(request)+"";
	
	String fromDate = (String) request.getParameter("FromDate");
	String toDate = (String) request.getParameter("ToDate");
	String ProductCode =(String) request.getParameter("productCode");
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
	out.print(fromDate + "---"+ toDate);
	Application_Properties.SERIAL=2;
	rpt_0002_quotationServices rptQuoservice = new rpt_0002_quotationServices();
	List<rpt_0002_quotation_model> alist = new ArrayList<rpt_0002_quotation_model>();
	alist = rptQuoservice.getQuotationList("", fromDate, toDate);
%>

<script src="assets/alert/dialogbox.js"></script>
<script src="assets/choosen/chosen.jquery.js" type="text/javascript"></script>
<div style="height: 15px;"></div>
<table>
	<tr align="center">
		<td align="left" style="text-align:right; height: 50px; font-size:14pt;" >From date :</td>
		<td align="left" width="250px">
	        <div class="input-group datepicker-demo date rpt_0002_quotation_txtReportDate" data-date=""
				data-date-format="dd-M-yyyy">
		        <input type="text" style="width: 300px; height: 50px; font-size:18pt; text-align: right;" value="<%out.print(fromDate); %>" 
		        id="rpt_002_quotation_txtDateFrom" name="rpt_002_quotation_txtDateFrom" class="form-control" placeholder="dd-MM-yyyy" data-date-format="dd-M-yyyy">
		        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		    </div>
		</td>			
	</tr>
	<tr>
		<td align="left" style="text-align:right; height: 50px; font-size:14pt;">To date :</td>
		<td align="left" width="250px">
	        <div class="input-group datepicker-demo date rpt_0002_quotation_txtReportDate" data-date=""
				data-date-format="dd-M-yyyy">
		        <input type="text" style="width: 300px; height: 50px; font-size:18pt; text-align: right;" value="<%out.print(toDate); %>" 
		        id="rpt_002_quotation_txtDateTo" name="rpt_002_quotation_txtDateTo" class="form-control" placeholder="dd-MM-yyyy" data-date-format="dd-M-yyyy">
		        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		    </div>
		</td>
	</tr>
	<tr>
		<td></td>
		<td align="left" style="padding-left:3px; ">
			 <button class="btn btn-primary" id="rpt_0002_quotation_refresh" style="width: 340px; height: 50px; font-size:18pt;">
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
     <table style="width: 100%;" id="rpt_0002_quotation_datatable" class="table table-bordered table-hover display">                    	
        <thead>
           <tr>
               <th class="text-center">NO</th>
				<th class="text-left">QUOTATION_NO</th>
				<th class="text-left">INSURED_NAME</th>
				<th class="text-right">SUM_INSURED</th>
				<th class="text-right">CROSS_PREMIUM</th>
				<th class="text-center">ISSUE_DATE</th>
				<th class="text-center">INTERMEDIARY</th>
				<th class="text-center">ISSUE_BY</th>
				<th class="text-left">REMARK</th>
           </tr>
       <thead>    
       	   <tbody>
       		<% 
       			for(int i=0;i<alist.size();i++){
       				rpt_0002_quotation_model mQuotation=alist.get(i);
       		%>
       		<tr>
				<td align="center"><%out.print(mQuotation.getNo()); %></td>
                <td align="left"><%out.print(mQuotation.getQuotationNo()); %></td>
                <td align="left"><%out.print(mQuotation.getInsuredName()); %></td>
                <td align="right"><%out.print(ClsHelper.formatNumber(mQuotation.getSumInsured())); %></td>
                <td align="right"><%out.print(ClsHelper.formatNumber(mQuotation.getCrossPremium())); %></td>
                <td align="center"><%out.print(mQuotation.getIssueDate()); %></td>
                <td align="center"><%out.print(mQuotation.getIntermediary()); %></td>
                <td align="center"><%out.print(mQuotation.getIssueBy()); %></td>
                <td align="center"><%out.print(mQuotation.getRemark()==null?"":mQuotation.getRemark()); %></td>
            </tr>
           <%} %>
           </tbody>
           <tfoot>
           </tfoot>
   </table>  
</div>

<input type="hidden" id="txt_rpt_loan_past_due_date" value="<%out.print(""); %>" />

<script type="text/javascript">
	var v_FromDate = $('#rpt_002_quotation_txtDateFrom').val();
	var v_ToDate = $('#rpt_002_quotation_txtDateTo').val();
	var v_delimiter='-';
	var v_title='QuotationReport'+v_delimiter+ v_FromDate +'-TO-'+ v_ToDate;
	$('#rpt_0002_quotation_datatable').DataTable( {
		"lengthMenu" : [ [ 200, 50, 25, 10, -1 ],[ 100, 50, 25, 10, "All" ] ],
		//"order": [[ 0, "asc" ]], //desc, asc Columns are ordered using 0 as first column on left. In a five column table the columns are numbered for sorting purposes like this: 0,1,2,3,4,5,6.
		dom: 'Bfrtip',
		"paging": true,
		"autoWidth": true,
		"buttons": [		
		{ extend: 'copy', title:v_title, footer: true },
		//{ extend: 'csv', title:v_title, footer: true },
		{ extend: 'excel', title:v_title, footer: true },
			{
				text: 'PDF',
				extend: 'pdfHtml5',
				filename: v_title,
				orientation: 'landscape', //portrait
				pageSize: 'legal', //A4, A3 , A5 , A6 , legal , letter
				exportOptions: {
				columns: ':visible',
				search: 'applied',
				order: 'applied'
				},
				
				customize: function (doc) {
					//Remove the title created by datatTables
					doc.content.splice(0,1);
					//Create a date string that we use in the footer. Format is dd-mm-yyyy
					var now = new Date();
					var jsDate = now.getDate()+'-'+(now.getMonth()+1)+'-'+now.getFullYear();
					// Logo converted to base64
					// var logo = getBase64FromImageUrl('https://datatables.net/media/images/logo.png');
					// The above call should work, but not when called from codepen.io
					// So we use a online converter and paste the string in.
					// Done on http://codebeautify.org/image-to-base64-converter
					// It's a LONG string scroll down to see the rest of the code !!!
					/* var logo = 'data:image/jpeg;base64,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ICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA='; */
					// A documentation reference can be found at
					// https://github.com/bpampuch/pdfmake#getting-started
					// Set page margins [left,top,right,bottom] or [horizontal,vertical]
					// or one number for equal spread
					// It's important to create enough space at the top for a header !!!
					doc.pageMargins = [10,10,10,10];
					// Set the font size fot the entire document
					doc.defaultStyle.fontSize = 6;
					// Set the fontsize for the table header
					doc.styles.tableHeader.fontSize = 6;
					// Create a header object with 3 columns
					// Left side: Logo
					// Middle: brandname
					// Right side: A document title
					
					doc['header']=(function() {
						return {
							columns: [
								/* {
									image: logo,
									width: 24
								}, */
								{
									alignment: 'left',
									italics: true,
									text: '',
									fontSize: 5,
									margin: [10,0]
								},
								{
									alignment: 'right',
									fontSize: 5,
									text: '',
									fillColor:"#000"
								}
							],
							tableHeader: {
					            bold:!0,
					            fontSize:6,
					            color:'black',
					            fillColor:'#000',
					            alignment:'center'
					        }
						}
						
					});
				}
			},				
			/* { extend: 'pdf', title:'GL-'+$('#txt_rpt_loan_collection_by_inst_date').val(), footer: true }, */
			//{ extend: 'print', title:v_title, footer: true },
		]
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
	$('#rpt_0002_quotation_datatable').DataTable();
	$('.preloading').fadeOut(500,function(){
		$('.content').fadeIn(500);
	});	
} );

// END CODE FOR BASIC DATA TABLE
/*datetime picker*/
$('.rpt_0002_quotation_txtReportDate').datetimepicker({
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

<!-- script for button preview -->
<script type="text/javascript">	
	$('#rpt_0002_quotation_refresh').click(function(){		
		var productCode = $('#rpt_002_quotation_productCode').val();
		var FromDate = $('#rpt_002_quotation_txtDateFrom').val();
		var ToDate = $('#rpt_002_quotation_txtDateTo').val();
		var msg = "pages/rpt-0002-quotation.jsp?productCode="+productCode+"&FromDate="+FromDate+"&ToDate="+ToDate;
		//alert(msg);
		$("#rpt0002quotation").load(msg);
	});
</script>


