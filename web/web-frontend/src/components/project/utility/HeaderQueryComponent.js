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

import React, {PureComponent} from "react";
// import ToolkitProvider from "react-bootstrap-table2-toolkit";
// import BootstrapTable from "react-bootstrap-table-next";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPlus, faTrash} from "@fortawesome/free-solid-svg-icons";

class HeaderQueryComponent extends PureComponent {

    constructor(props) {
        super(props);
        this.setNewHeaderHeader = this.setNewHeaderHeader.bind(this);
        this.setNewHeaderQuery = this.setNewHeaderQuery.bind(this);
        this.setNewHeaderMatchAny = this.setNewHeaderMatchAny.bind(this);
        this.setNewHeaderMatchCase = this.setNewHeaderMatchCase.bind(this);
        this.setNewHeaderMatchRegex = this.setNewHeaderMatchRegex.bind(this);

        this.onAddHeaderQueryClick = this.onAddHeaderQueryClick.bind(this);
        this.onRemoveHeaderQueryClick = this.onRemoveHeaderQueryClick.bind(this);
        this.deleteHeaderFormat = this.deleteHeaderFormat.bind(this);
        this.deleteHeaderStyle = this.deleteHeaderStyle.bind(this);

        this.headerQueryColumns = [
            {
                dataField: 'header',
                sort: false,
                formatter: this.deleteHeaderFormat,
                headerStyle: this.deleteHeaderStyle
            },
            {
                dataField: 'header',
                text: 'Header',
                sort: true
            }, {
                dataField: 'query',
                text: 'Query',
                sort: true
            }, {
                dataField: 'matchAny',
                text: 'Match Any',
                sort: true
            }, {
                dataField: 'matchCase',
                text: 'Match Case',
                sort: true
            }, {
                dataField: 'matchRegex',
                text: 'Match Regex',
                sort: true
            }
        ];

        this.state = {
            newHeaderQuery: {
                header: "",
                query: "",
                matchAny: false,
                matchCase: false,
                matchRegex: false
            }
        };
    }

    onAddHeaderQueryClick(){
        this.props.onHeaderQueryAdded(this.state.newHeaderQuery);
    }

    onRemoveHeaderQueryClick(row){
        this.props.onHeaderQueryRemoved(row);
    }

    deleteHeaderFormat(cell, row) {
        if(cell == null){
            return;
        }

        return (
            <div className="table-delete-column">
                <FontAwesomeIcon icon={faTrash} onClick={__ => this.onRemoveHeaderQueryClick(row)}/>
            </div>
        )
    }

    deleteHeaderStyle() {
        return { 'whiteSpace': 'nowrap', width: '50px' };
    }

    setNewHeaderHeader(header) {
        this.setState({ newHeaderQuery: {
                ...this.state.newHeaderQuery,
                header: header
            }
        });
    }

    setNewHeaderQuery(query) {
        this.setState({ newHeaderQuery: {
                ...this.state.newHeaderQuery,
                query: query
            }
        });
    }

    setNewHeaderMatchAny(matchAny) {
        this.setState({ newHeaderQuery: {
                ...this.state.newHeaderQuery,
                matchAny: matchAny
            }
        });
    }

    setNewHeaderMatchCase(matchCase) {
        this.setState({ newHeaderQuery: {
                ...this.state.newHeaderQuery,
                matchCase: matchCase
            }
        });
    }

    setNewHeaderMatchRegex(matchRegex) {
        this.setState({ newHeaderQuery: {
                ...this.state.newHeaderQuery,
                matchRegex: matchRegex
            }
        });
    }

    render() {
        return (
            <div>
                <h4>Add Header Query</h4>
                <div>
                    <div className="form-group row">
                        <label className="col-sm-2 col-form-label">Header</label>
                        <div className="col-sm-10">
                            <input className="form-control" type="text" onChange={event => this.setNewHeaderHeader(event.target.value)} />
                        </div>
                    </div>
                    <div className="form-group row">
                        <label className="col-sm-2 col-form-label">Query</label>
                        <div className="col-sm-10">
                            <input className="form-control" type="text" onChange={event => this.setNewHeaderQuery(event.target.value)} />
                        </div>
                    </div>
                    <div className="form-group row">
                        <label className="col-sm-2 col-form-label">Match Any</label>
                        <div className="col-sm-10">
                            <input type="checkbox" onChange={event => this.setNewHeaderMatchAny(event.target.checked)}/>
                        </div>
                    </div>
                    <div className="form-group row">
                        <label className="col-sm-2 col-form-label">Match Case</label>
                        <div className="col-sm-10">
                            <input type="checkbox" onChange={event => this.setNewHeaderMatchCase(event.target.checked)}/>
                        </div>
                    </div>
                    <div className="form-group row">
                        <label className="col-sm-2 col-form-label">Match Regex</label>
                        <div className="col-sm-10">
                            <input type="checkbox" onChange={event => this.setNewHeaderMatchRegex(event.target.checked)}/>
                        </div>
                    </div>
                    <div className="form-group row">
                        <button className="btn btn-success demo-button-disabled menu-button" onClick={this.onAddHeaderQueryClick}><FontAwesomeIcon icon={faPlus} className="button-icon"/><span>Add Header Query</span></button>
                    </div>
                </div>
                <div className="table-result">
                    {/* <ToolkitProvider bootstrap4
                                     columns={ this.headerQueryColumns}
                                     data={this.props.headerQueries}
                                     keyField="header"
                                     search>
                        {
                            (props) => (
                                <div>
                                    <BootstrapTable {...props.baseProps} bootstrap4
                                                    data={this.props.headerQueries} columns={this.headerQueryColumns}
                                                    keyField='header' hover
                                                    noDataIndication="No header queries"/>
                                </div>
                            )}
                    </ToolkitProvider> */}
                </div>
            </div>
        )
    };

}

export default HeaderQueryComponent;