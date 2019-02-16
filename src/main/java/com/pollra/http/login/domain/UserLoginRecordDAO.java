package com.pollra.http.login.domain;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLoginRecordDAO {
    private int num;
    private String id;
    private String pw;
    private int authority;
    private String name;
    private String ip;      // 최근 접속 IP
}
