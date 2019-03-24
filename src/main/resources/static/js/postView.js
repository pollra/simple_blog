$(document).ready(()=>{
    getOnePost();            // 하나의 포스트를 가져옴
    setPostsListView();      // 글 리스트를 불러옴
});
const currentPath = location.pathname;
let postsList = new Array("");

function getOnePost(){
    console.log("/posts/select/"+currentPath.split("/")[2]);
    $.get("/posts/select/"+currentPath.split("/")[2], function(markdown) {
        // console.log("markdown.content: "+markdown.content);
        postView = editormd.markdownToHTML("postView", {
            markdown        : `#${markdown.title}\r\n${markdown.content}\r\n<hr>`,
            htmlDecode      : "style,script,iframe",  // you can filter tags decode
            tocm            : true,    // Using [TOCM]
            // markdownSourceCode : true, // 마크다운 소스를 Textarea에 남깁니다.
            emoji           : true,
            taskList        : true,
            tex             : true,
            flowChart       : true,
            sequenceDiagram : true,
        });
        let writer = `<div class="writerInfo"><h5>작성자 ${markdown.writer}</h5></div>`;
        let date = `<div class="dateInfo"><h5>날짜 ${markdown.date}</h5></div>`;
        $(".postsInfo").html(writer + date);
    }).fail(()=>{
        $("#postView").html()
    });
}
/*
<div id="listContainer">
    <div id="listWrapper">
        <li class="list_item">
            <div class="list_title">안녀어엉?</div>
            <div class="list_date">19.03.10 18:50</div>
        </li>
    </div>
    <div id="listNav">
    </div>
</div>
 */
/**
 * 클릭하면 그 글로 넘어감
 *
 * 넘기는 값은 포스트의 num 임
 * 그래서 post 를 조회 후 카테고리의 넘버를 받은 다음
 * 카테고리의 전체 내용을 출력한다.
 */
function setPostsListView(){
    console.log(currentPath);
    const currentPostNum = currentPath.split("/")[2];
    if(!(currentPath.split("/")[1] === "posts")){
        console.log(`${currentPath} : currentPath 에 posts 가 포함되지 않습니다.`);
        return;
    }
    $.ajax({
        url:`/posts/category/${currentPostNum}/list`,
        type:"get"
    }).done((result)=>{
        // const posts = JSON.parse(result.responseText);
        let postsListLength = 0;
        let postsIndexLength = 0;
        let listNavButton = "";
        let active_btn = -1;
        $.each(result, (i, obj)=>{
            console.log(i);
            if(postsList[postsListLength] === undefined){
                postsList[postsListLength] = "";
            }
            if (obj.num+'' === currentPostNum) {
                console.log(`obj.num(${obj.num}) 와 currentPostNum (${currentPostNum}) 가 같습니다.`);
                postsList[postsListLength] += `<li class="list_item list_item_active" onclick="location.href='/posts/${obj.num}'">`;
                active_btn = postsListLength;
                console.log(`active_btn = ${active_btn}`)
            }else {
                postsList[postsListLength] += `<li class="list_item" onclick="location.href='/posts/${obj.num}'">`;
            }
            postsList[postsListLength] += `<div class="list_title">${obj.title}</div>`;
            postsList[postsListLength] += `<div class="list_date">${obj.date}</div>`;
            postsList[postsListLength] += `</li>`;
            // if(((i+1) % 5) === 0 && i > 0) {
            //     postsListLength++;
            //     console.log(`postsListLength(${postsListLength}) : active_btn(${active_btn})`);
            //     if (active_btn > 0 && postsListLength === active_btn){
            //         console.log(`btn active ${postsListLength} : ${active_btn}`);
            //         listNavButton += `<li class="list_index_btn list_index_active" onclick="postsIndexMove(${active_btn}); return false;">${active_btn + 1}</li>`;
            //     }else{
            //         listNavButton += `<li class="list_index_btn" onclick="postsIndexMove(${postsListLength}); return false;">${postsListLength+1}</li>`;
            //     }
            // }else if(i===0){
            //     console.log(`postsListLength(${postsListLength}) : active_btn(${active_btn})`);
            //     if(active_btn > 0 && postsListLength === active_btn){
            //         console.log(`btn active ${postsListLength} : ${active_btn}`);
            //         listNavButton += `<li class="list_index_btn list_index_active" onclick="postsIndexMove(${active_btn}); return false;">${active_btn+1}</li>`;
            //     }else{
            //         listNavButton += `<li class="list_index_btn" onclick="postsIndexMove(${postsListLength}); return false;">${postsListLength+1}</li>`;
            //     }
            // }
            if(((i+1) % 5) === 0 && i > 0){
                postsListLength++;
            }
            console.log(`postsList[${postsListLength}] : ${postsList[postsListLength]}`);
            // console.log(`listNavButton : ${listNavButton}`);
        });
        $.each(result, (i, obj)=>{
            if(((i+1) % 5) === 0 && i > 0) {
                console.log(`postsListLength(${postsIndexLength}) : active_btn(${active_btn})`);
                postsIndexLength++;
                if (active_btn >= 0 && postsIndexLength === active_btn){
                    console.log(`btn active ${postsIndexLength} : ${active_btn}`);
                    listNavButton += `<li class="list_index_btn list_index_active" onclick="postsIndexMove(${active_btn}); return false;">${active_btn + 1}</li>`;
                }else{
                    listNavButton += `<li class="list_index_btn" onclick="postsIndexMove(${postsIndexLength}); return false;">${postsIndexLength+1}</li>`;
                }
            }else if(i===0){
                console.log(`postsListLength(${postsIndexLength}) : active_btn(${active_btn})`);
                if(active_btn >= 0 && postsIndexLength === active_btn){
                    console.log(`btn active ${postsIndexLength} : ${active_btn}`);
                    listNavButton += `<li class="list_index_btn list_index_active" onclick="postsIndexMove(${active_btn}); return false;">${active_btn + 1}</li>`;
                }else{
                    listNavButton += `<li class="list_index_btn" onclick="postsIndexMove(${postsIndexLength}); return false;">${postsIndexLength+1}</li>`;
                }
            }

        })

        $("#listNav").html("");
        $("#listNav").html(listNavButton);
        $("#listWrapper").html("");
        $("#listWrapper").html(postsList[active_btn === -1 ? 0 : active_btn]);
    }).fail((result)=>{
        const error = JSON.parse(result.responseText);
        let postsList = "";
        console.log(`[ERROR]${error.code}/${error.timeStamp} : ${error.message}`);
        $("#listWrapper").html("");
        $("#listWrapper").html(postsList);
    })
}

/**
 * 목차 이동
 */
function postsIndexMove(num=0){
    console.log(`postsIndexMove start`);
    if(num > postsList.length || 0 > num){
        console.log("데이터가 정확하지 않음");
        return;
    }
    console.log(`인덱스 이동합니다. ${num} 로 이동.`);
    console.log(`변경되는 view.html : ${postsList[num]}`);
    $("#listWrapper").html("");
    $("#listWrapper").html(postsList[num]);
}
