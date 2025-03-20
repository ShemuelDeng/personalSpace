package com.shemuel.site.service;

import com.shemuel.site.entity.Education;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 教育经历 服务接口
 */
public interface EducationService extends IService<Education> {
    /**
     * 查询教育经历分页列表
     */
    IPage<Education> selectPage(Education education);

    /**
     * 查询教育经历列表
     */
    List<Education> selectList(Education education);

    /**
     * 新增教育经历
     */
    boolean insert(Education education);

    /**
     * 修改教育经历
     */
    boolean update(Education education);

    /**
     * 批量删除教育经历
     */
    boolean deleteByIds(List<Integer> ids);
}
