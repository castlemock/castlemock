/*
 * Copyright 2016 Karl Dahlgren
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

package com.castlemock.repository.core.file;

import com.castlemock.repository.core.file.http.model.HttpHeaderFile;
import com.castlemock.repository.core.file.http.model.HttpParameterFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author Karl Dahlgren
 * @since 1.4
 */
@Component
public class FileRepositorySupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileRepositorySupport.class);

    public String load(String directory, String filename){
        final Path path = FileSystems.getDefault().getPath(directory);
        this.createDirectory(path);

        final File file = new File(directory, filename);
        try {
            final byte[] raw = Files.readAllBytes(file.toPath());
            return new String(raw);
        } catch (IOException e) {
            LOGGER.error("Unable to find the following file: " + filename, e);
            throw new IllegalStateException("Unable to find the following file: " + filename, e);
        }
    }

    public void save(final String directory, final String filename, final String data){
        final Path path = FileSystems.getDefault().getPath(directory);
        this.createDirectory(path);

        final File file = new File(directory, filename);

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8));
            writer.write(data);
        } catch (Exception e) {
            LOGGER.error("Unable to save the following file: " + filename, e);
            throw new IllegalStateException("Unable to save the following file: " + filename, e);
        } finally {
            try {
                if(writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                LOGGER.error("Unable to close the writer", e);
            }
        }
    }

    public <T> Collection<T> load(final Class<T> entityClass, final String directory, final String postfix){
        final Path path = FileSystems.getDefault().getPath(directory);
        this.createDirectory(path);

        LOGGER.debug("Start loading files for the following type: " + entityClass.getSimpleName());
        return Optional.of(new File(directory))
                .map(File::listFiles)
                .map(Stream::of)
                .map(files -> files
                        .filter(File::isFile)
                        .filter(file -> file.getName().endsWith(postfix))
                        .map(file -> load(file, entityClass))
                        .flatMap(Optional::stream)
                        .collect(toList()))
                .orElseGet(Collections::emptyList);
    }

    @SuppressWarnings("unchecked")
    private <T> Optional<T> load(final File file, final Class<T> entityClass){
        try(final InputStream inputStream= new FileInputStream(file)) {
            try(final Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)){
                final JAXBContext jaxbContext = JAXBContext.newInstance(entityClass, HttpHeaderFile.class, HttpParameterFile.class);
                final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                LOGGER.debug("\tLoaded " + file.getName());
                return Optional.ofNullable((T) jaxbUnmarshaller.unmarshal(reader));
            }
        } catch (JAXBException | IOException e) {
            LOGGER.error("Unable to parse the following file: " + file.getAbsolutePath(), e);
        }
        return Optional.empty();
    }

    public <T> void save(T type, String filename){
        Writer writer = null;
        try {
            final JAXBContext context = JAXBContext.newInstance(type.getClass());
            final Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            writer = new FileWriter(filename, StandardCharsets.UTF_8);
            marshaller.marshal(type, writer);
        } catch (JAXBException e) {
            LOGGER.error("Unable to parse file: " + filename, e);
            throw new IllegalStateException("Unable to parse the following file: " + filename, e);
        } catch (IOException e) {
            LOGGER.error("Unable to read file: " + filename, e);
            throw new IllegalStateException("Unable to read the following file: " + filename, e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    LOGGER.error("Unable to close file writer for type " + type.getClass().getSimpleName(), e);
                }
            }
        }
    }

    public void delete(String filename){
        File file = new File(filename);
        if(!file.delete()){
            LOGGER.error("Unable to delete the following file: " + filename);
            throw new IllegalStateException("Unable to delete the following file: " + filename);
        }
    }

    public void delete(String directory, String filename){
        File file = new File(directory, filename);
        if(!file.delete()){
            LOGGER.error("Unable to delete the following file: " + filename);
            throw new IllegalStateException("Unable to delete the following file: " + filename);
        }
    }

    private void createDirectory(Path path){
        if(!Files.exists(path)){
            try {
                LOGGER.debug("Creating the following directory: " + path);
                Files.createDirectories(path);
            } catch (IOException e) {
                LOGGER.error("Unable to create the following directory: " + path, e);
                throw new IllegalStateException("Unable to create the following folder: " + path);
            }
        }
        if(!Files.isDirectory(path)){
            throw new IllegalStateException("The provided path is not a directory: " + path);
        }
    }

}
