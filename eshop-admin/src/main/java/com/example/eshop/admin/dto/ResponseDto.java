package com.example.eshop.admin.dto;

public class ResponseDto<T> {
    private Integer code;
    private String message;
    private T data;

    public static ResponseDto<Object> create(Integer code, String message) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        responseDto.setCode(code);
        responseDto.setMessage(message);
        responseDto.setData(null);
        return responseDto;
    }

    public static ResponseDto<Object> create(Integer code, String message, Object data) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        responseDto.setCode(code);
        responseDto.setMessage(message);
        responseDto.setData(data);
        return responseDto;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
