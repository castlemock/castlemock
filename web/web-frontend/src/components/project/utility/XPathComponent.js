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
import DataTable from "../../utility/DataTable";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPlus, faTrash} from "@fortawesome/free-solid-svg-icons";

class XPathComponent extends PureComponent {

    constructor(props) {
        super(props);
        this.setNewExpression = this.setNewExpression.bind(this);
        this.onAddXPathClick = this.onAddXPathClick.bind(this);
        this.onRemoveXPathClick = this.onRemoveXPathClick.bind(this);
        this.deleteHeaderFormat = this.deleteHeaderFormat.bind(this);
        this.deleteHeaderStyle = this.deleteHeaderStyle.bind(this);

        this.xpathColumns = [
            {
                dataField: 'expression',
                sort: false,
                formatter: this.deleteHeaderFormat,
                headerStyle: this.deleteHeaderStyle
            }, {
                dataField: 'expression',
                text: 'XPath',
                sort: true
            }
        ];

        this.state = {
            expression: ""
        };
    }


    onAddXPathClick(){
        this.props.onXPathAdded({
            expression: this.state.expression
        });
    }

    onRemoveXPathClick(row){
        this.props.onXPathRemoved(row);
    }

    deleteHeaderFormat(cell, row) {
        if(cell == null){
            return;
        }

        return (
            <div className="table-delete-column">
                <FontAwesomeIcon icon={faTrash} onClick={__ => this.onRemoveXPathClick(row)}/>
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
                <h4>Add XPath</h4>
                <div>
                    <div className="form-group row">
                        <label className="col-sm-2 col-form-label">Expression</label>
                        <div className="col-sm-10">
                            <input className="form-control" type="text" onChange={this.setNewExpression} />
                        </div>
                    </div>
                    <div className="form-group row">
                        <button className="btn btn-success demo-button-disabled menu-button" onClick={this.onAddXPathClick}><FontAwesomeIcon icon={faPlus} className="button-icon"/><span>Add XPath</span></button>
                    </div>
                </div>
                <div className="table-result">
                    <DataTable
                        columns={this.xpathColumns}
                        data={this.props.xpathExpressions}
                        keyField="expression"
                        search
                        defaultSort={this.defaultSort}
                        noDataIndication="No XPaths"
                        selectRow={this.selectRow}
                    ></DataTable>
                </div>
            </div>
        )
    };

}

export default XPathComponent;