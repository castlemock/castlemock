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

import React, {PureComponent} from 'react'
import '../css/Footer.css';
import VersionContext from "../context/VersionContext";

class Footer extends PureComponent {

    render() {
        return (
            <div className="footer">
                <div className="languages">
                </div>

                <VersionContext.Consumer>
                    {context => (
                        <div className="login-footer-info">
                            <a href="https://www.castlemock.com" target="_blank" rel="noopener noreferrer">Castle Mock version. {context}</a>
                        </div>
                    )}
                </VersionContext.Consumer>

                <div className="login-footer-info-api">
                    <a href="/doc/api/rest" target="_blank" rel="noopener noreferrer">REST API</a>
                </div>
                <div className="page-created">
                    Page created: {new Date().toISOString()}
                </div>
            </div>
    );
    }
}

export default Footer