package com.blcheung.zblmissyouadmin.kit;

import com.blcheung.zblmissyouadmin.common.Code;
import com.blcheung.zblmissyouadmin.vo.*;

/**
 * 统一结果返回辅助类
 *
 * @author BLCheung
 * @date 2021/12/2 2:46 上午
 */
@SuppressWarnings("unchecked")
public class ResultKit {

    public static <T> ResultVO<T> resolve(T data) {
        return (ResultVO<T>) ResultVO.builder()
                                     .code(Code.OK.getCode())
                                     .msg(Code.OK.getDesc())
                                     .data(data)
                                     .build();
    }

    public static <T> ResultVO<T> resolve(Integer code, T data) {
        return (ResultVO<T>) ResultVO.builder()
                                     .code(code)
                                     .msg(null)
                                     .data(data)
                                     .build();
    }

    public static ErrorVO reject(Integer code, String msg) {
        return new ErrorVO(code, msg);
    }

    public static <T> ErrorVO reject(Integer code, String msg, T data) {
        return new ErrorVO(code, msg, data);
    }

    public static CreatedVO resolveCreated() {
        return new CreatedVO();
    }

    public static <T> CreatedVO resolveCreated(T data) {
        return new CreatedVO(data);
    }

    public static <T> CreatedVO resolveCreated(Integer code, T data) {
        return new CreatedVO(code, data);
    }

    public static CreatedVO resolveCreated(Integer code, String msg) {
        return new CreatedVO(code, msg);
    }

    public static <T> CreatedVO resolveCreated(Integer code, String msg, T data) {
        return new CreatedVO(code, msg, data);
    }


    public static UpdatedVO resolveUpdated() {
        return new UpdatedVO();
    }

    public static <T> UpdatedVO resolveUpdated(T data) {
        return new UpdatedVO(data);
    }

    public static <T> UpdatedVO resolveUpdated(Integer code, T data) {
        return new UpdatedVO(code, data);
    }

    public static UpdatedVO resolveUpdated(Integer code, String msg) {
        return new UpdatedVO(code, msg);
    }

    public static <T> UpdatedVO resolveUpdated(Integer code, String msg, T data) {
        return new UpdatedVO(code, msg, data);
    }


    public static DeletedVO resolveDeleted() {
        return new DeletedVO();
    }

    public static <T> DeletedVO resolveDeleted(T data) {
        return new DeletedVO(data);
    }

    public static <T> DeletedVO resolveDeleted(Integer code, T data) {
        return new DeletedVO(code, data);
    }

    public static DeletedVO resolveDeleted(Integer code, String msg) {
        return new DeletedVO(code, msg);
    }

    public static <T> DeletedVO resolveDeleted(Integer code, String msg, T data) {
        return new DeletedVO(code, msg, data);
    }
}
