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

class HeaderComponent extends PureComponent {

    constructor(props) {
        super(props);
        this.setNewHeaderName = this.setNewHeaderName.bind(this);
        this.setNewHeaderValue = this.setNewHeaderValue.bind(this);
        this.onAddHeaderClick = this.onAddHeaderClick.bind(this);
        this.onRemoveHeaderClick = this.onRemoveHeaderClick.bind(this);
        this.deleteHeaderFormat = this.deleteHeaderFormat.bind(this);
        this.deleteHeaderStyle = this.deleteHeaderStyle.bind(this);

        this.headerColumns = [
            {
                dataField: 'name',
                sort: false,
                formatter: this.deleteHeaderFormat,
                headerStyle: this.deleteHeaderStyle
            }, {
                dataField: 'name',
                text: 'Name',
                sort: true
            }, {
                dataField: 'value',
                text: 'Value',
                sort: true
            }
        ];

        this.state = {
            newHeader: {

            }
        };
    }


    onAddHeaderClick(){
        this.props.onHeaderAdded(this.state.newHeader);
    }

    onRemoveHeaderClick(row){
        this.props.onHeaderRemoved(row)
    }

    deleteHeaderFormat(cell, row) {
        if(cell == null){
            return;
        }

        return (
            <div className="table-delete-column">
                <FontAwesomeIcon icon={faTrash} onClick={__ => this.onRemoveHeaderClick(row)}/>
            </div>
        )
    }

    deleteHeaderStyle() {
        return { 'whiteSpace': 'nowrap', width: '50px' };
    }

    setNewHeaderName(source) {
        this.setState({
            newHeader: {
                ...this.state.newHeader,
                name: source.target.value
            }
        });
    }

    setNewHeaderValue(source) {
        this.setState({
            newHeader: {
                ...this.state.newHeader,
                value: source.target.value
            }
        });
    }



    render() {
        return (
            <div>
                <h4>Add header</h4>
                <div>
                    <div className="form-group row">
                        <label className="col-sm-2 col-form-label">Name</label>
                        <div className="col-sm-10">
                            <input className="form-control" type="text" onChange={this.setNewHeaderName} />
                        </div>
                    </div>
                    <div className="form-group row">
                        <label className="col-sm-2 col-form-label">Value</label>
                        <div className="col-sm-10">
                            <input className="form-control" type="text" onChange={this.setNewHeaderValue} />
                        </div>
                    </div>
                    <div className="form-group row">
                        <button className="btn btn-success demo-button-disabled menu-button" onClick={this.onAddHeaderClick}><FontAwesomeIcon icon={faPlus} className="button-icon"/><span>Add header</span></button>
                    </div>
                </div>
                <div className="table-result">
                    {/* <ToolkitProvider bootstrap4
                                     columns={ this.headerColumns}
                                     data={this.props.httpHeaders}
                                     keyField="name"
                                     search>
                        {
                            (props) => (
                                <div>
                                    <BootstrapTable {...props.baseProps} bootstrap4
                                                    data={this.props.httpHeaders} columns={this.headerColumns}
                                                    defaultSorted={this.defaultSort} keyField='name' hover
                                                    noDataIndication="No headers"
                                                    selectRow={this.selectRow}/>
                                </div>
                            )}
                    </ToolkitProvider> */}
                </div>
            </div>
        )
    };

}

export default HeaderComponent;