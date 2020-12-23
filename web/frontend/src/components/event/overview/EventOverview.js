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
import validateErrorResponse from "../../../utility/HttpResponseValidator";
import {Link} from "react-router-dom";

const { SearchBar } = Search;

class EventOverview extends PureComponent {

    constructor(props) {
        super(props);
        this.nameFormat = this.nameFormat.bind(this);
        this.onDeleteAllEventsClick = this.onDeleteAllEventsClick.bind(this);
        this.userDateFormat = this.userDateFormat.bind(this);

        this.columns = [{
            dataField: 'id',
            text: 'id',
            hidden: true
        }, {
            dataField: 'resourceName',
            text: 'Resource name',
            sort: true,
            formatter: this.nameFormat
        }, {
            dataField: 'typeIdentifier.type',
            text: 'type',
            sort: true
        }, {
            dataField: 'startDate',
            text: 'Start date',
            sort: true,
            formatter: this.userDateFormat
        }, {
            dataField: 'endDate',
            text: 'End date',
            sort: true,
            formatter: this.userDateFormat
        }];

        this.defaultSort = [{
            dataField: 'startDate',
            order: 'startDate'
        }];

        this.state = {
            events: []
        };

        this.getEvents()
    }

    nameFormat(cell, row) {
        if(cell == null){
            return;
        }

        return (
            <div className="table-link">
                <Link to={"/web/" + row.typeIdentifier.typeUrl + "/event/" + row.id}>{cell}</Link>
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

    getEvents() {
        axios
            .get(process.env.PUBLIC_URL + "/api/rest/core/event")
            .then(response => {
                this.setState({
                    events: response.data,
                });
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    onDeleteAllEventsClick() {
        axios
            .delete(process.env.PUBLIC_URL + "/api/rest/core/event")
            .then(response => {
                this.getEvents();
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    render() {
        return (
            <div>
                <section>
                    <div className="content-top">
                        <div className="title">
                            <h1>Events</h1>
                        </div>
                        <div className="menu">
                            <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal" data-target="#deleteAllEventsModal"><span>Clear events</span></button>
                        </div>
                    </div>
                    <div className="panel panel-primary table-panel">
                        <div className="table-result">
                            <ToolkitProvider bootstrap4
                                             columns={ this.columns}
                                             data={ this.state.events }
                                             keyField="id"
                                             search>
                                {
                                    (props) => (
                                        <div>
                                            <div>
                                                <SearchBar { ...props.searchProps } className={"table-filter-field"} />
                                            </div>
                                            <div>
                                                <BootstrapTable { ...props.baseProps } bootstrap4 data={this.state.events} columns={this.columns}
                                                                defaultSorted={ this.defaultSort } keyField='id' hover
                                                                selectRow={ this.selectRow }
                                                                striped
                                                                noDataIndication="No events has so far been recorded"
                                                                pagination={ PaginationFactory() }/>
                                            </div>
                                        </div>
                                    )}
                            </ToolkitProvider>
                        </div>
                    </div>
                </section>

                <div className="modal fade" id="deleteAllEventsModal" tabIndex="-1" role="dialog"
                     aria-labelledby="deleteAllEventsModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="deleteAllEventsModalLabel">Delete all events?</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <p>Do you wanna delete all the events?</p>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-danger" data-dismiss="modal" onClick={this.onDeleteAllEventsClick}>Delete</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default EventOverview;