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
import axios from "axios";
import validateErrorResponse from "../../../../../utility/HttpResponseValidator";
import ToolkitProvider from "react-bootstrap-table2-toolkit";
import BootstrapTable from "react-bootstrap-table-next";
import PaginationFactory from "react-bootstrap-table2-paginator";


class DeleteMockResponsesModal extends PureComponent {

    constructor(props) {
        super(props);
        this.onDeleteMockResponsesClick = this.onDeleteMockResponsesClick.bind(this);

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

    onDeleteMockResponsesClick() {
        this.props.selectedMockResponses.forEach(mockResponse => {
            axios
                .delete(process.env.PUBLIC_URL + "/api/rest/soap/project/" + this.props.projectId + "/port/" +
                    this.props.portId + "/operation/" + this.props.operationId + "/mockresponse/" + mockResponse.id)
                .then(response => {
                    this.props.getOperation();
                })
                .catch(error => {
                    validateErrorResponse(error)
                });
        })
    }

    render() {
        return (
            <div className="modal fade" id="deleteMockResponsesModal" tabIndex="-1" role="dialog"
                 aria-labelledby="deleteMockResponsesModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="deleteMockResponsesModalLabel">Delete mockResponses?</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <p>Do you want delete the following mockResponses?</p>
                            <div className="table-result">
                                <ToolkitProvider bootstrap4
                                                 columns={ this.columns}
                                                 data={ this.props.selectedMockResponses }
                                                 keyField="id">
                                    {
                                        (props) => (
                                            <div>
                                                <BootstrapTable { ...props.baseProps } bootstrap4 data={this.props.selectedMockResponses} columns={this.columns}
                                                                defaultSorted={ this.defaultSort } keyField='id' hover
                                                                striped
                                                                pagination={ PaginationFactory({hideSizePerPage: true}) }/>
                                            </div>
                                        )}
                                </ToolkitProvider>
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-danger" data-dismiss="modal" onClick={this.onDeleteMockResponsesClick}>Delete</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default DeleteMockResponsesModal;