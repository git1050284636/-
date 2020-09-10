var roleName = null;
var roleCode = null;
var saveBtn = null;
var backBtn = null;


$(function () {
    roleName = $("#roleName");
    roleCode = $("#roleCode");
    saveBtn = $("#save");
    backBtn = $("#back");

    roleName.next().html("*");
    roleCode.next().html("*");

    saveBtn.on("click", function () {
        if (confirm("是否确认要提交数据？")) {
            $("#userForm").submit();
        }
    });
    backBtn.on("click", function () {
        //alert("modify: "+referer);
        if (referer != undefined
            && null != referer
            && "" != referer
            && "null" != referer
            && referer.length > 4) {
            window.location.href = referer;
        } else {
            history.back(-1);
        }
    });
});