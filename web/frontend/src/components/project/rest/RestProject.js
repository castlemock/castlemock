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
import {Link} from "react-router-dom";
import axios from "axios";
import ToolkitProvider, {Search} from "react-bootstrap-table2-toolkit";
import BootstrapTable from "react-bootstrap-table-next";
import PaginationFactory from "react-bootstrap-table2-paginator";
import {connect} from "react-redux";
import {setAuthenticationState} from "../../../redux/Actions";
import validateErrorResponse from "../../../utility/HttpResponseValidator";
import Badge from "react-bootstrap/Badge";
const { SearchBar } = Search;

const SELECT = true;
const DESELECT = false;

class RestProject extends PureComponent {

    constructor(props) {
        super(props);

        this.onRowSelect = this.onRowSelect.bind(this);
        this.onRowSelectAll = this.onRowSelectAll.bind(this);
        this.nameFormat = this.nameFormat.bind(this);
        this.onDeleteProjectClick = this.onDeleteProjectClick.bind(this);
        this.onUpdateProjectClick = this.onUpdateProjectClick.bind(this);
        this.setUpdateProjectName = this.setUpdateProjectName.bind(this);
        this.setUpdateProjectDescription = this.setUpdateProjectDescription.bind(this);
        this.onExportProjectClick = this.onExportProjectClick.bind(this);
        this.setNewApplicationName = this.setNewApplicationName.bind(this);
        this.onCreateApplicationClick = this.onCreateApplicationClick.bind(this);
        this.onDeleteApplicationsClick = this.onDeleteApplicationsClick.bind(this);

        this.columns = [{
            dataField: 'id',
            text: 'id',
            hidden: true
        }, {
            dataField: 'name',
            text: 'Name',
            sort: true,
            formatter: this.nameFormat
        }, {
            dataField: 'statusCount.MOCKED',
            text: 'Mocked',
            sort: true
        }, {
            dataField: 'statusCount.DISABLED',
            text: 'Disabled',
            sort: true
        }, {
            dataField: 'statusCount.FORWARDED',
            text: 'Forwarded',
            sort: true
        }, {
            dataField: 'statusCount.RECORDING',
            text: 'Recording',
            sort: true
        }, {
            dataField: 'statusCount.RECORD_ONCE',
            text: 'Record once',
            sort: true
        }, {
            dataField: 'statusCount.ECHO',
            text: 'Echo',
            sort: true
        }];

        this.updateColumns = [{
            dataField: 'id',
            text: 'id',
            hidden: true
        }, {
            dataField: 'name',
            text: 'Name',
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
            projectId: this.props.match.params.projectId,
            project: {
                applications: []
            },
            updateProject: {
                name: "",
                description: ""
            },
            newApplication: {
                name: ""
            },
            selectedApplications: []
        };

        this.getProject();
    }

    onRowSelect(value, mode) {
        let applications = this.state.selectedApplications.slice();
        let application = {
            id: value.id,
            name: value.name
        };
        if(mode === SELECT){
            applications.push(application);
        } else if(mode === DESELECT){
            let index = applications.indexOf(application);
            applications.splice(index, 1);
        }
        this.setState({
            selectedApplications: applications
        });
    }

    onRowSelectAll(mode) {
        if(mode === SELECT){
            let applications = [];
            this.state.project.applications.forEach(value => {
                let application = {
                    id: value.id,
                    name: value.name
                };
                applications.push(application);
            });
            this.setState({
                selectedApplications: applications
            });
        } else if(mode === DESELECT){
            this.setState({
                selectedApplications: []
            });
        }
    }

    nameFormat(cell, row) {
        if(cell == null){
            return;
        }

        return (
            <div className="table-link">
                <Link to={"/web/rest/project/" + this.state.projectId + "/application/" + row.id}>{cell}</Link>
            </div>
        )
    }

    setUpdateProjectName(name) {
        this.setState({ updateProject: {
                ...this.state.updateProject,
                name: name
            }
        });
    }

    setUpdateProjectDescription(description) {
        this.setState({ updateProject: {
                ...this.state.updateProject,
                description: description
            }
        });
    }

    setNewApplicationName(name) {
        this.setState({
            newApplication: {
                ...this.state.newApplication,
                name: name
            }
        });
    }

    getProject() {
        axios
            .get("/api/rest/rest/project/" + this.state.projectId)
            .then(response => {
                this.setState({
                    project: response.data,
                    updateProject: {
                        name: response.data.name,
                        description: response.data.description
                    }
                });
            })
            .catch(error => {
                validateErrorResponse(error, this.props.setAuthenticationState)
            });
    }

    onExportProjectClick() {
        axios({
            url:  "/api/rest/core/project/rest/" + this.state.projectId + "/export",
            method: 'GET',
            responseType: 'blob'
        }).then((response) => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', this.state.projectId + ".xml");
            document.body.appendChild(link);
            link.click();
        })
            .catch(error => {
                validateErrorResponse(error, this.props.setAuthenticationState)
            });
    }

    onUpdateProjectClick() {
        axios
            .put("/api/rest/core/project/rest/" + this.state.projectId, this.state.updateProject)
            .then(response => {
                this.getProject();
            })
            .catch(error => {
                validateErrorResponse(error, this.props.setAuthenticationState)
            });
    }

    onDeleteProjectClick() {
        axios
            .delete("/api/rest/core/project/rest/" + this.state.projectId)
            .then(response => {
                this.props.history.push("/web");
            })
            .catch(error => {
                validateErrorResponse(error, this.props.setAuthenticationState)
            });
    }

    onCreateApplicationClick() {
        axios
            .post("/api/rest/rest/project/" + this.state.projectId + "/application", this.state.newApplication)
            .then(response => {
                this.props.history.push("/web/rest/project/" + this.state.projectId + "/application/" + response.data.id);
            })
            .catch(error => {
                validateErrorResponse(error, this.props.setAuthenticationState)
            });
    }

    onDeleteApplicationsClick() {
        Array.from(this.state.selectedApplications).forEach(application => {
            axios
                .delete("/api/rest/rest/project/" + this.state.project.id + "/application/" + application.id)
                .then(response => {
                    this.getProject();
                })
                .catch(error => {
                    validateErrorResponse(error, this.props.setAuthenticationState)
                });
        });
    }

    render() {
        return (
            <div>
                <section>
                    <div className="navigation">
                        <nav aria-label="breadcrumb">
                            <ol className="breadcrumb breadcrumb-custom">
                                <li className="breadcrumb-item"><Link to={"/web"}>Home</Link></li>
                                <li className="breadcrumb-item">{this.state.project.name}</li>
                            </ol>
                        </nav>
                    </div>
                    <div className="content-top">
                        <div className="title">
                            <h1>Project: {this.state.project.name}</h1>
                        </div>
                        <div className="menu">
                            <button className="btn btn-success demo-button-disabled menu-button" data-toggle="modal" data-target="#updateProjectModal"><i className="fas fa-plus-circle"/> <span>Update project</span></button>
                            <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal" data-target="#createApplicationModal"><i className="fas fa-plus-circle"/> <span>Create application</span></button>
                            <div className="btn-group demo-button-disabled menu-button" role="group">
                                <button id="btnGroupDrop1" type="button" className="btn btn-primary dropdown-toggle"
                                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    Upload
                                </button>
                                <div className="dropdown-menu" aria-labelledby="btnGroupDrop1">
                                    <button className="dropdown-item" data-toggle="modal" data-target="#uploadRAMLModal">RAML</button>
                                    <button className="dropdown-item" data-toggle="modal" data-target="#uploadSwaggerModal">Swagger</button>
                                    <button className="dropdown-item" data-toggle="modal" data-target="#uploadWADLModal">WADL</button>
                                </div>
                            </div>                            <button className="btn btn-primary demo-button-disabled menu-button" onClick={this.onExportProjectClick}><i className="fas fa-plus-circle"/> <span>Export project</span></button>
                            <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal" data-target="#deleteProjectModal"><i className="fas fa-plus-circle"/> <span>Delete project</span></button>
                        </div>
                    </div>
                    <div className="content-summary">
                        <dl className="row">
                            <dt className="col-sm-2 content-title content-title">Description</dt>
                            <dd className="col-sm-9">{this.state.project.description}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Type</dt>
                            <dd className="col-sm-9"><Badge variant="success">REST</Badge></dd>
                        </dl>
                    </div>
                    <div className="panel panel-primary table-panel">
                        <div className="panel-heading table-panel-heading">
                            <h3 className="panel-title">Applications</h3>
                        </div>
                        <div className="table-result">
                            <ToolkitProvider bootstrap4
                                             columns={ this.columns}
                                             data={this.state.project.applications}
                                             keyField="id"
                                             search>
                                {
                                    (props) => (
                                        <div>
                                            <div>
                                                <SearchBar {...props.searchProps} className={"table-filter-field"}/>
                                            </div>
                                            <BootstrapTable {...props.baseProps} bootstrap4
                                                            data={this.state.project.applications} columns={this.columns}
                                                            defaultSorted={this.defaultSort} keyField='id' hover
                                                            selectRow={this.selectRow}
                                                            noDataIndication="Click on the 'Upload' to upload a REST API definition"
                                                            pagination={ PaginationFactory() }/>
                                        </div>
                                    )}
                            </ToolkitProvider>
                            <div className="panel-buttons">
                                <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal"
                                        disabled={this.state.selectedApplications.length === 0}
                                        data-target="#updateStatusModal"><i className="fas fa-plus-circle"/> <span>Update status</span></button>
                                <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal"
                                        disabled={this.state.selectedApplications.length === 0}
                                        data-target="#updateEndpointModal"><i className="fas fa-plus-circle"/> <span>Update endpoint</span></button>
                                <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal"
                                        disabled={this.state.selectedApplications.length === 0}
                                        data-target="#deleteApplicationsModal"><i className="fas fa-plus-circle"/> <span>Delete application</span></button>
                            </div>
                        </div>
                    </div>
                </section>

                <div className="modal fade" id="updateProjectModal" tabIndex="-1" role="dialog"
                     aria-labelledby="updateProjectModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="updateProjectModalLabel">Update project</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <form>
                                    <div className="form-group row">
                                        <label htmlFor="newProjectName" className="col-sm-2 col-form-label">Name</label>
                                        <div className="col-sm-10">
                                            <input className="form-control" type="text" name="updateProjectName" id="updateProjectName" value={this.state.updateProject.name} onChange={event => this.setUpdateProjectName(event.target.value)}/>
                                        </div>
                                    </div>
                                    <div className="form-group row">
                                        <label htmlFor="newProjectDescription" className="col-sm-2 col-form-label">Description</label>
                                        <div className="col-sm-10">
                                            <textarea className="form-control" name="updateProjectDescription" id="updateProjectDescription" value={this.state.updateProject.description} onChange={event => this.setUpdateProjectDescription(event.target.value)}/>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-success" data-dismiss="modal" onClick={this.onUpdateProjectClick}>Update</button>
                            </div>
                        </div>
                    </div>
                </div>


                <div className="modal fade" id="createApplicationModal" tabIndex="-1" role="dialog"
                     aria-labelledby="createApplicationModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="createApplicationModalLabel">Create application</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <form>
                                    <div className="form-group row">
                                        <label className="col-sm-2 col-form-label">Name</label>
                                        <div className="col-sm-10">
                                            <input className="form-control" type="text" value={this.state.newApplication.name} onChange={event => this.setNewApplicationName(event.target.value)}/>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-success" data-dismiss="modal" onClick={this.onCreateApplicationClick}>Update</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="modal fade" id="deleteProjectModal" tabIndex="-1" role="dialog"
                     aria-labelledby="deleteProjectModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="deleteProjectModalLabel">Delete the project?</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <p>Do you wanna delete the project?</p>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-danger" data-dismiss="modal" onClick={this.onDeleteProjectClick}>Delete</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="modal fade" id="updateStatusModal" tabIndex="-1" role="dialog"
                     aria-labelledby="updateStatusModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="updateStatusModalLabel">Update status?</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <p>Do you want update the status for the following applications?</p>
                                <div className="table-result">
                                    <ToolkitProvider bootstrap4
                                                     columns={ this.updateColumns}
                                                     data={ this.state.selectedApplications }
                                                     keyField="id">
                                        {
                                            (props) => (
                                                <div>
                                                    <BootstrapTable { ...props.baseProps } bootstrap4 data={this.state.selectedApplications} columns={this.updateColumns}
                                                                    defaultSorted={ this.defaultSort } keyField='id' hover
                                                                    striped
                                                                    pagination={ PaginationFactory({hideSizePerPage: true}) }/>
                                                </div>
                                            )}
                                    </ToolkitProvider>
                                </div>
                                <div className="form-group row">
                                    <label className="col-sm-2 col-form-label">Status</label>
                                    <div className="col-sm-10">
                                        <select id="inputStatus" className="form-control" defaultValue="MOCKED">
                                            <option>MOCKED</option>
                                            <option>DISABLED</option>
                                            <option>FORWARDED</option>
                                            <option>RECORDING</option>
                                            <option>RECORD_ONCE</option>
                                            <option>ECHO</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-primary" data-dismiss="modal">Update</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="modal fade" id="updateEndpointModal" tabIndex="-1" role="dialog"
                     aria-labelledby="updateEndpointModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="updateEndpointModalLabel">Update endpoint?</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <p>Do you want update the endpoint for the following applications?</p>
                                <div className="table-result">
                                    <ToolkitProvider bootstrap4
                                                     columns={ this.updateColumns}
                                                     data={ this.state.selectedApplications }
                                                     keyField="id">
                                        {
                                            (props) => (
                                                <div>
                                                    <BootstrapTable { ...props.baseProps } bootstrap4 data={this.state.selectedApplications} columns={this.updateColumns}
                                                                    defaultSorted={ this.defaultSort } keyField='id' hover
                                                                    striped
                                                                    pagination={ PaginationFactory({hideSizePerPage: true}) }/>
                                                </div>
                                            )}
                                    </ToolkitProvider>
                                </div>
                                <div className="form-group row">
                                    <label className="col-sm-2 col-form-label">Endpoint</label>
                                    <div className="col-sm-10">
                                        <input className="form-control" type="text" />
                                    </div>
                                </div>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-primary" data-dismiss="modal">Update</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="modal fade" id="deleteApplicationsModal" tabIndex="-1" role="dialog"
                     aria-labelledby="deleteApplicationsModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="deleteApplicationsModalLabel">Delete applications?</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <p>Do you want delete the following applications?</p>
                                <div className="table-result">
                                    <ToolkitProvider bootstrap4
                                                     columns={ this.updateColumns}
                                                     data={ this.state.selectedApplications }
                                                     keyField="id">
                                        {
                                            (props) => (
                                                <div>
                                                    <BootstrapTable { ...props.baseProps } bootstrap4 data={this.state.selectedApplications} columns={this.updateColumns}
                                                                    defaultSorted={ this.defaultSort } keyField='id' hover
                                                                    striped
                                                                    pagination={ PaginationFactory({hideSizePerPage: true}) }/>
                                                </div>
                                            )}
                                    </ToolkitProvider>
                                </div>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-danger" data-dismiss="modal" onClick={this.onDeleteApplicationsClick}>Delete</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

}

export default connect(
    null,
    { setAuthenticationState }
)(RestProject);