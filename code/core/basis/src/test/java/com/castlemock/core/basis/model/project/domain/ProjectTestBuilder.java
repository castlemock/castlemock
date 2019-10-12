package com.castlemock.core.basis.model.project.domain;

import com.castlemock.core.basis.model.TypeIdentifier;

import java.util.Date;

public final class ProjectTestBuilder {

    private String id;
    private String name;
    private Date updated;
    private Date created;
    private String description;
    private TypeIdentifier typeIdentifier;

    private ProjectTestBuilder(){
        this.id = "SoapProject";
        this.name = "Project name";
        this.description = "Project description";
        this.updated = new Date();
        this.created = new Date();
    }

    public static ProjectTestBuilder builder(){
        return new ProjectTestBuilder();
    }

    public ProjectTestBuilder id(final String id){
        this.id = id;
        return this;
    }

    public ProjectTestBuilder name(final String name){
        this.name = name;
        return this;
    }

    public ProjectTestBuilder updated(final Date updated){
        this.updated = updated;
        return this;
    }

    public ProjectTestBuilder created(final Date created){
        this.created = created;
        return this;
    }

    public ProjectTestBuilder description(final String description){
        this.description = description;
        return this;
    }

    public ProjectTestBuilder typeIdentifier(final TypeIdentifier typeIdentifier){
        this.typeIdentifier = typeIdentifier;
        return this;
    }

    public Project build(){
        final Project project = new Project();
        project.setCreated(created);
        project.setDescription(description);
        project.setId(id);
        project.setName(name);
        project.setTypeIdentifier(typeIdentifier);
        project.setUpdated(updated);

        return project;
    }
}
