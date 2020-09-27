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
const { SearchBar } = Search;

class SoapOperation extends PureComponent {

    constructor(props) {
        super(props);

        this.onRowSelect = this.onRowSelect.bind(this);
        this.onRowSelectAll = this.onRowSelectAll.bind(this);
        this.nameFormat = this.nameFormat.bind(this);

        this.columns = [{
            dataField: 'id',
            text: 'id',
            hidden: true
        }, {
            dataField: 'name',
            text: 'Name',
            sort: true,
            formatter: this.nameFormat
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
            }
        };

        this.getOperation(this.state.projectId, this.state.portId, this.state.operationId);
    }



    onRowSelect(value, mode) {

    }

    onRowSelectAll(mode) {

    }

    nameFormat(cell, row) {
        if(cell == null){
            return;
        }

        return (
            <div className="table-link">
                <Link to={"/beta/web/soap/project/" + this.state.projectId + "/port/" + this.state.portId + "/operation/" + this.state.operationId + "/mockresponse/" + row.id }>{cell}</Link>
            </div>
        )
    }

    getOperation(projectId, portId, operationId) {
        axios
            .get("/api/rest/soap/project/" + projectId + "/port/" + portId + "/operation/" + operationId)
            .then(response => {
                this.setState({
                    operation: response.data,
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
                        <nav aria-label="breadcrumb">
                            <ol className="breadcrumb breadcrumb-custom">
                                <li className="breadcrumb-item"><Link to={"/beta/web"}>Home</Link></li>
                                <li className="breadcrumb-item"><Link to={"/beta/web/soap/project/" + this.state.projectId}>Project</Link></li>
                                <li className="breadcrumb-item"><Link to={"/beta/web/soap/project/" + this.state.projectId + "/port/" + this.state.portId}>Port</Link></li>
                                <li className="breadcrumb-item">{this.state.operation.name}</li>
                            </ol>
                        </nav>
                    </div>
                    <div className="content-top">
                        <div className="title">
                            <h1>Operation: {this.state.operation.name}</h1>
                        </div>
                        <div className="menu" align="right">
                            <a className="btn btn-primary demo-button-disabled menu-button" href="/web/project/create"><i
                                className="fas fa-plus-circle"/> <span>Update operation</span></a>
                        </div>
                    </div>
                    <div className="content-summary">
                        <dl className="row">
                            <dt className="col-sm-3">Identifier</dt>
                            <dd className="col-sm-9">{this.state.operation.operationIdentifier.name}</dd>
                            <dt className="col-sm-3">SOAP Version</dt>
                            <dd className="col-sm-9">{this.state.operation.soapVersion}</dd>
                            <dt className="col-sm-3">Status</dt>
                            <dd className="col-sm-9">{this.state.operation.status}</dd>
                            <dt className="col-sm-3">Identify strategy</dt>
                            <dd className="col-sm-9">{this.state.operation.identifyStrategy}</dd>
                            <dt className="col-sm-3">Response strategy</dt>
                            <dd className="col-sm-9">{this.state.operation.responseStrategy}</dd>
                            <dt className="col-sm-3">Address</dt>
                            <dd className="col-sm-9">Test</dd>
                            <dt className="col-sm-3">Original endpoint</dt>
                            <dd className="col-sm-9">{this.state.operation.originalEndpoint}</dd>
                            <dt className="col-sm-3">Forwarded endpoint</dt>
                            <dd className="col-sm-9">{this.state.operation.forwardedEndpoint}</dd>
                            <dt className="col-sm-3">Simulate network delay</dt>
                            <dd className="col-sm-9">{this.state.operation.simulateNetworkDelay}</dd>
                            <dt className="col-sm-3">Network delay</dt>
                            <dd className="col-sm-9">{this.state.operation.networkDelay}</dd>
                            <dt className="col-sm-3">Default response</dt>
                            <dd className="col-sm-9">Test</dd>
                            <dt className="col-sm-3">Mock on failure</dt>
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
                                                            pagination={ PaginationFactory(PaginationFactory()) }/>
                                        </div>
                                    )}
                            </ToolkitProvider>
                            <div className="panel-buttons">
                                <button className="btn btn-primary panel-button" name="action" value="export"><i
                                    className="fas fa-cloud-download-alt"/> <span>Update</span></button>
                                <button className="btn btn-primary demo-button-disabled panel-button" name="action"
                                        value="update"><i className="fas fa-trash"/> <span>Update endpoint</span>
                                </button>
                                <button className="btn btn-danger demo-button-disabled panel-button" name="action"
                                        value="delete"><i className="fas fa-trash"/> <span>Delete port</span>
                                </button>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        )
    }

}

export default SoapOperation