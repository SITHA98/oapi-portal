/**
 * 
 */
function decimalFilter(regex,obj){
    var containsNonNumeric = obj.value.match(regex);
    if (containsNonNumeric)
    	obj.value = obj.value.replace(regex, '');
}

//for tab
$('.tab-panels .tabs li').on('click',function(){
	var className = $(this).attr('class');
	if(className=='active'){
		return;
	}
	var $panel=$(this).closest('.tab-panels')
	$panel.find('.tabs li.active').removeClass('active');
	$(this).addClass('active');
	var panelToShow=$(this).attr('rel');
	
	//$panel.find('.panel.active').slideUp(100,showNexPanel);
	$panel.find('.panel.active').hide(0,showNexPanel);
	function showNexPanel(){
		$(this).removeClass('active');
		//$('#'+panelToShow).slideDown(100,function(){
		$('#'+panelToShow).show(0,function(){
			$(this).addClass('active');
		});
	}
});
//for tab