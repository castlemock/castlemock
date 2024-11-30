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
import validateErrorResponse from "../../utility/HttpResponseValidator";
import DeleteUsersModal from "./modal/DeleteUsersModal";
import NewUserModal from "./modal/NewUserModal";
import {userRoleFormatter, userStatusFormatter} from "../user/utility/UserFormatter";
import {faUserMinus, faUserPlus} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const { SearchBar } = Search;

const SELECT = true;
const DESELECT = false;

class UserOverview extends PureComponent {

    constructor(props) {
        super(props);
        this.onRowSelect = this.onRowSelect.bind(this);
        this.onRowSelectAll = this.onRowSelectAll.bind(this);
        this.userNameFormat = this.userNameFormat.bind(this);
        this.userDateFormat = this.userDateFormat.bind(this);
        this.getUsers = this.getUsers.bind(this);
        this.userStatusFormat = this.userStatusFormat.bind(this);
        this.userRoleFormat = this.userRoleFormat.bind(this);

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
            dataField: 'fullName',
            width: 20,
            maxWidth: 20,
            text: 'Name',
            sort: true,
            formatter: this.userNameFormat
        }, {
            dataField: 'email',
            text: 'Email',
            sort: true
        }, {
            dataField: 'role',
            text: 'Role',
            sort: true,
            formatter: this.userRoleFormat
        }, {
            dataField: 'status',
            text: 'Status',
            sort: true,
            formatter: this.userStatusFormat
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
            deleteUsersDisabled: true
        };

        this.getUsers()
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

    userStatusFormat(cell) {
        if(cell == null){
            return;
        }

        return userStatusFormatter(cell);
    }

    userRoleFormat(cell) {
        if(cell == null){
            return;
        }

        return userRoleFormatter(cell);
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
            selectedUsers: users
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
                selectedUsers: users
            });
        } else if(mode === DESELECT){
            this.setState({
                selectedUsers: []
            });
        }
    }

    getUsers() {
        axios
            .get(process.env.PUBLIC_URL + "/api/rest/core/user")
            .then(response => {
                this.setState({
                    users: response.data,
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
                            <h1>Users</h1>
                        </div>
                        <div className="menu">
                            <button className="btn btn-success demo-button-disabled menu-button" data-toggle="modal" data-target="#newUserModal"><FontAwesomeIcon icon={faUserPlus} className="button-icon"/><span>New user</span></button>
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
                                <button className="btn btn-danger demo-button-disabled panel-button" disabled={this.state.selectedUsers.length === 0}
                                        data-toggle="modal" data-target="#deleteUsersModal"><i className="fas fa-trash"/> <FontAwesomeIcon icon={faUserMinus} className="button-icon"/><span>Delete users</span></button>
                            </div>
                        </div>
                    </div>
                </section>

                <NewUserModal/>
                <DeleteUsersModal selectedUsers={this.state.selectedUsers} getUsers={this.getUsers}/>

            </div>
        )
    }

}

export default UserOverview;