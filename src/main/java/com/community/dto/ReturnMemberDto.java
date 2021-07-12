package com.community.dto;

import lombok.Data;

@Data
public class ReturnMemberDto<T> {
    T data;
    String error;

    public ReturnMemberDto(T data, String error) {
        this.data = data;
        this.error = error;
    }


}
