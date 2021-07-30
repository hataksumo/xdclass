package indi.zhifa.learn.xdclass.common.entity;

import lombok.Data;
import lombok.Getter;

/**
 * @author 芝法酱
 */
@Data
public class ServiceException extends RuntimeException {
    private final String msg;
    private final int code;

    public ServiceException(String msg) {
        super(msg);
        this.msg = msg;
        this.code = 500;
    }

    public ServiceException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = 500;
    }

    public ServiceException(int pErrorCode, String msg) {
        this.msg = msg;
        this.code = pErrorCode;
    }

}
