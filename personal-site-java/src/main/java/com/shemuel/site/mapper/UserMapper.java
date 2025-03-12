package com.shemuel.site.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shemuel.site.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}