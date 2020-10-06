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
import ProjectOverview from './project/ProjectOverview'
import RestProject from './project/rest/RestProject'
import SoapProject from './project/soap/SoapProject'
import SoapPort from './project/soap/SoapPort'
import SoapOperation from './project/soap/SoapOperation'
import SoapMockResponse from './project/soap/SoapMockResponse'
import RestApplication from "./project/rest/RestApplication";
import RestResource from "./project/rest/RestResource";
import RestMethod from "./project/rest/RestMethod";
import RestMockResponse from "./project/rest/RestMockResponse";
import UserOverview from "./user/UserOverview";
import User from "./user/User";
import System from "./system/System";

class MainContainer extends PureComponent {

    constructor(props) {
        super(props);
        this.isAuthenticated = this.isAuthenticated.bind(this);
        this.validateErrorResponse = this.validateErrorResponse.bind(this);

        this.state = {
            authenticated: true
        };
    }

    validateErrorResponse(error){
        if (error.response.status === 401 || error.response.status === 403) {
            this.setState({ authenticated: false });
        }
    }

    isAuthenticated(){
        return this.state.authenticated;
    }

    render() {
        if(!this.isAuthenticated()){
            return <Redirect to = {{ pathname: "/beta/web/login" }} />;
        }

        return (
            <div>
                <Router>
                <Header/>
                    <div id="main-body">
                        <Switch>
                            <Route path="/beta/web/rest/project/:projectId/application/:applicationId/resource/:resourceId/method/:methodId/mockresponse/:mockResponseId" component={RestMockResponse} /> } />
                            <Route path="/beta/web/rest/project/:projectId/application/:applicationId/resource/:resourceId/method/:methodId" component={RestMethod} />
                            <Route path="/beta/web/rest/project/:projectId/application/:applicationId/resource/:resourceId" component={RestResource} />
                            <Route path="/beta/web/rest/project/:projectId/application/:applicationId" component={RestApplication} />
                            <Route path="/beta/web/soap/project/:projectId/port/:portId/operation/:operationId/mockresponse/:mockResponseId" component={SoapMockResponse} />
                            <Route path="/beta/web/soap/project/:projectId/port/:portId/operation/:operationId" component={SoapOperation} />
                            <Route path="/beta/web/soap/project/:projectId/port/:portId" component={SoapPort} />
                            <Route path="/beta/web/rest/project/:projectId" component={RestProject} />
                            <Route path="/beta/web/soap/project/:projectId" component={SoapProject} />
                            <Route path="/beta/web/user/:userId" component={User} />
                            <Route path="/beta/web/user" component={UserOverview} />
                            <Route path="/beta/web/system" component={System} />
                            <Route path="/beta/web" component={ProjectOverview} />
                            <Route path="/beta/*">
                                <Redirect to="/beta/web" />
                            </Route>
                            <Route path="/beta" >
                                <Redirect to="/beta/web" />
                            </Route>
                        </Switch>
                    </div>
                    <Footer/>
                </Router>
            </div>
    );
    }
}

export default MainContainer