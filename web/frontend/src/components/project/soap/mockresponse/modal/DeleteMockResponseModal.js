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
import { withRouter } from "react-router";
import validateErrorResponse from "../../../../../utility/HttpResponseValidator";

class DeleteMockResponseModal extends PureComponent {

    constructor(props) {
        super(props);
        this.onDeleteMockResponseClick = this.onDeleteMockResponseClick.bind(this);
    }

    onDeleteMockResponseClick() {
        axios
            .delete("/castlemock/api/rest/soap/project/" + this.props.projectId + "/port/" + this.props.portId + "/operation/" + this.props.operationId + "/mockresponse/" + this.props.mockResponseId)
            .then(response => {
                this.props.history.push("/castlemock/web/soap/project/" + this.props.projectId + "/port/" + this.props.portId + "/operation/" + this.props.operationId);
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    render() {
        return (
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
        );
    }
}

export default withRouter(DeleteMockResponseModal);