package com.github.xuyuansheng.xbdynamicdatasource.dynamic.aspect;


import com.github.xuyuansheng.xbdynamicdatasource.dynamic.DynamicDataSourceContextHolder;
import com.github.xuyuansheng.xbdynamicdatasource.dynamic.annotation.DynamicDS;
import com.github.xuyuansheng.xbdynamicdatasource.dynamic.datasource.DynamicDataSourceConfig;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xuyuansheng
 */
@Aspect
@Deprecated
public class AdviceAspect {

    @Autowired
    private DynamicDataSourceConfig dynamicDataSourceConfig;

    private final Logger LOGGER = LoggerFactory.getLogger(AdviceAspect.class);

    @Pointcut("@annotation(com.github.xuyuansheng.xbdynamicdatasource.dynamic.annotation.DynamicDS)")
    public void advice() {
    }

    @Before("advice()")
    public void beforeAdvice(JoinPoint joinPoint) {
        if (!(joinPoint instanceof MethodInvocationProceedingJoinPoint)) {
            return;
        }
        MethodInvocationProceedingJoinPoint point = MethodInvocationProceedingJoinPoint.class.cast(joinPoint);
        DynamicDS annotation = (DynamicDS) point.getSignature().getDeclaringType().getAnnotation(DynamicDS.class);
        String selectedSource = annotation.value();
        if (dynamicDataSourceConfig.getShowSelectedSource()) {
            LOGGER.debug("这次选择的数据源为 :　" + selectedSource);
        }
        DynamicDataSourceContextHolder.setDataSourceKey(selectedSource);
    }

    @After("advice()")
    public void afterAdvice(JoinPoint joinPoint) {
        DynamicDataSourceContextHolder.clearDataSourceKey();
    }

}
