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
import validateErrorResponse from "../../../../../utility/HttpResponseValidator";
import { withRouter } from "react-router";
import preventEnterEvent from "../../../../../utility/KeyboardUtility";
import {faCheckCircle} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

class CreateMethodModal extends PureComponent {

    constructor(props) {
        super(props);
        this.onCreateMethodClick = this.onCreateMethodClick.bind(this);
        this.setName = this.setName.bind(this);
        this.setHttpMethod = this.setHttpMethod.bind(this);

        this.state = {
            newMethod: {
                name: "",
                httpMethod: "GET"
            }
        };
    }

    setName(source) {
        this.setState({
            newMethod: {
                ...this.state.newMethod,
                name: source.target.value
            }
        });
    }

    setHttpMethod(source) {
        this.setState({
            newMethod: {
                ...this.state.newMethod,
                httpMethod: source.target.value
            }
        });
    }

    onCreateMethodClick() {
        axios
            .post(process.env.PUBLIC_URL + "/api/rest/rest/project/" + this.props.projectId + "/application/" + this.props.applicationId + "/resource/" + this.props.resourceId + "/method", this.state.newMethod)
            .then(response => {
                this.props.history.push("/web/rest/project/" + this.props.projectId + "/application/" + this.props.applicationId + "/resource/" + this.props.resourceId + "/method/" + response.data.id);
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    render() {
        return (
            <div className="modal fade" id="createMethodModal" tabIndex="-1" role="dialog"
                 aria-labelledby="createMethodModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="createMethodModalLabel">Create method</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <div className="form-group row">
                                <label className="col-sm-2 col-form-label">Name</label>
                                <div className="col-sm-10">
                                    <input className="form-control" type="text" value={this.state.newMethod.name} onChange={this.setName} onKeyDown={preventEnterEvent}/>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-sm-2 col-form-label">HTTP method</label>
                                <div className="col-sm-10">
                                    <select id="inputStatus" className="form-control" defaultValue="GET" onChange={this.setHttpMethod}>
                                        <option>GET</option>
                                        <option>POST</option>
                                        <option>PUT</option>
                                        <option>HEAD</option>
                                        <option>DELETE</option>
                                        <option>OPTIONS</option>
                                        <option>TRACE</option>
                                        <option>PATCH</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-success" data-dismiss="modal" onClick={this.onCreateMethodClick}><FontAwesomeIcon icon={faCheckCircle} className="button-icon"/>Create</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(CreateMethodModal);