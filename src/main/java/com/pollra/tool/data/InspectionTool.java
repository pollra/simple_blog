package com.pollra.tool.data;

import java.util.HashMap;
import java.util.Map;

public class InspectionTool {
    // 데이터를 넣기전에 한번 데이터를 검사한다.
    // 태그가 존재하면 태그를 치환하여 리턴한다.
    private Map<String, String> pattern;

    public InspectionTool() {
        pattern = new HashMap<>();

        pattern.put("<","&lt;");
        pattern.put(">","&gt;");
        pattern.put("$","&#36;");
        pattern.put("}","&#125;");
        pattern.put("{","&#123;");
    }

    public String stringDataTagCheck(String inputData){
        for(Map.Entry entry : pattern.entrySet()){
            inputData = inputData.replace("","");
        }
        return inputData;
    }
}
