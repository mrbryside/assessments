package com.kbtg.bootcamp.posttest.shared.response;

import org.springframework.http.ResponseEntity;

public class Response {
    public static <T> ResponseEntity<T> createdWithBody(T body) {
        return ResponseEntity.created(null).body(body);
    }
}
