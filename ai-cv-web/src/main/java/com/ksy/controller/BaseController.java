package com.ksy.controller;

import com.ksy.result.CvResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 拦截所有接口抛出的异常，所有Controller都需要继承这个类来达到异常拦截的效果<br>
 * <ul>
 * <li>所有Controller通过继承此类类处理异常</li>
 * <li>如果添加新的业务异常，需要在添加新的instanceof判断</li>
 * </ul>
 *
 * @author csy 2024/3/22 16:50
 */
public class BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    /**
     * 抛出异常的服务名称
     */
    @Deprecated
    public static String serverName;

    /**
     * 设置抛出异常的服务名称
     *
     * @param serverName 服务名称
     */
    public void setServerName(String serverName) {
        BaseController.serverName = serverName;
        LOG.debug("BaseController -> server-name:" + BaseController.serverName);
    }




    @ExceptionHandler(Exception.class)
    public CvResult AllExceptionHandler(Exception e) {
        e.printStackTrace();
        return com.ksy.exception.ExceptionHandler.execute(e);
    }
}
