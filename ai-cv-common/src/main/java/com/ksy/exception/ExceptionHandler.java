package com.ksy.exception;

import com.ksy.result.CvResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ServerWebInputException;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常信息处理
 *
 * @author csy 2023/3/22 15:11
 */
public class ExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandler.class);

    /**
     * 处理Controller层抛出的异常，捕获自定义与其他异常信息，并返回相应处理结果
     *
     * @param e 异常信息
     * @return 返回结果
     * @author csy 2024/3/22 15:13
     * @since 1.0.0
     */
    public static CvResult execute(Exception e) {
        LOG.debug("ExceptionHandler>>", e.getMessage());
        // 如果不是自定义异常，则打印相关异常信息
        if (!((e instanceof BusinessException)
                || (e instanceof ParameterException))) {
            try {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                LOG.error("Exception -- 500 >\r\n" + sw + "\r\n");
            } catch (Exception e2) {
                LOG.error("Exception catch -- 500 >" + e2);
                e2.printStackTrace();
            }
        }

        // 判断具体错误，抛出对应异常
        if (e instanceof BusinessException) {
            BusinessException currentException = (BusinessException) e;
            return CvResult.failed(currentException.getCode(), currentException.getMessage());
        }
        if (e instanceof ServerWebInputException) {
            CvResult response = CvResult.failed(400, "网络不稳定，请稍后重试！");
            return response;
        }
        if (e instanceof ParameterException) {
            ParameterException currentException = (ParameterException) e;
            CvResult response = CvResult.failed(400, currentException.getMessage());
            return response;
        } else {
            LOG.debug("Exception:{}", e.getMessage());
            CvResult response = CvResult.failed(500, "网络不稳定，请稍后重试！");
            return response;
        }
    }
}
