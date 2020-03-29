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
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
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
import MainContainer from './MainContainer'

class App extends Component {

    render() {
        return (
            <div className = "site-wrapper">
                <Router>
                    <Switch>
                        <Route path="/beta/login"  component={LoginContainer} />
                        <Route path="/beta/web/*"  component={MainContainer} />
                        <Route path="/beta/*"  component={MainContainer} />
                        <Route path="/beta"  component={MainContainer} />
                    </Switch>
                </Router>
            </div>
        );
    }
}

export default App;
