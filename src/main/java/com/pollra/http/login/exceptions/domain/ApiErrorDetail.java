package com.pollra.http.login.exceptions.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
public class ApiErrorDetail {
    private String message;
    private int code;
    @JsonFormat(shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Date timeStamp;
}
