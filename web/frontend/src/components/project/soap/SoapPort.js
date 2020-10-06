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

class SoapPort extends PureComponent {

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
            dataField: 'httpMethod',
            text: 'Method',
            sort: true
        }, {
            dataField: 'responseStrategy',
            text: 'Response strategy',
            sort: true
        }, {
            dataField: 'status',
            text: 'Status',
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
            port: {
                operations: []
            }
        };

        this.getPort(this.state.projectId, this.state.portId);
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
                <Link to={"/beta/web/soap/project/" + this.state.projectId + "/port/" + this.state.portId + "/operation/" + row.id}>{cell}</Link>
            </div>
        )
    }

    getPort(projectId, portId) {
        axios
            .get("/api/rest/soap/project/" + projectId + "/port/" + portId)
            .then(response => {
                this.setState({
                    port: response.data,
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
                                <li className="breadcrumb-item">{this.state.port.name}</li>
                            </ol>
                        </nav>
                    </div>
                    <div className="content-top">
                        <div className="title">
                            <h1>Port: {this.state.port.name}</h1>
                        </div>
                    </div>
                    <div className="content-summary">
                        <dl className="row">
                            <dt className="col-sm-3">URI</dt>
                            <dd className="col-sm-9">{this.state.port.uri}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3">Address</dt>
                            <dd className="col-sm-9">http://192.168.1.87:8080/castlemock/mock/soap/project/kfQYQH/{this.state.port.uri}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3">WSDL</dt>
                            <dd className="col-sm-9">http://192.168.1.87:8080/castlemock/mock/soap/project/kfQYQH/{this.state.port.uri}?wsdl</dd>
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
                                                            pagination={ PaginationFactory(PaginationFactory()) }/>
                                        </div>
                                    )}
                            </ToolkitProvider>
                            <div className="panel-buttons">
                                <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal" data-target="#updateProjectModal"><i className="fas fa-plus-circle"/> <span>Update endpoint</span></button>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        )
    }

}

export default SoapPort