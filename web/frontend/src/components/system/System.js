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
            .get("/api/rest/core/system")
            .then(response => {
                this.setState({
                    system: response.data
                });
            })
            .catch(error => {
                this.props.validateErrorResponse(error);
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
                            <dt className="col-sm-3">OS</dt>
                            <dd className="col-sm-9">{this.state.system.operatingSystemName}</dd>
                            <dt className="col-sm-3">Java version</dt>
                            <dd className="col-sm-9">{this.state.system.javaVersion}</dd>
                            <dt className="col-sm-3">Java vendor</dt>
                            <dd className="col-sm-9">{this.state.system.javaVendor}</dd>
                            <dt className="col-sm-3">Server info</dt>
                            <dd className="col-sm-9">{this.state.system.tomcatInfo}</dd>
                            <dt className="col-sm-3">Server built</dt>
                            <dd className="col-sm-9">{this.state.system.tomcatBuilt}</dd>
                            <dt className="col-sm-3">Server version</dt>
                            <dd className="col-sm-9">{this.state.system.tomcatVersion}</dd>
                            <dt className="col-sm-3">Total memory</dt>
                            <dd className="col-sm-9">{this.state.system.totalMemory} MB</dd>
                            <dt className="col-sm-3">Max memory</dt>
                            <dd className="col-sm-9">{this.state.system.maxMemory} MB</dd>
                            <dt className="col-sm-3">Free memory</dt>
                            <dd className="col-sm-9">{this.state.system.freeMemory} MB</dd>
                            <dt className="col-sm-3">Available processors</dt>
                            <dd className="col-sm-9">{this.state.system.availableProcessors}</dd>
                            <dt className="col-sm-3">Home directory</dt>
                            <dd className="col-sm-9">{this.state.system.castleMockHomeDirectory}</dd>
                        </dl>
                    </div>
                </section>
            </div>
        )
    }
}

export default System