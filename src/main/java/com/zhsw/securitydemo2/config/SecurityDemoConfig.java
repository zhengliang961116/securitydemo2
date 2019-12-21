package com.zhsw.securitydemo2.config;

import com.zhsw.securitydemo2.handler.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhengliang
 * @Description: security配置类
 * @Date: 2019/12/11 14:20
 */
@Configuration
@EnableWebSecurity
public class SecurityDemoConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* 登录配置 */
        http.formLogin().loginProcessingUrl("/login");

        //开启跨域共享，跨域伪造请求无效
        http.cors().and().csrf().disable();

        //配置session为无状态
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //登陆成功处理
        http.formLogin().successHandler(new LoginSuccessHandler());

        //登陆失败
        http.formLogin().failureHandler(new LoginFailureHandler());

        /* 登录过期/未登录 处理 */
        http.exceptionHandling().authenticationEntryPoint(new LoginExpireHandler());

        //用户权限不足处理
        http.exceptionHandling().accessDeniedHandler(new AuthLimitHandler());

        //退出系统处理
        http.logout().logoutUrl("/logout").permitAll()
                .invalidateHttpSession(true)
                .logoutSuccessHandler(new SignOutHandler());

        /* 开启授权认证 */
        http.authorizeRequests()
                /* 动态url权限 */
                .withObjectPostProcessor(new DefinedObjectPostProcessor())
                /* url决策 */
                .accessDecisionManager(accessDecisionManager())
                .anyRequest().authenticated();

        /* 配置token验证过滤器 */
        http.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * 跨域请求
     * @return
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }

    /**
     * 决策管理
     * @return
     */
    private AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
        decisionVoters.add(new WebExpressionVoter());
        decisionVoters.add(new AuthenticatedVoter());
        decisionVoters.add(new RoleVoter());
        /* 路由权限管理 */
        decisionVoters.add(new UrlRoleAuthHandler());
        return new UnanimousBased(decisionVoters);
    }


    @Resource
    private UrlRolesFilterHandler urlRolesFilterHandler;
    class DefinedObjectPostProcessor implements ObjectPostProcessor<FilterSecurityInterceptor> {
        @Override
        public <O extends FilterSecurityInterceptor> O postProcess(O object) {
            object.setSecurityMetadataSource(urlRolesFilterHandler);
            return object;
        }
    }
}
