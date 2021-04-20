/**
 * Show alert/error message
 * @param msg
 */
function showError(msg) {
    var target = $("div.alert span");
    target.text(msg);
    $("div.alert").removeClass("hidden");
}

/**
 * Show error testmessage in the container
 * @param container
 * @param msg
 */
function showMessage(container, msg) {
    container.empty();
    container.append("<p>"+msg+"</p>");
}

/**
 * Hide alert message on click of close
 */
$("div.alert a.close").click(function () {
    $("div.alert").toggleClass("hidden");
})
