package com.zhsw.securitydemo2.mapper;

import com.zhsw.securitydemo2.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: zhengliang
 * @Description: 用户dao层接口
 * @Date: 2019/12/11 16:21
 */
@Mapper
public interface SysUserMapper {
    SysUser selectByUserName(@Param("username") String username);
}
