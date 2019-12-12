package com.zhsw.securitydemo2.handler;

import com.alibaba.fastjson.JSONObject;
import com.zhsw.securitydemo2.entity.Jwt;
import com.zhsw.securitydemo2.entity.Result;
import com.zhsw.securitydemo2.entity.SysUser;
import com.zhsw.securitydemo2.utils.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: zhengliang
 * @Description: 登陆成功处理
 * @Date: 2019/12/11 14:28
 */
public class LoginSuccessHandler  implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        // 获取登录成功信息
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        boolean loginBoolean = true;
        Result result = new Result();

        SysUser user = (SysUser) authentication.getPrincipal();
        user.setPassword(null);
        long now = System.currentTimeMillis();

        JSONObject payload = new JSONObject();
        payload.put("iss","sys"); //签发人
        payload.put("aud",user.getUsername()); //受众
        payload.put("exp",now + JwtUtil.EXPIRE_TIME); //过期时间
        payload.put("nbf",now); //生效时间
        payload.put("iat",now); //签发时间
        payload.put("jti", user.getId()); //编号
        payload.put("sub","JWT-TEST"); //主题
        payload.put("user",user); //用户对象
        try {
            httpServletResponse.setHeader(JwtUtil.HEADER_TOKEN_NAME, new Jwt(payload.toJSONString()).toString() );
        } catch (Exception e) {
            loginBoolean = false;
        }
        if (loginBoolean){
            result.setCode("200");
            result.setMsg("登陆成功");
            result.setDate(user);
            httpServletResponse.getWriter().write(JSONObject.toJSONString(result));
        }else{
            result.setCode("500");
            result.setMsg("登陆失败");
            httpServletResponse.getWriter().write(JSONObject.toJSONString(result));
        }
    }

}
