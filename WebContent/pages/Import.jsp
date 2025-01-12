<%
	String user_login = (String)session.getAttribute("PRINCE_GR_ID");
%>
<script src="assets/alert/dialogbox.js"></script>
    <div style="height: 15px;"></div>
    <div class="card">
  <div class="card-body">
    <a href="images/TemplateFile.xlsm" class="btn btn-primary">Download Template</a>
  </div>
</div>
      <div class="card">
  <div class="card-body">
  
    <form method="post" action="uploadFile" enctype="multipart/form-data" id="myform">
	<table class="form">
		<!-- <tr>
	        <td align="right">Partner ID<span class="danger"> * </span> : </td>
	       	<td >
	       	<input type="text" id="partner_id" class="form-control " name="partner_id" required="required" />
	       	</td>
	    </tr> -->
	
	    <tr>
	        <td align="right">Import Excel<span class="danger"> * </span> : </td>
	       	<td >
	       	<input type="file" id="import_file" required="required" name="import_file" />
	       	</td>
	       	<td><div class="loader"></div></td>
	    </tr>
	    <tr>
	    	
	    	<td></td>
	    	<td><input type="button" class="btn btn-info" value="Upload" id="but_upload" /></td>
	    	<td></td>
	    </tr>
        <tr>
        	<td style="height:30px;"></td>
        </tr>
	</table>
	<input type="hidden" name=""/>
	</form>
</div>
</div>
	<div style="border: 1px #f9f9f9 solid;margin: 20px 0px;"></div>

<!-- <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script> -->
<script src="assets/alert/dialogbox.js"></script>
<script type="text/javascript">
var myApp = {
		init : function (){
			myApp.initData();
			myApp.bindingEvent();
		},
		initData: function(){
			$(".loader").hide();
		},
		bindingEvent: function(){
		 	
			$("#but_upload").click(function(){

			if($("#import_file").get(0).files.length == 0 ){				
				 $.dialogbox({
          			  type:'msg',
          			  width:400,
          			  title:'Information',
          			  icon:1,
          			  content:"Please select file",
          			  btn:['Ok'],
          			  call:[
          			    function(){
          			      $.dialogbox.close(); 
          			    }
          			  ]
          			});
			}else{
				
			$(".loader").show();
			$("#but_upload").prop("disabled",true);
	         var fd = new FormData();
	         var files = $('#import_file')[0].files[0];
	         fd.append('file',files);
	         fd.append('groupId',<%out.print(Integer.parseInt(user_login)); %>);

	         $.ajax({
	             url: 'uploadFile',
	             type: 'post',
	             data: fd,
	             contentType: false,
	             processData: false,
	             success: function(response){
	                 if(response != 0){                    
	                     $.dialogbox({
	           			  type:'msg',
	           			  width:400,
	           			  title:'Information',
	           			  icon:1,
	           			  content:response,
	           			  btn:['Ok'],
	           			  call:[
	           			    function(){
	           			      $.dialogbox.close(); 
	           			    }
	           			  ]
	           			});
	                     $("#import_file").val("");
	                     $(".loader").hide();
	     				$("#but_upload").prop("disabled",false);
	                     
	                 }else{
	                	 $.dialogbox({
		           			  type:'msg',
		           			  width:400,
		           			  title:'Error',
		           			  icon:1,
		           			  content:response,
		           			  btn:['Ok'],
		           			  call:[
		           			    function(){
		           			      $.dialogbox.close(); 
		           			    }
		           			  ]
		           			});
	                 }
	             },
	         });
	         
			}
	     });

		}
	};
$(document).ready(myApp.init);
	
// /* 	$( document ).ready(function() {
// 	$('.preloading').fadeOut(500,function(){
// 		$('.content').fadeIn(500);
// 	});
	
	
// 	$("#but_upload").click(function(){

//         var fd = new FormData();
//         var files = $('#import_file')[0].files[0];
//         fd.append('file',files);

//         $.ajax({
//             url: 'uploadFile',
//             type: 'post',
//             data: fd,
//             contentType: false,
//             processData: false,
//             success: function(response){
//                 if(response != 0){
//                     //$("#img").attr("src",response); 
//                     //$(".preview img").show(); // Display image element
//                     alert(response);
//                 }else{
//                     alert('file not uploaded');
//                 }
//             },
//         });
//     });
	
	
	
	
// } ); */
/* $('#user_btn_refresh').click(function(){
	$("#user").load("pages/user.jsp");
}); */
// END PREPAIR LOADING
</script>