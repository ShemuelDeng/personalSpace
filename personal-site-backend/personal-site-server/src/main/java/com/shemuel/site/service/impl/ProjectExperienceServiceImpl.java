package com.shemuel.site.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.shemuel.site.mapper.ProjectExperienceMapper;
import com.shemuel.site.entity.ProjectExperience;
import com.shemuel.site.service.ProjectExperienceService;
import com.shemuel.site.utils.PageUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 项目经验 服务实现类
 */
@Service
@RequiredArgsConstructor
public class ProjectExperienceServiceImpl extends ServiceImpl<ProjectExperienceMapper, ProjectExperience> implements ProjectExperienceService {

    /**
     * 查询项目经验分页列表
     */
    @Override
    public IPage<ProjectExperience> selectPage(ProjectExperience projectExperience) {
        LambdaQueryWrapper<ProjectExperience> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(projectExperience.getId() != null, ProjectExperience::getId, projectExperience.getId());
        wrapper.eq(projectExperience.getUserId() != null, ProjectExperience::getUserId, projectExperience.getUserId());
        wrapper.eq(projectExperience.getProjectName() != null, ProjectExperience::getProjectName, projectExperience.getProjectName());
        wrapper.eq(projectExperience.getRole() != null, ProjectExperience::getRole, projectExperience.getRole());
        wrapper.eq(projectExperience.getStartDate() != null, ProjectExperience::getStartDate, projectExperience.getStartDate());
        wrapper.eq(projectExperience.getEndDate() != null, ProjectExperience::getEndDate, projectExperience.getEndDate());
        wrapper.eq(projectExperience.getDescription() != null, ProjectExperience::getDescription, projectExperience.getDescription());
        wrapper.eq(projectExperience.getProjectUrl() != null, ProjectExperience::getProjectUrl, projectExperience.getProjectUrl());
        wrapper.eq(projectExperience.getSortOrder() != null, ProjectExperience::getSortOrder, projectExperience.getSortOrder());
        wrapper.eq(projectExperience.getCreatedAt() != null, ProjectExperience::getCreatedAt, projectExperience.getCreatedAt());
        wrapper.eq(projectExperience.getUpdatedAt() != null, ProjectExperience::getUpdatedAt, projectExperience.getUpdatedAt());
        return page(PageUtil.getPage(), wrapper);
    }

    /**
     * 查询项目经验列表
     */
    @Override
    public List<ProjectExperience> selectList(ProjectExperience projectExperience) {
        LambdaQueryWrapper<ProjectExperience> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(projectExperience.getId() != null, ProjectExperience::getId, projectExperience.getId());
        wrapper.eq(projectExperience.getUserId() != null, ProjectExperience::getUserId, projectExperience.getUserId());
        wrapper.eq(projectExperience.getProjectName() != null, ProjectExperience::getProjectName, projectExperience.getProjectName());
        wrapper.eq(projectExperience.getRole() != null, ProjectExperience::getRole, projectExperience.getRole());
        wrapper.eq(projectExperience.getStartDate() != null, ProjectExperience::getStartDate, projectExperience.getStartDate());
        wrapper.eq(projectExperience.getEndDate() != null, ProjectExperience::getEndDate, projectExperience.getEndDate());
        wrapper.eq(projectExperience.getDescription() != null, ProjectExperience::getDescription, projectExperience.getDescription());
        wrapper.eq(projectExperience.getProjectUrl() != null, ProjectExperience::getProjectUrl, projectExperience.getProjectUrl());
        wrapper.eq(projectExperience.getSortOrder() != null, ProjectExperience::getSortOrder, projectExperience.getSortOrder());
        wrapper.eq(projectExperience.getCreatedAt() != null, ProjectExperience::getCreatedAt, projectExperience.getCreatedAt());
        wrapper.eq(projectExperience.getUpdatedAt() != null, ProjectExperience::getUpdatedAt, projectExperience.getUpdatedAt());
        return list(wrapper);
    }

    /**
     * 新增项目经验
     */
    @Override
    public boolean insert(ProjectExperience projectExperience) {
        return save(projectExperience);
    }

    /**
     * 修改项目经验
     */
    @Override
    public boolean update(ProjectExperience projectExperience) {
        return updateById(projectExperience);
    }

    /**
     * 批量删除项目经验
     */
    @Override
    public boolean deleteByIds(List<Integer> ids) {
        return removeByIds(ids);
    }
}
