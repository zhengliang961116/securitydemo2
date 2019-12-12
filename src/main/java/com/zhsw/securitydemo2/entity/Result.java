package com.zhsw.securitydemo2.entity;

/**
 * @Author: zhengliang
 * @Description: 结果类
 * @Date: 2019/12/12 10:42
 */

public class Result {
    private String code;
    private String msg;
    private Object  Date;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getDate() {
        return Date;
    }

    public void setDate(Object date) {
        Date = date;
    }
}
