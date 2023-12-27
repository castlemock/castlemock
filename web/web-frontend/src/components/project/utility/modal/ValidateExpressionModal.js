/*
 Copyright 2021 Karl Dahlgren

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
import ToolkitProvider from "react-bootstrap-table2-toolkit";
import BootstrapTable from "react-bootstrap-table-next";
import PaginationFactory from "react-bootstrap-table2-paginator";
import axios from "axios";
import validateErrorResponse from "../../../../utility/HttpResponseValidator";
import {faCheckCircle} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

class ValidateExpressionModal extends PureComponent {

    constructor(props) {
        super(props);
        this.onValidateExpressionClick = this.onValidateExpressionClick.bind(this);
        this.setRequestBody = this.setRequestBody.bind(this);
        this.setResponseBody = this.setResponseBody.bind(this);

        this.state = {
            requestBody: "",
            responseBody: "",
            output: ""
        };
    }

    setRequestBody(event) {
        this.setState({ requestBody: event.target.value });
    }

    setResponseBody(event) {
        this.setState({ responseBody: event.target.value });
    }

    onValidateExpressionClick() {
        axios
            .post(process.env.PUBLIC_URL + "/api/rest/core/expression/validate", {
                requestUrl: this.state.requestUrl,
                expressionType: this.props.expressionType,
                requestBody: this.state.requestBody,
                responseBody: this.state.responseBody
            })
            .then(response => {
                this.setState({
                    output: response.data.output
                });
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }
    render() {
        return (
            <div className="modal fade" id="validateExpressionModal" tabIndex="-1" role="dialog"
                 aria-labelledby="validateExpressionModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="validateExpressionModalLabel">Validate expression</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                           <dl className="row">
                               <dt className="col-sm-2 content-title">Request Body</dt>
                                <dd className="col-sm-10">
                                    <textarea className="form-control" rows="5" onChange={this.setRequestBody}/>
                                </dd>
                            </dl>
                             <dl className="row">
                                <dt className="col-sm-2 content-title">Response Body / Expression</dt>
                                <dd className="col-sm-10">
                                    <textarea className="form-control" rows="5" onChange={this.setResponseBody}/>
                                </dd>
                            </dl>
                            <hr/>
                            <textarea className="form-control" rows="5" value={this.state.output}/>
                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-success" onClick={this.onValidateExpressionClick}><FontAwesomeIcon icon={faCheckCircle} className="button-icon"/>Validate</button>
                            <button className="btn btn-primary" data-dismiss="modal"><FontAwesomeIcon icon={faCheckCircle} className="button-icon"/>Close</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default ValidateExpressionModal;