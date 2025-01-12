<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- <title>Google Chart - Servlet 3</title> -->
<title>Prince Bank Report</title>
<%-- ${pieDataList} --%>

<script type="text/javascript" src="assets/google/charts/jsapi.js"></script>
<script type="text/javascript">
    // Load the Visualization API and the piechart package.
    google.load('visualization', '1.0', {
        'packages' : [ 'corechart' ]
    });

    google.setOnLoadCallback(drawChart);

    // Callback that creates and populates a data table,
    // instantiates the pie chart, passes in the data and
    // draws it.
    
    function drawChart() {

        // Create the data table.
        var data = new google.visualization.DataTable();
        /* data.addColumn('string', 'Topping');
        data.addColumn('number', 'Slices');
         data.addRows([
                    <c:forEach items="${pieDataList}" var="entry">
                        ['${entry.key}',${entry.value}],
                    </c:forEach>
                    ]);     */  
        
          var data = google.visualization.arrayToDataTable([
                                                              ['NO_LOAN', 'NUMBER OF LOAN',{ role: 'annotation'}],
                                                              <c:forEach items="${pieDataList}" var="entry">
                                                              	['${entry.key}',${entry.value},${entry.value}],
                                                              </c:forEach>
                                                        ]);
                    
        // Set chart options
        
        var options = {
            'title' : 'Tenure 贷款期限', //title which will be shown right above the Google Pie Chart
            is3D : true, //render Google Pie Chart as 3D
            pieSliceText: 'label', //on mouse hover show label or name of the Country
            tooltip :  {showColorCode: true}, // whether to display color code for a Country on mouse hover
            'width' : 900, //width of the Google Pie Chart
            'height' : 500 //height of the Google Pie Chart
        };

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
        chart.draw(data, options);
        
        /*  var chartColumn = new google.visualization.ColumnChart(document.getElementById('chart_div_column'));
        chartColumn.draw(data, options);
        
        var chartBar = new google.visualization.BarChart(document.getElementById('chart_div_bar'));
        chartBar.draw(data, options); */
    }
</script>
</head>
<body>
<!-- <div style="width: 50%;background-color: yellow;">

    <div style="width: 50%;background-color: blue;float: top;">
        <div id="chart_div">Hello</div>
    </div>
</div> -->
    <div style="width: 600px;">
        <div id="chart_div_column"></div>
    </div>
    <div style="width: 600px;">
        <div id="chart_div_bar"></div>
    </div>
    <div style="width: 50%;background-color: yellow;">
<div style="border: 1px solid; width: 35%; float: right; height: 750px;" id="chart_div">

</div>
<div style="border: 1px solid; width: 35%; height: 750px;" id="1">
	<table border="1em">
			<tr>
				<th>No</th>
				<th>#</th>
				<th>Loan #</th>
			</tr>
			
		<c:forEach items="${pieDataList}" var="entry">
        	
        	<tr>
        		<td>${entry.key}</td>
        		<td>${entry.value}</td>
        		<td>${entry.value}</td>
        	</tr>
        	
        </c:forEach>
        
        </table>
</div>
</div>

</body>
</html>
