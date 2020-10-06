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

class LoginContainer extends PureComponent {

    constructor(props) {
        super(props);
        this.onButtonLoginClick = this.onButtonLoginClick.bind(this);
        this.setUsername = this.setUsername.bind(this);
        this.setPassword = this.setPassword.bind(this);

        this.state = {
            username: "",
            password: "",
            authenticated: false
        };
    }

    setUsername(username) {
        this.setState({ username: username });
    }

    setPassword(password) {
        this.setState({ password: password });
    }

    onButtonLoginClick() {
        axios
            .post("/api/rest/core/login", {
                username: this.state.username,
                password: this.state.password
            })
            .then(response => {
                this.setState({ authenticated: true })
            })
            .catch(error => {
                console.log(error)

            });
    }

    render() {
        if(this.state.authenticated) {
            return <Redirect to = {{ pathname: "/beta/web" }} />;
        }

        return (
            <div id="login-body">
                <div className="login">
                    <div id="login-box">
                        <div className="logoImage">
                            <img src={Logo} id="logo" alt="Castle Mock Logo"/>
                        </div>
                        <div className="credentialsBox">
                            <div className="login-title">Castle Mock</div>

                            <div className="form-label-group">
                                <input type="text" id="inputUsername" className="form-control" placeholder="Username" onChange={event => this.setUsername(event.target.value)} required autoFocus/>
                            </div>
                            <div className="form-label-group">
                                <input type="password" id="inputPassword" className="form-control" placeholder="Password" onChange={event => this.setPassword(event.target.value)} required />
                            </div>
                            <button className="btn btn-lg btn-success btn-block text-uppercase" onClick={this.onButtonLoginClick}>Sign in</button>
                        </div>
                    </div>
                </div>

                <div id="login-footer">
                    <div id="login-footer-info">
                        <a href="https://www.castlemock.com" target="_blank" rel="noopener noreferrer">Castle Mock version. 1.41</a>
                    </div>

                    <div id="login-footer-info-api">
                        <a href="/doc/api/rest" target="_blank" rel="noopener noreferrer">REST API</a>
                    </div>
                </div>
            </div>
    );
    }
}

export default LoginContainer

/*


                            <input className="form-control login-credentials" type="text" name="username" id="username" placeholder="Username" onChange={event => this.setUsername(event.target.value)}/>
                            <input className="form-control login-credentials" type="password" name="password" id="password" placeholder="Password" onChange={event => this.setPassword(event.target.value)}/>

                            <div id="login-button">
                                <button className="btn btn-success" onClick={this.onButtonLoginClick}>Login</button>
                            </div>
 */