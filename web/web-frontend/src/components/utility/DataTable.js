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

import React, { PureComponent } from "react";
import FormCheck from "react-bootstrap/FormCheck";
import Table from "react-bootstrap/Table";
import FormControl from "react-bootstrap/FormControl"
import { memoize } from "underscore";

/**
 * Simple implementation of a Bootstrap Table with search, sorting and pagination capabilities.
 * 
 * @typedef DataTableColumn
 * @property {string} dataField
 * @property {string} text
 * @property {boolean} hidden
 * @property {(cell, row) => any} formatter
 * @property {() => any} headerStyle
 * 
 * @typedef {object} DataTableData
 * 
 * @typedef DataTableSelectRow
 * @property {"checkbox"} mode
 * @property {(value: DataTableData, mode: boolean) => void} onSelect
 * @property {(mode: boolean) => void} onSelectAll
 * 
 * @typedef {object} DataTableProps
 * @property {DataTableColumn[]} columns
 * @property {DataTableData[]} data
 * @property {string} keyField
 * @property {string} noDataIndication
 * @property {boolean} search
 * @property {DataTableSelectRow} selectRow
 * 
 * @extends {PureComponent<DataTableProps>}
 */
class DataTable extends PureComponent {

    constructor(props) {
        super(props);
        this.onRowSelect = this.onSelect.bind(this);
        this.onSelectAll = this.onSelectAll.bind(this);
        this.onSearchInput = this.onSearchInput.bind(this);

        this.state = {
            selectedIds: new Set(),
            allSelected: false,
            searchText: "",
        };
    }

    onSelect(row) {
        const id = row[this.props.keyField];
        const selected = !this.state.selectedIds.has(id);
        const newSelectedIds = new Set(this.state.selectedIds);
        if (selected) {
            newSelectedIds.add(id)
        } else {
            newSelectedIds.delete(id);
        }
        const newAllSelected = newSelectedIds.size === this.props.data.length;
        this.setState({
            selectedIds: newSelectedIds,
            allSelected: newAllSelected,
        });
        this.props.selectRow.onSelect(row, selected);
    }

    onSelectAll() {
        const newAllSelected = !this.state.allSelected;
        const newSelectedIds = newAllSelected
            ? new Set(this.props.data.map(row => row[this.props.keyField]))
            : new Set();
        this.setState({
            selectedIds: newSelectedIds,
            allSelected: newAllSelected,
        });
        this.props.selectRow.onSelectAll(newAllSelected);
    }

    onSearchInput(inputEvent) {
        this.setState({
            searchText: inputEvent.target.value.toLowerCase(),
        });
    }

    getFilteredData = memoize(
        (data, searchText) => {
            return searchText
                ? data.filter(row => this.props.columns
                    .some(column => row[column.dataField]?.toLowerCase()?.includes(searchText)))
                : data;
        },
        (data, searchText) => {
            return data.map(row => row[this.props.keyField]).join(",") + "-" + searchText;
        }
    );

    render() {
        const visibleColumns = this.props.columns.filter(column => !column.hidden);
        const totalColumns = visibleColumns.length + (this.props.selectRow ? 1 : 0);
        const filteredData = this.getFilteredData(this.props.data, this.state.searchText);

        return (
            <>
                {
                    this.props.search && <FormControl
                        className="table-filter-field"
                        placeholder="Search"
                        onInput={this.onSearchInput}
                    ></FormControl>
                }
                <Table bordered striped>
                    <thead>
                        <tr>
                            {this.props.selectRow && (
                                <th style={{ width: 0 }} onClick={() => this.onSelectAll()}>
                                    <FormCheck
                                        className="m-0"
                                        inline
                                        checked={this.state.allSelected}
                                        onChange={() => this.onSelectAll()}
                                    ></FormCheck>
                                </th>
                            )}
                            {visibleColumns.map((column) => (
                                <th key={column.dataField} style={column.headerStyle?.() || {}}>{column.text}</th>
                            ))}
                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.props.data.length === 0 ? (
                                <tr>
                                    <td className="text-center" colSpan={totalColumns}>{this.props.noDataIndication}</td>
                                </tr>
                            ) : filteredData.length === 0 ? (
                                <tr>
                                    <td className="text-center" colSpan={totalColumns}>No search results</td>
                                </tr>
                            ) : (
                                filteredData.map((row) => {
                                    const rowId = row[this.props.keyField];
                                    return (
                                        <tr key={rowId}>
                                            {this.props.selectRow && (
                                                <td style={{ width: 0 }} onClick={() => this.onSelect(row)}>
                                                    <FormCheck
                                                        className="m-0"
                                                        inline
                                                        checked={this.state.selectedIds.has(rowId)}
                                                        onChange={() => this.onSelect(row)}
                                                    ></FormCheck>
                                                </td>
                                            )}
                                            {visibleColumns.map((column) => {
                                                const cell = row[column.dataField];
                                                return (
                                                    <td key={column.dataField}>
                                                        {column.formatter ? column.formatter(cell, row) : cell}
                                                    </td>
                                                );
                                            })}
                                        </tr>
                                    )
                                })
                            )}
                    </tbody>
                </Table>
            </>
        );
    }
}

export default DataTable;
