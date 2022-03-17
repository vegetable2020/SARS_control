package com.unionman.SARS_control.handler;

import com.unionman.SARS_control.domain.vo.ResultVO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: jinhao.xu
 * @date 2022/3/16 17:13
 */
@Controller
@ControllerAdvice
public class ValidParamExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultVO handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> errors = bindingResult.getFieldErrors();
        //初始化错误信息大小
        Map<String,String> errorMessage = new HashMap<>(errors.size());
        for (FieldError error: errors
        ) {
            errorMessage.put(error.getField(), error.getDefaultMessage());
        }
        return ResultVO.error(-1,errorMessage.toString());
    }
}