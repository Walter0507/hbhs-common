package com.hbhs.common.datasource.autoconfig;

import com.alibaba.druid.pool.DruidDataSource;
import com.hbhs.common.datasource.autoconfig.property.DatabaseConfigProperties;
import com.hbhs.common.datasource.autoconfig.property.DatabaseConfig;
import com.hbhs.common.datasource.route.DynamicDataSource;
import com.hbhs.common.datasource.route.DynamicDataSourceHolder;
import com.hbhs.common.datasource.route.DynamicDataSourceInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(DatabaseConfigProperties.class)
@EnableTransactionManagement
@Slf4j
public class DataSourceAutoConfig {

    @Autowired
    private DatabaseConfigProperties configProperties;

    @Bean
    public DynamicDataSource dynamicDataSource() {
        DynamicDataSource dataSource = new DynamicDataSource();
        DataSource master = getDataSource(configProperties.getMaster());
        dataSource.setDefaultTargetDataSource(master);

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DynamicDataSourceHolder.MASTER, master);
        if (configProperties.getSlaves() != null && configProperties.getSlaves().size() > 0) {
            int index = 0;
            for (DatabaseConfig slaveProperties : configProperties.getSlaves()) {
                dataSourceMap.put(DynamicDataSourceHolder.SLAVE + "-" + index++, getDataSource(slaveProperties));
            }
        }

        dataSource.setTargetDataSources(dataSourceMap);

        return dataSource;
    }

    @Bean
    public DynamicDataSourceInterceptor dynamicDataSourceInterceptor() {
        return new DynamicDataSourceInterceptor();
    }


    @Bean
    public PlatformTransactionManager transactionManager(DynamicDataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource ds) throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(ds);
        return jdbcTemplate;
    }

    private DataSource getDataSource(DatabaseConfig databaseConfig) {

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(databaseConfig.getDriverClassName());
        dataSource.setUrl(databaseConfig.getUrl());
        dataSource.setUsername(databaseConfig.getUsername());
        dataSource.setPassword(databaseConfig.getPassword());
// 连接池启动时创建的初始化连接数量（默认值为0）
        dataSource.setInitialSize(databaseConfig.getInitialSize());
        // 连接池中可同时连接的最大的连接数
        dataSource.setMaxActive(databaseConfig.getMaxActive());
        // 连接池中最大的空闲的连接数，超过的空闲连接将被释放，如果设置为负数表示不限
        /*dataSource.setMaxIdle(databaseConfig.getMaxIdle());*/
        // 连接池中最小的空闲的连接数，低于这个数量会被创建新的连接
        dataSource.setMinIdle(databaseConfig.getMinIdle());
        // 最大等待时间，当没有可用连接时，连接池等待连接释放的最大时间，超过该时间限制会抛出异常，如果设置-1表示无限等待
        dataSource.setMaxWait(databaseConfig.getMaxWait());
        // 超过时间限制，回收没有用(废弃)的连接
        dataSource.setRemoveAbandonedTimeout(databaseConfig.getRemoveAbandonedTimeout());
        // 超过removeAbandonedTimeout时间后，是否进行没用连接（废弃）的回收
        dataSource.setRemoveAbandoned(databaseConfig.isRemoveAbandoned());
        dataSource.setTestOnBorrow(databaseConfig.isTestOnBorrow());
        dataSource.setTestOnReturn(databaseConfig.isTestOnReturn());
        dataSource.setTestWhileIdle(databaseConfig.isTestWhileIdle());
        dataSource.setValidationQuery(databaseConfig.getValidationQuery());
        // 检查无效连接的时间间隔F
        dataSource.setTimeBetweenEvictionRunsMillis(databaseConfig.getTimeBetweenEvictionRunsMillis());

        return dataSource;
    }
}