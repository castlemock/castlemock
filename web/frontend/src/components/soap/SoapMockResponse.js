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
import ToolkitProvider, {Search} from "react-bootstrap-table2-toolkit";
import BootstrapTable from "react-bootstrap-table-next";
const { SearchBar } = Search;

class SoapMockResponse extends PureComponent {

    constructor(props) {
        super(props);

        this.columns = [{
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
            mockResponseId: this.props.match.params.mockResponseId,
            mockResponse: {
                httpHeaders: []
            }
        };

        this.getMockResponse(this.state.projectId, this.state.portId, this.state.operationId, this.state.mockResponseId);
    }



    onRowSelect(value, mode) {

    }

    onRowSelectAll(mode) {

    }

    getMockResponse(projectId, portId, operationId, mockResponseId) {
        axios
            .get("/api/rest/soap/project/" + projectId + "/port/" + portId + "/operation/" + operationId + "/response/" + mockResponseId)
            .then(response => {
                this.setState({
                    mockResponse: response.data,
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
                                <li className="breadcrumb-item"><Link to={"/beta/web/soap/project/" + this.state.projectId + "/port/" + this.state.portId + "/operation/" + this.state.operationId}>Operation</Link></li>
                                <li className="breadcrumb-item">{this.state.mockResponse.name}</li>
                            </ol>
                        </nav>
                    </div>
                    <div className="content-top">
                        <div className="title">
                            <h1>Mock Response: {this.state.mockResponse.name}</h1>
                        </div>
                        <div className="menu" align="right">
                            <a className="btn btn-danger demo-button-disabled menu-button" href="/web/project/import"><i
                                className="fas fa-cloud-upload-alt"/> <span>Delete response</span></a>
                        </div>
                    </div>
                    <div className="content-summary">
                        <table className="content-summary-table">
                            <tr>
                                <td className="column1"><label>Name</label></td>
                                <td className="column2"><input></input></td>
                            </tr>
                            <tr>
                                <td className="column1"><label>HTTP Status code</label></td>
                                <td className="column2"><input></input></td>
                            </tr>
                            <tr>
                                <td className="column1"><label>Status</label></td>
                                <td className="column2"><input></input></td>
                            </tr>
                            <tr>
                                <td className="column1"><label>Use Expression</label></td>
                                <td className="column2"><input></input></td>
                            </tr>
                        </table>
                    </div>
                    <div>
                        <Tabs defaultActiveKey="body">
                            <Tab eventKey="body" title="Body">
                                <textarea className="form-control" id="body" rows="3"/>
                            </Tab>
                            <Tab eventKey="headers" title="Headers">
                                <div className="table-result">
                                    <ToolkitProvider bootstrap4
                                                     columns={ this.columns}
                                                     data={this.state.mockResponse.httpHeaders}
                                                     keyField="name"
                                                     search>
                                        {
                                            (props) => (
                                                <div>
                                                    <div>
                                                        <SearchBar {...props.searchProps} className={"table-filter-field"}/>
                                                    </div>
                                                    <BootstrapTable {...props.baseProps} bootstrap4
                                                                    data={this.state.mockResponse.httpHeaders} columns={this.columns}
                                                                    defaultSorted={this.defaultSort} keyField='name' hover
                                                                    selectRow={this.selectRow}/>
                                                </div>
                                            )}
                                    </ToolkitProvider>
                                </div>
                            </Tab>
                            <Tab eventKey="xpath" title="XPath">
                                <div className="table-result">
                                    <ToolkitProvider bootstrap4
                                                     data={this.state.mockResponse.httpHeaders}
                                                     keyField="name"
                                                     search>
                                        {
                                            (props) => (
                                                <div>
                                                    <div>
                                                        <SearchBar {...props.searchProps} className={"table-filter-field"}/>
                                                    </div>
                                                    <BootstrapTable {...props.baseProps} bootstrap4
                                                                    data={this.state.mockResponse.httpHeaders} columns={this.columns}
                                                                    defaultSorted={this.defaultSort} keyField='name' hover
                                                                    selectRow={this.selectRow}/>
                                                </div>
                                            )}
                                    </ToolkitProvider>
                                </div>
                            </Tab>
                        </Tabs>
                    </div>
                </section>
            </div>
        )
    }

}

export default SoapMockResponse