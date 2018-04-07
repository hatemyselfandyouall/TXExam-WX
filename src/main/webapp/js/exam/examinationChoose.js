function startExamination(examinationId,price,charged,chargeFinalTime,testTime){
    if(price==''||price==0.0){
        alert('免费试题无需付费')
        window.location.href='/home/mainPage.htm?id='+examinationId;
        return;
    }
    if(charged==0){
        if(confirm('您并未购买本题库的使用权,该题库价格为:'+price+',确认购买吗?')){
            payStart(examinationId)
        }
    }else {
        alert('您之前购买过此题库，请继续答题')
        if(testTime!=''&&testTime<3){
        alert('正式考试中是不能退出的，您已中断考试'+testTime+'次，中断3次将失去考试资格！')
            window.location.href="/home/mainPage.htm?id="+examinationId;
        }
        if(testTime!=''&&testTime>=3){
            alert('正式考试中是不能退出的，您已中断考试3次，失去考试资格！')
            clearPay(examinationId)
        }
    }
}

function checkPayType(){
    var wechatInfo = navigator.userAgent.match(/MicroMessenger\/([\d\.]+)/i) ;
    if( !wechatInfo ) {
        return false;
    } else if ( wechatInfo[1] < "5.0" ) {
        return false;
    }
    return true;
}


function payStart(examinationId){
    if(checkPayType()) {
        $.ajax({
            type: "post",
            url: "/home/payMoney.htm?action=prePayRequest",
            dataType: 'json',
            data: {
                "examinationId": examinationId,
                "payType":1,
            },
            async: false,
            success: function (data) {
                var rt = data;

                if (rt.ret_code == "0") {
                    alert(rt.ret_msg);
                }
                if (rt.ret_code == "1") {
                    window.location.href = '/home/payMoney.htm?examinationId=' + examinationId + "&prepay_id=" + rt.prepay_id;
                }
            }
        });
    }else {
        alert('非微信浏览器使用二维码支付，此处建议使用二维码支付，待定中')
        $.ajax({
            type: "post",
            url: "/home/payMoney.htm?action=prePayRequest",
            dataType: 'json',
            data: {
                "examinationId": examinationId,
                "payType":2,
            },
            async: false,
            success: function (data) {
                var rt = data;
                if (rt.ret_code == "0") {
                    alert(rt.ret_msg);
                }
                if (rt.ret_code == "1") {
                    window.location.href="/home/mainPage.htm?id="+examinationId;
                }
            }
        });
    }
}

function clearPay(examinationId){
        $.ajax({
            type: "post",
            url: "/home/payMoney.htm?action=clearPay",
            dataType: 'json',
            data: {
                "examinationId": examinationId,
            },
            async: false,
            success: function (data) {
                var rt = data;
                if (rt.ret_code == "0") {
                    alert(rt.ret_msg);
                }
                if (rt.ret_code == "1") {
                    window.location.reload()
                }
            }
        });

}