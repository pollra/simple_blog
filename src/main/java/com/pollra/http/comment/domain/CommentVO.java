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
}
