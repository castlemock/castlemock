/*
 Copyright 2020 Karl Dahlgren

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

import React, {PureComponent} from 'react';
import BootstrapTable from 'react-bootstrap-table-next';
import ToolkitProvider, {Search} from 'react-bootstrap-table2-toolkit';
import PaginationFactory from "react-bootstrap-table2-paginator";
import axios from "axios";
import {Link} from "react-router-dom";
import Badge from 'react-bootstrap/Badge'

const { SearchBar } = Search;

const SELECT = true;
const DESELECT = false;

class ProjectOverview extends PureComponent {

    constructor(props) {
        super(props);
        this.onRowSelect = this.onRowSelect.bind(this);
        this.onRowSelectAll = this.onRowSelectAll.bind(this);
        this.nameFormat = this.nameFormat.bind(this);
        this.typeFormat = this.typeFormat.bind(this);
        this.setNewProjectName = this.setNewProjectName.bind(this);
        this.setNewProjectDescription = this.setNewProjectDescription.bind(this);
        this.setNewProjectType = this.setNewProjectType.bind(this);
        this.onCreateProjectClick = this.onCreateProjectClick.bind(this);
        this.onImportProjectClick = this.onImportProjectClick.bind(this);
        this.onExportProjectsClick = this.onExportProjectsClick.bind(this);
        this.onDeleteProjectsClick = this.onDeleteProjectsClick.bind(this);

        this.columns = [{
            dataField: 'id',
            text: 'id',
            hidden: true
        }, {
            dataField: 'typeIdentifier.type',
            width: 20,
            maxWidth: 20,
            text: 'Type',
            sort: true,
            formatter: this.typeFormat
        }, {
            dataField: 'name',
            text: 'Name',
            sort: true,
            formatter: this.nameFormat
        }, {
            dataField: 'description',
            text: 'Description',
            sort: true
        }];

        this.deleteColumns = [{
            dataField: 'id',
            text: 'id',
            hidden: true
        }, {
            dataField: 'typeIdentifier.type',
            width: 20,
            maxWidth: 20,
            text: 'Type',
            sort: true,
            formatter: this.typeFormat
        }, {
            dataField: 'name',
            text: 'Name',
            sort: true
        }, {
            dataField: 'description',
            text: 'Description',
            sort: true
        }];

        this.selectRow = {
            mode: 'checkbox',
            onSelect: this.onRowSelect,
            onSelectAll: this.onRowSelectAll
        };

        this.defaultSort = [{
            dataField: 'name',
            order: 'asc'
        }];

        this.state = {
            projects: [],
            selectedProjects: [],
            unauthorized: false,
            newProjectName: "",
            newProjectDescription: "",
            newProjectType: "REST",
            deleteProjectsDisabled: true,
            exportProjectsDisabled: true
        };

        this.getProjects()
    }

    setNewProjectName(newProjectName) {
        this.setState({ newProjectName: newProjectName });
    }

    setNewProjectDescription(newProjectDescription) {
        this.setState({ newProjectDescription: newProjectDescription });
    }

    setNewProjectType(newProjectType) {
        this.setState({ newProjectType: newProjectType });
    }

    nameFormat(cell, row) {
        if(cell == null){
            return;
        }

        return (
            <div className="table-link">
                <Link to={"/beta/web/" + row.typeIdentifier.typeUrl + "/project/" + row.id}>{cell}</Link>
            </div>
        )
    }

    typeFormat(cell, row) {
        if(cell == null){
            return;
        }

        if(cell === "SOAP") {
            return (
                <div>
                    <Badge variant="primary"><Link className="project-type-badge-link" to={"/beta/web/" + row.typeIdentifier.typeUrl + "/project/" + row.id}>{cell}</Link></Badge>
                </div>
            )
        } else if(cell === "REST") {
            return (
                <div>
                    <Badge variant="success"><Link className="project-type-badge-link" to={"/beta/web/" + row.typeIdentifier.typeUrl + "/project/" + row.id}>{cell}</Link></Badge>
                </div>
            )
        }
    }

    onCreateProjectClick() {
        axios
            .post("/api/rest/core/project", {
                name: this.state.newProjectName,
                description: this.state.newProjectName,
                projectType: this.state.newProjectType
            })
            .then(response => {
                this.props.history.push("/beta/web/" + response.data.typeIdentifier.typeUrl + "/project/" + response.data.id)
            })
            .catch(error => {
                this.props.validateErrorResponse(error);
            });
    }

    onImportProjectClick() {

    }

    onExportProjectsClick() {

    }

    onDeleteProjectsClick() {

    }

    onRowSelect(value, mode) {
        let projects = this.state.selectedProjects.slice();
        let project = {
            id: value.id,
            name: value.name,
            description: value.description,
            typeIdentifier: {
                type: value.typeIdentifier.type
            }
        };
        if(mode === SELECT){
            projects.push(project);
        } else if(mode === DESELECT){
            let index = projects.indexOf(project);
            projects.splice(index, 1);
        }
        this.setState({
            selectedProjects: projects,
            exportProjectsDisabled: projects.length === 0,
            deleteProjectsDisabled: projects.length === 0
        });
    }

    onRowSelectAll(mode) {
        if(mode === SELECT){
            let projects = [];
            this.state.projects.forEach(value => {
                let project = {
                    id: value.id,
                    name: value.name,
                    description: value.description,
                    typeIdentifier: {
                        type: value.typeIdentifier.type
                    }
                };
                projects.push(project);
            });
            this.setState({
                selectedProjects: projects,
                deleteProjectsDisabled: false,
                exportProjectsDisabled: false
            });
        } else if(mode === DESELECT){
            this.setState({
                selectedProjects: [],
                deleteProjectsDisabled: true,
                exportProjectsDisabled: true
            });
        }
    }

    getProjects() {
        axios
            .get("/api/rest/core/project")
            .then(response => {
                this.setState({
                    projects: response.data,
                });
            })
            .catch(error => {
                this.props.validateErrorResponse(error);
            });
    }

    render() {
        return (
            <div>
                <section>
                    <div className="navigation">
                    </div>
                    <div className="content-top">
                        <div className="title">
                            <h1>Overview</h1>
                        </div>
                        <div className="menu">
                            <button className="btn btn-success demo-button-disabled menu-button" data-toggle="modal" data-target="#newProjectModal"><i className="fas fa-plus-circle"/> <span>New project</span></button>
                            <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal" data-target="#importProjectModal"><i className="fas fa-cloud-upload-alt"/> <span>Import project</span></button>
                        </div>
                    </div>
                    <div className="panel panel-primary table-panel">
                        <div className="table-result">
                            <ToolkitProvider bootstrap4
                                             columns={ this.columns}
                                             data={ this.state.projects }
                                             keyField="id"
                                             search>
                                {
                                    (props) => (
                                        <div>
                                            <div>
                                                <SearchBar { ...props.searchProps } className={"table-filter-field"} />
                                            </div>
                                            <div>
                                                <BootstrapTable { ...props.baseProps } bootstrap4 data={this.state.projects} columns={this.columns}
                                                                defaultSorted={ this.defaultSort } keyField='id' hover
                                                                selectRow={ this.selectRow }
                                                                striped
                                                                noDataIndication="Click on 'New project' button to create a new project"
                                                                pagination={ PaginationFactory(PaginationFactory()) }/>
                                            </div>
                                            </div>
                                    )}
                            </ToolkitProvider>
                        </div>
                        <div className="table-result">
                            <div className="panel-buttons">
                                <button className="btn btn-primary panel-button demo-button-disabled panel-button"  disabled={this.state.exportProjectsDisabled}
                                        data-toggle="modal" data-target="#exportProjectsModal"><i className="fas fa-cloud-download-alt"/> <span>Export projects</span></button>
                                <button className="btn btn-danger demo-button-disabled panel-button" disabled={this.state.deleteProjectsDisabled}
                                        data-toggle="modal" data-target="#deleteProjectsModal"><i className="fas fa-trash"/> <span>Delete projects</span></button>
                            </div>
                        </div>
                    </div>
                 </section>

                <div className="modal fade" id="newProjectModal" tabIndex="-1" role="dialog"
                     aria-labelledby="newProjectModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="newProjectModalLabel">New project</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <form>
                                    <div className="form-group row">
                                        <label htmlFor="newProjectName" className="col-sm-2 col-form-label">Name</label>
                                        <div className="col-sm-10">
                                            <input className="form-control" type="text" name="newProjectName" id="newProjectName" onChange={event => this.setNewProjectName(event.target.value)}/>
                                        </div>
                                    </div>
                                    <div className="form-group row">
                                        <label htmlFor="newProjectDescription" className="col-sm-2 col-form-label">Description</label>
                                        <div className="col-sm-10">
                                            <textarea className="form-control" name="newProjectDescription" id="newProjectDescription" onChange={event => this.setNewProjectDescription(event.target.value)}/>
                                        </div>
                                    </div>
                                    <div className="form-group row">
                                        <label htmlFor="inputState" className="col-sm-2 col-form-label">Type</label>
                                        <div className="col-sm-10">
                                            <select id="inputState" className="form-control" onChange={event => this.setNewProjectType(event.target.value)} defaultValue={"REST"}>
                                                <option>REST</option>
                                                <option>SOAP</option>
                                            </select>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-success" data-dismiss="modal" onClick={this.onCreateProjectClick}>Create</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="modal fade" id="importProjectModal" tabIndex="-1" role="dialog"
                     aria-labelledby="importProjectModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="importProjectModalLabel">Import project</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">

                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-success" data-dismiss="modal" onClick={this.onImportProjectClick}>Import</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="modal fade" id="deleteProjectsModal" tabIndex="-1" role="dialog"
                     aria-labelledby="deleteProjectsModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="deleteProjectsModalLabel">Export project</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <p>Do you want delete the following projects?</p>
                                <div className="table-result">
                                    <ToolkitProvider bootstrap4
                                                     columns={ this.deleteColumns}
                                                     data={ this.state.selectedProjects }
                                                     keyField="id">
                                        {
                                            (props) => (
                                                <div>
                                                    <BootstrapTable { ...props.baseProps } bootstrap4 data={this.state.selectedProjects} columns={this.deleteColumns}
                                                                    defaultSorted={ this.defaultSort } keyField='id' hover
                                                                    striped
                                                                    pagination={ PaginationFactory(PaginationFactory()) }/>
                                                </div>
                                            )}
                                    </ToolkitProvider>
                                </div>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-danger"data-dismiss="modal" onClick={this.onDeleteProjectsClick}>Delete</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="modal fade" id="exportProjectsModal" tabIndex="-1" role="dialog"
                     aria-labelledby="exportProjectsModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="exportProjectsModalLabel">Export projects</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <p>Do you want export the following projects?</p>
                                <div className="table-result">
                                    <ToolkitProvider bootstrap4
                                                     columns={ this.deleteColumns}
                                                     data={ this.state.selectedProjects }
                                                     keyField="id">
                                        {
                                            (props) => (
                                                <div>
                                                    <BootstrapTable { ...props.baseProps } bootstrap4 data={this.state.selectedProjects} columns={this.deleteColumns}
                                                                    defaultSorted={ this.defaultSort } keyField='id' hover
                                                                    striped
                                                                    pagination={ PaginationFactory(PaginationFactory()) }/>
                                                </div>
                                            )}
                                    </ToolkitProvider>
                                </div>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-success" data-dismiss="modal" onClick={this.onExportProjectsClick}>Export</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

}

export default ProjectOverview