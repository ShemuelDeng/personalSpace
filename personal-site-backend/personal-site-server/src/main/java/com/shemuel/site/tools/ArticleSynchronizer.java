package com.shemuel.site.tools;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shemuel.site.bo.SyncArticleResult;
import com.shemuel.site.entity.Article;
import com.shemuel.site.entity.ArticleSyncRecord;
import com.shemuel.site.entity.ThirdPartyPlatform;
import com.shemuel.site.exception.BusinessException;
import com.shemuel.site.mapper.ThirdPartyPlatformMapper;
import com.shemuel.site.service.ArticleSyncRecordService;
import com.shemuel.site.utils.OkHttpClientInstance;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 公众号: 加瓦点灯
 * @Date: 2025-03-31-14:52
 * @Description:
 */
@Slf4j
public abstract class ArticleSynchronizer {

    @Resource
    private ThirdPartyPlatformMapper thirdPartyPlatformMapper;
    @Resource
    private ArticleSyncRecordService articleSyncRecordService;

    protected OkHttpClientInstance httpClient = OkHttpClientInstance.getInstance();

    /**
     * 同步文章到其他平台
     * @param article 需要同步的文章信息
     * @param thirdPartyPlatform 其他平台的信息， 包含保存文章草稿url, 更新文章草稿url, header, cookied等等
     * @return
     */
    protected abstract SyncArticleResult doSyncArticle(Article article, ThirdPartyPlatform thirdPartyPlatform);

    public abstract ThirdPartyPlatform getPlatformInfo();

    public void syncArticle(Article article){

        ThirdPartyPlatform platformInfo = getPlatformInfo();

        ArticleSyncRecord queryExistDto = new ArticleSyncRecord();
        queryExistDto.setArticleTitle(article.getTitle());
        queryExistDto.setPlatformId(platformInfo.getId());
        // 先判断是否同步过
        if (articleSyncRecordService.selectList(queryExistDto).size() > 0) {
            log.info("标题:{}, 已经同步过了,平台:{}", platformInfo.getPlatformName());
            return;
        }
        // 执行同步逻辑
        SyncArticleResult syncArticleResult = doSyncArticle(article, platformInfo);
        // 记录同步日志
        ArticleSyncRecord record = queryExistDto;
        record.setArticleId(article.getId().toString());
        record.setArticleTitle(article.getTitle());
        record.setPlatformId(platformInfo.getId());
        record.setSyncResult(syncArticleResult.isSuccess() ? 1 : 0);
        record.setSyncFailReason(JSON.toJSONString(syncArticleResult.getFailReason()));
        record.setSyncTime(LocalDateTime.now());
        articleSyncRecordService.insert(record);
    }

    protected ThirdPartyPlatform getPlatformById(Integer id){
        LambdaQueryWrapper<ThirdPartyPlatform> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ThirdPartyPlatform::getPlatformType, id);
        ThirdPartyPlatform thirdPartyPlatform = thirdPartyPlatformMapper.selectOne(wrapper);
        if (thirdPartyPlatform == null) {
            throw  new BusinessException("不存在的平台");
        }
        return thirdPartyPlatform;
    }

    protected Map<String,String> buildRequestHeader(String dbHeader) {
        String[] headers = dbHeader.split("\n");

        Map<String,String> headerMap = new HashMap<>();
        // 添加headers
        for (String header : headers) {
            if (!header.trim().isEmpty()) {
                String[] parts = header.split(":", 2);
                if (parts.length == 2) {
                    headerMap.put(parts[0].trim(), parts[1].trim());
                }
            }
        }

        return headerMap;
    }


    protected String[] transferCookieToArray(String cookie) {
        String[] cookies = cookie.split("\n");

        return cookies;
    }
}
