/**
 * Created by czyczk on 2017-6-30.
 */

// Fully/Partially show/hide the right sidebar. Invoked by selectAnItem() and resetRightSidebar().
function updateSidebar(trend) {
    var sidebar = $(".right-sidebar").first();
    if (trend == "+") {
        // 从 0 到 1 则显示侧边栏
        // From 0 to 1: Show the sidebar
        if (numItemsSelected == 1) {
            // Hover on the right sidebar to reveal the labels
            $("#right-sidebar").hover(revealSidebarLabel, hideSidebarLabel);
            sidebar.animate({
                opacity: 1.00,
                right: '-18rem'
            }, "fast");
        }
        // 从 1 加至更多则隐藏 Play 和 Edit 选项
        // From 1 to more: Hide "Play" and "Edit"
        else {
            sidebar.find("li").first().slideToggle("normal");
            sidebar.find("li").eq(3).slideToggle("normal");
        }
    } else if (trend == "-") {
        // 从更多减少至 1 则显示 Play 和 Edit 选项
        // From more to 1: Show "Play" and "Edit"
        if (numItemsSelected == 1) {
            sidebar.find("li").first().slideToggle("normal");
            sidebar.find("li").eq(3).slideToggle("normal");
        }
        // 从 1 减小至 0 则隐藏侧边栏
        // From 1 to 0: Hide the sidebar
        else if (numItemsSelected == 0) {
            // Unbind the hover handlers
            $('#right-sidebar').unbind('mouseenter mouseleave');
            sidebar.animate({
                opacity: 0.25,
                right: '-30rem'
            });
        }
    }
}

// Reset numItemsSelected and hide the right sidebar. Invoked if no items are available (in loadItems()).
function resetRightSidebar() {
    numItemsSelected = 0;
    updateSidebar("-");
}

// Reveal the labels in the right sidebar. Invoked by updateSidebar().
function revealSidebarLabel() {
    $(this).animate({
        right: '0'
    }, 150);
}

// Hide the labels in the right sidebar. Invoked by updateSidebar().
function hideSidebarLabel() {
    $(this).animate({
        right: '-18rem'
    });
}
