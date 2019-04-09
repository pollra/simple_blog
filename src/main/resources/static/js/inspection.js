
function inspection(substitutionCharacter = ""){
    if(substitutionCharacter === ""){
        return "";
    }else{
        substitutionCharacter = substitutionCharacter.replace(/</gi,"&lt").replace(/>/gi,"&gt;");
        substitutionCharacter = substitutionCharacter.replace(/\$/gi,"&#36;").replace(/{/gi, "&#123;").replace(/{/gi, "&#125;");
        return substitutionCharacter;
    }
}