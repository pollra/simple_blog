var myInfoHtml =
    "<div class='myInfo'>" +
    "<div class='paragraph'>" +
    "<div class='dataExplanation'>" +
    "가입 데이터를 수정합니다.<br>" +
    "수정하고싶은 데이터들을 적고, 화면의 오른쪽 아래에 위치한 <button class='modifiedBtn'>수정</button> 버튼을 이용해 <br>" +
    "데이터를 수정할 수 있습니다." +
    "</div>" +
    "</div>" +
    "<div class='infoData sell'>" +
    "<label class='dataText'>아이디</label>" +
    "<span class='dataExplanation'>" +
    "아이디를 변경해야 하는 특수한 상황이라면 관리자에게 문의 주시길 바랍니다." +
    "</span>" +
    "<label class='dataInput'>Pollra</label>" +
    "<!--<input class='dataI' placeholder='Pollra'>-->" +
    "</div>" +
    "<div class='infoData sell'>" +
    "<label class='dataText' for='prepw'>비밀번호 변경</label>" +
    "<div class='paragraph' for='prepw'>" +
    "<span class='dataExplanation'>" +
    "비밀번호를 변경합니다.<br>" +
    "비밀번호는 반드시 영문, 숫자, 특수문자만 사용할 수 있습니다.<br>" +
    "기존 비밀번호를 적고 변경할 비밀번호를 기입한 뒤 <button class='modifiedBtn'>수정</button>버튼을 이용해 수정합니다." +
    "</span>" +
    "</div>" +
    "<div class='paragraph'>" +
    "<label class='dataInfo' for='prepw'>기존 비밀번호</label>" +
    "<input class='dataInput' id='prepw' type='password' placeholder='기존 비밀번호'>" +
    "</div>" +
    "<div class='paragraph'>" +
    "<label class='dataInfo' for='nexpw'>새로운 비밀번호</label>" +
    "<input class='dataInput' id='nexpw' type='password' placeholder='...'>" +
    "</div>" +
    "<div class='paragraph'>" +
    "<label class='dataInfo' for='repw'>재입력</label>" +
    "<input class='dataInput' id='repw' type='password' placeholder='...'>" +
    "</div>" +
    "</div>" +
    "<div class='infoData sell'>" +
    "<label class='dataText' for='updateName'>이름</label>" +
    "<span class='dataExplanation'>이름을 수정합니다. 수정하고싶은 이름을 적고 <button class='modifiedBtn'>수정</button>버튼을 이용해 수정합니다.</span>" +
    "<input class='dataInput' id='updateName' placeholder='이승근'>" +
    "</div>" +
    "<div class='infoData sell'>" +
    "<label class='dataText'>권한</label>" +
    "<span class='dataExplanation'>권한을 부여합니다. 관리자만이 수정할 수 있습니다.</span>" +
    "<label class='dataInput'>관리자</label>" +
    "</div>" +
    "</div>";

var cateInfoHtml =
    "<div class='cateInfo'>" +
    "<div class='paragraph'>" +
    "<label class='dataExplanation'>블로그의 카테고리를 추가, 수정 또는 삭제 합니다.<br>" +
    "</label>" +
    "</div>" +
    "<div class='infoData sell'>" +
    "<label class='dataText'>카테고리 추가</label>" +
    "<label class='dataExplanation'>" +
    "카테고리를 추가합니다. 오른쪽의 추가 버튼을 이용해 왼쪽의 네비게이션 바에 바로 적용시킵니다." +
    "</label>" +
    "<input class='dataInput' placeholder='카테고리 입력' id='inputCate'>" +
    "<button class='actionBtn' style='background: #507592'>추가</button>" +
    "</div>" +
    "<div class='infoData sell'>" +
    "<label class='dataText'>카테고리 수정</label>" +
    "<label class='dataExplanation'>" +
    "카테고리를 수정합니다. 오른쪽의 수정 버튼을 이용해 왼쪽의 네비게이션 바를 선택해서 수정합니다." +
    "</label>" +
    "<select class='categoryUpdateSelect' id='updateTargetCate'>" +
    "<option value='1'>1</option>" +
    "<option value='2'>2</option>" +
    "<option value='3'>3</option>" +
    "</select>" +
    "<input class='categoryUpdateInput' placeholder='왼쪽에서 선택하면 이곳에 표시됩니당' id='updateCate'>" +
    "<button class='actionBtn' style='background: green'>수정</button>" +
    "</div>" +
    "<div class='infoData sell'>" +
    "<label class='dataText'>카테고리 삭제</label>" +
    "<label class='dataExplanation'>" +
    "카테고리를 삭제합니다. 오른쪽의 삭제 버튼을 이용해 왼쪽의 네비게이션 바에서 바로 삭제합니다." +
    "</label>" +
    "<select class='categoryDeleteSelect' id='deleteTargetCate'>" +
    "<option value='1'>1</option>" +
    "<option value='2'>2</option>" +
    "<option value='3'>3</option>" +
    "</select>" +
    "<button class='actionBtn' style='background: darkred'>삭제</button>" +
    "</div>" +
    "<div class='infoData sell'>" +
    "<label class='dataText'>카테고리 비공개</label>" +
    "<label class='dataExplanation'>" +
    "카테고리 하나를 선택해서 비공개로 만듭니다. <br>" +
    "이 공개 설정은 자신만 볼 수 있으며 '운영자' 권한 이상만이 사용할 수 있습니다." +
    "</label>" +
    "<input class='dataInput' placeholder='카테고리 입력'>" +
    "<button class='actionBtn no_Action' style='background: gray'>적용</button>"+
    "</div>" +
    "</div>";

var gbInfoHtml =
    "<div class='gbInfo'>" +
    "<div class='paragraph'>" +
    "<label class='dataExplanation'>" +
    "방명록을 관리합니다. <br>" +
    "운영자 이상의 등급만 관리할 수 있습니다.<br>" +
    "</label>" +
    "<button class='goGBookPage' onclick='location.href=\'/guestbook\''>방명록 관리</button>" +
    "</div>" +
    "</div>";

var buttonSet = "<button class='modifiedBtn'>수정</button>" +
    "    <button class='resetBtn'>리셋</button>";

$(document).ready(()=>{
    $(".contents").html("");
    $(".contents").html(myInfoHtml);
    $(".btnSet").html("");
    $(".btnSet").html(buttonSet);
});

function openStatus(methodName){
    switch (methodName) {
        case "myInfo":
            $(".contents").html("");
            $(".contents").html(myInfoHtml);
            $(".btnSet").html("");
            $(".btnSet").html(buttonSet);
            break;
        case "cateInfo":
            $(".contents").html("");
            $(".contents").html(cateInfoHtml);
            $(".btnSet").html("");
            break;
        case "gbInfo":
            $(".contents").html("");
            $(".contents").html(gbInfoHtml);
            $(".btnSet").html("");
            break;
        default:
            alert("미구현된 기능입니다.");
    }
}
// 비밀번호 변경
function updatePasswordAction(){
    $.ajax({
        url: "/user",
        type: "put",
        data: JSON.stringify({"option":"pw","newPassword":$("#nexpw").val()}),
        contentType:"application/json; charset=utf-8;"
    }).done((result)=>{

    })
}