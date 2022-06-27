package com.tony.blog.config;


import com.tony.blog.utils.Md5Utils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.util.UrlPathHelper;

import java.nio.charset.StandardCharsets;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    //设置文件虚拟路径映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/cloudDisk/file/**").addResourceLocations("file:D:\\cloudDisk\\files\\");
        registry.addResourceHandler("/cloudDisk/phonePhotos/**").addResourceLocations("file:G:\\cloudDisk\\phonePhotos\\");
        registry.addResourceHandler("/cloudDisk/avatar/**").addResourceLocations("file:D:\\cloudDisk\\avatar\\");
        registry.addResourceHandler("/cloudDisk/markdown/**").addResourceLocations("file:D:\\cloudDisk\\markdown\\");
    }


    @Bean
    public Md5Utils md5Utils(){
        return new Md5Utils();
    }

    /**
     * 中午urlpath的静态资源无法访问的问题
     * @param configurer
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper helper = new UrlPathHelper();
        helper.setUrlDecode(false);
        helper.setDefaultEncoding(StandardCharsets.UTF_8.name());
        configurer.setUrlPathHelper(helper);
    }

    //拦截器配置
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //设置公开的资源
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/images/**", "/fonts/**", "/css/**", "/lib/**", "/"
                    , "/commons/**", "/blog.html", "/blog.html", "/index.html"
                    , "/message.html", "/pictures.html", "/search.html"
                    , "/types.html", "/cloudDisk/**", "/types/**", "/comment/**"

                    , "/pictures/picturesDates/*", "/pictures/picturesDates/public"
                    , "/pictures/getFilesByDate/public/*", "/pictures/downloadFile/*", "/pictures/downloadFiles"

                    , "/blog/**", "/blogs/**"

                    , "/admin", "/admin/login.html", "/admin/login");
    }
    /*@Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }
*/
}