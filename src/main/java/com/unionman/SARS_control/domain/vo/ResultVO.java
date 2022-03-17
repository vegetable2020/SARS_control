package com.unionman.SARS_control.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.unionman.SARS_control.constant.ResultEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据格式返回统一
 * @author weiming.wu
 * @date 2020/10/14 下午2:00
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 异常码
     */
    private Integer status;

    /**
     * 描述
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 成功的构造器
     */
    public ResultVO() {
        this.status = ResultEnum.SUCCESS.getCode();
        this.message= ResultEnum.SUCCESS.getMessage();
    }


    public ResultVO(Integer status, String msg) {
        this.status = status;
        this.message = msg;
    }


    public ResultVO(Integer status, String msg, T data) {
        this.status = status;
        this.message = msg;
        this.data = data;
    }

    /**
     * 请求成功/失败的构造方式
     * @param ResultEnum
     */
    public ResultVO(ResultEnum ResultEnum) {
        this.status = ResultEnum.getCode();
        this.message = ResultEnum.getMessage();
    }

    /**
     * 请求成功的构造方式
     * @param ResultEnum
     * @param data
     */
    public ResultVO(ResultEnum ResultEnum, T data) {
        this.status = ResultEnum.getCode();
        this.message = ResultEnum.getMessage();
        this.data = data;
    }

    public static <T> ResultVO<T> success(){
        return new ResultVO<>(ResultEnum.SUCCESS);
    }

    public static <T> ResultVO<T> success(T data){
        return new ResultVO<>(ResultEnum.SUCCESS, data);
    }

    public static <T> ResultVO<T> error(T data){
        return new ResultVO<>(ResultEnum.ERROR, data);
    }

    public static <T> ResultVO<T> success(int status, String msg){
        return new ResultVO<>(status, msg);
    }

    public static <T> ResultVO<T> error(int status, String msg){
        return new ResultVO<>(status, msg);
    }

    public static <T> ResultVO<T> error(ResultEnum ResultEnum){
        return new ResultVO<>(ResultEnum);
    }

    public static ResultVO<?> error(ResultEnum ResultEnum, Object data){
        return new ResultVO<>(ResultEnum, data);
    }


}
