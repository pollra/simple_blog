$(document).ready(function () {
    getGBookList();
});
var pagePath = document.location.href;
// 방명록 작성
var inputGBook = function () {
    $.ajax({
        url:"/gbook",
        data:JSON.stringify({"content":$("#guestBookContent").val()}),
        type: "post",
        contentType: "application/json"
    }).done(function (result) {
        console.log("msg: " + result);
        $("#guestBookContent").val("");
        getGBookList();
    }).fail(function (result) {
        console.log('error: '+JSON.parse(result.responseText).message);
        alert("방명록 작성에 실패했어요");
    })
};
// 방명록 불러오기
var getGBookList = function () {
    var data = "";
    $.ajax({
        url:"/gbook",
        type:"get",
        contentType: "application/json; charset=utf-8",
        dataType:'json'
    }).done(function (result) {
        $("#guestBook").html("");
        $.each(result, function (i, obj) {
            data += "<div class='division item'>" +
                "<li class='content'>"+obj.contents+"</li>" +
                "<li class='user'>"+obj.writer+"</li>" +
                "<li class='time'>"+obj.time+"</li>" +
                "<li class='btn'>" +
                "<button value='"+obj.num+"' class='uBtn'>수정</button>"+
                "<button value='"+obj.num+"' class='dBtn'>삭제</button>"+
                "</li>"+
                "</div>"
        })
        $("#guestBook").html(data);
    }).fail(function (result) {
        $("#guestBook").html("");
        console.log("[!] error: " +JSON.parse(result.responseText).message);
        data += "<div class='noData'>" +
            "<h3>아직 작성된 방명록이 없어요...</h3>" +
            "</div>";
        $("#guestBook").html(data);
    })
};

// 방명록 삭제
var deleteGBook = function (num) {
    if(confirm("정말 삭제하시겠습니까?")) {
        $.ajax({
            url: "/gbook",
            data: JSON.stringify({"gbookNum": num}),
            type: "delete",
            contentType: "application/json; charset=utf-8"
        }).done(function (result) {
            console.log(result);
            getGBookList();
        }).fail(function (result) {
            console.log("delete Error: " + JSON.parse(result.responseText).message);
            alert("삭제실패")
        })
    }
};
$(document).on("click", ".dBtn",function () {
    deleteGBook($(this).attr("value"));
});

/**
 * 방명록 수정 관련 기능
 */
// 방명록 수정 요청
var updateGBook = function (num, content) {
    if(confirm("정말 수정하시겠습니까?")) {
        $.ajax({
            url: "/gbook",
            data: JSON.stringify({"gbookNum": num, "gbookContent": content}),
            type: "put",
            contentType: "application/json; charset=utf-8"
        }).done(function (result) {
            console.log("방명록 업데이트 성공: " + result);
            $("#guestBookContent").val("");
            getGBookList();
        }).fail(function (result) {
            alert("권한이 없습니다.");
            console.log("방명록 업데이트 실패: " + JSON.parse(result.responseText).message);
        })
    }
};

// 수정버튼 누르면 작동
$(document).on("click", ".uBtn",function () {
    var content = $(this).parent().siblings().filter(".content").text();
    var num = $(this).attr("value");
    $("#guestBookContent").val(content);
    $("#guestBookContent").focus();
    $("#guestBookTarget").val(num);
    gbookBtnChange("update");
});
// 방명록 작성 버튼 체인지
function gbookBtnChange (type){
    if(type === "update") {
        $("#guestBookBtn").css("background", "lightcoral");
        $("#guestBookBtn").text("방명록 수정");
        $("#guestBookBtn").attr("onclick", "gbookUpdateBtn()");
        // input 태그에서 엔터키 눌렀을때의 기능 변경
        $("#guestBookContent").attr("onkeypress", "if(event.keyCode==13) {gbookUpdateBtn(); return false;}");
    }
    if(type === "input"){
        $("#guestBookBtn").css("background", "burlywood");
        $("#guestBookBtn").text("방명록 작성");
        $("#guestBookBtn").attr("onclick", "inputGBook()");
        // input 태그에서 엔터키 눌렀을때의 기능 변경
        $("#guestBookContent").attr("onkeypress", "if(event.keyCode==13) {inputGBook(); return false;}");
    }
}

// 방명록 수정버튼이 눌림
var gbookUpdateBtn = function () {
    var content = $("#guestBookContent").val();
    var num = $("#guestBookTarget").val();
    console.log("num: "+num);
    console.log("content: "+content);
    updateGBook(num, content);
    gbookBtnChange("input");
}