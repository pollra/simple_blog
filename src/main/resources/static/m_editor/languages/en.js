(function(){
    var factory = function (exports) {
        var lang = {
            name : "en",
            description : "오픈소스 온라인 마크다운 에디터.",
            tocTitle    : "목차",
            toolbar : {
                undo             : "작업취소(Ctrl+Z)",
                redo             : "되돌리기(Ctrl+Y)",
                bold             : "굵게",
                del              : "취소선",
                italic           : "기울이기",
                quote            : "인용 블록",
                ucwords          : "첫 글자를 대문자로 변환",
                uppercase        : "선택한 텍스트를 대문자로 변환",
                lowercase        : "선택한 텍스트를 소문자로 변환",
                h1               : "제목 1",
                h2               : "제목 2",
                h3               : "제목 3",
                h4               : "제목 4",
                h5               : "제목 5",
                h6               : "제목 6",
                "list-ul"        : "숫자가 없는 목록",
                "list-ol"        : "순서가 지정된 목록",
                hr               : "구분 선",
                link             : "하이퍼링크",
                "reference-link" : "참조 링크",
                image            : "이미지",
                code             : "코드로 보기",
                "preformatted-text" : "미리 서식이 지정된 텍스트 / 코드 블록 (탭 들여 쓰기)",
                "code-block"     : "코드 블록(다중언어)",
                table            : "표 만들기",
                datetime         : "현재 시간 데이터 표시",
                emoji            : "이모티콘",
                "html-entities"  : "HTML 개체",
                pagebreak        : "Page break",
                watch            : "Unwatch",
                unwatch          : "Watch",
                preview          : "HTML 미리보기 (Press Shift + ESC exit)",
                fullscreen       : "전체화면 (Press ESC exit)",
                clear            : "초기화",
                search           : "검색",
                help             : "도움말",
                info             : "About " + exports.title
            },
            buttons : {
                enter  : "입력",
                cancel : "취소",
                close  : "닫기"
            },
            dialog : {
                link : {
                    title    : "link",
                    url      : "address",
                    urlTitle : "title",
                    urlEmpty : "Error: 링크 주소를 입력하세요."
                },
                referenceLink : {
                    title    : "Reference link",
                    name     : "name",
                    url      : "address",
                    urlId    : "ID",
                    urlTitle : "Title",
                    nameEmpty: "Error: 참조 이름은 비워둘 수 없습니다.",
                    idEmpty  : "Error: 참조 링크 ID를 입력하세요",
                    urlEmpty : "Error: 참조 링크 URL 주소를 입력하세요"
                },
                image : {
                    title    : "image",
                    url      : "address",
                    link     : "link",
                    alt      : "title",
                    uploadButton     : "업로드",
                    imageURLEmpty    : "Error: 사진 URL 주소는 비워 둘 수 없습니다.",
                    uploadFileEmpty  : "Error: 업로드 사진은 비어있을 수 없습니다.",
                    formatNotAllowed : "Error: 오직 사진 파일을 업로드 할 수 있습니다 허용 된 이미지 파일 형식을 업로드 :"
                },
                preformattedText : {
                    title             : "미리 서식이 지정된 텍스트 / 코드",
                    emptyAlert        : "Error: 미리 서식이 지정된 텍스트 또는 코드의 내용을 입력하십시오."
                },
                codeBlock : {
                    title             : "코드 블록",
                    selectLabel       : "언어: ",
                    selectDefaultText : "코드 언어를 선택하십시오 ...",
                    otherLanguage     : "다른 언어들",
                    unselectedLanguageAlert : "Error: 코드 언어를 선택하십시오.",
                    codeEmptyAlert    : "Error: 코드 내용을 입력하십시오."
                },
                htmlEntities : {
                    title : "HTML Entities"
                },
                help : {
                    title : "도움말"
                }
            }
        };
        
        exports.defaults.lang = lang;
    };
    
	// CommonJS/Node.js
	if (typeof require === "function" && typeof exports === "object" && typeof module === "object")
    { 
        module.exports = factory;
    }
	else if (typeof define === "function")  // AMD/CMD/Sea.js
    {
		if (define.amd) { // for Require.js

			define(["editormd"], function(editormd) {
                factory(editormd);
            });

		} else { // for Sea.js
			define(function(require) {
                var editormd = require("../editormd");
                factory(editormd);
            });
		}
	} 
	else
	{
        factory(window.editormd);
	}
    
})();