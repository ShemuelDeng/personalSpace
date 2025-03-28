package com.shemuel.site.service;

import com.shemuel.site.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 文章分类表 服务接口
 */
public interface CategoryService extends IService<Category> {
    /**
     * 查询文章分类表分页列表
     */
    IPage<Category> selectPage(Category category);

    /**
     * 查询文章分类表列表
     */
    List<Category> selectList(Category category);

    /**
     * 新增文章分类表
     */
    boolean insert(Category category);

    /**
     * 修改文章分类表
     */
    boolean update(Category category);

    /**
     * 批量删除文章分类表
     */
    boolean deleteByIds(List<Integer> ids);
}
