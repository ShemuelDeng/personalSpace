package com.shemuel.site.service;

import com.shemuel.site.entity.ArticleSyncRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 文章同步记录表 服务接口
 */
public interface ArticleSyncRecordService extends IService<ArticleSyncRecord> {
    /**
     * 查询文章同步记录表分页列表
     */
    IPage<ArticleSyncRecord> selectPage(ArticleSyncRecord articleSyncRecord);

    /**
     * 查询文章同步记录表列表
     */
    List<ArticleSyncRecord> selectList(ArticleSyncRecord articleSyncRecord);

    /**
     * 新增文章同步记录表
     */
    boolean insert(ArticleSyncRecord articleSyncRecord);

    /**
     * 修改文章同步记录表
     */
    boolean update(ArticleSyncRecord articleSyncRecord);

    /**
     * 批量删除文章同步记录表
     */
    boolean deleteByIds(List<Integer> ids);
}
