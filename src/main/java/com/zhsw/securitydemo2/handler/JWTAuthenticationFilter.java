package com.zhsw.securitydemo2.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhsw.securitydemo2.entity.SysUser;
import com.zhsw.securitydemo2.utils.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: zhengliang
 * @Description: jwt过滤器
 * @Date: 2019/12/11 17:38
 */
public class JWTAuthenticationFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader(JwtUtil.HEADER_TOKEN_NAME);
        /* token为null直接走登录的过滤器，不为空走下面 */
        if (token!=null&&token.trim().length()>0) {
            String tokenBody = null;
            try {
                tokenBody = JwtUtil.testJwt(token);
            } catch (Exception e) {
                e.printStackTrace();
            }
            /* 从token中取出用户信息，放在上下文中 */
            if (tokenBody != null && tokenBody.trim().length()>0){
                JSONObject user = JSON.parseObject(tokenBody).getJSONObject("user");
                SysUser sysUser = JSON.toJavaObject(user,SysUser.class);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(sysUser,null,sysUser.getAuthorities()));
            }else{
                HttpServletResponse res = (HttpServletResponse) response;
                res.setContentType("application/json;charset=UTF-8");
                res.getWriter().write("{\"code\": \"405\", \"msg\": \"token错误\"}");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
