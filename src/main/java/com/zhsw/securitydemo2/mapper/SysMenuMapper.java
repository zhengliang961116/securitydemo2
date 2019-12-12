package com.zhsw.securitydemo2.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zhengliang
 * @Description: 菜单dao层接口
 * @Date: 2019/12/11 16:22
 */
@Mapper
public interface SysMenuMapper {
    List<String> selectRoleByUrl(@Param("url") String url);
}
