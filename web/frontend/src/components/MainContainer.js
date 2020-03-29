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
import ProjectOverview from './ProjectOverview'
import RestProject from './RestProject'

class MainContainer extends PureComponent {

    constructor(props) {
        super(props);
        this.isAuthenticated = this.isAuthenticated.bind(this);
    }

    isAuthenticated(){

    }

    render() {

        if(this.isAuthenticated()){

        }

        return (
            <div>
                <Header/>
                <div id="main-body">
                    <Router>
                        <Switch>
                            <Route path="/beta/web/rest/project/:projectId"  component={RestProject} />
                            <Route path="/beta/web"  component={ProjectOverview} />
                            <Route path="/beta/*">
                                <Redirect to="/beta/web" />
                            </Route>
                            <Route path="/beta" >
                                <Redirect to="/beta/web" />
                            </Route>
                        </Switch>
                    </Router>
                </div>
                <Footer/>
            </div>
    );
    }
}

export default MainContainer