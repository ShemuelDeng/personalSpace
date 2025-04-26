package com.shemuel.site.tools.mp;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shemuel.site.annotation.DisableUserFilter;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

@Intercepts({
    @Signature(type = Executor.class, method = "query", args = {
        MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class
    })
})
public class UserIdAutoFillInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];

        // 检查是否跳过过滤
        Method mapperMethod = getMapperMethod(ms);
        if (mapperMethod == null || mapperMethod.isAnnotationPresent(DisableUserFilter.class)) {
            return invocation.proceed(); // 跳过处理
        }

        // 获取当前用户ID
        Long userId = StpUtil.getLoginIdAsLong();
        
        // 动态修改查询条件（以 QueryWrapper 为例）
        if (parameter instanceof Map) {
            Map<?, ?> paramMap = (Map<?, ?>) parameter;
            if (paramMap.containsKey("ew")) {
                QueryWrapper<?> wrapper = (QueryWrapper<?>) paramMap.get("ew");
                wrapper.eq("user_id", userId);
            } else {
                QueryWrapper<?> wrapper = new QueryWrapper<>();
                wrapper.eq("user_id", userId);
                ((Map<String, Object>) parameter).put("ew", wrapper);
            }
        }

        return invocation.proceed();
    }

    // 通过反射获取 Mapper 方法
    private Method getMapperMethod(MappedStatement ms) {
        String methodName = ms.getId().substring(ms.getId().lastIndexOf(".") + 1);
        try {
            Class<?> mapperClass = Class.forName(ms.getId().substring(0, ms.getId().lastIndexOf(".")));
            return Arrays.stream(mapperClass.getMethods())
                    .filter(m -> m.getName().equals(methodName))
                    .findFirst()
                    .orElse(null);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {}
}