function jumpToExamination(prepayId) {
    $("#page__title").html("确认付款中。。。")
    $.ajax({
        type: "post",
        url: "?action=payCheck",
        data: {
          "prepayId":prepayId
        },
        dateType: "text",
        success: function (msg) {
            console.log(msg);
            var rt = eval('(' + msg + ')');
            if (rt.ret_code == 0) {
                window.location.href="/home/preExamination.htm?id="+rt.examinationId;
            } else {
                alert("确认支付时出错！："+rt.ret_msg);
            }
        }
    })
}
