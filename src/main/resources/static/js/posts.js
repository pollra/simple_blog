$(document).ready(()=>{
    set_category();
})

function getPostContent(){
    console.log("Data : "+$("#postsContent").val())
}

function toggleBtn_visibleBtn(option){
    if(option === 0) {
        $("#visibleSelectBtn").text("비공개")
        $("#visibleSelectBtn").attr("class", "invisibleSelectBtn");
        $("#visibleSelectBtn").attr("value", 0);
        $("#visibleSelectBtn").attr("onclick","toggleBtn_visibleBtn(1); return false;");
        $("#visibleSelect").text("비공개 상태로 저장합니다.");
    }else if(option === 1){
        $("#visibleSelectBtn").text("공개")
        $("#visibleSelectBtn").attr("class", "visibleSelectBtn");
        $("#visibleSelectBtn").attr("value", 1);
        $("#visibleSelectBtn").attr("onclick","toggleBtn_visibleBtn(0); return false;");
        $("#visibleSelect").text("공개 상태로 저장합니다.");
    }
}

function createNewBoard(){
    if($("#categorySelect option:selected").val() === 0){
        alert("글을 등록할 카테고리를 선택해주세요.");
        $("#categorySelect").focus();
    }
    $.ajax({
        url:"/posts/create",
        type:"post",
        data: JSON.stringify({
            "newBoardCategory":$("#categorySelect option:selected").val(),
            "visibleSelectBtn":$("#visibleSelectBtn").val(),
            "newBoardTitle":$("#newBoardTitle").val(),
            "newBoardContent":$("#newBoardContent").val()
        }),
        contentType:"application/json; charset:utf-8;"
    }).done((entry)=>{
        console.log(`createNewBoard entry : ${entry}`);
        location.href="/posts/"+entry;
    }).fail((result)=>{
        let error = JSON.parse(result.responseText);
        alert(error.message);
        location.back();
    })
}

// 카테고리 목록 불러오기
function set_category(){
    $.ajax({
        url:"/category",
        type:"get",
        contentType:"application/json"
    }).done((result)=>{
        let categoryList = selectBox_categoryListCreate(result, 3);
        $("#categorySelect").html("");
        $("#categorySelect").html(categoryList);
    })
}

function selectBox_categoryListCreate(result, resultLevel = 3){
    var resultData = "";
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
    resultData += `<option value="0">카테고리를 지정해주세요</option>`;
    console.log(`카테고리 비율 [level 1:${count1}, level 2 : ${count2}, level 3 : ${count3}]`);
// 큰 카테고리 반복문
    $.each(size_0_category, (i, obj1)=>{
        // 큰 카테고리 선언
        resultData += `<option value="${obj1.num}">${obj1.name}</option>`;
        // 중간 카테고리 체크
        if(size_1_category.length > 0) {
            // 중간카테고리중 위에서 표시한 큰 카테고리의 자식이 있는지 확인
            if (categoryCheck(size_1_category, obj1.num)) {
                // 중간카테고리 반복문
                $.each(size_1_category, (j, obj2) => {
                    // 중간 카테고리 존재여부 판단
                    if (obj2.parent === obj1.num) {
                        // 중간카테고리 선언
                        resultData += `<option value="${obj2.num}">ㄴ${obj2.name}</option>`;
                        // 출력할 심도 선택. 출력 심도가 3 일 경우에만 2레벨 카테고리를 리턴함
                        if (resultLevel === 3) {
                            // 작은 카테고리 체크
                            if (size_2_category.length > 0) {
                                // 작은 카테고리중 위에서 표시한 중간 카테고리의 자식이 있는지 확인
                                if (categoryCheck(size_2_category, obj2.num)) {
                                    // resultData += "<ul>"
                                    // 작은 카테고리 반복문
                                    $.each(size_2_category, (j, obj3) => {
                                        // 작은 카테고리 존재여부 판단
                                        if (obj3.parent === obj2.num) {
                                            // 작은 카테고리 선언
                                            resultData += `<option value="${obj3.parent}">ㄴㄴ${obj3.name}</option>`;
                                        }   // if (obj3.parent === obj1.num) {
                                    }) // $.each(size_2_category, (j, obj3) => {
                                } // if (categoryCheck(size_2_category, size_1_category.num)) {
                            } // if (size_2_category.length > 0) {
                        }
                    } // if (obj2.parent === obj1.num) {
                }) // $.each(size_1_category, (j, obj2) => {
            } // if (categoryCheck(size_1_category, size_0_category.num)) {
        } // if(size_1_category.length > 0) {
    }) // $.each(size_0_category, (i, obj1)=>{
    if(resultData ===""){
        return "";
    }
    return resultData;
}