package com.jb.framework.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author cheng
 * @since 2023/11/4 3:19 PM
 **/
@Configuration
public class DruidProperties {
    @Value("${spring.datasource.druid.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.druid.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.druid.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.druid.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.druid.connectTimeout}")
    private int connectTimeout;

    @Value("${spring.datasource.druid.socketTimeout}")
    private int socketTimeout;

    @Value("${spring.datasource.druid.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.druid.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.druid.maxEvictableIdleTimeMillis}")
    private int maxEvictableIdleTimeMillis;

    @Value("${spring.datasource.druid.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.druid.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.druid.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.druid.testOnReturn}")
    private boolean testOnReturn;

    public DruidDataSource dataSource(DruidDataSource datasource) {
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);

        /* 設定取得連線等待逾時的時間 */
        datasource.setMaxWait(maxWait);

        /* 設定驅動連線逾時時間，偵測資料庫建立連線的逾時時間(ms) */
        datasource.setConnectTimeout(connectTimeout);

        /* 設定網路逾時時間，等待資料庫操作完成的網路逾時時間(ms) */
        datasource.setSocketTimeout(socketTimeout);

        /* 設定間隔多久才進行一次檢測，檢測需要關閉的空閒連接(ms) */
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

        /* 設定一個連接在池中最小、最大生存的時間(ms) */
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setMaxEvictableIdleTimeMillis(maxEvictableIdleTimeMillis);

        /*
         * 用來偵測連線是否有效的sql，要求是一個查詢語句，常用select 'x'。
         * 如果validationQuery為null，testOnBorrow、testOnReturn、testWhileIdle都不會運作。
         */
        datasource.setValidationQuery(validationQuery);

        /*
         * 建議設定為true，不影響效能，並保證安全性。
         * 申請連線的時候檢測，如果空閒時間大於timeBetweenEvictionRunsMillis，執行validationQuery檢測連線是否有效。
         */
        datasource.setTestWhileIdle(testWhileIdle);

        /* 申請連線時執行validationQuery偵測連線是否有效，做了這個設定會降低效能。 */
        datasource.setTestOnBorrow(testOnBorrow);

        /* 歸還連線時執行validationQuery偵測連線是否有效，做了這個設定會降低效能。 */
        datasource.setTestOnReturn(testOnReturn);

        return datasource;
    }
}
