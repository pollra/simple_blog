package com.pollra.http.category.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryVO {
    private int num;
    private int parent;
    private int level;
    private String name;
    private String url;
    private int visible;

    public CategoryVO(int parent, int level, String name, String url) {
        this.parent = parent;
        this.level = level;
        this.name = name;
        this.url = url;
    }

    public int check(){
        if(name.trim().equals("")) return 0;
        return 1;
    }
}
