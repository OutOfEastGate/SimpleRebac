package xyz.wanghongtao.rebac.exception;

/**
 * @author wanghongtao
 * @data 2023/7/16 16:29
 */
public class CustomException extends RuntimeException {
    String code;

    String msg;

    public CustomException(String msg) {
        this.code = "500";
        this.msg = msg;
    }
    public CustomException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CustomException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
