package com.ksy.result;

import org.springframework.beans.BeanUtils;

/**
 * 网关返回数据使用
 * @author csy
 * @version 1.0.0
 * @time 2024/3/22 15:16
 *
 * @param <T>
 */
public class ResultVo<T> extends CvResult<T>{
    /**
     * 返回不带数据的响应成功结构
     *
     * @return 返回code=200、message=ok的返回结构
     * @author csy 2024/3/22 16:33
     */
    public static ResultVo success() {
        CvResult success = CvResult.success();
        ResultVo r = new ResultVo();
        BeanUtils.copyProperties(success, r);
        return r;
    }

    /**
     * 返回带数据的响应成功结构
     *
     * @param data 响应的数据结构
     * @return 返回code=200、message=ok、data=【入参】data的返回结构
     * @author csy 2024/3/22 16:33
     */
    public static <T> ResultVo success(T data) {
        CvResult success = CvResult.success(data);
        ResultVo r = new ResultVo();
        BeanUtils.copyProperties(success, r);
        return r;
    }

    /**
     * 返回500网络不稳定错误信息
     * @author csy
     * @version 1.0.0
     * @time 2024/3/22 15:07
     *
     * @return
     */
    public static ResultVo failed() {
        CvResult failed = CvResult.failed(500, "网络不稳定，请稍后重试！");
        ResultVo r = new ResultVo();
        BeanUtils.copyProperties(failed, r);
        return r;
    }

    /**
     * 返回指定错误信息
     *
     * @param message 错误信息
     * @return 返回code=1000、message=[入参]message的返回结构
     * @author csy 2024/3/22 16:33
     */
    public static ResultVo failed(String message) {
        CvResult failed = CvResult.failed(message);
        ResultVo r = new ResultVo();
        BeanUtils.copyProperties(failed, r);
        return r;
    }

    /**
     * 返回指定错误信息
     *
     * @param code    错误码
     * @param message 错误信息
     * @return 返回code=【入参】错误码、message=【入参】message的返回结构
     * @author csy 2024/3/22 16:33
     */
    public static ResultVo failed(int code, String message) {
        CvResult failed = CvResult.failed(code, message);
        ResultVo r = new ResultVo();
        BeanUtils.copyProperties(failed, r);
        return r;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
