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
import Tabs from 'react-bootstrap/Tabs'
import Tab from 'react-bootstrap/Tab'
import ToolkitProvider from "react-bootstrap-table2-toolkit";
import BootstrapTable from "react-bootstrap-table-next";
import {connect} from "react-redux";
import {setAuthenticationState} from "../../../redux/Actions";
import validateErrorResponse from "../../../utility/HttpResponseValidator";

class SoapMockResponse extends PureComponent {

    constructor(props) {
        super(props);
        this.onDeleteMockResponseClick = this.onDeleteMockResponseClick.bind(this);

        this.headerColumns = [{
            dataField: 'name',
            text: 'Name',
            sort: true
        }, {
            dataField: 'value',
            text: 'Value',
            sort: true
        }];

        this.xpathColumns = [{
            dataField: 'xpath',
            text: 'XPath',
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
            mockResponseId: this.props.match.params.mockResponseId,
            mockResponse: {
                httpHeaders: []
            }
        };

        this.getMockResponse();
    }



    onRowSelect(value, mode) {

    }

    onRowSelectAll(mode) {

    }

    getMockResponse() {
        axios
            .get("/api/rest/soap/project/" + this.state.projectId + "/port/" + this.state.portId + "/operation/" + this.state.operationId + "/mockresponse/" + this.state.mockResponseId)
            .then(response => {
                this.setState({
                    mockResponse: response.data,
                });
            })
            .catch(error => {
                validateErrorResponse(error, this.props.setAuthenticationState)
            });
    }

    onDeleteMockResponseClick() {
        axios
            .delete("/api/rest/soap/project/" + this.state.projectId + "/port/" + this.state.portId + "/operation/" + this.state.operationId + "/mockresponse/" + this.state.mockResponseId)
            .then(response => {
                this.props.history.push("/web/soap/project/" + this.state.projectId + "/port/" + this.state.portId + "/operation/" + this.state.operationId);
            })
            .catch(error => {
                validateErrorResponse(error, this.props.setAuthenticationState)
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
                                <li className="breadcrumb-item"><Link to={"/web/soap/project/" + this.state.projectId + "/port/" + this.state.portId + "/operation/" + this.state.operationId}>Operation</Link></li>
                                <li className="breadcrumb-item">{this.state.mockResponse.name}</li>
                            </ol>
                        </nav>
                    </div>
                    <div className="content-top">
                        <div className="title">
                            <h1>Mock Response: {this.state.mockResponse.name}</h1>
                        </div>
                        <div className="menu" align="right">
                            <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal" data-target="#deleteMockResponseModal"><i className="fas fa-plus-circle"/> <span>Delete mock response</span></button>
                        </div>
                    </div>
                    <div className="content-summary">
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Name</dt>
                            <dd className="col-sm-9"><input defaultValue={this.state.mockResponse.name}/></dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">HTTP Status code</dt>
                            <dd className="col-sm-9"><input defaultValue={this.state.mockResponse.httpStatusCode}/></dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Status</dt>
                            <dd className="col-sm-2">
                                <select id="inputState" className="form-control" defaultValue={this.state.mockResponse.status}>
                                    <option>ENABLED</option>
                                    <option>DISABLED</option>
                                </select>
                            </dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Use Expression</dt>
                            <dd className="col-sm-9"><input type="checkbox" defaultChecked={this.state.mockResponse.usingExpressions}/></dd>
                        </dl>
                    </div>
                    <div>
                        <Tabs defaultActiveKey="body">
                            <Tab eventKey="body" title="Body">
                                <textarea className="form-control" id="body" rows="20"  value={this.state.mockResponse.body}/>
                            </Tab>
                            <Tab eventKey="headers" title="Headers">
                                <div>
                                    <h4>Add header</h4>
                                    <div>
                                        <div className="form-group row">
                                            <label className="col-sm-2 col-form-label">Name</label>
                                            <div className="col-sm-10">
                                                <input className="form-control" type="text" />
                                            </div>
                                        </div>
                                        <div className="form-group row">
                                            <label className="col-sm-2 col-form-label">Value</label>
                                            <div className="col-sm-10">
                                                <input className="form-control" type="text" />
                                            </div>
                                        </div>
                                        <div className="form-group row">
                                            <button className="btn btn-success demo-button-disabled menu-button" ><i className="fas fa-plus-circle"/> <span>Add header</span></button>
                                        </div>
                                    </div>
                                    <div className="table-result">
                                        <ToolkitProvider bootstrap4
                                                         columns={ this.headerColumns}
                                                         data={this.state.mockResponse.httpHeaders}
                                                         keyField="name"
                                                         search>
                                            {
                                                (props) => (
                                                    <div>
                                                        <BootstrapTable {...props.baseProps} bootstrap4
                                                                        data={this.state.mockResponse.httpHeaders} columns={this.headerColumns}
                                                                        defaultSorted={this.defaultSort} keyField='name' hover
                                                                        noDataIndication="No headers"
                                                                        selectRow={this.selectRow}/>
                                                    </div>
                                                )}
                                        </ToolkitProvider>
                                    </div>
                                </div>
                            </Tab>
                            <Tab eventKey="xpath" title="XPath">
                                <div>
                                    <h4>Add XPath</h4>

                                    <div className="table-result">

                                        <ToolkitProvider bootstrap4
                                                         columns={ this.xpathColumns}
                                                         data={this.state.mockResponse.httpHeaders}
                                                         keyField="name"
                                                         search>
                                            {
                                                (props) => (
                                                    <div>
                                                        <BootstrapTable {...props.baseProps} bootstrap4
                                                                        data={this.state.mockResponse.httpHeaders} columns={this.xpathColumns}
                                                                        defaultSorted={this.defaultSort} keyField='name' hover
                                                                        noDataIndication="No XPaths"
                                                                        selectRow={this.selectRow}/>
                                                    </div>
                                                )}
                                        </ToolkitProvider>
                                    </div>
                                </div>
                            </Tab>
                        </Tabs>
                    </div>
                    <div className="panel-buttons">
                        <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal" data-target="#updateProjectModal"><i className="fas fa-plus-circle"/> <span>Update response</span></button>
                        <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal" data-target="#updateProjectModal"><i className="fas fa-plus-circle"/> <span>Discard changes</span></button>
                    </div>
                </section>

                <div className="modal fade" id="deleteMockResponseModal" tabIndex="-1" role="dialog"
                     aria-labelledby="deleteMockResponseModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="deleteMockResponseModalLabel">Delete the mock response?</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <p>Do you wanna delete the mock response?</p>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-danger" data-dismiss="modal" onClick={this.onDeleteMockResponseClick}>Delete</button>
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
)(SoapMockResponse);