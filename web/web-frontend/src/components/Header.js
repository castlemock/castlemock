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
import '../css/Header.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap.js';
import {Link} from "react-router-dom";
import {faSignOutAlt, faHome, faChartBar, faUser, faUsersCog, faCogs} from '@fortawesome/free-solid-svg-icons'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import Logo from '../images/logo-landscape-white.png'
import OverlayTrigger from "react-bootstrap/OverlayTrigger";
import Tooltip from "react-bootstrap/Tooltip";
import AuthenticationContext from "../context/AuthenticationContext";
import {isAdministrator} from "../utility/AuthorizeUtility";

class Header extends Component {

    render() {
        return (
            <AuthenticationContext.Consumer>
                {context => (
                    <div className="main-header">
                        <nav className="navbar navbar-default">
                            <div className="container-fluid">
                                <div className="navbar-header">
                                    <div className="header-logo">
                                        <Link to="/web">
                                            Castle Mock
                                            <img src={Logo} className="header-logo-img" alt="logo"/>
                                        </Link>
                                    </div>
                                </div>
                                <div className="header-menu-links">
                                    <div className="header-menu-link">
                                        <OverlayTrigger placement="bottom" overlay={<Tooltip id="button-tooltip-2">Projects</Tooltip>}>
                                            <Link to={"/web"}><FontAwesomeIcon icon={faHome} className="header-menu-icon" /></Link>
                                        </OverlayTrigger>
                                    </div>
                                    <div className="header-menu-link">
                                        <OverlayTrigger placement="bottom" overlay={<Tooltip id="button-tooltip-2">{context.authentication.username}</Tooltip>}>
                                            <Link to={"/web/profile"}><FontAwesomeIcon icon={faUser} className="header-menu-icon" /></Link>
                                        </OverlayTrigger>
                                    </div>
                                    <div className="header-menu-link">
                                        <OverlayTrigger placement="bottom" overlay={<Tooltip id="button-tooltip-2">Logs</Tooltip>}>
                                            <Link to={"/web/event"}><FontAwesomeIcon icon={faChartBar} className="header-menu-icon" /></Link>
                                        </OverlayTrigger>
                                    </div>
                                    { isAdministrator(context.authentication.role) &&
                                        <div className="header-menu-link">
                                            <OverlayTrigger placement="bottom" overlay={<Tooltip id="button-tooltip-2">Users</Tooltip>}>
                                                <Link to={"/web/user"}><FontAwesomeIcon icon={faUsersCog} className="header-menu-icon"/></Link>
                                            </OverlayTrigger>
                                        </div>
                                    }
                                    { isAdministrator(context.authentication.role) &&
                                        <div className="header-menu-link">
                                            <OverlayTrigger placement="bottom" overlay={<Tooltip id="button-tooltip-2">System</Tooltip>}>
                                                <Link to={"/web/system"}><FontAwesomeIcon icon={faCogs} className="header-menu-icon" /></Link>
                                            </OverlayTrigger>
                                        </div>
                                    }
                                    <div className="header-menu-link">
                                        <OverlayTrigger placement="bottom" overlay={<Tooltip id="button-tooltip-2">Logout</Tooltip>}>
                                            <Link to={"/web/logout"}><FontAwesomeIcon icon={faSignOutAlt} className="header-menu-icon" /></Link>
                                        </OverlayTrigger>
                                    </div>
                                </div>
                            </div>
                        </nav>
                    </div>
                )}
            </AuthenticationContext.Consumer>
        );
    }
}

export default Header;