package com.shanzhu.platform;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;


//开启事务
@EnableTransactionManagement
@MapperScan(basePackages = {"com.qy.news.mapper"})
@SpringBootApplication
public class AdminApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(AdminApplication.class, args);
        //拿出所有的bean
        System.out.println("----------------------");


    }
}
