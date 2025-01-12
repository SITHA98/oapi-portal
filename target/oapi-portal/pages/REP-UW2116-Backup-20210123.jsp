<%@page import="java.util.ArrayList"%>
<%@page import="sitha.rupp.service.PrincebankComponent"%>
<%@page import="sitha.rupp.helper.ClsHelper"%>
<%@page import="sitha.rupp.service.UserListModel"%>
<%@page import="sitha.rupp.service.UserDa"%>
<%@page import="java.util.List"%>
<%@page import="sitha.rupp.service.AccountServices" %>
<%@page import="sitha.rupp.model.Account" %>
<%@page
	import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>

<%
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component=PrincebankComponent.getInstance();
	
	int USER_ID=component.USER_ID(request);
	int BRANCH_ID=component.BRANCH_ID(USER_ID);
	//-- If Allow to see all records for all branches --//
	/* if(component.isFullAccess(USER_ID)){
		BRANCH_ID=-1;
	} */
	//-- If Allow to see all records in branch --//
	/* if(component.isPartitalAccess(USER_ID)){
		USER_ID=0;
	} */
	AccountServices acService = new AccountServices();
	
	List<Account> acList=new ArrayList<Account>();
	//Account ac =new Account();
	
	acList=acService.getAllAccount();	
%>
<script src="assets/alert/dialogbox.js"></script>

<script src="assets/choosen/chosen.jquery.js" type="text/javascript"></script>
<div class="preloading" style="left: 55%;margin-right: -45%;position: absolute;margin: 0px;transfrom:translate(-50%,-50%); ">
    <img src="loading/img/processing.gif" width="" />
</div>
<div class="col-xs-12 content" style="margin-top:0px; display: none;" >
	<!-- <div style="background-color: #ccffff;height: 100px;"> </div> -->	
	
</div>
<p></p>
<fieldset id="getFromDB">
<table>
	<%
		for(int i=0;i<acList.size();i++){
			Account ac=acList.get(i);
	%>
	<tr><td>id</td><td><input type="text" value=<%out.print(ac.getId()); %> disabled="disabled" class="bg-warning"></input></td><td></td></tr>
	<tr><td>name</td><td><input type="text" value=<%out.print(ac.getName());%> disabled="disabled" class="bg-warning"></input></td><td></td></tr>

	<%}%>
</table>
</fieldset>
<p></p>
<fieldset id="morePolicy">
  <table>
    <tr>
      <td>PolicyNo</td>  
      <td><input type="text" id="txtPolh_polnum" name="txtPolh_polnum[]" class="txtPolh_polnum"/></td>  
      <td><label id="lblPolh_polnum" class="lblPolh_polnum"></label></td>  
    </tr>  
    <tr>
      <td>Name</td>  
      <td><input type="text" id="txtPolh_insured_name" name="txtPolh_insured_name[]" class="txtPolh_insured_name"/></td>  
      <td><label id="lblPolh_insured_name" class="lblPolh_insured_name"></label></td>  
    </tr>
    <tr>
      <td></td>  
      <td><input type="button" id="btnAddmore" name="btnAddmore" class="btnAddmore" value="+"/></td>  
      <td><input type="button" id="btnSavePolicy" name="btnSavePolicy" class="btnSavePolicy" value="SavePolicy"/></td>
    </tr>  
  </table>  
</fieldset>
<p></p>
<!-- add and remove jquery -->
<fieldset id="addRemove">
<div >
    <input type="text" class="txtName" name="txtName[]" placeholder="enter name"></input>
    <button id="btnAdd" class="btnAdd" name="btnAdd" value="Add">Add</button>
</div>
<div id="newRow"></div>

<div>
    <button id="btnSave" class="btnSave" name="btnSave" value="SAVE">SAVE</button>
</div>
<div id="result"></div>
</fieldset>

<div>
<p></p>
<p></p>
</div>
<script type="text/javascript" src="assets/insured-js/REP-UW2116.js"></script>
<fieldset id="validation">
<form id="formInput">
<p>https://www.youtube.com/watch?v=CaRZEdYRfaU&feature=youtu.be</p>
	<table>
		<tr><td>Name:</td><td><input type="text" class="form_text bg-warning" id="form_username"></td><td><span class="text-danger" id="err_name_message"></span></td></tr>
		<tr><td>Sex:</td><td><input type="text" class="form_text bg-warning" id="txt_sex"></td><td><span class="text-danger" id="err_sex_message"></span></td></tr>
		<tr><td></td><td><input type="button" id="btnSubmit" value="SaveOne"/></td><td></td></tr>	
	</table>
</form>
</fieldset>
<br><br><br>

<script type="text/javascript" src="assets/insured-js/REP-UW2116-M.js"></script>
<fieldset id="validation">
<legend>MUTIPLE INPUT FORM M-UW2116.JS</legend>
<form id="m_formInput">
<p>https://www.youtube.com/watch?v=CaRZEdYRfaU&feature=youtu.be</p>
	<table>
		<tr><td>Name:</td><td><input type="text" class="form_text bg-warning" id="mtxtName" name="mtxtName[]"></td><td><span class="text-danger" id="merr_name_message"></span></td></tr>
		<tr><td>Sex:</td><td><input type="text" class="form_text bg-warning" id="mtxtSex" name="mtxtSex[]"></td><td><span class="text-danger" id="merr_sex_message"></span></td></tr>
	</table>
	<div id="NewRow"></div>
	<div><input type="button" id="mbtnPlus" name="mbtnPlus" class="mbtnPlus" value="+"/></div>
	<div><input type="button" id="btnSaveAll" name="btnSaveAll" value="SAVEALL"/></div>
</form>
</fieldset>

<script type="text/javascript" src="assets/insured-js/REP-UW2116-VALIDATE.js"></script>
<fieldset class="form-group">
<legend>JQuery Validator .js</legend>
<div class="container">
      <!-- Horizonatal Form -->
      <div class="row">
        <div class="col-xs-6 col-xs-offset-1">
        <!-- <form id="form1" class="form-horizontal"> -->
          <form id="form1" class="form">
          <div><p>https://www.youtube.com/watch?v=TehnRqQtKv8&list=PL5ze0DjYv5DaAm5eC2chbTK1Y6uoTUtZ9&index=2</p></div>
            <div class="form-group">
              <label for="nameField" class="col-xs-2">Name</label>
              <div class="col-xs-10">
                <input type="text" class="form-control bg-warning" id="nameField" name="nameField" placeholder="Your Name" />
              </div>
            </div>
          
            <div class="form-group">
              <label for="emailField" class="col-xs-2">Email</label>
              <div class="col-xs-10">
                <input type="email" class="form-control" id="emailField" name="emailField" placeholder="Your Email" />
              </div>
            </div>
          
            <div class="form-group">
              <label for="phoneField" class="col-xs-2">Phone</label>
              <div class="col-xs-10">
                <input type="text" class="form-control" id="phoneField" name="phoneField" placeholder="Your Phone Number" />
              </div>
            </div>
          
            <div class="form-group">
              <label for="descField" class="col-xs-2">Description </label>
              <div class="col-xs-10">
                <textarea type="text" class="form-control" id="descField" name="phoneField" placeholder="Your Comments"></textarea>
              </div>
            </div>
            
            <div class="col-xs-10 col-xs-offset-2">
              <button type="submit" class="btn btn-primary" >Submit</button> <button type="reset" class="btn btn-default">Reset</button>
            </div>
            
          </form>
        </div>
      </div>
    </div>
</fieldset>




<!-- end add and remove jquery -->
<script type="text/javascript">

//===========================================

$('#btnAdd').click(function(){
    //alert('jquery.');
    // Add more control 
    let html=''; 
    html +='<div id="newInputRow">';
    html +='<input type="text" id="txtName" name="txtName[]" placeholder="enter sex"></input>';
    html +='<button id="btnRemove">Remove</button> ';
    html +='</div>';
    //console.log(html);
    $('#newRow').append(html);

    $(document).on('click','#btnRemove',function(){
        $(this).closest('#newInputRow').remove();
    });

});

//===========================================
//SAVE click
//===========================================
$('#btnSave').click(function(){
    //alert('SAVE DATA');
 //   let vName=$('#txtName').val();
    let r='';
    let inputs=document.getElementsByName('txtName[]');
    for(let i=0;i<inputs.length;i++){
        var inp=inputs[i];
        //alert('txtName['+i+'].value='+inp.value);
        r += inp.value +' ';
        console.log(r);
    }
    alert(r);
});
//===========================================
//END SAVE
//===========================================
</script>

<script type="text/javascript">
	$('#btnAddmore').click(function(){
		let f='<fieldset id="morePolicy2"><table><tr><td>PolicyNo</td><td><input type="text" id="txtPolh_polnum" name="txtPolh_polnum[]" class="txtPolh_polnum"/></td><td></td></tr><tr><td>Name</td><td><input type="text" id="txtPolh_insured_name" name="txtPolh_insured_name[]" class="txtPolh_insured_name"/></td><td><input type="button" id="btnRemoveOne" name="btnRemoveOne" class="btnRemoveOne" value="-"/></td></tr></table></fieldset>';
		$('#morePolicy').append(f);
	});

	$('#btnSavePolicy').click(function(){
		//alert('btnSavePolicy');
		let polh_polnum=$('.txtPolh_polnum').val();
		alert(polh_polnum);
	});
	
	$('#morePolicy').on('click','#btnRemoveOne',(function(){
		//alert('removeone');
		$(this).closest('#morePolicy2').remove();
	}));
</script>

<script src="assets/alert/dialogbox.js"></script>
<script type="text/javascript">
// START PREPAIR LOADING 
$(document).ready(function() {
	$('.preloading').fadeOut(500,function(){
		$('.content').fadeIn(500);
	});

	var h='<tr><td><input type="text" id="txtName2" placeholder="enter name"/></td><td><input type="button" id="remove" value="Remove" name="remove"/></td></tr>';
    $('#btnAddName').click(function(){
    	$('#t1').append(h);
    	//alert('hi +');
    });
    
    $('#t1').on('click','#remove',function(){
    	$(this).closest('tr').remove();
    	//alert('hi +');
    });
    
});
// END PREPAIR LOADING
</script>
<script type="text/javascript">
	function fnSave(){
		let v_name = $('#txtName2').val();
		alert(v_name);
		//$('#txtName').fadeToggle();
	}
	

    var hsex='<div id="div1_1"><input type="text" id="txtSex" placeholder="enter sex"/><input type="button" id="removeSex" value="removeSex" name="removeSex"/><div>';
    $('#btnAddSex').click(function(){
    	//alert('hi sex');
    	$('#div1').append(hsex);
    });
    
    $('#div1').on('click','#removeSex',function(){
    	//alert('hi remove sex');
    	$(this).closest('#div1_1').remove();
    })
    
</script>


<script type="text/javascript">
function userdailog(a){
	$.dialogbox({
		  type:'msg',
		  width:400,
		  title:'Information',
		  icon:1,
		  content:a,
		  btn:['Ok'],
		  call:[
		    function(){
		    	$.dialogbox.close(); 
		    }
		  ]
	});
}
</script>
<script>
/*datetime picker*/
$('.value_date_timepicker').datetimepicker({
       language:  'en',
       weekStart: 1,
       todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
   }); 
</script>