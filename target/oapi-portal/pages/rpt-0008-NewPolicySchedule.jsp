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
            <div class="panel-body" style="background-color: #CCFF;"><h4><b>PRINT HC01 SCHEDULE</b></h4></div>    
        </div>    
    </div>
	<fieldset>
		
		<legend><b>Report filter</b></legend>
		    <table class="form" >
		        <tr>
		            <td align="right" >POLICY NUMBER<span class="danger"> * </span> : </td>
					<td >
						<input type="text" id="HC01_txtpolicynumber" name="HC01_txtpolicynumber" style="width: 600px; height: 50px; font-size:18pt;" />					       	
					</td>
		        </tr>
		        
		        <tr>
		        	<td>
		        		<label>Report Generate : </label>
		        	</td>
		        	<td>
				        <button class="btn btn-primary" id="btnExporttoPDF-HC01" style="width: 600px; height: 50px; font-size:18pt;">
				        	<i class="fa fa-save"></i> PRINT TO PDF
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

$('#btnExporttoPDF-HC01').click(function(){
	var product_id=$('#rpt_0008_product').val();
	//var dateTo=$('#rpt_tellertrans_txtDateTo').val();
	//var dateFrom=$('#rpt_tellertrans_txtDateFrom').val();
	var v_policynumber=$('#HC01_txtpolicynumber').val();
	//var v_txtvattin=$('#HC01_txtvattin').val();
	//window.open("Schedule.htm?policynumber="+v_policynumber+"&product_id="+product_id+"&reportType=1");
	window.open("Schedule.htm?policynumber="+v_policynumber+"&reportType=1");
	window.open("AttachedList.htm?policynumber="+v_policynumber+"&reportType=1");
	window.open("PAHC_CondAndClause.htm?policynumber="+v_policynumber+"&reportType=1");
	window.open("PrintInvoice.htm?policynumber="+v_policynumber+ "&vattin= "+"&reportType=1");
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