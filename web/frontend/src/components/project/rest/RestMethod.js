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

class RestMethod extends PureComponent {

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
        }, {
            dataField: 'status',
            text: 'Status',
            sort: true
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
            }
        };

        this.getMethod(this.state.projectId, this.state.applicationId, this.state.resourceId, this.state.methodId);
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
                <Link to={"/beta/web/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" + this.state.resourceId + "/method/" + this.state.methodId + "/mockresponse/" + row.id}>{cell}</Link>
            </div>
        )
    }

    getMethod(projectId, applicationId, resourceId, methodId) {
        axios
            .get("/api/rest/rest/project/" + projectId + "/application/" + applicationId + "/resource/" + resourceId + "/method/" + methodId)
            .then(response => {
                this.setState({
                    method: response.data
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
                                <li className="breadcrumb-item"><Link to={"/beta/web/rest/project/" + this.state.projectId}>Project</Link></li>
                                <li className="breadcrumb-item"><Link to={"/beta/web/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId}>Application</Link></li>
                                <li className="breadcrumb-item"><Link to={"/beta/web/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" + this.state.resourceId}>Resource</Link></li>
                                <li className="breadcrumb-item">{this.state.method.name}</li>
                            </ol>
                        </nav>
                    </div>
                    <div className="content-top">
                        <div className="title">
                            <h1>Method: {this.state.method.name}</h1>
                        </div>
                        <div className="menu" align="right">
                            <button className="btn btn-success demo-button-disabled menu-button" data-toggle="modal" data-target="#updateMethodModal"><i className="fas fa-plus-circle"/> <span>Update method</span></button>
                            <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal" data-target="#updateMethodModal"><i className="fas fa-plus-circle"/> <span>Delete method</span></button>
                        </div>
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
                                                            pagination={ PaginationFactory(PaginationFactory()) }/>
                                        </div>
                                    )}
                            </ToolkitProvider>
                            <div className="panel-buttons">
                                <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal" data-target="#updateMethodModal"><i className="fas fa-plus-circle"/> <span>Update</span></button>
                                <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal" data-target="#updateMethodModal"><i className="fas fa-plus-circle"/> <span>Update endpoint</span></button>
                                <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal" data-target="#updateMethodModal"><i className="fas fa-plus-circle"/> <span>Delete mock response</span></button>
                            </div>
                        </div>
                    </div>
                </section>

                <div className="modal fade" id="updateMethodModal" tabIndex="-1" role="dialog"
                     aria-labelledby="updateMethodModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="updateMethodModalLabel">Update method</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <form>
                                    <div className="form-group row">
                                        <label htmlFor="newMethodName" className="col-sm-2 col-form-label">Name</label>
                                        <div className="col-sm-10">
                                            <input className="form-control" type="text" name="updateMethodName" id="updateMethodName" onChange={event => this.setUpdateMethodName(event.target.value)}/>
                                        </div>
                                    </div>
                                    <div className="form-group row">
                                        <label htmlFor="newMethodDescription" className="col-sm-2 col-form-label">Description</label>
                                        <div className="col-sm-10">
                                            <textarea className="form-control" name="updateMethodDescription" id="updateMethodDescription" onChange={event => this.setUpdateMethodDescription(event.target.value)}/>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-success" data-dismiss="modal" onClick={this.onExportMethodsClick}>Update</button>
                            </div>
                        </div>
                    </div>
                </div>
                
            </div>
        )
    }

}

export default RestMethod