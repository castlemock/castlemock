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
import DeleteUserModal from "./modal/DeleteUserModal";
import UpdateUserModal from "./modal/UpdateUserModal";
import {userRoleFormatter, userStatusFormatter} from "./utility/UserFormatter";
import {faUserEdit, faUserMinus} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

class User extends PureComponent {

    constructor(props) {
        super(props);
        this.getUser = this.getUser.bind(this);


        this.state = {
            userId: this.props.match.params.userId,
            user: {}
        };

        this.getUser();
    }

    getUser() {
        axios
            .get(process.env.PUBLIC_URL + "/api/rest/core/user/" + this.state.userId)
            .then(response => {
                this.setState({
                    user: response.data
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
                            <button className="btn btn-success demo-button-disabled menu-button" data-toggle="modal" data-target="#updateUserModal"><FontAwesomeIcon icon={faUserEdit} className="button-icon"/><span>Update user</span></button>
                            <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal" data-target="#deleteUserModal"><FontAwesomeIcon icon={faUserMinus} className="button-icon"/><span>Delete user</span></button>
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

                <DeleteUserModal userId={this.state.userId} user={this.state.user}/>
                <UpdateUserModal userId={this.state.userId} user={this.state.user} getUser={this.getUser}/>
            </div>
        )
    }

}


export default User;