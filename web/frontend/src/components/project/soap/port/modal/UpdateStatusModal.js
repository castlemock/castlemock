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
import ToolkitProvider from "react-bootstrap-table2-toolkit";
import BootstrapTable from "react-bootstrap-table-next";
import PaginationFactory from "react-bootstrap-table2-paginator";
import axios from "axios";
import validateErrorResponse from "../../../../../utility/HttpResponseValidator";
import {operationStatusFormatter} from "../../utility/SoapFormatter"

class UpdateStatusModal extends PureComponent {

    constructor(props) {
        super(props);
        this.onUpdateStatusClick = this.onUpdateStatusClick.bind(this);
        this.setUpdatePortStatus = this.setUpdatePortStatus.bind(this);

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

        this.state = {
            updateStatus: "MOCKED"
        };
    }

    setUpdatePortStatus(status) {
        this.setState({ updateStatus: status });
    }

    onUpdateStatusClick() {
        let operationIds = this.props.selectedOperations.map(operation => operation.id);
        axios
            .put("/castlemock/api/rest/soap/project/" + this.props.projectId + "/port/" + this.props.portId + "/operation/status", {
                projectId: this.props.projectId,
                portId: this.props.portId,
                operationIds: operationIds,
                status: this.state.updateStatus
            })
            .then(response => {
                this.props.getPort();
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    render() {
        return (
            <div className="modal fade" id="updateStatusModal" tabIndex="-1" role="dialog"
                 aria-labelledby="updateStatusModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="updateStatusModalLabel">Update status?</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <p>Do you want update the status for the following operations?</p>
                            <div className="table-result">
                                <ToolkitProvider bootstrap4
                                                 columns={ this.columns}
                                                 data={ this.props.selectedOperations }
                                                 keyField="id">
                                    {
                                        (props) => (
                                            <div>
                                                <BootstrapTable { ...props.baseProps } bootstrap4 data={this.props.selectedOperations} columns={this.columns}
                                                                defaultSorted={ this.defaultSort } keyField='id' hover
                                                                striped
                                                                pagination={ PaginationFactory({hideSizePerPage: true}) }/>
                                            </div>
                                        )}
                                </ToolkitProvider>
                            </div>
                            <div className="form-group row">
                                <label className="col-sm-2 col-form-label">Status</label>
                                <div className="col-sm-10">
                                    <select id="inputStatus" className="form-control" defaultValue="MOCKED" onChange={event => this.setUpdatePortStatus(event.target.value)}>
                                        <option value={"MOCKED"}>{operationStatusFormatter("MOCKED")}</option>
                                        <option value={"DISABLED"}>{operationStatusFormatter("DISABLED")}</option>
                                        <option value={"FORWARDED"}>{operationStatusFormatter("FORWARDED")}</option>
                                        <option value={"RECORDING"}>{operationStatusFormatter("RECORDING")}</option>
                                        <option value={"RECORD_ONCE"}>{operationStatusFormatter("RECORD_ONCE")}</option>
                                        <option value={"ECHO"}>{operationStatusFormatter("ECHO")}</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-primary" data-dismiss="modal" onClick={this.onUpdateStatusClick}>Update</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default UpdateStatusModal;