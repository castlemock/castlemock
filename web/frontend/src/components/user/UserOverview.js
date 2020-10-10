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
import BootstrapTable from 'react-bootstrap-table-next';
import ToolkitProvider, {Search} from 'react-bootstrap-table2-toolkit';
import PaginationFactory from "react-bootstrap-table2-paginator";
import axios from "axios";
import {Link} from "react-router-dom";
import {connect} from "react-redux";
import {setAuthenticationState} from "../../redux/Actions";
import validateErrorResponse from "../../utility/HttpResponseValidator";

const { SearchBar } = Search;

const SELECT = true;
const DESELECT = false;

class UserOverview extends PureComponent {

    constructor(props) {
        super(props);
        this.onRowSelect = this.onRowSelect.bind(this);
        this.onRowSelectAll = this.onRowSelectAll.bind(this);
        this.onCreateUserClick = this.onCreateUserClick.bind(this);
        this.onDeleteUsersClick = this.onDeleteUsersClick.bind(this);
        this.setNewUserName = this.setNewUserName.bind(this);
        this.setNewEmail = this.setNewEmail.bind(this);
        this.setNewRole = this.setNewRole.bind(this);
        this.setNewStatus = this.setNewStatus.bind(this);
        this.setNewPassword = this.setNewPassword.bind(this);
        this.userNameFormat = this.userNameFormat.bind(this);
        this.userDateFormat = this.userDateFormat.bind(this);

        this.columns = [{
            dataField: 'id',
            text: 'id',
            hidden: true
        }, {
            dataField: 'username',
            width: 20,
            maxWidth: 20,
            text: 'Username',
            sort: true,
            formatter: this.userNameFormat
        }, {
            dataField: 'email',
            text: 'Email',
            sort: true
        }, {
            dataField: 'role',
            text: 'Role',
            sort: true
        }, {
            dataField: 'status',
            text: 'Status',
            sort: true
        },{
            dataField: 'created',
            text: 'Created',
            sort: true,
            formatter: this.userDateFormat
        },{
            dataField: 'updated',
            text: 'Updated',
            sort: true,
            formatter: this.userDateFormat
        }];

        this.deleteColumns = [{
            dataField: 'id',
            text: 'id',
            hidden: true
        }, {
            dataField: 'username',
            width: 20,
            maxWidth: 20,
            text: 'Username',
            sort: true,
            formatter: this.userNameFormat
        }];

        this.selectRow = {
            mode: 'checkbox',
            onSelect: this.onRowSelect,
            onSelectAll: this.onRowSelectAll
        };

        this.defaultSort = [{
            dataField: 'name',
            order: 'asc'
        }];

        this.state = {
            users: [],
            selectedUsers: [],
            deleteUsersDisabled: true,
            newUser: {
                username: "",
                email: "",
                password: "",
                role: "READER",
                status: "ACTIVE"
            }
        };

        this.getUsers()
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

    userNameFormat(cell, row) {
        if(cell == null){
            return;
        }

        return (
            <div className="table-link">
                <Link to={"/web/user/" + row.id}>{cell}</Link>
            </div>
        )
    }

    userDateFormat(cell) {
        if(cell == null){
            return;
        }

        let date = new Date(cell).toLocaleString();
        return (
            <div>{date}</div>
        )
    }
    
    onCreateUserClick() {
        axios
            .post("/api/rest/core/user", this.state.newUser)
            .then(response => {
                this.props.history.push("/web/user/" + response.data.id)
            })
            .catch(error => {
                validateErrorResponse(error, this.props.setAuthenticationState)
            });
    }
    
    onDeleteUsersClick() {
        Array.from(this.state.selectedUsers).forEach(user => {
            axios
                .delete("/api/rest/core/user/" + user.id)
                .then(response => {
                    this.getUsers();
                })
                .catch(error => {
                    validateErrorResponse(error, this.props.setAuthenticationState)
                });
        });
    }

    onRowSelect(value, mode) {
        let users = this.state.selectedUsers.slice();
        let user = {
            id: value.id,
            username: value.username
        };
        if(mode === SELECT){
            users.push(user);
        } else if(mode === DESELECT){
            let index = users.indexOf(user);
            users.splice(index, 1);
        }
        this.setState({
            selectedUsers: users,
            exportUsersDisabled: users.length === 0,
            deleteUsersDisabled: users.length === 0
        });
    }

    onRowSelectAll(mode) {
        if(mode === SELECT){
            let users = [];
            this.state.users.forEach(value => {
                let user = {
                    id: value.id,
                    username: value.username
                };
                users.push(user);
            });
            this.setState({
                selectedUsers: users,
                deleteUsersDisabled: false,
                exportUsersDisabled: false
            });
        } else if(mode === DESELECT){
            this.setState({
                selectedUsers: [],
                deleteUsersDisabled: true,
                exportUsersDisabled: true
            });
        }
    }

    getUsers() {
        axios
            .get("/api/rest/core/user")
            .then(response => {
                this.setState({
                    users: response.data,
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
                            <h1>Users</h1>
                        </div>
                        <div className="menu">
                            <button className="btn btn-success demo-button-disabled menu-button" data-toggle="modal" data-target="#newUserModal"><i className="fas fa-plus-circle"/> <span>New user</span></button>
                        </div>
                    </div>
                    <div className="panel panel-primary table-panel">
                        <div className="table-result">
                            <ToolkitProvider bootstrap4
                                             columns={ this.columns}
                                             data={ this.state.users }
                                             keyField="id"
                                             search>
                                {
                                    (props) => (
                                        <div>
                                            <div>
                                                <SearchBar { ...props.searchProps } className={"table-filter-field"} />
                                            </div>
                                            <div>
                                                <BootstrapTable { ...props.baseProps } bootstrap4 data={this.state.users} columns={this.columns}
                                                                defaultSorted={ this.defaultSort } keyField='id' hover
                                                                selectRow={ this.selectRow }
                                                                striped
                                                                noDataIndication="Click on 'New user' button to create a new user"
                                                                pagination={ PaginationFactory() }/>
                                            </div>
                                        </div>
                                    )}
                            </ToolkitProvider>
                        </div>
                        <div className="table-result">
                            <div className="panel-buttons">
                                <button className="btn btn-danger demo-button-disabled panel-button" disabled={this.state.deleteUsersDisabled}
                                        data-toggle="modal" data-target="#deleteUsersModal"><i className="fas fa-trash"/> <span>Delete users</span></button>
                            </div>
                        </div>
                    </div>
                </section>

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

                <div className="modal fade" id="deleteUsersModal" tabIndex="-1" role="dialog"
                     aria-labelledby="deleteUsersModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="deleteUsersModalLabel">Delete users</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <div className="table-result">
                                    <ToolkitProvider bootstrap4
                                                     columns={ this.deleteColumns}
                                                     data={ this.state.selectedUsers }
                                                     keyField="id">
                                        {
                                            (props) => (
                                                <div>
                                                    <BootstrapTable { ...props.baseProps } bootstrap4 data={this.state.selectedUsers} columns={this.deleteColumns}
                                                                    defaultSorted={ this.defaultSort } keyField='id' hover
                                                                    striped
                                                                    pagination={ PaginationFactory({hideSizePerPage: true}) }/>
                                                </div>
                                            )}
                                    </ToolkitProvider>
                                </div>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-danger"data-dismiss="modal" onClick={this.onDeleteUsersClick}>Delete</button>
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
)(UserOverview);