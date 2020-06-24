package com.kakaopay.assignment.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Collection;
import java.util.Collections;

@Configuration
public class FilterConfig {
    Collection<String> DEFAULT_URL_PATTERNS = Collections.singletonList("/*");

    @Bean
    public FilterRegistrationBean characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setUrlPatterns(DEFAULT_URL_PATTERNS);
        registrationBean.setFilter(characterEncodingFilter);
        return registrationBean;
    }
}
