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
import validateErrorResponse from "../../../utility/HttpResponseValidator";


class DeleteUserModal extends PureComponent {

    constructor(props) {
        super(props);
        this.onDeleteUserClick = this.onDeleteUserClick.bind(this);

    }

    onDeleteUserClick() {
        axios
            .delete("/api/rest/core/user/" + this.props.userId)
            .then(response => {
                this.props.history.push("/web/user/");
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    render() {
        return (
            <div className="modal fade" id="deleteUserModal" tabIndex="-1" role="dialog"
                 aria-labelledby="deleteUserModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="deleteUserModalLabel">Delete user</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <p>Do you want to delete {this.props.user.username}?</p>
                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-danger" data-dismiss="modal" onClick={this.onDeleteUserClick}>Delete</button>
                        </div>
                    </div>
                </div>
            </div>

        );
    }
}

export default withRouter(DeleteUserModal);