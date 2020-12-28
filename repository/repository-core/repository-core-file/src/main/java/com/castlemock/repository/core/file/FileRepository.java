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

package com.castlemock.repository.core.file;

import com.castlemock.model.core.Saveable;
import com.castlemock.repository.Repository;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

import static java.util.stream.Collectors.toList;

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
public abstract class FileRepository<T extends Saveable<I>, D, I extends Serializable> implements Repository<D, I> {

    @Autowired
    protected DozerBeanMapper mapper;
    @Autowired
    protected FileRepositorySupport fileRepositorySupport;

    private Class<T> entityClass;

    private Class<D> dtoClass;

    protected Map<I, T> collection = new ConcurrentHashMap<I, T>();

    private Map<I, Semaphore> writeLocks = new ConcurrentHashMap<>();

    private JAXBContext jaxbContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(FileRepository.class);

    /**
     * The default constructor for the AbstractRepositoryImpl class. The constructor will extract class instances of the
     * generic types (TYPE and ID). These instances could later be used to identify the types for when interacting
     * with the file system.
     */
    @SuppressWarnings("unchecked")
    public FileRepository() {
        final ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        this.dtoClass = (Class<D>) genericSuperclass.getActualTypeArguments()[1];
    }


    /**
     * The initialize method is responsible for initiating the file repository. This procedure involves loading
     * the types (TYPE) from the file system and store them in the collection.
     * @see #loadFiles()
     * @see #postInitiate()
     */
    @Override
    public void initialize(){
        LOGGER.debug("Creating a new JAXB context for the following class: " + entityClass.getSimpleName());
        try {
            this.jaxbContext = JAXBContext.newInstance(entityClass);
        } catch (JAXBException e) {
            LOGGER.error("Unable to create a new JAXB instance" , e);
            throw new IllegalStateException("Unable to create a new JAXB instance" , e);
        }
        LOGGER.debug("Start the initialize phase for the type " + entityClass.getSimpleName());
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
    public D findOne(final I id) {
        Preconditions.checkNotNull(id, "The provided id cannot be null");
        LOGGER.debug("Retrieving " + entityClass.getSimpleName() + " with id " + id);
        T type = collection.get(id);
        return type != null ? mapper.map(type, dtoClass) : null;
    }

    /**
     * Retrieves a list of all the instances of the specific type
     * @return A list that contains all the instances of the type that is managed by the operation
     */
    @Override
    public List<D> findAll() {
        LOGGER.debug("Retrieving all instances for " + entityClass.getSimpleName());
        return toDtoList(collection.values(), dtoClass);
    }

    /**
     * The save method provides the functionality to save an instance to the file system.
     * @param dto The type that will be saved to the file system.
     * @return The type that was saved to the file system. The main reason for it is being returned is because
     *         there could be modifications of the object during the save process. For example, if the type does not
     *         have an identifier, then the method will generate a new identifier for the type.
     */
    @Override
    public D save(final D dto) {
        T type = mapper.map(dto, entityClass);
        return save(type);
    }

    /**
     * The save method provides the functionality to save an instance to the file system. The instance
     * should already exist on the file system and should be identified with the provided <code>id</code>.
     * @param id The id of the type that will be re-saved to the file system.
     * @return The type that was saved to the file system. The main reason for it is being returned is because
     *         there could be modifications of the object during the save process. For example, if the type does not
     *         have an identifier, then the method will generate a new identifier for the type.
     */
    protected D save(final I id){
        final T type = collection.get(id);
        return save(type);
    }

    /**
     * The save method provides the functionality to save an instance to the file system.
     * @param type The type that will be saved to the file system.
     * @return The type that was saved to the file system. The main reason for it is being returned is because
     *         there could be modifications of the object during the save process. For example, if the type does not
     *         have an identifier, then the method will generate a new identifier for the type.
     */
    @SuppressWarnings("unchecked")
    protected D save(final T type){
        I id = type.getId();

        if(id == null){
            do {
                id = (I)generateId();
            } while (exists(id));
            type.setId(id);
        }
        checkType(type);
        final String filename = getFilename(id);

        final Semaphore writeLock = getWriteLock(id);

        try {
            writeLock.acquire();
            fileRepositorySupport.save(type, filename);
            collection.put(id, type);
            return mapper.map(type, dtoClass);
        } catch (InterruptedException e) {
            throw new IllegalStateException("Unable to acquire the write lock", e);
        } finally {
            writeLock.release();
        }
    }

    /**
     * Updates an instance that matches the provided id.
     *
     * @param id   The id of the instance that will be updated.
     * @param type The updated version that will replace the old one.
     * @return A copy of the replaced value.
     * @since 1.20
     */
    @Override
    public D update(I id, D type) {
        return save(type);
    }

    /**
     * The method is responsible for retrieving a new write lock.
     * A new write lock will be created if a write lock already doesn't exist and is associated
     * with the provided <code>id</code>.
     * @param id The id is used as an identifier for the write lock
     * @return Either a new write lock or an already existing write lock associated with the <code>id</code>.
     * @since 1.5
     */
    private synchronized Semaphore getWriteLock(final I id){
        Semaphore writeLock = writeLocks.get(id);
        if(writeLock == null){
            // Create a new write lock and only one is allowed to write to the file at a time.
            writeLock = new Semaphore(1);
            writeLocks.put(id, writeLock);
        }
        return writeLock;
    }

    /**
     * Count all the stored entities for the repository
     * @return The count of entities
     */
    @Override
    public Integer count(){
        return collection.size();
    }

    /**
     * Delete an instance that match the provided id
     * @param id The instance that matches the provided id will be deleted in the database
     */
    @Override
    public D delete(final I id) {
        Preconditions.checkNotNull(id, "The provided id cannot be null");
        final String filename = getFilename(id);
        LOGGER.debug("Start the deletion of " + entityClass.getSimpleName() + " with id " + id);
        Semaphore writeLock = getWriteLock(id);
        try {
            writeLock.acquire();
            fileRepositorySupport.delete(filename);
            T type = collection.remove(id);
            LOGGER.debug("Deletion of " + entityClass.getSimpleName() + " with id " + id + " was successfully completed");
            return mapper.map(type, dtoClass);
        } catch (InterruptedException e) {
            throw new IllegalStateException("Unable to accuire the write lock", e);
        } finally {
            writeLock.release();
        }
    }


    /**
     * The method provides the functionality to export an entity and convert it to a String
     * @param id The id of the entityy that will be converted and exported
     * @return The entity with the provided id as a String
     */
    @Override
    public String exportOne(final I id){
        try {
            final T type = collection.get(id);
            final Marshaller marshaller = this.jaxbContext.createMarshaller();
            final StringWriter writer = new StringWriter();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(type, writer);
            return writer.toString();
        }
        catch (JAXBException e) {
            throw new IllegalStateException("Unable to export type", e);
        }
    }

    /**
     * The method provides the functionality to import a enity as a String
     * @param raw The entity as a String
     */
    @Override
    @SuppressWarnings("unchecked")
    public D importOne(final String raw){

        try {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream (raw.getBytes());

            final Unmarshaller jaxbUnmarshaller = this.jaxbContext.createUnmarshaller();
            final T type = (T) jaxbUnmarshaller.unmarshal(byteArrayInputStream);

            // Check if a type already have the same id.
            // If so, throw a new exception.
            D existing = findOne(type.getId());
            if(existing != null){
                throw new IllegalArgumentException("A type with the following ID already exists: " + type.getId());
            }

            D dto = mapper.map(type, dtoClass);
            dto = save(dto);
            return dto;
        } catch (JAXBException e) {
            throw new IllegalStateException("Unable to import type", e);
        }
    }

    /**
     * Checks if the provided <code>id</code> already exists.
     * @param id The id that will be checked if it is already being used.
     * @return <code>true</code> if the id exists. <code>false</code> otherwise.
     * @since 1.20
     */
    public boolean exists(I id){
        return this.collection.containsKey(id);
    }

    /**
     * The method creates a new file name based on the provided <code>id</code>.
     * @param id The id will be the foundation of the generated file name.
     * @return A file name based on the provided id.
     */
    private String getFilename(I id){
        final String directory = getFileDirectory();
        final String postfix = getFileExtension();
        return directory + File.separator + id + postfix;
    }

    /**
     * The post initialize method can be used to run functionality for a specific service. The method is called when
     * the method {@link #initialize} has finished successful. The method does not contain any functionality and the
     * whole idea is the it should be overridden by subclasses, but only if certain functionality is required to
     * run after the {@link #initialize} method has completed.
     * @see #initialize
     */
    protected void postInitiate(){
        LOGGER.debug("Post initialize method not implemented for " + entityClass.getSimpleName());
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
    private Collection<T> loadFiles(){
        final String directory = getFileDirectory();
        final String postfix = getFileExtension();
        return fileRepositorySupport.load(entityClass, directory, postfix);
    }

    /**
     * The method provides the functionality to convert a Collection of TYPE instances into a list of DTO instances
     * @param types The collection that will be converted into a list of DTO
     * @param clazz CLass of the DTO type (D)
     * @return The provided collection but converted into the DTO class
     */
    protected List<D> toDtoList(final Collection<T> types, final Class<D> clazz) {
        return types.stream()
                .map(type -> mapper.map(type, clazz))
                .collect(toList());
    }

    /**
     * The method is responsible for generating new ID for the entity. The
     * ID will be six character and contain both characters and numbers.
     * @return A generated ID
     */
    protected String generateId(){
        return RandomStringUtils.random(6, true, true);
    }

    @XmlRootElement(name = "httpHeader")
    public static class HttpHeaderFile {

        @Mapping("name")
        private String name;
        @Mapping("value")
        private String value;

        @XmlElement
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlElement
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    @XmlRootElement(name = "httpParameter")
    public static class HttpParameterFile {

        @Mapping("name")
        private String name;
        @Mapping("value")
        private String value;

        @XmlElement
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlElement
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
