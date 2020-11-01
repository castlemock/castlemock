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
import { withRouter } from "react-router";

class NewUserModal extends PureComponent {

    constructor(props) {
        super(props);
        this.setNewUserName = this.setNewUserName.bind(this);
        this.setNewEmail = this.setNewEmail.bind(this);
        this.setNewRole = this.setNewRole.bind(this);
        this.setNewStatus = this.setNewStatus.bind(this);
        this.setNewPassword = this.setNewPassword.bind(this);
        this.onCreateUserClick = this.onCreateUserClick.bind(this);

        this.state = {
            newUser: {
                username: "",
                email: "",
                password: "",
                role: "READER",
                status: "ACTIVE"
            }
        };
    }

    setNewUserName(username) {
        this.setState({
            newUser: {
                ...this.state.newUser,
                username: username
            } });
    }

    setNewEmail(email) {
        this.setState({
            newUser: {
                ...this.state.newUser,
                email: email
            }
        });
    }

    setNewPassword(password) {
        this.setState({
            newUser: {
                ...this.state.newUser,
                password: password
            }
        });
    }

    setNewRole(role) {
        this.setState({
            newUser: {
                ...this.state.newUser,
                role: role
            }
        });
    }

    setNewStatus(status) {
        this.setState({
            newUser: {
                ...this.state.newUser,
                status: status
            }
        });
    }


    onCreateUserClick() {
        axios
            .post("/castlemock/api/rest/core/user", this.state.newUser)
            .then(response => {
                this.props.history.push("/castlemock/web/user/" + response.data.id)
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    render() {
        return (
            <div className="modal fade" id="newUserModal" tabIndex="-1" role="dialog"
                 aria-labelledby="newUserModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="newUserModalLabel">New user</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <form>
                                <div className="form-group row">
                                    <label htmlFor="newUserUserName" className="col-sm-2 col-form-label">Name</label>
                                    <div className="col-sm-10">
                                        <input className="form-control" type="text" name="newUserUserName" id="newUserUserName" onChange={event => this.setNewUserName(event.target.value)}/>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label htmlFor="newUserEmail" className="col-sm-2 col-form-label">Email</label>
                                    <div className="col-sm-10">
                                        <input className="form-control" type="text" name="newUserEmail" id="newUserEmail" onChange={event => this.setNewEmail(event.target.value)}/>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label htmlFor="newUserPassword" className="col-sm-2 col-form-label">Password</label>
                                    <div className="col-sm-10">
                                        <input className="form-control" type="text" name="newUserPassword" id="newUserPassword" onChange={event => this.setNewPassword(event.target.value)}/>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label htmlFor="inputState" className="col-sm-2 col-form-label">Role</label>
                                    <div className="col-sm-10">
                                        <select id="inputState" className="form-control" onChange={event => this.setNewRole(event.target.value)} defaultValue={"READER"}>
                                            <option>READER</option>
                                            <option>MODIFIER</option>
                                            <option>ADMIN</option>
                                        </select>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label htmlFor="inputState" className="col-sm-2 col-form-label">Status</label>
                                    <div className="col-sm-10">
                                        <select id="inputState" className="form-control" onChange={event => this.setNewStatus(event.target.value)} defaultValue={"ACTIVE"}>
                                            <option>ACTIVE</option>
                                            <option>INACTIVE</option>
                                        </select>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-success" data-dismiss="modal" onClick={this.onCreateUserClick}>Create</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(NewUserModal);