<!DOCTYPE html>
<%@page import="sitha.rupp.model.MTMenu"%>
<%@page import="sitha.rupp.model.MTUser"%>
<%@page import="sitha.rupp.service.SYS_DATEService"%>
<%@page import="sitha.rupp.helper.ClsHelper"%>
<%@page import="sitha.rupp.service.UserDa"%>
<%@page import="sitha.rupp.service.PrincebankComponent"%>
<%@page import="sitha.rupp.service.LoginDa"%>
<%@page import="java.util.List"%>
<%@page import="sitha.rupp.service.MenuDa"%>
<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<html lang="en">
<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">		
		<title>LY HOUR INSURANCE REPORT</title>
		<!-- <meta name="description" content="Free Bootstrap 4 Admin Theme | Pike Admin">
		<meta name="author" content="Pike Web Development - https://www.pikephp.com"> -->
		<meta name="description" content="LY HOUR Report">
		<meta name="author" content="LY HOUR - https://www.lyhourinsurance.com">
		<!-- Favicon -->
		<link rel="shortcut icon" href="assets/images/lyhour.ico">
		<!-- Bootstrap CSS -->	
		<link href="assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />	
		<!-- Font Awesome CSS -->
		<link href="assets/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />		
		<!-- Custom CSS -->
		<link href="assets/css/style.css" rel="stylesheet" type="text/css" />		
		<!-- BEGIN CSS for this page -->
		<link href="assets/css/dataTables.bootstrap4.min.css" rel="stylesheet" type="text/css" />
		<!-- END CSS for this page -->		
        <!-- tab style -->        
        <link rel="stylesheet" href="assets/tab/bootstrap.min.css">
        <link rel="stylesheet" href="assets/tab/font-awesome.min.css">
        <link rel="stylesheet" href="assets/tab/bootstrap-dynamic-tabs/bootstrap-dynamic-tabs.css">
        <link rel="stylesheet" href="assets/css/mystyle.css">
		<!-- end tab style -->
        <!-- date time picker -->
        <link rel="stylesheet" href="assets/picker/bootstrap-datetimepicker.css">
        <!-- <link rel="stylesheet" href="assets/picker/bootstrap.min.csss"> -->
        
        <!-- alerts -->
        <!-- <script src="assets/alert/dialogbox.js"></script> -->
        <link rel="stylesheet" type="text/css" href="assets/alert/dialogbox.css"/>
        <!-- end alerts --> 
        <!-- choosen -->
        <link rel="stylesheet" href="assets/choosen/bootstrap.css" />  		
        <!-- end choosen -->
        <!-- tree for group role -->
        <link href="assets/plugins/jstree/style.css" rel="stylesheet" type="text/css"/>
</head>
<% 
				PrincebankComponent component=new PrincebankComponent();
        		
        		int USER_ID=component.USER_ID(request);
        		String username=component.USER_DISPLAY_NAME(USER_ID);
        		UserDa userDa=new UserDa();
        		MTUser user=userDa.getUserInfo(USER_ID);
        		int ROLE_ID=user.getGrId();
        		int BRANCH_ID=user.getBrachId();
        		String branchCode=component.BR_NAME(BRANCH_ID);
        		SYS_DATEService dateService=new SYS_DATEService();
        		String date=dateService.SYS_FCSRV_DATE();
%>
<body class="adminbody" >
<div id="main" >
	<!-- top bar navigation -->
	<div class="headerbar">
		<!-- LOGO -->
        <div class="headerbar-left" style="padding-top:3px;">
			<a href="#" ><img alt="Logo" src="assets/images/logo.png" width="60%"/> </a>
        </div>
        <nav class="navbar-custom">

                    <ul class="list-inline float-right mb-0">
						
						<li class="list-inline-item dropdown notif">
                            <a class="nav-link dropdown-toggle arrow-none" data-toggle="dropdown" href="#" role="button" aria-haspopup="false" aria-expanded="false">
                                <i class="fa fa-fw fa-question-circle"></i>
                            </a>
                            <div class="dropdown-menu dropdown-menu-right dropdown-arrow dropdown-arrow-success dropdown-lg">
                                <!-- item-->
                                <div class="dropdown-item noti-title">
                                    <h5><small>Help and Support</small></h5>
                                </div>

                                <!-- item-->
                                <a target="_blank" href="https://www.lyhourinsurance.com" class="dropdown-item notify-item">                                    
                                    <p class="notify-details ml-0">
                                        <b>Do you want support from helpdesk?</b>
                                        <span>Name&nbsp;&nbsp;: SOPHEAP SITHA</span>
                                        <span>Phone&nbsp;: (855)12 63 63 98</span>
                                        <span>E-mail&nbsp;: its@lyhourinsurance.com</span>
                                        <span>Extension&nbsp;: <b>777</b></span>
                                    </p>
                                </a>

                                <!-- All-->
                                <a title="Clcik to visit Pike Admin Website" target="_blank" href="https://www.lyhourinsurance.com" class="dropdown-item notify-item notify-all">
                                    <i class="fa fa-link"></i> Visit LY HOUR INSURNACE Website
                                </a>

                            </div>
                        </li>

                        <li class="list-inline-item dropdown notif">
                            <a class="nav-link dropdown-toggle nav-user" data-toggle="dropdown" href="#" role="button" aria-haspopup="false" aria-expanded="false">
                                <img src="assets/images/avatars/user-logo.png" alt="Profile image" class="avatar-rounded">
                            </a>
                            <div class="dropdown-menu dropdown-menu-right profile-dropdown ">
                                <!-- item-->
                                <div class="dropdown-item noti-title">
                                    <h5 class="text-overflow"><small>Settings</small> </h5>
                                </div>

                                <!-- item-->
                               <%--  <a href="#" id="change_profile_settings" class="dropdown-item notify-item" onclick="change_profile_settings('<% out.print(USER_ID); %>')" >
                                    <i class="fa fa-user"></i> <span>Profile</span>
                                </a> --%>

                                <!-- item-->
                                <a href="${pageContext.request.contextPath}/logout" class="dropdown-item notify-item">
                                    <i class="fa fa-power-off"></i> <span>Logout</span>
                                </a>
								
								<!-- item-->
                                <!-- <a target="_blank" href="https://www.pikeadmin.com" class="dropdown-item notify-item">
                                    <i class="fa fa-external-link"></i> <span>Pike Admin</span>
                                </a> -->
                            </div>
                        </li>

                    </ul>

                    <ul class="list-inline menu-left mb-0">
                        <li class="float-left">
                            <button class="button-menu-mobile open-left">
								<i class="fa fa-fw fa-bars"></i>
                            </button>
                        </li>    
                        <li class="float-right">
                        	<!-- <div style="padding-top:12px;font-size:16px;color:yellow;"> -->
                        	<div style="padding-top:12px;font-size:12px;color:#BE955B;">
                            	<span><b>Current Date : </b></span>
                            	<span id="dateDisplay123"><% out.print(date); %></span>
                                <span id="clockDisplay" style="margin-left:10px;"></span>
                                <%-- <span style="margin-left:50px;"><b>Branch : </b></span><span><% out.print(ClsHelper.format3Digits(BRANCH_ID+"")+"-"+branchCode); %></span> --%>
                                <span style="margin-left:50px;"><b>User : </b><b><% out.print(username); %></b></span>
                               <%--  <span style="margin-left:30px;"><b>Role : </b></span><span><% out.print(component.getGroupDesc(ROLE_ID)); %></span> --%>
                            </div>
                        </li>                    
                    </ul>
        </nav>
	</div>
	<!-- End Navigation -->
	<% 
		ApplicationContext context=new ClassPathXmlApplicationContext("Spring_Bean.xml");
		MenuDa me_da=(MenuDa)context.getBean("menuDa");
		//UserOnlineDa uolda=(UserOnlineDa)context.getBean("useronlineDa");
	%>
 
	<!-- Left Sidebar -->
	<div class="left main-sidebar">
		<div class="sidebar-inner leftscroll">
			<div id="sidebar-menu" style="height:800px;overflow:auto;">
			<%
			int index=1;
            LoginDa logDa=(LoginDa)context.getBean("loginDa");
            String userId=(String)session.getAttribute("PRINCE_USER_ID"); 
            //String userId="1";
            int gr=logDa.getUserGr(Integer.parseInt(userId));
            List<MTMenu> menu=me_da.getMenuall(gr);            
            String menustr="<ul id=\"menuSideBar\">";
            for(int i=0;i<menu.size();i++){
            	index++;
            	MTMenu mel=(MTMenu)menu.get(i);
            	if(mel.getMenu_main()==0){
            		//menustr+="<li><a href=\"#"+mel.getMenu_name()+"\"> <i class=\""+mel.getMenu_icon()+" menu-item-icon\"></i><span class=\"inner-text\">"+ mel.getMenu_description() +"</span></a>";
            		List<MTMenu> menus=me_da.getMenu(gr,mel.getMenu_id(),response);
            		if(menus.size()>0){
            			menustr+="<li><a href='javascript:void(0);'> <i class=\""+mel.getMenu_icon()+" menu-item-icon\"></i><span class=\"inner-text\">"+ mel.getMenu_description() +"</span><span class=\"menu-arrow\"></span></a>";
            			menustr+="<ul>";
            			for(int j=0;j<menus.size();j++){ 
            				index++;
            				MTMenu mes=(MTMenu)menus.get(j);
                			//menustr+="<li><a href='#"+mes.getMenu_name()+"'><span class=\"inner-text\">"+mes.getMenu_description()+"</span></a>";
                			List<MTMenu> menuss=me_da.getMenu(gr,mes.getMenu_id(),response);                			
                			if(menuss.size()>0){
                				menustr+="<li><a href='javascript:void(0);'><span class=\"inner-text\">"+mes.getMenu_description()+"</span><span class=\"menu-arrow\"></span></a>";
                				menustr+="<ul>";
                				for(int k=0;k<menuss.size();k++){
                					index++;
                					MTMenu mess=(MTMenu)menuss.get(k);
                					menustr+="<li  class=\"submenu\" id=\"me"+mess.getMenu_name()+"\">"
                							+"<a href=\"javascript:void(0);\">"
                							+"<span class=\"inner-text\">"+mess.getMenu_description()+"</span></a>";                					
                					List<MTMenu> menusss=me_da.getMenu(gr,mess.getMenu_id(),response);
                					if(menusss.size()>0){
                						menustr+="<ul>";
                						//menustr+="<li><a href='#'><span>hello world true</span></a></li>";
                						for(int l=0;l<menusss.size();l++){
                							index++;
                							MTMenu messs=(MTMenu)menusss.get(l);
                							menustr+="<li class=\"submenu\" id=\"me"+messs.getMenu_name()+"\">"
                									+"<a href=\"javascript:void(0);\">"
                									+"<span class=\"inner-text\">"+messs.getMenu_description()+"</span></a></li>";
                						}
                						menustr+="</ul>";
                					}
                				}
                				menustr+="</li></ul>";
                			}  
                			else{
                				menustr+="<li class=\"submenu\" id=\"me"+mes.getMenu_name()+"\">"
                						+"<a href=\"javascript:void(0);\">"
                						+"<span class=\"inner-text\">"+mes.getMenu_description()+"</span></a>";
                			}
                		}            			
            			menustr+="</li></ul>";
            		}
            		else{
            			menustr+="<li class=\"submenu\" id=\"me"+mel.getMenu_name()+"\">"
            					+"<a href=\"javascript:void(0);\"> "
            					+"<i class=\""+mel.getMenu_icon()+" menu-item-icon\"></i>"
            					+"<span class=\"inner-text\">"+ mel.getMenu_description() +"</span></a>";
        			}
            	}
            }
           	menustr+="</li></ul>";%>
            <%out.print(menustr);%>
			 
			<!-- <ul id="menuSideBar">	
                  <li class="submenu" id="samplepage">
                      <a tabindex="-1" href="javascript:void(0);">
                      	<i class="fa fa-pagelines"></i><span> Simple Page </span> 
                      </a>
                  </li>
                  <li class="submenu" id="samplepage-table">
                      <a tabindex="-1" href="javascript:void(0);">
                      	<i class="fa fa-pagelines"></i><span> Table </span> 
                      </a>
                  </li>
            </ul>  -->        
             
            <hr style="border-color:#666;">
	        <div style="text-align:center;color:#666;">
            	<h6>License to : LY HOUR INSURANCE </h6>
            </div>
            <div class="clearfix">
            	
            </div>

			</div>
        
			<div class="clearfix"></div>

		</div>

	</div>
	<!-- End Sidebar -->
			
		<div class="content-page">	
		<!-- Start content -->
	        <div class="content">
	        	<div class="container" >
	        	    <div id="tabs" style="padding-left: 1px;"></div>
	        	</div>
  			</div>
  		</div>
    </div>
	<!-- END content-page -->
    
	<footer class="footer" style="position:fixed;">
		<span class="text-right">
		<b>Copyright<sup>&copy;</sup> <a target="_blank" href="#">IT DEPARTMENT.</a></b>
		</span>
		<span><b>Version 1.0.0.0</b></span>
		<span class="float-right">
		<b>Powered by <a target="_blank" href="https://www.lyhourinsurance.com">LY HOUR INSURANCE</a></b>
		</span>
	</footer>
    
<script src="assets/js-main/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="assets/js-main/jquery-ui.js" type="text/javascript" ></script>
<script src="assets/js-main/bootstrap.min.js" type="text/javascript"></script>
<script src="assets/js/jquery.validate.min.js" type="text/javascript"></script>

<script src="assets/tab/bootstrap-dynamic-tabs/bootstrap-dynamic-tabs.js" type="text/javascript" ></script>    
<script type="text/javascript">
var tabs = $('#tabs').bootstrapDynamicTabs();
<%
for(int i=0;i<menu.size();i++){
	index++;
	MTMenu mel=(MTMenu)menu.get(i);%>
	$(<%out.print("'#me"+mel.getMenu_name()+"'");%>).click(function(){		
		tabs.addTab({
			title: <%out.print("'"+mel.getMenu_description()+"'");%>,
			id: <%out.print("'"+mel.getMenu_name()+"'");%>,
			ajaxUrl: <%out.print("'pages/"+mel.getMenu_name()+".jsp'");%>
		});
	});
<% } %> 
/* $('#change_profile_settings').click(function(){
	tabs.addTab({
		title: 'Change info.',
		id: 'user',
		ajaxUrl: 'pages/user.jsp?id='
	});
}); */
function change_profile_settings(user_id){
	tabs.addTab({
		title: 'Change info.',
		id: 'user',
		ajaxUrl: 'pages/user.jsp?flag=staticstr&id='+user_id
	});
}
$('#samplepage').click(function(){
	tabs.addTab({
		title: 'Sample Page',
		id: 'samplepage-id',
		ajaxUrl: 'form/sample-page.jsp'
	});
});
$('#samplepage-table').click(function(){
	tabs.addTab({
		title: 'test-table',
		id: 'test-table-id',
		ajaxUrl: 'form/test-table.jsp'
	});
});
</script>
    

</div>
<!-- END main -->

<script src="assets/js/modernizr.min.js"></script>
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/moment.min.js"></script>
		
<script src="assets/js/popper.min.js"></script>
<!--<script src="assets/js/bootstrap.min.js"></script>-->

<script src="assets/js/detect.js"></script>
<script src="assets/js/fastclick.js"></script>
<script src="assets/js/jquery.blockUI.js"></script>
<script src="assets/js/jquery.nicescroll.js"></script>

<!-- App js -->
<script src="assets/js/pikeadmin.js"></script>

<!-- BEGIN Java Script for this page -->
<script src="assets/chart/js/Chart.min.js"></script>
<script src="assets/chart/js/jquery.dataTables.min.js"></script>
<script src="assets/chart/js/dataTables.bootstrap4.min.js"></script>

<!-- Counter-Up-->
<script src="assets/plugins/waypoints/lib/jquery.waypoints.min.js"></script>
<script src="assets/plugins/counterup/jquery.counterup.min.js"></script>	
<!-- date time picker -->
<script src="assets/picker/bootstrap-datetimepicker.js"></script>
<!-- number format -->
<script src="assets/numberformat/accounting.js"></script>
<script src="assets/numberformat/accounting.min.js"></script>
<script src="assets/numberformat/custom.js"></script>
<script>
		$(document).ready(function() {
			/*get height screen*/
			//alert(screen.height);
			//alert(screen.width);
			// data-tables
			//$('#example1').DataTable();
					
			// counter-up
			/* $('.counter').counterUp({
				delay: 10,
				time: 600
			}); */
			 /* tabs.addTab({
				title: 'Dashboard',
				id: 'dashboard',
				ajaxUrl: 'pages/dashboard.jsp'
			});  */
		} );		
</script>
	
	<script>
	var ctx1 = document.getElementById("lineChart").getContext('2d');
	var lineChart = new Chart(ctx1, {
		type: 'bar',
		data: {
			labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
			datasets: [{
					label: 'Dataset 1',
					backgroundColor: '#3EB9DC',
					data: [10, 14, 6, 7, 13, 9, 13, 16, 11, 8, 12, 9] 
				}, {
					label: 'Dataset 2',
					backgroundColor: '#EBEFF3',
					data: [12, 14, 6, 7, 13, 6, 13, 16, 10, 8, 11, 12]
				}]
				
		},
		options: {
						tooltips: {
							mode: 'index',
							intersect: false
						},
						responsive: true,
						scales: {
							xAxes: [{
								stacked: true,
							}],
							yAxes: [{
								stacked: true
							}]
						}
					}
	});


	var ctx2 = document.getElementById("pieChart").getContext('2d');
	var pieChart = new Chart(ctx2, {
		type: 'pie',
		data: {
				datasets: [{
					data: [12, 19, 3, 5, 2, 3],
					backgroundColor: [
						'rgba(255,99,132,1)',
						'rgba(54, 162, 235, 1)',
						'rgba(255, 206, 86, 1)',
						'rgba(75, 192, 192, 1)',
						'rgba(153, 102, 255, 1)',
						'rgba(255, 159, 64, 1)'
					],
					label: 'Dataset 1'
				}],
				labels: [
					"Red",
					"Orange",
					"Yellow",
					"Green",
					"Blue"
				]
			},
			options: {
				responsive: true
			}
	 
	});


	var ctx3 = document.getElementById("doughnutChart").getContext('2d');
	var doughnutChart = new Chart(ctx3, {
		type: 'doughnut',
		data: {
				datasets: [{
					data: [12, 19, 3, 5, 2, 3],
					backgroundColor: [
						'rgba(255,99,132,1)',
						'rgba(54, 162, 235, 1)',
						'rgba(255, 206, 86, 1)',
						'rgba(75, 192, 192, 1)',
						'rgba(153, 102, 255, 1)',
						'rgba(255, 159, 64, 1)'
					],
					label: 'Dataset 1'
				}],
				labels: [
					"Red",
					"Orange",
					"Yellow",
					"Green",
					"Blue"
				]
			},
			options: {
				responsive: true
			}
	 
	});
	</script>
<!-- END Java Script for this page -->
<!--timer-->
<script type="text/javascript">
$(function(){
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!	
	var yyyy = today.getFullYear();
	
	if(dd<10){
		dd='0'+dd;
	} 
	if(mm<10){
		mm='0'+mm;
	} 
	var today = dd+'/'+mm+'/'+yyyy;
	document.getElementById('dateDisplay');
});
function renderTime() {
	var currentTime = new Date();
	var diem = "AM";
	var h = currentTime.getHours();
	var m = currentTime.getMinutes();
    var s = currentTime.getSeconds();
	setTimeout('renderTime()',1000);
    /*start time*/
	if (h == 0) {
		h = 12;
	} else if (h > 12) { 
		h = h - 12;
		diem="PM";
	}
	if (h < 10) {
		h = "0" + h;
	}
	if (m < 10) {
		m = "0" + m;
	}
	if (s < 10) {
		s = "0" + s;
	}
	/*end time*/
	/*start date*/
	var dd = currentTime.getDate();
	var mm = currentTime.getMonth()+1; //January is 0!	
	var yyyy = currentTime.getFullYear();
	
	if(dd<10){
		dd='0'+dd;
	} 
	if(mm<10){
		mm='0'+mm;
	} 
	var today = dd+'/'+mm+'/'+yyyy;
	/*end date*/
    var myClock = document.getElementById('clockDisplay');
	var myDate = document.getElementById('dateDisplay');
	myClock.textContent = h + ":" + m + ":" + s + " " + diem;
	myClock.innerText = h + ":" + m + ":" + s + " " + diem;
	//myDate.innerHTML=today;
}
renderTime();
</script>
<!--end time-->
<!-- detect idle time -->
<script type="text/javascript">
var idleTime = 0;
$(document).ready(function () {
    //Increment the idle time counter every minute.
    var idleInterval = setInterval(timerIncrement, 60000); // 1 minute
    //Zero the idle timer on mouse movement.
    $(this).mousemove(function (e) {
        idleTime = 0;
    });
    $(this).keypress(function (e) {
        idleTime = 0;
    });
});

function timerIncrement() {
    idleTime = idleTime + 1;
    if ( idleTime > 15 ) { /* 20 minutes */
        //alert('time out');
        location.replace("/oapi-portal");
    	//window.location.reload();
    }
}
</script>
<!-- end detect idle time --> 

<!-- export data table -->	
<!-- <link rel="stylesheet" type="text/css" href="assets/export-datatable/css/jquery.dataTables.min.css"> -->
<link rel="stylesheet" type="text/css" href="assets/export-datatable/css/buttons.dataTables.min.css">
<script type="text/javascript" language="javascript" src="assets/export-datatable/js/dataTables.buttons.min.js"></script>	
<script type="text/javascript" language="javascript" src="assets/export-datatable/js/jszip.min.js"></script>
<script type="text/javascript" language="javascript" src="assets/export-datatable/js/pdfmake.min.js"></script>
<script type="text/javascript" language="javascript" src="assets/export-datatable/js/vfs_fonts.js"></script> 
<script type="text/javascript" language="javascript" src="assets/export-datatable/js/buttons.html5.min.js"></script>
<script type="text/javascript" language="javascript" src="assets/export-datatable/js/buttons.print.min.js"></script>
<script type="text/javascript" language="javascript" src="assets/js/jquery.validate.min.js"></script>
</body>
</html>