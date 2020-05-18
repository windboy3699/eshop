package com.example.eshop.admin.controller;

import com.example.eshop.admin.dto.ResponseDto;

public class BaseController {
    public ResponseDto<Object> createResponseDto(Integer code, String message) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        responseDto.setCode(code);
        responseDto.setMessage(message);
        responseDto.setData(null);
        return responseDto;
    }

    public ResponseDto<Object> createResponseDto(Integer code, String message, Object data) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        responseDto.setCode(code);
        responseDto.setMessage(message);
        responseDto.setData(data);
        return responseDto;
    }
}
