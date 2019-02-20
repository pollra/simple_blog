/**
 * 
 * 로그인 시도
 *
 */
function loginAction(){
    $.ajax({
        url: "/login",
        type: "post",
        data: JSON.stringify({"userId":$("#inputId").val(), "userPw":$("#inputPw").val()}),
        contentType: "application/json; charset=utf-8"
    }).done((result)=>{ // ok
        console.log("[!] login function complete.");
        if(result === "ok"){
            location.href="/";
        }
    }).fail((result)=>{
        console.log("[!] login function failed.");
        const error = JSON.parse(result.responseText);  // 날아온 JSON 텍스트 데이터를 JSON 객체로 변환해줌
        $("#inputPw").text("");
        if(error.code === 2001){
            alert(error.message);
        }
        $("#inputPw").focus();
    })
}

