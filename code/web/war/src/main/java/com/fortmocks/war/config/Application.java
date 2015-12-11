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

import com.fortmocks.core.basis.model.Repository;
import com.fortmocks.core.basis.model.ServiceFacade;
import com.fortmocks.web.basis.model.session.token.repository.SessionTokenRepository;
import com.fortmocks.web.basis.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * The Application class contains the main method and is also responsible for configuring the application. The application
 * class is not responsible for all the configurations, but it is responsible to collect all the configuration classes
 * and mark them as configuration sources.
 *
 * @author Karl Dahlgren
 * @since 1.0
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class Application extends SpringBootServletInitializer {

    @Value("${app.version}")
    private String version;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ServiceRegistry serviceRegistry;
    @Autowired
    private SessionTokenRepository tokenRepository;

    /**
     * The configure method for Fort Mocks. The method is responsible for mark all the configuration classes
     * as sources for configurations for the SpringApplicationBuilder
     * @param application The Spring application builder
     * @return Returns the Spring application builder with new sources
     * @see org.springframework.boot.builder.SpringApplicationBuilder
     */
    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(Application.class, MimeConfig.class, MvcConfig.class, SecurityConfig.class);
    }

    /**
     * The main method for Fort Mocks. The method is responsible for triggering the Spring application to
     * run the application.
     * @param args Provided String arguments. The arguments will be forwarded towards the SpringApplication
     * @see org.springframework.boot.SpringApplication
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * The initialize method is responsible for initiating all the components when the application has been started.
     * @see Repository
     * @see com.fortmocks.core.basis.model.Service
     */
    @PostConstruct
    protected void initiate(){
        printLogo();
        initializeProcessRegistry();
        initializeRepository();
        initializeServiceFacade();
        initializeTokenRepository();
    }

    protected void printLogo(){
        final StringBuilder logo = new StringBuilder();
        logo.append("  ______         _     __  __            _        \n");
        logo.append(" |  ____|       | |   |  \\/  |          | |       \n");
        logo.append(" | |__ ___  _ __| |_  | \\  / | ___   ___| | _____ \n");
        logo.append(" |  __/ _ \\| '__| __| | |\\/| |/ _ \\ / __| |/ / __|\n");
        logo.append(" | | | (_) | |  | |_  | |  | | (_) | (__|   <\\__ \\\n");
        logo.append(" |_|  \\___/|_|   \\__| |_|  |_|\\___/ \\___|_|\\_\\___/\n");
        logo.append("===================================================\n");
        logo.append("Fort Mocks (v" + version + ")");
        logo.append("\n");
        System.out.println(logo);
    }

    /**
     * The method provides the functionality to retrieve all the repositories and initialize them
     * @see Repository
     */
    protected void initializeRepository(){
        final Map<String, Object> repositories = applicationContext.getBeansWithAnnotation(org.springframework.stereotype.Repository.class);
        for(Map.Entry<String, Object> entry : repositories.entrySet()){
            final Object value = entry.getValue();
            if(value instanceof Repository){
                final Repository repository = (Repository) value;
                repository.initialize();
            }
        }
    }


    /**
     * The method provides the functionality to retrieve all the service facades and initialize them
     * @see ServiceFacade
     * @see com.fortmocks.core.basis.model.Service
     */
    protected void initializeServiceFacade(){
        final Map<String, Object> components = applicationContext.getBeansWithAnnotation(Service.class);
        for(Map.Entry<String, Object> entry : components.entrySet()){
            final Object value = entry.getValue();
            if(value instanceof ServiceFacade){
                final ServiceFacade serviceFacade = (ServiceFacade) value;
                serviceFacade.initiate();
            }
        }
    }

    protected void initializeTokenRepository(){
        tokenRepository.initialize();
    }

    /**
     * The method provides the functionality to retrieve all the repositories and initialize them
     * @see Repository
     */
    protected void initializeProcessRegistry(){
        serviceRegistry.initialize();;
    }
}