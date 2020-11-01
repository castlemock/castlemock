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
import '../css/Login.css';
import Logo from '../images/logo.png'
import axios from "axios";
import {Redirect} from "react-router-dom";
import AuthenticationContext from "../context/AuthenticationContext";
import VersionContext from "../context/VersionContext";

class LoginContainer extends PureComponent {

    constructor(props) {
        super(props);
        this.onButtonLoginClick = this.onButtonLoginClick.bind(this);
        this.setUsername = this.setUsername.bind(this);
        this.setPassword = this.setPassword.bind(this);
        this.isAuthenticated = this.isAuthenticated.bind(this);
        this.onEnterClick = this.onEnterClick.bind(this);

        this.state = {
            username: "",
            password: "",
            authenticated: false,
            authentication: {}
        };
    }

    setUsername(username) {
        this.setState({ username: username });
    }

    setPassword(password) {
        this.setState({ password: password });
    }

    onButtonLoginClick(context) {
        axios
            .post("/castlemock/api/rest/core/login", {
                username: this.state.username,
                password: this.state.password
            })
            .then(response => {
                context.updateAuthentication({
                    token: response.data.token,
                    username: response.data.username,
                    role: response.data.role
                });
                this.setState({
                    authenticated: true
                })
            })
            .catch(error => {
            });
    }

    onEnterClick(event, context) {
        if (event.key === 'Enter') {
            this.onButtonLoginClick(context);
        }
    }

    isAuthenticated(){
        return this.state.authenticated;
    }

    render() {
        if(this.isAuthenticated()){
            return <Redirect to = {{ pathname: "/castlemock/web" }} />
        }

        return (
            <AuthenticationContext.Consumer>
                {context => (
                    <div id="login-body">
                        <div className="login">
                            <div id="login-box">
                                <div className="logoImage">
                                    <img src={Logo} id="logo" alt="Castle Mock Logo"/>
                                </div>
                                <div className="credentialsBox">
                                    <div className="login-title">Castle Mock</div>

                                    <div className="alert alert-danger" role="alert" hidden={this.state.loginFailed ? '' : 'hidden'}>
                                        Unable to login.
                                    </div>

                                    <div className="form-label-group">
                                        <input type="text" id="inputUsername" className="form-control" placeholder="Username" onChange={event => this.setUsername(event.target.value)} onKeyDown={event => this.onEnterClick(event, context)} required autoFocus/>
                                    </div>
                                    <div className="form-label-group">
                                        <input type="password" id="inputPassword" className="form-control" placeholder="Password" onChange={event => this.setPassword(event.target.value)} onKeyDown={event => this.onEnterClick(event, context)} required />
                                    </div>
                                    <button className="btn btn-lg btn-success btn-block text-uppercase" onClick={event => this.onButtonLoginClick(context)}>Sign in</button>
                                </div>
                            </div>
                        </div>

                        <VersionContext.Consumer>
                            {version => (
                                <div id="login-footer">
                                    <div id="login-footer-info">
                                        <a href="https://www.castlemock.com" target="_blank" rel="noopener noreferrer">Castle Mock version. {version}</a>
                                    </div>

                                    <div id="login-footer-info-api">
                                        <a href="/doc/castlemock/api/rest" target="_blank" rel="noopener noreferrer">REST API</a>
                                    </div>
                                </div>
                            )}
                        </VersionContext.Consumer>
                    </div>
                )}
            </AuthenticationContext.Consumer>
    );
    }
}

export default LoginContainer;