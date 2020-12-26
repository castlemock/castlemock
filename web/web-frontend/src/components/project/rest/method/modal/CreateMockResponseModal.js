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

import React, {PureComponent} from "react";
import axios from "axios";
import { withRouter } from "react-router";
import validateErrorResponse from "../../../../../utility/HttpResponseValidator";
import {mockResponseStatusFormatter} from "../../utility/RestFormatter";
import preventEnterEvent from "../../../../../utility/KeyboardUtility";
import {faCheckCircle} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

class CreateMockResponseModal extends PureComponent {

    constructor(props) {
        super(props);
        this.setNewMockResponseName = this.setNewMockResponseName.bind(this);
        this.setNewMockResponseStatus = this.setNewMockResponseStatus.bind(this);
        this.onCreateMockResponseClick = this.onCreateMockResponseClick.bind(this);

        this.state = {
            newMockResponse: {
                name: "",
                status: "ENABLED",
                httpStatusCode: 200
            }
        };

    }


    setNewMockResponseName(source) {
        this.setState({ newMockResponse: {
                ...this.state.newMockResponse,
                name: source.target.value
            }
        });
    }



    setNewMockResponseStatus(source) {
        this.setState({ newMockResponse: {
                ...this.state.newMockResponse,
                status: source.target.value
            }
        });
    }


    onCreateMockResponseClick() {
        axios
            .post(process.env.PUBLIC_URL + "/api/rest/rest/project/" + this.props.projectId + "/application/" +
                this.props.applicationId + "/resource/" + this.props.resourceId + "/method/" + this.props.methodId + "/mockresponse",
                this.state.newMockResponse)
            .then(response => {
                this.props.history.push("/web/rest/project/" + this.props.projectId + "/application/" + this.props.applicationId +
                    "/resource/" + this.props.resourceId + "/method/" + this.props.methodId + "/mockresponse/" + response.data.id);
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }



    render() {
        return (
            <div className="modal fade" id="createMockResponseModal" tabIndex="-1" role="dialog"
                 aria-labelledby="createMockResponseModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="createMockResponseModalLabel">Create response?</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <div className="form-group row">
                                <label className="col-sm-2 col-form-label">Name</label>
                                <div className="col-sm-10">
                                    <input className="form-control" type="text" onChange={this.setNewMockResponseName} onKeyDown={preventEnterEvent}/>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-sm-2 col-form-label">Status</label>
                                <div className="col-sm-10">
                                    <select id="inputStatus" className="form-control" defaultValue="MOCKED" onChange={this.setNewMockResponseStatus}>
                                        <option value={"ENABLED"}>{mockResponseStatusFormatter("ENABLED")}</option>
                                        <option value={"DISABLED"}>{mockResponseStatusFormatter("DISABLED")}</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-primary" data-dismiss="modal" onClick={this.onCreateMockResponseClick}><FontAwesomeIcon icon={faCheckCircle} className="button-icon"/>Create</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(CreateMockResponseModal);