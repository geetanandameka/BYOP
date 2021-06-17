package com.example.url.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class ResponseBean<T> {
    private String status;
    private T data;
    private int statusCode;
    private String userMsg;
    private String statusMsg;
}
