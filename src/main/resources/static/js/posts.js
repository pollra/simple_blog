// document.write("<script src='posts.js'></script>");
document.write("<script src='postView.js'></script>")
$(document).ready(()=>{
    $.when(set_category()).done(
        selectSetCategory()
    );
});

function getPostContent(){
    // console.log("Data : "+$("#postsContent").val())
}

function toggleBtn_visibleBtn(option){
    if(option === 0 || option === "0") {
        $("#visibleSelectBtn").text("비공개")
        $("#visibleSelectBtn").attr("class", "invisibleSelectBtn");
        $("#visibleSelectBtn").attr("value", 0);
        $("#visibleSelectBtn").attr("onclick","toggleBtn_visibleBtn(1); return false;");
        $("#visibleSelect").text("비공개 상태로 저장합니다.");
    }else if(option === 1 || option === "1"){
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
        // console.log(`createNewBoard entry : ${entry}`);
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
    // console.log(`카테고리 비율 [level 1:${count1}, level 2 : ${count2}, level 3 : ${count3}]`);
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
                                            resultData += `<option value="${obj3.num}">ㄴㄴ${obj3.name}</option>`;
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

// 글 - 각 글의 비공개 버튼 변경
function updateVisible(targetNum, targetVisible){
    let visible = 0;
    if(targetVisible === 0){
        visible = 1;
    }
    // console.log("[updateVisible] 업데이트 targetNum: "+ targetNum+" / visible: "+visible );
    $.ajax({
        url:`/posts/${targetNum}/update/visible/value/${visible}`,
        type:"put"
    }).done((result)=>{
        setPostList();
    }).fail((result)=>{
        let error = JSON.parse(result.responseText);
        alert(error.message);
    })
}

function setPostList(){
    $.ajax({
        url: "/posts/boardcategory",
        type:"get"
    }).done((result)=>{
        let data ="";
        $.each(result, (i, obj)=>{
            data += `<ul class="dataContent" id="${obj.num}">`;
            data += `<li class="boardIndex_category">${obj.name}</li>`;
            data += `<li class="boardIndex_title" onclick="location.href='/posts/update/${obj.num}'">${obj.title}</li>`;
            data += `<li class="boardIndex_date">${obj.date}</li>`;
            data += `<li class="boardIndex_visible" onclick="updateVisible(${obj.num}, ${obj.visible}); return false;">`;
            if(obj.visible === 1){
                data += '공개';
            }
            if(obj.visible === 0){
                data += '비공개';
            }
            data += `</li>`;
            data += `</ul>`;
        })
        $("#dataContentList").html("");
        $("#dataContentList").html(data);
    }).fail((result)=>{
        let error = JSON.parse(result.responseText);
        $("#dataContentList").html("");
        $("#dataContentList").html("게시물이 존재하지 않습니다.");
        alert(error.message);
    })
}

/**
 *
 * 포스트 수정
 * 제목과 컨텐츠, 공개여부 수정가능
 *
 * "newBoardCategory":$("#categorySelect option:selected").val(),
 * "visibleSelectBtn":$("#visibleSelectBtn").val(),
 * "newBoardTitle":$("#newBoardTitle").val(),
 * "newBoardContent":$("#newBoardContent").val()
 *
 * 데이터를 put 으로 날림
 * 
 * 서버로 날리는 json 데이터 이름 확인하고 설정에 맞춰 제작
 * 서버에서 받는 controller 만들어야됨(?)
 * 서버에서 실행될 service 만들어야됨
 *
 * option : all
 */

function updateOnePost(targetNum, option){
    // console.log("[updateOnePost] start");
    // console.log(`targetNum: ${targetNum}, option: ${option}`);
    $.ajax({
        url: `/posts/${targetNum}/update/${option}`,
        type: "put",
        contentType: "application/json; charset=utf-8;",
        data: JSON.stringify({
            "boardCategory":$("#categorySelect option:selected").val(),
            "visibleSelectBtn":$("#visibleSelectBtn").val(),
            "boardTitle":$("#newBoardTitle").val(),
            "boardContent":$("#newBoardContent").val()
        })
    }).done((result)=>{
        location.href=`/posts/${targetNum}`;
    }).fail((result)=>{

    })
}

/**
 * 글 - 업데이트페이지에서 글을 받아온 뒤 찍음
 */
function getOneBoard(){
    let path = location.pathname;
    // console.log(`현재 페이지 경로: ${path}`);
    
    // /posts/update/{num} 일 경우에만 실행됨
    if(path.split("/")[1] !== "posts"){
        // console.log("[getOneBoard] 경로에서 posts 를 감지할 수 없습니다. false 리턴합니다.");
        return false;
    }
    if(path.split("/")[2] !== "update"){
        // console.log("[getOneBoard] 경로에서 update 를 감지할 수 없습니다. false 리턴합니다.");
        return false;
    }
    $.get("/posts/select/"+path.split("/")[3], function(result) {
        // let board = JSON.parse(result.responseText);
        $("#newBoardTitle").val(result.title);
        $("#newBoardContent").val(result.content);
        toggleBtn_visibleBtn(result.visible);
        $("#saveBtnWrapper button").attr("onclick",`updateOnePost(${result.num},'all'); return false;`);

        $(document).ready(()=>{
            $("#categorySelect").val(`${result.category}`).prop("selected", true);
        })
    }).fail((result)=>{
        let error = JSON.parse(result.responseText);
        // console.log(`error : ${error.message}`);
        alert(error.message);
    })
}

// 현재 경로를 확인하고 카테고리를 미리 선택해둔다
// /posts/{num}/category/{categoryNum}
/**
 * path[1] : posts
 * path[2] : category
 * path[3] : {categoryNum}
 * @returns {boolean}
 */
function selectSetCategory(){
    let path = location.pathname.split("/");
    // console.log("path: "+ location.pathname + " / path.length : "+path.length);
    if(path.length < 4){
        // console.log("[selectSetCategory] 길이가 부족합니다. path.length: " + path.length);
        return false;
    }
    if(path[1] !== 'posts'){
        // console.log("[selectSetCategory] 카테고리셋이 실행되지 않습니다. path[1]: " + path[1]);
        return false;
    }
    if(path[2] !== 'category'){
        // console.log("[selectSetCategory] 카테고리셋이 실행되지 않습니다. path[2]: " + path[2]);
        return false;
    }
    $("#categorySelect").val(`${path[3]}`).prop("selected", true);
}