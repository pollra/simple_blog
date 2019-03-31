package com.pollra.http.comment.domain;

import lombok.*;

@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentVO {
    private int num;
    private int board;
    private String writer;
    private String date;
    private String content;
    private String password;

    public void setWriter(String writer) {
        this.writer = writer.equals("0:0:0:0:0:0:0:1") ? "관리자" : writer;
    }

    public String board_content_password_check(){
        String result = "";
        if(this.board == 0) result+= "board";
        if(this.content == "") result+= "content";
        if(this.password == "") result+= "password";
        return result;
    }
}
