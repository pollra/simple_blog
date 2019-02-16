package com.pollra.http.login.domain;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginRecordVO {
    private int num;        // pk
    private String user;    // fk
    private String ip;
}
