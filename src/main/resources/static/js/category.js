// 페이지 로딩 완료 시 액션
$(document).ready(()=>{
    
});
// 카테고리 추가
function addCategory(){
    console.log("[ addCategory ] start");
    console.log(`[ addCategory ] data log : {\n` +
    `categoryParent : ${$("#parentTargetCate option:selected").val()}\n` +
    `categoryLevel : ${$("#parentTargetCate option:selected").attr("name")}\n` +
    `categoryName : ${$("#parentTargetCate option:selected").text()}\n` +
    `categoryUrl : ${$("#parentTargetCate option:selected").attr("class")}\n}`
    );
    $.ajax({
        url:"/category",
        type:"post",
        data: JSON.stringify({
            "categoryParent":$("#parentTargetCate").val(),
            "categoryLevel":$("#parentTargetCate").attr("name"),
            "categoryName":$("#parentTargetCate").text(),
            "categoryUrl":$("#parentTargetCate").attr("class")
        }),
        contentType:"application/json; charset=utf-8;"
    }).done((result) =>{

    }).fail((result) =>{
    })
}
// 카테고리 수정
function updateCategory(){
    $.ajax({
        url:"/category",
        type:"put",
        data:JSON.stringify({}),
        contentType:"application/json; charset=utf-8;"
    }).done((result)=>{
    }).fail((result) =>{
    })
}
// 카테고리 삭제
function deleteCategory(){
    $.ajax({
        url:"/category",
        type:"delete",
        data: JSON.stringify({}),
        contentType:"application/json; charset=utf-8;"
    }).done((result)=>{
    }).fail((result) =>{
    })
}
// 카테고리 비공개 설정
function visibleSetCategory(){
    $.ajax({
        url:"/category",
        type:"put",
        data:JSON.stringify({}),
        contentType:"application/json; charset=utf-8;"
    }).done(()=>{
    }).fail(()=>{
    })
}

// 카테고리 목록 불러오기
function getCategoryList(){
    $.ajax({
        url:"/category",
        type:"get"
    }).done((result)=>{
    }).fail((result) =>{
    })
}