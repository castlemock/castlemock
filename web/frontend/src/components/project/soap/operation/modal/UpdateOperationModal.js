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
import {operationStatusFormatter, operationResponseStrategy} from "../../utility/SoapFormatter"

class UpdateOperationModal extends PureComponent {

    constructor(props) {
        super(props);
        this.onUpdateOperationClick = this.onUpdateOperationClick.bind(this);
        this.onStatusChange = this.onStatusChange.bind(this);
        this.onResponseStrategyChange = this.onResponseStrategyChange.bind(this);
        this.onForwardedEndpointChange = this.onForwardedEndpointChange.bind(this);
        this.onSimulateNetworkDelayChange = this.onSimulateNetworkDelayChange.bind(this);
        this.onNetworkDelayChange = this.onNetworkDelayChange.bind(this);
        this.onMockOnFailureChange = this.onMockOnFailureChange.bind(this);

        this.state = {
            updateOperation: {
                status: this.props.operation.status,
                responseStrategy: this.props.operation.responseStrategy,
                forwardedEndpoint: this.props.operation.forwardedEndpoint,
                simulateNetworkDelay: this.props.operation.simulateNetworkDelay,
                networkDelay: this.props.operation.simulateNetworkDelay,
                mockOnFailure: this.props.operation.mockOnFailure
            }
        };
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
                forwardedEndpoint: forwardedEndpoint
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

    onUpdateOperationClick(){
        axios
            .put("/castlemock/api/rest/soap/project/" + this.props.projectId + "/port/" +
                this.props.portId + "/operation/" + this.props.operationId, this.state.updateOperation)
            .then(response => {
                this.props.getOperation();
            })
            .catch(error => {
                validateErrorResponse(error)
            });
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
                            <form>
                                <div className="form-group row">
                                    <label htmlFor="newOperationStatus" className="col-sm-3 col-form-label">Status</label>
                                    <div className="col-sm-9">
                                        <select id="inputStatus" className="form-control" defaultValue={this.state.updateOperation.status}
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
                                                defaultValue={this.state.updateOperation.responseStrategy}
                                                onChange={event => this.onResponseStrategyChange(event.target.value)}>
                                            <option value={"RANDOM"}>{operationResponseStrategy("RANDOM")}</option>
                                            <option value={"SEQUENCE"}>{operationResponseStrategy("SEQUENCE")}</option>
                                            <option value={"XPATH_INPUT"}>{operationResponseStrategy("XPATH_INPUT")}</option>
                                        </select>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label className="col-sm-3 col-form-label">Forwarded endpoint</label>
                                    <div className="col-sm-9">
                                        <input className="form-control" type="text"
                                               defaultValue={this.state.updateOperation.forwardedEndpoint}
                                               onChange={event => this.onForwardedEndpointChange(event.target.value)} />
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label className="col-sm-3 col-form-label">Simulate network delay</label>
                                    <div className="col-sm-9">
                                        <input type="checkbox" defaultValue={this.state.updateOperation.simulateNetworkDelay}
                                               onChange={event => this.onSimulateNetworkDelayChange(event.target.value)}/>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label className="col-sm-3 col-form-label">Network delay</label>
                                    <div className="col-sm-9">
                                        <input className="form-control" type="text" defaultValue={this.state.updateOperation.networkDelay}
                                               onChange={event => this.onNetworkDelayChange(event.target.value)}/>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label htmlFor="newOperationResponseStrategy" className="col-sm-3 col-form-label">Default response</label>
                                    <div className="col-sm-9">
                                        <select id="inputStatus" className="form-control" >

                                        </select>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label className="col-sm-3 col-form-label">Mock on failure</label>
                                    <div className="col-sm-9">
                                        <input type="checkbox" defaultValue={this.state.updateOperation.mockOnFailure}
                                               onChange={event => this.onMockOnFailureChange(event.target.value)}/>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-success" data-dismiss="modal" onClick={this.onUpdateOperationClick}>Update</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default UpdateOperationModal;