package com.shemuel.site.tools;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shemuel.site.bo.SyncArticleResult;
import com.shemuel.site.bo.ThirdPartyPlatformWithAuthInfo;
import com.shemuel.site.common.RestResult;
import com.shemuel.site.dto.JuejinArticleDTO;
import com.shemuel.site.entity.Article;
import com.shemuel.site.entity.ThirdPartyPlatform;
import com.shemuel.site.exception.BusinessException;
import com.shemuel.site.utils.OkHttpClientInstance;
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
public class JueJinArticleSync extends ArticleSynchronizer{


    @Override
    public RestResult doSyncArticle(Article article, ThirdPartyPlatformWithAuthInfo thirdPartyPlatform) {
        try {
            // 1. 创建草稿
            JuejinArticleDTO.CreateDraftRequest createRequest = new JuejinArticleDTO.CreateDraftRequest();
            createRequest.setTitle(article.getTitle());
            createRequest.setMark_content(article.getContent());
            createRequest.setBrief_content(article.getSummary());
            log.info("创建草稿请求参数: {}", JSON.toJSONString(createRequest));

            String createDraftJson = JSON.toJSONString(createRequest);
            RestResult<String> createDraftResponseString = httpClient.doPostJson(thirdPartyPlatform.getCreateDraftUrl(), createDraftJson, buildRequestHeader(thirdPartyPlatform.getHeader()), transferCookieToArray(thirdPartyPlatform.getCookie()));
            log.info("创建草稿相应: {}", createDraftResponseString);

            JuejinArticleDTO.CreateDraftResponse createDraftResponse = JSON.parseObject(createDraftResponseString.getData(), JuejinArticleDTO.CreateDraftResponse.class);
            if (createDraftResponse.getErr_no() != 0){
                log.error("创建草稿失败: {}", createDraftResponseString);
                return RestResult.error("创建草稿失败");
            }

            String draftId = createDraftResponse.getData().getId();
            JuejinArticleDTO.PublishRequest request = new JuejinArticleDTO.PublishRequest();
            request.setDraft_id(draftId);

            RestResult publishResponseString = httpClient.doPostJson(thirdPartyPlatform.getPublishArticleUrl(), JSON.toJSONString(request), buildRequestHeader(thirdPartyPlatform.getHeader()), transferCookieToArray(thirdPartyPlatform.getCookie()));
            log.info("发布文章结果：{}", publishResponseString);
            return publishResponseString;
        } catch (Exception e) {
            log.error("Error syncing article to Juejin: {}", e.getMessage(), e);
            return RestResult.error("同步到掘金失败"+e.getMessage());
        }
    }

    @Override
    public ThirdPartyPlatformWithAuthInfo getPlatformInfo() {
       return getPlatformById(1);
    }
}
