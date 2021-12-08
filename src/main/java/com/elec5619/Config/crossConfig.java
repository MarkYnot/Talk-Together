package com.elec5619.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/*
 *
 * Resolving cross-domain issues
 * */
@Configuration
public class crossConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

//        List<String> ALLOW_ORIGINS = new ArrayList<>();
//        ALLOW_ORIGINS.add("http://localhost:8080");
//        ALLOW_ORIGINS.add("http://api.fanyi.baidu.com/api/trans/vip/translate");

        registry.addMapping("/**").allowedOrigins("http://localhost:8080").allowCredentials(true).allowedHeaders("*")
                .allowedMethods("GET","POST","PUT","DELETE").maxAge(3600);

//        registry.addMapping("/**").allowedOriginPatterns("*").allowCredentials(true)
//                .allowedMethods("GET","POST","PUT","DELETE").maxAge(3600);
    }
}
