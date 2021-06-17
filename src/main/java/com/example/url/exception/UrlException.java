package com.example.url.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UrlException extends RuntimeException {

    private String msgKey;
    private Object[] args;
}
