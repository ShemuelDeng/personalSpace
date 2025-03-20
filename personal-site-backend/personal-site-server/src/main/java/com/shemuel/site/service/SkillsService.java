package com.shemuel.site.service;

import com.shemuel.site.entity.Skills;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 技能信息 服务接口
 */
public interface SkillsService extends IService<Skills> {
    /**
     * 查询技能信息分页列表
     */
    IPage<Skills> selectPage(Skills skills);

    /**
     * 查询技能信息列表
     */
    List<Skills> selectList(Skills skills);

    /**
     * 新增技能信息
     */
    boolean insert(Skills skills);

    /**
     * 修改技能信息
     */
    boolean update(Skills skills);

    /**
     * 批量删除技能信息
     */
    boolean deleteByIds(List<Integer> ids);
}
