package com.castlemock.repository.core.dynamodb;

import org.dozer.DozerBeanMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.castlemock")
public class TestApplication {

    @Bean
    public DozerBeanMapper getMapper() {
        return new DozerBeanMapper();
    }
}
