$(document).ready(()=>{
    comment_select_list_action();
    comment_list_index();
});
let current_btn_option = "input";
$(".comment_change_input_btn").hover(()=>{
    $(".comment_submit").text("입력");
});
$(".comment_change_input_btn").mouseleave(()=>{
    $(".comment_submit").text(current_btn_option);
});
// 입력버튼으로 바꾸는 함수
function comment_btn_basic_setting(){
    $("#comment_input_box").val("");
    $(".comment_password").val("");
    $(".comment_submit").text("입력");
    $(".comment_submit").attr("class", "comment_submit");
    $(".comment_submit").attr("onclick", `comment_create_action();`);
}

/**
 * 댓글입력창을 바꿈
 * @param option : 
    * create, update, delete
 */
function form_change(target, option='create'){
    console.log(`타겟 : ${target} / 옵션 : ${option}`);
    if(target === undefined && !(option === "create")){
        console.log(">ㅁ<");
        return;
    }
    let yesOrNo = $("#comment_input_box").val()=== "";
    switch (option) {
        case 'create':
            if(current_btn_option === "input"){
                return;
            }else if(yesOrNo){
                inputBoxChange();
                current_btn_option = "input";
            }else if(confirm("기존에 입력한 정보가 지워집니다. 입력버튼으로 바꾸시겠습니까?")) {
                inputBoxChange();
                current_btn_option = "input";
            }
            break;
        case 'update':
            $("#comment_input_box").val($(`#comment_${target}`).children(".comment_content").text());
            // console.log("타겟 데이터: "+$(`#comment_${target}`).children(".comment_content").text());
            current_btn_option = "update";
            $(".comment_submit").text("수정");
            $(".comment_submit").attr("class","comment_submit update_btn");
            $(".comment_submit").attr("onclick",`comment_update_action(${target})`);
            break;
        case 'delete':
            current_btn_option = "delete";
            $("#comment_input_box").val($(`#comment_${target}`).children(".comment_content").text());
            $(".comment_submit").text("삭제");
            $(".comment_submit").attr("class","comment_submit delete_btn");
            $(".comment_submit").attr("onclick",`comment_delete_action(${target})`);
            break;
    }
    function inputBoxChange(){
        $("#comment_input_box").val("");
        $(".comment_password").val("");
        $(".comment_submit").text("입력");
        $(".comment_submit").attr("class", "comment_submit");
        $(".comment_submit").attr("onclick", `comment_create_action();`);
    }
}

/**
 * 댓글 하나를 게시함
 */
function comment_create_action(){
    if($("#comment_input_box").val()===""){
        alert("댓글이 입력되지 않았습니다.");
        throw new Error("댓글이 입력되지 않았습니다.");
    }else {
        if ($(".comment_password").val() === "") {
            alert("비밀번호를 입력해주세요.");
            throw new Error("비밀번호를 입력해주세요.");
        } else {
            comment_update_ajax().then(() => {
                comment_btn_basic_setting();
                comment_select_list_action();
            }).catch((err) => {
                alert(err.message);
            });
        }
    }

    function comment_update_ajax(){
        return new Promise((resolve, reject)=>{
            $.ajax({
                url: "/comment/create/one",
                type: "post",
                contentType: "application/json;utf-8;",
                data: JSON.stringify({
                    "board": location.pathname.split("/posts/")[1],
                    "content": $("textarea.comment_content").val(),
                    "password": $("input.comment_password").val()
                })
            }).done(() => {
                resolve();
            }).fail((result) => {
                const error = JSON.parse(result.responseText);
                reject(error.message);
            })
        })
    }
}
/**
 * 댓글 하나를 업데이트함
 * @param target
 */
function comment_update_action(target = -1) {
    comment_update_ajax(target).then(()=>{
        comment_select_list_action();
        comment_btn_basic_setting();
    }).catch((err)=>{
        alert(err.message);
    });
    function comment_update_ajax(target) {
        return new Promise((resolve, reject)=>{
            if (target === -1) reject(new Error("target is null"));
            $.ajax({
                url: "/comment/update/one",
                type: "put",
                contentType: "application/json;utf-8;",
                data: JSON.stringify({
                    "num": target,
                    "content": $("textarea.comment_content").val(),
                    "password": $("input.comment_password").val()
                })
            }).done((result) => {
                resolve("comment update complete");
            }).fail((result) => {
                reject(JSON.parse(result.responseText));
            });
        })
    }
}

/**
 * 댓글 하나를 삭제함
 * @param target
 */
function comment_delete_action(target = -1) {
    if(confirm("정말 삭제하시겠습니까?")) {
        comment_delete_ajax(target).then(() => {
            comment_select_list_action();
            comment_btn_basic_setting();
        }).catch((err) => {
            alert(err.message);
        })
    }
    function comment_delete_ajax(target) {
        return new Promise(function (resolve, reject) {
            if (target === -1) reject(new Error("게시글을 찾을 수 없습니다."));
            $.ajax({
                url: "/comment/delete/one",
                type: "put",
                contentType: "application/json;utf-8;",
                data: JSON.stringify({
                    "num": target,
                    "password": $("input.comment_password").val()
                })
            }).done((result) => {
                resolve(result);
            }).fail((result) => {
                reject(JSON.parse(result.responseText));
            });
        })
    }
}

/**
 * 댓글 리스트를 불러오고 페이징처리
 */
const _comment_list_count = 10; // 인덱스 1페이지당 댓글의 수
let _comment_list = [];     // 댓글들을 갯수에 맞춰 저장함
const _comment_index_count = 20; // 한페이지에 보여질 인덱스의 갯수
let _comment_index = [];    // 인덱스를 갯수에 맞춰 저장함

function comment_select_list_action() {
    comment_select_list_ajax().catch((err)=>{
        // ajax 요청 에러
        console.log("comment loading failed. comment is not found.");
        console.log(err.message);
    }).then((result)=>{
        console.log("comment loading complete");
        // 댓글의 HTML 코드를 만듬
        comment_list_html_create(result);
        return;
    }).then(()=>{
        // 만든 코드를 페이지에 뿌림
        $(".comment_view").html(_comment_list[0]);
        $(".comment_index_list").html(_comment_index[0]);
    })

    // ajax 요청으로 댓글 리스트를 불러옴
    function comment_select_list_ajax() {
        return new Promise((resolve, reject) => {
            $.ajax({
                url: `/comment/select/list/value/${location.pathname.split("/posts/")[1]}`,
                type: "get"
            }).done((result) => {
                resolve(result);
            }).fail((result) => {
                let error = JSON.parse(result.responseText);
                reject(error);
            })
        });
    }
    /**
     * 전체적인 댓글의 HTML 코드를 만듬
     *
     * 전역변수 배열 _comment_list 에 _comment_list_count 단위로 HTML 코드를 저장.
     * 전역변수 배열 _comment_index 에 _comment_index_count 단위로 HTML 코드를 저장.
     * 
     let _comment_list = []; // 댓글들을 갯수에 맞춰 저장함
     const _comment_list_count = 10;
     */
    function comment_list_html_create(result) {
        let okCount = 0;
        if (result === undefined){
            throw new Error("result is undefined");
        }else {
            let comments = "";
            let commentIndex = "";
            let currentValue = 0;
            let prevValue = 0;
            $.each(result, (i, obj) => {
                comments += comment_list_one_html(obj);
                currentValue = Math.floor((i + 1) / _comment_list_count);
                if(currentValue === prevValue){
                    _comment_list[currentValue] = comments;
                }else{
                    comments=comment_list_one_html(obj);
                    _comment_list[currentValue] = comment_list_one_html(obj);
                }
                if (result.length === i + 1) okCount++;
                prevValue = currentValue;
            })
            // 댓글의 페이징처리.
            $.each(_comment_list, (i, obj)=>{
                commentIndex += i===0?
                    `<li class="comment_index_item index_active" id="comment_index_${i+1}" onclick="comment_index_move(${i+1});">${i+1}</li>`
                :`<li class="comment_index_item" id="comment_index_${i+1}" onclick="comment_index_move(${i+1});">${i+1}</li>`;
                _comment_index[Math.floor((i+1) / _comment_index_count)] = commentIndex;
                if (_comment_list.length === i + 1) okCount++;
            })
        }
    }

    /**
     * 댓글 하나의 HTML 코드를 생성
     * @param obj
     * @returns {string}
     */
    function comment_list_one_html(obj) {
        return `<div class="comment_view_single" id="comment_${obj.num}">`
            + `<div class="comment_info">`
            + `<div class="comment_info_writer">${obj.writer}</div>`
            + `<div class="comment_info_date">${obj.date}</div>`
            + `</div>`
            + `<div class="comment_content">${obj.content}</div>`
            + `<div class="btn_box">`
            + `<button class="update_btn btn"`
            + ` onclick="form_change(${obj.num},'update');return false;">수정</button>`
            + `<button class="delete_btn btn" `
            + ` onclick="form_change(${obj.num},'delete');return false;">삭제</button>`
            + `</div>`
            + `</div>`;
    }
}

/**
 const _comment_list_count = 10; // 1페이지당 댓글의 수
 let _comment_list = [];     // 댓글들을 갯수에 맞춰 저장함
 const _comment_index_count = 5; // 한페이지에 보여질 인덱스의 갯수
 let _comment_index = [];    // 인덱스를 갯수에 맞춰 저장함
 * @param num
 */
function comment_index_move(num){
    $(".comment_view").html("");
    $(".comment_view").html(_comment_list[num-1]);
    $(".comment_index_item").attr("class","comment_index_item");
    $(`#comment_index_${num}`).attr("class","comment_index_item index_active");

}
