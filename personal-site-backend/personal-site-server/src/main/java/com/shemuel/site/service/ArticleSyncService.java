package com.shemuel.site.service;

import com.shemuel.site.dto.CsdnArticleDTO;
import com.shemuel.site.entity.Article;

public interface ArticleSyncService {
    /**
     * 同步文章到CSDN平台
     * @param article 文章信息
     * @return 同步结果
     */
    boolean syncToCSDN(Article article);

    /**
     * 同步文章到掘金平台
     * @param article 文章信息
     * @return 同步结果
     */
    boolean syncToJuejin(Article article);
}