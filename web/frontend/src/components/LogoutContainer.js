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
import axios from "axios";
import {Redirect} from "react-router-dom";
import AuthenticationContext from "../context/AuthenticationContext";
import MainContainer from "./MainContainer";

class LogoutContainer extends PureComponent {

    constructor(props) {
        super(props);
        this.logout = this.logout.bind(this);

    }

    componentDidMount() {
        this.logout();
    }

    logout() {
        axios
            .get("/castlemock/api/rest/core/logout/")
            .then(response => {
            })
            .catch(error => {
            });
    }

    render() {
        return <Redirect to = {{ pathname: "/castlemock/web/login" }} />
    }
}

MainContainer.contextType = AuthenticationContext;
export default LogoutContainer;