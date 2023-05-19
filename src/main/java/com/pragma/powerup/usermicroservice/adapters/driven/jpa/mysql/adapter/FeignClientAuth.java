package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "http://localhost:8091")
public interface FeignClientAuth {
    @PostMapping("/auth/")
    String getUserAuth(@RequestHeader String authorizationHeader);
}
