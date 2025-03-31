package com.shemuel.site.tools;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shemuel.site.bo.SyncArticleResult;
import com.shemuel.site.dto.CsdnArticleDTO;
import com.shemuel.site.entity.Article;
import com.shemuel.site.entity.ThirdPartyPlatform;
import com.shemuel.site.exception.BusinessException;
import com.shemuel.site.mapper.ThirdPartyPlatformMapper;
import com.shemuel.site.service.ThirdPartyPlatformService;
import com.shemuel.site.utils.OkHttpClientInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 同步文章到csdn
 * @Author: 公众号: 加瓦点灯
 * @Date: 2025-03-31-15:08
 * @Description:
 */
@Component
@Slf4j
public class CsdnArticleSync extends ArticleSynchronizer{
    @Resource
    private OkHttpClientInstance client;

    @Override
    public SyncArticleResult doSyncArticle(Article article, ThirdPartyPlatform thirdPartyPlatform) {
        try {
            // 构建请求体
            CsdnArticleDTO csdnArticle = new CsdnArticleDTO();
            csdnArticle.setTitle(article.getTitle());
            csdnArticle.setMarkdowncontent(article.getContent());
            csdnArticle.setContent(article.getHtmlContent());
            csdnArticle.setDescription(article.getSummary());

            String createCsdnArticleResponse = client.doPostJson(thirdPartyPlatform.getCreateDraftUrl(), 
                JSON.toJSONString(csdnArticle),
                buildRequestHeader(thirdPartyPlatform.getHeader()),
                transferCookieToArray(thirdPartyPlatform.getCookie()));
            log.info("csdn 发布文章结果{}",createCsdnArticleResponse);
            return SyncArticleResult.success();
        } catch (Exception e) {
            log.error("Error syncing article to CSDN: {}", e.getMessage(), e);
            return SyncArticleResult.fail("同步到CSDN失败");
        }
    }

    @Override
    public ThirdPartyPlatform getPlatformInfo() {
       return super.getPlatformById(4);
    }
}
