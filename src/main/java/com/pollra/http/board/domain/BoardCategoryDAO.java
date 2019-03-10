package com.pollra.http.board.domain;

import lombok.*;

@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardCategoryDAO {
    private int num;
    private String name;
    private String title;
    private String date;
    private int visible;
}
