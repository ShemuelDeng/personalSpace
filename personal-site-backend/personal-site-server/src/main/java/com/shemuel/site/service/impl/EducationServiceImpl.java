package com.shemuel.site.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.shemuel.site.mapper.EducationMapper;
import com.shemuel.site.entity.Education;
import com.shemuel.site.service.EducationService;
import com.shemuel.site.utils.PageUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 教育经历 服务实现类
 */
@Service
@RequiredArgsConstructor
public class EducationServiceImpl extends ServiceImpl<EducationMapper, Education> implements EducationService {

    /**
     * 查询教育经历分页列表
     */
    @Override
    public IPage<Education> selectPage(Education education) {
        LambdaQueryWrapper<Education> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(education.getId() != null, Education::getId, education.getId());
        wrapper.eq(education.getUserId() != null, Education::getUserId, education.getUserId());
        wrapper.eq(education.getSchoolName() != null, Education::getSchoolName, education.getSchoolName());
        wrapper.eq(education.getMajor() != null, Education::getMajor, education.getMajor());
        wrapper.eq(education.getDegree() != null, Education::getDegree, education.getDegree());
        wrapper.eq(education.getStartDate() != null, Education::getStartDate, education.getStartDate());
        wrapper.eq(education.getEndDate() != null, Education::getEndDate, education.getEndDate());
        wrapper.eq(education.getIsCurrent() != null, Education::getIsCurrent, education.getIsCurrent());
        wrapper.eq(education.getDescription() != null, Education::getDescription, education.getDescription());
        wrapper.eq(education.getSortOrder() != null, Education::getSortOrder, education.getSortOrder());
        wrapper.eq(education.getCreatedAt() != null, Education::getCreatedAt, education.getCreatedAt());
        wrapper.eq(education.getUpdatedAt() != null, Education::getUpdatedAt, education.getUpdatedAt());
        return page(PageUtil.getPage(), wrapper);
    }

    /**
     * 查询教育经历列表
     */
    @Override
    public List<Education> selectList(Education education) {
        LambdaQueryWrapper<Education> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(education.getId() != null, Education::getId, education.getId());
        wrapper.eq(education.getUserId() != null, Education::getUserId, education.getUserId());
        wrapper.eq(education.getSchoolName() != null, Education::getSchoolName, education.getSchoolName());
        wrapper.eq(education.getMajor() != null, Education::getMajor, education.getMajor());
        wrapper.eq(education.getDegree() != null, Education::getDegree, education.getDegree());
        wrapper.eq(education.getStartDate() != null, Education::getStartDate, education.getStartDate());
        wrapper.eq(education.getEndDate() != null, Education::getEndDate, education.getEndDate());
        wrapper.eq(education.getIsCurrent() != null, Education::getIsCurrent, education.getIsCurrent());
        wrapper.eq(education.getDescription() != null, Education::getDescription, education.getDescription());
        wrapper.eq(education.getSortOrder() != null, Education::getSortOrder, education.getSortOrder());
        wrapper.eq(education.getCreatedAt() != null, Education::getCreatedAt, education.getCreatedAt());
        wrapper.eq(education.getUpdatedAt() != null, Education::getUpdatedAt, education.getUpdatedAt());
        return list(wrapper);
    }

    /**
     * 新增教育经历
     */
    @Override
    public boolean insert(Education education) {
        return save(education);
    }

    /**
     * 修改教育经历
     */
    @Override
    public boolean update(Education education) {
        return updateById(education);
    }

    /**
     * 批量删除教育经历
     */
    @Override
    public boolean deleteByIds(List<Integer> ids) {
        return removeByIds(ids);
    }
}
