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
import Logo from '../images/logo-landscape-white.png'


class Header extends Component {

    render() {
        return (
            <div className="main-header">
                <nav className="navbar navbar-default">
                    <div className="container-fluid">
                        <div className="navbar-header">
                            <a className="navbar-brand header-logo" href="/web">Castle Mock <img
                                src={Logo} className="header-logo-img" alt="logo"/></a>
                        </div>
                    </div>

                </nav>
            </div>
        );
    }
}

/*
                      <div className="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                          <ul className="links nav navbar-nav navbar-right">

                              <li className="header-menu-full-row">
                                  <div>
                                      <a href="/web/me">
                                          <div className="avatar-username">
                                          </div>
                                          <div className="avatar-circle">
                                              <div className="avatar-inner-circle">
                                                  <i className="fas fa-user fa-2x"/>
                                              </div>
                                          </div>
                                      </a>
                                  </div>
                              </li>


                              <div className="header-menu-collapse">
                                  <li className="header-menu-collapse-row">
                                      <a href="/web">
                                          <div className="header-menu-icon">
                                              <i className="fas fa-tachometer-alt fa-2x"/>
                                          </div>
                                          <div className="header-menu-title">
                                              Projects
                                          </div>
                                      </a>
                                  </li>
                                  <li className="header-menu-collapse-row">
                                      <a href="/web/event">
                                          <div className="header-menu-icon">
                                              <i className="fas fa-chart-bar fa-2x"/>
                                          </div>
                                          <div className="header-menu-title">
                                              Logs
                                          </div>
                                      </a>
                                  </li>

                                  <li className="header-menu-collapse-row">
                                      <a href="/web/user">
                                          <div className="header-menu-icon">
                                              <i className="fas fa-users-cog fa-2x"/>
                                          </div>
                                          <div className="header-menu-title">
                                              Users
                                          </div>
                                      </a>
                                  </li>

                                  <li className="header-menu-collapse-row">
                                      <a href="/web/system">
                                          <div className="header-menu-icon">
                                              <i className="fas fas fa-cogs fa-2x"/>
                                          </div>
                                          <div className="header-menu-title">
                                              System
                                          </div>
                                      </a>
                                  </li>

                                  <li className="header-menu-collapse-row">
                                      <a href="/web/logout">
                                          <div className="header-menu-icon">
                                              <i className="fas fas fa-sign-out-alt fa-2x"/>
                                          </div>
                                          <div className="header-menu-title">
                                              Logout
                                          </div>
                                      </a>
                                  </li>
                              </div>

                              <li className="header-menu-full-row"><a href="/web/logout"><i
                                  className="fas fa-sign-out-alt fa-2x"/></a></li>
                          </ul>
                      </div>
                      */

export default Header;
