
function deleteExamination(id){
    if(confirm('是否要删除题库!请仔细考虑!')){
    $.ajax({
        type: "post",
        url: "?action=deleteExamination",
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
function submitExamination(){
    var id=$('#id').val();
    var introduce=$('#introduce').val();
    var price=$('#price').val();
    var examTime=$('#examTime').val();
    var examinationName=$('#examinationName').val();
    var examinationTotalScore=$('#examinationTotalScore').val();
    var examinationJudgeScore=$('#examinationJudgeScore').val();
    var examinationSingleScore=$('#examinationSingleScore').val();
    var examinationMultScore=$('#examinationMultScore').val();
    var examinationJudgeUse=$('#examinationJudgeUse').val();
    var examinationSingleUse=$('#examinationSingleUse').val();
    var examinationMultUse=$('#examinationMultUse').val();
    var examinationSAQUse=$('#examinationSAQUse').val();
    var examinationChapterSize=$('#examinationChapterSize').val();
    var examinationChapterMaxScore=$('#examinationChapterMaxScore').val();
    var examinationChapterMinScore=$('#examinationChapterMinScore').val();
    $.ajax({
        type: "post",
        url: "?action=submitExamination",
        dataType: 'json',
        data: {
            "id": id,
            "introduce":introduce,
            "price":price,
            "examTime":examTime,
            "name": examinationName,
            "totalScore": examinationTotalScore,
            "judgeScore": examinationJudgeScore,
            "singleScore": examinationSingleScore,
            "multScore": examinationMultScore,
            "judgeUse": examinationJudgeUse,
            "singleUse":examinationSingleUse,
            "multUse": examinationMultUse,
            "saqUse": examinationSAQUse,
            "chapterSize": examinationChapterSize,
            "chapterMaxScore": examinationChapterMaxScore,
            "chapterMinScore": examinationChapterMinScore,
        },
        async: false,
        success: function (data) {
            if (data.ret_code == "1") {
                submitSuccessed(id,data.ret_resultId)
            } else {
                alert(data.ret_msg);
            }
        }
    });
}

function submitSuccessed(id,resultid){
    if(id==0){
            alert("题库提交成功！没有配套试题的话题库是不能运转的！请配置题目");
            managerQuestions(resultid)
        }else {
            alert("题库修改成功！")
            window.location.reload();
        }
    }

/***
 * 跳转到题目管理页面
 * @param id
 */
function managerQuestions(id){
    if(id==0){
        alert("请先配置题库再配置题库所属试题！")
    }else{
        window.location.href="/exam/questionManager.htm?id="+id;
    }
}

/***
 * 跳转到题目管理页面
 * @param id
 */
function managerChapters(id){
    if(id==0){
        alert("请先配置题库再配置题库出题方案！")
    }else{
        window.location.href="/exam/chapterExaminationManager.htm?id="+id;
    }
}

/***
 * 跳转到题目管理页面
 * @param id
 */
function makeExam(id){
    if(confirm("确定已经配置了题目及出题方案吗?")){
    if(id==0){
        alert("请先配置题库再生成试题")
    }else{
        var url="/home/mainPage.htm?id="+id;
        window.open(url)
    }
}
}
