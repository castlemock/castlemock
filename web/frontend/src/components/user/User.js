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
import {connect} from "react-redux";
import {setAuthenticationState} from "../../redux/Actions";
import validateErrorResponse from "../../utility/HttpResponseValidator";

class User extends PureComponent {

    constructor(props) {
        super(props);
        this.getUser = this.getUser.bind(this);
        this.onUpdateUserClick = this.onUpdateUserClick.bind(this);
        this.onDeleteUserClick = this.onDeleteUserClick.bind(this);
        this.setUpdateUserUserName = this.setUpdateUserUserName.bind(this);
        this.setUpdateUserEmail = this.setUpdateUserEmail.bind(this);
        this.setUpdateUserPassword = this.setUpdateUserPassword.bind(this);
        this.setUpdateUserRole = this.setUpdateUserRole.bind(this);
        this.setUpdateUserStatus = this.setUpdateUserStatus.bind(this);

        this.state = {
            userId: this.props.match.params.userId,
            user: {},
            updateUser: {
                username: "",
                email: "",
                password: "",
                role: "READER",
                status: "ACTIVE"
            }
        };

        this.getUser();
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
            .put("/api/rest/core/user/" + this.state.userId, this.state.updateUser)
            .then(response => {
                this.getUser();
            })
            .catch(error => {
                validateErrorResponse(error, this.props.setAuthenticationState)
            });
    }

    onDeleteUserClick() {
        axios
            .delete("/api/rest/core/user/" + this.state.userId)
            .then(response => {
                this.props.history.push("/web/user/");
            })
            .catch(error => {
                validateErrorResponse(error, this.props.setAuthenticationState)
            });
    }

    getUser() {
        axios
            .get("/api/rest/core/user/" + this.state.userId)
            .then(response => {
                this.setState({
                    user: response.data,
                    updateUser: {
                        username: response.data.username,
                        email: response.data.email,
                        role: response.data.role,
                        status: response.data.status
                    },
                });
            })
            .catch(error => {
                validateErrorResponse(error, this.props.setAuthenticationState)
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
                            <dt className="col-sm-2 content-title">Username</dt>
                            <dd className="col-sm-9">{this.state.user.username}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Full name</dt>
                            <dd className="col-sm-9">{this.state.user.fullName}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Email</dt>
                            <dd className="col-sm-9">{this.state.user.email}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Status</dt>
                            <dd className="col-sm-9">{this.state.user.status}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Role</dt>
                            <dd className="col-sm-9">{this.state.user.role}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Created</dt>
                            <dd className="col-sm-9">{new Date(this.state.user.created).toLocaleString()}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Updated</dt>
                            <dd className="col-sm-9">{new Date(this.state.user.updated).toLocaleString()}</dd>
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
                                            <input className="form-control" type="text" name="updateUserUserName" id="updateUserUserName" defaultValue={this.state.user.username} onChange={event => this.setUpdateUserUserName(event.target.value)}/>
                                        </div>
                                    </div>
                                    <div className="form-group row">
                                        <label htmlFor="updateUserEmail" className="col-sm-2 col-form-label">Email</label>
                                        <div className="col-sm-10">
                                            <input className="form-control" type="text" name="updateUserEmail" id="updateUserEmail" defaultValue={this.state.user.email} onChange={event => this.setUpdateUserEmail(event.target.value)}/>
                                        </div>
                                    </div>
                                    <div className="form-group row">
                                        <label htmlFor="newUserPassword" className="col-sm-2 col-form-label">Password</label>
                                        <div className="col-sm-10">
                                            <input className="form-control" type="text" name="updateUserPassword" id="updateUserPassword"  defaultValue={this.state.user.password} onChange={event => this.setUpdateUserPassword(event.target.value)}/>
                                        </div>
                                    </div>
                                    <div className="form-group row">
                                        <label htmlFor="inputState" className="col-sm-2 col-form-label">Role</label>
                                        <div className="col-sm-10">
                                            <select id="inputState" className="form-control" defaultValue={this.state.user.role} onChange={event => this.setUpdateUserRole(event.target.value)}>
                                                <option>READER</option>
                                                <option>MODIFIER</option>
                                                <option>ADMIN</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div className="form-group row">
                                        <label htmlFor="inputState" className="col-sm-2 col-form-label">Status</label>
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
                                <p>Do you want to delete {this.state.user.username}?</p>
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


export default connect(
    null,
    { setAuthenticationState }
)(User);