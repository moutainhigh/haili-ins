package com.haili.ins;

//import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
//@EnableApolloConfig
public class MemberApplication {


    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }
}
