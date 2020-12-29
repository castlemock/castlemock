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
import DeleteApplicationModal from "./modal/DeleteApplicationModal";
import DeleteResourcesModal from "./modal/DeleteResourcesModal";
import UpdateApplicationModal from "./modal/UpdateApplicationModal";
import UpdateEndpointModal from "./modal/UpdateEndpointModal";
import UpdateStatusModal from "./modal/UpdateStatusModal";
import CreateResourceModal from "./modal/CreateResourceModal"
import {isOnlyReader} from "../../../../utility/AuthorizeUtility";
import AuthenticationContext from "../../../../context/AuthenticationContext";
import {faEdit, faTrash, faFile, faCodeBranch} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const { SearchBar } = Search;
const SELECT = true;
const DESELECT = false;

class RestApplication extends PureComponent {

    constructor(props) {
        super(props);

        this.onRowSelect = this.onRowSelect.bind(this);
        this.onRowSelectAll = this.onRowSelectAll.bind(this);
        this.nameFormat = this.nameFormat.bind(this);
        this.getApplication = this.getApplication.bind(this);

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
            .get(process.env.PUBLIC_URL + "/api/rest/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId)
            .then(response => {
                this.setState({
                    application: response.data,
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
                                <li className="breadcrumb-item">{this.state.application.name}</li>
                            </ol>
                        </nav>
                    </div>
                    <div className="content-top">
                        <div className="title">
                            <h1>Application: {this.state.application.name}</h1>
                        </div>
                        <AuthenticationContext.Consumer>
                            {context => (
                                <div className="menu" align="right">
                                    <button className="btn btn-success demo-button-disabled menu-button" data-toggle="modal" data-target="#updateApplicationModal" disabled={isOnlyReader(context.authentication.role)}><FontAwesomeIcon icon={faEdit} className="button-icon"/><span>Update application</span></button>
                                    <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal" data-target="#createResourceModal" disabled={isOnlyReader(context.authentication.role)}><FontAwesomeIcon icon={faFile} className="button-icon"/><span>Create resource</span></button>
                                    <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal" data-target="#deleteApplicationModal" disabled={isOnlyReader(context.authentication.role)}><FontAwesomeIcon icon={faTrash} className="button-icon"/><span>Delete application</span></button>
                                </div>
                                )}
                        </AuthenticationContext.Consumer>
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
                            <AuthenticationContext.Consumer>
                                {context => (
                                    <div className="panel-buttons">
                                        <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal"
                                                disabled={this.state.selectedResources.length === 0 || isOnlyReader(context.authentication.role)}
                                                data-target="#updateStatusModal"><FontAwesomeIcon icon={faEdit} className="button-icon"/><span>Update status</span></button>
                                        <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal"
                                                disabled={this.state.selectedResources.length === 0 || isOnlyReader(context.authentication.role)}
                                                data-target="#updateEndpointModal"><FontAwesomeIcon icon={faCodeBranch} className="button-icon"/><span>Update endpoint</span></button>
                                        <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal"
                                                disabled={this.state.selectedResources.length === 0 || isOnlyReader(context.authentication.role)}
                                                data-target="#deleteResourcesModal"><FontAwesomeIcon icon={faTrash} className="button-icon"/><span>Delete resource</span></button>
                                    </div>
                                )}
                            </AuthenticationContext.Consumer>
                        </div>
                    </div>
                </section>

                <CreateResourceModal projectId={this.state.projectId} applicationId={this.state.applicationId}/>
                <DeleteApplicationModal projectId={this.state.projectId} applicationId={this.state.applicationId}/>
                <DeleteResourcesModal projectId={this.state.projectId} applicationId={this.state.applicationId} selectedResources={this.state.selectedResources}/>
                <UpdateApplicationModal projectId={this.state.projectId} applicationId={this.state.applicationId} getApplication={this.getApplication}/>
                <UpdateEndpointModal projectId={this.state.projectId} applicationId={this.state.applicationId} selectedResources={this.state.selectedResources} getApplication={this.getApplication}/>
                <UpdateStatusModal projectId={this.state.projectId} applicationId={this.state.applicationId} selectedResources={this.state.selectedResources} getApplication={this.getApplication}/>
            </div>
        )
    }

}

export default RestApplication;