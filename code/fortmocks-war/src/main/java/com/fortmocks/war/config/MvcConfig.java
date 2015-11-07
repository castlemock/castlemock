/*
 * Copyright 2015 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fortmocks.war.config;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Locale;

/**
 * The MvcConfig class is responsible for configuring the MVC related configurations
 * @author Karl Dahlgren
 * @since 1.0
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = { "com.fortmocks" })
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private SecurityInterceptor securityInterceptor;

    /**
     * Add the resource handlers to the registry
     * @param registry The resource handler registry
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("/src/main/META-INF/resources/images/favicon.ico");
    }

    /**
     * The method is responsible for configuring the default servlet handling. It will always enable the configurer
     * @param configurer The default servlet handler configurer
     */
    @Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


    /**
     * The method takes an interceptor registry and add the interceptor created in the method localeChangeInterceptor
     * @param registry The interceptor registry which the interceptor will be added to.
     */
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(loggingInterceptor());
        registry.addInterceptor(securityInterceptor);
    }

    /**
     * The JSP view resolver is responsible for pointing out where the JSP files are located and which suffix they
     * are using
     * @return A new internal resource view resolver
     */
    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        final InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".jsp");
        return bean;
    }

    /**
     * Creates a locale resolver. This is used to identify which language is currently configured to be used
     * for a user
     * @return A new cookie locale resolver
     */
    @Bean
    public LocaleResolver localeResolver() {
        final CookieLocaleResolver ret = new CookieLocaleResolver();
        ret.setDefaultLocale(new Locale("en"));
        return ret;
    }

    /**
     * Creates a Reloadable resource bundle message source. The resource is responsible for keeping track of the basename and
     * which encoding will be used on the application.
     * @return Returns a new Reloadable resource bundle message source
     */
    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:language/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * The method handlerMapping is responsbile for creating and configuring the locale change interceptor
     * @return A new request mapping handler mapping
     */
    @Bean
    public HandlerMapping handlerMapping() {
        final LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        final RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();
        requestMappingHandlerMapping.setInterceptors(new Object[] { localeChangeInterceptor });
        return requestMappingHandlerMapping;
    }

    /**
     * Create a new locale change interceptor
     * @return A new locale change interceptor
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        final LocaleChangeInterceptor localeChangeInterceptor=new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }

    /**
     * Creates a new Logging intercepter
     * @return
     */
    @Bean
    public LoggingInterceptor loggingInterceptor(){
        return new LoggingInterceptor();
    }

    /**
     * Creates the Dozer mapper
     * @return New Dozer bean mapper
     */
    @Bean
    public DozerBeanMapper getMapper() {
        return new DozerBeanMapper();
    }
}
