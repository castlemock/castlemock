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

import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Karl Dahlgren
 * @since 1.4
 */
@Component
public class FileRepositorySupport {

    private static final Logger LOGGER = Logger.getLogger(FileRepositorySupport.class);

    public String read(File file){
        Preconditions.checkNotNull(file, "The file cannot be null");
        try {
            final byte[] raw = Files.readAllBytes(file.toPath());
            return new String(raw);
        } catch (IOException e) {
            LOGGER.error("Unable to find the following file: " + file.getName(), e);
            throw new IllegalStateException("Unable to find the following file: " + file.getName());
        }
    }

    public String load(String directory, String filename){
        final Path path = FileSystems.getDefault().getPath(directory);
        this.createDirectory(path);

        final File file = new File(directory, filename);
        try {
            final byte[] raw = Files.readAllBytes(file.toPath());
            return new String(raw);
        } catch (IOException e) {
            LOGGER.error("Unable to find the following file: " + filename, e);
            throw new IllegalStateException("Unable to find the following file: " + filename);
        }
    }

    public void save(String directory, String filename, String data){
        final Path path = FileSystems.getDefault().getPath(directory);
        this.createDirectory(path);

        final File file = new File(directory, filename);

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(data);
        } catch (Exception e) {
            LOGGER.error("Unable to save the following file: " + filename, e);
            throw new IllegalStateException("Unable to save the following file: " + filename);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                LOGGER.error("Unable to close the writer", e);
            }
        }
    }


    public <T> Collection<T> load(Class<T> entityClass, String directory, String postfix){
        final Collection<T> loadedTypes = new ArrayList<T>();
        final Path path = FileSystems.getDefault().getPath(directory);
        this.createDirectory(path);

        final File folder = new File(directory);
        try {
            LOGGER.debug("Start loading files for the following type: " + entityClass.getSimpleName());
            for (final File file : folder.listFiles()) {
                if (file.isFile() && file.getName().endsWith(postfix)) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(entityClass);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    T type = (T) jaxbUnmarshaller.unmarshal(file);
                    loadedTypes.add(type);
                    LOGGER.debug("\tLoaded " + file.getName());
                }
            }
        } catch (JAXBException e) {
            LOGGER.error("Unable to parse files for type " + entityClass.getSimpleName(), e);
        }
        return loadedTypes;
    }

    public <T> void save(T type, String filename){
        Writer writer = null;
        try {
            JAXBContext context = JAXBContext.newInstance(type.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            writer = new FileWriter(filename);
            marshaller.marshal(type, writer);
        } catch (JAXBException e) {
            LOGGER.error("Unable to parse file: " + filename, e);
            throw new IllegalStateException("Unable to parse the following file: " + filename);
        } catch (IOException e) {
            LOGGER.error("Unable to read file: " + filename, e);
            throw new IllegalStateException("Unable to read the following file: " + filename);
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

    public void moveAllFiles(String oldDirectory, String newDirectory, String postfix){
        final Path oldPath = FileSystems.getDefault().getPath(oldDirectory);
        final Path newPath = FileSystems.getDefault().getPath(newDirectory);

        this.createDirectory(oldPath);
        this.createDirectory(newPath);

        final File oldFolder = new File(oldDirectory);
        final File newFolder = new File(newDirectory);
        for (final File oldFile : oldFolder.listFiles()) {
            if (oldFile.isFile() && oldFile.getName().endsWith(postfix)) {
                File newFile = new File(newFolder, oldFile.getName());
                Path fromPath = oldFile.toPath();
                Path toPath = newFile.toPath();
                try {
                    Files.move(fromPath, toPath);
                } catch (IOException e) {
                    LOGGER.debug("Unable to move the following file: " + fromPath);
                }
            }
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
