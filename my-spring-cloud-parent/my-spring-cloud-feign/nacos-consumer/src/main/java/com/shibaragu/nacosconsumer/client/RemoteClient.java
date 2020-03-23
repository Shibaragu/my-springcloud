package com.shibaragu.nacosconsumer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author liwei
 * @date 2020-03-19 15:46
 * @describetion
 */
@FeignClient(name = "nacos-provide", fallback = RemoteHystrix.class)
public interface RemoteClient {

    @GetMapping("/helloNacos")
    String helloNacos();
}
