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

const { SearchBar } = Search;

class EventOverview extends PureComponent {

    constructor(props) {
        super(props);

        this.columns = [{
            dataField: 'id',
            text: 'id',
            hidden: true
        }, {
            dataField: 'resourceName',
            text: 'Resource name',
            sort: true
        }, {
            dataField: 'type',
            text: 'type',
            sort: true
        }, {
            dataField: 'startDate',
            text: 'Start date',
            sort: true
        }, {
            dataField: 'endDate',
            text: 'End date',
            sort: true
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

    getEvents() {
        axios
            .get("/api/rest/core/events")
            .then(response => {
                this.setState({
                    Events: response.data,
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
                    <div className="content-top">
                        <div className="title">
                            <h1>Events</h1>
                        </div>
                        <div className="menu">
                            <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal" data-target="#importLogModal"><i className="fas fa-cloud-upload-alt"/> <span>Clear events</span></button>
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
            </div>
        )
    }
}

export default EventOverview