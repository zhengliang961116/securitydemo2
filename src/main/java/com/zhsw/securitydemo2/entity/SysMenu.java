package com.zhsw.securitydemo2.entity;

/**
 * @Author: zhengliang
 * @Description: 菜单实体类
 * @Date: 2019/12/11 10:47
 */
public class SysMenu {
    private int  id;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SysMenu() {
    }

    @Override
    public String toString() {
        return "SysMenu{" +
                "id=" + id +
                ", url='" + url + '\'' +
                '}';
    }
}
