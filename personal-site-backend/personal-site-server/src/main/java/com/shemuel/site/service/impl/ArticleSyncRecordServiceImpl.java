package com.shemuel.site.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.shemuel.site.mapper.ArticleSyncRecordMapper;
import com.shemuel.site.entity.ArticleSyncRecord;
import com.shemuel.site.service.ArticleSyncRecordService;
import com.shemuel.site.utils.PageUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 文章同步记录表 服务实现类
 */
@Service
@RequiredArgsConstructor
public class ArticleSyncRecordServiceImpl extends ServiceImpl<ArticleSyncRecordMapper, ArticleSyncRecord> implements ArticleSyncRecordService {

    /**
     * 查询文章同步记录表分页列表
     */
    @Override
    public IPage<ArticleSyncRecord> selectPage(ArticleSyncRecord articleSyncRecord) {
        LambdaQueryWrapper<ArticleSyncRecord> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(articleSyncRecord.getId() != null, ArticleSyncRecord::getId, articleSyncRecord.getId());
        wrapper.eq(articleSyncRecord.getArticleId() != null, ArticleSyncRecord::getArticleId, articleSyncRecord.getArticleId());
        wrapper.eq(articleSyncRecord.getArticleTitle() != null, ArticleSyncRecord::getArticleTitle, articleSyncRecord.getArticleTitle());
        wrapper.eq(articleSyncRecord.getPlatformId() != null, ArticleSyncRecord::getPlatformId, articleSyncRecord.getPlatformId());
        wrapper.eq(articleSyncRecord.getSyncResult() != null, ArticleSyncRecord::getSyncResult, articleSyncRecord.getSyncResult());
        wrapper.eq(articleSyncRecord.getSyncFailReason() != null, ArticleSyncRecord::getSyncFailReason, articleSyncRecord.getSyncFailReason());
        wrapper.eq(articleSyncRecord.getSyncTime() != null, ArticleSyncRecord::getSyncTime, articleSyncRecord.getSyncTime());
        return page(PageUtil.getPage(), wrapper);
    }

    /**
     * 查询文章同步记录表列表
     */
    @Override
    public List<ArticleSyncRecord> selectList(ArticleSyncRecord articleSyncRecord) {
        LambdaQueryWrapper<ArticleSyncRecord> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(articleSyncRecord.getId() != null, ArticleSyncRecord::getId, articleSyncRecord.getId());
        wrapper.eq(articleSyncRecord.getArticleId() != null, ArticleSyncRecord::getArticleId, articleSyncRecord.getArticleId());
        wrapper.eq(articleSyncRecord.getArticleTitle() != null, ArticleSyncRecord::getArticleTitle, articleSyncRecord.getArticleTitle());
        wrapper.eq(articleSyncRecord.getPlatformId() != null, ArticleSyncRecord::getPlatformId, articleSyncRecord.getPlatformId());
        wrapper.eq(articleSyncRecord.getSyncResult() != null, ArticleSyncRecord::getSyncResult, articleSyncRecord.getSyncResult());
        wrapper.eq(articleSyncRecord.getSyncFailReason() != null, ArticleSyncRecord::getSyncFailReason, articleSyncRecord.getSyncFailReason());
        wrapper.eq(articleSyncRecord.getSyncTime() != null, ArticleSyncRecord::getSyncTime, articleSyncRecord.getSyncTime());
        return list(wrapper);
    }

    /**
     * 新增文章同步记录表
     */
    @Override
    public boolean insert(ArticleSyncRecord articleSyncRecord) {
        return save(articleSyncRecord);
    }

    /**
     * 修改文章同步记录表
     */
    @Override
    public boolean update(ArticleSyncRecord articleSyncRecord) {
        return updateById(articleSyncRecord);
    }

    /**
     * 批量删除文章同步记录表
     */
    @Override
    public boolean deleteByIds(List<Integer> ids) {
        return removeByIds(ids);
    }
}
