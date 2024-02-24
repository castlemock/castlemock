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

class JsonPathComponent extends PureComponent {

    constructor(props) {
        super(props);
        this.setNewExpression = this.setNewExpression.bind(this);
        this.onAddJsonPathClick = this.onAddJsonPathClick.bind(this);
        this.onRemoveJsonPathClick = this.onRemoveJsonPathClick.bind(this);
        this.deleteHeaderFormat = this.deleteHeaderFormat.bind(this);
        this.deleteHeaderStyle = this.deleteHeaderStyle.bind(this);

        this.jsonPathColumns = [
            {
                dataField: 'expression',
                sort: false,
                formatter: this.deleteHeaderFormat,
                headerStyle: this.deleteHeaderStyle
            }, {
                dataField: 'expression',
                text: 'JsonPath',
                sort: true
            }
        ];

        this.state = {
            expression: ""
        };
    }


    onAddJsonPathClick(){
        this.props.onJsonPathAdded({
            expression: this.state.expression
        });
    }

    onRemoveJsonPathClick(row){
        this.props.onJsonPathRemoved(row);
    }

    deleteHeaderFormat(cell, row) {
        if(cell == null){
            return;
        }

        return (
            <div className="table-delete-column">
                <FontAwesomeIcon icon={faTrash} onClick={__ => this.onRemoveJsonPathClick(row)}/>
            </div>
        )
    }

    deleteHeaderStyle() {
        return { 'whiteSpace': 'nowrap', width: '50px' };
    }

    setNewExpression(source) {
        this.setState({
            expression: source.target.value
        });
    }

    render() {
        return (
            <div>
                <h4>Add JsonPath</h4>
                <div>
                    <div className="form-group row">
                        <label className="col-sm-2 col-form-label">Expression</label>
                        <div className="col-sm-10">
                            <input className="form-control" type="text" onChange={this.setNewExpression} />
                        </div>
                    </div>
                    <div className="form-group row">
                        <button className="btn btn-success demo-button-disabled menu-button" onClick={this.onAddJsonPathClick}><FontAwesomeIcon icon={faPlus} className="button-icon"/><span>Add JSON Path</span></button>
                    </div>
                </div>
                <div className="table-result">
                    {/* <ToolkitProvider bootstrap4
                                     columns={ this.jsonPathColumns}
                                     data={this.props.jsonPathExpressions}
                                     keyField="name"
                                     search>
                        {
                            (props) => (
                                <div>
                                    <BootstrapTable {...props.baseProps} bootstrap4
                                                    data={this.props.jsonPathExpressions} columns={this.jsonPathColumns}
                                                    defaultSorted={this.defaultSort} keyField='expression' hover
                                                    noDataIndication="No JsonPaths"
                                                    selectRow={this.selectRow}/>
                                </div>
                            )}
                    </ToolkitProvider> */}
                </div>
            </div>
        )
    };

}

export default JsonPathComponent;