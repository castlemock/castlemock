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
import validateErrorResponse from "../../../../utility/HttpResponseValidator";
import DeleteMethodModal from "./modal/DeleteMethodModal";
import DeleteMockResponsesModal from "./modal/DeleteMockResponsesModal";
import UpdateMethodModal from "./modal/UpdateMethodModal";
import CreateMockResponseModal from "./modal/CreateMockResponseModal";
import DuplicateMockResponsesModal from "./modal/DuplicateMockResponsesModal";
import UpdateStatusModal from "./modal/UpdateStatusModal"
import {mockResponseStatusFormatter, methodResponseStrategyFormatter, methodStatusFormatter} from "../utility/RestFormatter";

const { SearchBar } = Search;
const SELECT = true;
const DESELECT = false;

class RestMethod extends PureComponent {

    constructor(props) {
        super(props);

        this.onRowSelect = this.onRowSelect.bind(this);
        this.onRowSelectAll = this.onRowSelectAll.bind(this);
        this.nameFormat = this.nameFormat.bind(this);
        this.getMethod = this.getMethod.bind(this);
        this.statusFormat = this.statusFormat.bind(this);

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
            dataField: 'status',
            text: 'Status',
            sort: true,
            formatter: this.statusFormat
        }, {
            dataField: 'httpStatusCode',
            text: 'HTTP status code',
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
            resourceId: this.props.match.params.resourceId,
            methodId: this.props.match.params.methodId,
            method: {
                mockResponses: []
            },
            selectedMockResponses: []
        };

        this.getMethod();
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
            this.state.method.mockResponses.forEach(value => {
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
                <Link to={"/castlemock/web/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" + this.state.resourceId + "/method/" + this.state.methodId + "/mockresponse/" + row.id}>{cell}</Link>
            </div>
        )
    }

    statusFormat(cell) {
        if(cell == null){
            return;
        }

        return mockResponseStatusFormatter(cell);
    }

    getMethod() {
        axios
            .get("/castlemock/api/rest/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" + this.state.resourceId + "/method/" + this.state.methodId)
            .then(response => {
                this.setState({
                    method: response.data
                });
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
                                <li className="breadcrumb-item"><Link to={"/castlemock/web/rest/project/" + this.state.projectId}>Project</Link></li>
                                <li className="breadcrumb-item"><Link to={"/castlemock/web/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId}>Application</Link></li>
                                <li className="breadcrumb-item"><Link to={"/castlemock/web/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" + this.state.resourceId}>Resource</Link></li>
                                <li className="breadcrumb-item">{this.state.method.name}</li>
                            </ol>
                        </nav>
                    </div>
                    <div className="content-top">
                        <div className="title">
                            <h1>Method: {this.state.method.name}</h1>
                        </div>
                        <div className="menu" align="right">
                            <button className="btn btn-success demo-button-disabled menu-button" data-toggle="modal" data-target="#updateMethodModal"><span>Update method</span></button>
                            <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal" data-target="#createMockResponseModal"><span>Create response</span></button>
                            <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal" data-target="#deleteMethodModal"><span>Delete method</span></button>
                        </div>
                    </div>
                    <div className="content-summary">
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Type</dt>
                            <dd className="col-sm-9">{this.state.method.httpMethod}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Status</dt>
                            <dd className="col-sm-9">{methodStatusFormatter(this.state.method.status)}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Response strategy</dt>
                            <dd className="col-sm-9">{methodResponseStrategyFormatter(this.state.method.responseStrategy)}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Address</dt>
                            <dd className="col-sm-9">Test</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Forwarded endpoint</dt>
                            <dd className="col-sm-9">{this.state.method.forwardedEndpoint}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Simulate network delay</dt>
                            <dd className="col-sm-9"><input type="checkbox" value={this.state.method.simulateNetworkDelay} disabled={true}/></dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Network delay</dt>
                            <dd className="col-sm-9">{this.state.method.networkDelay} ms</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Default response</dt>
                            <dd className="col-sm-9">{this.state.method.defaultResponseName}</dd>
                        </dl>
                    </div>
                    <div className="panel panel-primary table-panel">
                        <div className="panel-heading table-panel-heading">
                            <h3 className="panel-title">Mock responses</h3>
                        </div>
                        <div className="table-result">
                            <ToolkitProvider bootstrap4
                                             columns={ this.columns}
                                             data={this.state.method.mockResponses}
                                             keyField="id"
                                             search>
                                {
                                    (props) => (
                                        <div>
                                            <div>
                                                <SearchBar {...props.searchProps} className={"table-filter-field"}/>
                                            </div>
                                            <BootstrapTable {...props.baseProps} bootstrap4
                                                            data={this.state.method.mockResponses} columns={this.columns}
                                                            defaultSorted={this.defaultSort} keyField='id' hover
                                                            selectRow={this.selectRow}
                                                            noDataIndication="No mocked responses"
                                                            pagination={ PaginationFactory() }/>
                                        </div>
                                    )}
                            </ToolkitProvider>
                            <div className="panel-buttons">
                                <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal"
                                        disabled={this.state.selectedMockResponses.length === 0}
                                        data-target="#updateStatusModal"><span>Update status</span></button>
                                <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal"
                                        disabled={this.state.selectedMockResponses.length === 0}
                                        data-target="#duplicateMockResponsesModal"><span>Duplicate</span></button>
                                <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal"
                                        disabled={this.state.selectedMockResponses.length === 0}
                                        data-target="#deleteMockResponsesModal"><span>Delete mock response</span></button>
                            </div>
                        </div>
                    </div>
                </section>

                <CreateMockResponseModal projectId={this.state.projectId} applicationId={this.state.applicationId} resourceId={this.state.resourceId} methodId={this.state.methodId}/>
                <DeleteMethodModal projectId={this.state.projectId} applicationId={this.state.applicationId} resourceId={this.state.resourceId} methodId={this.state.methodId}/>
                <DeleteMockResponsesModal projectId={this.state.projectId} applicationId={this.state.applicationId} resourceId={this.state.resourceId} methodId={this.state.methodId} getMethod={this.getMethod} selectedMockResponses={this.state.selectedMockResponses}/>
                <UpdateMethodModal projectId={this.state.projectId} applicationId={this.state.applicationId} resourceId={this.state.resourceId} methodId={this.state.methodId} method={this.state.method} getMethod={this.getMethod}/>
                <DuplicateMockResponsesModal projectId={this.state.projectId} applicationId={this.state.applicationId} resourceId={this.state.resourceId} methodId={this.state.methodId} getMethod={this.getMethod} selectedMockResponses={this.state.selectedMockResponses} />
                <UpdateStatusModal projectId={this.state.projectId} applicationId={this.state.applicationId} resourceId={this.state.resourceId} methodId={this.state.methodId} selectedMockResponses={this.state.selectedMockResponses} getMethod={this.getMethod}/>
            </div>
        )
    }

}

export default RestMethod;