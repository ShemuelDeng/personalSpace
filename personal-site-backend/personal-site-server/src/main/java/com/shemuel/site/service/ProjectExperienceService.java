package com.shemuel.site.service;

import com.shemuel.site.entity.ProjectExperience;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 项目经验 服务接口
 */
public interface ProjectExperienceService extends IService<ProjectExperience> {
    /**
     * 查询项目经验分页列表
     */
    IPage<ProjectExperience> selectPage(ProjectExperience projectExperience);

    /**
     * 查询项目经验列表
     */
    List<ProjectExperience> selectList(ProjectExperience projectExperience);

    /**
     * 新增项目经验
     */
    boolean insert(ProjectExperience projectExperience);

    /**
     * 修改项目经验
     */
    boolean update(ProjectExperience projectExperience);

    /**
     * 批量删除项目经验
     */
    boolean deleteByIds(List<Integer> ids);
}
