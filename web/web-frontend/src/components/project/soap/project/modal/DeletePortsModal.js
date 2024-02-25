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

import React, {PureComponent} from "react";
import DataTable from "../../../../utility/DataTable";
import axios from "axios";
import validateErrorResponse from "../../../../../utility/HttpResponseValidator";
import {faTrash} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

class DeletePortsModal extends PureComponent {

    constructor(props) {
        super(props);
        this.onDeletePortClick = this.onDeletePortClick.bind(this);

        this.columns = [{
            dataField: 'id',
            text: 'id',
            hidden: true
        }, {
            dataField: 'name',
            text: 'Name',
            sort: true
        }];

        this.defaultSort = [{
            dataField: 'name',
            order: 'asc'
        }];
    }


    onDeletePortClick() {
        this.props.selectedPorts.forEach(port => {
            axios
                .delete(process.env.PUBLIC_URL + "/api/rest/soap/project/" + this.props.projectId + "/port/" + port.id)
                .then(response => {
                    this.props.getProject();
                })
                .catch(error => {
                    validateErrorResponse(error)
                });
        });
    }

    render() {
        return (
            <div className="modal fade" id="deletePortsModal" tabIndex="-1" role="dialog"
                 aria-labelledby="deletePortsModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="deletePortsModalLabel">Delete ports?</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <p>Do you want delete the following ports?</p>
                            <div className="table-result">
                                <DataTable
                                    columns={this.columns}
                                    data={this.props.selectedPorts}
                                    keyField="id"
                                    defaultSort={this.defaultSort}
                                    pagination={{ hideSizePerPage: true }}
                                ></DataTable>
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-danger" data-dismiss="modal" onClick={this.onDeletePortClick}><FontAwesomeIcon icon={faTrash} className="button-icon"/>Delete</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default DeletePortsModal;