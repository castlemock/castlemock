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
import DeleteMethodsModal from "./modal/DeleteMethodsModal";
import DeleteResourceModal from "./modal/DeleteResourceModal";
import UpdateEndpointModal from "./modal/UpdateEndpointModal";
import UpdateResourceModal from "./modal/UpdateResourceModal";
import UpdateStatusModal from "./modal/UpdateStatusModal";
import CreateMethodModal from "./modal/CreateMethodModal"
import {methodStatusFormatter} from "../utility/RestFormatter";
import {isOnlyReader} from "../../../../utility/AuthorizeUtility";
import AuthenticationContext from "../../../../context/AuthenticationContext";
import ContextContext from "../../../../context/ContextContext";
import {faEdit, faTrash, faFile, faCodeBranch} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const { SearchBar } = Search;
const SELECT = true;
const DESELECT = false;

class RestResource extends PureComponent {

    constructor(props) {
        super(props);

        this.onRowSelect = this.onRowSelect.bind(this);
        this.onRowSelectAll = this.onRowSelectAll.bind(this);
        this.nameFormat = this.nameFormat.bind(this);
        this.getResource = this.getResource.bind(this);
        this.responseStrategyFormat = this.responseStrategyFormat.bind(this);

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
            text: 'Method type',
            sort: true
        }, {
            dataField: 'status',
            text: 'Method Status',
            sort: true,
            formatter: this.responseStrategyFormat
        }, {
            dataField: 'forwardedEndpoint',
            text: 'Forwarded endpoint',
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
            resource: {
                methods: []
            },
            selectedMethods: []
        };

        this.getResource();
    }

    onRowSelect(value, mode) {
        let methods = this.state.selectedMethods.slice();
        let method = {
            id: value.id,
            name: value.name
        };
        if(mode === SELECT){
            methods.push(method);
        } else if(mode === DESELECT){
            let index = methods.indexOf(method);
            methods.splice(index, 1);
        }
        this.setState({
            selectedMethods: methods
        });
    }

    onRowSelectAll(mode) {
        if(mode === SELECT){
            let methods = [];
            this.state.resource.methods.forEach(value => {
                let method = {
                    id: value.id,
                    name: value.name
                };
                methods.push(method);
            });
            this.setState({
                selectedMethods: methods
            });
        } else if(mode === DESELECT){
            this.setState({
                selectedMethods: []
            });
        }
    }

    nameFormat(cell, row) {
        if(cell == null){
            return;
        }

        return (
            <div className="table-link">
                <Link to={"/web/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" + this.state.resourceId + "/method/" + row.id}>{cell}</Link>
            </div>
        )
    }

    responseStrategyFormat(cell) {
        if(cell == null){
            return;
        }

        return methodStatusFormatter(cell);
    }

    getResource() {
        axios
            .get(process.env.PUBLIC_URL + "/api/rest/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" + this.state.resourceId)
            .then(response => {
                this.setState({
                    resource: response.data,
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
                                <li className="breadcrumb-item"><Link to={"/web/rest/project/" + this.state.projectId}>Project</Link></li>
                                <li className="breadcrumb-item"><Link to={"/web/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId}>Application</Link></li>
                                <li className="breadcrumb-item">{this.state.resource.name}</li>
                            </ol>
                        </nav>
                    </div>
                    <div className="content-top">
                        <div className="title">
                            <h1>Resource: {this.state.resource.name}</h1>
                        </div>
                        <AuthenticationContext.Consumer>
                            {context => (
                                <div className="menu" align="right">
                                    <button className="btn btn-success demo-button-disabled menu-button" data-toggle="modal" data-target="#updateResourceModal" disabled={isOnlyReader(context.authentication.role)}><FontAwesomeIcon icon={faEdit} className="button-icon"/><span>Update resource</span></button>
                                    <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal" data-target="#createMethodModal" disabled={isOnlyReader(context.authentication.role)}><FontAwesomeIcon icon={faFile} className="button-icon"/><span>Create method</span></button>
                                    <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal" data-target="#deleteResourceModal" disabled={isOnlyReader(context.authentication.role)}><FontAwesomeIcon icon={faTrash} className="button-icon"/><span>Delete resource</span></button>
                                </div>
                            )}
                        </AuthenticationContext.Consumer>
                    </div>
                    <div className="content-summary">
                        <dl className="row">
                            <dt className="col-sm-2 content-title">URI</dt>
                            <dd className="col-sm-9">{this.state.resource.uri}</dd>
                        </dl>
                        <ContextContext.Consumer>
                            {context => (
                                <dl className="row">
                                    <dt className="col-sm-2 content-title">Address</dt>
                                    <dd className="col-sm-9">{window.location.origin + context + "/mock/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + this.state.resource.uri}</dd>
                                </dl>
                            )}
                        </ContextContext.Consumer>
                    </div>
                    <div className="panel panel-primary table-panel">
                        <div className="panel-heading table-panel-heading">
                            <h3 className="panel-title">Methods</h3>
                        </div>
                        <div className="table-result">
                            <ToolkitProvider bootstrap4
                                             columns={ this.columns}
                                             data={this.state.resource.methods}
                                             keyField="id"
                                             search>
                                {
                                    (props) => (
                                        <div>
                                            <div>
                                                <SearchBar {...props.searchProps} className={"table-filter-field"}/>
                                            </div>
                                            <BootstrapTable {...props.baseProps} bootstrap4
                                                            data={this.state.resource.methods} columns={this.columns}
                                                            defaultSorted={this.defaultSort} keyField='id' hover
                                                            selectRow={this.selectRow}
                                                            pagination={ PaginationFactory() }/>
                                        </div>
                                    )}
                            </ToolkitProvider>
                            <AuthenticationContext.Consumer>
                                {context => (
                                    <div className="panel-buttons">
                                        <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal"
                                                disabled={this.state.selectedMethods.length === 0 || isOnlyReader(context.authentication.role)}
                                                data-target="#updateStatusModal"><FontAwesomeIcon icon={faEdit} className="button-icon"/><span>Update status</span></button>
                                        <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal"
                                                disabled={this.state.selectedMethods.length === 0 || isOnlyReader(context.authentication.role)}
                                                data-target="#updateEndpointModal"><FontAwesomeIcon icon={faCodeBranch} className="button-icon"/><span>Update endpoint</span></button>
                                        <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal"
                                                disabled={this.state.selectedMethods.length === 0 || isOnlyReader(context.authentication.role)}
                                                data-target="#deleteMethodsModal"><FontAwesomeIcon icon={faTrash} className="button-icon"/><span>Delete method</span></button>
                                    </div>
                                )}
                            </AuthenticationContext.Consumer>
                        </div>

                    </div>
                </section>

                <CreateMethodModal projectId={this.state.projectId} applicationId={this.state.applicationId} resourceId={this.state.resourceId} />
                <DeleteMethodsModal projectId={this.state.projectId} applicationId={this.state.applicationId} resourceId={this.state.resourceId} selectedMethods={this.state.selectedMethods} getResource={this.getResource}/>
                <DeleteResourceModal projectId={this.state.projectId} applicationId={this.state.applicationId} resourceId={this.state.resourceId}/>
                <UpdateEndpointModal projectId={this.state.projectId} applicationId={this.state.applicationId} resourceId={this.state.resourceId} selectedMethods={this.state.selectedMethods} getResource={this.getResource}/>
                <UpdateResourceModal projectId={this.state.projectId} applicationId={this.state.applicationId} resourceId={this.state.resourceId} resource={this.state.resource}  getResource={this.getResource}/>
                <UpdateStatusModal projectId={this.state.projectId} applicationId={this.state.applicationId} resourceId={this.state.resourceId} selectedMethods={this.state.selectedMethods}  getResource={this.getResource}/>
            </div>
        )
    }

}

export default RestResource;