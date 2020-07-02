document.domain = 'localhost';

var uploadUrl = "http://localhost:8080/admin/upload";
var iframeWrapper;
var clickInput;

$(function() {
    initUpload();
});
function initUpload() {
    var a = '<div id="uploadIframeWrapper" style="position:absolute;z-index:1001;display:none;"><iframe src="' + uploadUrl + '" frameborder="0" id="uploadIframe" scrolling="no" width="100%" height="50"></iframe></div>';
    $(document.body).append(a);
    iframeWrapper = $("#uploadIframeWrapper");
    actionUpload()
}
function cancelUpload() {
    iframeWrapper.hide();
    if (clickInput != null) {
        $(clickInput).focus()
    }
}
function actionUpload() {
    $("input.needUpload").unbind("click").click(function() {
        startUpload(this)
    })
}
function startUpload(c) {
    clickInput = c;
    $(c).after(iframeWrapper);
    var d = $(c).position();
    var b = d.top - (40 - $(c).innerHeight()) / 2 + 30;
    var a = d.left - 8;
    var h = $(c).css("width");
    var w = parseInt(h.substring(0, h.length - 2)) + 16;
    iframeWrapper.css({
        top: b + "px",
        left: a + "px",
        width: w + "px",
    });
    iframeWrapper.show()
}
function uploadCallBack(a) {
    $(clickInput).val(a);
    $("#uploadIframe").attr("src", uploadUrl);
    iframeWrapper.hide()
};