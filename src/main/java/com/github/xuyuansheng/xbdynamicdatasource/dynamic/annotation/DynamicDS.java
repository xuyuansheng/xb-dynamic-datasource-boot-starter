package com.github.xuyuansheng.xbdynamicdatasource.dynamic.annotation;

import java.lang.annotation.*;

/**
 * @author xuyuansheng
 * @date 2019-12-10 15:09
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DynamicDS {

    /**
     * 具体数据源名称
     *
     * @return 数据源名称
     */
    String value();

}
