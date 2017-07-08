/**
 * Created by czyczk on 2017-6-30.
 */

// 记录当前页面选中项目个数的整数
// Holds the number of items selected in this page
numItemsSelected = 0;
numItemsInTotal = 0;
var items;
var formError = false;

// For editing form
var oldItem;
var newItem;

// Register handlers on load
$(function() {
    // Query the servlet for items
    loadItems();
    // Hover on the right sidebar to reveal the labels
    $("#right-sidebar").hover(revealSidebarLabel, hideSidebarLabel);
    // Download button handler
    $("#download-button").click(handleDownloadButton);
    // Delete button handler
    $("#delete-button").click(handleDeleteButton);
    // Edit button handler
    $("#edit-button").click(handleEditButton);
    $('.update-submit').click(submitUpdate);
    // Bind
    bindProperties();
});

// Update numItemsInTotal. Invoked after querying for the total number of items (in loadItems()).
function updateNumItemsInTotal(data) {
    numItemsInTotal = data;
    console.info("[SQL query] Total number of item(s): " + data);
}

// Update items. Invoked after querying for items (in loadItems()).
function updateItems(data) {
    items = data;
    console.info("[SQL query] " + data.length + " item(s) returned.");
}

// Query the servlet for items
function loadItems() {
    // First query for the total number of movies
    $.ajax({
        url: "FileListGeneratorServlet",
        data: "requestType=count&mediaType=" + encodeURIComponent(mediaType),
        type: "post",
        async: false,
        success: updateNumItemsInTotal
    });
    // Then, query for items
    $.ajax({
        url: "FileListGeneratorServlet",
        data: sqlStatement,
        type: "post",
        async: false,
        success: updateItems
    });

    // Arrange the items onto the page
    arrangeItems();

    // Register a handler for items. If one item card is tapped, invoke the handler.
    if (items != undefined && items.length > 0)
        $("div.item-card").click(selectAnItem);
    // Reset right sidebar
    resetRightSidebar();
}

// The handler for item cards. In charge for toggle the tick mask and update the status of the right sidebar.
function selectAnItem() {
    // Find the mask (containing the visible checkbox) and toggle it
    var mask = $(this).find(".thumbnail-checkbox-mask").first();
    toggleMask(mask);

    // 找到这个项目的隐藏复选框，改变其勾选状态，维护 numItemsSelected 并更新侧边栏状态
    // Find the hidden checkbox of the item, toggle the checkbox, update numItemsSelected and the sidebar.
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

// Show/hide the tick mask for each item. Invoked by selectAnItem().
function toggleMask(mask) {
    // Change the class from "thumbnail-checkbox-mask-visible" to "thumbnail-checkbox-mask-invisible" or vice versa.
    if (mask.hasClass("thumbnail-checkbox-mask-visible")) {
        mask.removeClass("thumbnail-checkbox-mask-visible").addClass("thumbnail-checkbox-mask-invisible");
    } else {
        mask.removeClass("thumbnail-checkbox-mask-invisible");
        mask.addClass("thumbnail-checkbox-mask-visible");
    }
}

// The handler for the download button.
function handleDownloadButton() {
    // Collect all hidden checkboxes that are checked (of the selected items)
    $("input:checkbox[name='selected-items']:checked").each(function() {
        // For each such item, invoke triggerDownload with its deterministic property
        triggerDownload($(this).val());
    });
}

// The handler for the delete button.
function handleDeleteButton() {
    // Collect all hidden checkboxes that are checked (of the selected items)
    $("input:checkbox[name='selected-items']:checked").each(function() {
        // For each such item, invoke triggerDelete with its deterministic property
        triggerDelete($(this).val());
    });
}

// The handler for the edit button.
function handleEditButton() {
    // Collect all hidden checkboxes that are checked (of the selected items)
    $("input:checkbox[name='selected-items']:checked").each(function() {
        // For each such item, invoke triggerEdit with its deterministic property
        triggerEdit($(this).val());
    });
}


// Trigger download. Invoked by handleDownloadButton().
function triggerDownload(it) {
    // Movie: it == IMDB. Find the item by its IMDB. #IMDB -> .item-card
    // Other: it == SHA256. Find the item by its SHA256. #SHA256 -> .item-card
    var SHA256 = $('#'+it).find('.item-sha256').text();
    var size = $('#'+it).find('.item-size').text();
    var indicatedFilename = $('#'+it).find('.item-header').text();

    console.info("[Download request] indicatedFilename=" + indicatedFilename);
    // Open a new window and access DownloadServlet
    window.open("DownloadServlet?SHA256=" + SHA256 + "&size=" + size + "&indicatedFilename=" + indicatedFilename);
}

// Update success handler. Invoked by the Ajax request in submitUpdate() in each page.
function handleUpdateSuccess(data) {
    if (data.messageType == "success") {
        // Reload the content
        $('#property-form').modal('hide');
        loadItems();
    } else {
        // Alert error
        alert(data.message);
    }
}