package com.pollra.http.comment.domain;

import lombok.*;

@Data
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentViewDTO {
    private int num;
    private String writer;
    private String date;
    private String content;
}
