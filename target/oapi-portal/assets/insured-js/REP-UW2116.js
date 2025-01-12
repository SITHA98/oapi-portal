$(function(){
	//declaration
	console.log("log rep-uw2116");
	$("err_name_message").hide();
	$("err_sex_message").hide();
	
	var err_name=false;
	var err_sex=false;	
	var txtName;
	var txtSex;
	
	//end declaration ***************************************************************************/
	
	//Event
	$('#form_username').focusout(function(){
		//alert("form_username");
		check_name();
	});
	
	$('#txt_sex').focusout(function(){
		check_sex();
	});
	//End Event ***************************************************************************/
	
	//Function
	function check_name(){
		init();
		if(txtName==''){
			$("#err_name_message").html("Enter name");
			$("#err_name_message").show();
			err_name=true;
		}else{
			$("#err_name_message").hide();
		}
		console.log("err_name:" + err_name +" txtName:"+txtName)
	}
	
	function check_sex(){
		init();
		if(txtSex==''){
			$("#err_sex_message").html("Enter Sex");
			$("#err_sex_message").show();
			err_sex=true;
		}else{
			$("#err_sex_message").hide();
		}
		console.log("err_sex:"+err_sex +" txtSex:"+txtSex)
	}
	
	function init(){
		txtName=$("#form_username").val();
		txtSex=$("#txt_sex").val();
		console.log("called init. "  + txtName +" and "+txtSex);
	}
	/*
	 * End Function
	 ***************************************************************************/


	/*
	 * Action on Button
	 */
	$('#btnSubmit').click(function(){
		err_name=false;
		err_sex=false;
		
		check_name();
		check_sex();
		
		if(err_name==false && err_sex==false){
			console.log("form summitted." + txtName + " and " + txtSex);
			return true;
		}else{
			console.log("form not summitted");
			return false;
		}
	});
	
	/*
	 * End action on button
	 */
	
});