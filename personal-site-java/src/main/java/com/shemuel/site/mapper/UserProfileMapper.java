package com.shemuel.site.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shemuel.site.entity.UserProfile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserProfileMapper extends BaseMapper<UserProfile> {

}