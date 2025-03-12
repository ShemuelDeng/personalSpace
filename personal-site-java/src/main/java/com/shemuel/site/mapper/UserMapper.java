package com.shemuel.site.mapper;

import com.shemuel.site.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insert(User user);
    int update(User user);
    int deleteById(Long userId);
    User selectById(Long userId);
    User selectByUsername(String username);
    // 根据业务需要可添加更多方法
}