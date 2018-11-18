package com.castlemock.deploy.jetty.common;

import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class JettyConfig {

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        final JettyServletWebServerFactory factory = new JettyServletWebServerFactory();

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
