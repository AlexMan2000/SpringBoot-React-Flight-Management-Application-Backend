package nyu.alex.config;

import nyu.alex.interceptor.LoginInterceptor;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyMvcConfg implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry){
//        registry.addInterceptor(new LoginInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/","/login","/index")
//                .excludePathPatterns("/public/**","/resources/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowCredentials(true)
                .allowedMethods("*")
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
