/*限制上传文件大小*/
function fileSize(id, max, min){   //id为上传的input的ID值    max,min  为Int型正整数（方法内使用默认代表max MB）
    var file = $('#'+id)[0].files;
    if(file != undefined){
        if( file[0].size > max*1048576 || file[0].size< min*1048576 ){
            alert("上传文件过大，请重新上传！", backPage);
        }
        else{
            return true;
        }
    }
}

/**
 * Created by YS-YJJ-1456 on 2017/7/14.
 */
// 上传关系excel
function openAlertSuccess(obj) {    //上传文件改变触发事件
    //fileSize('pdfFile',5,0);   //调用上面限制文件大小试一下
    if(fileSize(obj,5,0)){
        var formData = new FormData($("#importPdf")[0]);
        var id = $("#id").val();
        $.ajax({
            type: "post",
            url: "?action=uploadQuestions&id="+id,
            data: formData,
            async: false,
            cache: false,
            processData: false,
            // 告诉jQuery不要去设置Content-Type请求头
            contentType: false,
            dateType: "text",
            success: function (msg) {
                console.log(msg);
                var rt = eval('(' + msg + ')');
                if (rt.ret_code == 0) {
                    alert("上传成功",backPage2(id));
                } else {
                    alert(rt.msg);
                }
            }
        })
    }
}

// 保存pdf信息
function save() {
    $.ajax({
        type: "post",
        url: "?action=save",
        data: $('#save').serialize(),
        dateType: "text",
        success: function (msg) {
            console.log(msg);
            var rt = eval('(' + msg + ')');
            if (rt.ret_code == 0) {
                alert("上传成功", backPage);
            } else {
                alert(rt.msg);
            }
        }
    })
}
function backPage() {
    window.location.href="/admin/assay/singleUploadReport.htm?action=sampleList";
}
function getImg(){
    $("#auditer").html("<img class='sign-name' src='/img/bsAuditerName.png'>");
}

function save1() {
    $.ajax({
        type: "post",
        url: "?action=save",
        data: $('#save').serialize(),
        dateType: "text",
        success: function (msg) {
            console.log(msg);
            var rt = eval('(' + msg + ')');
            if (rt.ret_code == 0) {
                alert("上传成功", backPage1);

            } else {
                alert(rt.msg);
            }
        }
    })
}
function backPage1() {
    window.location.href="/admin/assay/batchImport.htm";
}

function backPage2(id) {
    window.location.reload();
    }
function deleteQuestion(id) {
    if (confirm('是否要删除试题!请仔细考虑!')) {
        $.ajax({
            type: "post",
            url: "?action=deleteQuestion",
            dataType: 'json',
            data: {
                "id": id,
            },
            async: false,
            success: function (data) {
                if (data.ret_code == "1") {
                    alert(data.ret_msg);
                    window.location.reload();
                } else {
                    alert(data.ret_msg);
                }
            }
        });
    }

}
function submitQuestion(id,examinationId) {
    var content = $('#questionContent').val();
    var questionType = $('#questionType').val();
    var choice = $('#questionChoice').val();
    var answerChoice = $('#questionAnswerChoice').val();
    var answerDetail = $('#questionAnswerDetail').val();
    var score = $('#questionScore').val();
    var chapterId = $('#questionChapterId').val();
    var hardRank = $('#questionHardRank').val();
    var isReal = $('#questionIsReal').val();
    $.ajax({
        type: "post",
        url: "?action=editQuestion",
        dataType: 'json',
        data: {
            id:id,
            examinationId:examinationId,
            "content":content,
            "questionType":questionType,
            "choice": choice,
            "answerChoice": answerChoice,
            "answerDetail": answerDetail,
            "score": score,
            "chapterId": chapterId,
            "hardRank": hardRank,
            "isReal": isReal
        },
        async: false,
        success: function (data) {
            if (data.ret_code == "1") {
                submitSuccessed(id,examinationId)
            } else {
                alert(data.ret_msg);
            }
        }
    });
}

function submitSuccessed(id,examinationId) {
        alert("题目提交成功");
        if (id==""){
            window.location.href="http://localhost:8080/exam/questionManager.htm?id="+examinationId;
        }else {
        window.location.reload();
        }
}