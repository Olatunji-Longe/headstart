package com.quadbaze.headstart.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author Olatunji O. Longe: Created on (17/08/2018)
 * Ref: https://www.baeldung.com/spring-boot-internationalization
 */
@Configuration
public class LocalizationConfig {

    @Value("${spring.messages.basename}")
    private String baseName;

    @Value("${spring.messages.cache-duration}")
    private Integer cacheDuration;

    @Value("${spring.messages.encoding}")
    private String defaultEncoding;

    /**
     * Message source for fetching messages and properties.
     * NOTE - Method must be named `messageSource` otherwise the Bean name must
     * be explicitly specified as `messageSource`. Here I just do both...
     *
     * @return
     */
    @Bean(name="messageSource")
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(baseName, "classpath:application");
        messageSource.setCacheSeconds(cacheDuration);
        messageSource.setDefaultEncoding(defaultEncoding);
        return messageSource;
    }

}
