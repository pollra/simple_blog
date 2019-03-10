$(document).ready(()=>{
    getOnePost();
});

function getOnePost(){
    let path = location.pathname;
    console.log("/posts/select/"+path.split("/")[2]);
    $.get("/posts/select/"+path.split("/")[2], function(markdown) {
        console.log("markdown.content: "+markdown.content);
        $("#postViewTitle").text(markdown.title);
        postView = editormd.markdownToHTML("postView", {
            markdown        : markdown.content + "\r\n"+"<hr>",
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
