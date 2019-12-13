package com.github.xuyuansheng.xbdynamicdatasource;

import com.github.xuyuansheng.xbdynamicdatasource.dynamic.aspect.DynamicDataSourceAdvisor;
import com.github.xuyuansheng.xbdynamicdatasource.dynamic.aspect.DynamicDataSourceMethodInterceptor;
import com.github.xuyuansheng.xbdynamicdatasource.dynamic.datasource.DynamicDataSource;
import com.github.xuyuansheng.xbdynamicdatasource.dynamic.datasource.DynamicDataSourceConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;

/**
 * @author xuyuansheng
 * @date 2019-12-10 18:17
 */

@Configuration
@ConditionalOnBean(DataSource.class)
@ImportAutoConfiguration({DynamicDataSourceConfig.class})
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class DynamicDataSourceAutoConfig {


    @Bean
    @Primary
    DynamicDataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        return dynamicDataSource;
    }

    @Bean
    @Order(1)
    DynamicDataSourceAdvisor dynamicDataSourceAdvisor(DynamicDataSourceMethodInterceptor dynamicDataSourceMethodInterceptor) {
        DynamicDataSourceAdvisor dynamicDataSourceAdvisor = new DynamicDataSourceAdvisor(dynamicDataSourceMethodInterceptor);
        return dynamicDataSourceAdvisor;
    }

    @Bean
    DynamicDataSourceMethodInterceptor dynamicDataSourceMethodInterceptor() {
        DynamicDataSourceMethodInterceptor dynamicDataSourceMethodInterceptor = new DynamicDataSourceMethodInterceptor();
        return dynamicDataSourceMethodInterceptor;
    }

}
