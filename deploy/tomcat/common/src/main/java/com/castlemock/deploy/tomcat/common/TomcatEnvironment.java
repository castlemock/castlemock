/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.deploy.tomcat.common;

import com.castlemock.core.basis.Environment;
import org.apache.catalina.util.ServerInfo;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Component
public class TomcatEnvironment implements Environment {

    private static final Logger LOGGER = LoggerFactory.getLogger(TomcatEnvironment.class);


    @Override
    public boolean copy(final InputStream inputStream,
                        final OutputStream outputStream) {
        try {
            IOUtils.copy(inputStream, outputStream);
            return true;
        } catch (IOException e) {
            LOGGER.error("Unable to copy data", e);
            return false;
        }
    }

    @Override
    public String getServerInfo() {
        return ServerInfo.getServerInfo();
    }

    @Override
    public String getServerBuilt() {
        return ServerInfo.getServerBuilt();
    }

    @Override
    public String getServerNumber() {
        return ServerInfo.getServerNumber();
    }
}
