package com.pollra.http.board.domain;

import lombok.*;

import java.util.Date;

@Data
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardVO {
    private int num;
    private String writer;
    private String title;
    private String content;
    private String date;
    private int visible;
    private int category;
}
