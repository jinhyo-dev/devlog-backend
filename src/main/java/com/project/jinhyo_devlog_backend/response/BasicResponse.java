package com.project.jinhyo_devlog_backend.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicResponse {

    private Integer code;

    private HttpStatus httpStatus;

    private String message;

    private Integer count;

    private Object result;

}
