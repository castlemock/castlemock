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
import axios from "axios";
import validateErrorResponse from "../../utility/HttpResponseValidator";

class System extends PureComponent {

    constructor(props) {
        super(props);

        this.state = {
            system: {}
        };

        this.getSystem()
    }

    getSystem() {
        axios
            .get(process.env.PUBLIC_URL + "/api/rest/core/system")
            .then(response => {
                this.setState({
                    system: response.data
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
                    </div>
                    <div className="content-top">
                        <div className="title">
                            <h1>System</h1>
                        </div>
                    </div>
                    <div className="content-summary">
                        <dl className="row">
                            <dt className="col-sm-2 content-title">OS</dt>
                            <dd className="col-sm-9">{this.state.system.operatingSystemName}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Java version</dt>
                            <dd className="col-sm-9">{this.state.system.javaVersion}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Java vendor</dt>
                            <dd className="col-sm-9">{this.state.system.javaVendor}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Server info</dt>
                            <dd className="col-sm-9">{this.state.system.tomcatInfo}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Server built</dt>
                            <dd className="col-sm-9">{this.state.system.tomcatBuilt}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Server version</dt>
                            <dd className="col-sm-9">{this.state.system.tomcatVersion}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Total memory</dt>
                            <dd className="col-sm-9">{this.state.system.totalMemory} MB</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Max memory</dt>
                            <dd className="col-sm-9">{this.state.system.maxMemory} MB</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Free memory</dt>
                            <dd className="col-sm-9">{this.state.system.freeMemory} MB</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Available processors</dt>
                            <dd className="col-sm-9">{this.state.system.availableProcessors}</dd>
                        </dl>
                        <dl className="row">
                            <dt className="col-sm-2 content-title">Home directory</dt>
                            <dd className="col-sm-9">{this.state.system.castleMockHomeDirectory}</dd>
                        </dl>
                    </div>
                </section>
            </div>
        )
    }
}


export default System;