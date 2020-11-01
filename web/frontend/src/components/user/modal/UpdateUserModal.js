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
import validateErrorResponse from "../../../utility/HttpResponseValidator";

class UpdateUserModal extends PureComponent {

    constructor(props) {
        super(props);
        this.setUpdateUserUserName = this.setUpdateUserUserName.bind(this);
        this.setUpdateUserEmail = this.setUpdateUserEmail.bind(this);
        this.setUpdateUserPassword = this.setUpdateUserPassword.bind(this);
        this.setUpdateUserRole = this.setUpdateUserRole.bind(this);
        this.setUpdateUserStatus = this.setUpdateUserStatus.bind(this);
        this.onUpdateUserClick = this.onUpdateUserClick.bind(this);

        this.state = {
            updateUser: {
                username: "",
                email: "",
                password: "",
                role: "READER",
                status: "ACTIVE"
            }
        };
    }

    setUpdateUserUserName(username) {
        this.setState({
            updateUser: {
                ...this.state.updateUser,
                username: username
            }
        });
    }

    setUpdateUserEmail(email) {
        this.setState({
            updateUser: {
                ...this.state.updateUser,
                email: email
            }
        });
    }

    setUpdateUserPassword(password) {
        this.setState({
            updateUser: {
                ...this.state.updateUser,
                password: password
            }
        });
    }

    setUpdateUserRole(role) {
        this.setState({
            updateUser: {
                ...this.state.updateUser,
                role: role
            }
        });
    }

    setUpdateUserStatus(status) {
        this.setState({
            updateUser: {
                ...this.state.updateUser,
                status: status
            }
        });
    }

    onUpdateUserClick() {
        axios
            .put("/castlemock/api/rest/core/user/" + this.props.userId, this.state.updateUser)
            .then(response => {
                this.props.getUser();
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }


    render() {
        return (
            <div className="modal fade" id="updateUserModal" tabIndex="-1" role="dialog"
                 aria-labelledby="updateUserModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="updateUserModalLabel">Update user</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <form>
                                <div className="form-group row">
                                    <label htmlFor="updateUserUserName" className="col-sm-2 col-form-label">Name</label>
                                    <div className="col-sm-10">
                                        <input className="form-control" type="text" name="updateUserUserName" id="updateUserUserName" defaultValue={this.props.user.username} onChange={event => this.setUpdateUserUserName(event.target.value)}/>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label htmlFor="updateUserEmail" className="col-sm-2 col-form-label">Email</label>
                                    <div className="col-sm-10">
                                        <input className="form-control" type="text" name="updateUserEmail" id="updateUserEmail" onChange={event => this.setUpdateUserEmail(event.target.value)}/>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label htmlFor="newUserPassword" className="col-sm-2 col-form-label">Password</label>
                                    <div className="col-sm-10">
                                        <input className="form-control" type="text" name="updateUserPassword" id="updateUserPassword"  defaultValue={this.props.user.password} onChange={event => this.setUpdateUserPassword(event.target.value)}/>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label htmlFor="inputState" className="col-sm-2 col-form-label">Role</label>
                                    <div className="col-sm-10">
                                        <select id="inputState" className="form-control" defaultValue={this.props.user.role} onChange={event => this.setUpdateUserRole(event.target.value)}>
                                            <option>READER</option>
                                            <option>MODIFIER</option>
                                            <option>ADMIN</option>
                                        </select>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label htmlFor="inputState" className="col-sm-2 col-form-label">Status</label>
                                    <div className="col-sm-10">
                                        <select id="inputState" className="form-control" value={this.props.user.status} onChange={event => this.setUpdateUserStatus(event.target.value)} defaultValue={"ACTIVE"}>
                                            <option>ACTIVE</option>
                                            <option>INACTIVE</option>
                                        </select>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-success" data-dismiss="modal" onClick={this.onUpdateUserClick}>Update</button>
                        </div>
                    </div>
                </div>
            </div>


        );
    }
}

export default UpdateUserModal;