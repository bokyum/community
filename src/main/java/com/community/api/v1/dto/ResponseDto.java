package com.community.api.v1.dto;

import lombok.Data;

@Data
public class ResponseDto<T, K> {
    T data;
    K error;

    public ResponseDto(T data, K error) {
        this.data = data;
        this.error = error;
    }
}
