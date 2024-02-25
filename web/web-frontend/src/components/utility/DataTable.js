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
import cn from "classnames";
import { sortBy } from "underscore";

import "../../css/DataTable.css";

const SORT_ORDER_CYCLE = new Map([
    [undefined, "desc"],
    ["desc", "asc"],
    ["asc", "desc"]
]);

/**
 * Simple implementation of a Bootstrap Table with search, sorting and pagination capabilities.
 * 
 * @typedef DTColumn
 * @property {string} dataField
 * @property {string} text
 * @property {boolean} hidden
 * @property {boolean} sort
 * @property {(cell, row) => any} formatter
 * @property {() => any} headerStyle
 * 
 * @typedef {object} DTData
 * @typedef {"desc" | "asc" | undefined} DTOrder
 * @typedef {{ dataField: DTColumn["dataField"], order: DTOrder }} DTColumnSort
 * 
 * @typedef DTSelectRow
 * @property {"checkbox"} mode
 * @property {(value: DTData, mode: boolean) => void} onSelect
 * @property {(mode: boolean) => void} onSelectAll
 * 
 * @typedef {object} DTProps
 * @property {DTColumn[]} columns
 * @property {DTData[]} data
 * @property {string} keyField
 * @property {string} noDataIndication
 * @property {boolean} search
 * @property {DTSelectRow} selectRow
 * @property {DTColumnSort[]} defaultSort
 * 
 * @extends {PureComponent<DTProps>}
 */
class DataTable extends PureComponent {

    constructor(props) {
        super(props);
        this.onRowSelect = this.onRowSelect.bind(this);
        this.onRowSelectAll = this.onRowSelectAll.bind(this);
        this.onSearchInput = this.onSearchInput.bind(this);
        this.onColumnSort = this.onColumnSort.bind(this);
        this.getFilteredData = this.getFilteredData.bind(this);

        if (this.props.defaultSort?.length > 1) {
            console.warn("DataTable : 'defaultSort' attribute doesn't (yet) support multiple columns");
        }

        this.state = {
            selectedKeys: new Set(),
            allSelected: false,
            searchText: "",
            columnSort: this.props.defaultSort?.slice(0, 1) || [],
        };
    }

    onRowSelect(row) {
        const rowKey = row[this.props.keyField];
        const selected = !this.state.selectedKeys.has(rowKey);
        const newSelectedKeys = new Set(this.state.selectedKeys);
        if (selected) {
            newSelectedKeys.add(rowKey)
        } else {
            newSelectedKeys.delete(rowKey);
        }
        const newAllSelected = newSelectedKeys.size === this.props.data.length;
        this.setState({
            selectedKeys: newSelectedKeys,
            allSelected: newAllSelected,
        });
        this.props.selectRow.onSelect(row, selected);
    }

    onRowSelectAll() {
        const newAllSelected = !this.state.allSelected;
        const newSelectedKeys = newAllSelected
            ? new Set(this.props.data.map(row => row[this.props.keyField]))
            : new Set();
        this.setState({
            selectedKeys: newSelectedKeys,
            allSelected: newAllSelected,
        });
        this.props.selectRow.onSelectAll(newAllSelected);
    }

    onSearchInput(inputEvent) {
        this.setState({
            searchText: inputEvent.target.value.toLowerCase(),
        });
    }

    onColumnSort(column) {
        if (!column.sort) {
            return false;
        }
        const currentSort = this.state.columnSort.find(sort => sort.dataField === column.dataField);
        const newColumnSort = [{
            dataField: column.dataField,
            order: SORT_ORDER_CYCLE.get(currentSort?.order),
        }];
        this.setState({ columnSort: newColumnSort });
    }

    getFilteredData(data, searchText) {
        let filtered = searchText
            ? data.filter(row => this.props.columns
                .some(column => row[column.dataField]?.toLowerCase()?.includes(searchText)))
            : data;
        const columnSort = this.state.columnSort[0];
        if (columnSort) {
            const fieldExtractor = (row) => row[columnSort.dataField]?.toLowerCase();
            if (columnSort.order === "asc") {
                filtered = sortBy(filtered, fieldExtractor);
            } else if (columnSort.order === "desc") {
                filtered = sortBy(filtered, fieldExtractor).reverse();
            }
        }
        return filtered;
    }

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
                                <th style={{ width: 0 }} onClick={() => this.onRowSelectAll()}>
                                    <FormCheck
                                        className="m-0"
                                        inline
                                        checked={this.state.allSelected}
                                        onChange={() => this.onRowSelectAll()}
                                    ></FormCheck>
                                </th>
                            )}
                            {visibleColumns.map((column) => {
                                const sort = this.state.columnSort.find(sort => sort.dataField === column.dataField);
                                return (
                                    <th
                                        key={column.dataField}
                                        style={column.headerStyle?.() || {}}
                                        className={cn({ sortable: column.sort }, sort?.order)}
                                        tabIndex={0}
                                        onKeyUp={event => event.key === "Enter" && this.onColumnSort(column)}
                                        onClick={() => this.onColumnSort(column)}
                                    > {column.text}<span className="sort-icons"></span>
                                    </th>
                                )
                            })}
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
                                    const rowKey = row[this.props.keyField];
                                    return (
                                        <tr key={rowKey}>
                                            {this.props.selectRow && (
                                                <td style={{ width: 0 }} onClick={() => this.onRowSelect(row)}>
                                                    <FormCheck
                                                        className="m-0"
                                                        inline
                                                        checked={this.state.selectedKeys.has(rowKey)}
                                                        onChange={() => this.onRowSelect(row)}
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
