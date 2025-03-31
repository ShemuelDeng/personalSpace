package com.shemuel.site.tools;

import com.alibaba.fastjson.JSON;
import com.shemuel.site.bo.SyncArticleResult;
import com.shemuel.site.dto.ZhihuArticleDTO;
import com.shemuel.site.entity.Article;
import com.shemuel.site.entity.ThirdPartyPlatform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 掘金文章同步器
 * @Author: 公众号: 加瓦点灯
 * @Date: 2025-03-31-15:13
 * @Description:
 */
@Component
@Slf4j
public class ZhihuArticleSync extends ArticleSynchronizer{

    @Override
    public SyncArticleResult doSyncArticle(Article article, ThirdPartyPlatform thirdPartyPlatform) {
        try {
            // 1. 创建文章草稿
            ZhihuArticleDTO.CreateDraftRequest createDraftRequest = new ZhihuArticleDTO.CreateDraftRequest();
            createDraftRequest.setContent(article.getHtmlContent());
            createDraftRequest.setTitle(article.getTitle());

            String createDraftJson = JSON.toJSONString(createDraftRequest);
            String createDraftResponseString = httpClient.doPostJson(thirdPartyPlatform.getCreateDraftUrl(), createDraftJson, buildRequestHeader(thirdPartyPlatform.getHeaders()), thirdPartyPlatform.getCookies());
            log.info("创建知乎草稿响应: {}", createDraftResponseString);

            ZhihuArticleDTO.CreateDraftResponse createDraftResponse = JSON.parseObject(createDraftResponseString, ZhihuArticleDTO.CreateDraftResponse.class);
            String draftId = createDraftResponse.getId();
            if (draftId == null) {
                log.error("创建知乎草稿失败");
                return SyncArticleResult.fail("创建知乎草稿失败");
            }

            // 2. 设置文章主题
            ZhihuArticleDTO.TopicRequest topicRequest = new ZhihuArticleDTO.TopicRequest();
            String setTopicUrl = String.format(thirdPartyPlatform.getSetTopicUrl(), draftId);
            String setTopicResponseString = httpClient.doPostJson(setTopicUrl, JSON.toJSONString(topicRequest), buildRequestHeader(thirdPartyPlatform.getHeaders()), thirdPartyPlatform.getCookies());
            log.info("设置知乎文章主题响应: {}", setTopicResponseString);

            // 3. 发布文章
            ZhihuArticleDTO.PublishRequest publishRequest = new ZhihuArticleDTO.PublishRequest();
            publishRequest.getData().getDraft().setId(draftId);

            String publishResponseString = httpClient.doPostJson(thirdPartyPlatform.getPublishArticleUrl(), JSON.toJSONString(publishRequest), buildRequestHeader(thirdPartyPlatform.getHeaders()), thirdPartyPlatform.getCookies());
            log.info("发布知乎文章响应: {}", publishResponseString);

            ZhihuArticleDTO.PublishResponse publishResponse = JSON.parseObject(publishResponseString, ZhihuArticleDTO.PublishResponse.class);
            if (publishResponse.getCode() != 0) {
                log.error("发布知乎文章失败: {}", publishResponse.getMessage());
                return SyncArticleResult.fail("发布知乎文章失败");
            }

            return SyncArticleResult.success();
        } catch (Exception e) {
            log.error("Error syncing article to Zhihu: {}", e.getMessage(), e);
            return SyncArticleResult.fail("同步到知乎失败");
        }
    }

    @Override
    public ThirdPartyPlatform getPlatformInfo() {
       return getPlatformById(3);
    }
}
