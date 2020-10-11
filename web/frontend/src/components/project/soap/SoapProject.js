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
import Dropzone from 'react-dropzone'
import {connect} from "react-redux";
import {setAuthenticationState} from "../../../redux/Actions";
import validateErrorResponse from "../../../utility/HttpResponseValidator";
import Badge from "react-bootstrap/Badge";
import {faUpload} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const { SearchBar } = Search;

const SELECT = true;
const DESELECT = false;

class SoapProject extends PureComponent {

    constructor(props) {
        super(props);

        this.onRowSelect = this.onRowSelect.bind(this);
        this.onRowSelectAll = this.onRowSelectAll.bind(this);
        this.nameFormat = this.nameFormat.bind(this);
        this.onUpdateProjectClick = this.onUpdateProjectClick.bind(this);
        this.onDeleteProjectClick = this.onDeleteProjectClick.bind(this);
        this.setUpdateProjectName = this.setUpdateProjectName.bind(this);
        this.setUpdateProjectDescription = this.setUpdateProjectDescription.bind(this);
        this.statusHeaderStyle = this.statusHeaderStyle.bind(this);
        this.onExportProjectClick = this.onExportProjectClick.bind(this);

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
            width: 20,
            sort: true,
            align: "center",
            headerStyle: this.statusHeaderStyle
        }, {
            dataField: 'statusCount.DISABLED',
            text: 'Disabled',
            width: 20,
            sort: true,
            align: "center",
            headerStyle: this.statusHeaderStyle
        }, {
            dataField: 'statusCount.FORWARDED',
            text: 'Forwarded',
            width: 20,
            sort: true,
            align: "center",
            headerStyle: this.statusHeaderStyle
        }, {
            dataField: 'statusCount.RECORDING',
            text: 'Recording',
            width: 20,
            sort: true,
            align: "center",
            headerStyle: this.statusHeaderStyle
        }, {
            dataField: 'statusCount.RECORD_ONCE',
            text: 'Record once',
            width: 20,
            sort: true,
            align: "center",
            headerStyle: this.statusHeaderStyle
        }, {
            dataField: 'statusCount.ECHO',
            text: 'Echo',
            width: 20,
            sort: true,
            align: "center",
            headerStyle: this.statusHeaderStyle
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
                ports: []
            },
            updateProject: {
                name: "",
                description: ""
            },
            selectedPorts: []
        };

        this.getProject();
    }

    statusHeaderStyle() {
        return { 'whiteSpace': 'nowrap', width: '140px' };
    }

    onDrop(acceptedFiles){

    }

    onRowSelect(value, mode) {
        let ports = this.state.selectedPorts.slice();
        let port = {
            id: value.id,
            name: value.name
        };
        if(mode === SELECT){
            ports.push(port);
        } else if(mode === DESELECT){
            let index = ports.indexOf(port);
            ports.splice(index, 1);
        }
        this.setState({
            selectedPorts: ports
        });
    }

    onRowSelectAll(mode) {
        if(mode === SELECT){
            let ports = [];
            this.state.project.ports.forEach(value => {
                let port = {
                    id: value.id,
                    name: value.name
                };
                ports.push(port);
            });
            this.setState({
                selectedPorts: ports
            });
        } else if(mode === DESELECT){
            this.setState({
                selectedPorts: []
            });
        }
    }

    nameFormat(cell, row) {
        if(cell == null){
            return;
        }

        return (
            <div className="table-link">
                <Link to={"/web/soap/project/" + this.state.projectId + "/port/" + row.id}>{cell}</Link>
            </div>
        )
    }

    getProject() {
        axios
            .get("/api/rest/soap/project/" + this.state.projectId)
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

    onExportProjectClick() {
        axios({
            url:  "/api/rest/core/project/soap/" + this.state.projectId + "/export",
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
            .put("/api/rest/core/project/soap/" + this.state.projectId, this.state.updateProject)
            .then(response => {
                this.getProject();
            })
            .catch(error => {
                validateErrorResponse(error, this.props.setAuthenticationState)
            });
    }

    onDeleteProjectClick() {
        axios
            .delete("/api/rest/core/project/soap/" + this.state.projectId)
            .then(response => {
                this.props.history.push("/web");
            })
            .catch(error => {
                validateErrorResponse(error, this.props.setAuthenticationState)
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
                        <div className="menu" align="right">
                            <button className="btn btn-success demo-button-disabled menu-button" data-toggle="modal" data-target="#updateProjectModal"><i className="fas fa-plus-circle"/> <span>Update project</span></button>
                            <div className="btn-group demo-button-disabled menu-button" role="group">
                                <button id="btnGroupDrop1" type="button" className="btn btn-primary dropdown-toggle"
                                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    Upload
                                </button>
                                <div className="dropdown-menu" aria-labelledby="btnGroupDrop1">
                                    <button className="dropdown-item" data-toggle="modal" data-target="#uploadWSDLModal">WSDL</button>
                                </div>
                            </div>
                            <button className="btn btn-primary demo-button-disabled menu-button" onClick={this.onExportProjectClick}><i className="fas fa-plus-circle"/> <span>Export project</span></button>
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
                            <dd className="col-sm-9"><Badge variant="primary">SOAP</Badge></dd>
                        </dl>
                    </div>
                    <div className="panel panel-primary table-panel">
                        <div className="panel-heading table-panel-heading">
                            <h3 className="panel-title">Ports</h3>
                        </div>
                        <div className="table-result">
                            <ToolkitProvider bootstrap4
                                             columns={ this.columns}
                                             data={this.state.project.ports}
                                             keyField="id"
                                             search>
                                {
                                    (props) => (
                                        <div>
                                            <div>
                                                <SearchBar {...props.searchProps} className={"table-filter-field"}/>
                                            </div>
                                            <BootstrapTable {...props.baseProps} bootstrap4
                                                            data={this.state.project.ports} columns={this.columns}
                                                            defaultSorted={this.defaultSort} keyField='id' hover
                                                            selectRow={this.selectRow}
                                                            noDataIndication="Upload a WSDL file to load ports"
                                                            pagination={ PaginationFactory() }/>
                                        </div>
                                    )}
                            </ToolkitProvider>
                            <div className="panel-buttons">
                                <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal"
                                        disabled={this.state.selectedPorts.length === 0}
                                        data-target="#updateStatusModal"><i className="fas fa-plus-circle"/> <span>Update status</span></button>
                                <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal"
                                        disabled={this.state.selectedPorts.length === 0}
                                        data-target="#updateEndpointModal"><i className="fas fa-plus-circle"/> <span>Update endpoint</span></button>
                                <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal"
                                        disabled={this.state.selectedPorts.length === 0}
                                        data-target="#deletePortsModal"><i className="fas fa-plus-circle"/> <span>Delete port</span></button>
                            </div>
                        </div>
                    </div>
                </section>

                <div className="modal fade" id="updateProjectModal" tabIndex="-1" role="dialog"
                     aria-labelledby="updateProjectModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="newProjectModalLabel">Update project</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <form>
                                    <div className="form-group row">
                                        <label htmlFor="updateProjectName" className="col-sm-2 col-form-label">Name</label>
                                        <div className="col-sm-10">
                                            <input className="form-control" type="text" name="newProjectName" id="updateProjectName" value={this.state.updateProject.name} onChange={event => this.setUpdateProjectName(event.target.value)} required/>
                                        </div>
                                    </div>
                                    <div className="form-group row">
                                        <label htmlFor="updateProjectDescription" className="col-sm-2 col-form-label">Description</label>
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

                <div className="modal fade" id="uploadWSDLModal" tabIndex="-1" role="dialog"
                     aria-labelledby="uploadWSDLModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="uploadWSDLModalLabel">Upload WSDL</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <div className="dropzone-content">
                                    <Dropzone styles={{ dropzone: { minHeight: 200, maxHeight: 250 } }}>
                                        {({getRootProps, getInputProps}) => (
                                            <div {...getRootProps()}>
                                                <input {...getInputProps()} />
                                                <div className="dropzone-icon-content">
                                                    <FontAwesomeIcon icon={faUpload} className="dropzone-icon" />
                                                </div>
                                                <p>Drag 'n' drop some files here, or click to select files</p>
                                            </div>
                                        )}
                                    </Dropzone>
                                </div>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-success" data-dismiss="modal" onClick={this.onCreateProjectClick}>Upload</button>
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
                                <p>Do you want update the status for the following ports?</p>
                                <div className="table-result">
                                    <ToolkitProvider bootstrap4
                                                     columns={ this.updateColumns}
                                                     data={ this.state.selectedPorts }
                                                     keyField="id">
                                        {
                                            (props) => (
                                                <div>
                                                    <BootstrapTable { ...props.baseProps } bootstrap4 data={this.state.selectedPorts} columns={this.updateColumns}
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
                                <p>Do you want update the endpoint for the following ports?</p>
                                <div className="table-result">
                                    <ToolkitProvider bootstrap4
                                                     columns={ this.updateColumns}
                                                     data={ this.state.selectedPorts }
                                                     keyField="id">
                                        {
                                            (props) => (
                                                <div>
                                                    <BootstrapTable { ...props.baseProps } bootstrap4 data={this.state.selectedPorts} columns={this.updateColumns}
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
                
                <div className="modal fade" id="deletePortsModal" tabIndex="-1" role="dialog"
                     aria-labelledby="deletePortsModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="deletePortsModalLabel">Delete ports?</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <p>Do you want delete the following ports?</p>
                                <div className="table-result">
                                    <ToolkitProvider bootstrap4
                                                     columns={ this.updateColumns}
                                                     data={ this.state.selectedPorts }
                                                     keyField="id">
                                        {
                                            (props) => (
                                                <div>
                                                    <BootstrapTable { ...props.baseProps } bootstrap4 data={this.state.selectedPorts} columns={this.updateColumns}
                                                                    defaultSorted={ this.defaultSort } keyField='id' hover
                                                                    striped
                                                                    pagination={ PaginationFactory({hideSizePerPage: true}) }/>
                                                </div>
                                            )}
                                    </ToolkitProvider>
                                </div>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-danger" data-dismiss="modal">Delete</button>
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
)(SoapProject);