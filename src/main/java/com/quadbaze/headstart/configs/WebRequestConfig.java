package com.quadbaze.headstart.configs;

import com.quadbaze.headstart.interceptors.LocaleDiscoveryInterceptor;
import com.quadbaze.headstart.services.MessageConvertersService;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author: Olatunji O. Longe
 * @created: 18 Oct, 2018, 5:19 PM
 */
@Configuration
public class WebRequestConfig implements WebMvcConfigurer {

    private LocaleDiscoveryInterceptor localeDiscoveryInterceptor;
    private MessageConvertersService messageConvertersService;

    @Autowired
    public WebRequestConfig(
            LocaleDiscoveryInterceptor localeDiscoveryInterceptor,
            MessageConvertersService messageConvertersService){
        this.localeDiscoveryInterceptor = localeDiscoveryInterceptor;
        this.messageConvertersService = messageConvertersService;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.addAll(messageConvertersService.getMessageConverters());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeDiscoveryInterceptor);
    }

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

}

