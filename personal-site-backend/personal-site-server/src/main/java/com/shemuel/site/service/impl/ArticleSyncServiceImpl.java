package com.shemuel.site.service.impl;

import com.alibaba.fastjson.JSON;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

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

    private OkHttpClientInstance client = new OkHttpClientInstance();



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


            String createDraftResponseString = client.doPostJson(JUEJIN_CREATE_DRAFT_URL, createDraftJson, buildRequestHeader( headers), cookies);
            log.info("创建草稿相应: {}", createDraftResponseString);

            JuejinArticleDTO.CreateDraftResponse createDraftResponse = JSON.parseObject(createDraftResponseString, JuejinArticleDTO.CreateDraftResponse.class);
            if (createDraftResponse.getErr_no() != 0){
                log.error("创建草稿失败: {}", createDraftResponseString);
            }

            String draftId = createDraftResponse.getData().getId();
            JuejinArticleDTO.PublishRequest request = new JuejinArticleDTO.PublishRequest();
            request.setDraft_id(draftId);


            String publishResponseString = client.doPostJson(JUEJIN_PUBLISH_URL, JSON.toJSONString(request), buildRequestHeader(headers), cookies);
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

            String createCsdnArticleResponse = client.doPostJson(CSDN_API_URL, JSON.toJSONString(csdnArticle), buildRequestHeader(headersString), cookies);
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
            String createDraftResponseString = client.doPostJson(ZHIHU_CREATE_DRAFT_URL, createDraftJson, buildRequestHeader(headers), null);
            log.info("创建知乎草稿响应: {}", createDraftResponseString);

            ZhihuArticleDTO.CreateDraftResponse createDraftResponse = JSON.parseObject(createDraftResponseString, ZhihuArticleDTO.CreateDraftResponse.class);
            String draftId = createDraftResponse.getId();
            if (draftId == null) {
                log.error("创建知乎草稿失败");
                return false;
            }

            // 2. 设置文章主题
            ZhihuArticleDTO.TopicRequest topicRequest = new ZhihuArticleDTO.TopicRequest();
            String setTopicUrl = String.format(ZHIHU_SET_TOPIC_URL, draftId);
            String setTopicResponseString = client.doPostJson(setTopicUrl, JSON.toJSONString(topicRequest), buildRequestHeader(headers), null);
            log.info("设置知乎文章主题响应: {}", setTopicResponseString);

            // 3. 发布文章
            ZhihuArticleDTO.PublishRequest publishRequest = new ZhihuArticleDTO.PublishRequest();
            publishRequest.getData().getDraft().setId(draftId);

            String publishResponseString = client.doPostJson(ZHIHU_PUBLISH_URL, JSON.toJSONString(publishRequest), buildRequestHeader(headers), new String[0]);
            log.info("发布知乎文章响应: {}", publishResponseString);

            ZhihuArticleDTO.PublishResponse publishResponse = JSON.parseObject(publishResponseString, ZhihuArticleDTO.PublishResponse.class);
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
            Map<String, String> createDraftParams = new HashMap<>();
            createDraftParams.put("source", "0");
            createDraftParams.put("content", article.getHtmlContent());
            createDraftParams.put("title", article.getTitle());
            createDraftParams.put("search_creation_info", "{\"searchTopOne\":0,\"abstract\":\"\",\"clue_id\":\"\"}");
            // 生成一个唯一的title_id
            String titleId = System.currentTimeMillis() + "_" + (Math.random() * 10000000000L);
            createDraftParams.put("title_id", titleId);
            createDraftParams.put("extra", "{\"content_word_cnt\":"+countWords(article.getHtmlContent())+",\"is_multi_title\":0,\"sub_titles\":[],\"gd_ext\":{\"entrance\":\"\",\"from_page\":\"publisher_mp\",\"enter_from\":\"PC\",\"device_platform\":\"mp\",\"is_message\":0},\"tuwen_wtt_transfer_switch\":\"1\"}");
            createDraftParams.put("mp_editor_stat", "{}");
            createDraftParams.put("is_refute_rumor", "0");
            createDraftParams.put("save", "0");
            createDraftParams.put("timer_status", "0");
            // 修改coverType为2，与成功请求保持一致
            createDraftParams.put("draft_form_data", "{\"coverType\":2}");
            createDraftParams.put("pgc_feed_covers", "[]");
            createDraftParams.put("article_ad_type", "3");
            createDraftParams.put("is_fans_article", "0");
            createDraftParams.put("govern_forward", "0");
            createDraftParams.put("praise", "0");
            createDraftParams.put("disable_praise", "0");
            createDraftParams.put("tree_plan_article", "0");
            createDraftParams.put("claim_exclusive", "0");
            // 记录完整的请求参数，便于调试
            log.info("创建头条草稿请求参数: {}", createDraftParams);

            FormBody.Builder formBuilder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : createDraftParams.entrySet()) {
                formBuilder.add(entry.getKey(), entry.getValue());
            }

            Request.Builder createDraftRequestBuilder = new Request.Builder()
                    .url(TOUTIAO_CREATE_DRAFT_URL)
                    .post(formBuilder.build());

            // 添加请求头
            for (String header : headers) {
                if (!header.trim().isEmpty()) {
                    String[] parts = header.split(":", 2);
                    if (parts.length == 2) {
                        createDraftRequestBuilder.addHeader(parts[0].trim(), parts[1].trim());
                    }
                }
            }

            OkHttpClient client = new OkHttpClient();
            Response createDraftResponse = client.newCall(createDraftRequestBuilder.build()).execute();
            String createDraftResponseString = createDraftResponse.body().string();
            log.info("创建头条草稿响应: {}", createDraftResponseString);

            Map<String, Object> createDraftResult = JSON.parseObject(createDraftResponseString);
            if ((int) createDraftResult.get("code") != 0) {
                log.error("创建头条草稿失败: {}", createDraftResult.get("message"));
                return false;
            }

            Map<String, Object> data = (Map<String, Object>) createDraftResult.get("data");
            String pgcId = (String) data.get("pgc_id");

            // 2. 发布文章
            Map<String, String> publishParams = new HashMap<>();
            publishParams.put("pgc_id", pgcId);
            publishParams.put("source", "0");
            publishParams.put("content", article.getHtmlContent());
            publishParams.put("title", article.getTitle());
            publishParams.put("search_creation_info", "{\"searchTopOne\":0,\"abstract\":\"\",\"clue_id\":\"\"}");
            // 使用与创建草稿相同的title_id
            publishParams.put("title_id", titleId);
            // 修改extra参数，确保与成功请求一致
            publishParams.put("extra", "{\"content_word_cnt\":"+countWords(article.getHtmlContent())+",\"is_multi_title\":0,\"sub_titles\":[],\"gd_ext\":{\"entrance\":\"\",\"from_page\":\"publisher_mp\",\"enter_from\":\"PC\",\"device_platform\":\"mp\",\"is_message\":0},\"tuwen_wtt_trans_flag\":\"1\",\"info_source\":{\"source_type\":-1}}");
            publishParams.put("mp_editor_stat", "{}");
            publishParams.put("is_refute_rumor", "0");
            publishParams.put("save", "1");
            publishParams.put("timer_status", "0");
            // 修改coverType为1，与成功请求保持一致
            publishParams.put("draft_form_data", "{\"coverType\":1}");
            publishParams.put("pgc_feed_covers", "[]");
            // 记录完整的请求参数，便于调试
            log.info("发布头条文章请求参数: {}", publishParams);
            publishParams.put("article_ad_type", "3");
            publishParams.put("is_fans_article", "0");
            publishParams.put("govern_forward", "0");
            publishParams.put("praise", "0");
            publishParams.put("disable_praise", "0");
            publishParams.put("tree_plan_article", "0");
            publishParams.put("claim_exclusive", "0");

            FormBody.Builder publishFormBuilder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : publishParams.entrySet()) {
                publishFormBuilder.add(entry.getKey(), entry.getValue());
            }

            Request.Builder publishRequestBuilder = new Request.Builder()
                    .url(TOUTIAO_CREATE_DRAFT_URL)
                    .post(publishFormBuilder.build());

            // 添加请求头
            for (String header : headers) {
                if (!header.trim().isEmpty()) {
                    String[] parts = header.split(":", 2);
                    if (parts.length == 2) {
                        publishRequestBuilder.addHeader(parts[0].trim(), parts[1].trim());
                    }
                }
            }

            Response publishResponse = client.newCall(publishRequestBuilder.build()).execute();
            String publishResponseString = publishResponse.body().string();
            log.info("发布头条文章响应: {}", publishResponseString);

            Map<String, Object> publishResult = JSON.parseObject(publishResponseString);
            if ((int) publishResult.get("code") != 0) {
                log.error("发布头条文章失败: {}", publishResult.get("message"));
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