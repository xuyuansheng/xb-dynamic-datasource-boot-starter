package com.github.xuyuansheng.xbdynamicdatasource.dynamic.aspect;


import com.github.xuyuansheng.xbdynamicdatasource.dynamic.annotation.DynamicDS;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.util.Assert;

/**
 * @author xuyuansheng
 */
public class DynamicDataSourceAdvisor extends AbstractPointcutAdvisor {

    private Advice advice;
    private Pointcut pointcut;

    public DynamicDataSourceAdvisor(Advice advice) {
        this.advice = advice;
        this.pointcut = buildPointcut();
    }

    @Override
    public Pointcut getPointcut() {
        Assert.notNull(this.pointcut, "pointcut 不能为空");
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        Assert.notNull(this.pointcut, "pointcut 不能为空");
        return this.advice;
    }


    private Pointcut buildPointcut() {
        Pointcut cpc = new AnnotationMatchingPointcut(DynamicDS.class, true);
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(DynamicDS.class);
        return new ComposablePointcut(cpc).union(mpc);
    }

}
