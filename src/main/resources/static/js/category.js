document.write("<script src='nav.js'></script>");

// 페이지 로딩 완료 시 액션
$(document).ready(()=>{

});
// 카테고리 추가
function add_Category(){
    console.log("[ addCategory ] start");
    console.log(`[ addCategory ] data log : {\n` +
    `categoryParent : ${$("#parentTargetCate option:selected").attr("class")}\n` +
    `parentLevel : ${$("#parentTargetCate option:selected").attr("name")}\n` +
    `categoryName : ${$("#inputCate").val()}\n}`
    );
    $.ajax({
        url:"/category",
        type:"post",
        data: JSON.stringify({
            "categoryParent":$("#parentTargetCate option:selected").attr("class"),
            "parentLevel":$("#parentTargetCate option:selected").attr("name"),
            "categoryName":$("#inputCate").val()
        }),
        contentType:"application/json; charset=utf-8;"
    }).done((result) =>{
        let data = "";
        getCategoryList();      // 네비게이션 카테고리 새로고침
        get_CategoryList();     // 선택창 카테고리 새로고침
        $("#inputCate").val("");
    }).fail((result) =>{
        const error = JSON.parse(result.responseText);
        console.log(`msg: ${error.message} code: ${error.code} time: ${error.timeStamp}`);
        alert("카테고리 추가 실패");
    })
}
// 카테고리 수정
function update_Category(){
    $.ajax({
        url:"/category",
        type:"put",
        data:JSON.stringify({
            "updateTarget":$("#updateTargetCate option:selected").attr("class"),
            "updateText":$("#updateCate").val()
        }),
        contentType:"application/json; charset=utf-8;"
    }).done((result)=>{
        getCategoryList();      // 네비게이션 카테고리 새로고침
        get_CategoryList();     // 선택창 카테고리 새로고침
        $("#updateCate").val("");
    }).fail((result) =>{
        alert("수정 실패");
    })
}
// 카테고리 삭제
function delete_Category(){
    $.ajax({
        url:"/category",
        type:"delete",
        data: JSON.stringify({
            "deleteTarget":$("#deleteTargetCate option:selected").attr("class")
        }),
        contentType:"application/json; charset=utf-8;"
    }).done((result)=>{
        getCategoryList();      // 네비게이션 카테고리 새로고침
        get_CategoryList();     // 선택창 카테고리 새로고침
    }).fail((result) =>{
        alert("삭제 실패");
    })
}
// 카테고리 비공개 설정
function put_visibleSetCategory(option = 0){
    $.ajax({
        url:"/category/vi",
        type:"put",
        data:JSON.stringify({
            "visibleTarget":$("#visibleTargetCate option:selected").attr("class"),
            "visibleOption":option
        }),
        contentType:"application/json; charset=utf-8;"
    }).done(()=>{
        getCategoryList();      // 네비게이션 카테고리 새로고침
        get_CategoryList();     // 선택창 카테고리 새로고침
    }).fail(()=>{
        alert("설정 변경 실패");
    })
}

// 카테고리 목록 불러오기
function get_CategoryList(option = "all"){
    $.ajax({
        url:"/category",
        type:"get",
        contentType:"application/json"
    }).done((result)=>{
        var data_level3 = selectBox_categoryListCreate(result, 3);
        var data_level2 = selectBox_categoryListCreate(result, 2);
        if(option === "create" || option === "all") {
            // console.log(data);
            $("#parentTargetCate").html("");
            $("#parentTargetCate").html(data_level2);
        }
        if(option === "update" || option === "all") {
            // console.log(data);
            $("#updateTargetCate").html("");
            $("#updateTargetCate").html(data_level3);
        }
        if(option === "delete" || option === "all") {
            // console.log(data);
            $("#deleteTargetCate").html("");
            $("#deleteTargetCate").html(data_level3);
        }

        if(option === "visible" || option === "all") {
            // console.log(data);
            $("#visibleTargetCate").html("");
            $("#visibleTargetCate").html(data_level3);
        }

    }).fail((result) =>{
        console.log(JSON.parse(result.responseText).message);
    })
}
// 카테고리 관리 페이지 선택창 카테고리 불러오기
function selectBox_categoryListCreate(result, resultLevel = 3){
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
    resultData += `<option class="0" value="0" name="0">default</option>`;
    console.log(`${count1} : ${count2} : ${count3}`)
    // 큰 카테고리 반복문
    $.each(size_0_category, (i, obj1)=>{
        // 큰 카테고리 선언
        resultData += `<option name="${obj1.level}" value="${obj1.parent}" class="${obj1.num}">${obj1.name}</option>`;
        // 중간 카테고리 체크
        if(size_1_category.length > 0) {
            // 중간카테고리중 위에서 표시한 큰 카테고리의 자식이 있는지 확인
            if (categoryCheck(size_1_category, obj1.num)) {
                // 중간카테고리 반복문
                $.each(size_1_category, (j, obj2) => {
                    // 중간 카테고리 존재여부 판단
                    if (obj2.parent === obj1.num) {
                        // 중간카테고리 선언
                        resultData += `<option name="${obj2.level}" value="${obj2.parent}" class="${obj2.num}">ㄴ${obj2.name}</option>`;
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
                                            resultData += `<option name="${obj3.level}" value="${obj3.parent}" class="${obj3.num}">ㄴㄴ${obj3.name}</option>`;
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