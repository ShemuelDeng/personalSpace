package com.shemuel.site.service;

import com.shemuel.site.entity.UserCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 用户私有分类 服务接口
 */
public interface UserCategoryService extends IService<UserCategory> {
    /**
     * 查询用户私有分类分页列表
     */
    IPage<UserCategory> selectPage(UserCategory userCategory);

    /**
     * 查询用户私有分类列表
     */
    List<UserCategory> selectList(UserCategory userCategory);

    /**
     * 新增用户私有分类
     */
    boolean insert(UserCategory userCategory);

    /**
     * 修改用户私有分类
     */
    boolean update(UserCategory userCategory);

    /**
     * 批量删除用户私有分类
     */
    boolean deleteByIds(List<Integer> ids);
}
