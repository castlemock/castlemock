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
import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom";
import '../css/Main.css';
import Footer from './Footer'
import Header from './Header'
import ProjectOverview from './project/overview/ProjectOverview'
import RestProject from './project/rest/project/RestProject'
import SoapProject from './project/soap/project/SoapProject'
import SoapPort from './project/soap/port/SoapPort'
import SoapResource from "./project/soap/resource/SoapResource";
import SoapOperation from './project/soap/operation/SoapOperation'
import SoapMockResponse from './project/soap/mockresponse/SoapMockResponse'
import RestApplication from "./project/rest/application/RestApplication";
import RestResource from "./project/rest/resource/RestResource";
import RestMethod from "./project/rest/method/RestMethod";
import RestMockResponse from "./project/rest/mockresponse/RestMockResponse";
import UserOverview from "./user/UserOverview";
import User from "./user/User";
import Profile from "./profile/Profile";
import System from "./system/System";
import EventOverview from "./event/EventOverview"
import axios from "axios";
import AuthenticationContext from "../context/AuthenticationContext"

class MainContainer extends PureComponent {

    constructor(props) {
        super(props);
        this.getUser = this.getUser.bind(this);
    }

    componentDidMount() {
        this.getUser(this.context);
    }

    getUser(context) {
        axios
            .get("/api/rest/core/profile")
            .then(response => {
                context.updateAuthentication({
                    username: response.data.username,
                    role: response.data.role
                });
            })
            .catch(error => {
                context.updateAuthentication({})
                this.props.history.push("/web/login");
            });
    }

    render() {
        return (
            <div>
                <Router>
                <Header/>
                    <div id="main-body">
                        <Switch>
                            <Route path="/web/rest/project/:projectId/application/:applicationId/resource/:resourceId/method/:methodId/mockresponse/:mockResponseId" component={RestMockResponse} /> } />
                            <Route path="/web/rest/project/:projectId/application/:applicationId/resource/:resourceId/method/:methodId" component={RestMethod} />
                            <Route path="/web/rest/project/:projectId/application/:applicationId/resource/:resourceId" component={RestResource} />
                            <Route path="/web/rest/project/:projectId/application/:applicationId" component={RestApplication} />
                            <Route path="/web/soap/project/:projectId/port/:portId/operation/:operationId/mockresponse/:mockResponseId" component={SoapMockResponse} />
                            <Route path="/web/soap/project/:projectId/port/:portId/operation/:operationId" component={SoapOperation} />
                            <Route path="/web/soap/project/:projectId/port/:portId" component={SoapPort} />
                            <Route path="/web/soap/project/:projectId/resource/:resourceId" component={SoapResource} />
                            <Route path="/web/rest/project/:projectId" component={RestProject} />
                            <Route path="/web/soap/project/:projectId" component={SoapProject} />
                            <Route path="/web/user/:userId" component={User} />
                            <Route path="/web/user" component={UserOverview} />
                            <Route path="/web/profile" component={Profile} />
                            <Route path="/web/system" component={System} />
                            <Route path="/web/event" component={EventOverview} />
                            <Route path="/web" component={ProjectOverview} />
                            <Route path="/*">
                                <Redirect to="/web" />
                            </Route>
                            <Route path="" >
                                <Redirect to="/web" />
                            </Route>
                        </Switch>
                    </div>
                    <Footer/>
                </Router>
            </div>
    );
    }
}

MainContainer.contextType = AuthenticationContext;
export default MainContainer;