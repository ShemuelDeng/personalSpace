package com.shemuel.site.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shemuel.site.entity.ThirdPartyPlatform;
import com.shemuel.site.service.ThirdPartyPlatformService;
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

    private final ThirdPartyPlatformService thirdPartyPlatformService;

    /**
     * 查询文章同步记录表分页列表
     */
    @Override
    public IPage<ArticleSyncRecord> selectPage(ArticleSyncRecord articleSyncRecord) {

        List<ThirdPartyPlatform> thirdPartyPlatforms = thirdPartyPlatformService.selectList(new ThirdPartyPlatform());
        Map<Integer, String> nameMap = thirdPartyPlatforms.stream().collect(Collectors.toMap(ThirdPartyPlatform::getId, ThirdPartyPlatform::getPlatformName));

        LambdaQueryWrapper<ArticleSyncRecord> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(articleSyncRecord.getId() != null, ArticleSyncRecord::getId, articleSyncRecord.getId());
        wrapper.eq(articleSyncRecord.getArticleId() != null, ArticleSyncRecord::getArticleId, articleSyncRecord.getArticleId());
        wrapper.eq(articleSyncRecord.getArticleTitle() != null, ArticleSyncRecord::getArticleTitle, articleSyncRecord.getArticleTitle());
        wrapper.eq(articleSyncRecord.getPlatformId() != null, ArticleSyncRecord::getPlatformId, articleSyncRecord.getPlatformId());
        wrapper.eq(articleSyncRecord.getSyncResult() != null, ArticleSyncRecord::getSyncResult, articleSyncRecord.getSyncResult());
        wrapper.eq(articleSyncRecord.getSyncFailReason() != null, ArticleSyncRecord::getSyncFailReason, articleSyncRecord.getSyncFailReason());
        wrapper.eq(articleSyncRecord.getSyncTime() != null, ArticleSyncRecord::getSyncTime, articleSyncRecord.getSyncTime());
        Page<ArticleSyncRecord> pageResult = page(PageUtil.getPage(), wrapper);
        List<ArticleSyncRecord> collect = pageResult.getRecords().stream().map(o -> {
            o.setPlatformName(nameMap.get(o.getPlatformId()));
            return o;
        }).collect(Collectors.toList());
        pageResult.setRecords(collect);

        return pageResult;
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
