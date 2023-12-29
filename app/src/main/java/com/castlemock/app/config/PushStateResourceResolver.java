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

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PushStateResourceResolver implements ResourceResolver {

    private static final Resource INDEX = new ClassPathResource("/public/index.html");
    private static final List<String> HANDLED_EXTENSIONS = Arrays.asList("html", "js", "json", "csv", "css", "png", "svg", "eot", "ttf", "woff", "appcache", "jpg", "jpeg", "gif", "ico");
    private static final List<String> IGNORED_PATHS = List.of("api");

    @Override
    public Resource resolveResource(final HttpServletRequest request, final String requestPath, List<? extends Resource> locations,
                                    final ResourceResolverChain chain) {
        return resolve(requestPath, locations)
                .orElse(null);
    }

    @Override
    public String resolveUrlPath(final String resourcePath, List<? extends Resource> locations,
                                 final ResourceResolverChain chain) {
        return resolve(resourcePath, locations)
                .map(resolvedResource -> {
                    try {
                        return resolvedResource.getURL().toString();
                    } catch (IOException e) {
                        return resolvedResource.getFilename();
                    }
                })
                .orElse(null);

    }

    private Optional<Resource> resolve(String requestPath, List<? extends Resource> locations) {
        if(requestPath.startsWith("web/")){
            requestPath = requestPath.replace("web/", "");
        }

        final String fixedRequestPath = requestPath;

        if (isIgnored(requestPath)) {
            return Optional.empty();
        }
        if (isHandled(requestPath)) {
            return locations.stream()
                    .map(loc -> createRelative(loc, fixedRequestPath))
                    .flatMap(Optional::stream)
                    .filter(Resource::exists)
                    .findFirst();
        }
        return Optional.of(INDEX);
    }

    private Optional<Resource> createRelative(final Resource resource, final String relativePath) {
        try {
            final Resource relativtResource = resource.createRelative(relativePath);
            return Optional.of(relativtResource);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private boolean isIgnored(final String path) {
        return IGNORED_PATHS.contains(path);
    }

    private boolean isHandled(final String path) {
        final String extension = StringUtils.getFilenameExtension(path);
        return HANDLED_EXTENSIONS.stream().anyMatch(ext -> ext.equals(extension));
    }
}