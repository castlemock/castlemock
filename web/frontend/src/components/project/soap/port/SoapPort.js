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
import AuthenticationContext from "../../../../context/AuthenticationContext";
import {isOnlyReader} from "../../../../utility/AuthorizeUtility";
import DeletePortModal from "./modal/DeletePortModal"
import UpdateEndpointModal from "./modal/UpdateEndpointModal";
import UpdateStatusModal from "./modal/UpdateStatusModal";
import {operationStatusFormatter, operationResponseStrategy} from "../utility/SoapFormatter"

const { SearchBar } = Search;
const SELECT = true;
const DESELECT = false;

class SoapPort extends PureComponent {

    constructor(props) {
        super(props);

        // Table
        this.onRowSelect = this.onRowSelect.bind(this);
        this.onRowSelectAll = this.onRowSelectAll.bind(this);
        this.nameFormat = this.nameFormat.bind(this);
        this.methodHeaderStyle = this.methodHeaderStyle.bind(this);
        this.responseStrategyHeaderStyle = this.responseStrategyHeaderStyle.bind(this);
        this.statusHeaderStyle = this.statusHeaderStyle.bind(this);
        this.responseStrategyFormat = this.responseStrategyFormat.bind(this);
        this.statusFormat = this.statusFormat.bind(this);

        this.getPort = this.getPort.bind(this);

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
            dataField: 'httpMethod',
            text: 'Method',
            sort: true,
            headerStyle: this.methodHeaderStyle
        }, {
            dataField: 'responseStrategy',
            text: 'Response strategy',
            sort: true,
            headerStyle: this.responseStrategyHeaderStyle,
            formatter: this.responseStrategyFormat
        }, {
            dataField: 'status',
            text: 'Status',
            sort: true,
            headerStyle: this.statusHeaderStyle,
            formatter: this.statusFormat
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
            port: {
                operations: []
            },
            selectedOperations: []
        };

        this.getPort();
    }

    methodHeaderStyle() {
        return { 'whiteSpace': 'nowrap', width: '150px' };
    }

    responseStrategyHeaderStyle() {
        return { 'whiteSpace': 'nowrap', width: '200px' };
    }

    statusHeaderStyle() {
        return { 'whiteSpace': 'nowrap', width: '150px' };
    }
    
    onRowSelect(value, mode) {
        let operations = this.state.selectedOperations.slice();
        let operation = {
            id: value.id,
            name: value.name
        };
        if(mode === SELECT){
            operations.push(operation);
        } else if(mode === DESELECT){
            let index = operations.indexOf(operation);
            operations.splice(index, 1);
        }
        this.setState({
            selectedOperations: operations
        });
    }

    onRowSelectAll(mode) {
        if(mode === SELECT){
            let operations = [];
            this.state.port.operations.forEach(value => {
                let operation = {
                    id: value.id,
                    name: value.name
                };
                operations.push(operation);
            });
            this.setState({
                selectedOperations: operations
            });
        } else if(mode === DESELECT){
            this.setState({
                selectedOperations: []
            });
        }
    }

    nameFormat(cell, row) {
        if(cell == null){
            return;
        }

        return (
            <div className="table-link">
                <Link to={"/web/soap/project/" + this.state.projectId + "/port/" + this.state.portId + "/operation/" + row.id}>{cell}</Link>
            </div>
        )
    }

    responseStrategyFormat(cell) {
        if(cell == null){
            return;
        }

        return operationResponseStrategy(cell);
    }

    statusFormat(cell) {
        if(cell == null){
            return;
        }

        return operationStatusFormatter(cell);
    }

    getPort() {
        axios
            .get(process.env.PUBLIC_URL + "/api/rest/soap/project/" + this.state.projectId + "/port/" + this.state.portId)
            .then(response => {
                this.setState({
                    port: response.data
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
                                <li className="breadcrumb-item"><Link to={"/web/soap/project/" + this.state.projectId}>Project</Link></li>
                                <li className="breadcrumb-item">{this.state.port.name}</li>
                            </ol>
                        </nav>
                    </div>
                    <div className="content-top">
                        <div className="title">
                            <h1>Port: {this.state.port.name}</h1>
                        </div>
                        <AuthenticationContext.Consumer>
                            {context => (
                                <div className="menu" align="right">
                                    <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal" data-target="#deletePortModal" disabled={isOnlyReader(context.authentication.role)}>
                                        <span>Delete port</span>
                                    </button>
                                </div>
                            )}
                        </AuthenticationContext.Consumer>
                    </div>
                    <div className="content-summary">
                        <dl className="row">
                            <dt className="col-sm-2 content-title">URI</dt>
                            <dd className="col-sm-9">{this.state.port.uri}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Address</dt>
                            <dd className="col-sm-9">{window.location.origin + "/mock/soap/project/" + this.state.projectId + "/" + this.state.port.uri}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">WSDL</dt>
                            <dd className="col-sm-9">{window.location.origin + "/mock/soap/project/" + this.state.projectId + "/" + this.state.port.uri + "?wsdl"}</dd>
                        </dl>
                    </div>
                    <div className="panel panel-primary table-panel">
                        <div className="panel-heading table-panel-heading">
                            <h3 className="panel-title">Operations</h3>
                        </div>
                        <div className="table-result">
                            <ToolkitProvider bootstrap4
                                             columns={ this.columns}
                                             data={this.state.port.operations}
                                             keyField="id"
                                             search>
                                {
                                    (props) => (
                                        <div>
                                            <div>
                                                <SearchBar {...props.searchProps} className={"table-filter-field"}/>
                                            </div>
                                            <BootstrapTable {...props.baseProps} bootstrap4
                                                            data={this.state.port.operations} columns={this.columns}
                                                            defaultSorted={this.defaultSort} keyField='id' hover
                                                            selectRow={this.selectRow}
                                                            striped
                                                            noDataIndication="Table is Empty"
                                                            pagination={ PaginationFactory() }/>
                                        </div>
                                    )}
                            </ToolkitProvider>
                            <AuthenticationContext.Consumer>
                                {context => (
                                    <div className="panel-buttons">
                                        <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal"
                                                disabled={this.state.selectedOperations.length === 0 || isOnlyReader(context.authentication.role)}
                                                data-target="#updateStatusModal"><span>Update status</span></button>
                                        <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal"
                                                disabled={this.state.selectedOperations.length === 0 || isOnlyReader(context.authentication.role)}
                                                data-target="#updateEndpointModal"><span>Update endpoint</span></button>
                                    </div>
                                )}
                            </AuthenticationContext.Consumer>
                        </div>
                    </div>
                </section>

                <DeletePortModal projectId={this.state.projectId} portId={this.state.portId} selectedOperations={this.state.selectedOperations} />
                <UpdateEndpointModal projectId={this.state.projectId} portId={this.state.portId} selectedOperations={this.state.selectedOperations} getPort={this.getPort} />
                <UpdateStatusModal projectId={this.state.projectId} portId={this.state.portId} selectedOperations={this.state.selectedOperations} getPort={this.getPort} />

            </div>
        )
    }
}

export default SoapPort;