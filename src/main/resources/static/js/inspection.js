function inspection(substitutionCharacter = ""){
    if (substitutionCharacter === "") {
        return "";
    } else {
        substitutionCharacter = substitutionCharacter.replace(/&lt/gi, "<").replace(/&gt;/gi, ">");
        substitutionCharacter = substitutionCharacter.replace(/&#36;/gi, "\$").replace(/&#123;/gi, "{").replace(/&#125;/gi, "{");
        return substitutionCharacter;
    }
}