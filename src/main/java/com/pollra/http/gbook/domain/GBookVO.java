package com.pollra.http.gbook.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GBookVO {
    private int num;
    private String writer;
    private String contents;
    private String date;
    private String time;

    public int check(){
        if(writer == null || writer == "")
            return -1;
        if(contents == null || contents == "")
            contents = "작성된 글이 없습니다.";
        if(writer.equals("0:0:0:0:0:0:0:1"))
            writer = "관리자";
        return 1;
    }
}
