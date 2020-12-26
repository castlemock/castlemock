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
import { withRouter } from "react-router";
import {faTrash} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

class DeleteProjectModal extends PureComponent {

    constructor(props) {
        super(props);
        this.onDeleteProjectClick = this.onDeleteProjectClick.bind(this);
    }

    onDeleteProjectClick() {
        axios
            .delete(process.env.PUBLIC_URL + "/api/rest/core/project/rest/" + this.props.projectId)
            .then(response => {
                this.props.history.push("/web");
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    render() {
        return (
            <div className="modal fade" id="deleteProjectModal" tabIndex="-1" role="dialog"
                 aria-labelledby="deleteProjectModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="deleteProjectModalLabel">Delete the project?</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <p>Do you wanna delete the project?</p>
                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-danger" data-dismiss="modal" onClick={this.onDeleteProjectClick}><FontAwesomeIcon icon={faTrash} className="button-icon"/>Delete</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(DeleteProjectModal);