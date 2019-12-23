package com.zhsw.securitydemo2.service.impl;

import com.zhsw.securitydemo2.entity.SysUser;
import com.zhsw.securitydemo2.mapper.SysUserMapper;
import com.zhsw.securitydemo2.service.SysUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: zhengliang
 * @Description: impl
 * @Date: 2019/12/23 10:47
 */
@Service
public class SysUserServiceImpl implements SysUserService {
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
