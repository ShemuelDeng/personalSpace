package com.shemuel.site.service;

import com.shemuel.site.entity.SocialLinks;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 社交连接信息 服务接口
 */
public interface SocialLinksService extends IService<SocialLinks> {
    /**
     * 查询社交连接信息分页列表
     */
    IPage<SocialLinks> selectPage(SocialLinks socialLinks);

    /**
     * 查询社交连接信息列表
     */
    List<SocialLinks> selectList(SocialLinks socialLinks);

    /**
     * 新增社交连接信息
     */
    boolean insert(SocialLinks socialLinks);

    /**
     * 修改社交连接信息
     */
    boolean update(SocialLinks socialLinks);

    /**
     * 批量删除社交连接信息
     */
    boolean deleteByIds(List<Integer> ids);
}
