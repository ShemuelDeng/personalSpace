package com.shemuel.site.tools;

import com.shemuel.site.bo.SyncArticleResult;
import com.shemuel.site.entity.Article;
import com.shemuel.site.entity.ThirdPartyPlatform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 掘金文章同步器
 * @Author: 公众号: 加瓦点灯
 * @Date: 2025-03-31-15:13
 * @Description:
 */
@Component
@Slf4j
public class TouTiaoArticleSync extends ArticleSynchronizer{
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public SyncArticleResult doSyncArticle(Article article, ThirdPartyPlatform thirdPartyPlatform) {
        try {
            // 构建表单参数
            MultiValueMap<String, String> createDraftParams = new LinkedMultiValueMap<>();
            createDraftParams.add("source", "0");
            createDraftParams.add("content", article.getHtmlContent());
            createDraftParams.add("title", article.getTitle());
            createDraftParams.add("search_creation_info", "{\"searchTopOne\":0,\"abstract\":\"\",\"clue_id\":\"\"}");
            String titleId = System.currentTimeMillis() + "_" + (Math.random() * 10000000000L);
            createDraftParams.add("title_id", titleId);
            createDraftParams.add("extra", "{\"content_word_cnt\":" + countWords(article.getHtmlContent()) + ",\"is_multi_title\":0,\"sub_titles\":[],\"gd_ext\":{\"entrance\":\"\",\"from_page\":\"publisher_mp\",\"enter_from\":\"PC\",\"device_platform\":\"mp\",\"is_message\":0},\"tuwen_wtt_transfer_switch\":\"1\"}");
            createDraftParams.add("mp_editor_stat", "{}");
            createDraftParams.add("is_refute_rumor", "0");
            createDraftParams.add("save", "0");
            createDraftParams.add("timer_status", "0");
            createDraftParams.add("draft_form_data", "{\"coverType\":1}");
            createDraftParams.add("pgc_feed_covers", "[]");
            createDraftParams.add("article_ad_type", "3");
            createDraftParams.add("is_fans_article", "0");
            createDraftParams.add("govern_forward", "0");
            createDraftParams.add("praise", "0");
            createDraftParams.add("disable_praise", "0");
            createDraftParams.add("tree_plan_article", "0");
            createDraftParams.add("claim_exclusive", "0");
            log.info("创建头条草稿请求参数: {}", createDraftParams);

            // 构建请求头
            HttpHeaders createDraftHeaders = new HttpHeaders();
            createDraftHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            for (String header : thirdPartyPlatform.getHeader()) {
                if (!header.trim().isEmpty()) {
                    String[] parts = header.split(":", 2);
                    if (parts.length == 2) {
                        createDraftHeaders.add(parts[0].trim(), parts[1].trim());
                    }
                }
            }

            // 发送创建草稿请求
            HttpEntity<MultiValueMap<String, String>> createDraftRequest =
                    new HttpEntity<>(createDraftParams, createDraftHeaders);
            ResponseEntity<Map> createDraftResponse = restTemplate.postForEntity(
                    thirdPartyPlatform.getCreateDraftUrl(),
                    createDraftRequest,
                    Map.class
            );

            log.info("创建头条草稿响应: {}", createDraftResponse.getBody());
            if ((int) createDraftResponse.getBody().get("code") != 0) {
                log.error("创建头条草稿失败: {}", createDraftResponse.getBody().get("message"));
                return SyncArticleResult.fail("创建头条草稿失败");
            }

            // 解析返回的pgc_id
            Map<String, Object> data = (Map<String, Object>) createDraftResponse.getBody().get("data");
            String pgcId = (String) data.get("pgc_id");

            // 2. 发布文章
            MultiValueMap<String, String> publishParams = new LinkedMultiValueMap<>();
            publishParams.add("pgc_id", pgcId);
            publishParams.add("source", "0");
            publishParams.add("content", article.getHtmlContent());
            publishParams.add("title", article.getTitle());
            publishParams.add("search_creation_info", "{\"searchTopOne\":0,\"abstract\":\"\",\"clue_id\":\"\"}");
            publishParams.add("title_id", titleId);
            publishParams.add("extra", "{\"content_word_cnt\":" + countWords(article.getHtmlContent()) + ",\"is_multi_title\":0,\"sub_titles\":[],\"gd_ext\":{\"entrance\":\"\",\"from_page\":\"publisher_mp\",\"enter_from\":\"PC\",\"device_platform\":\"mp\",\"is_message\":0},\"tuwen_wtt_trans_flag\":\"1\",\"info_source\":{\"source_type\":-1}}");
            publishParams.add("mp_editor_stat", "{}");
            publishParams.add("is_refute_rumor", "0");
            publishParams.add("save", "1");
            publishParams.add("timer_status", "0");
            publishParams.add("draft_form_data", "{\"coverType\":1}");
            publishParams.add("pgc_feed_covers", "[]");
            publishParams.add("article_ad_type", "3");
            publishParams.add("is_fans_article", "0");
            publishParams.add("govern_forward", "0");
            publishParams.add("praise", "0");
            publishParams.add("disable_praise", "0");
            publishParams.add("tree_plan_article", "0");
            publishParams.add("claim_exclusive", "0");
            log.info("发布头条文章请求参数: {}", publishParams);

            // 复用相同的请求头
            HttpHeaders publishHeaders = new HttpHeaders();
            publishHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            publishHeaders.putAll(createDraftHeaders);

            // 发送发布请求
            HttpEntity<MultiValueMap<String, String>> publishRequest =
                    new HttpEntity<>(publishParams, publishHeaders);
            ResponseEntity<Map> publishResponse = restTemplate.postForEntity(
                    thirdPartyPlatform.getPublishArticleUrl(),
                    publishRequest,
                    Map.class
            );

            log.info("发布头条文章响应: {}", publishResponse.getBody());
            if ((int) publishResponse.getBody().get("code") != 0) {
                log.error("发布头条文章失败: {}", publishResponse.getBody().get("message"));
                return SyncArticleResult.fail("发布头条文章失败");
            }

            return SyncArticleResult.success();
        } catch (Exception e) {
            log.error("Error syncing article to Toutiao: {}", e.getMessage(), e);
            return SyncArticleResult.fail("同步到头条失败");
        }
    }

    @Override
    public ThirdPartyPlatform getPlatformInfo() {
       return getPlatformById(2);
    }

    // 计算文章内容的字数
    private int countWords(String content) {
        if (content == null || content.isEmpty()) {
            return 0;
        }
        // 移除HTML标签
        String plainText = content.replaceAll("<[^>]*>", "");
        return plainText.length();
    }
}
