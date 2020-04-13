package com.shibaragu.myrateimiter.controller;

import com.shibaragu.myrateimiter.service.impl.RateLimiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author liwei
 * @date 2020-04-13 20:41
 * @describetion
 */
@RestController
@RequestMapping("rateLimiter")
public class RateLimiterController {

    @Autowired
    RateLimiterService rateLimiterService;

    @GetMapping("/sendMsg")
    public void sendMsg(@RequestParam String phone) {
        while (true) {
            rateLimiterService.sendMsg(phone);
        }
    }
}
