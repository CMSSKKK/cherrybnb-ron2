package com.team14.cherrybnb.common.config;

import com.team14.cherrybnb.auth.ui.AuthInterceptor;
import com.team14.cherrybnb.auth.ui.LoginFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    private final String kakaoKey;
    private final AuthInterceptor authInterceptor;

    public WebConfig(@Value("${kakao.rest.api.key}") String kakaoKey, AuthInterceptor authInterceptor) {
        this.kakaoKey = kakaoKey;
        this.authInterceptor = authInterceptor;
    }

    @Bean(name = "kakao-navi")
    public WebClient kakaoWebClient() {
        return WebClient.builder()
                .baseUrl("https://apis-navi.kakaomobility.com/v1")
                .defaultHeader(HttpHeaders.HOST, "apis-navi.kakaomobility.com")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoKey)
                .build();
    }

    @Bean(name = "github")
    public WebClient githubWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.github.com")
                .build();

    }

    @Bean
    public FilterRegistrationBean<Filter> loginFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/error");
    }
}
