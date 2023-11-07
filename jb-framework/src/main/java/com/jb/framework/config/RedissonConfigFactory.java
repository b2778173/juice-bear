package com.jb.framework.config;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.redisson.config.Config;

@Slf4j
public class RedissonConfigFactory {

    /**
     * 单节点
     */
    private static final String STANDALONE = "standalone";
    /**
     * 集群
     */
    private static final String CLUSTER = "cluster";

    private static final String PREFIX = "redis://";

    public Config createRedissonProperties(RedissonProperties redissonProperties) {
        Preconditions.checkNotNull(redissonProperties);
        Preconditions.checkNotNull(redissonProperties.getAddress());
        Preconditions.checkNotNull(redissonProperties.getPassword());
        Preconditions.checkNotNull(redissonProperties.getType());
        String mode = redissonProperties.getType();

        Config config = new Config();
        config.setNettyThreads(redissonProperties.getNettyThreads());

        if (mode.equalsIgnoreCase(STANDALONE)) {
            Preconditions.checkNotNull(redissonProperties.getDatabase());
            if (!redissonProperties.getAddress().startsWith(PREFIX)) {
                redissonProperties.setAddress(PREFIX + redissonProperties.getAddress());
            }
            config.useSingleServer()
                    .setKeepAlive(true)
                    .setClientName(redissonProperties.getClientName())
                    .setAddress(redissonProperties.getAddress())
                    .setPassword(redissonProperties.getPassword())
                    .setDatabase(redissonProperties.getDatabase());
            log.info("使用模式 = {} , 创建Redisson config", mode);
        } else if (mode.equalsIgnoreCase(CLUSTER)) {
            String[] nodes = redissonProperties.getAddress().split(",");
            for (int i = 0; i < nodes.length; i++) {
                if (!nodes[i].startsWith(PREFIX)) {
                    nodes[i] = PREFIX + nodes[i];
                }
            }
            config.useClusterServers()
                    .setKeepAlive(true)
                    .setClientName(redissonProperties.getClientName())
                    .addNodeAddress(nodes)

                    .setSlaveConnectionMinimumIdleSize(redissonProperties.getSlaveConnectionMinimumIdleSize())
                    .setSlaveConnectionPoolSize(redissonProperties.getSlaveConnectionPoolSize())

                    .setMasterConnectionMinimumIdleSize(redissonProperties.getMasterConnectionMinimumIdleSize())
                    .setMasterConnectionPoolSize(redissonProperties.getMasterConnectionPoolSize())

                    .setPassword(redissonProperties.getPassword())
                    .setTimeout(redissonProperties.getTimeout());
            log.info("使用模式 = {} , 创建Redisson config", mode);
        } else {
            throw new IllegalArgumentException("创建Redisson连接Config失败！当前连接方式:" + mode);
        }

        return config;
    }


}
