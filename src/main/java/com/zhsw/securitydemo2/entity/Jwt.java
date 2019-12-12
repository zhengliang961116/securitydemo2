package com.zhsw.securitydemo2.entity;

import com.zhsw.securitydemo2.utils.JwtUtil;

/**
 * @Author: zhengliang
 * @Description: JWT
 * @Date: 2019/12/11 17:20
 */
public class Jwt {
    /* 头部 */
    private String header;
    /* 负载 */
    private String payload;
    /* 签名 */
    private String signature;
    public Jwt(String payload) throws Exception {
        this.header = JwtUtil.encode(JwtUtil.DEFAULT_HEADER);
        this.payload = JwtUtil.encode(payload);
        this.signature = JwtUtil.getSignature(payload);
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return header + "." + payload + "." + signature;
    }
}
