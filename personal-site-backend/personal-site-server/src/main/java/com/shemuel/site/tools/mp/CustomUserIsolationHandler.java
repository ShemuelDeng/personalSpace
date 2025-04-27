package com.shemuel.site.tools.mp;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomUserIsolationHandler implements TenantLineHandler {

    private static List<String> ignoreTableList = new ArrayList<>();

    static {
        ignoreTableList.add("article_tag_relation");
        ignoreTableList.add("gen_table");
        ignoreTableList.add("gen_table_column");
        ignoreTableList.add("third_party_platform");
        ignoreTableList.add("user_profile");
    }

    @Override
    public Expression getTenantId() {
        // 假设有一个租户上下文，能够从中获取当前用户的租户
         Long tenantId = StpUtil.getLoginIdAsLong();
        // 返回租户ID的表达式，LongValue 是 JSQLParser 中表示 bigint 类型的 class
        return new LongValue(tenantId);
    }

    @Override
    public String getTenantIdColumn() {
        return "user_Id";
    }

    @Override
    public boolean ignoreTable(String tableName) {
        // 根据需要返回是否忽略该表
        return ignoreTableList.contains(tableName);
    }

}