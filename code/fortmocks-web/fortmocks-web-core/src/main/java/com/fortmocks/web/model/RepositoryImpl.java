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

package com.fortmocks.web.model;

import com.fortmocks.core.model.Repository;
import com.fortmocks.core.model.Saveable;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The abstract repository provides functionality to interact with the file system in order to manage a specific type.
 * The abstract repository is responsible for retrieving and managing instances for a specific type. All the communication
 * that involves interacting with the file system, such as saving and loading instances, are done through the file repository.
 * @author Karl Dahlgren
 * @since 1.0
 * @param <T> The type that is being managed by the file repository. This file will both be saved and loaded from the
 *              file system.
 * @param <I> The id is used as an identifier for the type.
 * @see Saveable
 */
@org.springframework.stereotype.Repository
public abstract class RepositoryImpl<T extends Saveable<I>, I extends Serializable> implements Repository<T, I> {

    protected Class<T> entityClass;

    protected Class<I> idClass;

    protected Map<I, T> collection = new ConcurrentHashMap<I, T>();

    private static final Logger LOGGER = Logger.getLogger(RepositoryImpl.class);

    /**
     * The default constructor for the AbstractRepositoryImpl class. The constructor will extract class instances of the
     * generic types (TYPE and ID). These instances could later be used to identify the types for when interacting
     * with the file system.
     */
    public RepositoryImpl() {
        final ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        this.idClass = (Class<I>) genericSuperclass.getActualTypeArguments()[1];
    }


    /**
     * The initiate method is responsible for initiating the file repository. This procedure involves loading
     * the types (TYPE) from the file system and store them in the collection.
     * @see #loadFiles()
     * @see #postInitiate()
     */
    @Override
    public void initiate(){
        LOGGER.debug("Start the initiate phase for the type " + entityClass.getSimpleName());
        final Collection<T> loadedFiles = loadFiles();
        for(T type : loadedFiles){
            collection.put(type.getId(), type);
        }
        postInitiate();
    }

    /**
     * The method provides the functionality to find a specific instance that matches the provided id
     * @param id The id that an instance has to match in order to be retrieved
     * @return Returns an instance that matches the provided id
     */
    @Override
    public T findOne(final I id) {
        Preconditions.checkNotNull(id, "The provided id cannot be null");
        LOGGER.debug("Retrieving " + entityClass.getSimpleName() + " with id " + id);
        return collection.get(id);
    }

    /**
     * Retrieves a list of all the instances of the specific type
     * @return A list that contains all the instances of the type that is managed by the operation
     */
    @Override
    public List<T> findAll() {
        LOGGER.debug("Retrieving all instances for " + entityClass.getSimpleName());
        return new LinkedList<>(collection.values());
    }

    /**
     * The save method provides the functionality to save an instance to the file system.
     * @param type The type that will be saved to the file system.
     * @return The type that was saved to the file system. The main reason for it is being returned is because
     *         there could be modifications of the object during the save process. For example, if the type does not
     *         have an identifier, then the method will generate a new identifier for the type.
     */
    @Override
    public T save(final T type) {
        I id = type.getId();

        if(id == null){
            id = generateId();
            type.setId(id);
        }
        checkType(type);
        Writer writer = null;
        final String filename = getFilename(id);
        try {
            JAXBContext context = JAXBContext.newInstance(entityClass);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            writer = new FileWriter(filename);
            marshaller.marshal(type, writer);
            collection.put(id, type);
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
                    LOGGER.error("Unable to close file writer for type " + entityClass.getSimpleName(), e);
                }
            }
        }
        return type;
    }

    /**
     * Delete an instance that match the provided id
     * @param id The instance that matches the provided id will be deleted in the database
     */
    @Override
    public void delete(final I id) {
        Preconditions.checkNotNull(id, "The provided id cannot be null");
        final String filename = getFilename(id);
        LOGGER.debug("Start the deletion of " + entityClass.getSimpleName() + " with id " + id);
        File file = new File(filename);
        if(!file.delete()){
            LOGGER.error("Unable to delete file with id " + id);
            throw new IllegalStateException("Unable to delete file with id " + id);
        }
        collection.remove(id);
        LOGGER.debug("Deletion of " + entityClass.getSimpleName() + " with id " + id + " was successfully completed");
    }

    /**
     * The method provides the functionality to generate a new id to an instance of TYPE
     * @return The new generated id
     */
    protected I generateId(){
        return generateId(collection.keySet(), idClass);
    }

    /**
     * The method provides the functionality to generate a new id to an instance of TYPE
     * @return The new generated id
     */
    protected <ID> ID generateId(final Collection<ID> ids, final Class<?> clazz){
        if(clazz == Long.class){
            Long nextId = 0L;
            for(ID id : ids){
                Long currentId = (Long) id;
                if(currentId >= nextId){
                    nextId = currentId;
                    nextId++;
                }
            }
            return (ID)nextId;
        }
        throw new IllegalStateException("Unable to generate next id");
    }

    private String getFilename(I id){
        final String directory = getFileDirectory();
        final String postfix = getFileExtension();
        return directory + File.separator + id + postfix;
    }

    /**
     * The post initiate method can be used to run functionality for a specific service. The method is called when
     * the method {@link #initiate} has finished successful. The method does not contain any functionality and the
     * whole idea is the it should be overridden by subclasses, but only if certain functionality is required to
     * run after the {@link #initiate} method has completed.
     * @see #initiate
     */
    protected void postInitiate(){
        LOGGER.debug("Post initiate method not implemented for " + entityClass.getSimpleName());
    }

    /**
     * The method returns the directory for the specific file repository. The directory will be used to indicate
     * where files should be saved and loaded from. The method is abstract and every subclass is responsible for
     * overriding the method and provided the directory for their corresponding file type.
     * @return The file directory where the files for the specific file repository could be saved and loaded from.
     */
    protected abstract String getFileDirectory();

    /**
     * The method returns the postfix for the file that the file repository is responsible for managing.
     * The method is abstract and every subclass is responsible for overriding the method and provided the postfix
     * for their corresponding file type.
     * @return The file extension for the file type that the repository is responsible for managing .
     */
    protected abstract String getFileExtension();

    /**
     * The method is responsible for controller that the type that is about the be saved to the file system is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to the file system, but also loaded from the
     * file system upon application startup. The method will throw an exception in case of the type not being acceptable.
     * @param type The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             the file system.
     * @see #save
     */
    protected abstract void checkType(T type);


    /**
     * The method is responsible for loading specific files from the file system. The files are identified by the
     * file name extension and from which directory that are stored in.
     * @return A collection with the loaded types retrieved from the file system
     * @throws IllegalStateException The FileException is thrown in case of creation of the
     *                                                       folder fails (If it does not exist) or if the directory
     *                                                       is not a folder but a file instead.
     *
     * @see #getFileDirectory()
     * @see #getFileExtension()
     */
    protected Collection<T> loadFiles(){
        final String directory = getFileDirectory();
        final String postfix = getFileExtension();
        final Path path = FileSystems.getDefault().getPath(directory);
        final Collection<T> loadedTypes = new ArrayList<T>();

        if(!Files.exists(path)){
            try {
                LOGGER.debug("Creating the following directory: " + path);
                Files.createDirectories(path);
            } catch (IOException e) {
                LOGGER.error("Unable to create the following directory: " + path, e);
                throw new IllegalStateException("Unable to create the following folder: " + directory);
            }
        }
        if(!Files.isDirectory(path)){
            throw new IllegalStateException("The provided path is not a directory: " + path);
        }

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
}
