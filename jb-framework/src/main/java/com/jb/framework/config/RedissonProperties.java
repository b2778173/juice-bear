package com.jb.framework.config;

import lombok.Data;

@Data
public class RedissonProperties {
    /**
     * redis主机地址，ip：port，有多个用半角逗号分隔
     */
    private String address;

    /**
     * 连接类型，支持standalone-单机节点，sentinel-哨兵，cluster-集群，masterslave-主从
     * only support standalone and cluster
     */
    private String type;

    /**
     * redis 连接密码
     */
    private String password;

    /**
     * 选取那个数据库 (only in standalone)
     */
    private Integer database;
    /**
     * 超时时间
     */
    private Integer timeout = 3000;
    /**
     * 对Redis集群节点状态扫描的时间间隔。单位是毫秒。默认1000
     */
    private Integer scanInterval;
    /**
     * 默认值: 当前处理核数量 * 2,这个线程池数量是在一个Redisson实例内，被其创建的所有分布式数据类型和服务，以及底层客户端所一同共享的线程池里保存的线程数量
     */
    private Integer nettyThreads = 32;

    private String clientName = "service";

    private Integer slaveConnectionMinimumIdleSize = 16;
    private Integer slaveConnectionPoolSize = 32;

    private Integer masterConnectionMinimumIdleSize = 16;
    private Integer masterConnectionPoolSize = 32;
}
