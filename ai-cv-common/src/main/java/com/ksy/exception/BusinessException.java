package com.ksy.exception;

/**
 * 业务异常，在业务逻辑执行中出现的业务相关的异常，需要停止业务处理的，通过抛出该异常来终止
 *
 * @author csy 2024/3/22 14:43
 */
public class BusinessException extends RuntimeException{
    private Integer code;
    /**
     * 报错的服务名称
     */
    private String serverName;

    public Integer getCode() {
        return this.code;
    }

    public String getServerName() {
        return serverName;
    }

    /**
     * 设置错误消息来创建异常，错误编码自动设置为1000
     *
     * @param message 错误消息
     */
    public BusinessException(String message) {
        super(message);
        this.code = 1000;
    }

    /**
     * 设置错误编码和错误消息来创建异常
     * <p/>
     * 并不是所有异常都需要错误编码，只有需要程序判断并自动处理的异常才使用错误编码<br/>
     * 如用户登录页面需要输入手机号和验证码，则服务器判断登录失败后，有可能是手机号错误，也有可能是验证码错误<br/>
     * 这时服务器必须告知客户端程序是对应的业务错误码,这样客户端程序可根据编码来高亮提示用户<br/>
     * 而如果是用户已经被冻结而不能登录，客户端程序不需要额外的判断，则无需验证码，直接通过msg提示错误内容即可
     *
     * @param code    错误编码
     * @param message 错误消息
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(Integer code, String message, String serverName) {
        super(message);
        this.code = code;
        this.serverName = serverName;
    }
}
