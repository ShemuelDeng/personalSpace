package com.shemuel.site.bo;

import lombok.Data;

/**
 * 同步第三方平台结果
 * @Author: 公众号: 加瓦点灯
 * @Date: 2025-03-31-14:55
 * @Description:
 */
@Data
public class SyncArticleResult<T> {

    private boolean success;

    private T failReason;

    public static SyncArticleResult fail(String msg) {
        SyncArticleResult syncArticleResult = new SyncArticleResult();
        syncArticleResult.setFailReason(msg);
        return syncArticleResult;
    }

    public static SyncArticleResult success() {
        SyncArticleResult syncArticleResult = new SyncArticleResult();
        syncArticleResult.setSuccess(true);
        return syncArticleResult;
    }
}
