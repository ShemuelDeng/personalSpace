package com.shemuel.site.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.shemuel.site.mapper.WorkExperienceMapper;
import com.shemuel.site.entity.WorkExperience;
import com.shemuel.site.service.WorkExperienceService;
import com.shemuel.site.utils.PageUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 工作经历 服务实现类
 */
@Service
@RequiredArgsConstructor
public class WorkExperienceServiceImpl extends ServiceImpl<WorkExperienceMapper, WorkExperience> implements WorkExperienceService {

    /**
     * 查询工作经历分页列表
     */
    @Override
    public IPage<WorkExperience> selectPage(WorkExperience workExperience) {
        LambdaQueryWrapper<WorkExperience> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(workExperience.getId() != null, WorkExperience::getId, workExperience.getId());
        wrapper.eq(workExperience.getUserId() != null, WorkExperience::getUserId, workExperience.getUserId());
        wrapper.eq(workExperience.getCompanyName() != null, WorkExperience::getCompanyName, workExperience.getCompanyName());
        wrapper.eq(workExperience.getPosition() != null, WorkExperience::getPosition, workExperience.getPosition());
        wrapper.eq(workExperience.getStartDate() != null, WorkExperience::getStartDate, workExperience.getStartDate());
        wrapper.eq(workExperience.getEndDate() != null, WorkExperience::getEndDate, workExperience.getEndDate());
        wrapper.eq(workExperience.getIsCurrent() != null, WorkExperience::getIsCurrent, workExperience.getIsCurrent());
        wrapper.eq(workExperience.getDescription() != null, WorkExperience::getDescription, workExperience.getDescription());
        wrapper.eq(workExperience.getSortOrder() != null, WorkExperience::getSortOrder, workExperience.getSortOrder());
        wrapper.eq(workExperience.getCreatedAt() != null, WorkExperience::getCreatedAt, workExperience.getCreatedAt());
        wrapper.eq(workExperience.getUpdatedAt() != null, WorkExperience::getUpdatedAt, workExperience.getUpdatedAt());
        return page(PageUtil.getPage(), wrapper);
    }

    /**
     * 查询工作经历列表
     */
    @Override
    public List<WorkExperience> selectList(WorkExperience workExperience) {
        LambdaQueryWrapper<WorkExperience> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(workExperience.getId() != null, WorkExperience::getId, workExperience.getId());
        wrapper.eq(workExperience.getUserId() != null, WorkExperience::getUserId, workExperience.getUserId());
        wrapper.eq(workExperience.getCompanyName() != null, WorkExperience::getCompanyName, workExperience.getCompanyName());
        wrapper.eq(workExperience.getPosition() != null, WorkExperience::getPosition, workExperience.getPosition());
        wrapper.eq(workExperience.getStartDate() != null, WorkExperience::getStartDate, workExperience.getStartDate());
        wrapper.eq(workExperience.getEndDate() != null, WorkExperience::getEndDate, workExperience.getEndDate());
        wrapper.eq(workExperience.getIsCurrent() != null, WorkExperience::getIsCurrent, workExperience.getIsCurrent());
        wrapper.eq(workExperience.getDescription() != null, WorkExperience::getDescription, workExperience.getDescription());
        wrapper.eq(workExperience.getSortOrder() != null, WorkExperience::getSortOrder, workExperience.getSortOrder());
        wrapper.eq(workExperience.getCreatedAt() != null, WorkExperience::getCreatedAt, workExperience.getCreatedAt());
        wrapper.eq(workExperience.getUpdatedAt() != null, WorkExperience::getUpdatedAt, workExperience.getUpdatedAt());
        return list(wrapper);
    }

    /**
     * 新增工作经历
     */
    @Override
    public boolean insert(WorkExperience workExperience) {
        return save(workExperience);
    }

    /**
     * 修改工作经历
     */
    @Override
    public boolean update(WorkExperience workExperience) {
        return updateById(workExperience);
    }

    /**
     * 批量删除工作经历
     */
    @Override
    public boolean deleteByIds(List<Integer> ids) {
        return removeByIds(ids);
    }
}
