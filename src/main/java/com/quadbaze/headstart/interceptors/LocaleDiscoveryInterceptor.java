package com.quadbaze.headstart.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * @author: Olatunji O. Longe
 * @created: 07 Sep, 2018, 6:39 PM
 *
 * This interceptor bean will initiate a locale switch based on the value of
 * the `lang` parameter appended to a request coming from the client
 */

@Component
public class LocaleDiscoveryInterceptor extends LocaleChangeInterceptor {

    public LocaleDiscoveryInterceptor(){
        this.setParamName("lang");
    }
}
