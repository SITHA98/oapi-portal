<html>
   <head>
      <title>Google Charts Tutorial</title>
      <script type = "text/javascript" src = "assets/google/charts/loader.js">
      </script>
      <script type = "text/javascript">
         google.charts.load('current', {packages: ['corechart']});    
      </script>
   </head>
   
   
      <script language = "JavaScript">
         function drawChart() {
            // Define the chart to be drawn.
            var data = google.visualization.arrayToDataTable([
               ['Year', 'Asia', { role: 'annotation'} ,'Europe', { role: 'annotation'}],
               ['2012',  900,'900',      390, '390'],
               ['2013',  1000,'1000',      400,'400'],
               ['2014',  1170,'1170',      440,'440'],
               ['2015',  1250,'1250',       480,'480'],
               ['2016',  1530,'1530',      540,'540']
            ]);

            var options = {title: 'Population (in millions)'};  

            // Instantiate and draw the chart.
            var chart = new google.visualization.ColumnChart(document.getElementById('container'));
            chart.draw(data, options);
         }
         
         google.charts.setOnLoadCallback(drawChart);
      </script>
      
      <script language = "JavaScript">
         function drawChart2() {
            // Define the chart to be drawn.
            var data2 = google.visualization.arrayToDataTable([
               ['Year', 'Asia', { role: 'annotation'}],
               ['2012',  900,'900',],
               ['2013',  1000,'1000'],
               ['2014',  1170,'1170'],
               ['2015',  1250,'1250'],
               ['2016',  1530,'1530']
            ]);

            var options2 = {title: 'Population (in millions)'};  

            // Instantiate and draw the chart.
            var chart2 = new google.visualization.ColumnChart(document.getElementById('container2'));
            chart2.draw(data2, options2);
         }
         
         google.charts.setOnLoadCallback(drawChart2);
      </script>
      
   <body>
      <div id = "container" style = "width: 550px; height: 400px; margin: 0 auto"></div>
      
      <div id = "container2" style = "width: 550px; height: 400px; margin: 0 auto"></div>
      
      
      <div class="col-xs-12 content" style="margin-top:0px; display: none;">		
		<h1>Hello world</h1>
		<div class="row">
			<div class="col-xxs-12 col-xs-6">
				<div class="panel-body">
					<table id="user" class="table table-bordered table-striped"
						style="clear: both">
						<tbody>
							<tr style="background-color: #e1effc;">
								<td colspan="2" style="padding: 0px 10px;"><h4>
										Loan Account - Info
									</h4></td>
							</tr>
							<tr>
								<td style="border-right: none;">Loan Account</td>
								<td><label id="lblLoanAccount" name="lblLoanAccount"></label></td>
							</tr>
							<tr>
								<td style="border-right: none;">Customer Name </td>
								<td><label id="lblCustomerName" name="lblCustomerName"></label></td>
							</tr>
							<tr>
								<td style="border-right: none;">Currency Code</td>
								<td><label id="lblCurrCode" name="lblCurrCode"></label></td>
							</tr>
							<tr>
								<td style="border-right: none;">Disbursement Date</td>
								<td><label id="lblDisbDate" name="lblDisbDate"></label></td>
							</tr>
							<tr>
								<td style="border-right: none;">Maturity Date</td>
								<td><label id="lblMatDate" name="lblMatDate"></label></td>
							</tr>
							<tr>
								<td style="border-right: none;">Principal Amount</td>
								<td><label id="lblPrincipal" name="lblPrincipal"></label></td>
							</tr>
							<tr>
								<td style="border-right: none;">Outstanding Amount</td>
								<td>
									<a style="cursor: pointer;" href="javascript:void(0)" id="loan_centre_show_account_statement1">
										<label id="lblOutstanding" name="lblOutstanding" style="color: blue;"></label>
									</a>
								</td>
							</tr>
							<!-- <tr>
								<td style="border-right: none;">Total Interest Earned</td>
								<td><label id="lblInterestEarned" name="lblInterestEarned"></label></td>
							</tr>
							<tr>
								<td style="border-right: none;">Accrued Interest Receivable</td>
								<td><label id="lblAccuredAmt" name="lblAccuredAmt"></label></td>
							</tr> -->
							<tr>
								<td style="border-right: none;">Interest Rate (%)</td>
								<td><label id="lblIntRate" name="lblIntRate"></label></td>
							</tr>
							<tr>
								<td style="border-right: none;">Loan Class</td>
								<td><label id="lblLoanClass" name="lblLoanClass"></label></td>
							</tr>
							<tr>
								<td style="border-right: none;">Provision Amount</td>
								<td><label id="lblProvisionAmount" name="lblProvisionAmount"></label></td>
							</tr>
							<tr>
								<td style="border-right: none;">Provision Mod</td>
								<td><label id="lblProvisionMod" name="lblProvisionMod"></label></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

			<div class="col-xxs-12 col-xs-6">
				<div class="panel-body">
					<table id="user" class="table table-bordered table-striped"
						style="clear: both">
						<tbody>
							<tr style="background-color: #e1effc;">
								<td colspan="2" style="padding: 0px 10px;"><h4>
										Current&nbsp; / &nbsp;Saving Account - Info.
									</h4></td>
							</tr>
							<tr>
								<td style="border-right: none;"> Current &nbsp; / &nbsp; Saving Account </td>
								<td><label id="lblCASAAccount" name="lblCASAAccount"></label></td>
							</tr>
							<tr>
								<td style="border-right: none;">Product Code</td>
								<td><label id="lblProCode" name="lblProCode"></label></td>
							</tr>
							<tr>
								<td style="border-right: none;">Currency</td>
								<td><label id="lblCurrency" name="lblCurrency"></label></td>
							</tr>
							<tr>
								<td style="border-right: none;">Opening Date</td>
								<td><label id="lblOpeningDate" name="lblOpeningDate"></label></td>
							</tr>
							<tr>
								<td style="border-right: none;">Current Balance</td>
								<td><label id="lblCurrentBal" name="lblCurrentBal"></label></td>
							</tr>
							<tr>
								<td style="border-right: none;">Available Balance</td>
								<td><label id="lblAvailBal" name="lblAvailBal"></label></td>
							</tr>
							
							<tr>
								<td style="border: none; background-color: #fff;" align="right"></td>
								<td style="border: none; background-color: #fff;" align="left">
									<label id="lblFeeDue" name="lblFeeDue"></label>
								</td>
							</tr>
							<tr>
								<td style="border: none; background-color: #fff;" align="right"></td>
								<td style="border: none; background-color: #fff;" align="left">
									<label id="lblPenDue" name="lblPenDue"></label>
								</td>
							</tr>
							<tr>
								<td style="border-right: none;"></td>
								<td>
									<label id="lblTotal" name="lblTotal"></label>
								</td>
							</tr>
							<!-- <tr>
								<td style="border-right: none;"></td>
								<td>
									<input type="hidden" id="loan_centre_CASA" />
									<a style="cursor: pointer;" href="javascript:void(0)" id="loan_centre_show_account_statement">
										<label id="lblAvailableBal" name="lblAvailableBal" style="color: blue;"></label>
									</a>
								</td>
							</tr> -->
							<tr>
								<td style="border-right: none;"><!-- Balance Due From Customer --></td>
								<td>
									<label id="lblDuefromCust" name="lblDuefromCust"></label>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		
</div>
   </body>
</html>