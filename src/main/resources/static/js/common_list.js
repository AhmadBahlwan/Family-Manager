function clearFilter() {
    window.location = moduleURL;
}

function showDeleteConfirmModal(link, entityName) {
    entityId = link.attr("entityId");

    $("#yesButton").attr("href", link.attr("href"));
    $("#confirmText").text("هل انت متاكد انك تريد حذف هذا" + entityName + "ID " + entityId + "?");

    $("#confirmModal").modal("show");
}