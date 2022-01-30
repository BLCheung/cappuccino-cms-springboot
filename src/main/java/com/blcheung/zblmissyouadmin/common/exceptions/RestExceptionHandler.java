package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
import com.blcheung.zblmissyouadmin.common.configuration.CodeConfiguration;
import com.blcheung.zblmissyouadmin.kit.ResultKit;
import com.blcheung.zblmissyouadmin.util.RequestUtil;
import com.blcheung.zblmissyouadmin.vo.common.ErrorVO;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
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

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @Value("${spring.servlet.multipart.max-request-size}")
    private String maxRequestSize;

    /**
     * 处理通用异常
     *
     * @param request
     * @param response
     * @param exception
     * @return com.blcheung.zblmissyouadmin.vo.common.ErrorVO
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
     * @return com.blcheung.zblmissyouadmin.vo.common.ErrorVO
     * @author BLCheung
     * @date 2021/12/9 8:43 下午
     */
    @ExceptionHandler({ HttpException.class })
    public ErrorVO handleException(HttpServletRequest request, HttpServletResponse response, HttpException exception) {
        // 错误码
        Integer code = exception.getCode();
        // 自定义消息
        String msg = exception.getMsg();
        // 状态码
        int statusCode = null == exception.getStatusCode()
                ? HttpStatus.INTERNAL_SERVER_ERROR.value()
                : exception.getStatusCode();
        // 默认消息，从code码取或异常自带
        String defaultMsg = StringUtils.hasText(CodeConfiguration.getMessage(code))
                ? CodeConfiguration.getMessage(code)
                : exception.getMessage();
        // 是否为默认消息
        Boolean isDefaultMsg = exception.isDefaultMsg;

        ErrorVO errorVO = ResultKit.reject(code, isDefaultMsg ? defaultMsg : msg);
        errorVO.setRequest(RequestUtil.getActionRequest(request));

        response.setStatus(statusCode);

        return errorVO;
    }

    /**
     * 捕捉普通参数（非java bean）校验错误时抛出的异常
     *
     * @param request
     * @param response
     * @param exception
     * @return com.blcheung.zblmissyouadmin.vo.common.ErrorVO
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
     * @return com.blcheung.zblmissyouadmin.vo.common.ErrorVO
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
     * @return com.blcheung.zblmissyouadmin.vo.common.ErrorVO
     * @author BLCheung
     * @date 2021/12/9 9:58 下午
     */
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ErrorVO handleException(HttpServletRequest request, HttpServletResponse response,
                                   MethodArgumentTypeMismatchException exception) {
        String message = exception.getName() + Code.ARGUMENT_TYPE_ERROR.getDesc();
        ErrorVO errorVO = ResultKit.reject(Code.ARGUMENT_TYPE_ERROR.getCode(), message);
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
     * @return com.blcheung.zblmissyouadmin.vo.common.ErrorVO
     * @author BLCheung
     * @date 2021/12/9 11:07 下午
     */
    @ExceptionHandler({ TypeMismatchException.class })
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
     * @return com.blcheung.zblmissyouadmin.vo.common.ErrorVO
     * @author BLCheung
     * @date 2021/12/9 10:31 下午
     */
    @ExceptionHandler({ MissingServletRequestParameterException.class })
    public ErrorVO handleException(HttpServletRequest request, HttpServletResponse response,
                                   MissingServletRequestParameterException exception) {
        String message = Code.MISSING_REQUEST_PARAMETER_ERROR.getDesc() + ": " + exception.getParameterName();
        ErrorVO errorVO = ResultKit.reject(Code.MISSING_REQUEST_PARAMETER_ERROR.getCode(), message);
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
     * @return com.blcheung.zblmissyouadmin.vo.common.ErrorVO
     * @author BLCheung
     * @date 2021/12/9 10:40 下午
     */
    @ExceptionHandler({ ServletException.class })
    public ErrorVO handleException(HttpServletRequest request, HttpServletResponse response,
                                   ServletException exception) {
        ErrorVO errorVO = ResultKit.reject(Code.PARAMETER_ERROR.getCode(), exception.getLocalizedMessage());
        errorVO.setRequest(RequestUtil.getActionRequest(request));

        response.setStatus(HttpStatus.BAD_REQUEST.value());

        return errorVO;
    }

    /**
     * 处理请求没有对应控制器api方法时的异常
     * 在路径不会被匹配的时候才会抛出NoHandlerFoundException异常，
     * 但是由于默认的匹配路径有/**，所以即使你的地址错误，
     * 仍然会匹配到 /**这个静态资源映射地址，就不会进入noHandlerFound方法，
     * 自然不会抛出NoHandlerFoundException了，
     * 所以需要在配置文件进行404异常配置和关闭静态资源映射
     *
     * @param request
     * @param response
     * @param exception
     * @return com.blcheung.zblmissyouadmin.vo.common.ErrorVO
     * @author BLCheung
     * @date 2021/12/9 10:58 下午
     */
    @ExceptionHandler({ NoHandlerFoundException.class })
    public ErrorVO handleException(HttpServletRequest request, HttpServletResponse response,
                                   NoHandlerFoundException exception) {
        ErrorVO errorVO = ResultKit.reject(Code.METHOD_NOT_FOUND.getCode(), Code.METHOD_NOT_FOUND.getDesc());
        errorVO.setRequest(RequestUtil.getActionRequest(request));

        response.setStatus(HttpStatus.NOT_FOUND.value());

        return errorVO;
    }

    /**
     * 处理body请求体的异常
     *
     * @param request
     * @param response
     * @param exception
     * @return com.blcheung.zblmissyouadmin.vo.common.ErrorVO
     * @author BLCheung
     * @date 2021/12/9 10:48 下午
     */
    @ExceptionHandler({ HttpMessageNotReadableException.class })
    public ErrorVO handleException(HttpServletRequest request, HttpServletResponse response,
                                   HttpMessageNotReadableException exception) {
        Throwable cause = exception.getCause();
        String message;
        Integer code;
        // body请求体存在字段类型错误等
        if (cause instanceof MismatchedInputException) {
            MismatchedInputException e = (MismatchedInputException) cause;
            code    = Code.ARGUMENT_TYPE_ERROR.getCode();
            message = this.formatMismatchedInputFieldsMessage(e.getPath()) + Code.ARGUMENT_TYPE_ERROR.getDesc();
        } else {
            // 无body请求体或缺失字段等错误
            code    = Code.MISSING_REQUEST_PARAMETER_ERROR.getCode();
            message = Code.MISSING_REQUEST_PARAMETER_ERROR.getDesc();
        }

        ErrorVO errorVO = ResultKit.reject(code, message);
        errorVO.setRequest(RequestUtil.getActionRequest(request));

        response.setStatus(HttpStatus.BAD_REQUEST.value());

        return errorVO;
    }

    /**
     * 上传的文件体积超过限制异常
     *
     * @param request
     * @param response
     * @param exception
     * @return com.blcheung.zblmissyouadmin.vo.common.ErrorVO
     * @author BLCheung
     * @date 2022/1/19 8:03 下午
     */
    @ExceptionHandler({ MaxUploadSizeExceededException.class })
    public ErrorVO handleException(HttpServletRequest request, HttpServletResponse response,
                                   MaxUploadSizeExceededException exception) {
        Throwable rootCause = exception.getRootCause();
        String message;
        if (rootCause instanceof FileSizeLimitExceededException) {
            message = CodeConfiguration.getMessage(10026) + this.maxFileSize;
        } else {
            message = CodeConfiguration.getMessage(10027) + this.maxRequestSize;
        }

        ErrorVO errorVO = ResultKit.reject(10024, message);
        errorVO.setRequest(RequestUtil.getActionRequest(request));

        response.setStatus(HttpStatus.PAYLOAD_TOO_LARGE.value());

        return errorVO;
    }


    private String formatAllBeanValidatorErrorMessage(List<ObjectError> errors) {
        StringBuffer errorMsg = new StringBuffer();
        errors.forEach(error -> errorMsg.append(error.getDefaultMessage())
                                        .append(";"));
        return errorMsg.toString();
    }

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

    private String formatMismatchedInputFieldsMessage(List<JsonMappingException.Reference> path) {
        StringBuffer errorMsg = new StringBuffer();
        errorMsg.append("[");
        String fieldsStr = org.apache.commons.lang3.StringUtils.join(path.stream()
                                                                         .map(JsonMappingException.Reference::getFieldName)
                                                                         .toArray(String[]::new));
        errorMsg.append(fieldsStr)
                .append("]");

        return errorMsg.toString();
    }
}
