package com.shemuel.site.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shemuel.site.dto.CsdnArticleDTO;
import com.shemuel.site.entity.Article;
import com.shemuel.site.service.ArticleSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ArticleSyncServiceImpl implements ArticleSyncService {

    private static final String CSDN_API_URL = "https://bizapi.csdn.net/blog-console-api/v3/mdeditor/saveArticle";
    private static final String COOKIE_FILE_PATH = "cookie/csdn.cookie";
    private static final String HEADER_FILE_PATH = "cookie/csdn.header";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean syncToCSDN(Article article) {
        try {
            // 读取Cookie
            String[] cookies = readCookieFromFile();
            if (cookies == null || cookies.length == 0) {
                log.error("Failed to read CSDN cookie");
                return false;
            }

            // 读取Cookie
            String[] headersString = readHeaderFromFile();
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

            // 发送请求
            HttpEntity<CsdnArticleDTO> requestEntity = new HttpEntity<>(csdnArticle, headers);
            Map<String, Object> response = restTemplate.postForObject(CSDN_API_URL, requestEntity, HashMap.class);

            if (response != null && response.containsKey("code") && response.get("code").equals(200)) {
                log.info("Successfully synced article to CSDN: {}", article.getTitle());
                return true;
            } else {
                log.error("Failed to sync article to CSDN: {}, response: {}", article.getTitle(), response);
                return false;
            }

        } catch (Exception e) {
            log.error("Error syncing article to CSDN: {}", e.getMessage(), e);
            return false;
        }
    }

    private String[] readCookieFromFile() {
        try {
            ClassPathResource resource = new ClassPathResource(COOKIE_FILE_PATH);
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
    private String[] readHeaderFromFile() {
        try {
            ClassPathResource resource = new ClassPathResource(HEADER_FILE_PATH);
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
}