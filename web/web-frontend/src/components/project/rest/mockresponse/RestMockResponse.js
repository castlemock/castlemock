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
import {isOnlyReader} from "../../../../utility/AuthorizeUtility";
import AuthenticationContext from "../../../../context/AuthenticationContext";
import {faEdit, faTrash} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import JsonPathComponent from "../../utility/JsonPathComponent";
import HeaderQueryComponent from "../../utility/HeaderQueryComponent";
import ParameterQueryComponent from "../../utility/ParameterQueryComponent";


class RestMockResponse extends PureComponent {

    constructor(props) {
        super(props);
        this.getMockResponse = this.getMockResponse.bind(this);
        this.onUpdateMockResponseClick = this.onUpdateMockResponseClick.bind(this);
        this.onDiscardChangesClick = this.onDiscardChangesClick.bind(this);
        this.setName = this.setName.bind(this);
        this.setHttpStatusCode = this.setHttpStatusCode.bind(this);
        this.setStatus = this.setStatus.bind(this);
        this.setUsingExpression = this.setUsingExpression.bind(this);
        this.setBody = this.setBody.bind(this);
        this.onHeaderAdded = this.onHeaderAdded.bind(this);
        this.onHeaderRemoved = this.onHeaderRemoved.bind(this);
        this.onXPathAdded = this.onXPathAdded.bind(this);
        this.onXPathRemoved = this.onXPathRemoved.bind(this);
        this.onJsonPathAdded = this.onJsonPathAdded.bind(this);
        this.onJsonPathRemoved = this.onJsonPathRemoved.bind(this);
        this.onHeaderQueryAdded = this.onHeaderQueryAdded.bind(this);
        this.onHeaderQueryRemoved = this.onHeaderQueryRemoved.bind(this);
        this.onParameterQueryAdded = this.onParameterQueryAdded.bind(this);
        this.onParameterQueryRemoved = this.onParameterQueryRemoved.bind(this);

        this.state = {
            projectId: this.props.match.params.projectId,
            applicationId: this.props.match.params.applicationId,
            resourceId: this.props.match.params.resourceId,
            methodId: this.props.match.params.methodId,
            mockResponseId: this.props.match.params.mockResponseId,
            mockResponse: {
                httpHeaders: [],
                xpathExpressions: [],
                jsonPathExpressions: [],
                headerQueries: [],
                parameterQueries: []
            },
            updateMockResponse: {
                httpHeaders: [],
                xpathExpressions: [],
                jsonPathExpressions: [],
                headerQueries: [],
                parameterQueries: []
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

    onJsonPathAdded(jsonPath){
        let jsonPathExpressions = this.state.updateMockResponse.jsonPathExpressions.slice();
        jsonPathExpressions.push(jsonPath)
        this.setState({
            updateMockResponse: {
                ...this.state.updateMockResponse,
                jsonPathExpressions: jsonPathExpressions
            }
        })
    }

    onJsonPathRemoved(jsonPath){
        let jsonPathExpressions = this.state.updateMockResponse.jsonPathExpressions.slice();
        let index = jsonPathExpressions.indexOf(jsonPath);
        jsonPathExpressions.splice(index, 1);
        this.setState({
            updateMockResponse: {
                ...this.state.updateMockResponse,
                jsonPathExpressions: jsonPathExpressions
            }
        })
    }

    onHeaderQueryAdded(headerQuery){
        let headerQueries = this.state.updateMockResponse.headerQueries.slice();
        headerQueries.push(headerQuery)
        this.setState({
            updateMockResponse: {
                ...this.state.updateMockResponse,
                headerQueries: headerQueries
            }
        })
    }

    onHeaderQueryRemoved(headerQuery){
        let headerQueries = this.state.updateMockResponse.headerQueries.slice();
        let index = headerQueries.indexOf(headerQuery);
        headerQueries.splice(index, 1);
        this.setState({
            updateMockResponse: {
                ...this.state.updateMockResponse,
                headerQueries: headerQueries
            }
        })
    }
    
    onParameterQueryAdded(parameterQuery){
        let parameterQueries = this.state.updateMockResponse.parameterQueries.slice();
        parameterQueries.push(parameterQuery)
        this.setState({
            updateMockResponse: {
                ...this.state.updateMockResponse,
                parameterQueries: parameterQueries
            }
        })
    }

    onParameterQueryRemoved(parameterQuery){
        let parameterQueries = this.state.updateMockResponse.parameterQueries.slice();
        let index = parameterQueries.indexOf(parameterQuery);
        parameterQueries.splice(index, 1);
        this.setState({
            updateMockResponse: {
                ...this.state.updateMockResponse,
                parameterQueries: parameterQueries
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

    setHttpStatusCode(source) {
        this.setState({
            updateMockResponse: {
                ...this.state.updateMockResponse,
                httpStatusCode: source.target.value
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
        this.props.history.push("/web/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId +
            "/resource/" + this.state.resourceId + "/method/" + this.state.methodId);
    }

    onUpdateMockResponseClick() {
        axios
            .put(process.env.PUBLIC_URL + "/api/rest/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" + this.state.resourceId + "/method/" + this.state.methodId + "/mockresponse/" + this.state.mockResponseId, this.state.updateMockResponse)
            .then(__ => {
                this.props.history.push("/web/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" + this.state.resourceId + "/method/" + this.state.methodId);
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    getMockResponse() {
        axios
            .get(process.env.PUBLIC_URL + "/api/rest/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" +
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
                                <li className="breadcrumb-item"><Link to={"/web/rest/project/" + this.state.projectId}>Project</Link></li>
                                <li className="breadcrumb-item"><Link to={"/web/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId}>Application</Link></li>
                                <li className="breadcrumb-item"><Link to={"/web/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" + this.state.resourceId}>Resource</Link></li>
                                <li className="breadcrumb-item"><Link to={"/web/rest/project/" + this.state.projectId + "/application/" + this.state.applicationId + "/resource/" + this.state.resourceId + "/method/" + this.state.methodId}>Method</Link></li>
                                <li className="breadcrumb-item">{this.state.mockResponse.name}</li>
                            </ol>
                        </nav>
                    </div>
                    <div className="content-top">
                        <div className="title">
                            <h1>Mock Response: {this.state.mockResponse.name}</h1>
                        </div>
                        <AuthenticationContext.Consumer>
                            {context => (
                                <div className="menu" align="right">
                                    <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal" data-target="#deleteMockResponseModal" disabled={isOnlyReader(context.authentication.role)}><FontAwesomeIcon icon={faTrash} className="button-icon"/><span>Delete mock response</span></button>
                                </div>
                            )}
                        </AuthenticationContext.Consumer>
                    </div>
                    <div className="content-summary">
                        <dl className="row">
                            <dt className="col-sm-3 content-title">Name</dt>
                            <dd className="col-sm-9"><input defaultValue={this.state.mockResponse.name} onChange={this.setName}/></dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-3 content-title">HTTP Status code</dt>
                            <dd className="col-sm-9"><input type="number" defaultValue={this.state.mockResponse.httpStatusCode} onChange={this.setHttpStatusCode}/></dd>
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
                            <Tab eventKey="jsonPath" title="JSON Path">
                                <div className="response-section">
                                    <JsonPathComponent onJsonPathAdded={this.onJsonPathAdded} onJsonPathRemoved={this.onJsonPathRemoved} jsonPathExpressions={this.state.updateMockResponse.jsonPathExpressions}/>
                                </div>
                            </Tab>
                             <Tab eventKey="parameterQuery" title="Parameter Queries">
                                <div className="response-section">
                                    <ParameterQueryComponent onParameterQueryAdded={this.onParameterQueryAdded} onParameterQueryRemoved={this.onParameterQueryRemoved} parameterQueries={this.state.updateMockResponse.parameterQueries}/>
                                </div>
                            </Tab>
                            <Tab eventKey="headerQuery" title="Header Queries">
                                <div className="response-section">
                                    <HeaderQueryComponent onHeaderQueryAdded={this.onHeaderQueryAdded} onHeaderQueryRemoved={this.onHeaderQueryRemoved} headerQueries={this.state.updateMockResponse.headerQueries}/>
                                </div>
                            </Tab>
                        </Tabs>
                    </div>
                    <AuthenticationContext.Consumer>
                        {context => (
                            <div className="panel-buttons">
                                <button className="btn btn-primary demo-button-disabled menu-button" data-toggle="modal" data-target="#updateProjectModal" onClick={this.onUpdateMockResponseClick} disabled={isOnlyReader(context.authentication.role)}><FontAwesomeIcon icon={faEdit} className="button-icon"/><span>Update response</span></button>
                                <button className="btn btn-danger demo-button-disabled menu-button" data-toggle="modal" data-target="#updateProjectModal" onClick={this.onDiscardChangesClick}><FontAwesomeIcon icon={faTrash} className="button-icon"/><span>Discard changes</span></button>
                            </div>
                        )}
                    </AuthenticationContext.Consumer>
                </section>

                <DeleteMockResponseModal projectId={this.state.projectId} portId={this.state.portId} operationId={this.state.operationId} mockResponseId={this.state.mockResponseId}/>
            </div>
        )
    }
}

export default RestMockResponse;