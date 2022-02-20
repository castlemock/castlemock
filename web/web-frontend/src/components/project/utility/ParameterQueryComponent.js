/*
 Copyright 2021 Karl Dahlgren

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
import axios from "axios";
import ToolkitProvider from "react-bootstrap-table2-toolkit";
import BootstrapTable from "react-bootstrap-table-next";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPlus, faTrash} from "@fortawesome/free-solid-svg-icons";

class ParameterQueryComponent extends PureComponent {

    constructor(props) {
        super(props);
        this.setNewParameterParameter = this.setNewParameterParameter.bind(this);
        this.setNewParameterQuery = this.setNewParameterQuery.bind(this);
        this.setNewParameterMatchAny = this.setNewParameterMatchAny.bind(this);
        this.setNewParameterMatchCase = this.setNewParameterMatchCase.bind(this);
        this.setNewParameterMatchRegex = this.setNewParameterMatchRegex.bind(this);
        this.setNewParameterUrlEncoded = this.setNewParameterUrlEncoded.bind(this);

        this.onAddParameterQueryClick = this.onAddParameterQueryClick.bind(this);
        this.onRemoveParameterQueryClick = this.onRemoveParameterQueryClick.bind(this);
        this.deleteParameterFormat = this.deleteParameterFormat.bind(this);
        this.deleteParameterStyle = this.deleteParameterStyle.bind(this);
        this.getResourceParameters = this.getResourceParameters.bind(this);


        this.parameterQueryColumns = [
            {
                dataField: 'parameter',
                sort: false,
                formatter: this.deleteParameterFormat,
                parameterStyle: this.deleteParameterStyle
            },
            {
                dataField: 'parameter',
                text: 'Parameter',
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
            }, {
                dataField: 'urlEncoded',
                text: 'URL Encoded',
                sort: true
            }
        ];

        this.state = {
            newParameterQuery: {
                parameter: "",
                query: "",
                matchAny: false,
                matchCase: false,
                matchRegex: false,
                urlEncoded: false
            },
            resourceParameters: []
        };

        this.getResourceParameters();
    }

    onAddParameterQueryClick(){
        this.props.onParameterQueryAdded(this.state.newParameterQuery);
    }

    onRemoveParameterQueryClick(row){
        this.props.onParameterQueryRemoved(row);
    }

    deleteParameterFormat(cell, row) {
        if(cell == null){
            return;
        }

        return (
            <div className="table-delete-column">
                <FontAwesomeIcon icon={faTrash} onClick={__ => this.onRemoveParameterQueryClick(row)}/>
            </div>
        )
    }

    deleteParameterStyle() {
        return { 'whiteSpace': 'nowrap', width: '50px' };
    }

    setNewParameterParameter(parameter) {
        this.setState({ newParameterQuery: {
                ...this.state.newParameterQuery,
                parameter: parameter
            }
        });
    }

    setNewParameterQuery(query) {
        this.setState({ newParameterQuery: {
                ...this.state.newParameterQuery,
                query: query
            }
        });
    }

    setNewParameterMatchAny(matchAny) {
        this.setState({ newParameterQuery: {
                ...this.state.newParameterQuery,
                matchAny: matchAny
            }
        });
    }

    setNewParameterMatchCase(matchCase) {
        this.setState({ newParameterQuery: {
                ...this.state.newParameterQuery,
                matchCase: matchCase
            }
        });
    }

    setNewParameterMatchRegex(matchRegex) {
        this.setState({ newParameterQuery: {
                ...this.state.newParameterQuery,
                matchRegex: matchRegex
            }
        });
    }

    setNewParameterUrlEncoded(urlEncoded) {
        this.setState({ newParameterQuery: {
                ...this.state.newParameterQuery,
                urlEncoded: urlEncoded
            }
        });
    }

    getResourceParameters() {
            axios
                .get(process.env.PUBLIC_URL + "/api/rest/rest/project/" + this.props.projectId + "/application/" + this.props.applicationId + "/resource/" + this.props.resourceId + "/parameter")
                .then(response => {
                    this.setState({
                        resourceParameters: response.data
                    });
                })
                .catch(error => {
                    validateErrorResponse(error)
                });
        }

    render() {
        return (
            <div>
                <h4>Add Parameter Query</h4>
                <div>
                    <div className="form-group row">
                        <label className="col-sm-2 col-form-label">Parameter</label>
                        <div className="col-sm-10">
                            <select id="inputStatus" className="form-control" onChange={event => this.setNewParameterParameter(event.target.value)} >
                                <option value={""}> -- select a parameter -- </option>
                                {this.state.resourceParameters.map(resourceParameter =>
                                    <option key={resourceParameter.query} value={resourceParameter.query}>{resourceParameter.query}</option>
                                )};
                            </select>
                        </div>
                    </div>
                    <div className="form-group row">
                        <label className="col-sm-2 col-form-label">Query</label>
                        <div className="col-sm-10">
                            <input className="form-control" type="text" onChange={event => this.setNewParameterQuery(event.target.value)} />
                        </div>
                    </div>
                    <div className="form-group row">
                        <label className="col-sm-2 col-form-label">Match Any</label>
                        <div className="col-sm-10">
                            <input type="checkbox" onChange={event => this.setNewParameterMatchAny(event.target.checked)}/>
                        </div>
                    </div>
                    <div className="form-group row">
                        <label className="col-sm-2 col-form-label">Match Case</label>
                        <div className="col-sm-10">
                            <input type="checkbox" onChange={event => this.setNewParameterMatchCase(event.target.checked)}/>
                        </div>
                    </div>
                    <div className="form-group row">
                        <label className="col-sm-2 col-form-label">Match Regex</label>
                        <div className="col-sm-10">
                            <input type="checkbox" onChange={event => this.setNewParameterMatchRegex(event.target.checked)}/>
                        </div>
                    </div>
                    <div className="form-group row">
                        <label className="col-sm-2 col-form-label">URL Encoded</label>
                        <div className="col-sm-10">
                            <input type="checkbox" onChange={event => this.setNewParameterUrlEncoded(event.target.checked)}/>
                        </div>
                    </div>
                    <div className="form-group row">
                        <button className="btn btn-success demo-button-disabled menu-button"
                            onClick={this.onAddParameterQueryClick} disabled={this.state.newParameterQuery.parameter === ""}>
                                <FontAwesomeIcon icon={faPlus} className="button-icon"/>
                                <span>Add Parameter Query</span>
                        </button>
                    </div>
                </div>
                <div className="table-result">
                    <ToolkitProvider bootstrap4
                                     columns={ this.parameterQueryColumns}
                                     data={this.props.parameterQueries}
                                     keyField="parameter"
                                     search>
                        {
                            (props) => (
                                <div>
                                    <BootstrapTable {...props.baseProps} bootstrap4
                                                    data={this.props.parameterQueries} columns={this.parameterQueryColumns}
                                                    keyField='parameter' hover
                                                    noDataIndication="No parameter queries"/>
                                </div>
                            )}
                    </ToolkitProvider>
                </div>
            </div>
        )
    };

}

export default ParameterQueryComponent;