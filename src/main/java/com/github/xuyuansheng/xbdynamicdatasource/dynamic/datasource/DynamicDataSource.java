package com.github.xuyuansheng.xbdynamicdatasource.dynamic.datasource;

import com.github.xuyuansheng.xbdynamicdatasource.dynamic.DynamicDataSourceContextHolder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 动态数据源
 *
 * @author xuyuansheng
 * @date 2019-12-10 09:03
 */
@Primary
public class DynamicDataSource extends AbstractRoutingDataSource implements BeanFactoryAware {

    @Autowired
    private DynamicDataSourceConfig dynamicDataSourceConfig;

    private BeanFactory beanFactory;

    @Override
    public void afterPropertiesSet() {
        Map<String, DataSource> beans =
                ((ListableBeanFactory) this.beanFactory).getBeansOfType(DataSource.class);
        List<String> self = beans.entrySet().stream()
                .filter(d -> d.getValue() instanceof DynamicDataSource)
                .map(d -> d.getKey())
                .collect(Collectors.toList());
        /* 把自己从目标数据源中去掉 */
        self.forEach(k -> beans.remove(k));

        HashMap<Object, Object> objectHashMap = new HashMap<>(beans);
        super.setTargetDataSources(objectHashMap);
        super.setDefaultTargetDataSource(this.setDefaultTargetDataSource(beans));
        super.afterPropertiesSet();
    }

    /**
     * 找默认的目标数据源,如果没有找到,则默认使用第一个,如果一个数据源都没有,就会抛出异常
     *
     * @param beans 真实数据源列表,为空时会找不到数据源抛出异常
     * @return
     */
    private DataSource setDefaultTargetDataSource(Map<String, DataSource> beans) {
        return beans.getOrDefault(dynamicDataSourceConfig.getDefaultDataSource(),
                beans.values()
                        .stream()
                        .findFirst().orElseThrow(() -> new RuntimeException("没有找到默认目标数据源 : DefaultTargetDataSource ")));
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceKey = DynamicDataSourceContextHolder.getDataSourceKey();
        return dataSourceKey;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

}
