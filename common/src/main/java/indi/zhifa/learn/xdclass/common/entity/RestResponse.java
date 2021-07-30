package indi.zhifa.learn.xdclass.common.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

/**
 * R Created by IntelliJ IDEA.
 *
 * @Author: 芝法酱
 * @Create 2021/3/17 10:33
 */
@Slf4j
@Data
@Schema(name = "通用返回")
public class RestResponse<T>{
    @Schema(name = "返回数据")
    private T data;
    @Schema(name = "返回码")
    private int code;
    @Schema(name = "已知逻辑错误的返回码")
    private int errorCode;
    @Schema(name = "状态信息")
    private String msg;

    public RestResponse() {
        code = HttpStatus.OK.value();
    }

    public RestResponse(T pData) {
        code = HttpStatus.OK.value();
        data = pData;
    }

    public RestResponse(int pCode, int pErrCode, String pErrMsg) {
        code = pCode;
        errorCode = pErrCode;
        msg = pErrMsg;
    }

    public static RestResponse ok() {
        RestResponse r = new RestResponse();
        return r;
    }

    public static <T> RestResponse ok(T pData) {
        RestResponse r = new RestResponse(pData);
        return r;
    }

    public static RestResponse clientError(String pErrMsg) {
        return clientError(0,pErrMsg);
    }

    public static RestResponse clientError(int pErrCode, String pErrMsg) {
        RestResponse r = new RestResponse(HttpStatus.BAD_REQUEST.value(), pErrCode, pErrMsg);
        return r;
    }

    public static RestResponse error(String pErrMsg) {
        RestResponse r = new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), 0, pErrMsg);
        return r;
    }

    public static RestResponse serverError(int pErrCode, String pErrMsg) {
        RestResponse r = new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), pErrCode, pErrMsg);
        return r;
    }

    public static RestResponse notImplement() {
        RestResponse r = new RestResponse(HttpStatus.NOT_IMPLEMENTED.value(),
                0,
                "该接口没有实现");
        return r;
    }

    public static RestResponse unAuthorise(int pErrCode, String pErrMsg) {
        RestResponse r = new RestResponse(HttpStatus.UNAUTHORIZED.value(),
                pErrCode,
                pErrMsg);
        return r;
    }

}
