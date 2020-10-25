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
import { withRouter } from "react-router";
import axios from "axios";
import validateErrorResponse from "../../../../../utility/HttpResponseValidator";


class DeleteResourceModal extends PureComponent {

    constructor(props) {
        super(props);
        this.onDeleteResourceClick = this.onDeleteResourceClick.bind(this);
    }

    onDeleteResourceClick() {
        axios
            .delete("/api/rest/rest/project/" + this.props.projectId + "/application/" + this.props.applicationId + "/resource/" + this.props.resourceId)
            .then(response => {
                this.props.history.push("/web/rest/project/" + this.props.projectId + "/application/" + this.props.applicationId);
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    render() {
        return (
            <div className="modal fade" id="deleteResourceModal" tabIndex="-1" role="dialog"
                 aria-labelledby="deleteResourceModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="deleteResourceModalLabel">Delete the resource?</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <p>Do you wanna delete the resource?</p>
                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-danger" data-dismiss="modal" onClick={this.onDeleteResourceClick}>Delete</button>
                        </div>
                    </div>
                </div>
            </div>

        );
    }
}

export default withRouter(DeleteResourceModal);