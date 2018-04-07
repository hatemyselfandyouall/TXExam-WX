
function editExamination(id,examinationId){
    if(confirm('确定要保存吗')){
    var judgeSize=$('#judgeSize'+id).val();
    var judgeUse=$('#judgeUse'+id).val();
    var singleSize=$('#singleSize'+id).val();
    var singleUse=$('#singleUse'+id).val();
    var multSize=$('#multSize'+id).val();
    var multUse=$('#multUse'+id).val();
    var saqSize=$('#saqSize'+id).val();
    var saqUse=$('#saqUse'+id).val();
    $.ajax({
        type: "post",
        url: "?action=editExamination",
        dataType: 'json',
        data: {
            "charpterId":id,
            "examinationId":examinationId,
            "judgeSize": judgeSize,
            "judgeUse": judgeUse,
            "singleSize": singleSize,
            "singleUse": singleUse,
            "multSize": multSize,
            "multUse": multUse,
            "saqSize": saqSize,
            "saqUse":saqUse,
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
}

function submitSuccessed(){
    alert('修改成功!')
    window.location.reload();
}