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
import axios from "axios";

class RestProject extends PureComponent {

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
            projectId: 'wMn1lm',
            project: {
                applications: []
            }
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
            <a href={"/beta/web/rest/project/" + this.state.projectId + "/application/" + row.id}>{cell}</a>
        )
    }

    getProject(projectId) {
        axios
            .get("/api/rest/core/project/rest/" + projectId)
            .then(response => {
                this.setState({
                    project: response.data,
                });
            })
            .catch(error => {
            });
    }


    render() {
        return (
            <div>
                <section>
                    <div className="content-top">
                        <div className="title">
                            <h1>Project: {this.state.project.name}</h1>
                        </div>
                        <div className="menu" align="right">
                            <a className="btn btn-success demo-button-disabled menu-button" href="/web/project/create"><i
                                className="fas fa-plus-circle"/> <span>Update project</span></a>
                            <a className="btn btn-primary demo-button-disabled menu-button" href="/web/project/import"><i
                                className="fas fa-cloud-upload-alt"/> <span>Create application</span></a>
                            <a className="btn btn-primary demo-button-disabled menu-button" href="/web/project/import"><i
                                className="fas fa-cloud-upload-alt"/> <span>Upload</span></a>
                            <a className="btn btn-primary demo-button-disabled menu-button" href="/web/project/import"><i
                                className="fas fa-cloud-upload-alt"/> <span>Export project</span></a>
                            <a className="btn btn-primary demo-button-disabled menu-button" href="/web/project/import"><i
                                className="fas fa-cloud-upload-alt"/> <span>Delete project</span></a>
                        </div>
                    </div>
                    <div className="panel panel-primary table-panel">
                        <div className="panel-heading table-panel-heading">
                            <h3 className="panel-title">Applications</h3>
                        </div>
                        <div className="table-responsive">
                            <div className="panel-buttons">
                                <button className="btn btn-primary panel-button" type="submit" name="action" value="export"><i
                                    className="fas fa-cloud-download-alt"/> <span>Update</span></button>
                                <button className="btn btn-danger demo-button-disabled panel-button" type="submit" name="action"
                                        value="delete"><i className="fas fa-trash"/> <span>Update endpoint</span>
                                </button>
                                <button className="btn btn-danger demo-button-disabled panel-button" type="submit" name="action"
                                        value="delete"><i className="fas fa-trash"/> <span>Delete application</span>
                                </button>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        )
    }

}

export default RestProject