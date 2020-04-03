package com.imooc.mall.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.imooc.mall.enums.ResponseEnum;
import lombok.Data;
import org.springframework.validation.BindingResult;

/**
 * 返回给前段的对象
 *
 * @param <T>
 */
@Data
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class ResponseVo<T> {
    private  Integer state;

    private String msg;

    private T data;

    public  ResponseVo(Integer state, String msg) {
        this.state = state;
        this.msg = msg;
    }
    public  ResponseVo(Integer state, T data) {
        this.state = state;
        this.data = data;
    }
    public static <T>ResponseVo<T> successByMsg(String msg){
        return new ResponseVo<T>(ResponseEnum.SUCCESS.getCode(),msg);

    }
    public static <T>ResponseVo<T> success(T data){
        return new ResponseVo<T>(ResponseEnum.SUCCESS.getCode(),data);

    }
    public static <T>ResponseVo<T> successByMsg(){
        return new ResponseVo<T>(ResponseEnum.SUCCESS.getCode(),ResponseEnum.SUCCESS.getDesc());

    }
    public static <T>ResponseVo<T> error(ResponseEnum responseEnum ){
        return new ResponseVo<T>(ResponseEnum.SUCCESS.getCode(),responseEnum.getDesc());

    }
    public static <T>ResponseVo<T> error(ResponseEnum responseEnum ,String msg ){
        return new ResponseVo<T>(ResponseEnum.SUCCESS.getCode(),msg);

    }
    public static <T>ResponseVo<T> error(ResponseEnum responseEnum , BindingResult bindingResult ){
        return new ResponseVo<T>(ResponseEnum.SUCCESS.getCode(),bindingResult.getFieldError()+" "+bindingResult.getFieldError().getDefaultMessage());

    }

}
