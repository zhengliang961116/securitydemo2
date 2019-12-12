package com.zhsw.securitydemo2.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * @Author: zhengliang
 * @Description: 角色实体类
 * @Date: 2019/12/10 14:56
 */
public class SysRole  implements GrantedAuthority {
    private int id;
    private String authority;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return  id + "," + authority ;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
