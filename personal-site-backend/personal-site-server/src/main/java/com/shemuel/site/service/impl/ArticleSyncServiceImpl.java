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
}