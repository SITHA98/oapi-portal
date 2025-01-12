<%@page import="sitha.rupp.service.SYS_DATEService"%>
<%@page import="sitha.rupp.configuration.Application_Properties"%>
<%@page import="sitha.rupp.service.PrincebankComponent"%>
<%@page import="java.util.List"%>
<%@page
	import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>

<script src="assets/alert/dialogbox.js"></script>
<script type="text/javascript">
// START PREPAIR LOADING 
$(document).ready(function() {
	$('.preloading').fadeOut(500,function(){
		$('.content').fadeIn(500);
	});
} );
// END PREPAIR LOADING
</script>
<div style="height: 50px;"></div>
<div class="preloading" style="left: 55%;margin-right: -45%;position: absolute;margin: 0px;transfrom:translate(-50%,-50%); ">
    <img src="loading/img/processing.gif" width="" />
</div>
<div class="col-xs-12 content" style="margin-top:0px; display: none;" >
<%
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	Application_Properties.SERIAL=1;
	int USER_ID=component.USER_ID(request);
	int BRANCH_ID=component.BRANCH_ID(USER_ID);

	SYS_DATEService dateService=new SYS_DATEService();
	String date=component.getSYSTEMDATE();
	
%>

	<div class="container">    
         
        <div class="panel panel-default">    
            <div class="panel-body" style="background-color: #CCFF;"><h4><b>Daily Teller Total Transactions Report</b></h4></div>    
        </div>    
    </div>
	<fieldset>
		
		<legend><b>Report filter</b></legend>
			
		    <table class="form">
		         <tr>
		            <td align="right" >Branch <span class="danger"> * </span> : </td>
		            <td>
		            	<select id="rpt_tellertrans_selBranch" style="width: 100%;">
		            		<%
		            			if(component.isHQ(USER_ID)){
		            		%>
		            				<option value=" ">All</option>
		            		<%	
		            				out.print(component.createOptionValue("Select BRANCH_ID ID,TO_CHAR(BRANCH_ID,'000')||'-'||CODE DESCR from branch WHERE DELETED='N'   ORDER BY 1", "ID", "DESCR", 0));
		            			}else{
		            				out.print(component.createOptionValue("Select BRANCH_ID ID,TO_CHAR(BRANCH_ID,'000')||'-'||CODE DESCR from branch where DELETED='N' AND branch_id="+BRANCH_ID+"   ORDER BY 1", "ID", "DESCR", 0));
		            			}
		            		%>
						</select>
		            </td>                            
		        </tr>
		       
		        <tr>
		            <td align="right" >From date<span class="danger"> * </span> : </td>
		            <td>
		            	<div class="input-group datepicker-demo date rpt_tellertrans_txtDateFrom" data-date=""
								data-date-format="dd-M-yyyy">
			                   <input style="text-align: center;" 
			                   value="<% out.print(date); %>"
			                   type="text" id="rpt_tellertrans_txtDateFrom" name="rpt_tellertrans_txtDateFrom" class="form-control" placeholder="dd-MMM-yyyy">
			                   <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
			            </div>
		            </td>                            
		        </tr>
		        <tr>
		            <td align="right" >End date<span class="danger"> * </span> : </td>
		            <td>
		            	<div class="input-group datepicker-demo date rpt_tellertrans_txtDateTo" data-date=""
								data-date-format="dd-M-yyyy">
			                   <input style="text-align: center;" 
			                   value="<% out.print(date); %>"
			                   type="text" id="rpt_tellertrans_txtDateTo" name="rpt_tellertrans_txtDateTo" class="form-control" placeholder="dd-MMM-yyyy">
			                   <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
			            </div>
		            </td>                            
		        </tr>
		        <tr>
		        	<td>
		        		<label>Report Generate : </label>
		        	</td>
		        	<td>
				        <button id="btnPreview-DailyTellerTotalTxn" style="height: 35px;width: 100px;">
				        	<i class="fa fa-save"></i> Preview
				        </button>
				        <button id="btnExporttoExcel-DailyTellerTotalTxn" style="height: 35px;width: 100px;">
				        	<i class="fa fa-save"></i> To excel
				        </button>
				        <button id="btnExporttoPDF-DailyTellerTotalTxn" style="height: 35px;width: 100px;">
				        	<i class="fa fa-save"></i> To PDF
				        </button>
		        	</td>
		        </tr>
		        <tr>
		        	
		        </tr>
		    </table>  
    </fieldset>  

<script src="assets/choosen/chosen.jquery.js" type="text/javascript"></script>
<script>
  $(function() {
	$('.chosen-select').chosen();
  });
</script>
<script type="text/javascript">
	var regex = new RegExp(/[^0-9.,]/g);
	var regex2 = new RegExp(/[^0-9,]/g);

$('#btnPreview-DailyTellerTotalTxn').click(function(){
	var branch_id=$('#rpt_tellertrans_selBranch').val();
	var dateTo=$('#rpt_tellertrans_txtDateTo').val();
	var dateFrom=$('#rpt_tellertrans_txtDateFrom').val();
	window.open("DailyTellerTotalTxn.htm?branch_id="+branch_id+"&dateFrom="+dateFrom+"&dateTo="+dateTo+"&reportType=0");
});
$('#btnExporttoExcel-DailyTellerTotalTxn').click(function(){
	var branch_id=$('#rpt_tellertrans_selBranch').val();
	var dateTo=$('#rpt_tellertrans_txtDateTo').val();
	var dateFrom=$('#rpt_tellertrans_txtDateFrom').val();
	window.open("DailyTellerTotalTxn.htm?branch_id="+branch_id+"&dateFrom="+dateFrom+"&dateTo="+dateTo+"&reportType=2");
});
$('#btnExporttoPDF-DailyTellerTotalTxn').click(function(){
	var branch_id=$('#rpt_tellertrans_selBranch').val();
	var dateTo=$('#rpt_tellertrans_txtDateTo').val();
	var dateFrom=$('#rpt_tellertrans_txtDateFrom').val();
	window.open("DailyTellerTotalTxn.htm?branch_id="+branch_id+"&dateFrom="+dateFrom+"&dateTo="+dateTo+"&reportType=1");
});
</script>
<script type="text/javascript">    
	$('.datepicker-demo,.form_datelbl').datetimepicker({
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
<script type="text/javascript">
function Alertx(content){
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
function Success(content){
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
function Error(content){
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