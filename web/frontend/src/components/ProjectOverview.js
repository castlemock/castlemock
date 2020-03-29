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
import BootstrapTable from 'react-bootstrap-table-next';
import ToolkitProvider, {Search} from 'react-bootstrap-table2-toolkit';
import axios from "axios";
const { SearchBar } = Search;

class ProjectOverview extends PureComponent {

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
            dataField: 'typeIdentifier.type',
            text: 'Type',
            sort: true
        }, {
            dataField: 'description',
            text: 'Description',
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
            projects: []
        };

        this.getProjects()
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
            <a href={"/beta/web/" + row.typeIdentifier.typeUrl + "/project/" + row.id}>{cell}</a>
        )
    }


    getProjects() {
        axios
            .get("/api/rest/core/project")
            .then(response => {
                this.setState({
                    projects: response.data,
                });
            })
            .catch(error => {
            });
    }


    render() {
        return (
            <div>
                <section>
                    <div class="content-top">
                        <div className="title">
                            <h1>Overview</h1>
                        </div>
                        <div className="menu">
                            <button className="btn btn-success demo-button-disabled menu-button"  data-toggle="modal" data-target="#newProjectModal"><i class="fas fa-plus-circle"/> <span>New project</span></button>
                            <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal" data-target="#newProjectModal"><i class="fas fa-cloud-upload-alt"/> <span>Import project</span></button>
                        </div>
                    </div>
                    <div className="panel panel-primary table-panel">
                        <div className="panel-heading table-panel-heading">
                            <h3 className="panel-title">Projects</h3>
                        </div>
                        <div class="table-responsive">
                            <ToolkitProvider bootstrap4
                                             data={ this.projects }
                                             keyField="id"
                                             search>
                                {
                                    (props) => (
                                        <div>
                                            <div>
                                                <SearchBar { ...props.searchProps } className={"table-filter-field"} />
                                            </div>
                                            <BootstrapTable { ...props.baseProps } bootstrap4 data={this.state.projects} columns={this.columns}
                                                            defaultSorted={ this.defaultSort } keyField='id' hover
                                                            selectRow={ this.selectRow }/>
                                            </div>
                                    )}
                            </ToolkitProvider>
                        </div>
                        <div className="table-responsive">
                            <div className="panel-buttons">
                                <button className="btn btn-primary panel-button" type="submit" name="action" value="export"><i
                                    className="fas fa-cloud-download-alt"/> <span>Export projects</span></button>
                                <button className="btn btn-danger demo-button-disabled panel-button" type="submit" name="action"
                                        value="delete"><i className="fas fa-trash"/> <span>Delete projects</span>
                                </button>
                            </div>
                        </div>
                    </div>
                 </section>

                <div className="modal fade" id="newProjectModal" tabIndex="-1" role="dialog"
                     aria-labelledby="newProjectModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="privacyPolicyModalLabel">New project</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

}

export default ProjectOverview