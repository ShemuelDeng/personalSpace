package com.shemuel.site.service;

import com.shemuel.site.entity.WorkExperience;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 工作经历 服务接口
 */
public interface WorkExperienceService extends IService<WorkExperience> {
    /**
     * 查询工作经历分页列表
     */
    IPage<WorkExperience> selectPage(WorkExperience workExperience);

    /**
     * 查询工作经历列表
     */
    List<WorkExperience> selectList(WorkExperience workExperience);

    /**
     * 新增工作经历
     */
    boolean insert(WorkExperience workExperience);

    /**
     * 修改工作经历
     */
    boolean update(WorkExperience workExperience);

    /**
     * 批量删除工作经历
     */
    boolean deleteByIds(List<Integer> ids);
}
