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

package com.castlemock.app.config;

import com.castlemock.core.basis.model.LegacyRepository;
import com.castlemock.core.basis.model.ServiceFacade;
import com.castlemock.repository.Repository;
import com.castlemock.repository.token.SessionTokenRepository;
import com.castlemock.web.core.manager.FileManager;
import com.castlemock.web.core.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * The Application class contains the main method and is also responsible for configuring the application. The application
 * class is not responsible for all the configurations, but it is responsible to collect all the configuration classes
 * and mark them as configuration sources.
 *
 * @author Karl Dahlgren
 * @author Mohammad Hewedy
 * @since 1.0
 */
@SuppressWarnings("rawtypes")
public abstract class Application extends SpringBootServletInitializer{

    @Value("${app.version}")
    private String version;
    @Value("${base.file.directory}")
    private String baseFileDirectory;
    @Value("${http.sslverify:true}")
    private boolean securityCertificationValidationEnabled;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ServiceRegistry serviceRegistry;
    @Autowired
    private SessionTokenRepository tokenRepository;
    @Autowired
    private FileManager fileManager;

    /**
     * The initialize method is responsible for initiating all the components when the application has been started.
     * @see Repository
     * @see com.castlemock.core.basis.model.Service
     */
    @PostConstruct
    protected void initiate(){
        printLogo();
        initializeUnSecureTLS();
        updateBaseFileDirectory(); // This is required to change the base folder name from .fortmocks to .castlemock
        initializeProcessRegistry();
        initializeRepository();
        initializeLegacyRepository();
        initializeServiceFacade();
        initializeTokenRepository();
    }

    protected void printLogo(){
        final StringBuilder logo = new StringBuilder();
        logo.append("  _____          _   _        __  __            _   \n");
        logo.append(" / ____|        | | | |      |  \\/  |          | |   \n");
        logo.append("| |     __ _ ___| |_| | ___  | \\  / | ___   ___| | __\n");
        logo.append("| |    / _` / __| __| |/ _ \\ | |\\/| |/ _ \\ / __| |/ /\n");
        logo.append("| |___| (_| \\__ \\ |_| |  __/ | |  | | (_) | (__|   < \n");
        logo.append(" \\_____\\__,_|___/\\__|_|\\___| |_|  |_|\\___/ \\___|_|\\_\\\n");
        logo.append("=====================================================\n");
        logo.append("Castle Mock (v" + version + ")");
        logo.append("\n");
        System.out.println(logo);
    }

    /**
     * The method provides the functionality to retrieve all the repositories and initialize them
     * @see Repository
     */
    @SuppressWarnings("rawtypes")
    protected void initializeLegacyRepository(){
        final Map<String, Object> repositories = applicationContext.getBeansWithAnnotation(org.springframework.stereotype.Repository.class);
        for(Map.Entry<String, Object> entry : repositories.entrySet()){
            final Object value = entry.getValue();
            if(value instanceof LegacyRepository){
                final LegacyRepository repository = (LegacyRepository) value;
                repository.initialize();
            }
        }
    }

    /**
     * The method provides the functionality to retrieve all the repositories and initialize them
     * @see Repository
     */
    @SuppressWarnings("rawtypes")
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
     * @see com.castlemock.core.basis.model.Service
     */
    @SuppressWarnings("rawtypes")
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
        serviceRegistry.initialize();
    }

    /**
     * The method is only a temporary method used to change the the base file directory name for
     * .fortmocks to .castlemock. This method can later be removed when users have had the change
     * to update the version of Castle mock to a newer version.
     * @since 1.5
     */
    private void updateBaseFileDirectory(){
        String previousBaseFolderDirectory = baseFileDirectory.replace(".castlemock", ".fortmocks");
        fileManager.renameDirectory(previousBaseFolderDirectory, baseFileDirectory);
    }

    /**
     * Conditionally bypass http ssl/tls check for all websites
     * @since 1.36
     */
    private void initializeUnSecureTLS() {
        if (!securityCertificationValidationEnabled) {
            try {
                // Create a trust manager that does not validate certificate chains
                TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return null;
                            }

                            public void checkClientTrusted(X509Certificate[] certs, String authType) {
                            }

                            public void checkServerTrusted(X509Certificate[] certs, String authType) {
                            }
                        }
                };
                // Install the all-trusting trust manager
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

                // Install the all-trusting host verifier
                HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}