package com.shemuel.site.tools;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shemuel.site.bo.SyncArticleResult;
import com.shemuel.site.bo.ThirdPartyPlatformWithAuthInfo;
import com.shemuel.site.common.RestResult;
import com.shemuel.site.dto.CsdnArticleDTO;
import com.shemuel.site.entity.Article;
import com.shemuel.site.entity.ThirdPartyPlatform;
import com.shemuel.site.exception.BusinessException;
import com.shemuel.site.mapper.ThirdPartyPlatformMapper;
import com.shemuel.site.service.ThirdPartyPlatformService;
import com.shemuel.site.utils.OkHttpClientInstance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 同步文章到csdn
 * @Author: 公众号: 加瓦点灯
 * @Date: 2025-03-31-15:08
 * @Description:
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CsdnArticleSync extends ArticleSynchronizer{

    private final RestTemplate restTemplate;

    @Override
    public RestResult doSyncArticle(Article article, ThirdPartyPlatformWithAuthInfo thirdPartyPlatform) {
        try {
            // 构建请求体
            CsdnArticleDTO csdnArticle = new CsdnArticleDTO();
            csdnArticle.setTitle(article.getTitle());
            csdnArticle.setMarkdowncontent(article.getContent());
            csdnArticle.setContent(article.getHtmlContent());
            csdnArticle.setDescription(article.getSummary());

            // 构建请求头（包含Cookie）
            HttpHeaders headers = new HttpHeaders();
            // 添加原始请求头
            headers.setAll(buildRequestHeader(thirdPartyPlatform.getHeader()));
            // 添加Cookie（需要拼接为字符串）
            if (StringUtils.isNotEmpty(thirdPartyPlatform.getCookie())){
                headers.add(HttpHeaders.COOKIE, String.join("; ", transferCookieToArray(thirdPartyPlatform.getCookie())));
            }
            // 设置Content-Type
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 创建请求实体
            HttpEntity<String> requestEntity = new HttpEntity<>(
                    JSON.toJSONString(csdnArticle),
                    headers
            );

            // 发送POST请求
            ResponseEntity<String> response = restTemplate.exchange(
                    thirdPartyPlatform.getCreateDraftUrl(),
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            // 将响应转换为RestResult（根据实际情况调整）
            RestResult createCsdnArticleResponse = new RestResult();
            createCsdnArticleResponse.setCode(response.getStatusCode().value());
            createCsdnArticleResponse.setData(response.getBody());

            log.info("csdn 发布文章结果{}", createCsdnArticleResponse);
            return createCsdnArticleResponse;
        } catch (Exception e) {
            log.error("Error syncing article to CSDN: {}", e.getMessage(), e);
            return RestResult.error(500, e.getMessage());
        }
    }

    @Override
    public ThirdPartyPlatformWithAuthInfo getPlatformInfo() {
       return super.getPlatformById(4);
    }
}
