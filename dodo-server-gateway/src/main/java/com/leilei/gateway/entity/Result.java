package com.leilei.gateway.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lei
 * @create 2022-04-11 15:21
 * @desc
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result<T> {

    private Integer code;

    private String message;

    private T data;


    public static  <T> Result<T> success(T data) {
       return Result.<T>builder().data(data).code(0).message("操作成功!").build();
    }
    public static  <T> Result<T> success() {
       return Result.<T>builder().data(null).code(0).message("操作成功!").build();
    }
    public static  <T> Result<T> fail() {
       return Result.<T>builder().data(null).code(-1).message("操作失败!").build();

    } public static  <T> Result<T> fail(String message) {
       return Result.<T>builder().data(null).code(-1).message(message).build();
    }

}
