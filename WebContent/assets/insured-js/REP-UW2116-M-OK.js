$(function() {
	
	$("#buttonAdd").bind("click", function() {
		var div = $("<div />");
		// div.html(GenerateTextName(""));
		div.html(AddMoreWebControl(""))
		$("#TextBoxContainer").append(div);
	});
	$("#btnShowValues").bind("click", function() {
		var values = "";
		$("input[name=nameField]").each(function() {
			values += $(this).val() + "\n";
		});
		alert(values);
	});
	$("body").on("click", "#remove", function() {
		$(this).closest("#fMore").remove();
	});

	$("body").on("focusout", "input[name=nameField]", function() {
		// alert("out");
		if($(this).val()<=5){
			console.log($(this).val() + " is less than 5.\n")
			$(this).val("");
			alert("enter value that >5");
		}
	});

	$("#btnSave").click(function(){
		alert("submitted");
	});
	
	//Addx
	$('#vi_input').on('click','li',function(){
		$('#inputbox').fadeout(500);
	});
	
	$('#btnAddx').on('click',function(){
		var newItem='<div id="inputbox"><input type="text" id="txtText" name="txtText"/></div>';
		$('#inputbox1').append(newItem);
	});
});

function GenerateTextName(value) {
	let v_html = '';
	v_html += '<div class="form-group" id="form-group">';
	v_html += '<label for="nameField" class="col-xs-2">Name</label>';
	v_html += '<div class="col-xs-10">';
	v_html += '<input type="text" name = "nameField" value = "' + value
			+ '" class="form-control" required="required"/> ';
	v_html += '<input type="button" value="Remove" class="remove" />';
	v_html += '</div>';
	return v_html;
}

function AddMoreWebControl(value) {
	let v_html = '';
	v_html += '<fieldset id="fMore">';
	v_html += '<div class="form-group">';
	v_html += '<label for="nameField" class="col-xs-2">Name</label>';
	v_html += '<div class="col-xs-10">';
	v_html += '<input type="text" class="form-control bg-warning" id="nameField" name="nameField"';
	v_html += 'required="required" />';
	v_html += '</div>';
	v_html += '</div>';
	v_html += '<div class="form-group">';
	v_html += '<label for="emailField" class="col-xs-2">Email</label>';
	v_html += '<div class="col-xs-10">';
	v_html += '<input type="email" class="form-control" id="emailField" name="emailField"';
	v_html += 'placeholder="Your Email" />';
	v_html += '</div>';
	v_html += '</div>';
	v_html += '<div class="form-group">';
	v_html += '<label for="phoneField" class="col-xs-2">Phone</label>';
	v_html += '<div class="col-xs-10">';
	v_html += '<input type="text" class="form-control" id="phoneField" name="phoneField"';
	v_html += 'placeholder=" Your Phone Number" />';
	v_html += '</div>';
	v_html += '</div>';
	v_html += '<div class="form-group">';
	v_html += '<label for="descField" class="col-xs-2">Description </label>';
	v_html += '<div class="col-xs-10">';
	v_html += '<textarea type="text" class="form-control" id="descField" name="phoneField"';
	v_html += 'placeholder="Your Comments"></textarea>';
	v_html += '</div>';
	v_html += '<input type="button" value="-" id="remove" class="btn btn-primary" />';
	v_html += '</div>';
	v_html += '</fieldset>';
	return v_html;
}