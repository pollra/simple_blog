package com.pollra.tool.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class InspectionTool {
    // 데이터를 넣기전에 한번 데이터를 검사한다.
    // 태그가 존재하면 태그를 치환하여 리턴한다.
    private static final Logger log = LoggerFactory.getLogger(InspectionTool.class);

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
            inputData = inputData.replace(entry.getKey().toString(),entry.getValue().toString());
        }
        return inputData;
    }

    /**
     * 일시적으로 일부 유저에게 닉네임을 지정해줌
     */
    public String whatYourName(HttpServletRequest request) {
        String writer = "";
        String logMessage = "";
        try{
            logMessage+= "x";
            writer = request.getHeader("x-real-ip");
            if(writer.equals("null")) throw new Exception();
            logMessage+= "xc";
        }catch(Exception e){
            logMessage+= "r";
            writer = request.getRemoteAddr();
            logMessage+= "rc";
        }

        Map<String, String> users = new HashMap<>();
        users.put("a","robunit");
        users.put("b","하얀수염");
        users.put("c","P R A D A");
        users.put("d","나는빡빡이다");
        for(Map.Entry entry : users.entrySet()){
            if(entry.getKey().equals(writer)){
                log.info("userReturn: "+entry.getValue().toString());
                return entry.getValue().toString();
            }
        }

        log.info(logMessage);
        return writer;
    }
}
