package com.github.xuyuansheng.xbdynamicdatasource.dynamic.datasource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 020102
 * @date 2019-12-10 19:05
 */
@org.springframework.context.annotation.Configuration
@ConfigurationProperties("spring.dynamic.datasource")
@Data
public class DynamicDataSourceConfig {
    public String defaultDataSource = "master";
    public Boolean showSelectedSource = false;

}
