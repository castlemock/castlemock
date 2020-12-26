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
import validateErrorResponse from "../../utility/HttpResponseValidator";
import {userStatusFormatter, userRoleFormatter} from "../user/utility/UserFormatter";
import preventEnterEvent from "../../utility/KeyboardUtility";
import {faUserEdit} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

class Profile extends PureComponent {

    constructor(props) {
        super(props);
        this.getUser = this.getUser.bind(this);
        this.onUpdateUserClick = this.onUpdateUserClick.bind(this);
        this.setUpdateUserUserName = this.setUpdateUserUserName.bind(this);
        this.setUpdateUserEmail = this.setUpdateUserEmail.bind(this);
        this.setUpdateUserPassword = this.setUpdateUserPassword.bind(this);
        this.setUpdateUserFullName = this.setUpdateUserFullName.bind(this);

        this.state = {
            userId: this.props.match.params.userId,
            user: {},
            updateUser: {
                username: "",
                email: "",
                fullName: "",
                password: ""
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

    setUpdateUserFullName(fullName) {
        this.setState({
            updateUser: {
                ...this.state.updateUser,
                fullName: fullName
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

    onUpdateUserClick() {
        axios
            .put(process.env.PUBLIC_URL + "/api/rest/core/profile", this.state.updateUser)
            .then(response => {
                this.getUser();
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    getUser() {
        axios
            .get(process.env.PUBLIC_URL + "/api/rest/core/profile")
            .then(response => {
                this.setState({
                    user: response.data,
                    updateUser: {
                        ...response.data
                    },
                });
            })
            .catch(error => {
                validateErrorResponse(error)
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
                            <button className="btn btn-success demo-button-disabled menu-button" data-toggle="modal" data-target="#updateUserModal"><FontAwesomeIcon icon={faUserEdit} className="button-icon"/><span>Update profile</span></button>
                        </div>
                    </div>
                    <div className="content-summary">
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Username</dt>
                            <dd className="col-sm-9">{this.state.user.username}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Name</dt>
                            <dd className="col-sm-9">{this.state.user.fullName}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Email</dt>
                            <dd className="col-sm-9">{this.state.user.email}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Status</dt>
                            <dd className="col-sm-9">{userStatusFormatter(this.state.user.status)}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Role</dt>
                            <dd className="col-sm-9">{userRoleFormatter(this.state.user.role)}</dd>
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
                                        <label htmlFor="updateUserUserName" className="col-sm-2 col-form-label">Username</label>
                                        <div className="col-sm-10">
                                            <input className="form-control" type="text" defaultValue={this.state.user.username} onChange={event => this.setUpdateUserUserName(event.target.value)} onKeyDown={preventEnterEvent}/>
                                        </div>
                                    </div>
                                    <div className="form-group row">
                                        <label htmlFor="updateUserEmail" className="col-sm-2 col-form-label">Name</label>
                                        <div className="col-sm-10">
                                            <input className="form-control" type="text" defaultValue={this.state.user.fullName} onChange={event => this.setUpdateUserFullName(event.target.value)} onKeyDown={preventEnterEvent}/>
                                        </div>
                                    </div>
                                    <div className="form-group row">
                                        <label htmlFor="updateUserEmail" className="col-sm-2 col-form-label">Email</label>
                                        <div className="col-sm-10">
                                            <input className="form-control" type="text" defaultValue={this.state.user.email} onChange={event => this.setUpdateUserEmail(event.target.value)} onKeyDown={preventEnterEvent}/>
                                        </div>
                                    </div>
                                    <div className="form-group row">
                                        <label htmlFor="newUserPassword" className="col-sm-2 col-form-label">Password</label>
                                        <div className="col-sm-10">
                                            <input className="form-control" type="password" defaultValue={this.state.user.password} onChange={event => this.setUpdateUserPassword(event.target.value)} onKeyDown={preventEnterEvent}/>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-success" data-dismiss="modal" onClick={this.onUpdateUserClick}><FontAwesomeIcon icon={faUserEdit} className="button-icon"/>Update</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

}

export default Profile;