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
   </body>
</html>