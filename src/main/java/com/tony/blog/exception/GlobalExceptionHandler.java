package com.tony.blog.exception;

import com.tony.blog.pojo.ResultInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

//@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {
//    @ExceptionHandler(NullPointerException.class)
//    @ResponseStatus(value = HttpStatus.)
    public ResultInfo handleException(NullPointerException e){
        System.out.println(e.getMessage());
        return new ResultInfo(null, true, "空指针");
    }
}
