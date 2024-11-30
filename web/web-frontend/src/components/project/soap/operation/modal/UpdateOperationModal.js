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
import {
    operationIdentifyStrategy,
    operationResponseStrategy,
    operationStatusFormatter
} from "../../utility/SoapFormatter"
import preventEnterEvent from "../../../../../utility/KeyboardUtility";
import {faCheckCircle} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

class UpdateOperationModal extends PureComponent {

    constructor(props) {
        super(props);
        this.onUpdateOperationClick = this.onUpdateOperationClick.bind(this);
        this.onStatusChange = this.onStatusChange.bind(this);
        this.onResponseStrategyChange = this.onResponseStrategyChange.bind(this);
        this.onForwardedEndpointChange = this.onForwardedEndpointChange.bind(this);
        this.onSimulateNetworkDelayChange = this.onSimulateNetworkDelayChange.bind(this);
        this.onAutomaticForward = this.onAutomaticForward.bind(this);
        this.onNetworkDelayChange = this.onNetworkDelayChange.bind(this);
        this.onMockOnFailureChange = this.onMockOnFailureChange.bind(this);
        this.onOperationIdentifyStrategyChange = this.onOperationIdentifyStrategyChange.bind(this);
        this.onDefaultMockResponseIdChange = this.onDefaultMockResponseIdChange.bind(this);

        this.getOperation = this.getOperation.bind(this);
        this.state = {
            updateOperation: {},
            mockResponses: []
        };

        this.getOperation(props.projectId, props.portId, props.operationId)
    }

    getOperation(projectId, portId, operationId) {
        axios
            .get(process.env.PUBLIC_URL + "/api/rest/soap/project/" + projectId + "/port/" + portId + "/operation/" + operationId)
            .then(response => {
                this.setState({
                    updateOperation: {
                        status: response.data.status,
                        responseStrategy: response.data.responseStrategy,
                        forwardedEndpoint: response.data.forwardedEndpoint,
                        simulateNetworkDelay: response.data.simulateNetworkDelay,
                        networkDelay: response.data.networkDelay,
                        mockOnFailure: response.data.mockOnFailure,
                        identifyStrategy: response.data.identifyStrategy,
                        defaultMockResponseId: response.data.defaultMockResponseId,
                        automaticForward: response.data.automaticForward
                    },
                    mockResponses: response.data.mockResponses
                });
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    onStatusChange(status){
        this.setState({ updateOperation: {
            ...this.state.updateOperation,
                status: status
            }
        });
    }

    onResponseStrategyChange(responseStrategy){
        this.setState({ updateOperation: {
                ...this.state.updateOperation,
                responseStrategy: responseStrategy
            }
        });
    }

    onForwardedEndpointChange(forwardedEndpoint){
        this.setState({ updateOperation: {
                ...this.state.updateOperation,
                forwardedEndpoint: forwardedEndpoint,
                automaticForward: forwardedEndpoint ? this.state.updateOperation.automaticForward : false
            }
        });
    }

    onSimulateNetworkDelayChange(simulateNetworkDelay){
        this.setState({ updateOperation: {
                ...this.state.updateOperation,
                simulateNetworkDelay: simulateNetworkDelay
            }
        });
    }

    onAutomaticForward(automaticForward){
        this.setState({ updateOperation: {
                ...this.state.updateOperation,
                automaticForward: automaticForward
            }
        });
    }

    onNetworkDelayChange(networkDelay){
        this.setState({ updateOperation: {
                ...this.state.updateOperation,
                networkDelay: networkDelay
            }
        });
    }

    onMockOnFailureChange(mockOnFailure){
        this.setState({ updateOperation: {
                ...this.state.updateOperation,
                mockOnFailure: mockOnFailure
            }
        });
    }

    onOperationIdentifyStrategyChange(identifyStrategy){
        this.setState({ updateOperation: {
                ...this.state.updateOperation,
                identifyStrategy: identifyStrategy
            }
        });
    }

    onDefaultMockResponseIdChange(defaultMockResponseId){
        this.setState({ updateOperation: {
                ...this.state.updateOperation,
                defaultMockResponseId: defaultMockResponseId,
                automaticForward: defaultMockResponseId == "-- select an option --" ? this.state.updateOperation.automaticForward : false
            }
        });
    }

    onUpdateOperationClick(){
        axios
            .put(process.env.PUBLIC_URL + "/api/rest/soap/project/" + this.props.projectId + "/port/" +
                this.props.portId + "/operation/" + this.props.operationId, this.state.updateOperation)
            .then(response => {
                this.props.getOperation();
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    canEnableAutomaticForward() {
        return this.state.updateOperation.forwardedEndpoint
            && (this.state.updateOperation.defaultMockResponseId == null || this.state.updateOperation.defaultMockResponseId == "-- select an option --")
    }

    render() {
        return (
            <div className="modal fade" id="updateOperationModal" tabIndex="-1" role="dialog"
                 aria-labelledby="updateOperationModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="updateOperationModalLabel">Update operation</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <div className="form-group row">
                                <label htmlFor="newOperationStatus" className="col-sm-3 col-form-label">Status</label>
                                <div className="col-sm-9">
                                    <select id="inputStatus" className="form-control" value={this.state.updateOperation.status}
                                            onChange={event => this.onStatusChange(event.target.value)}>
                                        <option value={"MOCKED"}>{operationStatusFormatter("MOCKED")}</option>
                                        <option value={"DISABLED"}>{operationStatusFormatter("DISABLED")}</option>
                                        <option value={"FORWARDED"}>{operationStatusFormatter("FORWARDED")}</option>
                                        <option value={"RECORDING"}>{operationStatusFormatter("RECORDING")}</option>
                                        <option value={"RECORD_ONCE"}>{operationStatusFormatter("RECORD_ONCE")}</option>
                                        <option value={"ECHO"}>{operationStatusFormatter("ECHO")}</option>
                                    </select>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="newOperationResponseStrategy" className="col-sm-3 col-form-label">Response strategy</label>
                                <div className="col-sm-9">
                                    <select id="inputStatus" className="form-control"
                                            value={this.state.updateOperation.responseStrategy}
                                            onChange={event => this.onResponseStrategyChange(event.target.value)}>
                                        <option value={"RANDOM"}>{operationResponseStrategy("RANDOM")}</option>
                                        <option value={"SEQUENCE"}>{operationResponseStrategy("SEQUENCE")}</option>
                                        <option value={"XPATH_INPUT"}>{operationResponseStrategy("XPATH_INPUT")}</option>
                                    </select>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-sm-3 col-form-label">Identify strategy</label>
                                <div className="col-sm-9">
                                    <select id="inputStatus" className="form-control"
                                            value={this.state.updateOperation.identifyStrategy}
                                            onChange={event => this.onOperationIdentifyStrategyChange(event.target.value)}>
                                        <option value={"ELEMENT"}>{operationIdentifyStrategy("ELEMENT")}</option>
                                        <option value={"ELEMENT_NAMESPACE"}>{operationIdentifyStrategy("ELEMENT_NAMESPACE")}</option>
                                    </select>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-sm-3 col-form-label">Forwarded endpoint</label>
                                <div className="col-sm-9">
                                    <input className="form-control" type="text"
                                           value={this.state.updateOperation.forwardedEndpoint}
                                           onChange={event => this.onForwardedEndpointChange(event.target.value)} onKeyDown={preventEnterEvent}/>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-sm-3 col-form-label">Automatic forward with no match</label>
                                <div className="col-sm-9">
                                    <input type="checkbox"
                                            checked={this.canEnableAutomaticForward() && this.state.updateOperation.automaticForward}
                                            disabled={!this.canEnableAutomaticForward()}
                                            onChange={event => this.onAutomaticForward(event.target.checked)}/>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-sm-3 col-form-label">Simulate network delay</label>
                                <div className="col-sm-9">
                                    <input type="checkbox" checked={this.state.updateOperation.simulateNetworkDelay}
                                           onChange={event => this.onSimulateNetworkDelayChange(event.target.checked)}/>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-sm-3 col-form-label">Network delay</label>
                                <div className="col-sm-9">
                                    <input className="form-control" type="text" value={this.state.updateOperation.networkDelay}
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
                                            <option key={mockResponse.id} value={mockResponse.id} selected={mockResponse.id === this.state.updateOperation.defaultMockResponseId}>{mockResponse.name}</option>
                                        )};
                                    </select>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-sm-3 col-form-label">Mock on failure</label>
                                <div className="col-sm-9">
                                    <input type="checkbox" checked={this.state.updateOperation.mockOnFailure}
                                           onChange={event => this.onMockOnFailureChange(event.target.checked)}/>
                                </div>
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-success" data-dismiss="modal" onClick={this.onUpdateOperationClick}><FontAwesomeIcon icon={faCheckCircle} className="button-icon"/>Update</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default UpdateOperationModal;