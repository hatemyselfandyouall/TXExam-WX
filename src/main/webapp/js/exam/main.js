var hour;
var minute;
var second=0;
var remainMinute;
var remainHour;
$(function(){
    minute=$("#totalTime").val();
    console.log("总时间"+minute);

    initTime();
    initExam();
    //statisScore();
});

function initExam(){
    $('.topbox').css('display','none');
    $('.test:first').css('display','block');
}

function initTime(){
    second=second;
    hour=parseInt(minute/60);
    minute=minute%60;
    remainMinute=$("#remainTime").val();
    remainHour=parseInt(remainMinute/60);
    remainMinute=remainMinute%60;
    var initTime=time2Str(hour)+':'+time2Str(minute)+':'+time2Str(second);
    var remainTime=time2Str(remainHour)+':'+time2Str(remainMinute)+':'+time2Str(second);
    $('.initTime').html(initTime);
    $('.restTime').html(remainTime);
}
function time2Str(time){
    if(time<10){
        time='0'+time;
    }
    return time;
}

var timer = $.timer(function() {
    changeTime()
});timer.set({ time : 1000, autostart : true })

function changeTime(){
    if(second>0){
        second=second-1;
    }else{
        second=59;
        if(remainMinute>0){
            remainMinute=remainMinute-1;
        }else{
            remainMinute=59;
            if(remainHour>0){
                remainHour=remainHour-1;
            }else{
                alert('时间到!')
                $('#timeUp').val(1)
                finalPage();
                return;
            }
        }
    }
    var restTime=time2Str(remainHour)+':'+time2Str(remainMinute)+':'+time2Str(second);
    $('.restTime').html(restTime);
}


function sleep(temp,title) {
    var timer = $.timer(function() {
        console.log($('#timeUp').val())
        if($('#timeUp').val()==0) {
            $(temp).parents('div').css('display', 'none');
            $(title).parents('div').next('div').css('display', 'block');
        }
    });
    timer.once(1000);
}

function nextQuestion(examId,sort,type){
//        var answer=$('li .on');
    sort=sort+1;
    var select='';
    switch (type){
        case '1':
            select=getjudgeAnswer(select);
            break;
        case '2':
            select=getsingleAnswer(select);
            break;
        case '3':
            select=getmultAnswer(select);
            break;
        case '4':
            alert('简答题方案设计中')
            return;
    }
    console.log(select)
    if(select==''){
        if(!confirm('该题未回答！你确定要到下一题吗')){
            return;
        }
    }
    $.ajax({
        type: "post",
        url: "?action=upLoadAnswer",
        dataType: 'json',
        data: {
            "id": examId,
            "questionSort":sort,
            "questionType":type,
            "answer":select,
            "answersort":(sort-1)
        },
        async: false,
        success: function (data) {
            var rt =data;
            if (rt.ret_code == "5") {
                finalPage();
            }
            if (rt.ret_code == "4") {
                window.location.href='?id='+examId+'&questionSort='+sort+'&questionType='+type+'&answer='+select+'&answersort='+(sort-1);
            }
            if (rt.ret_code == "3") {
                window.location.reload();
            }
            if (rt.ret_code == "2") {
               if(confirm(rt.ret_msg)){
                   finalPage();
               }
            }
            if (rt.ret_code == "1") {
                window.location.href='?id='+examId+'&questionSort='+sort+'&questionType='+type+'&answer='+select+'&answersort='+(sort-1);
            }
        }
    });
    //window.location.href='?id='+examId+'&questionSort='+sort+'&questionType='+type+'&answer='+select+'&answersort='+(sort-1);
}
function finalPage(){
    var examinationId=$('#examinationId').val();
    window.location.href='?action=finalPage&id='+examinationId;
}
