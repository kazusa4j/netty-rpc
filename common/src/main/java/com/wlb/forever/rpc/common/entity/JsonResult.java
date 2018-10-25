package com.wlb.forever.rpc.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: william
 * @Date: 18/10/18 11:02
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult {
    //成功
    public static final Integer SUCCESS = 0;
    public static final String SUCCESS_MESSAGE = "成功";
    //未处理异常
    public static final Integer ERROR = 1;
    public static final String ERROR_MESSAGE = "接口出现未处理异常！";
    //缺少参数
    public static final Integer ERROR_REQUIREDPARAM = 2;
    public static final String ERROR_REQUIREDPARAM_MESSAGE = "缺少参数！";
    //参数存在sql注入风险
    public static final Integer ERROR_SQLINJECTION = 3;
    public static final String ERROR_SQLINJECTION_MESSAGE = "输入参数存在SQL注入风险！";

    //返回状态码(0:成功,1:失败)
    private Integer status = SUCCESS;

    //返回描述
    private String desc = SUCCESS_MESSAGE;

    //返回数据
    private Object result = null;

}
