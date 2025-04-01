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
import validateErrorResponse from "../../../utility/HttpResponseValidator";
import {Link} from "react-router-dom";
import Tabs from "react-bootstrap/Tabs";
import Tab from "react-bootstrap/Tab";
import DataTable from '../../utility/DataTable';

class RestEvent extends PureComponent {

    constructor(props) {
        super(props);
        this.getEvent = this.getEvent.bind(this);

        this.columns = [{
            dataField: 'name',
            text: 'Name',
            sort: true
        }, {
            dataField: 'value',
            text: 'Value',
            sort: true
        }];

        this.defaultSort = [{
            dataField: 'name',
            order: 'asc'
        }];

        this.state = {
            eventId: this.props.match.params.eventId,
            event: {
                request: {
                    httpHeaders: []
                },
                response: null
            }
        };

        this.getEvent();
    }

    getEvent() {
        axios
            .get(process.env.PUBLIC_URL + "/api/rest/rest/event/" + this.state.eventId)
            .then(response => {
                this.setState({
                    event: response.data
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
                        <nav aria-label="breadcrumb">
                            <ol className="breadcrumb breadcrumb-custom">
                                <li className="breadcrumb-item"><Link to={"/web"}>Home</Link></li>
                                <li className="breadcrumb-item"><Link to={"/web/event"}>Log</Link></li>
                                <li className="breadcrumb-item"><Link to={"/web/rest/event/" + this.state.eventId}>{this.state.eventId}</Link></li>
                            </ol>
                        </nav>
                    </div>
                    <div className="content-top">
                        <div className="title">
                            <h1>REST event: {this.state.event.id}</h1>
                        </div>
                    </div>
                    <div className="content-summary">
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Event Id</dt>
                            <dd className="col-sm-9">{this.state.event.id}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Project id</dt>
                            <dd className="col-sm-9"><Link to={"/web/rest/project/" + this.state.event.projectId}>{this.state.event.projectId}</Link></dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Application id</dt>
                            <dd className="col-sm-9"><Link to={"/web/rest/project/" + this.state.event.projectId + "/application/" + this.state.event.applicationId}>{this.state.event.applicationId}</Link></dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Resource id</dt>
                            <dd className="col-sm-9"><Link to={"/web/rest/project/" + this.state.event.projectId + "/application/" + this.state.event.applicationId + "/resource/" + this.state.event.resourceId}>{this.state.event.resourceId}</Link></dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Method id</dt>
                            <dd className="col-sm-9"><Link to={"/web/rest/project/" + this.state.event.projectId + "/application/" + this.state.event.applicationId + "/resource/" + this.state.event.resourceId + "/method/" + this.state.event.methodId}>{this.state.event.methodId}</Link></dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Start date</dt>
                            <dd className="col-sm-9">{new Date(this.state.event.startDate).toLocaleString()}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">End date</dt>
                            <dd className="col-sm-9">{new Date(this.state.event.endDate).toLocaleString()}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">URI</dt>
                            <dd className="col-sm-9">{this.state.event.request.uri}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Method type</dt>
                            <dd className="col-sm-9">{this.state.event.request.httpMethod}</dd>
                        </dl>
                    </div>
                    <div>
                        <Tabs defaultActiveKey="request">
                            <Tab eventKey="request" title="Request">
                                {this.state.event.request.body !== null && this.state.event.request.body !== "" &&
                                    <div>
                                        <h2>Body</h2>
                                        <textarea className="form-control" id="body" rows="10"
                                                  value={this.state.event.request.body} disabled={true}/>
                                    </div>
                                }
                                <h2>Headers</h2>
                                <div className="table-result">
                                    <DataTable
                                        columns={this.columns}
                                        data={this.state.event.request.httpHeaders}
                                        keyField="name"
                                        search
                                        defaultSort={this.defaultSort}
                                        noDataIndication="No headers"
                                        pagination
                                    ></DataTable>
                                </div>
                            </Tab>
                            {this.state.event.response !== null &&
                                <Tab eventKey="response" title="Response">
                                    {this.state.event.response.body !== null && this.state.event.response.body !== "" &&
                                        <div>
                                            <h2>Body</h2>
                                            <textarea className="form-control" id="body" rows="10"
                                                      value={this.state.event.response.body} disabled={true}/>
                                        </div>
                                    }
                                    <div>
                                        <h2>Headers</h2>
                                        <div className="table-result">
                                            <DataTable
                                                columns={this.columns}
                                                data={this.state.event.response.httpHeaders}
                                                keyField="name"
                                                search
                                                defaultSort={this.defaultSort}
                                                noDataIndication="No headers"
                                                pagination
                                            ></DataTable>
                                        </div>
                                    </div>
                                </Tab>
                            }
                        </Tabs>
                    </div>
                </section>
            </div>
        )
    }

}

export default RestEvent;