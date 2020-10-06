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

class User extends PureComponent {

    constructor(props) {
        super(props);
        this.onUpdateUserClick = this.onUpdateUserClick.bind(this);
        this.onDeleteUserClick = this.onDeleteUserClick.bind(this);
        this.setUpdateUserUserName = this.setUpdateUserUserName.bind(this);
        this.setUpdateUserEmail = this.setUpdateUserEmail.bind(this);
        this.setUpdateUserRole = this.setUpdateUserRole.bind(this);
        this.setUpdateUserStatus = this.setUpdateUserStatus.bind(this);

        this.state = {
            userId: this.props.match.params.userId,
            user: {},
            updateUser: {
                userName: "",
                email: "",
                role: "READER",
                status: "ACTIVE"
            }
        };

        this.getUser(this.state.userId)
    }

    setUpdateUserUserName(username) {
        this.setState({
            updateUser: {
                userName: username
            }
        });
    }

    setUpdateUserEmail(email) {
        this.setState({
            updateUser: {
                email: email
            }
        });
    }

    setUpdateUserRole(role) {
        this.setState({
            updateUser: {
                role: role
            }
        });
    }

    setUpdateUserStatus(status) {
        this.setState({
            status: status
        });
    }

    onUpdateUserClick() {
        axios
            .post("/api/rest/core/user", {
                name: this.state.updateUserUserName,
                description: this.state.updateUserUserName,
                userType: this.state.updateUserType
            })
            .then(response => {
                this.props.history.push("/beta/web/" + response.data.typeIdentifier.typeUrl + "/user/" + response.data.id)
            })
            .catch(error => {
                this.props.validateErrorResponse(error);
            });
    }

    onDeleteUserClick() {

    }

    getUser(userId) {
        axios
            .get("/api/rest/core/user/" + userId)
            .then(response => {
                this.setState({
                    user: response.data,
                    updateUser: {
                        userName: response.data.username,
                        email: response.data.email,
                        role: response.data.role,
                        status: response.data.status
                    },
                });
            })
            .catch(error => {
                this.props.validateErrorResponse(error);
            });
    }

    render() {
        return (
            <div>
                <section>
                    <div className="navigation">
                    </div>
                    <div className="content-top">
                        <div className="title">
                            <h1>User</h1>
                        </div>
                        <div className="menu">
                            <button className="btn btn-success demo-button-disabled menu-button" data-toggle="modal" data-target="#updateUserModal"><i className="fas fa-plus-circle"/> <span>Update user</span></button>
                            <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal" data-target="#deleteUserModal"><i className="fas fa-plus-circle"/> <span>Delete user</span></button>
                        </div>
                    </div>
                    <div className="content-summary">
                        <dl className="row">
                            <dt className="col-sm-3">Username</dt>
                            <dd className="col-sm-9">{this.state.user.username}</dd>
                            <dt className="col-sm-3">Full name</dt>
                            <dd className="col-sm-9">{this.state.user.fullName}</dd>
                            <dt className="col-sm-3">Email</dt>
                            <dd className="col-sm-9">{this.state.user.email}</dd>
                            <dt className="col-sm-3">Status</dt>
                            <dd className="col-sm-9">{this.state.user.status}</dd>
                            <dt className="col-sm-3">Role</dt>
                            <dd className="col-sm-9">{this.state.user.role}</dd>
                            <dt className="col-sm-3">Created</dt>
                            <dd className="col-sm-9">{this.state.user.created}</dd>
                            <dt className="col-sm-3">Updated</dt>
                            <dd className="col-sm-9">{this.state.user.updated}</dd>
                        </dl>
                    </div>
                </section>

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
                                            <input className="form-control" type="text" name="updateUserUserName" id="updateUserUserName" value={this.state.user.username} onChange={event => this.setUpdateUserUserName(event.target.value)}/>
                                        </div>
                                    </div>
                                    <div className="form-group row">
                                        <label htmlFor="updateUserEmail" className="col-sm-2 col-form-label">Email</label>
                                        <div className="col-sm-10">
                                            <input className="form-control" type="text" name="updateUserEmail" id="updateUserEmail" value={this.state.user.email} onChange={event => this.setUpdateUserEmail(event.target.value)}/>
                                        </div>
                                    </div>
                                    <div className="form-group row">
                                        <label htmlFor="inputState" className="col-sm-2 col-form-label">Role</label>
                                        <div className="col-sm-10">
                                            <select id="inputState" className="form-control" value={this.state.user.role} onChange={event => this.setUpdateUserRole(event.target.value)} defaultValue={"READER"}>
                                                <option>READER</option>
                                                <option>MODIFIER</option>
                                                <option>ADMIN</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div className="form-group row">
                                        <label htmlFor="inputState" className="col-sm-2 col-form-label">Role</label>
                                        <div className="col-sm-10">
                                            <select id="inputState" className="form-control" value={this.state.user.status} onChange={event => this.setUpdateUserStatus(event.target.value)} defaultValue={"ACTIVE"}>
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

                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-danger" data-dismiss="modal" onClick={this.onDeleteUserClick}>Delete</button>
                            </div>
                        </div>
                    </div>
                </div>


            </div>
        )
    }

}

export default User