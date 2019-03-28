$(document).ready(()=>{
    comment_select_list_action();
});
let current_btn_option = "입력";
$(".comment_change_input_btn").hover(()=>{
    $(".comment_submit").text("입력");
},()=>{
    $(".comment_submit").text(current_btn_option);
});

/**
 * 
 * @param option : 
    * create, update, delete
 */
function form_change(target, option='create'){
    console.log(`타겟 : ${target} / 옵션 : ${option}`);
    if(target === undefined) return;
    switch (option) {
        case 'create':
            break;
        case 'update':
            $("#comment_input_box").val($(`#comment_${target}`).children(".comment_content").text());
            console.log("타겟 데이터: "+$(`#comment_${target}`).children(".comment_content").text());
            current_btn_option = "수정";
            $(".comment_submit").text("수정");
            $(".comment_submit").attr("class","comment_submit update_btn");
            $(".comment_submit").attr("onclick",`comment_update_action(${target})`);
            break;
        case 'delete':
            current_btn_option = "삭제";
            $("#comment_input_box").val($(`#comment_${target}`).children(".comment_content").text());
            $(".comment_submit").text("삭제");
            $(".comment_submit").attr("class","comment_submit delete_btn");
            $(".comment_submit").attr("onclick",`comment_delete_action(${target})`);
            break;
    }
}

/**
 * 댓글 하나를 게시함
 */
function comment_create_action(){
    $.ajax({
        url:"/comment/create/one",
        type:"post",
        contentType:"application/json;utf-8;",
        data:JSON.stringify({
            "board":location.pathname.split("/posts/")[1],
            "content":$("textarea.comment_content").val(),
            "password":$("input.comment_password").val()
        })
    }).done(()=>{
        // 댓글입력창 초기화
        $("textarea.comment_content").val("");
        $("input.comment_password").val("");

        // 댓글 리스트를 다시 불러옴
        comment_select_list_action();
    }).fail((result)=>{
        const error = JSON.parse(result.responseText);
        alert(error.message);
    })
}

/**
 * 댓글 하나를 업데이트함
 * @param target
 */
function comment_update_action(target = -1) {
    comment_update_ajax(target).then(()=>{
        comment_select_list_action();
    }).catch((err)=>{
        console.log(err.message);
    })
    function comment_update_ajax(target) {
        return new Promise((resolve, reject)=>{
            if (target === -1) reject(new Error("target is null"));
            $.ajax({
                url: "/comment/update/one",
                type: "put",
                contentType: "application/json;utf-8;",
                data: JSON.stringify({
                    "target": target,
                    "content": $("textarea.comment_content").text(),
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
function comment_delete_action(target = -1) {
    comment_delete_ajax(target).then(()=>{
        comment_select_list_action();
    }).catch((err)=>{
        console.log(err.message);
    })
    function comment_delete_ajax(target) {
        return new Promise(function (resolve, reject) {
            if (target === -1) reject(new Error("게시글을 찾을 수 없습니다."));
            $.ajax({
                url: "/comment/update/one",
                type: "put",
                contentType: "application/json;utf-8;",
                data: JSON.stringify({
                    "target": target,
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