package com.ksy.result;

import com.ksy.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Properties;

/**
 * 返回数据的父类
 *
 * @author csy
 * @version 1.0.0
 * @time 2024/3/22 10:57
 *
 * @param <T>
 */
public class CvResult<T> implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(CvResult.class);
    /**
     * 响应码
     */
    private int code;
    /**
     * 响应消息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 抛出异常的服务
     */
    private String serverName;
    /**
     * 配置信息，用于获取服务名称
     */
    private static Properties properties;

    public static void setProperties(Properties properties) {
        CvResult.properties = properties;
    }

    /**
     * 返回不带数据的响应成功结构
     *
     * @return 返回code=200、message=ok的返回结构
     * @author sushuang 2024/3/22 10:57
     */
    public static CvResult success() {
        CvResult instance = new CvResult();
        instance.setCode(200);
        instance.setMessage("ok");
        instance.setServerName(instance.getServerName());

        // 打印返回信息日志
        instance.log();

        return instance;
    }

    /**
     * 返回带数据的响应成功结构
     *
     * @param data 响应的数据结构
     * @return 返回code=200、message=ok、data=【入参】data的返回结构
     * @author csy 2024/3/22 16:39
     */
    public static <T> CvResult success(T data) {
        CvResult instance = new CvResult();
        instance.setCode(200);
        instance.setMessage("ok");
        instance.setData(data);

        // 打印返回信息日志
        instance.log();

        return instance;
    }

    /**
     * 返回500网络不稳定错误信息
     * @author csy
     * @version 1.0.0
     * @author csy 2024/3/22 16:39
     *
     * @return
     */
    public static <T> CvResult failed() {
        return CvResult.failed(500, "网络不稳定，请稍后重试！");
    }

    /**
     * 返回指定错误信息
     *
     * @param message 错误信息
     * @return 返回code=1000、message=[入参]message的返回结构
     * @author csy 2024/3/22 16:40
     */
    public static <T> CvResult failed(String message) {
        CvResult instance = new CvResult();
        instance.setCode(1000);
        instance.setMessage(message);

        // 打印返回信息日志
        instance.log();

        return instance;
    }

    /**
     * 熔断器异常信息
     * @param serverName 异常服务名称
     * @return
     */
    public static <T> CvResult failedHystrix(String serverName) {
        CvResult instance = new CvResult();
        instance.setCode(1000);
        instance.setMessage("网络不稳定，请稍后重试！");
        instance.setServerName(serverName);

        // 打印返回信息日志
        instance.log();

        return instance;
    }

    /**
     * 返回指定错误信息
     *
     * @param code    错误码
     * @param message 错误信息
     * @return 返回code=【入参】错误码、message=【入参】message的返回结构
     * @author csy 2024/3/22 16:40
     */
    public static <T> CvResult failed(int code, String message) {
        CvResult instance = new CvResult();
        instance.setCode(code);
        instance.setMessage(message);

        // 打印返回信息日志
        instance.log();

        return instance;
    }

    /**
     * 返回指定错误信息
     *
     * @param code    错误码
     * @param message 错误信息
     * @return 返回code=【入参】错误码、message=【入参】message的返回结构
     * @author csy 2024/3/22 16:40
     */
    public static <T> CvResult failed(int code, String message, String serverName) {
        CvResult instance = new CvResult();
        instance.setCode(code);
        instance.setMessage(message);
        instance.setServerName(serverName);

        // 打印返回信息日志
        instance.log();

        return instance;
    }

    /**
     * 返回业务异常数据
     *
     * @param be 业务异常{@link BusinessException}
     * @return 返回业务异常编码与编码对应的错误内容的返回信息
     */
    public static <T> CvResult failed(BusinessException be) {
        CvResult instance = new CvResult();
        instance.setCode(be.getCode());
        instance.setMessage(be.getMessage());

        // 打印返回信息日志
        instance.log();

        return instance;
    }

    /**
     * 返回异常数据
     *
     * @param t 返回未知异常错误
     * @return 返回业务异常编码与编码对应的错误内容的返回信息
     */
    public static <T> CvResult failed(Throwable t) {
        CvResult instance = new CvResult();
        instance.setCode(1000);
        instance.setMessage(t.getMessage());

        // 打印返回信息日志
        instance.log();
        return instance;
    }

    /**
     * 抛出异常
     * @author csy
     * @version 1.0.0
     * @time 2024/3/22 11:35
     *
     */
    public void throwError() {
        if (this.getCode() != 200) {
            LOG.debug("error -> server-name:" + serverName);
            throw new BusinessException(this.getCode(), this.getMessage(), serverName);
        }
    }

    /**
     * 抛出异常
     *
     * @author CvResult
     * @version 1.0.0
     * @time 2024/3/22 16:40
     *
     * @param serverName 异常服务名称
     */
    public void throwError(String serverName) {
        if (this.getCode() != 200) {
            LOG.debug("error -> server-name:" + serverName);
            throw new BusinessException(this.getCode(), this.getMessage(), serverName);
        }
    }

    protected void log() {
//        LOG.debug("response ->" + this.toString());
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getServerName() {
        if ((serverName == null || "".equals(serverName)) && null != properties) {
            String name = properties.getProperty("spring.application.name");
            String port = properties.getProperty("server.port");

            if (name != null && port != null) {
                serverName = name + "-" + port.substring(2, 4);
            }
        }
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ZliResult{");
        sb.append("code=").append(code);
        sb.append(", message='").append(message).append('\'');
        sb.append(", data=").append(data);
        sb.append(", serverName='").append(serverName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
