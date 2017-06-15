// 记录当前页面选中项目个数的整数
// Holds the number of items selected in this page
numItemsSelected = 0;

// jquery
$(function() {
	// Register a handler for items.
	// If the item card is tapped, invoke the handler.
	$("div.tag").click(selectAnItem);
	// Change the style after tapping a content item
//	$('.tag').click(function(e){
//		if($(this).hasClass('glass')){
//			$(this).removeClass('glass');
//		}else{
//			$(this).addClass('glass');
//		}
//	})
});

$('#contentRight input:radio').click(function(){
//   alert('sss');
	var $radio = $(this);
	//if this was previously checked
	if($radio.data('waschecked') == true) {
		$radio.prop('checked', false);
		$radio.data('waschecked', false);
		$(".sidebar").animate({
	      	right: '-100px'
    	});
	} else {
		$radio.prop('checked', true);
		$radio.data('waschecked', true);
		$(".sidebar").animate({
	      	right: '0px'
    	});
	}
	
});

// The handler for item cards
function selectAnItem() {
	// Find the mask (containing the visible checkbox) and toggle it
	var mask = $(this).find(".thumbnail-checkbox-mask").first();
	toggleMask(mask);
	
	// 找到这个项目的隐藏复选框，改变其勾选状态，维护 numItemsSelected 并更新侧边栏状态
	// Find the hidden checkbox of the item, toggle the checkbox, update numItemsSelected and the sidear
	var hiddenCb = $(this).find(".item-checkbox").first();
	if (hiddenCb.prop("checked")) {
		hiddenCb.prop("checked", false);
		numItemsSelected--;
		updateSidebar("-");
	} else {
		hiddenCb.prop("checked", true);
		numItemsSelected++;
		updateSidebar("+");
	}
}

function updateSidebar(trend) {
	var sidebar = $(".sidebar").first();
	if (trend == "+") {
		// 从 0 到 1 则显示侧边栏
		if (numItemsSelected == 1) {
			sidebar.animate({
	      	right: '0px'
    		});
		}
		// 从 1 加至更多则隐藏 Play 选项
		else {
			sidebar.find("li").first().hide("normal");
		}
	} else if (trend == "-") {
		// 从更多减少至 1 则显示 Play 选项
		if (numItemsSelected == 1) {
			sidebar.find("li").first().show("normal");
		}
		// 从 1 减小至 0 则隐藏侧边栏
		else if (numItemsSelected == 0) {
			sidebar.animate({
	      	right: '-100px'
    		});
		}
	}
}

function toggleMask(mask) {
	if (mask.hasClass("thumbnail-checkbox-mask-visible")) {
		mask.removeClass("thumbnail-checkbox-mask-visible").addClass("thumbnail-checkbox-mask-invisible");
	} else {
		mask.removeClass("thumbnail-checkbox-mask-invisible");
		mask.addClass("thumbnail-checkbox-mask-visible");
	}
}
