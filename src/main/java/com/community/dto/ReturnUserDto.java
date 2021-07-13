package com.community.dto;

import lombok.Data;

@Data
public class ReturnUserDto<T> {
    T data;
    String error;

    public ReturnUserDto(T data, String error) {
        this.data = data;
        this.error = error;
    }


}
