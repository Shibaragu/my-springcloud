package com.shibaragu.myrateimiter.service.impl;

import org.redisson.Redisson;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

/**
 * @author zhoutianyu
 * @date 2020/3/15
 * @time 18:55
 */
@Service
public class RateLimiterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RateLimiterService.class);

    private static RedissonClient client;

    @PostConstruct
    public void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.56.101:6379");
        client = Redisson.create(config);
    }

   /* static {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.56.101:6379");
        client = Redisson.create(config);
    }*/

    public void sendMsg(String phone) {
        if (!StringUtils.isEmpty(phone)) {
            RRateLimiter rateLimiter =
                    client.getRateLimiter("REDISSON_RATE_LIMITER" + phone);
            //每10秒产生1个令牌
            rateLimiter.trySetRate(RateType.OVERALL, 1, 10,
                    RateIntervalUnit.SECONDS);

            if (rateLimiter.tryAcquire()) {
                LOGGER.info("向手机:{}发送短信", phone);
            }
        }
    }
}