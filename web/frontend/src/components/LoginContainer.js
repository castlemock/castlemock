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

class LoginContainer extends PureComponent {
    render() {
        return (
            <div id="login-body">
                <div className="login">
                    <div id="login-box">

                        <img src={Logo} id="logo"  alt="Castle Mock Logo"/>
                        <div id="logo-title">Castle Mock</div>
                        <div id="logo-meta-text">Login with your Castle Mock ID</div>

                        <form name='loginForm' action="/login" method='POST'>
                            <input className="form-control login-credentials" type="text" name="username" id="username" placeholder="Username"/>
                            <input className="form-control login-credentials" type="password" name="password" id="password" placeholder="Password"/>
                            <div id="login-remember-me">
                                <input className="form-check-input" id="rememberMeCheck" type="checkbox" name="remember-me" />
                                <label className="form-check-label" for="rememberMeCheck">Keep me signed in</label>
                            </div>
                            <div id="login-button">
                                <button className="btn btn-success" type="submit" name="submit">Login</button>
                            </div>
                        </form>
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