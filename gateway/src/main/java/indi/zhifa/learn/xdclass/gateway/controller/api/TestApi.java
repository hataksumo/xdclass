package indi.zhifa.learn.xdclass.gateway.controller.api;

import indi.zhifa.learn.xdclass.common.entity.RestResponse;
import indi.zhifa.learn.xdclass.gateway.config.AppProperty;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Api(tags = "测试接口")
@Validated
@RequestMapping("/api/test")
@RestController
@AllArgsConstructor
public class TestApi {

    AppProperty mAppProperty;

    @Operation(summary = "查看值a")
    @GetMapping(value = "/a")
    Mono<RestResponse<String>> testNacosConfig(){
        RestResponse<String> resp = RestResponse.ok(mAppProperty.getA());
        return Mono.just(resp);
    }
}
