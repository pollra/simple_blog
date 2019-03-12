/**
 * 카테고리 리스트를 받아온다.
 */
$(document).ready(()=>{
    getCategoryList();
    loginCheck();
    $(".logoutBtn").attr("onclick","logout(); return false;")
})
function getCategoryList(){
    $.ajax({
        url:"/category",
        type:"get",
        contentType: "application/json"
    }).done((result)=>{
        $(".categoryContainer").html("");
        var data = "";
        data += categoryListCreate(result);
        $(".categoryContainer").html(data);
    }).fail((result) =>{
        alert(result);
        $(".categoryContainer").html("<li class='size0'>카테고리가 존재하지 않아요</li>");
    })
}

function categoryCheck(category, parentNum){
    var resultTF = false;
    $.each(category, (i, obj)=>{
        if(obj.parent === parentNum){
            console.log(`categoryCheck: {${obj.parent} : ${parentNum}} is TRUE.`);
            resultTF = true;
        }
    })
    return resultTF;
}
function categoryListCreate(result){
    var resultData = "";        //
    var size_0_category = [];
    var size_1_category = [];
    var size_2_category = [];
    var count1 = 0;
    var count2 = 0;
    var count3 = 0;
    // 카테고리 나누기
    $.each(result, (i, obj)=>{
        if(obj.level === 0){
            size_0_category[count1++] = obj;
        }
        if(obj.level === 1){
            size_1_category[count2++] = obj;
        }
        if(obj.level === 2){
            size_2_category[count3++] = obj;
        }
    });
    // console.log(`${count1} : ${count2} : ${count3}`);
    // 큰 카테고리 반복문
    $.each(size_0_category, (i, obj1)=>{
        // 큰 카테고리 선언
        resultData += `<li class="size${obj1.level} visible_${obj1.visible}" onclick="location.href='/category/${obj1.num}/lastpost'">${obj1.name}</li>`;
        // 중간 카테고리 체크
        if(size_1_category.length > 0) {
            // 중간카테고리중 위에서 표시한 큰 카테고리의 자식이 있는지 확인
            if (categoryCheck(size_1_category, obj1.num)) {
                resultData += "<ul>";
                // 중간카테고리 반복문
                $.each(size_1_category, (j, obj2) => {
                    // 중간 카테고리 존재여부 판단
                    if (obj2.parent === obj1.num) {
                        // 중간카테고리 선언
                        resultData += `<li class="size${obj2.level} visible_${obj2.visible}" onclick="location.href='/category/${obj2.num}/lastpost'">${obj2.name}</li>`;
                        // 작은 카테고리 체크
                        if (size_2_category.length > 0) {
                            // 작은 카테고리중 위에서 표시한 중간 카테고리의 자식이 있는지 확인
                            if (categoryCheck(size_2_category, obj2.num)) {
                                resultData += "<ul>"
                                // 작은 카테고리 반복문
                                $.each(size_2_category, (k, obj3) => {
                                    // 작은 카테고리 존재여부 판단
                                    if (obj3.parent === obj2.num) {
                                        // 작은 카테고리 선언
                                        resultData += `<li class="size${obj3.level} visible_${obj3.visible}" onclick="location.href='/category/${obj3.num}/lastpost'">${obj3.name}</li>`;
                                    }
                                })
                                resultData += "</ul>"
                            }
                        }
                    }else{
                        // console.log(`${obj1.num}의 중간카테고리가 존재하지 않습니다.`);
                    }
                })
                resultData += "</ul>"
            }else{
                // console.log(`categoryCheck(size_1_category, ${obj1.num}) is FALSE.`);
            }
        }
    })
    if(resultData ===""){
        return "<li class='size0'>큰 카테고리가 존재하지 않아요</li>";
    }
    return resultData;
}

/**
 * 로그인 관련 기능
 */
// 로그인 체크
function loginCheck(){
    $.ajax({
        url:"/login",
        type:"get"
    }).done((result)=>{
        if(result !== ""){
            console.log(`login id : ${result}`);
            $(".loginNik").text(result);
            $(".loginNik").attr("onclick","location.href='/user/info'");
            $(".logoutBtn").text("로그아웃");
            $(".logoutBtn").attr("onclick","logout(); return false;");
        }
    }).fail((errorObject)=>{
        const error = JSON.parse(errorObject.responseText);  // 날아온 JSON 텍스트 데이터를 JSON 객체로 변환해줌
        console.log(`${error.message}`);
        $(".loginNik").text("로그인");
        $(".loginNik").attr("onclick","location.href='/login/page'");
        $(".logoutBtn").text("");
        $(".logoutBtn").attr("onclick","#");
        console.log("[loginCheck] location.pathname.indexOf(\"/user\") : "+ location.pathname.indexOf("/user"));
        if(location.pathname.indexOf("/user")!== -1){
            location.href = "/login/page";
        }
    })
}
// 로그아웃 기능
function logout(){
    console.log("logout action start.");
    $.ajax({
        url: "/logout",
        type: "get"
    }).done((result)=>{
        console.log("logout complete.");
        console.log("[logout] location.pathname.indexOf(\"/user\") : "+ location.pathname.indexOf("/user"));
        if(!(location.pathname.indexOf("/user") === -1)){
            location.href = "/login/page";
        }
        $(".loginNik").text("로그인");
        $(".loginNik").attr("onclick","location.href='/login/page'");
        $(".logoutBtn").text("회원가입");
        $(".logoutBtn").attr("onclick","location.href='/signup'");
    }).fail(()=>{
        console.log("logout failed.");
        alert("로그아웃 실패.");
    })
}