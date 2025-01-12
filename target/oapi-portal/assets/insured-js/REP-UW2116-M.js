$(function(){

	//declaration
	console.log("log rep-uw2116 - M");
	$("err_name_message").hide();
	$("err_sex_message").hide();
	
	var err_name=false;
	var err_sex=false;	
	var txtName;
	var txtSex;
	
	//end declaration ***************************************************************************/
	
	//Event
	$('#form_text mtxtName').focusout(function(){
		//alert("form_username");
		check_name();
	});
	
	$('#mtxtSex').focusout(function(){
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

	
	
	
	console.log("mutiple input form.")
	$('#mbtnPlus').click(function(){
	    //alert('jquery.');
	    // Add more control 
	    let html='';
	    html +='<fieldset id="NewRow">';
	    html +='<table>';
	    html +='<tr><td>Name:</td><td><input type="text" class="form_text bg-warning" id="mtxtName" name="mtxtName[]" required="required"></td><td><span class="text-danger" id="err_name_message[]" ></span></td></tr>';
	    html +='<tr><td>Sex:</td><td><input type="text" class="form_text bg-warning" id="mtxtSex" name="mtxtSex[]"></td><td><span class="text-danger" id="err_sex_message[]"></span></td></tr>';
	    html +='<tr><td></td><td><input type="button" id="btnRemoveOne" name="btnRemoveOne" value="-"/></td><td></td></tr>';
	    html +='</table>';
	    html +='</fieldset>';
//	    console.log(html);
	    $('#NewRow').append(html);

	    $(document).on('click','#btnRemoveOne',function(){
	        $(this).closest('#NewRow').remove();
	    });
	
	});
	
	$('#btnSaveAll').click(function(){
//	    alert('SAVE ALL DATA');
	 //   let vName=$('#txtName').val();
	    let r='';
	    let v_name=document.getElementsByName('mtxtName[]');
	    let v_sex=document.getElementsByName('mtxtSex[]');
	    for(let i=0;i<v_name.length;i++){
	        //var inp=inputs[i];
	        //alert('txtName['+i+'].value='+inp.value);
	        r +=  v_name[i].value+ ' '+ v_sex[i].value;
	        console.log(r +' , ' +i);
	    }
	    //alert(r);
	});

		
});