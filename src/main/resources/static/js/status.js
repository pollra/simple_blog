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
    "비밀번호는 반드시 8~20자리로. 영문(대/소문자), 숫자, 특수문자를 섞어야 합니다.<br>" +
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
    "<input class='dataInput' id='repw' type='password' placeholder='...' onclick='outLineSetNone(); return '>" +
    "</div>" +
    "<div class='paragraph btnParagraph'>" +
    "<button class='actionBtn' onclick='updateFunction(`pw`);return false;'>수정</button>" +
    "</div>"+
    "</div>" +
    "<div class='infoData sell'>" +
    "<label class='dataText' for='updateName'>이름</label>" +
    "<span class='dataExplanation'>이름을 수정합니다. 수정하고싶은 이름을 적고 <button class='modifiedBtn'>수정</button>버튼을 이용해 수정합니다.</span>" +
    "<input class='dataInput' id='updateName' placeholder='이승근'>" +
    "<div class='paragraph btnParagraph'>" +
    "<button class='actionBtn' onclick='updateFunction(`name`);return false;'>수정</button>"+
    "</div>" +
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
    "모든 기능은 즉시 적용되며 왼쪽의 카테고리 창에서 확인할 수 있습니다." +
    "</label>" +
    "</div>" +
    "<div class='infoData sell'>" +
    "<label class='dataText'>카테고리 추가</label>" +
    "<label class='dataExplanation'>" +
    "카테고리를 추가합니다.<br>" +
    "왼쪽의 선택창에서 상위 카테고리를 선택한 뒤 오른쪽의 입력창에서 카테고리의 이름을 입력합니다.<br>" +
    "최 상위 카테고리로 지정하고 싶다면 'none'으로 지정하고 이름을 입력하면 됩니다."+
    "</label>" +
    "<select class='categoryParentSelect' id='parentTargetCate'>" +
    "<option value='default'>none</option>" +
    "<option value='2'>2</option>" +
    "<option value='3'>3</option>" +
    "</select>" +
    "<input class='categoryParentInput' placeholder='카테고리 입력' id='inputCate'>" +
    "<button class='actionBtn' style='background: #507592'>추가</button>" +
    "</div>" +
    "<div class='infoData sell'>" +
    "<label class='dataText'>카테고리 수정</label>" +
    "<label class='dataExplanation'>" +
    "카테고리를 수정합니다.<br>" +
    "왼쪽의 선택창에서 수정할 카테고리를 선택 후 오른쪽의 입력창에서 수정될 값을 입력합니다." +
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

$(document).ready(()=>{
    $(".contents").html("");
    $(".contents").html(myInfoHtml);
    $(".btnSet").html("");
});

function openStatus(methodName){
    console.log("[openStatus] start")
    switch (methodName) {
        case "myInfo":
            $(".contents").html("");
            $(".contents").html(myInfoHtml);
            $(".btnSet").html("");
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

function updateFunction(actionMethod = ""){
    console.log("[updateFunction] start")
    if(actionMethod === "paName"){
        if(dataEntryCheck(optionCheck())){
            updatePwName();
        }
        return;
    }
    if(actionMethod === "name"){
        if(dataEntryCheck(optionCheck())){
            updateName();
        }
        return;
    }
    if(actionMethod === "pw"){
        if(dataEntryCheck(optionCheck())){
            updatePassword();
        }
        return;
    }
    if(actionMethod === ""){
        console.log("updateFunction-actionMethod is null");
    }
}

class MyInfo{
    constructor(){
        // private
        this._object_originalPassword = $("#prepw");
        this._object_newInputPassword = $("#nexpw");
        this._object_reEnterPassword = $("#repw");
        this._object_newInputName = $("#updateName");
        this._passwordRegex = /^.*(?=^.{8,20}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;

        // public
        this.update = {
            name:()=>{
                $.ajax({
                    url: "/user",
                    type: "put",
                    data: JSON.stringify({"option":"name","newName":$("#updateName").val()}),
                    contentType:"application/json; charset=utf-8;"
                }).done((result)=>{
                    $(".updateName").val("");
                    alert("이름 변경에 성공했습니다.");
                }).fail((result)=>{
                    console.log("[!] name update failed.");
                    const error = JSON.parse(result.responseText);  // 날아온 JSON 텍스트 데이터를 JSON 객체로 변환해줌
                    alert(error.message);
                })
            },
            password:()=>{
                $.ajax({
                    url: "/user",
                    type: "put",
                    data: JSON.stringify({"option":"pw","newPassword":$("#nexpw").val()}),
                    contentType:"application/json; charset=utf-8;"
                }).done((result)=>{
                    $(".prepw").val("");
                    $(".nexpw").val("");
                    $(".repw").val("");
                    alert("비밀번호 변경에 성공했습니다.");
                }).fail((result)=>{
                    console.log("[!] password update failed.");
                    const error = JSON.parse(result.responseText);  // 날아온 JSON 텍스트 데이터를 JSON 객체로 변환해줌
                    alert(error.message);
                })
            },
            passwordName:()=>{
                console.log("[MyInfo.update.password] start");
                $.ajax({
                    url: "/user",
                    type: "put",
                    data: JSON.stringify({
                        "option":"pwName",
                        "newPassword":$("#nexpw").val(),
                        "newName":$("#updateName").val()
                    }),
                    contentType:"application/json; charset=utf-8;"
                }).done((result)=>{
                    $(".prepw").val("");
                    $(".nexpw").val("");
                    $(".repw").val("");
                    $("#updateName").val("");
                    alert("내 정보 수정에 성공했습니다.");
                }).fail((result)=>{
                    console.log("[!] info update failed.");
                    const error = JSON.parse(result.responseText);  // 날아온 JSON 텍스트 데이터를 JSON 객체로 변환해줌
                    alert(error.message);
                })
            }
        };
        this.option;
    }
}

/**
 * 데이터 유효성 검사
 * 데이터의 길이 검사
 * 데이터 입력 검사
 * this._object_originalPassword = $("#prepw");
   this._object_newInputPassword = $("#nexpw");
   this._object_reEnterPassword = $("#repw");
   this._object_newInputName = $("#updateName");
 * @param _option
 * @returns {boolean}
 */
function dataEntryCheck(_option = ""){
    console.log("[dataEntryCheck] start");
    const originalPassword = $("#prepw");
    const newInputPassword = $("#nexpw");
    const reEnterPassword = $("#repw");
    const newInputName = $("#updateName");
    const passwordRegex = /^.*(?=^.{8,20}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
    if(_option === "name"){
        console.log(
            `[dataEntryCheck] name : {
 newInputName : ${newInputName.val().length}}`);
        if(newInputName.val().length <= 0){  // 데이터가 없을경우
            return false;
        }
        if(newInputName.val() === newInputName.attr("placeholder")){
            alert("기존의 이름과 같은 이름으로 지정할 수 없습니다.");
            return false;
        }
        return true;
    }
    if(_option === "pw"){
        console.log(
`[dataEntryCheck] pw : {
 originalPassword : ${originalPassword.val().length}
 newInputPassword : ${newInputPassword.val().length}
 reEnterPassword : ${reEnterPassword.val().length}}`);
        if(originalPassword.val().length <= 0){    // 데이터가 없을경우
            console.log("[dataEntryCheck] pw : originalPassword 데이터가 입력되지 않았습니다.");
            return false;
        }
        if(newInputPassword.val().length <= 0){
            console.log("[dataEntryCheck] pw : newInputPassword 데이터가 입력되지 않았습니다.");
            return false;
        }
        if(reEnterPassword.val().length <= 0){
            console.log("[dataEntryCheck] pw : reEnterPassword 데이터가 입력되지 않았습니다.");
            return false;
        }

        // 데이터 유효성 검사
        if(originalPassword.val() === newInputPassword.val()){
            alert("새로 지정하는 비밀번호는 기존 번호와 같을 수 없습니다.");
            return false;
        }
        if(!(newInputPassword.val() === reEnterPassword.val())){
            alert("재입력한 비밀번호를 다시 확인해주십시오.");
            return false;
        }
        if(!passwordRegex.test(newInputPassword.val())){
            alert("비밀번호는 반드시 8~20자리로. 영문(대/소문자), 숫자, 특수문자를 섞어야 합니다.");
            return false;
        }
        return true;
    }
    if(_option === "pwName"){
        console.log(
`[dataEntryCheck] pwName : {
 originalPassword : ${originalPassword.val().length}
 newInputPassword : ${newInputPassword.val().length}
 reEnterPassword : ${reEnterPassword.val().length}
 newInputName : ${newInputName.val().length}
 }`);
        if(newInputName.val().length <= 0){  // 데이터가 없을경우
            return false;
        }
        if(newInputName.val() === newInputName.attr("placeholder")){
            alert("기존의 이름과 같은 이름으로 지정할 수 없습니다.");
            return false;
        }

        if(originalPassword.val().length <= 0){    // 데이터가 없을경우
            console.log("[dataEntryCheck] pw : originalPassword 데이터가 입력되지 않았습니다.");
            return false;
        }
        if(newInputPassword.val().length <= 0){
            console.log("[dataEntryCheck] pw : newInputPassword 데이터가 입력되지 않았습니다.");
            return false;
        }
        if(reEnterPassword.val().length <= 0){
            console.log("[dataEntryCheck] pw : reEnterPassword 데이터가 입력되지 않았습니다.");
            return false;
        }

        // 데이터 유효성 검사
        if(originalPassword.val() === newInputPassword.val()){
            alert("새로 지정하는 비밀번호는 기존 번호와 같을 수 없습니다.");
            return false;
        }
        if(!(newInputPassword.val() === reEnterPassword.val())){
            alert("재입력한 비밀번호를 다시 확인해주십시오.");
            return false;
        }
        if(!passwordRegex.test(newInputPassword.val())){
            alert("비밀번호는 반드시 8~20자리로. 영문(대/소문자), 숫자, 특수문자를 섞어야 합니다.");
            return false;
        }
        return true;
    }else{
        console.log("[dataEntryCheck] pw : _option 데이터가 초기화 되지 않았거나 옵션을 찾을 수 없습니다.");
        return false;
    }

}

/**
 * 옵션 체크
 * pwName / name / pw
 * @returns {string}
 */
function optionCheck(){
    console.log("[optionCheck] start");
    const originalPassword = $("#prepw");
    const newInputPassword = $("#nexpw");
    const reEnterPassword = $("#repw");
    const newInputName = $("#updateName");
    if(newInputName.val().length > 0 && originalPassword.val().length > 0){
        console.log("select Option : pwName");
        return "pwName";
    }
    if(newInputName.val().length > 0){
        console.log("select Option : name");
        return "name";
    }
    if(originalPassword.val().length > 0){
        console.log("select Option : pw");
        return "pw";
    }else{
        console.log("[optionCheck] none : 옵션을 찾을 수 없습니다.");
        return "";
    }
}

/**
 * ajax put method - pwName
 * ajax 요청 데이터를 업데이트 함
 */
function updatePwName(){
    console.log("[updatePwName] start");
    $.ajax({
        url: "/user",
        type: "put",
        data: JSON.stringify({
            "option":"pwName",
            "newPassword":$("#nexpw").val(),
            "newName":$("#updateName").val()
        }),
        contentType:"application/json; charset=utf-8;"
    }).done((result)=>{
        $("#prepw").val("");
        $("#nexpw").val("");
        $("#repw").val("");
        $("#updateName").val("");
        alert("내 정보 수정에 성공했습니다.");
    }).fail((result)=>{
        console.log("[!] info update failed.");
        const error = JSON.parse(result.responseText);  // 날아온 JSON 텍스트 데이터를 JSON 객체로 변환해줌
        alert(error.message);
    })
};

/**
 * ajax put method - pw
 * ajax 요청 데이터를 업데이트 함
 */
function updatePassword(){
    console.log("[updatePassword] start");
    $.ajax({
        url: "/user",
        type: "put",
        data: JSON.stringify({"option":"pw","newPassword":$("#nexpw").val()}),
        contentType:"application/json; charset=utf-8;"
    }).done((result)=>{
        $("#prepw").val("");
        $("#nexpw").val("");
        $("#repw").val("");
        alert("비밀번호 변경에 성공했습니다.");
    }).fail((result)=>{
        console.log("[!] password update failed.");
        const error = JSON.parse(result.responseText);  // 날아온 JSON 텍스트 데이터를 JSON 객체로 변환해줌
        alert(error.message);
    })
}

/**
 * ajax put method - name
 * ajax 요청 데이터를 업데이트 함
 */
function updateName(){
    console.log("[updateName] start");
    $.ajax({
        url: "/user",
        type: "put",
        data: JSON.stringify({"option":"name","newName":$("#updateName").val()}),
        contentType:"application/json; charset=utf-8;"
    }).done((result)=>{
        $("#updateName").val("");
        alert("이름 변경에 성공했습니다.");
    }).fail((result)=>{
        console.log("[!] name update failed.");
        const error = JSON.parse(result.responseText);  // 날아온 JSON 텍스트 데이터를 JSON 객체로 변환해줌
        alert(error.message);
    })
}