package com.github.xuyuansheng.xbdynamicdatasource.dynamic.aspect;

import com.github.xuyuansheng.xbdynamicdatasource.dynamic.DynamicDataSourceContextHolder;
import com.github.xuyuansheng.xbdynamicdatasource.dynamic.annotation.DynamicDS;
import com.github.xuyuansheng.xbdynamicdatasource.dynamic.datasource.DynamicDataSourceConfig;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

/**
 * @author xuyuansheng
 */

public class DynamicDataSourceMethodInterceptor implements MethodInterceptor {

    private final Logger LOGGER = LoggerFactory.getLogger(DynamicDataSourceMethodInterceptor.class);

    @Autowired
    private DynamicDataSourceConfig dynamicDataSourceConfig;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        try {
            Method method = methodInvocation.getMethod();
            DynamicDS dynamicDs = method.isAnnotationPresent(DynamicDS.class) ?
                    method.getDeclaredAnnotation(DynamicDS.class) :
                    method.getDeclaringClass().getDeclaredAnnotation(DynamicDS.class);
            String selectedSource = dynamicDs.value();
            if (dynamicDataSourceConfig.getShowSelectedSource()) {
                LOGGER.info("这次选择的数据源为 :　" + selectedSource);
            }
            DynamicDataSourceContextHolder.setDataSourceKey(selectedSource);
            Object result = methodInvocation.proceed();
            return result;
        } finally {
            DynamicDataSourceContextHolder.clearDataSourceKey();
        }
    }
}
