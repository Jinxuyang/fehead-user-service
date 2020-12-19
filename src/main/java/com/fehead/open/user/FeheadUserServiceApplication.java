package com.fehead.open.user;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description:
 * @Author lmwis
 * @Date 2019-11-10 16:58
 * @Version 1.0
 */
@SpringBootApplication(scanBasePackages={"com.fehead.lang","com.fehead.open.user"})
@MapperScan({"com.fehead.open.user.dao"})
@Configuration
@EnableTransactionManagement // 启用事务
@EnableSwagger2 // api
//@EnableEurekaClient // 启用注册中心
@EnableFeignClients // 开启feign
@EnableCircuitBreaker // hystrix
@EnableDiscoveryClient //nacos 服务发现
public class FeheadUserServiceApplication {

    private static final Logger logger = LoggerFactory.getLogger(FeheadUserServiceApplication.class);

    public static void main(String[] args) {


        // 应用关闭钩子
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                // 从注册中心实例注册列表删除
                logger.info("This should completed shut down of DiscoveryClient");

            }
        });

        SpringApplication.run(FeheadUserServiceApplication.class,args);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
