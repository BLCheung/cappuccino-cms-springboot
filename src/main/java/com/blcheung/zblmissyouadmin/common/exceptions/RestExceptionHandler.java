package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
import com.blcheung.zblmissyouadmin.common.configuration.CodeConfiguration;
import com.blcheung.zblmissyouadmin.kit.ResultKit;
import com.blcheung.zblmissyouadmin.util.RequestUtil;
import com.blcheung.zblmissyouadmin.vo.ErrorVO;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author BLCheung
 * @date 2021/12/8 1:28 上午
 */
@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * 处理通用异常
     *
     * @param request
     * @param response
     * @param exception
     * @return com.blcheung.zblmissyouadmin.vo.ErrorVO
     * @author BLCheung
     * @date 2021/12/9 8:42 下午
     */
    @ExceptionHandler({ Exception.class })
    public ErrorVO handleException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        ErrorVO errorVO = ResultKit.reject(Code.INTERNAL_SERVER_ERROR.getCode(), Code.INTERNAL_SERVER_ERROR.getDesc());
        errorVO.setRequest(RequestUtil.getActionRequest(request));

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return errorVO;
    }

    /**
     * 处理Http异常
     *
     * @param request
     * @param response
     * @param exception
     * @return com.blcheung.zblmissyouadmin.vo.ErrorVO
     * @author BLCheung
     * @date 2021/12/9 8:43 下午
     */
    @ExceptionHandler({ HttpException.class })
    public ErrorVO handleException(HttpServletRequest request, HttpServletResponse response, HttpException exception) {
        Integer code = exception.getCode();
        String message = StringUtils.hasText(CodeConfiguration.getMessage(code))
                ? CodeConfiguration.getMessage(code)
                : exception.getMessage();

        ErrorVO errorVO = ResultKit.reject(code, message);
        errorVO.setRequest(RequestUtil.getActionRequest(request));

        response.setStatus(exception.getStatusCode());

        return errorVO;
    }

    /**
     * 捕捉普通参数（非java bean）校验错误时抛出的异常
     *
     * @param request
     * @param response
     * @param exception
     * @return com.blcheung.zblmissyouadmin.vo.ErrorVO
     * @author BLCheung
     * @date 2021/12/9 8:43 下午
     */
    @ExceptionHandler({ ConstraintViolationException.class })
    public ErrorVO handleException(HttpServletRequest request, HttpServletResponse response,
                                   ConstraintViolationException exception) {
        String message = this.formatAllConstraintViolationErrorMessage(exception);

        ErrorVO errorVO = ResultKit.reject(Code.PARAMETER_ERROR.getCode(), message);
        errorVO.setRequest(RequestUtil.getActionRequest(request));

        response.setStatus(HttpStatus.BAD_REQUEST.value());

        return errorVO;
    }

    /**
     * 捕捉将请求体（body）解析到java bean时出错则抛出的MethodArgumentNotValidException异常
     * 参数绑定到java bean时出错则抛出BindException异常
     * 而MethodArgumentNotValidException继承自BindException
     *
     * @param request
     * @param response
     * @param exception
     * @return com.blcheung.zblmissyouadmin.vo.ErrorVO
     * @author BLCheung
     * @date 2021/12/9 9:01 下午
     */
    @ExceptionHandler({ BindException.class })
    public ErrorVO handleException(HttpServletRequest request, HttpServletResponse response, BindException exception) {
        List<ObjectError> errors = exception.getBindingResult()
                                            .getAllErrors();
        String message = this.formatAllBeanValidatorErrorMessage(errors);

        ErrorVO errorVO = ResultKit.reject(Code.PARAMETER_ERROR.getCode(), message);
        errorVO.setRequest(RequestUtil.getActionRequest(request));

        response.setStatus(HttpStatus.BAD_REQUEST.value());

        return errorVO;
    }

    /**
     * 处理解析控制器方法时发生类型错误异常
     *
     * @param request
     * @param response
     * @param exception
     * @return com.blcheung.zblmissyouadmin.vo.ErrorVO
     * @author BLCheung
     * @date 2021/12/9 9:58 下午
     */
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ErrorVO handleException(HttpServletRequest request, HttpServletResponse response,
                                   MethodArgumentTypeMismatchException exception) {
        String message = exception.getValue() + CodeConfiguration.getMessage(10005);
        ErrorVO errorVO = ResultKit.reject(Code.PARAMETER_ERROR.getCode(), message);
        errorVO.setRequest(RequestUtil.getActionRequest(request));

        response.setStatus(HttpStatus.BAD_REQUEST.value());

        return errorVO;
    }

    /**
     * 处理bean实体属性类型错误异常
     *
     * @param request
     * @param response
     * @param exception
     * @return com.blcheung.zblmissyouadmin.vo.ErrorVO
     * @author BLCheung
     * @date 2021/12/9 11:07 下午
     */
    @ExceptionHandler(TypeMismatchException.class)
    public ErrorVO handleException(HttpServletRequest request, HttpServletResponse response,
                                   TypeMismatchException exception) {
        ErrorVO errorVO = ResultKit.reject(Code.PARAMETER_ERROR.getCode(), exception.getLocalizedMessage());
        errorVO.setRequest(RequestUtil.getActionRequest(request));

        response.setStatus(HttpStatus.BAD_REQUEST.value());

        return errorVO;
    }

    /**
     * 处理请求参数丢失异常
     *
     * @param request
     * @param response
     * @param exception
     * @return com.blcheung.zblmissyouadmin.vo.ErrorVO
     * @author BLCheung
     * @date 2021/12/9 10:31 下午
     */
    @ExceptionHandler({ MissingServletRequestParameterException.class })
    public ErrorVO handleException(HttpServletRequest request, HttpServletResponse response,
                                   MissingServletRequestParameterException exception) {
        ErrorVO errorVO = ResultKit.reject(Code.PARAMETER_ERROR.getCode(), CodeConfiguration.getMessage(10006));
        errorVO.setRequest(RequestUtil.getActionRequest(request));

        response.setStatus(HttpStatus.BAD_REQUEST.value());

        return errorVO;
    }

    /**
     * 处理定义servlet遇到困难时抛出的异常
     *
     * @param request
     * @param response
     * @param exception
     * @return com.blcheung.zblmissyouadmin.vo.ErrorVO
     * @author BLCheung
     * @date 2021/12/9 10:40 下午
     */
    @ExceptionHandler({ ServletException.class })
    public ErrorVO handleException(HttpServletRequest request, HttpServletResponse response,
                                   ServletException exception) {
        ErrorVO errorVO = ResultKit.reject(Code.FAIL.getCode(), exception.getLocalizedMessage());
        errorVO.setRequest(RequestUtil.getActionRequest(request));

        response.setStatus(HttpStatus.BAD_REQUEST.value());

        return errorVO;
    }

    /**
     * 处理请求没有对应控制器api方法时的异常
     *
     * @param request
     * @param response
     * @param exception
     * @return com.blcheung.zblmissyouadmin.vo.ErrorVO
     * @author BLCheung
     * @date 2021/12/9 10:58 下午
     */
    @ExceptionHandler({ NoHandlerFoundException.class })
    public ErrorVO handleException(HttpServletRequest request, HttpServletResponse response,
                                   NoHandlerFoundException exception) {
        ErrorVO errorVO = ResultKit.reject(Code.NOT_FOUND.getCode(), CodeConfiguration.getMessage(10007));
        errorVO.setRequest(RequestUtil.getActionRequest(request));

        response.setStatus(HttpStatus.NOT_FOUND.value());

        return errorVO;
    }

    /**
     * 处理无请求体的异常
     *
     * @param request
     * @param response
     * @param exception
     * @return com.blcheung.zblmissyouadmin.vo.ErrorVO
     * @author BLCheung
     * @date 2021/12/9 10:48 下午
     */
    @ExceptionHandler({ HttpMessageNotReadableException.class })
    public ErrorVO handleException(HttpServletRequest request, HttpServletResponse response,
                                   HttpMessageNotReadableException exception) {
        ErrorVO errorVO = ResultKit.reject(Code.PARAMETER_ERROR.getCode(), CodeConfiguration.getMessage(10006));
        errorVO.setRequest(RequestUtil.getActionRequest(request));

        response.setStatus(HttpStatus.BAD_REQUEST.value());

        return errorVO;
    }


    /**
     * 格式化body方式的参数验证错误
     *
     * @param errors
     * @return java.lang.String
     * @author BLCheung
     * @date 2021/12/9 8:44 下午
     */
    private String formatAllBeanValidatorErrorMessage(List<ObjectError> errors) {
        StringBuffer errorMsg = new StringBuffer();
        errors.forEach(error -> errorMsg.append(error.getDefaultMessage())
                                        .append(";"));
        return errorMsg.toString();
    }


    /**
     * 格式化query方式参数验证错误
     *
     * @param e
     * @return java.lang.String
     * @author BLCheung
     * @date 2021/12/9 8:44 下午
     */
    private String formatAllConstraintViolationErrorMessage(ConstraintViolationException e) {
        StringBuffer errorMsg = new StringBuffer();
        e.getConstraintViolations()
         .forEach(err -> {
             String[] propertyPath = err.getPropertyPath()
                                        .toString()
                                        .split("\\.");
             errorMsg.append(propertyPath[ propertyPath.length - 1 ])
                     .append(err.getMessage())
                     .append(";");
         });
        return errorMsg.toString();
    }
}
