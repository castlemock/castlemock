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

package com.castlemock.service.core.utility;

import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toMap;

/**
 * @author Karl Dahlgren
 * @since 1.8
 */
public class UrlUtility {

    private final static Pattern VARIABLE_PATTERN = Pattern.compile("\\{(.+?)\\}");

    public static int getPatternMatchScore(final String pattern, final String path){
        try {
            return getMatchInfo(pattern, path).getUriVariables().size();
        } catch (Exception exception) {
            return -1;
        }
    }

    public static boolean isPatternMatch(final String pattern, final String path){
        try {
            return getMatchInfo(pattern, path) != null;
        } catch (Exception exception) {
            return Boolean.FALSE;
        }
    }

    public static Map<String, Set<String>> getPathParameters(final String pattern, final String path){
        try {
            final PathPattern.PathMatchInfo matchInfo = getMatchInfo(pattern, path);
            return matchInfo.getUriVariables()
                    .entrySet()
                    .stream()
                    .collect(toMap(Map.Entry::getKey, entry -> Set.of(entry.getValue())));
        } catch (Exception exception) {
            return Collections.emptyMap();
        }
    }

    public static Map<String, Set<String>> getQueryStringParameters(final String uri,
                                                               final Map<String, Set<String>> httpParameters) {
        final HashMap<String, Set<String>> output = new HashMap<>();
        if(uri.indexOf('?') > 0){
            final String queryString = uri.split("\\?")[1];
            final String[] queries = queryString.split("&");

            for(String query : queries){
                final String[] queryParts = query.split("=");

                if(queryParts.length != 2){
                    continue;
                }

                final String queryName = queryParts[0];
                final String queryValue = queryParts[1];

                if(queryValue.startsWith("{") && queryValue.endsWith("}")){
                    final Set<String> values = httpParameters.get(queryName);
                    if(values != null){
                        if(!output.containsKey(queryName)) {
                            output.put(queryName, new HashSet<>());
                        }
                        values.forEach(value -> output.get(queryName).add(value));
                    }
                }
            }
        }

        return output;
    }

    public static Set<String> getPathParameters(final String uri){
        final Set<String> parameters = new HashSet<>();
        final Matcher matcher = VARIABLE_PATTERN.matcher(uri);
        while (matcher.find()){
            String paramName = matcher.group(1);
            if(paramName != null){
                parameters.add(paramName);
            }
        }
        return parameters;
    }

    private static PathPattern.PathMatchInfo getMatchInfo(final String pattern, final String path){
        final String basePattern = getBaseUriPattern(pattern);
        final PathPatternParser parser = new PathPatternParser();
        final PathPattern pathPattern = parser.parse(basePattern);
        final PathContainer pathContainer = PathContainer.parsePath(path);
        return pathPattern.matchAndExtract(pathContainer);
    }

    private static String getBaseUriPattern(final String pattern){
        final int index = pattern.indexOf("?");
        if(index == -1){
            return pattern;
        }

        return pattern.substring(0, index);
    }

    @SuppressWarnings("deprecation")
    public static String getPath(final String originalPath,
                                 final String newPath){

        try {
            final URL url = new URL(originalPath);
            return new URL(url, newPath).toString();
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
