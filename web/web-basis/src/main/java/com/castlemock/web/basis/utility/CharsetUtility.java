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

package com.castlemock.web.basis.utility;

import com.castlemock.core.basis.model.http.domain.HttpHeader;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@link CharsetUtility} is a utility class that provides
 * functionality related to charset.
 * @author Karl Dahlgren
 * @since 1.18
 */
public class CharsetUtility {

    private static final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*\"?([^\\s;\"]*)");
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final Logger LOGGER = LoggerFactory.getLogger(CharsetUtility.class);

    /**
     * Extract the charset from a list of provided {@link HttpHeader}.
     * The method will only take the Content-Type header into consideration.
     * @param headers The list of headers that the charset will be extracted from.
     * @return The extracted charset. The default charset ({@link CharsetUtility#DEFAULT_CHARSET})
     * will be returned if a charset couldn't be extracted.
     * @see CharsetUtility#parseContentType(String)
     */
    public static String parseHttpHeaders(final List<HttpHeader> headers){
        if(headers == null){
            return DEFAULT_CHARSET;
        }

        for(HttpHeader header : headers){
            if(CONTENT_TYPE.equalsIgnoreCase(header.getName())){
                return parseContentType(header.getValue());
            }
        }
        return DEFAULT_CHARSET;
    }


    /**
     * Extract the charset from a Content-Type string.
     * @param contentType The Content-Type value that might contain a charset.
     * @return The extracted charset. The default charset ({@link CharsetUtility#DEFAULT_CHARSET})
     * will be returned if a charset couldn't be extracted.
     */
    public static String parseContentType(final String contentType){
        try {
            if (contentType == null)
                return DEFAULT_CHARSET;

            Matcher matcher = charsetPattern.matcher(contentType);
            if (matcher.find()) {
                return matcher.group(1).trim().toUpperCase();
            }
        } catch (Exception e){
            LOGGER.info("Unable to extract the charset from the following Content-Type: " + contentType, e);
        }
        return DEFAULT_CHARSET;
    }


}
