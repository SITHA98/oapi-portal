<!--Author      : @arboshiki-->
<%@page import="sitha.rupp.model.MTMenu"%>
<%@page import="sitha.rupp.model.MTGroupDetail"%>
<%@page import="sitha.rupp.service.GroupDa"%>
<%@page import="org.bouncycastle.asn1.gnu.GNUObjectIdentifiers"%>
<%@page import="java.util.List"%>
<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<% 
ApplicationContext context=new ClassPathXmlApplicationContext("Spring_Bean.xml");
	GroupDa gr_da=(GroupDa)context.getBean("groupDa");	
	/* MenuModel me=me_da.getBranchInfo(Integer.parseInt(brId)); */	
	String grId = (String) request.getParameter("id");
	/* out.print("Cust ID:"+custId); */
	if(grId==null){
		grId="0";
	}
	String grname="";
	/*grId="27";*/
	List<MTGroupDetail> gdn=gr_da.getGroupDetail(Integer.parseInt(grId));
	for(int a=0;a<gdn.size();a++){
		MTGroupDetail gdna=(MTGroupDetail)gdn.get(a);
		grname=gdna.getGrName();
	}
	
%>

<link href="assets/role/css/abixTreeList.css" rel="stylesheet">
<script src="assets/alert/dialogbox.js"></script>
<script>
// START PREPAIR LOADING 
$(document).ready(function() {
	$('.preloading').fadeOut(500,function(){
		$('.content').fadeIn(500);
	});
} );
// END PREPAIR LOADING
</script>
	
    <div class="toppanel">
    	<button id="group-role_btn_save_menu" style="height: 35px;width: 100px;">
        	<i class="fa fa-save"></i> Save
        </button>
    </div>   

<input type="hidden" id="grroletxtuserId" name="grroletxtuserId" value="<%out.print(session.getAttribute("PRINCE_USER_ID")); %>" />
<input type="hidden" id="grroletxtgr_Id" name="grroletxtgr_Id" value="<%out.print(grId); %>" />
<div class="preloading" style="left: 55%;margin-right: -45%;position: absolute;margin: 0px;transfrom:translate(-50%,-50%); ">
    <img src="loading/img/processing.gif" width="" />
</div>
<div class="col-xs-12 content" style="margin-top:0px; display: none;" >	
<table>
	<tr>
		<td align="right">Role description <span class="danger">*</span> : </td>
		<td>
			<input type="text" style="width: 100%;" value="<%out.print(grname); %>" id="grroletxtGrName" name="grroletxtGrName"  />
		</td>
	</tr>
	<script type="text/javascript" src="assets/role/jquery-checktree.js"></script>
	<tr>
		<td></td>
		<td style="font-size: 16px;">
			<%  List<MTMenu> menu=gr_da.getMenuall();
           List<MTGroupDetail> gd=gr_da.getGroupDetail(Integer.parseInt(grId));
           int user=1;
           String menustr="<ul class=\"tree\" id=\"tree\">";
           for(int i=0;i<menu.size();i++){
        	   MTMenu mel=(MTMenu)menu.get(i);
           	if(mel.getMenu_main()==0){			            		
           		menustr+="<li>";
           				menustr+="<input type=\"checkbox\" " ;
          				for(int a=0;a<gd.size();a++){
          					MTGroupDetail gda=(MTGroupDetail)gd.get(a);
          					if(mel.getMenu_id()==gda.getMenuId()){
          						menustr+=" checked=\"checked\" ";
          						break;
          					}
          				}
          				menustr+=" value=\""+mel.getMenu_id()+"\" name=\"chMenu\" id=\""+mel.getMenu_id()+"\" /> ";
          				menustr+="<label for=\""+mel.getMenu_id()+"\">"+mel.getMenu_description()+"</label>";
           		List<MTMenu> menus=gr_da.getMenu(mel.getMenu_id());
           		if(menus.size()>0){
           			menustr+="<ul>";
           			for(int j=0;j<menus.size();j++){            				
           				MTMenu mes=(MTMenu)menus.get(j);
               			menustr+="<li><input type=\"checkbox\" ";
               			for(int a=0;a<gd.size();a++){
               				MTGroupDetail gda=(MTGroupDetail)gd.get(a);
           					if(mes.getMenu_id()==gda.getMenuId()){
           						menustr+=" checked=\"checked\" ";
           						break;
           					}
           				}
               			menustr+=" value=\""+mes.getMenu_id()+"\"  name=\"chMenu\" id=\""+mes.getMenu_id()+"\" /> ";
               			menustr+="<label for=\""+mes.getMenu_id()+"\">"+mes.getMenu_description()+"</label>";
               			List<MTMenu> menuss=gr_da.getMenu(mes.getMenu_id());                			
               			if(menuss.size()>0){
               				menustr+="<ul>";
               				for(int k=0;k<menuss.size();k++){
               					MTMenu mess=(MTMenu)menuss.get(k);
               					menustr+="<li><input type=\"checkbox\" ";
               					for(int a=0;a<gd.size();a++){
               						MTGroupDetail gda=(MTGroupDetail)gd.get(a);
	            					if(mess.getMenu_id()==gda.getMenuId()){
	            						menustr+=" checked=\"checked\" ";
	            						break;
	            					}
	            				}
               					menustr+=" value=\""+mess.getMenu_id()+"\"  name=\"chMenu\" /> ";                					
               					menustr+="<label for=\""+mess.getMenu_id()+"\">"+mess.getMenu_description()+"</label>";
               					List<MTMenu> menusss=gr_da.getMenu(mess.getMenu_id());
               					if(menusss.size()>0){
               						menustr+="<ul>";
               						for(int l=0;l<menusss.size();l++){
               							MTMenu messs=(MTMenu)menusss.get(l);
               							menustr+="<li><input type=\"checkbox\" ";
               							for(int a=0;a<gd.size();a++){
               								MTGroupDetail gda=(MTGroupDetail)gd.get(a);
			            					if(messs.getMenu_id()==gda.getMenuId()){
			            						menustr+=" checked=\"checked\" ";
			            						break;
			            					}
			            				}
               							menustr+=" value=\""+messs.getMenu_id()+"\"  name=\"chMenu\" id=\""+messs.getMenu_id()+"\" /> ";
               							menustr+="<label for=\""+messs.getMenu_id()+"\">"+messs.getMenu_description()+"</label>";
               							menustr+="</li>";
               						}
               						menustr+="</ul>";
               					}
               				}
               				menustr+="</li></ul>";
               			}                			
               		}
           			menustr+="</li></ul>";
           		}
           	}
        }
    menustr+="</li></ul>";%>
<%out.print(menustr);%>	
		</td>
	</tr>		
</table>
<div style="border: 1px #f9f9f9 solid;margin: 20px 0px;"></div>
<div style="margin: 20px 0px;">
	<table>
		<tr>
			<td align="right">Created by : </td>
			<td>
				<input type="text" />
			</td>
			<td style="width: 10%;"></td>
			<td align="right">Authorized by : </td>
			<td>
				<input type="text" />
			</td>
		</tr>
		<tr>
			<td align="right">Created date : </td>
			<td>
				<input type="text" />
			</td>
			<td style="width: 10%;"></td>
			<td align="right">Authorized date : </td>
			<td>
				<input type="text" />
			</td>
		</tr>
	</table>
</div>
</div>
<script>
$('#tree').checktree();
</script>
<script src="assets/alert/dialogbox.js"></script>
<script type="text/javascript">

 	$('#group-role_btn_save_menu').click(function(e){
 		/*alert('hello world.');
 		return false;*/
 		if(($('#grroletxtGrName').val()).trim()==""){
 			Alertx('Please input group')		
 			return false;
 		} 	
 		if (document.querySelectorAll("input[name=chMenu]:checked").length < 1) {
 			Alertx('Please choose menu')
 			return false;
 		}
 		var selected = new Array();
		$("input:checkbox[name=chMenu]:checked").each(function(){
	    	selected.push($(this).val());
		});		
		//alert($('#grroletxtGrName').val());
		var data=""; 
		/* alert(JSON.stringify(selected));
		return false; */
		
		data=JSON.stringify(selected);
		//alert('Call :==>'+data);
 		$.ajax({
			cache: false,
	        url : "${pageContext.request.contextPath}/group/insertGroupDetail",
	        type : "POST",
	        data:{
	        	txtgr_id:$('#grroletxtgr_Id').val(),
	        	txtGrName:$('#grroletxtGrName').val(),
	        	txtgrId:data,
	        	userId:$('#grroletxtuserId').val()
	        },
	        //contentType: "application/json; charset=utf-8",
	        //dataType: "json",	       
	        success : function(data){
	        	Successx('Success');
	        },
	        error : function(data) {
	        	Errorx('Error : '+data);
	        }
		});		
	});
 </script>
<style type="text/css"> 
.tree,
.tree ul {
  margin:0 0 0 1em; /* indentation */
  padding:0;
  list-style:none;
  color:#015494;
  position:relative;
}

.tree ul {margin-left:.5em} /* (indentation/2) */

.tree:before,
.tree ul:before {
  content:"";
  display:block;
  width:0;
  position:absolute;
  top:0;
  bottom:0;
  left:0;
  border-left:1px solid;
}

.tree li {
  margin:0;
  padding:0 1.5em; /* indentation + .5em */
  line-height:2em; /* default list item's `line-height` */
  font-weight:bold;
  position:relative;
}

.tree li:before {
  content:"";
  display:block;
  width:10px; /* same with indentation */
  height:0;
  border-top:1px solid;
  margin-top:-1px; /* border top width */
  position:absolute;
  top:1em; /* (line-height/2) */
  left:0;
}

.tree li:last-child:before {
  background:white; /* same with body background */
  height:auto;
  top:1em; /* (line-height/2) */
  bottom:0;
}
</style>
<script type="text/javascript">
function Alertx(content){
	$.dialogbox({
		  type:'msg',
		  width:450,
		  title:'Information',
		  icon:1,
		  content:content,
		  btn:['OK'],
		  call:[
		    function(){
		      $.dialogbox.close(); 
		    }
		  ]
		});
}
function Successx(content){
	$.dialogbox({
		  type:'msg',
		  width:450,
		  title:'Success',
		  icon:1,
		  content:content,
		  btn:['OK'],
		  call:[
		    function(){
		      $.dialogbox.close(); 
		    }
		  ]
		});
}
function Errorx(content){
	$.dialogbox({
		  type:'msg',
		  width:450,
		  title:'Error',
		  icon:1,
		  content:content,
		  btn:['OK'],
		  call:[
		    function(){
		      $.dialogbox.close(); 
		    }
		  ]
		});
}
</script>