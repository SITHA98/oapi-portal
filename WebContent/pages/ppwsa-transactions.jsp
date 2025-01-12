<%@page import="sitha.rupp.model.MTUserTracker"%>
<%@page import="sitha.rupp.service.UserTrackerService"%>
<%@page import="sitha.rupp.helper.ClsHelper"%>
<%@page import="sitha.rupp.service.UserListModel"%>
<%@page import="sitha.rupp.service.UserDa"%>
<%@page import="java.util.List"%>
<%@page import="sitha.rupp.service.PrincebankComponent"%>
<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="sitha.rupp.configuration.Application_Properties"%>
<%@page import="sitha.rupp.service.PPWSATxnDa"%>
<%@page import="sitha.rupp.model.PPWSATxnParam"%>
<%@page import="sitha.rupp.model.PPWSATxn"%>

<%
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	Application_Properties.SERIAL = 1;
	String date = component.getSYSTEMDATE();
	int USER_ID = component.USER_ID(request);
	int BRANCH_ID = component.BRANCH_ID(USER_ID);
	UserTrackerService trackerService = new UserTrackerService();
	if (USER_ID == 0){
		out.print("Session timeout. Please login again !");
		return;
	}
%>

<div class="preloading" id="loading" 
	style="left: 55%; margin-right: -45%; position: absolute; margin: 350px 0px 0px 0px; transfrom: translate(-50%, -50%);">
	<img src="loading/img/processing.gif" width="" />
</div>
<fieldset>
	<legend>
		<b>Report filter</b>
	</legend>
	<table class="form">
		<tr>
			<td align="right">Account No <span class="danger"> * </span> :
			</td>
			<td><select id="rpt_ppwsa_selAccNo" style="width: 100%;">
					<%
						out.print(component.createOptionValue(
								"SELECT distinct trim(regexp_substr(u.TILL_ID , '[^|]+', 1, LEVEL)) AS TILL_ID "
										+ " FROM USERS u WHERE u.ENABLED = 'N' AND u.DELETED = 'N' AND USER_ID = '" + USER_ID + "' "
										+ " CONNECT BY instr(u.TILL_ID, '|', 1, LEVEL - 1) > 0  ORDER BY TILL_ID asc",
								"TILL_ID", "TILL_ID", 0));
					%>
			</select></td>
		</tr>

		<tr>
			<td align="right">From date<span class="danger"> * </span> :
			</td>
			<td>
				<div class="input-group datepicker-demo date rpt_ppwsa_txtDateFrom"
					data-date="" data-date-format="dd-M-yyyy">
					<input style="text-align: center;" value="<%out.print(date);%>"
						type="text" id="rpt_ppwsa_txtDateFrom"
						name="rpt_ppwsa_txtDateFrom" class="form-control"
						placeholder="dd-MMM-yyyy"> <span class="input-group-addon"><i
						class="fa fa-calendar"></i></span>
				</div>
			</td>
		</tr>

		<tr>
			<td align="right">End date<span class="danger"> * </span> :
			</td>
			<td>
				<div class="input-group datepicker-demo date rpt_ppwsa_txtDateTo"
					data-date="" data-date-format="dd-M-yyyy">
					<input style="text-align: center;" value="<%out.print(date);%>"
						type="text" id="rpt_ppwsa_txtDateTo" name="rpt_ppwsa_txtDateTo"
						class="form-control" placeholder="dd-MMM-yyyy"> <span
						class="input-group-addon"><i class="fa fa-calendar"></i></span>
				</div>
			</td>
		</tr>

		<tr>
			<td><label>Report Generate : </label></td>
			<td>
				<button id="btnPreview-ppwsa" style="height: 35px; width: 100px;">
					<i class="fa fa-save"></i> Preview
				</button>
			</td>
		</tr>
	</table>
</fieldset>

<div class="col-xs-12 content" style="margin-top: 6px; display: none;">
	<table id="dg-ppwsa" class="table table-bordered table-hover display">
		<thead>
			<tr>
				<th align="center">No</th>
				<th align="center" width="100">Date</th>
				<th align="center">CBS_Ref_Number</th>
				<th align="center">Biller Number</th>
				<th align="center" width="90">Consumer Number</th>
				<th align="center"  width="150">Consumer Name</th>
				<th align="center">Currency</th>
				<th align="center">Amount</th>
			</tr>
		</thead>

		<%-- <tbody>
			<%
				PPWSATxnDa txn = new PPWSATxnDa();
				PPWSATxnParam txnPar = new PPWSATxnParam();
				//txnPar.setFromDate();
              	List<PPWSATxn> txnLs = txn.getPPWSATxn(txnPar);
              	for(int i=0;i<txnLs.size();i++){
              		PPWSATxn t = (PPWSATxn)txnLs.get(i);
             %>
					<tr>
						<td><% out.print(t.getCus_num());%></td>
		                <td><% out.print(t.getCus_num());%></td>
		                <td><% out.print(t.getPhone());%></td>
		                <td><% out.print(t.getBill_amt());%></td>
		                <td><% out.print(t.getBill_date());%></td>
		                <td><% out.print(t.getBill_period());%></td>
		                <td><% out.print(t.getBill_date());%></td>
		                <td><% out.print(t.getBill_period());%></td>                       
		           </tr>
				<%}%>
		</tbody> --%>

	</table>
</div>
<script type="text/javascript">
	// START CODE FOR BASIC DATA TABLE 
	$(document).ready(
			function() {
				$('#dg-ppwsa').DataTable(
						{
							"lengthMenu" : [ [ 100, 50, 25, 10, -1 ],[ 100, 50, 25, 10, "All" ] ],
							"columns": [ { "data": "rec" },
								 		 { "data": "trn_DT" },
								 		 { "data": "trn_REF_NO" },
										 { "data": "biller_NO" },
										 { "data": "consumer_NO" },
										 { "data": "trn_DESC" },
										 { "data": "ac_CCY" },
										 { "data": "acy_BAL" }
										]
						});
				$('.preloading').fadeOut(500, function() {
					$('.content').fadeIn(500);
				});
			});
	// END CODE FOR BASIC DATA TABLE
</script>

<script type="text/javascript">
	$('.datepicker-demo,.form_datelbl').datetimepicker({
		language : 'en',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		forceParse : 0
	});
</script>

<script type="text/javascript">
	$('#btnPreview-ppwsa').click(function() {
			if ($('#rpt_ppwsa_selAccNo').val() == '') {
				alert('Please select account no !');
				return;
			}
			if ($('#rpt_ppwsa_txtDateFrom').val() == '') {
				alert('Please select from date !');
				return;
			}
			if ($('#rpt_ppwsa_txtDateTo').val() == '') {
				alert('Please select to date !');
				return;
			}
			
			$("#loading").show();
			$('#rpt_ppwsa_selAccNo').attr('disabled', true);
			$('#rpt_ppwsa_txtDateFrom').attr('disabled', true);
			$('#rpt_ppwsa_txtDateTo').attr('disabled', true);
			$('#btnPreview-ppwsa').attr('disabled', true);
					
			$.ajax({
				cache : false,
				url : "${pageContext.request.contextPath}/partnership/casa-txn",
				type : "GET",
				data : {
					account_no : $('#rpt_ppwsa_selAccNo').val(),
					dateFrom : $("#rpt_ppwsa_txtDateFrom").val(),
					dateTo : $("#rpt_ppwsa_txtDateTo").val()
				},
				dataType : "json",
				success : function(json) {
					var obj = JSON.parse(json);
					if (obj.status == true) {
						var table = $('#dg-ppwsa').DataTable();
						var arrObj = [];
						var j;
						for (j=0; j< Object.keys(obj.data).length; j++){
							var item = {};
							item["rec"] = obj.data[j].rec;
							item["trn_DT"] =obj.data[j].trn_DT;
							item["trn_REF_NO"] = obj.data[j].trn_REF_NO;
							item["biller_NO"] =obj.data[j].biller_NO;					
							item["consumer_NO"] = obj.data[j].consumer_NO;
							item["trn_DESC"] = obj.data[j].trn_DESC;							
							item["ac_CCY"] =obj.data[j].ac_CCY;
							item["acy_BAL"] = obj.data[j].acy_BAL;			 
							arrObj.push(item);
							//console.log(item);
						}
						table.clear().draw();
						table.rows.add(arrObj).draw();
						
						$("#loading").hide();
						$('#rpt_ppwsa_selAccNo').attr('disabled', false);
						$('#rpt_ppwsa_txtDateFrom').attr('disabled', false);
						$('#rpt_ppwsa_txtDateTo').attr('disabled', false);
						$('#btnPreview-ppwsa').attr('disabled', false);
						
						//table.find("tbody tr").remove();
						/* $.each(obj.data, function(index,jsonObject) {
							table.append("<tr> <td>"+ jsonObject.ac_NAME +"</td>" 
											+ "<td>"+ jsonObject.cust_PHONE	+"</td>" 
											+ "<td>"+ jsonObject.ac_NO +"</td>" 
											+ "<td>"+ jsonObject.debit +"</td>" 
											+ "<td>"+ jsonObject.credit +"</td>" 
											+ "<td>"+ jsonObject.ac_CCY +"</td>" 
											+ "<td>"+ jsonObject.trn_DESC +"</td>" 
											+ "<td>"+ jsonObject.trn_DT +"</td>" 
										 + "</tr>");
						});	 */
					} else {
						alert(obj.message);
					}
				},
				error : function(data) {
					alert("error: " + data);
				}
			});
			
		});
</script>

