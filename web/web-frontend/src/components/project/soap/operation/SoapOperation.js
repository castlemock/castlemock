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
// import ToolkitProvider, {Search} from "react-bootstrap-table2-toolkit";
// import BootstrapTable from "react-bootstrap-table-next";
// import PaginationFactory from "react-bootstrap-table2-paginator";
import validateErrorResponse from "../../../../utility/HttpResponseValidator";
import AuthenticationContext from "../../../../context/AuthenticationContext";
import {isOnlyReader} from "../../../../utility/AuthorizeUtility";
import UpdateStatusModal from "./modal/UpdateStatusModal";
import UpdateOperationModal from "./modal/UpdateOperationModal";
import DeleteMockResponsesModal from "./modal/DeleteMockResponsesModal";
import CreateMockResponseModal from "./modal/CreateMockResponseModal";
import DuplicateMockResponseModal from "./modal/DuplicateMockResponseModal"
import {operationStatusFormatter, operationSoapVersionFormatter,
    operationIdentifyStrategy, operationResponseStrategy, mockResponseStatusFormatter} from "../utility/SoapFormatter"
import ContextContext from "../../../../context/ContextContext";
import {faTrash, faFile, faEdit, faCopy} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

// const { SearchBar } = Search;
const SELECT = true;
const DESELECT = false;

class SoapOperation extends PureComponent {

    constructor(props) {
        super(props);

        // Table
        this.onRowSelect = this.onRowSelect.bind(this);
        this.onRowSelectAll = this.onRowSelectAll.bind(this);
        this.nameFormat = this.nameFormat.bind(this);
        this.statusFormat = this.statusFormat.bind(this);
        this.getOperation = this.getOperation.bind(this);
        this.getPort = this.getPort.bind(this);

        // On click
        this.onDuplicateClick = this.onDuplicateClick.bind(this);

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
            sort: true,
            formatter: this.statusFormat
        }, {
            dataField: 'httpStatusCode',
            text: 'HTTP status code',
            sort: true
        }];

        this.defaultSort = [{
            dataField: 'name',
            order: 'asc'
        }];

        this.selectRow = {
            mode: 'checkbox',
            onSelect: this.onRowSelect,
            onSelectAll: this.onRowSelectAll
        };

        this.state = {
            projectId: this.props.match.params.projectId,
            portId: this.props.match.params.portId,
            operationId: this.props.match.params.operationId,
            operation: {
                operationIdentifier: {},
                mockResponses: []
            },
            port: {
                uri: ""
            },
            selectedMockResponses: []
        };

        this.getOperation();
        this.getPort();
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

    statusFormat(cell) {
        if(cell == null){
            return;
        }

        return mockResponseStatusFormatter(cell);
    }

    getOperation() {
        axios
            .get(process.env.PUBLIC_URL + "/api/rest/soap/project/" + this.state.projectId + "/port/" + this.state.portId + "/operation/" + this.state.operationId)
            .then(response => {
                this.setState({
                    operation: response.data});
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    getPort() {
        axios
            .get(process.env.PUBLIC_URL + "/api/rest/soap/project/" + this.state.projectId + "/port/" + this.state.portId)
            .then(response => {
                this.setState({
                    port: {
                        uri: response.data.uri
                    }
                });
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    onDuplicateClick(){
        let mockResponseIds = this.state.selectedMockResponses.map(mockResponse => mockResponse.id);
        axios
            .post(process.env.PUBLIC_URL + "/api/rest/soap/project/" + this.state.projectId + "/port/" +
                this.state.portId + "/operation/" + this.state.operationId + "/mockresponse/duplicate", {
                mockResponseIds: mockResponseIds
            })
            .then(response => {
                this.getOperation();
            })
            .catch(error => {
                validateErrorResponse(error)
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
                        <AuthenticationContext.Consumer>
                            {context => (
                                <div className="menu" align="right">
                                    <button className="btn btn-success demo-button-disabled menu-button" data-toggle="modal" data-target="#updateOperationModal" disabled={isOnlyReader(context.authentication.role)}>
                                        <i className="fas fa-plus-circle"/>
                                        <FontAwesomeIcon icon={faEdit} className="button-icon"/>
                                        <span>Update operation</span>
                                    </button>
                                    <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal" data-target="#createMockResponseModal" disabled={isOnlyReader(context.authentication.role)}>
                                        <i className="fas fa-plus-circle"/>
                                        <FontAwesomeIcon icon={faFile} className="button-icon"/>
                                        <span>Create response</span>
                                    </button>
                                </div>
                            )}
                        </AuthenticationContext.Consumer>
                    </div>
                    <div className="content-summary">
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Identifier</dt>
                            <dd className="col-sm-9">{this.state.operation.operationIdentifier.name}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">SOAP Version</dt>
                            <dd className="col-sm-9">{operationSoapVersionFormatter(this.state.operation.soapVersion)}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Identify strategy</dt>
                            <dd className="col-sm-9">{operationIdentifyStrategy(this.state.operation.identifyStrategy)}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Status</dt>
                            <dd className="col-sm-9">{operationStatusFormatter(this.state.operation.status)}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Response strategy</dt>
                            <dd className="col-sm-9">{operationResponseStrategy(this.state.operation.responseStrategy)}</dd>
                        </dl>
                        <ContextContext.Consumer>
                            {context => (
                                <dl className="row">
                                    <dt className="col-sm-3 content-title">Address</dt>
                                    <dd className="col-sm-9">{window.location.origin + context + "/mock/soap/project/" + this.state.projectId + "/" + this.state.port.uri}</dd>
                                </dl>
                            )}
                        </ContextContext.Consumer>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Original endpoint</dt>
                            <dd className="col-sm-9">{this.state.operation.originalEndpoint}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Forwarded endpoint</dt>
                            <dd className="col-sm-9">{this.state.operation.forwardedEndpoint}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Automatic forward with no match</dt>
                            <dd className="col-sm-9"><input type="checkbox" checked={this.state.operation.automaticForward} disabled={true}/></dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Simulate network delay</dt>
                            <dd className="col-sm-9"><input type="checkbox" checked={this.state.operation.simulateNetworkDelay} disabled={true}/></dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Network delay</dt>
                            <dd className="col-sm-9">{this.state.operation.networkDelay} ms</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Default response</dt>
                            <dd className="col-sm-9">{this.state.operation.defaultResponseName}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Mock on failure</dt>
                            <dd className="col-sm-9"><input type="checkbox" checked={this.state.operation.mockOnFailure} disabled={true}/></dd>
                        </dl>
                    </div>
                    <div className="panel panel-primary table-panel">
                        <div className="panel-heading table-panel-heading">
                            <h3 className="panel-title">Mock responses</h3>
                        </div>
                        <div className="table-result">
                            {/* <ToolkitProvider bootstrap4
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
                                                            noDataIndication="No mocked responses"
                                                            pagination={ PaginationFactory() }/>
                                        </div>
                                    )}
                            </ToolkitProvider> */}
                            <AuthenticationContext.Consumer>
                                {context => (
                                    <div className="panel-buttons">
                                        <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal"
                                                disabled={this.state.selectedMockResponses.length === 0 || isOnlyReader(context.authentication.role)}
                                                data-target="#updateStatusModal"><FontAwesomeIcon icon={faEdit} className="button-icon"/><span>Update status</span></button>
                                        <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal"
                                                disabled={this.state.selectedMockResponses.length === 0 || isOnlyReader(context.authentication.role)}
                                                data-target="#duplicateModal"><FontAwesomeIcon icon={faCopy} className="button-icon"/><span>Duplicate</span></button>
                                        <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal"
                                                disabled={this.state.selectedMockResponses.length === 0 || isOnlyReader(context.authentication.role)}
                                                data-target="#deleteMockResponsesModal"><FontAwesomeIcon icon={faTrash} className="button-icon"/><span>Delete responses</span></button>
                                    </div>
                                )}
                            </AuthenticationContext.Consumer>
                        </div>
                    </div>
                </section>

                <UpdateStatusModal projectId={this.state.projectId} portId={this.state.portId} operationId={this.state.operationId} getOperation={this.getOperation} selectedMockResponses={this.state.selectedMockResponses}/>
                <CreateMockResponseModal projectId={this.state.projectId} portId={this.state.portId} operationId={this.state.operationId}/>
                <DeleteMockResponsesModal projectId={this.state.projectId} portId={this.state.portId} operationId={this.state.operationId} selectedMockResponses={this.state.selectedMockResponses} getOperation={this.getOperation}/>
                <UpdateOperationModal projectId={this.state.projectId} portId={this.state.portId} operationId={this.state.operationId} operation={this.state.operation} getOperation={this.getOperation}/>
                <DuplicateMockResponseModal projectId={this.state.projectId} portId={this.state.portId} operationId={this.state.operationId} getOperation={this.getOperation} selectedMockResponses={this.state.selectedMockResponses}/>
            </div>
        )
    }
}

export default SoapOperation;