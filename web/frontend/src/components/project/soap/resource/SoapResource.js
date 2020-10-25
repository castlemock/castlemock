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

class SoapResource extends PureComponent {

    constructor(props) {
        super(props);

        this.state = {
            projectId: this.props.match.params.projectId,
            resourceId: this.props.match.params.resourceId,
            resource: {},
            resourceContent: ""
        };

        this.getResource();
        this.getResourceContent();
    }

    getResource() {
        axios
            .get("/api/rest/soap/project/" + this.state.projectId + "/resource/" + this.state.resourceId)
            .then(response => {
                this.setState({
                    resource: response.data,
                });
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    getResourceContent() {
        axios
            .get("/api/rest/soap/project/" + this.state.projectId + "/resource/" + this.state.resourceId + "/content")
            .then(response => {
                this.setState({
                    resourceContent: response.data,
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
                                <li className="breadcrumb-item"><Link to={"/web/soap/project/" + this.state.projectId}>Project</Link></li>
                                <li className="breadcrumb-item">{this.state.resource.name}</li>
                            </ol>
                        </nav>
                    </div>
                    <div className="content-top">
                        <div className="title">
                            <h1>Resource: {this.state.resource.name}</h1>
                        </div>
                    </div>
                    <div className="content-summary">
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Name</dt>
                            <dd className="col-sm-9">{this.state.resource.name}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Type</dt>
                            <dd className="col-sm-9">{this.state.resource.type}</dd>
                        </dl>
                    </div>
                    <div>
                        <textarea className="form-control" rows="20" value={this.state.resourceContent}/>
                    </div>
                </section>
            </div>
        )
    }
}

export default SoapResource;