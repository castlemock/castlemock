package com.castlemock.deploy.tomcat.common;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class TomcatConfig {

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        final TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();

        final Set<ErrorPage> errorPages = new HashSet<>();
        errorPages.add(new ErrorPage(HttpStatus.NOT_FOUND, "/web/error/404"));
        factory.setErrorPages(errorPages);

        final MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        mappings.add("xsd", "text/xml; charset=utf-8");
        mappings.add("ico", "image/x-icon");
        factory.setMimeMappings(mappings);

        return factory;
    }

}
