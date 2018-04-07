var hammer = '';
var currentIndex = 0;
var body_width = $('body').width();
var body_height = $('body').height();

$("#clipArea").photoClip({
    width: body_width * 0.5125,
    height: body_width * 0.5125,
    file: "#file",
    view: "#hit",
    ok: "#clipBtn",
    loadStart: function () {
        //console.log("照片读取中");
        $('.lazy_tip span').text('');
        $('.lazy_cover,.lazy_tip').show();
    },
    loadComplete: function () {
        //console.log("照片读取完成");
        $('.lazy_cover,.lazy_tip').hide();
    },
    clipFinish: function (dataURL) {
        $('#hit').attr('src', dataURL);
        saveImageInfo();
    }
});

//图片上传
function saveImageInfo() {
    var filename = $('#hit').attr('fileName');
    var img_data = $('#hit').attr('src');
    if(img_data==""){alert('null');}
    var type = filename.toString().split(".")[filename.toString().split(".").length - 1];
    if (type == "jpg" || type == "jpeg"||type == "png"||type == "JPG" || type == "JPEG"||type == "PNG") {
        var imgId= $('#imgId').val();
        var calligraphyId= $('#calligraphyId').val();

        $.post("image.htm?action=uploadImage", {image: img_data,imgId:imgId,type:type,calligraphyId:calligraphyId}, function () {
            window.location.href="image.htm?action=compareResult";
        });
    }else{
        alert("该文件为" + type + ",必须为图像文件");
    }



}

/*获取文件拓展名*/
function getFileExt(str) {
    var d = /\.[^\.]+$/.exec(str);
    return d;
}

//图片上传结束
$(function () {
    $('#upload2').on('click', function () {
        //图片上传按钮
        $('#file').click();
    });
})


function Close(){$('#plan').hide();}

function changeImage(){
    alert("两指旋转即可翻转图片!");
}

