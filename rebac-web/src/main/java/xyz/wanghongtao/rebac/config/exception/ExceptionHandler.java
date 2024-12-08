package xyz.wanghongtao.rebac.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.wanghongtao.rebac.exception.CustomException;
import xyz.wanghongtao.rebac.exception.ErrorCode;
import xyz.wanghongtao.rebac.object.viewObject.Result;

import static xyz.wanghongtao.rebac.exception.ErrorCode.PARAM_ERROR;

/**
 * @author wanghongtao
 * @data 2023/7/16 17:41
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
    public Result<?> handCustomException(CustomException e) {
        log.error(e.getMsg());
        return Result.fail(e.getCode(),e.getMsg());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();
        bindingResult.getFieldErrors().forEach(item -> stringBuilder.append(item.getField()).append(": ").append(item.getDefaultMessage()).append(";"));
        log.error(stringBuilder.toString());
        return Result.fail(PARAM_ERROR.getCode(), stringBuilder.toString());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public Result<?> handleBindException(BindException bindException) {
        BindingResult bindingResult = bindException.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();
        bindingResult.getFieldErrors().forEach(item -> stringBuilder.append(item.getField()).append(": ").append(item.getDefaultMessage()).append(";"));
        log.error(stringBuilder.toString());
        return Result.fail(PARAM_ERROR.getCode(), stringBuilder.toString());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public Result<?> handleException(InterruptedException e) {
      return Result.fail("500", "内存超出限制");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public Result<?> defaultHandler(Exception e) {
        e.printStackTrace();
        return Result.fail(ErrorCode.ERROR);
    }
}
