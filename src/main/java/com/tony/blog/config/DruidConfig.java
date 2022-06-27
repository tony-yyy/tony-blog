package com.tony.blog.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;


@Configuration
public class DruidConfig {
    /* 将自定义的 Druid数据源添加到容器中，不再让 Spring Boot 自动创建
   绑定全局配置文件中的 druid 数据源属性到 com.alibaba.druid.pool.DruidDataSource从而让它们生效
   @ConfigurationProperties(prefix = "spring.datasource")：作用就是将 全局配置文件中
   前缀为 spring.datasource的属性值注入到 com.alibaba.druid.pool.DruidDataSource 的同名参数中*/
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }
    // 后台监控: web.xml
    // 因为springboot 内置了 servlet 容器，所以没有web.xml
    // 替代方法：ServletRegistrationBean
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        // 后台需要有人登录，账号密码配置
        HashMap<String, String> initParameters = new HashMap<>();
        // 增加配置
        initParameters.put("loginUsername", "tony"); // 登录key，时固定的loginUsername、loginPassword
        initParameters.put("loginPassword", "qweqweqwe123");
        // 允许谁可以访问
//        initParameters.put("allow", "127.0.0.1");
        // 禁止谁能访问
//        initParameters.put("tony", "192.168.1.1");

        bean.setInitParameters(initParameters); // 设置初始化参数
        return bean;
    }

}
