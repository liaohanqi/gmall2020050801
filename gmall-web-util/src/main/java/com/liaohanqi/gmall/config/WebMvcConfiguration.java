package com.liaohanqi.gmall.config;

import com.liaohanqi.gmall.interceptors.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}

    /** 解决跨域问题 **/
//    public void addCorsMappings(CorsRegistry registry) ;
    /** 添加拦截器 **/
//    void addInterceptors(InterceptorRegistry registry);
    /** 这里配置视图解析器 **/
//    void configureViewResolvers(ViewResolverRegistry registry);
    /** 配置内容裁决的一些选项 **/
//    void configureContentNegotiation(ContentNegotiationConfigurer configurer);
    /** 视图跳转控制器 **/
//    void addViewControllers(ViewControllerRegistry registry);
    /** 静态资源处理 **/
//    void addResourceHandlers(ResourceHandlerRegistry registry);
    /** 默认静态资源处理器 **/
//    void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer);