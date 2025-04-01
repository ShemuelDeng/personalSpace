package com.shemuel.site.service.impl;

import com.alibaba.fastjson.JSON;
import com.shemuel.site.common.RestResult;
import com.shemuel.site.dto.CsdnArticleDTO;
import com.shemuel.site.dto.JuejinArticleDTO;
import com.shemuel.site.dto.ZhihuArticleDTO;
import com.shemuel.site.entity.Article;
import com.shemuel.site.service.ArticleSyncService;
import com.shemuel.site.utils.OkHttpClientInstance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleSyncServiceImpl implements ArticleSyncService {

    private static final String CSDN_API_URL = "https://bizapi.csdn.net/blog-console-api/v3/mdeditor/saveArticle";
    private static final String COOKIE_FILE_PATH = "cookie/csdn.cookie";
    private static final String HEADER_FILE_PATH = "cookie/csdn.header";

    private static final String JUEJIN_CREATE_DRAFT_URL = "https://api.juejin.cn/content_api/v1/article_draft/create?aid=26081&uuid=7277531885797787195";
    private static final String JUEJIN_UPDATE_DRAFT_URL = "https://api.juejin.cn/content_api/v1/article_draft/update?aid=26081&uuid=7277531885797787195";
    private static final String JUEJIN_PUBLISH_URL = "https://api.juejin.cn/content_api/v1/article/publish?aid=26081&uuid=7277531885797787195";
    private static final String JUEJIN_COOKIE_FILE_PATH = "cookie/juejin.cookie";
    private static final String JUEJIN_HEADER_FILE_PATH = "cookie/juejin.header";

    private static final String ZHIHU_CREATE_DRAFT_URL = "https://zhuanlan.zhihu.com/api/articles/drafts";
    private static final String ZHIHU_SET_TOPIC_URL = "https://zhuanlan.zhihu.com/api/articles/%s/topics";
    private static final String ZHIHU_PUBLISH_URL = "https://www.zhihu.com/api/v4/content/publish";
    private static final String ZHIHU_HEADER_FILE_PATH = "cookie/zhihu.header";

    private OkHttpClientInstance client = OkHttpClientInstance.getInstance();

    private final RestTemplate restTemplate;

    @Override
    public boolean syncToJuejin(Article article) {
        try {
            // 读取Cookie和Header
            String[] cookies = readCookieFromFile(JUEJIN_COOKIE_FILE_PATH);
            String[] headers = readHeaderFromFile(JUEJIN_HEADER_FILE_PATH);
            if (cookies == null || headers == null) {
                return false;
            }

            // 1. 创建草稿
            JuejinArticleDTO.CreateDraftRequest createRequest = new JuejinArticleDTO.CreateDraftRequest();
            createRequest.setTitle(article.getTitle());
            createRequest.setMark_content(article.getContent());
            createRequest.setBrief_content(article.getSummary());
            log.info("创建草稿请求参数: {}", JSON.toJSONString(createRequest));



            String createDraftJson = JSON.toJSONString(createRequest);

            RestResult<String> createDraftResponseString = client.doPostJson(JUEJIN_CREATE_DRAFT_URL, createDraftJson, buildRequestHeader( headers), cookies);
            log.info("创建草稿相应: {}", createDraftResponseString);

            if (!RestResult.isSuccess(createDraftResponseString)){
                return false;
            }

            JuejinArticleDTO.CreateDraftResponse createDraftResponse = JSON.parseObject(createDraftResponseString.getData(), JuejinArticleDTO.CreateDraftResponse.class);
            if (createDraftResponse.getErr_no() != 0){
                log.error("创建草稿失败: {}", createDraftResponseString);
            }

            String draftId = createDraftResponse.getData().getId();
            JuejinArticleDTO.PublishRequest request = new JuejinArticleDTO.PublishRequest();
            request.setDraft_id(draftId);


            RestResult publishResponseString = client.doPostJson(JUEJIN_PUBLISH_URL, JSON.toJSONString(request), buildRequestHeader(headers), cookies);
            log.info("发布文章结果：{}", publishResponseString);
            return true;


        } catch (Exception e) {
            log.error("Error syncing article to Juejin: {}", e.getMessage(), e);
            return false;
        }
    }


    private Map<String,String> buildRequestHeader(String[] headers) {

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
    @Override
    public boolean syncToCSDN(Article article) {
        try {
            // 读取Cookie
            String[] cookies = readCookieFromFile(COOKIE_FILE_PATH);
            if (cookies == null || cookies.length == 0) {
                log.error("Failed to read CSDN cookie");
                return false;
            }

            // 读取Cookie
            String[] headersString = readHeaderFromFile(HEADER_FILE_PATH);
            if (headersString == null || headersString.length == 0) {
                log.error("Failed to read CSDN headers");
                return false;
            }

            // 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            for (String header : headersString) {
                if (!header.trim().isEmpty()) {
                    String[] parts = header.split(":", 2);
                    if (parts.length == 2) {
                        headers.add(parts[0].trim(), parts[1].trim());
                    }
                }
            }

            // 设置cookie
            for (String cookie : cookies) {
                if (!cookie.trim().isEmpty()) {
                    headers.add("Cookie", cookie.trim());
                }
            }

            // 构建请求体
            CsdnArticleDTO csdnArticle = new CsdnArticleDTO();
            csdnArticle.setTitle(article.getTitle());
            csdnArticle.setMarkdowncontent(article.getContent());
            csdnArticle.setContent(article.getHtmlContent());
            csdnArticle.setDescription(article.getSummary());

            RestResult createCsdnArticleResponse = client.doPostJson(CSDN_API_URL, JSON.toJSONString(csdnArticle), buildRequestHeader(headersString), cookies);
            log.info("csdn 发布文章结果{}",createCsdnArticleResponse);
            return true;
        } catch (Exception e) {
            log.error("Error syncing article to CSDN: {}", e.getMessage(), e);
            return false;
        }
    }


    private String[] readCookieFromFile(String cookieFilePath) {
        try {
            ClassPathResource resource = new ClassPathResource(cookieFilePath);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
                return reader.lines()
                        .filter(line -> !line.trim().isEmpty())
                        .map(s -> s.replace("\t", ""))
                        .toArray(String[]::new);
            }
        } catch (Exception e) {
            log.error("Error reading CSDN cookie file: {}", e.getMessage(), e);
            return null;
        }
    }
    private String[] readHeaderFromFile(String headerFilePath) {
        try {
            ClassPathResource resource = new ClassPathResource(headerFilePath);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
                return reader.lines()
                        .filter(line -> !line.trim().isEmpty())
                        .toArray(String[]::new);
            }
        } catch (Exception e) {
            log.error("Error reading CSDN cookie file: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean syncToZhihu(Article article) {
        try {
            // 读取Header
            String[] headers = readHeaderFromFile(ZHIHU_HEADER_FILE_PATH);
            if (headers == null) {
                log.error("Failed to read Zhihu headers");
                return false;
            }

            // 1. 创建文章草稿
            ZhihuArticleDTO.CreateDraftRequest createDraftRequest = new ZhihuArticleDTO.CreateDraftRequest();
            createDraftRequest.setContent(article.getHtmlContent());
            createDraftRequest.setTitle(article.getTitle());

            String createDraftJson = JSON.toJSONString(createDraftRequest);
            RestResult<String> createDraftResponseString = client.doPostJson(ZHIHU_CREATE_DRAFT_URL, createDraftJson, buildRequestHeader(headers), null);
            log.info("创建知乎草稿响应: {}", createDraftResponseString);

            ZhihuArticleDTO.CreateDraftResponse createDraftResponse = JSON.parseObject(createDraftResponseString.getData(), ZhihuArticleDTO.CreateDraftResponse.class);
            String draftId = createDraftResponse.getId();
            if (draftId == null) {
                log.error("创建知乎草稿失败");
                return false;
            }

            // 2. 设置文章主题
            ZhihuArticleDTO.TopicRequest topicRequest = new ZhihuArticleDTO.TopicRequest();
            String setTopicUrl = String.format(ZHIHU_SET_TOPIC_URL, draftId);
            RestResult setTopicResponseString = client.doPostJson(setTopicUrl, JSON.toJSONString(topicRequest), buildRequestHeader(headers), null);
            log.info("设置知乎文章主题响应: {}", setTopicResponseString);

            // 3. 发布文章
            ZhihuArticleDTO.PublishRequest publishRequest = new ZhihuArticleDTO.PublishRequest();
            publishRequest.getData().getDraft().setId(draftId);

            RestResult<String> publishResponseString = client.doPostJson(ZHIHU_PUBLISH_URL, JSON.toJSONString(publishRequest), buildRequestHeader(headers), new String[0]);
            log.info("发布知乎文章响应: {}", publishResponseString);

            ZhihuArticleDTO.PublishResponse publishResponse = JSON.parseObject(publishResponseString.getData(), ZhihuArticleDTO.PublishResponse.class);
            if (publishResponse.getCode() != 0) {
                log.error("发布知乎文章失败: {}", publishResponse.getMessage());
                return false;
            }

            return true;
        } catch (Exception e) {
            log.error("Error syncing article to Zhihu: {}", e.getMessage(), e);
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(TOUTIAO_CREATE_DRAFT_URL.length());
    }

    private static final String TOUTIAO_CREATE_DRAFT_URL = "https://mp.toutiao.com/mp/agw/article/publish?source=mp&type=article&aid=1231&msToken=Gbu0ObV-OYxiJWKQQXmiAc8fiWGZfWHHH21u2FQaytFt6CahOLumTQdQxYioKBMpCDFmh4-mI5jD1S90uRIzApXSIrhGMVdC1JvoAMguvvIZpaZ39uaq2nQtxLp2DQSXNFCykjvdUIzA-Huncl-MzD5atyVQkgKKpLzfdN9RipOj9D9nx3Vdsw%3D%3D&a_bogus=EX4nDtywdNmfadarYOBbSRKlFADlrsuydBTdbox6HKF%2FGHFTMKN7pCjknPLWXoZUZmBt2qAHJfb3YVjbQseslFokLmkkuDUSwU2I97vohqNdTevsDrDpCLszKwBYUcsx-52fiCgRWGMqgjA5Vr9EAQanw%2FXJ5cuB%2Fr-vV1LGEoymUWWjin2Va3fhzhlH";
//    private static final String TOUTIAO_PUBLISH_URL = "https://mp.toutiao.com/mp/agw/article/publish?source=mp&type=article&aid=1231";

    private static final String TOUTIAO_HEADER_FILE_PATH = "cookie/toutiao.header";

    @Override
    public boolean syncToTouTiao(Article article) {
        try {
            // 读取请求头信息
            String[] headers = readHeaderFromFile(TOUTIAO_HEADER_FILE_PATH);
            if (headers == null) {
                log.error("Failed to read Toutiao headers");
                return false;
            }


            // 1. 创建文章草稿
            // 构建表单参数
            MultiValueMap<String, String> createDraftParams = new LinkedMultiValueMap<>();
            createDraftParams.add("source", "0");
            createDraftParams.add("content", article.getHtmlContent());
            createDraftParams.add("title", article.getTitle());
            createDraftParams.add("search_creation_info", "{\"searchTopOne\":0,\"abstract\":\"\",\"clue_id\":\"\"}");
            String titleId = System.currentTimeMillis() + "_" + (Math.random() * 10000000000L);
            createDraftParams.add("title_id", titleId);
            createDraftParams.add("extra", "{\"content_word_cnt\":"+countWords(article.getHtmlContent())+",\"is_multi_title\":0,\"sub_titles\":[],\"gd_ext\":{\"entrance\":\"\",\"from_page\":\"publisher_mp\",\"enter_from\":\"PC\",\"device_platform\":\"mp\",\"is_message\":0},\"tuwen_wtt_transfer_switch\":\"1\"}");
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
            for (String header : headers) {
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
                    TOUTIAO_CREATE_DRAFT_URL,
                    createDraftRequest,
                    Map.class
            );

            log.info("创建头条草稿响应: {}", createDraftResponse.getBody());
            if ((int) createDraftResponse.getBody().get("code") != 0) {
                log.error("创建头条草稿失败: {}", createDraftResponse.getBody().get("message"));
                return false;
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
            publishParams.add("extra", "{\"content_word_cnt\":"+countWords(article.getHtmlContent())+",\"is_multi_title\":0,\"sub_titles\":[],\"gd_ext\":{\"entrance\":\"\",\"from_page\":\"publisher_mp\",\"enter_from\":\"PC\",\"device_platform\":\"mp\",\"is_message\":0},\"tuwen_wtt_trans_flag\":\"1\",\"info_source\":{\"source_type\":-1}}");
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
                    TOUTIAO_CREATE_DRAFT_URL,  // 注意：这里应该确认是否是正确的发布URL
                    publishRequest,
                    Map.class
            );

            log.info("发布头条文章响应: {}", publishResponse.getBody());
            if ((int) publishResponse.getBody().get("code") != 0) {
                log.error("发布头条文章失败: {}", publishResponse.getBody().get("message"));
                return false;
            }

            return true;
        } catch (Exception e) {
            log.error("Error syncing article to Toutiao: {}", e.getMessage(), e);
            return false;
        }
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