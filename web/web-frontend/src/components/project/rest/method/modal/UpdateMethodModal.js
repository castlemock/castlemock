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
import preventEnterEvent from "../../../../../utility/KeyboardUtility";
import {
    methodResponseStrategyFormatter,
    methodStatusFormatter
} from "../../utility/RestFormatter";
import {faCheckCircle} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

class UpdateMethodModal extends PureComponent {

    constructor(props) {
        super(props);
        this.onNameChange = this.onNameChange.bind(this);
        this.onTypeChange = this.onTypeChange.bind(this);
        this.onStatusChange = this.onStatusChange.bind(this);
        this.onResponseStrategyChange = this.onResponseStrategyChange.bind(this);
        this.onForwardedEndpointChange = this.onForwardedEndpointChange.bind(this);
        this.onSimulateNetworkDelayChange = this.onSimulateNetworkDelayChange.bind(this);
        this.onAutomaticForward = this.onAutomaticForward.bind(this);
        this.onNetworkDelayChange = this.onNetworkDelayChange.bind(this);
        this.onUpdateMethodClick = this.onUpdateMethodClick.bind(this);
        this.onDefaultMockResponseIdChange = this.onDefaultMockResponseIdChange.bind(this);

        this.getMethod = this.getMethod.bind(this);

        this.state = {
            updateMethod: {},
            mockResponses: []
        };

        this.getMethod();
    }

    onNameChange(name){
        this.setState({ updateMethod: {
                ...this.state.updateMethod,
                name: name
            }
        });
    }

    onTypeChange(httpMethod){
        this.setState({ updateMethod: {
                ...this.state.updateMethod,
                httpMethod: httpMethod
            }
        });
    }

    onStatusChange(status){
        this.setState({ updateMethod: {
                ...this.state.updateMethod,
                status: status
            }
        });
    }

    onResponseStrategyChange(responseStrategy){
        this.setState({ updateMethod: {
                ...this.state.updateMethod,
                responseStrategy: responseStrategy
            }
        });
    }

    onForwardedEndpointChange(forwardedEndpoint){
        this.setState({ updateMethod: {
                ...this.state.updateMethod,
                forwardedEndpoint: forwardedEndpoint,
                automaticForward: forwardedEndpoint ? this.state.updateMethod.automaticForward : false
            }
        });
    }

    onSimulateNetworkDelayChange(simulateNetworkDelay){
        this.setState({ updateMethod: {
                ...this.state.updateMethod,
                simulateNetworkDelay: simulateNetworkDelay
            }
        });
    }

    onAutomaticForward(automaticForward){
        this.setState({ updateMethod: {
                ...this.state.updateMethod,
                automaticForward: automaticForward
            }
        });
    }

    onNetworkDelayChange(networkDelay){
        this.setState({ updateMethod: {
                ...this.state.updateMethod,
                networkDelay: networkDelay
            }
        });
    }

    getMethod() {
        axios
            .get(process.env.PUBLIC_URL + "/api/rest/rest/project/" + this.props.projectId + "/application/" + this.props.applicationId + "/resource/" + this.props.resourceId + "/method/" + this.props.methodId)
            .then(response => {
                this.setState({
                    updateMethod: {
                        name: response.data.name,
                        httpMethod: response.data.httpMethod,
                        status: response.data.status,
                        responseStrategy: response.data.responseStrategy,
                        forwardedEndpoint: response.data.forwardedEndpoint,
                        simulateNetworkDelay: response.data.simulateNetworkDelay,
                        networkDelay: response.data.networkDelay,
                        defaultMockResponseId: response.data.defaultMockResponseId,
                        automaticForward: response.data.automaticForward,
                    },
                    mockResponses: response.data.mockResponses
                });
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    onDefaultMockResponseIdChange(defaultMockResponseId){
        console.log(defaultMockResponseId)
        this.setState({ updateMethod: {
                ...this.state.updateMethod,
                defaultMockResponseId: defaultMockResponseId,
                automaticForward: defaultMockResponseId == "-- select an option --" ? this.state.updateMethod.automaticForward : false
            }
        });
    }

    onUpdateMethodClick(){
        axios
            .put(process.env.PUBLIC_URL + "/api/rest/rest/project/" + this.props.projectId + "/application/" +
                this.props.applicationId + "/resource/" + this.props.resourceId + "/method/" + this.props.methodId, this.state.updateMethod)
            .then(response => {
                this.props.getMethod();
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    canEnableAutomaticForward() {
        return this.state.updateMethod.forwardedEndpoint
            && (this.state.updateMethod.defaultMockResponseId == null || this.state.updateMethod.defaultMockResponseId == "-- select an option --")
    }

    render() {
        return (

            <div className="modal fade" id="updateMethodModal" tabIndex="-1" role="dialog"
                 aria-labelledby="updateMethodModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="updateMethodModalLabel">Update method</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <div className="form-group row">
                                <label className="col-sm-3 col-form-label">Name</label>
                                <div className="col-sm-9">
                                    <input className="form-control" type="text" value={this.state.updateMethod.name}
                                           onChange={event => this.onNameChange(event.target.value)} onKeyDown={preventEnterEvent}/>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-sm-3 col-form-label">Type</label>
                                <div className="col-sm-9">
                                    <select id="inputStatus" className="form-control" value={this.state.updateMethod.httpMethod}
                                            onChange={event => this.onTypeChange(event.target.value)}>
                                        <option value={"GET"}>GET</option>
                                        <option value={"POST"}>POST</option>
                                        <option value={"PUT"}>PUT</option>
                                        <option value={"HEAD"}>HEAD</option>
                                        <option value={"DELETE"}>DELETE</option>
                                        <option value={"OPTIONS"}>OPTIONS</option>
                                        <option value={"TRACE"}>TRACE</option>
                                        <option value={"PATCH"}>PATCH</option>

                                    </select>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-sm-3 col-form-label">Status</label>
                                <div className="col-sm-9">
                                    <select id="inputStatus" className="form-control" value={this.state.updateMethod.status}
                                            onChange={event => this.onStatusChange(event.target.value)}>
                                        <option value={"MOCKED"}>{methodStatusFormatter("MOCKED")}</option>
                                        <option value={"DISABLED"}>{methodStatusFormatter("DISABLED")}</option>
                                        <option value={"FORWARDED"}>{methodStatusFormatter("FORWARDED")}</option>
                                        <option value={"RECORDING"}>{methodStatusFormatter("RECORDING")}</option>
                                        <option value={"RECORD_ONCE"}>{methodStatusFormatter("RECORD_ONCE")}</option>
                                        <option value={"ECHO"}>{methodStatusFormatter("ECHO")}</option>
                                    </select>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-sm-3 col-form-label">Response strategy</label>
                                <div className="col-sm-9">
                                    <select id="inputStatus" className="form-control"
                                            value={this.state.updateMethod.responseStrategy}
                                            onChange={event => this.onResponseStrategyChange(event.target.value)}>
                                        <option value={"RANDOM"}>{methodResponseStrategyFormatter("RANDOM")}</option>
                                        <option value={"SEQUENCE"}>{methodResponseStrategyFormatter("SEQUENCE")}</option>
                                        <option value={"XPATH"}>{methodResponseStrategyFormatter("XPATH")}</option>
                                        <option value={"JSON_PATH"}>{methodResponseStrategyFormatter("JSON_PATH")}</option>
                                        <option value={"QUERY_MATCH"}>{methodResponseStrategyFormatter("QUERY_MATCH")}</option>
                                        <option value={"HEADER_QUERY_MATCH"}>{methodResponseStrategyFormatter("HEADER_QUERY_MATCH")}</option>
                                    </select>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-sm-3 col-form-label">Forwarded endpoint</label>
                                <div className="col-sm-9">
                                    <input className="form-control" type="text"
                                           defaultValue={this.state.updateMethod.forwardedEndpoint}
                                           onChange={event => this.onForwardedEndpointChange(event.target.value)} onKeyDown={preventEnterEvent}/>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-sm-3 col-form-label">Automatic forward with no match</label>
                                <div className="col-sm-9">
                                    <input type="checkbox"
                                            checked={this.canEnableAutomaticForward() && this.state.updateMethod.automaticForward}
                                            disabled={!this.canEnableAutomaticForward()}
                                            onChange={event => this.onAutomaticForward(event.target.checked)}/>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-sm-3 col-form-label">Simulate network delay</label>
                                <div className="col-sm-9">
                                    <input type="checkbox" checked={this.state.updateMethod.simulateNetworkDelay}
                                           onChange={event => this.onSimulateNetworkDelayChange(event.target.checked)}/>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-sm-3 col-form-label">Network delay</label>
                                <div className="col-sm-9">
                                    <input className="form-control" type="text" value={this.state.updateMethod.networkDelay}
                                           onChange={event => this.onNetworkDelayChange(event.target.value)} onKeyDown={preventEnterEvent}/>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-sm-3 col-form-label">Default response</label>
                                <div className="col-sm-9">
                                    <select id="inputStatus" className="form-control"
                                            onChange={event => this.onDefaultMockResponseIdChange(event.target.value)}>
                                        <option value={null}> -- select an option -- </option>
                                        {this.state.mockResponses.map(mockResponse =>
                                            <option key={mockResponse.id} value={mockResponse.id} selected={mockResponse.id === this.state.updateMethod.defaultMockResponseId}>{mockResponse.name}</option>
                                        )};
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-success" data-dismiss="modal" onClick={this.onUpdateMethodClick}><FontAwesomeIcon icon={faCheckCircle} className="button-icon"/>Update</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default UpdateMethodModal;