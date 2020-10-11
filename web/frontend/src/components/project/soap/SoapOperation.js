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

class SoapOperation extends PureComponent {

    constructor(props) {
        super(props);

        this.onRowSelect = this.onRowSelect.bind(this);
        this.onRowSelectAll = this.onRowSelectAll.bind(this);
        this.nameFormat = this.nameFormat.bind(this);
        this.setNewMockResponseName = this.setNewMockResponseName.bind(this);
        this.setNewMockResponseStatus = this.setNewMockResponseStatus.bind(this);
        this.onCreateMockResponseClick = this.onCreateMockResponseClick.bind(this);
        this.onDeleteMockResponsesClick = this.onDeleteMockResponsesClick.bind(this);

        this.columns = [{
            dataField: 'id',
            text: 'id',
            hidden: true
        }, {
            dataField: 'name',
            text: 'Response name',
            sort: true,
            formatter: this.nameFormat
        }, {
            dataField: 'status',
            text: 'Status',
            sort: true
        }, {
            dataField: 'httpStatusCode',
            text: 'HTTP status code',
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
            portId: this.props.match.params.portId,
            operationId: this.props.match.params.operationId,
            operation: {
                operationIdentifier: {},
                mockResponses: []
            },
            selectedMockResponses: [],
            newMockResponse: {
                name: "",
                status: "ENABLED"
            }
        };

        this.getOperation();
    }

    setNewMockResponseName(name) {
        this.setState({ newMockResponse: {
                ...this.state.newMockResponse,
                name: name
            }
        });
    }

    setNewMockResponseStatus(status) {
        this.setState({ newMockResponse: {
                ...this.state.newMockResponse,
                status: status
            }
        });
    }

    onRowSelect(value, mode) {
        let mockResponses = this.state.selectedMockResponses.slice();
        let mockResponse = {
            id: value.id,
            name: value.name
        };
        if(mode === SELECT){
            mockResponses.push(mockResponse);
        } else if(mode === DESELECT){
            let index = mockResponses.indexOf(mockResponse);
            mockResponses.splice(index, 1);
        }
        this.setState({
            selectedMockResponses: mockResponses
        });
    }

    onRowSelectAll(mode) {
        if(mode === SELECT){
            let mockResponses = [];
            this.state.operation.mockResponses.forEach(value => {
                let mockResponse = {
                    id: value.id,
                    name: value.name
                };
                mockResponses.push(mockResponse);
            });
            this.setState({
                selectedMockResponses: mockResponses
            });
        } else if(mode === DESELECT){
            this.setState({
                selectedMockResponses: []
            });
        }
    }

    nameFormat(cell, row) {
        if(cell == null){
            return;
        }

        return (
            <div className="table-link">
                <Link to={"/web/soap/project/" + this.state.projectId + "/port/" + this.state.portId + "/operation/" + this.state.operationId + "/mockresponse/" + row.id }>{cell}</Link>
            </div>
        )
    }

    getOperation() {
        axios
            .get("/api/rest/soap/project/" + this.state.projectId + "/port/" + this.state.portId + "/operation/" + this.state.operationId)
            .then(response => {
                this.setState({
                    operation: response.data,
                });
            })
            .catch(error => {
                validateErrorResponse(error, this.props.setAuthenticationState)
            });
    }

    onCreateMockResponseClick() {
        axios
            .post("/api/rest/soap/project/" + this.state.projectId + "/port/" +
                this.state.portId + "/operation/" + this.state.operationId + "/response",
                this.state.newMockResponse)
            .then(response => {
                this.props.history.push("/web/soap/project/" + this.state.projectId + "/port/" + this.state.portId + "/operation/" + this.state.operationId + "/response");
            })
            .catch(error => {
                validateErrorResponse(error, this.props.setAuthenticationState)
            });
    }

    onDeleteMockResponsesClick() {
        this.state.selectedMockResponses.forEach(mockResponse => {
            axios
                .delete("/api/rest/soap/project/" + this.state.projectId + "/port/" +
                    this.state.portId + "/operation/" + this.state.operationId + "/response/" + mockResponse.id)
                .then(response => {
                    this.getOperation();
                })
                .catch(error => {
                    validateErrorResponse(error, this.props.setAuthenticationState)
                });
        })

    }

    render() {
        return (
            <div>
                <section>
                    <div className="navigation">
                        <nav aria-label="breadcrumb">
                            <ol className="breadcrumb breadcrumb-custom">
                                <li className="breadcrumb-item"><Link to={"/web"}>Home</Link></li>
                                <li className="breadcrumb-item"><Link to={"/web/soap/project/" + this.state.projectId}>Project</Link></li>
                                <li className="breadcrumb-item"><Link to={"/web/soap/project/" + this.state.projectId + "/port/" + this.state.portId}>Port</Link></li>
                                <li className="breadcrumb-item">{this.state.operation.name}</li>
                            </ol>
                        </nav>
                    </div>
                    <div className="content-top">
                        <div className="title">
                            <h1>Operation: {this.state.operation.name}</h1>
                        </div>
                        <div className="menu" align="right">
                            <button className="btn btn-success demo-button-disabled menu-button" data-toggle="modal" data-target="#updateOperationModal"><i className="fas fa-plus-circle"/> <span>Update operation</span></button>
                            <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal" data-target="#createMockResponseModal"><i className="fas fa-plus-circle"/> <span>Create response</span></button>
                        </div>
                    </div>
                    <div className="content-summary">
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Identifier</dt>
                            <dd className="col-sm-9">{this.state.operation.operationIdentifier.name}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">SOAP Version</dt>
                            <dd className="col-sm-9">{this.state.operation.soapVersion}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Identify strategy</dt>
                            <dd className="col-sm-9">{this.state.operation.identifyStrategy}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Status</dt>
                            <dd className="col-sm-9">{this.state.operation.status}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Response strategy</dt>
                            <dd className="col-sm-9">{this.state.operation.responseStrategy}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Address</dt>
                            <dd className="col-sm-9">Test</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Original endpoint</dt>
                            <dd className="col-sm-9">{this.state.operation.originalEndpoint}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Forwarded endpoint</dt>
                            <dd className="col-sm-9">{this.state.operation.forwardedEndpoint}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Simulate network delay</dt>
                            <dd className="col-sm-9">{this.state.operation.simulateNetworkDelay}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Network delay</dt>
                            <dd className="col-sm-9">{this.state.operation.networkDelay}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Default response</dt>
                            <dd className="col-sm-9">{this.state.operation.defaultResponseName}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Mock on failure</dt>
                            <dd className="col-sm-9">{this.state.operation.mockOnFailure}</dd>
                        </dl>
                    </div>
                    <div className="panel panel-primary table-panel">
                        <div className="panel-heading table-panel-heading">
                            <h3 className="panel-title">Mock responses</h3>
                        </div>
                        <div className="table-result">
                            <ToolkitProvider bootstrap4
                                             columns={ this.columns}
                                             data={this.state.operation.mockResponses}
                                             keyField="id"
                                             search>
                                {
                                    (props) => (
                                        <div>
                                            <div>
                                                <SearchBar {...props.searchProps} className={"table-filter-field"}/>
                                            </div>
                                            <BootstrapTable {...props.baseProps} bootstrap4
                                                            data={this.state.operation.mockResponses} columns={this.columns}
                                                            defaultSorted={this.defaultSort} keyField='id' hover
                                                            selectRow={this.selectRow}
                                                            pagination={ PaginationFactory() }/>
                                        </div>
                                    )}
                            </ToolkitProvider>
                            <div className="panel-buttons">
                                <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal"
                                        disabled={this.state.selectedMockResponses.length === 0}
                                        data-target="#updateStatusModal"><i className="fas fa-plus-circle"/> <span>Update status</span></button>
                                <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal"
                                        disabled={this.state.selectedMockResponses.length === 0}
                                        data-target="#updateOperationModal"><i className="fas fa-plus-circle"/> <span>Duplicate</span></button>
                                <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal"
                                        disabled={this.state.selectedMockResponses.length === 0}
                                        data-target="#deleteMockResponsesModal"><i className="fas fa-plus-circle"/> <span>Delete responses</span></button>
                            </div>
                        </div>
                    </div>
                </section>

                <div className="modal fade" id="updateOperationModal" tabIndex="-1" role="dialog"
                     aria-labelledby="updateOperationModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="updateOperationModalLabel">Update operation</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <form>
                                    <div className="form-group row">
                                        <label htmlFor="newOperationStatus" className="col-sm-3 col-form-label">Status</label>
                                        <div className="col-sm-9">
                                            <select id="inputStatus" className="form-control" defaultValue={this.state.operation.status}>
                                                <option>MOCKED</option>
                                                <option>DISABLED</option>
                                                <option>FORWARDED</option>
                                                <option>RECORDING</option>
                                                <option>RECORD_ONCE</option>
                                                <option>ECHO</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div className="form-group row">
                                        <label htmlFor="newOperationResponseStrategy" className="col-sm-3 col-form-label">Response strategy</label>
                                        <div className="col-sm-9">
                                            <select id="inputStatus" className="form-control" defaultValue={this.state.operation.responseStrategy}>
                                                <option>RANDOM</option>
                                                <option>SEQUENCE</option>
                                                <option>XPATH_INPUT</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div className="form-group row">
                                        <label className="col-sm-3 col-form-label">Response strategy</label>
                                        <div className="col-sm-9">
                                            <input className="form-control" type="text" />
                                        </div>
                                    </div>
                                    <div className="form-group row">
                                        <label className="col-sm-3 col-form-label">Simulate network delay</label>
                                        <div className="col-sm-9">
                                            <input type="checkbox" />
                                        </div>
                                    </div>
                                    <div className="form-group row">
                                        <label className="col-sm-3 col-form-label">Network delay</label>
                                        <div className="col-sm-9">
                                            <input className="form-control" type="text" />
                                        </div>
                                    </div>
                                    <div className="form-group row">
                                        <label htmlFor="newOperationResponseStrategy" className="col-sm-3 col-form-label">Default response</label>
                                        <div className="col-sm-9">
                                            <select id="inputStatus" className="form-control" >

                                            </select>
                                        </div>
                                    </div>
                                    <div className="form-group row">
                                        <label className="col-sm-3 col-form-label">Mock on failure</label>
                                        <div className="col-sm-9">
                                            <input type="checkbox" />
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-success" data-dismiss="modal" onClick={this.onExportOperationsClick}>Update</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="modal fade" id="createMockResponseModal" tabIndex="-1" role="dialog"
                     aria-labelledby="createMockResponseModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="createMockResponseModalLabel">Create response?</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <div className="form-group row">
                                    <label className="col-sm-2 col-form-label">Name</label>
                                    <div className="col-sm-10">
                                        <input className="form-control" type="text" onChange={event => this.setNewMockResponseName(event.target.value)} />
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label className="col-sm-2 col-form-label">Status</label>
                                    <div className="col-sm-10">
                                        <select id="inputStatus" className="form-control" defaultValue="MOCKED" onChange={event => this.setNewMockResponseStatus(event.target.value)}>
                                            <option>ENABLED</option>
                                            <option>DISABLED</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-primary" data-dismiss="modal" onClick={this.onCreateMockResponseClick}>Create</button>
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
                                <p>Do you want update the status for the following mock responses?</p>
                                <div className="table-result">
                                    <ToolkitProvider bootstrap4
                                                     columns={ this.updateColumns}
                                                     data={ this.state.selectedMockResponses }
                                                     keyField="id">
                                        {
                                            (props) => (
                                                <div>
                                                    <BootstrapTable { ...props.baseProps } bootstrap4 data={this.state.selectedMockResponses} columns={this.updateColumns}
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
                                <p>Do you want update the endpoint for the following mockResponses?</p>
                                <div className="table-result">
                                    <ToolkitProvider bootstrap4
                                                     columns={ this.updateColumns}
                                                     data={ this.state.selectedMockResponses }
                                                     keyField="id">
                                        {
                                            (props) => (
                                                <div>
                                                    <BootstrapTable { ...props.baseProps } bootstrap4 data={this.state.selectedMockResponses} columns={this.updateColumns}
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

                <div className="modal fade" id="deleteMockResponsesModal" tabIndex="-1" role="dialog"
                     aria-labelledby="deleteMockResponsesModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="deleteMockResponsesModalLabel">Delete mockResponses?</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <p>Do you want delete the following mockResponses?</p>
                                <div className="table-result">
                                    <ToolkitProvider bootstrap4
                                                     columns={ this.updateColumns}
                                                     data={ this.state.selectedMockResponses }
                                                     keyField="id">
                                        {
                                            (props) => (
                                                <div>
                                                    <BootstrapTable { ...props.baseProps } bootstrap4 data={this.state.selectedMockResponses} columns={this.updateColumns}
                                                                    defaultSorted={ this.defaultSort } keyField='id' hover
                                                                    striped
                                                                    pagination={ PaginationFactory({hideSizePerPage: true}) }/>
                                                </div>
                                            )}
                                    </ToolkitProvider>
                                </div>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-danger" data-dismiss="modal" onClick={this.onDeleteMockResponsesClick}>Delete</button>
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
)(SoapOperation);