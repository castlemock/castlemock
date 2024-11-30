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

import React, {Component} from 'react';
import {HashRouter as Router, Route, Switch} from "react-router-dom";
import {createBrowserHistory} from 'history';
import '../css/App.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap.js';
import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css';
import 'react-bootstrap-table-next/dist/react-bootstrap-table-next.min.js';
import 'react-bootstrap-table2-paginator/dist/react-bootstrap-table2-paginator.min.css';
import 'react-bootstrap-table2-paginator/dist/react-bootstrap-table2-paginator.min.js';
import 'react-bootstrap-table2-toolkit/dist/react-bootstrap-table2-toolkit.min.css';
import 'react-bootstrap-table2-toolkit/dist/react-bootstrap-table2-toolkit.min.js';
import LoginContainer from './LoginContainer'
import LogoutContainer from './LogoutContainer'
import MainContainer from './MainContainer'
import AuthenticationContext from "../context/AuthenticationContext";
import VersionContext from "../context/VersionContext";
import axios from "axios";
import ContextContext from "../context/ContextContext";

export const history = createBrowserHistory({
    basename: process.env.PUBLIC_URL
});

class App extends Component {

    constructor(props) {
        super(props);
        this.getVersion = this.getVersion.bind(this);
        this.getContext = this.getContext.bind(this);

        this.updateAuthentication = (authentication) => {
            this.setState({
                authentication: authentication
            });
        }

        this.state = {
            authentication: {},
            updateAuthentication: this.updateAuthentication,
            version: "1.0",
            context: "castlemock"
        };

        this.getVersion();
        this.getContext();
    }

    getVersion() {
        axios
            .get(process.env.PUBLIC_URL + "/api/rest/core/version")
            .then(response => {
                this.setState({version: response.data.version})
            });
    }

    getContext() {
        axios
            .get(process.env.PUBLIC_URL + "/api/rest/core/context")
            .then(response => {
                this.setState({context: response.data.context})
            });
    }

    render() {
        return (
            <ContextContext.Provider value={this.state.context}>
                <VersionContext.Provider value={this.state.version}>
                    <AuthenticationContext.Provider value={this.state}>
                        <div className = "site-wrapper">
                            <Router>
                                <Switch>
                                    <Route path="/web/login"  component={LoginContainer} />
                                    <Route path="/web/logout"  component={LogoutContainer} />
                                    <Route path="/web/*"  component={MainContainer} />
                                    <Route path="/*"  component={MainContainer} />
                                    <Route path=""  component={MainContainer} />
                                </Switch>
                            </Router>
                        </div>
                    </AuthenticationContext.Provider>
                </VersionContext.Provider>
            </ContextContext.Provider>
        );
    }
}

export default App;
