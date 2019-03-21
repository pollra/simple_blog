$(document).ready(()=>{
    getOnePost();
    setPostsListView();
});
const currentPath = location.pathname;

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
    if(!(currentPath.split("/")[1] === "posts")){
        console.log(`${currentPath} : currentPath 에 posts 가 포함되지 않습니다.`);
        return;
    }
    $.ajax({
        url:`/posts/category/${currentPath.split("/")[2]}/list`,
        type:"get"
    }).done((result)=>{
        // const posts = JSON.parse(result.responseText);
        let postsList = "";
        $.each(result, (i, obj)=>{

            postsList += `<li class="list_item" onclick="location.href='/posts/${obj.num}'">`;
            postsList += `<div class="list_title">${obj.title}</div>`;
            postsList += `<div class="list_date">${obj.date}</div>`;
            postsList += `</li>`;
        })
        $("#listWrapper").html("");
        $("#listWrapper").html(postsList);

    }).fail((result)=>{
        const error = JSON.parse(result.responseText);
        let postsList = "";
        console.log(`[ERROR]${error.code}/${error.timeStamp} : ${error.message}`);
        $("#listWrapper").html("");
        $("#listWrapper").html(postsList);
    })
}