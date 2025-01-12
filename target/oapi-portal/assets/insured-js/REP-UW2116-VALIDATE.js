$(function(){
	console.log("log rep-uw2116 VALIDATION");
	
	 $("#form1").validate({
	        rules:{
	        	nameField:{
	                required:true
	            },
	            emailField:"required",
	            phoneField:"required"
	        },
	        messages:{
	        	nameField:{
	                required:'Please enter name'
	            }
	        }

	    });
});