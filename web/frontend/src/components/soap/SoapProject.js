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

class SoapProject extends PureComponent {

    constructor(props) {
        super(props);

        this.onRowSelect = this.onRowSelect.bind(this);
        this.onRowSelectAll = this.onRowSelectAll.bind(this);
        this.nameFormat = this.nameFormat.bind(this);
        this.onUpdateProjectClick = this.onUpdateProjectClick.bind(this);
        this.setUpdateProjectName = this.setUpdateProjectName.bind(this);
        this.setUpdateProjectDescription = this.setUpdateProjectDescription.bind(this);

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
            project: {
                ports: []
            },
            updateProjectName: "",
            updateProjectDescription: "",
        };

        this.getProject(this.state.projectId);
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
                <Link to={"/beta/web/soap/project/" + this.state.projectId + "/port/" + row.id}>{cell}</Link>
            </div>
        )
    }

    getProject(projectId) {
        axios
            .get("/api/rest/core/project/soap/" + projectId)
            .then(response => {
                this.setState({
                    project: response.data,
                });
            })
            .catch(error => {
                this.props.validateErrorResponse(error);
            });
    }

    setUpdateProjectName(updateProjectName) {
        this.setState({ updateProjectName: updateProjectName });
    }

    setUpdateProjectDescription(updateProjectDescription) {
        this.setState({ updateProjectDescription: updateProjectDescription });
    }


    onUpdateProjectClick() {

    }

    render() {
        return (
            <div>
                <section>
                    <div className="navigation">
                        <nav aria-label="breadcrumb">
                            <ol className="breadcrumb breadcrumb-custom">
                                <li className="breadcrumb-item"><Link to={"/beta/web"}>Home</Link></li>
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
                            <a className="btn btn-primary demo-button-disabled menu-button" href="/web/project/import"><i
                                className="fas fa-cloud-upload-alt"/> <span>Export project</span></a>
                            <a className="btn btn-danger demo-button-disabled menu-button" href="/web/project/import"><i
                                className="fas fa-cloud-upload-alt"/> <span>Delete project</span></a>
                        </div>
                    </div>
                    <div className="content-summary">
                        <dl className="row">
                            <dt className="col-sm-3">Description</dt>
                            <dd className="col-sm-9">{this.state.project.description}</dd>
                            <dt className="col-sm-3">Type</dt>
                            <dd className="col-sm-9">SOAP</dd>
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
                                                            pagination={ PaginationFactory(PaginationFactory()) }/>
                                        </div>
                                    )}
                            </ToolkitProvider>
                            <div className="panel-buttons">
                                <button className="btn btn-primary panel-button" type="submit" name="action" value="export"><i
                                    className="fas fa-cloud-download-alt"/> <span>Update</span></button>
                                <button className="btn btn-primary demo-button-disabled panel-button" type="submit" name="action"
                                        value="update"><i className="fas fa-trash"/> <span>Update endpoint</span>
                                </button>
                                <button className="btn btn-danger demo-button-disabled panel-button" type="submit" name="action"
                                        value="delete"><i className="fas fa-trash"/> <span>Delete port</span>
                                </button>
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
                                            <input className="form-control" type="text" name="newProjectName" id="updateProjectName" onChange={event => this.setUpdateProjectName(event.target.value)}/>
                                        </div>
                                    </div>
                                    <div className="form-group row">
                                        <label htmlFor="updateProjectDescription" className="col-sm-2 col-form-label">Description</label>
                                        <div className="col-sm-10">
                                            <textarea className="form-control" name="updateProjectDescription" id="updateProjectDescription" onChange={event => this.setupdateProjectDescription(event.target.value)}/>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-success" data-dismiss="modal" onClick={this.onupdateProjectClick}>Update</button>
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

                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-success" data-dismiss="modal" onClick={this.onCreateProjectClick}>Upload</button>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        )
    }

}

export default SoapProject