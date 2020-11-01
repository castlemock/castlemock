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
import validateErrorResponse from "../../../../utility/HttpResponseValidator";
import DeleteMockResponseModal from "./modal/DeleteMockResponseModal";
import Tabs from "react-bootstrap/Tabs";
import Tab from "react-bootstrap/Tab";
import HeaderComponent from "../../utility/HeaderComponent";
import XPathComponent from "../../utility/XPathComponent";
import {mockResponseStatusFormatter} from "../utility/RestFormatter";

class RestMockResponse extends PureComponent {

    constructor(props) {
        super(props);
        this.getMockResponse = this.getMockResponse.bind(this);
        this.onUpdateMockResponseClick = this.onUpdateMockResponseClick.bind(this);
        this.onDiscardChangesClick = this.onDiscardChangesClick.bind(this);
        this.setName = this.setName.bind(this);
        this.setHttpStatus = this.setHttpStatus.bind(this);
        this.setStatus = this.setStatus.bind(this);
        this.setUsingExpression = this.setUsingExpression.bind(this);
        this.setBody = this.setBody.bind(this);
        this.onHeaderAdded = this.onHeaderAdded.bind(this);
        this.onHeaderRemoved = this.onHeaderRemoved.bind(this);
        this.onXPathAdded = this.onXPathAdded.bind(this);
        this.onXPathRemoved = this.onXPathRemoved.bind(this);

        this.state = {
            projectId: this.props.match.params.projectId,
            applicationId: this.props.match.params.applicationId,
            resourceId: this.props.match.params.resourceId,
            methodId: this.props.match.params.methodId,
            mockResponseId: this.props.match.params.mockResponseId,
            mockResponse: {
                httpHeaders: [],
                xpathExpressions: []
            },
            updateMockResponse: {
                httpHeaders: [],
                xpathExpressions: []
            }
        };

        this.getMockResponse();
    }


    onHeaderAdded(header){
        let httpHeaders = this.state.updateMockResponse.httpHeaders.slice();
        httpHeaders.push(header)
        this.setState({
            updateMockResponse: {
                ...this.state.updateMockResponse,
                httpHeaders: httpHeaders
            }
        })
    }

    onHeaderRemoved(header){
        let httpHeaders = this.state.updateMockResponse.httpHeaders.slice();
        let index = httpHeaders.indexOf(header);
        httpHeaders.splice(index, 1);
        this.setState({
            updateMockResponse: {
                ...this.state.updateMockResponse,
                httpHeaders: httpHeaders
            }
        })
    }

    onXPathAdded(xpath){
        let xpathExpressions = this.state.updateMockResponse.xpathExpressions.slice();
        xpathExpressions.push(xpath)
        this.setState({
            updateMockResponse: {
                ...this.state.updateMockResponse,
                xpathExpressions: xpathExpressions
            }
        })
    }

    onXPathRemoved(xpath){
        let xpathExpressions = this.state.updateMockResponse.xpathExpressions.slice();
        let index = xpathExpressions.indexOf(xpath);
        xpathExpressions.splice(index, 1);
        this.setState({
            updateMockResponse: {
                ...this.state.updateMockResponse,
                xpathExpressions: xpathExpressions
            }
        })
    }

    setName(source) {
        this.setState({
            updateMockResponse: {
                ...this.state.updateMockResponse,
                name: source.target.value
            }
        });
    }

    setHttpStatus(source) {
        this.setState({
            updateMockResponse: {
                ...this.state.updateMockResponse,
                httpStatus: source.target.value
            }
        });
    }

    setStatus(source) {
        this.setState({
            updateMockResponse: {
                ...this.state.updateMockResponse,
                status: source.target.value
            }
        });
    }

    setUsingExpression(source) {
        this.setState({
            updateMockResponse: {
                ...this.state.updateMockResponse,
                usingExpressions: source.target.checked
            }
        });
    }

    setBody(source) {
        this.setState({
            updateMockResponse: {
                ...this.state.updateMockResponse,
                body: source.target.value
            }
        });
    }

    onDiscardChangesClick(){
        this.props.history.push("/castlemock/web/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId +
            "/resource/" + this.state.resourceId + "/method/" + this.state.methodId);
    }

    onUpdateMockResponseClick() {
        axios
            .put("/castlemock/api/rest/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" + this.state.resourceId + "/method/" + this.state.methodId + "/mockresponse/" + this.state.mockResponseId, this.state.updateMockResponse)
            .then(__ => {
                this.props.history.push("/castlemock/web/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" + this.state.resourceId + "/method/" + this.state.methodId);
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    getMockResponse() {
        axios
            .get("/castlemock/api/rest/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" +
                this.state.resourceId + "/method/" + this.state.methodId + "/mockresponse/" + this.state.mockResponseId)
            .then(response => {
                this.setState({
                    mockResponse: response.data,
                    updateMockResponse: response.data
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
                                <li className="breadcrumb-item"><Link to={"/castlemock/web/rest/project/" + this.state.projectId}>Project</Link></li>
                                <li className="breadcrumb-item"><Link to={"/castlemock/web/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId}>Application</Link></li>
                                <li className="breadcrumb-item"><Link to={"/castlemock/web/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" + this.state.resourceId}>Resource</Link></li>
                                <li className="breadcrumb-item"><Link to={"/castlemock/web/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" + this.state.resourceId + "/method/" + this.state.methodId}>Method</Link></li>
                                <li className="breadcrumb-item">{this.state.mockResponse.name}</li>
                            </ol>
                        </nav>
                    </div>
                    <div className="content-top">
                        <div className="title">
                            <h1>Mock Response: {this.state.mockResponse.name}</h1>
                        </div>
                        <div className="menu" align="right">
                            <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal" data-target="#deleteMockResponseModal"><span>Delete mock response</span></button>
                        </div>
                    </div>
                    <div className="content-summary">
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Name</dt>
                            <dd className="col-sm-9"><input defaultValue={this.state.mockResponse.name} onChange={this.setName}/></dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">HTTP Status code</dt>
                            <dd className="col-sm-9"><input type="number" defaultValue={this.state.mockResponse.httpStatusCode} onChange={this.setHttpStatus}/></dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Status</dt>
                            <dd className="col-sm-2">
                                <select id="inputState" className="form-control" defaultValue={this.state.mockResponse.status} onChange={this.setStatus}>
                                    <option value={"ENABLED"}>{mockResponseStatusFormatter("ENABLED")}</option>
                                    <option value={"DISABLED"}>{mockResponseStatusFormatter("DISABLED")}</option>
                                </select>
                            </dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Use Expression</dt>
                            <dd className="col-sm-9"><input type="checkbox" defaultChecked={this.state.mockResponse.usingExpressions} onChange={this.setUsingExpression}/></dd>
                        </dl>
                    </div>
                    <div>
                        <Tabs defaultActiveKey="body">
                            <Tab eventKey="body" title="Body">
                                <div className="response-section">
                                    <textarea className="form-control" id="body" rows="20"  value={this.state.updateMockResponse.body} onChange={this.setBody}/>
                                </div>
                            </Tab>
                            <Tab eventKey="headers" title="Headers">
                                <div className="response-section">
                                    <HeaderComponent onHeaderAdded={this.onHeaderAdded} onHeaderRemoved={this.onHeaderRemoved} httpHeaders={this.state.updateMockResponse.httpHeaders}/>
                                </div>
                            </Tab>
                            <Tab eventKey="xpath" title="XPath">
                                <div className="response-section">
                                    <XPathComponent onXPathAdded={this.onXPathAdded} onXPathRemoved={this.onXPathRemoved} xpathExpressions={this.state.updateMockResponse.xpathExpressions}/>
                                </div>
                            </Tab>
                        </Tabs>
                    </div>
                    <div className="panel-buttons">
                        <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal" data-target="#updateProjectModal" onClick={this.onUpdateMockResponseClick}><span>Update response</span></button>
                        <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal" data-target="#updateProjectModal" onClick={this.onDiscardChangesClick}><span>Discard changes</span></button>
                    </div>
                </section>

                <DeleteMockResponseModal projectId={this.state.projectId} portId={this.state.portId} operationId={this.state.operationId} mockResponseId={this.state.mockResponseId}/>
            </div>
        )
    }
}

export default RestMockResponse;