package com.blog.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@SuppressWarnings("all")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;

    private String msg;

    private T data;

    /**
     * 返回成功数据（status：200）
     *
     * @param data
     * @param <T>
     * @return Result实例
     */
    public static <T> Result success(T data) {
        return new Result().setCode(ResultStatusEnum.SUCCESS.getCode()).setMsg(ResultStatusEnum.SUCCESS.getValue()).setData(data);
    }

    public static <T> Result success(Integer code, String msg) {
        return new Result().setCode(code).setMsg(msg);
    }

    public static <T> Result fail(T data) {
        return new Result().setCode(ResultStatusEnum.FAIL.getCode()).setMsg(ResultStatusEnum.FAIL.getValue()).setData(data);
    }

    /**
     * 自定义返回错误信息
     *
     * @param code
     * @param msg
     * @return Result实例
     */
    public static Result error(Integer code, String msg) {
        return new Result().setCode(code).setMsg(msg);
    }


    /**
     * 自定义返回错误信息
     * @param code
     * @param meg
     * @param data
     * @return Result实例
     */
    public static <T> Result error(Integer code, String meg, T data) {
        return new Result().setCode(code).setMsg(meg).setData(data);
    }

}
