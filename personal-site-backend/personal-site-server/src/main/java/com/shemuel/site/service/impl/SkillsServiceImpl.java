package com.shemuel.site.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.shemuel.site.mapper.SkillsMapper;
import com.shemuel.site.entity.Skills;
import com.shemuel.site.service.SkillsService;
import com.shemuel.site.utils.PageUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 技能信息 服务实现类
 */
@Service
@RequiredArgsConstructor
public class SkillsServiceImpl extends ServiceImpl<SkillsMapper, Skills> implements SkillsService {

    /**
     * 查询技能信息分页列表
     */
    @Override
    public IPage<Skills> selectPage(Skills skills) {
        LambdaQueryWrapper<Skills> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(skills.getId() != null, Skills::getId, skills.getId());
        wrapper.eq(skills.getUserId() != null, Skills::getUserId, skills.getUserId());
        wrapper.eq(skills.getSkillName() != null, Skills::getSkillName, skills.getSkillName());
        wrapper.eq(skills.getProficiency() != null, Skills::getProficiency, skills.getProficiency());
        wrapper.eq(skills.getSortOrder() != null, Skills::getSortOrder, skills.getSortOrder());
        wrapper.eq(skills.getCreatedAt() != null, Skills::getCreatedAt, skills.getCreatedAt());
        wrapper.eq(skills.getUpdatedAt() != null, Skills::getUpdatedAt, skills.getUpdatedAt());
        return page(PageUtil.getPage(), wrapper);
    }

    /**
     * 查询技能信息列表
     */
    @Override
    public List<Skills> selectList(Skills skills) {
        LambdaQueryWrapper<Skills> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(skills.getId() != null, Skills::getId, skills.getId());
        wrapper.eq(skills.getUserId() != null, Skills::getUserId, skills.getUserId());
        wrapper.eq(skills.getSkillName() != null, Skills::getSkillName, skills.getSkillName());
        wrapper.eq(skills.getProficiency() != null, Skills::getProficiency, skills.getProficiency());
        wrapper.eq(skills.getSortOrder() != null, Skills::getSortOrder, skills.getSortOrder());
        wrapper.eq(skills.getCreatedAt() != null, Skills::getCreatedAt, skills.getCreatedAt());
        wrapper.eq(skills.getUpdatedAt() != null, Skills::getUpdatedAt, skills.getUpdatedAt());
        return list(wrapper);
    }

    /**
     * 新增技能信息
     */
    @Override
    public boolean insert(Skills skills) {
        return save(skills);
    }

    /**
     * 修改技能信息
     */
    @Override
    public boolean update(Skills skills) {
        return updateById(skills);
    }

    /**
     * 批量删除技能信息
     */
    @Override
    public boolean deleteByIds(List<Integer> ids) {
        return removeByIds(ids);
    }
}
