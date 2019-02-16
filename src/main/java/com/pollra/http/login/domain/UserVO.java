package com.pollra.http.login.domain;

import lombok.*;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.logging.Logger;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserVO {
    private int num;
    private String id;
    private String pw;
    private int authority;
    private String name;

    public UserVO(Map<String, Object> param) {
        id = getObject(param.get("userId")).toString();
        pw = getObject(param.get("userPw")).toString();
        name = getObject(param.get("userName")).toString();
        authority = 0;
    }

    public boolean check(){
        // 데이터 유효성 검사
        if(id.getBytes().length > 50 || id.getBytes().length == 0) return false;
        if(pw.getBytes().length > 50 || pw.getBytes().length == 0) return false;
        if(name.getBytes().length > 30 || name.getBytes().length == 0) return false;
        return true;
    }

    private Object getObject(Object value){
        if(value == null){
            return "";
        }
        return value;
    }
}
