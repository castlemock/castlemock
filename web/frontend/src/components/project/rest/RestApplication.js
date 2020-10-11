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
const { SearchBar } = Search;

const SELECT = true;
const DESELECT = false;

class RestApplication extends PureComponent {

    constructor(props) {
        super(props);

        this.onRowSelect = this.onRowSelect.bind(this);
        this.onRowSelectAll = this.onRowSelectAll.bind(this);
        this.nameFormat = this.nameFormat.bind(this);
        this.onDeleteApplicationClick = this.onDeleteApplicationClick.bind(this);
        this.onDeleteResourcesClick = this.onDeleteResourcesClick.bind(this);

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
            applicationId: this.props.match.params.applicationId,
            application: {
                resources: []
            },
            selectedResources: []
        };

        this.getApplication();
    }

    onRowSelect(value, mode) {
        let resources = this.state.selectedResources.slice();
        let resource = {
            id: value.id,
            name: value.name
        };
        if(mode === SELECT){
            resources.push(resource);
        } else if(mode === DESELECT){
            let index = resources.indexOf(resource);
            resources.splice(index, 1);
        }
        this.setState({
            selectedResources: resources
        });
    }

    onRowSelectAll(mode) {
        if(mode === SELECT){
            let resources = [];
            this.state.application.resources.forEach(value => {
                let resource = {
                    id: value.id,
                    name: value.name
                };
                resources.push(resource);
            });
            this.setState({
                selectedResources: resources
            });
        } else if(mode === DESELECT){
            this.setState({
                selectedResources: []
            });
        }
    }


    nameFormat(cell, row) {
        if(cell == null){
            return;
        }

        return (
            <div className="table-link">
                <Link to={"/web/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" + row.id}>{cell}</Link>
            </div>
        )
    }

    getApplication() {
        axios
            .get("/api/rest/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId)
            .then(response => {
                this.setState({
                    application: response.data,
                });
            })
            .catch(error => {
                validateErrorResponse(error, this.props.setAuthenticationState)
            });
    }

    onDeleteApplicationClick() {
        axios
            .delete("/api/rest/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId)
            .then(response => {
                this.props.history.push("/web/rest/project/" + this.state.projectId);
            })
            .catch(error => {
                validateErrorResponse(error, this.props.setAuthenticationState)
            });
    }

    onDeleteResourcesClick() {
        Array.from(this.state.selectedResources).forEach(resource => {
            axios
                .delete("/api/rest/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" + resource.id)
                .then(response => {
                    this.getApplication();
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
                                <li className="breadcrumb-item"><Link to={"/web/rest/project/" + this.state.projectId}>Project</Link></li>
                                <li className="breadcrumb-item">{this.state.application.name}</li>
                            </ol>
                        </nav>
                    </div>
                    <div className="content-top">
                        <div className="title">
                            <h1>Application: {this.state.application.name}</h1>
                        </div>
                        <div className="menu" align="right">
                            <button className="btn btn-success demo-button-disabled menu-button" data-toggle="modal" data-target="#updateApplicationModal"><i className="fas fa-plus-circle"/> <span>Update application</span></button>
                            <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal" data-target="#updateApplicationModal"><i className="fas fa-plus-circle"/> <span>Create resource</span></button>
                            <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal" data-target="#deleteApplicationModal"><i className="fas fa-plus-circle"/> <span>Delete application</span></button>
                        </div>
                    </div>
                    <div className="panel panel-primary table-panel">
                        <div className="panel-heading table-panel-heading">
                            <h3 className="panel-title">Resources</h3>
                        </div>
                        <div className="table-result">
                            <ToolkitProvider bootstrap4
                                             columns={ this.columns}
                                             data={this.state.application.resources}
                                             keyField="id"
                                             search>
                                {
                                    (props) => (
                                        <div>
                                            <div>
                                                <SearchBar {...props.searchProps} className={"table-filter-field"}/>
                                            </div>
                                            <BootstrapTable {...props.baseProps} bootstrap4
                                                            data={this.state.application.resources} columns={this.columns}
                                                            defaultSorted={this.defaultSort} keyField='id' hover
                                                            selectRow={this.selectRow}
                                                            pagination={ PaginationFactory() }/>
                                        </div>
                                    )}
                            </ToolkitProvider>
                            <div className="panel-buttons">
                                <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal"
                                        disabled={this.state.selectedResources.length === 0}
                                        data-target="#updateStatusModal"><i className="fas fa-plus-circle"/> <span>Update status</span></button>
                                <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal"
                                        disabled={this.state.selectedResources.length === 0}
                                        data-target="#updateEndpointModal"><i className="fas fa-plus-circle"/> <span>Update endpoint</span></button>
                                <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal"
                                        disabled={this.state.selectedResources.length === 0}
                                        data-target="#deleteResourcesModal"><i className="fas fa-plus-circle"/> <span>Delete resource</span></button>
                            </div>
                        </div>
                    </div>
                </section>

                <div className="modal fade" id="updateApplicationModal" tabIndex="-1" role="dialog"
                     aria-labelledby="updateApplicationModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="updateApplicationModalLabel">Update application</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <form>
                                    <div className="form-group row">
                                        <label htmlFor="newApplicationName" className="col-sm-2 col-form-label">Name</label>
                                        <div className="col-sm-10">
                                            <input className="form-control" type="text" name="updateApplicationName" id="updateApplicationName" onChange={event => this.setUpdateApplicationName(event.target.value)}/>
                                        </div>
                                    </div>
                                    <div className="form-group row">
                                        <label htmlFor="newApplicationDescription" className="col-sm-2 col-form-label">Description</label>
                                        <div className="col-sm-10">
                                            <textarea className="form-control" name="updateApplicationDescription" id="updateApplicationDescription" onChange={event => this.setUpdateApplicationDescription(event.target.value)}/>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-success" data-dismiss="modal" onClick={this.onExportApplicationsClick}>Update</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="modal fade" id="deleteApplicationModal" tabIndex="-1" role="dialog"
                     aria-labelledby="deleteApplicationModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="deleteApplicationModalLabel">Delete the application?</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <p>Do you wanna delete the application?</p>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-danger" data-dismiss="modal" onClick={this.onDeleteApplicationClick}>Delete</button>
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
                                <p>Do you want update the status for the following resources?</p>
                                <div className="table-result">
                                    <ToolkitProvider bootstrap4
                                                     columns={ this.updateColumns}
                                                     data={ this.state.selectedResources }
                                                     keyField="id">
                                        {
                                            (props) => (
                                                <div>
                                                    <BootstrapTable { ...props.baseProps } bootstrap4 data={this.state.selectedResources} columns={this.updateColumns}
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
                                <p>Do you want update the endpoint for the following resources?</p>
                                <div className="table-result">
                                    <ToolkitProvider bootstrap4
                                                     columns={ this.updateColumns}
                                                     data={ this.state.selectedResources }
                                                     keyField="id">
                                        {
                                            (props) => (
                                                <div>
                                                    <BootstrapTable { ...props.baseProps } bootstrap4 data={this.state.selectedResources} columns={this.updateColumns}
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

                <div className="modal fade" id="deleteResourcesModal" tabIndex="-1" role="dialog"
                     aria-labelledby="deleteResourcesModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="deleteResourcesModalLabel">Delete resources?</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <p>Do you want delete the following resources?</p>
                                <div className="table-result">
                                    <ToolkitProvider bootstrap4
                                                     columns={ this.updateColumns}
                                                     data={ this.state.selectedResources }
                                                     keyField="id">
                                        {
                                            (props) => (
                                                <div>
                                                    <BootstrapTable { ...props.baseProps } bootstrap4 data={this.state.selectedResources} columns={this.updateColumns}
                                                                    defaultSorted={ this.defaultSort } keyField='id' hover
                                                                    striped
                                                                    pagination={ PaginationFactory({hideSizePerPage: true}) }/>
                                                </div>
                                            )}
                                    </ToolkitProvider>
                                </div>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-danger" data-dismiss="modal" onClick={this.onDeleteResourcesClick}>Delete</button>
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
)(RestApplication);