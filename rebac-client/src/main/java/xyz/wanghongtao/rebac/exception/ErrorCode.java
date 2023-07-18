package xyz.wanghongtao.rebac.exception;

public enum ErrorCode {
    PARAM_ERROR("400", "参数不合法"),
    USER_LOGIN_ERROR("401", "未登录"),
    IP_LIMIT("403","访问频繁"),
    ERROR("500", "服务器繁忙"),
    Model_NOT_EXIST("501","Model不存在"),
    PERMISSION_DENY("503","没有权限"),
    FILE_UPLOAD_FAILED("504","文件上传失败"),
    FILE_NOT_EXISTED("505","文件不存在"),
    Store_NOT_EXIST("506","Store不存在"),
    FILE_TYPE_NOT_EXISTED("507","文件分类不存在"),
    MAIL_ERROR("508","邮件发送失败"),
    USER_NOT_EXISTED("509", "用户不存在"),
    TIME_EXISTED("510", "结束时间不可早于当前时间"),
    Key_Generate_Error("511","密钥生成失败"),
    Add_Store_Error("511","密钥生成失败");


    private final String code;
    private final String msg;
    ErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}