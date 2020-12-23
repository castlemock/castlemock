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
import Badge from "react-bootstrap/Badge";
import AuthenticationContext from "../../../../context/AuthenticationContext";
import {isOnlyReader} from "../../../../utility/AuthorizeUtility";
import UpdateEndpointModal from "./modal/UpdateEndpointModal"
import UpdateStatusModal from "./modal/UpdateStatusModal"
import UpdateProjectModal from "./modal/UpdateProjectModal"
import DeletePortsModal from "./modal/DeletePortsModal"
import DeleteProjectModal from "./modal/DeleteProjectModal"
import UploadWSDLModal from "./modal/UploadWSDLModal"

const { SearchBar } = Search;

const SELECT = true;
const DESELECT = false;

class SoapProject extends PureComponent {

    constructor(props) {
        super(props);

        // Table
        this.onRowSelect = this.onRowSelect.bind(this);
        this.onRowSelectAll = this.onRowSelectAll.bind(this);
        this.resourceNameFormat = this.resourceNameFormat.bind(this);
        this.nameFormat = this.nameFormat.bind(this);
        this.statusHeaderStyle = this.statusHeaderStyle.bind(this);
        this.resourceTypeHeaderStyle = this.resourceTypeHeaderStyle.bind(this);

        // Getters
        this.getProject = this.getProject.bind(this);

        // On click
        this.onExportProjectClick = this.onExportProjectClick.bind(this);

        // Setters

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

        this.resourceColumns = [{
            dataField: 'id',
            text: 'id',
            hidden: true
        }, {
            dataField: 'type',
            text: 'Type',
            sort: true,
            headerStyle: this.resourceTypeHeaderStyle
        }, {
            dataField: 'name',
            text: 'Name',
            sort: true,
            formatter: this.resourceNameFormat
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
                ports: [],
                resources: []
            },
            selectedPorts: []
        };

        this.getProject();
    }

    resourceTypeHeaderStyle() {
        return { 'whiteSpace': 'nowrap', width: '140px' };
    }

    statusHeaderStyle() {
        return { 'whiteSpace': 'nowrap', width: '140px' };
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

    resourceNameFormat(cell, row) {
        if(cell == null){
            return;
        }

        return (
            <div className="table-link">
                <Link to={"/web/soap/project/" + this.state.projectId + "/resource/" + row.id}>{cell}</Link>
            </div>
        )
    }

    getProject() {
        axios
            .get(process.env.PUBLIC_URL + "/api/rest/soap/project/" + this.state.projectId)
            .then(response => {
                this.setState({
                    project: response.data
                });
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    onExportProjectClick() {
        axios({
            url:  process.env.PUBLIC_URL + "/api/rest/core/project/soap/" + this.state.projectId + "/export",
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
                                <li className="breadcrumb-item">{this.state.project.name}</li>
                            </ol>
                        </nav>
                    </div>
                    <div className="content-top">
                        <div className="title">
                            <h1>Project: {this.state.project.name}</h1>
                        </div>
                        <AuthenticationContext.Consumer>
                            {context => (
                                <div className="menu" align="right">
                                    <button className="btn btn-success demo-button-disabled menu-button" data-toggle="modal" data-target="#updateProjectModal" disabled={isOnlyReader(context.authentication.role)}><span>Update project</span></button>
                                    <div className="btn-group demo-button-disabled menu-button" role="group">
                                        <button id="btnGroupDrop1" type="button" className="btn btn-primary dropdown-toggle"
                                                data-toggle="dropdown" aria-haspopup="true"
                                                aria-expanded="false" disabled={isOnlyReader(context.authentication.role)}>
                                            Upload
                                        </button>
                                        <div className="dropdown-menu" aria-labelledby="btnGroupDrop1">
                                            <button className="dropdown-item" data-toggle="modal" data-target="#uploadWSDLModal">WSDL</button>
                                        </div>
                                    </div>
                                    <button className="btn btn-primary demo-button-disabled menu-button" onClick={this.onExportProjectClick} disabled={isOnlyReader(context.authentication.role)}><span>Export project</span></button>
                                    <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal" data-target="#deleteProjectModal" disabled={isOnlyReader(context.authentication.role)}><span>Delete project</span></button>
                                </div>
                            )}
                        </AuthenticationContext.Consumer>
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
                            <AuthenticationContext.Consumer>
                                {context => (
                                    <div className="panel-buttons">
                                        <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal"
                                                disabled={this.state.selectedPorts.length === 0 || isOnlyReader(context.authentication.role)}
                                                data-target="#updateStatusModal"><span>Update status</span></button>
                                        <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal"
                                                disabled={this.state.selectedPorts.length === 0 || isOnlyReader(context.authentication.role)}
                                                data-target="#updateEndpointModal"><span>Update endpoint</span></button>
                                        <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal"
                                                disabled={this.state.selectedPorts.length === 0 || isOnlyReader(context.authentication.role)}
                                                data-target="#deletePortsModal"><span>Delete port</span></button>
                                    </div>
                                )}
                            </AuthenticationContext.Consumer>
                        </div>
                    </div>
                    <div className="panel panel-primary table-panel">
                        <div className="panel-heading table-panel-heading">
                            <h3 className="panel-title">Resources</h3>
                        </div>
                        <div className="table-result">
                            <ToolkitProvider bootstrap4
                                             columns={ this.resourceColumns}
                                             data={this.state.project.resources}
                                             keyField="id"
                                             search>
                                {
                                    (props) => (
                                        <div>
                                            <BootstrapTable {...props.baseProps} bootstrap4
                                                            data={this.state.project.resources} columns={this.resourceColumns}
                                                            defaultSorted={this.defaultSort} keyField='id' hover
                                                            noDataIndication="Upload a WSDL file to load ports"
                                                            pagination={ PaginationFactory() }/>
                                        </div>
                                    )}
                            </ToolkitProvider>
                        </div>
                    </div>
                </section>

                <DeletePortsModal projectId={this.state.projectId} selectedPorts={this.state.selectedPorts} getProject={this.getProject}/>
                <DeleteProjectModal projectId={this.state.projectId}/>
                <UpdateEndpointModal projectId={this.state.projectId} selectedPorts={this.state.selectedPorts} getProject={this.getProject}/>
                <UpdateProjectModal projectId={this.state.projectId} project={this.state.project} getProject={this.getProject}/>
                <UpdateStatusModal projectId={this.state.projectId} selectedPorts={this.state.selectedPorts} getProject={this.getProject}/>
                <UploadWSDLModal  projectId={this.state.projectId} getProject={this.getProject}/>
            </div>
        )
    }

}

export default SoapProject;