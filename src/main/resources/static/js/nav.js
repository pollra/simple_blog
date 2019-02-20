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
    $.each(category, (i, obj)=>{
        if(obj.parent === parentNum){
            return true;
        }
    })
    return false;
}
function categoryListCreate(result){
    var resultData = "";        //
    var size_0_category = [];
    var size_1_category = [];
    var size_2_category = [];
    // 카테고리 나누기
    $.each(result, (i, obj)=>{
        if(obj.level === 0){
            size_0_category[i] = obj;
        }
        if(obj.level === 1){
            size_1_category[i] = obj;
        }
        if(obj.level === 2){
            size_2_category[i] = obj;
        }
    })
    console.log(`${size_0_category.length} : ${size_1_category.length} : ${size_2_category.length}`)
    // 큰 카테고리 반복문
    $.each(size_0_category, (i, obj1)=>{
        // 큰 카테고리 선언
        resultData += "<li class='size"+obj1.level+"' onclick='location.href=\""+obj1.url+"\"'>"+obj1.name+"</li>";
        // 중간 카테고리 체크
        if(size_1_category.length > 0) {
            // 중간카테고리중 위에서 표시한 큰 카테고리의 자식이 있는지 확인
            if (categoryCheck(size_1_category, size_0_category.num)) {
                resultData += "<ul>"
                // 중간카테고리 반복문
                $.each(size_1_category, (j, obj2) => {
                    // 중간 카테고리 존재여부 판단
                    if (obj2.parent === obj1.num) {
                        // 중간카테고리 선언
                        resultData += "<li class='size" + obj2.level + "' onclick='location.href=\""+obj2.url+"\"'>" + obj2.name + "</li>";
                        // 작은 카테고리 체크
                        if (size_2_category.length > 0) {
                            // 작은 카테고리중 위에서 표시한 중간 카테고리의 자식이 있는지 확인
                            if (categoryCheck(size_2_category, size_1_category.num)) {
                                resultData += "<ul>"
                                // 작은 카테고리 반복문
                                $.each(size_2_category, (j, obj3) => {
                                    // 작은 카테고리 존재여부 판단
                                    if (obj3.parent === obj1.num) {
                                        // 작은 카테고리 선언
                                        resultData += "<li class='size" + obj3.level + "' onclick='location.href=\""+obj3.url+"\"'>" + obj3.name + "</li>";
                                    }
                                })
                                resultData += "</ul>"
                            }
                        }
                    }
                })
                resultData += "</ul>"
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
        $(".logoutBtn").text("회원가입");
        $(".logoutBtn").attr("onclick","location.href='/signup'");
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
        $(".loginNik").text("로그인");
        $(".loginNik").attr("onclick","location.href='/login/page'");
        $(".logoutBtn").text("회원가입");
        $(".logoutBtn").attr("onclick","location.href='/signup'");
    }).fail(()=>{
        console.log("logout failed.");
        alert("로그아웃 실패.");
    })
}