package com.zhsw.securitydemo2.handler;

import com.zhsw.securitydemo2.entity.SysUser;
import com.zhsw.securitydemo2.mapper.SysUserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: zhengliang
 * @Description: 用登陆处理
 * @Date: 2019/12/12 9:21
 */
@Component
public class UserLoginService  implements UserDetailsService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username==null||username.trim().length()<=0) {
            throw new UsernameNotFoundException("用户名为空");
        }
        SysUser sysUser = sysUserMapper.selectByUserName(username);
        if (sysUser != null){
            return sysUser;
        }
        throw new UsernameNotFoundException("用户不存在!");

    }

}
