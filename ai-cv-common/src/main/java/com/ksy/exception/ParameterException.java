package com.ksy.exception;

/**
 * 参数异常，通常在检查外部接口输入时抛出
 *
 * @author csy 2024/3/22 14:54
 */
public class ParameterException extends RuntimeException{
    private String paramterName;

    public String getParamterName() {
        return paramterName;
    }

    /**
     * 通过设置不合法参数的参数名创建异常<br>
     * <hr>
     * 开发时间：2024/3/22 14:54
     * 具体需求：校验一些直接通过param获取的参数不合法时抛出的异常
     * <hr>
     *
     * @param paramterName 不合法参数的参数名
     */
    public ParameterException(String paramterName) {
        super();
        this.paramterName = paramterName;
    }
}
