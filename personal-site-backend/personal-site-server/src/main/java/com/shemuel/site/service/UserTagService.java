package com.shemuel.site.service;

import com.shemuel.site.entity.UserTag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 用户私有标签库 服务接口
 */
public interface UserTagService extends IService<UserTag> {
    /**
     * 查询用户私有标签库分页列表
     */
    IPage<UserTag> selectPage(UserTag userTag);

    /**
     * 查询用户私有标签库列表
     */
    List<UserTag> selectList(UserTag userTag);

    /**
     * 新增用户私有标签库
     */
    boolean insert(UserTag userTag);

    /**
     * 修改用户私有标签库
     */
    boolean update(UserTag userTag);

    /**
     * 批量删除用户私有标签库
     */
    boolean deleteByIds(List<Integer> ids);
}
