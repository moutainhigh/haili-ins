package com.haili.ins.feign;


import com.haili.ins.core.invoke.dto.InvokeRequest;
import com.haili.ins.core.invoke.dto.InvokeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author: LeonMa
 * @date: 2018/12/23 23:10
 */
@Component
@FeignClient(name = "oauth2-server")
public interface AuthOAuth2Feign {

    /**
     * member invoke
     * @param invokeRequest
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/invoke")
    InvokeResponse invoke(@RequestBody InvokeRequest invokeRequest);
}
