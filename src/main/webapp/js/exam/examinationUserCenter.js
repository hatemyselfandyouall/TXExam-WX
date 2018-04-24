function continueExam(examinationId,testCount,id) {
    if(confirm('您之前购买过此题库，要继续答题吗？')){
        $.ajax({
            type: "post",
            url: "examinationusercenter.htm?action=addTestCount",
            dataType: 'json',
            data: {
                "id": id,
            },
            async: false,
            success: function (data) {
                var rt = data;
                if (rt.ret_code == "1") {
                    window.location.href="/home/mainPage.htm?id="+examinationId;
                }else {
                    alert("未知错误")
                }
            }
        });
    }
}