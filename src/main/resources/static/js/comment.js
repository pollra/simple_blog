$(document).ready(()=>{
    comment_select_list_action();
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
                console.log("\'ㅂ \'");
                return;
            }else if(yesOrNo){
                console.log("ㅇㅅㅇ");
                inputBoxChange();
                current_btn_option = "input";
            }else if(confirm("기존에 입력한 정보가 지워집니다. 입력버튼으로 바꾸시겠습니까?")) {
                console.log("ㅇㅂㅇ");
                inputBoxChange();
                current_btn_option = "input";
            }
            break;
        case 'update':
            $("#comment_input_box").val($(`#comment_${target}`).children(".comment_content").text());
            console.log("타겟 데이터: "+$(`#comment_${target}`).children(".comment_content").text());
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
    comment_update_ajax().then(()=>{
        comment_btn_basic_setting();
        comment_select_list_action();
    }).catch((err)=>{
        alert(err.message);
    });

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
 * 댓글 리스트를 불러옴
 */
function comment_select_list_action() {
    comment_select_list_ajax().then(()=>{
        console.log("comment loading complete");
    }).catch(()=>{
        console.log("comment loading failed. comment is not found.");
    });

    function comment_select_list_ajax() {
        return new Promise((resolve, reject) => {
            $.ajax({
                url: `/comment/select/list/value/${location.pathname.split("/posts/")[1]}`,
                type: "get"
            }).done((result) => {
                let comment_list = "";
                comment_list_html_create(result).then(
                    function (html) {
                        $(".comment_view").html(html);
                        resolve();
                    }, function (err) {
                        console.log(err.message);
                        reject(err.message);
                    })
            }).fail((result) => {
                let error = JSON.parse(result.responseText);
                reject(error.message);
            })
        });

        function comment_list_html_create(result) {
            return new Promise((resolve, reject) => {
                if (result === undefined) reject(new Error("result is null"));
                let comment_list = "";
                $.each(result, (i, obj) => {
                    comment_list += comment_list_one_html(obj);
                    if (result.length === i + 1) resolve(comment_list);
                })
            })
        }

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
}

/**
 * 댓글 리스트의 목차를 만듬
 */
let _comment_current_num = 0;   // 현재 자신의 위치를 기억
let _comment_last_num = 0;      // 마지막 댓글 페이지를 기억
let _comment_index = [];        // 목차를 기억
const _comment_list_size = 10;  // 페이지에 표시할 댓글의 갯수
const _comment_index_count=5;   // 목차에 표시할 인덱스의 길이
let _comment_list = [];         // 댓글 전체를 기억할 변수

/**
 let _comment_current_num = 0;   // 현재 자신의 위치를 기억
 let _comment_last_num = 0;      // 마지막 페이지를 기억
 let _comment_index = [];        // 현재 포커싱되어있는 목차를 저장함
 const _comment_list_size = 10;  // 페이지에 표시할 댓글의 갯수
 const _comment_index_count=5;   // 목차에 표시할 인덱스의 길이
 let _comment_list = [];         // 댓글 전체를 기억할 변수
 */
comment_index_move = (action="", target=-1)=>{
    return new Promise((resolve, reject)=>{
        if(action === "") throw new Error("필수값 action 에 아무런 값도 넘어오지 않았습니다.");
        if(target === -1) throw new Error("필수값 target 이 -1 입니다.");
        comment_index_range_check(target)
            .catch((err)=>{throw err;})
            .then(()=>{
                comment_index_move_action(action).then(()=>{

                })
            })
    })

    function comment_index_move_action(action){
        return new Promise((resolve, reject) => {
            switch (action) {
                case "prev":
                    // 인덱스를 바꿔야함.
                    if(_comment_index[0] === 1){
                        reject(new Error("첫번째 인덱스입니다."))
                    }else{
                        _comment_current_num = _comment_index[0] -1;    // _comment_index[0] == 6
                        _comment_index = [];
                        // _comment_index_count : 5
                        for(let j= ((_comment_current_num+1) - _comment_index_count);
                            j<=_comment_current_num;j++){
                            // 인덱스 작아짐
                            _comment_index.push(j);
                        }
                        resolve();
                    }
                    break;
                case "next":
                    // 인덱스를 바꿔야함.
                    if(_comment_index[_comment_index_count-1] === _comment_last_num) {
                        reject(new Error("마지막 인덱스입니다."));
                    }else{
                        _comment_current_num =
                            _comment_index[_comment_index_count-1] +1 > _comment_last_num
                                ? _comment_last_num :_comment_index[_comment_index_count-1] +1;
                        _comment_index = [];
                        for(let j = _comment_current_num; j<=_comment_index_count; j++){
                            if(j <= _comment_last_num){
                                _comment_index.push(j);
                            }
                        }
                        resolve();
                    }
                    break;
            }
        })
    }
    /**
     * 범위 체크
     */
    function comment_index_range_check(target){
        return new Promise((resolve1, reject1) => {
            if(target <= 0 || _comment_last_num < target){
                reject1(new Error(`범위가 맞지 않습니다: ${target}`));
            }else{
                resolve1()
            }
        });
    }
}
function comment_list_index() {
    comment_list_index_data().then((result)=>{

    }).catch((err)=>{

    })

    function comment_list_index_data() {
        return new Promise((resolve, reject) => {
            $.ajax({
                url: `/comment/select/list/value/${location.pathname.split("/posts/")[1]}`,
                type: "get"
            }).done((result) => {
                resolve(result)
            }).fail((err) => {
                reject(JSON.parse(err.responseText));
            })
        })
    }

    function comment_list_create(data){
        return new Promise((resolve, reject) => {
            /**
            let _comment_current_num = 0;   // 현재 자신의 위치를 기억
            let _comment_last_num = 0;      // 마지막 페이지를 기억
            let _comment_index = [];        // 목차 전체를 저장함
            const _comment_list_size = 10;  // 페이지에 표시할 댓글의 갯수
            const _comment_index_count=5;   // 목차에 표시할 인덱스의 길이
            let _comment_list = [];         // 댓글 전체를 기억할 변수
             */
            if(data.length <= 0) reject(new Error("데이터가 존재하지 않습니다."));
            _comment_current_num = 1;
            _comment_last_num = Math.floor(data.length/10);
            for(let i=0; i<_comment_index_count; i++) _comment_index.push(i);
            let comment_list_sub = [];
            $.each(data,(i,obj)=>{
                comment_list_sub.push(comment_list_one_html(obj));
                if((i+1)%_comment_list_size === 0 || i+1===data.length){
                    console.log(`comment_list_sub.length : ${comment_list_sub.length}`);
                    _comment_list.push(comment_list_sub);
                    comment_list_sub = [];
                }
            })
            // 전체적인 목차를 만들었음. 이제 인덱스에서 부르면 찍혀야함.
            // 인덱스를 변경하는 함수를 생성. 이 함수는 전역으로 사용될 수 있음
            // 지금은 안에서 선언되지만 이것은 코드를 작성하는데 편하기위함임.
            // 모두 작성되면 밖으로 빼자
            /**
             * 현재 경로에 상관없이 어떤 움직임을 할것이냐가 관건.
             * pointer : ... 을 눌렀을 때, 숫자를 눌렀을 때
             * prev : << 을 눌렀을 때
             * next : >> 를 눌렀을 때
             * @param action
             */
        })
    }

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