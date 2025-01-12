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
<div class="col-xs-12 content" style="margin-top:0px; display: none;" ></div>
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
            <div class="panel-body" style="background-color: #CCFF;"><h4><b>PRINT ENDORSMENT</b></h4></div>    
        </div>    
    </div>
	<fieldset>
		
		<legend><b>Report filter</b></legend>
			
		    <table class="form" >
		         
		        <tr>
		            <td align="right" >POLICY NUMBER<span class="danger"> * </span> : </td>
					<td >
						<input type="text" id="PA00_0014_ENDOR_txtpolicynumber" name="PA00_0014_ENDOR_txtpolicynumber" style="width: 600px; height: 50px; font-size:18pt;" />					       	
					</td>
					
		        </tr>
		        <tr>
		        <td align="right" >ENDORSEMENT NUMBER<span class="danger"> * </span> : </td>
		        <td >
						<input type="text" id="PA00_0014_ENDOR_txtExtNum" name="PA00_0014_ENDOR_txtExtNum" style="width: 600px; height: 50px; font-size:18pt;" />					       	
					</td>
		        </tr>
		        <tr>
		        	<td>
		        		<label>Report Generate : </label>
		        	</td>
		        	<td>
				        <button class="btn btn-primary" id="PA00_0014_ENDOR_btnExportToPDF" style="width: 600px; height: 50px; font-size:18pt;">
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

$('#PA00_0014_ENDOR_btnExportToPDF').click(function(){	
	var v_policynumber=$('#PA00_0014_ENDOR_txtpolicynumber').val();	
	var v_extNum=$('#PA00_0014_ENDOR_txtExtNum').val();
	window.open("0014EndorsementNote.htm?policynumber="+v_policynumber+"&extNum="+v_extNum+"&reportType=1");
	window.open("0014EndorsementAttachedList.htm?policynumber="+v_policynumber+"&extNum="+v_extNum+"&reportType=1");
	window.open("0014EndorsementInvoice.htm?policynumber="+v_policynumber+"&extNum="+v_extNum+"&reportType=1");
	//window.open("PrintInvoice.htm?policynumber="+v_policynumber+ "&vattin= "+"&reportType=1");
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