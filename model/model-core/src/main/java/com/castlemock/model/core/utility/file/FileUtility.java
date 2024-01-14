/*
 * Copyright 2017 Karl Dahlgren
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

package com.castlemock.model.core.utility.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The {@link FileUtility} provides utility methods related to files.
 * @since 1.10
 * @author Karl Dahlgren
 */
public final class FileUtility {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtility.class);

    private FileUtility() {

    }

    /**
     * Parse the incoming <code>file</code> into a String.
     * @param file The {@link File} that will be parsed into a String.
     * @return A {@link File} content.
     */
    public static String getFileContent(final File file){
        final StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();

            while(line != null){
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }

        } catch (Exception e) {
            LOGGER.error("Unable to read the file", e);
            throw new IllegalArgumentException("Unable to parse the file", e);
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.error("Unable to close input stream for the file", e);
                }
            }
        }
        return stringBuilder.toString();
    }


}
