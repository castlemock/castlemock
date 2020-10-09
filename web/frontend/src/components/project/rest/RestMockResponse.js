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
import {Link} from "react-router-dom";
import axios from "axios";

class RestMockResponse extends PureComponent {

    constructor(props) {
        super(props);
        this.onDeleteMockResponseClick = this.onDeleteMockResponseClick.bind(this);

        this.state = {
            projectId: this.props.match.params.projectId,
            applicationId: this.props.match.params.applicationId,
            resourceId: this.props.match.params.resourceId,
            methodId: this.props.match.params.methodId,
            mockResponseId: this.props.match.params.mockResponseId,
            mockResponse: {
            }
        };

        this.getMockResponse();
    }

    getMockResponse() {
        axios
            .get("/api/rest/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" +
                this.state.resourceId + "/method/" + this.state.methodId + "/mockresponse/" + this.state.mockResponseId)
            .then(response => {
                this.setState({
                    mockResponse: response.data,
                });
            })
            .catch(error => {
                this.props.validateErrorResponse(error);
            });
    }

    onDeleteMockResponseClick() {
        axios
            .delete("/api/rest/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" + this.state.resourceId + "/method/" + this.state.methodId + "/mockresponse/" + this.state.mockResponseId)
            .then(response => {
                this.props.history.push("/beta/web/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" + this.state.resourceId + "/method/" + this.state.methodId);
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
                        <nav aria-label="breadcrumb">
                            <ol className="breadcrumb breadcrumb-custom">
                                <li className="breadcrumb-item"><Link to={"/beta/web"}>Home</Link></li>
                                <li className="breadcrumb-item"><Link to={"/beta/web/rest/project/" + this.state.projectId}>Project</Link></li>
                                <li className="breadcrumb-item"><Link to={"/beta/web/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId}>Application</Link></li>
                                <li className="breadcrumb-item"><Link to={"/beta/web/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" + this.state.resourceId}>Resource</Link></li>
                                <li className="breadcrumb-item"><Link to={"/beta/web/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" + this.state.resourceId + "/method/" + this.state.methodId}>Method</Link></li>
                                <li className="breadcrumb-item">{this.state.mockResponse.name}</li>
                            </ol>
                        </nav>
                    </div>
                    <div className="content-top">
                        <div className="title">
                            <h1>Mock response: {this.state.mockResponse.name}</h1>
                        </div>
                        <div className="menu" align="right">
                            <button className="btn btn-success demo-button-disabled menu-button" data-toggle="modal" data-target="#updateMethodModal"><i className="fas fa-plus-circle"/> <span>Update mock response</span></button>
                            <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal" data-target="#updateMethodModal"><i className="fas fa-plus-circle"/> <span>Delete mock response</span></button>
                        </div>
                    </div>
                </section>

                <div className="modal fade" id="deleteMockResponseModal" tabIndex="-1" role="dialog"
                     aria-labelledby="deleteMockResponseModalLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="deleteMockResponseModalLabel">Delete the mock response?</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <p>Do you wanna delete the mock response?</p>
                            </div>
                            <div className="modal-footer">
                                <button className="btn btn-danger" data-dismiss="modal" onClick={this.onDeleteMockResponseClick}>Delete</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

}

export default RestMockResponse