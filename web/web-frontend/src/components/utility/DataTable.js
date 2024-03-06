/*
 Copyright 2024 Karl Dahlgren

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

import React, { PureComponent, createRef } from "react";
import { FormCheck, Table, FormControl, Pagination } from "react-bootstrap";
import { get, sortBy } from "underscore";
import cn from "classnames";

import "../../css/DataTable.css";

const SORT_ORDER_CYCLE = new Map([
    [undefined, "desc"],
    ["desc", "asc"],
    ["asc", "desc"]
]);
const DEFAULT_SIZE_PER_PAGE_LIST = [10, 25, 30, 50];
const DEFAULT_SIZE_PER_PAGE = DEFAULT_SIZE_PER_PAGE_LIST[0];

function normalizeString(value) {
    return value?.toString().toLowerCase() || ""
}

function getValue(row, dottedPath) {
    return get(row, dottedPath.split("."));
}

/**
 * Custom implementation of a Bootstrap Table component with search, sorting and
 * pagination capabilities. This replaces the legacy package `react-bootstrap-table2`
 * and uses almost the same prop names but does not implement all of its features.
 *
 * @see https://react-bootstrap-table.github.io/react-bootstrap-table2/
 *
 * @typedef DTColumn
 * @property {string} dataField
 * @property {string} text
 * @property {boolean} hidden
 * @property {boolean} sort
 * @property {(cell, row) => any} formatter
 * @property {() => any} headerStyle
 * @property {number} width
 * @property {"left" | "center" | "right"} align
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
 * @typedef DTPagination
 * @property {number} sizePerPage
 * @property {number[]} sizePerPageList
 * @property {boolean} hideSizePerPage
 *
 * @typedef {object} DTProps
 * @property {DTColumn[]} columns
 * @property {DTData[]} data
 * @property {string} keyField
 * @property {string} noDataIndication
 * @property {boolean} search
 * @property {DTSelectRow} selectRow
 * @property {DTColumnSort[]} defaultSort
 * @property {boolean | DTPagination} pagination
 * 
 * @typedef DTState
 * @property {Set<string>} selectedKeys
 * @property {boolean} allSelected
 * @property {string} searchText
 * @property {DTColumnSort[]} columnSort
 * @property {number} sizePerPage
 * @property {number[]} sizePerPageList
 * @property {number} currentPageIndex
 *
 * @extends {PureComponent<DTProps, DTState>}
 */
class DataTable extends PureComponent {

    constructor(props) {
        super(props);
        this.onRowSelect = this.onRowSelect.bind(this);
        this.onRowSelectAll = this.onRowSelectAll.bind(this);
        this.onSearch = this.onSearch.bind(this);
        this.onColumnSort = this.onColumnSort.bind(this);
        this.onSizePerPageChange = this.onSizePerPageChange.bind(this);
        this.onPageChange = this.onPageChange.bind(this);
        this.filterData = this.filterData.bind(this);
        this.sortData = this.sortData.bind(this);
        this.paginateData = this.paginateData.bind(this);

        if (this.props.defaultSort?.length > 1) {
            console.warn("DataTable : 'defaultSort' prop doesn't (yet) support multiple columns");
        }
        if (!this.props.columns) {
            console.error("DataTable : 'columns' prop is mandatory");
        }
        if (!this.props.data) {
            console.error("DataTable : 'data' prop is mandatory");
        }
        if (!this.props.keyField) {
            console.error("DataTable : 'keyField' prop is mandatory");
        }

        this.selectAllCheckboxRef = createRef();

        this.state = {
            selectedKeys: new Set(),
            allSelected: false,
            searchText: "",
            columnSort: this.props.defaultSort?.slice(0, 1) || [],
            sizePerPage: this.props.pagination?.sizePerPage || DEFAULT_SIZE_PER_PAGE,
            sizePerPageList: this.props.pagination?.sizePerPageList || DEFAULT_SIZE_PER_PAGE_LIST,
            currentPageIndex: 0,
            filteredData: [],
            sortedData: [],
            paginatedData: [],
        };
    }

    /**
     * Handles a change in the selection state of a single row.
     *
     * @param {DTData} row Selected of unselected row
     */
    onRowSelect(row) {
        const rowKey = row[this.props.keyField];
        const selected = !this.state.selectedKeys.has(rowKey);
        const newSelectedKeys = new Set(this.state.selectedKeys);
        if (selected) {
            newSelectedKeys.add(rowKey)
        } else {
            newSelectedKeys.delete(rowKey);
        }
        const newAllSelected = newSelectedKeys.size === this.props.data?.length;
        this.setState({
            selectedKeys: newSelectedKeys,
            allSelected: newAllSelected,
        });
        this.props.selectRow?.onSelect(row, selected);
    }

    /**
     * Handles a change in the selection state of all rows at once.
     */
    onRowSelectAll() {
        const newAllSelected = !this.state.allSelected;
        const newSelectedKeys = newAllSelected
            ? new Set(this.props.data?.map(row => row[this.props.keyField]) || [])
            : new Set();
        this.setState({
            selectedKeys: newSelectedKeys,
            allSelected: newAllSelected,
        });
        this.props.selectRow?.onSelectAll(newAllSelected);
    }

    /**
     * Handles change events in the search input.
     *
     * @param {string} searchText Search input text
     */
    onSearch(searchText) {
        const newSearchText = normalizeString(searchText);
        const newCurrentPageIndex = 0;
        const newFilteredData = this.filterData(this.props.data, newSearchText);
        const newSortedData = this.sortData(newFilteredData, this.state.columnSort);
        const newPaginatedData = this.paginateData(newSortedData, newCurrentPageIndex, this.state.sizePerPage);
        this.setState({
            searchText: newSearchText,
            currentPageIndex: newCurrentPageIndex,
            filteredData: newFilteredData,
            sortedData: newSortedData,
            paginatedData: newPaginatedData,
        });
    }

    /**
     * Handles a change in the sort order of a column, if it is sortable.
     *
     * @param {DTColumn} column
     */
    onColumnSort(column) {
        if (!column.sort) {
            return;
        }
        const currentSort = this.state.columnSort.find(sort => sort.dataField === column.dataField);
        const newColumnSort = [{
            dataField: column.dataField,
            order: SORT_ORDER_CYCLE.get(currentSort?.order),
        }];
        const newSortedData = this.sortData(this.state.filteredData, newColumnSort);
        const newPaginatedData = this.paginateData(newSortedData, this.state.currentPageIndex, this.state.sizePerPage);
        this.setState({
            columnSort: newColumnSort,
            sortedData: newSortedData,
            paginatedData: newPaginatedData,
        });
    }

    /**
     * Handles a change in the number of element per page.
     *
     * @param {number} sizePerPage
     */
    onSizePerPageChange(sizePerPage) {
        const newCurrentPageIndex = 0;
        const newPaginatedData = this.paginateData(this.state.sortedData, newCurrentPageIndex, sizePerPage);
        this.setState({
            sizePerPage: sizePerPage,
            currentPageIndex: newCurrentPageIndex,
            paginatedData: newPaginatedData,
        });
    }

    /**
     * Handles a change of page.
     *
     * @param {number} pageIndex Index of the selected page
     */
    onPageChange(pageIndex) {
        const newPaginatedData = this.paginateData(this.state.sortedData, pageIndex, this.state.sizePerPage);
        this.setState({
            currentPageIndex: pageIndex,
            paginatedData: newPaginatedData,
        });
    }

    /**
     * React to props and state changes.
     *
     * @param {DTProps} prevProps Previous props
     * @param {DTState} prevState Previous state
     */
    componentDidUpdate(prevProps, prevState) {
        // If an element of data has been deleted, remove it from selectedKeys state
        if (prevProps.data.length !== this.props.data.length) {
            const newDataKeys = new Set(this.props.data.map(row => row[this.props.keyField]))
            const newSelectedKeys = new Set([...this.state.selectedKeys].filter(key => newDataKeys.has(key)));
            const newAllSelected = newSelectedKeys.size > 0 && newSelectedKeys.size === this.props.data.length;
            this.setState({
                selectedKeys: newSelectedKeys,
                allSelected: newAllSelected,
            });
        }
        // If selection state has changed, update indeterminate state for the "select all" checkbox
        if (prevState.selectedKeys.size !== this.state.selectedKeys.size && this.selectAllCheckboxRef.current) {
            const indeterminate = this.state.selectedKeys.size > 0 && !this.state.allSelected
            this.selectAllCheckboxRef.current.indeterminate = indeterminate;
        }
        // React to data change
        if (prevProps.data !== this.props.data) {
            this.onSearch(this.state.searchText);
        }
    }

    /**
     * Filters a data array according to the value of the search input.
     *
     * @param {DTData[]} data The data array to filter
     * @param {string} searchText Search input text
     * @returns {DTData[]} The filtered data
     */
    filterData(data, searchText) {
        let list = data;
        if (searchText) {
            list = list.filter((row) => {
                return this.props.columns?.some((column) => {
                    return normalizeString(getValue(row, column.dataField)).includes(searchText)
                });
            });
        }
        return list;
    }

    /**
     * Sorts a data array according to the current column sort.
     *
     * @param {DTData[]} data The data array to sort
     * @param {DTColumnSort[]} columnSort Column sort
     * @returns {DTData[]} The sorted data
     */
    sortData(data, columnSort) {
        let list = data;
        const firstColumnSort = columnSort[0];
        if (firstColumnSort) {
            const fieldExtractor = (row) => normalizeString(getValue(row, firstColumnSort.dataField));
            if (firstColumnSort.order === "asc") {
                list = sortBy(list, fieldExtractor);
            } else if (firstColumnSort.order === "desc") {
                list = sortBy(list, fieldExtractor).reverse();
            }
        }
        return list;
    }

    /**
     * Slices the data array according to the current page index and sizePerPage.
     *
     * @param {DTData[]} data The data to paginate
     * @param {number} currentPageIndex Current page index
     * @param {number} sizePerPage Number of elements per page
     * @returns {DTData[]} The paginated data
     */
    paginateData(data, currentPageIndex, sizePerPage) {
        let list = data;
        if (this.props.pagination) {
            const startIndex = currentPageIndex * sizePerPage;
            const endIndex = startIndex + sizePerPage;
            list = list.slice(startIndex, endIndex);
        }
        return list;
    }

    render() {
        const visibleColumns = this.props.columns?.filter(column => !column.hidden) || [];
        const numberOfColumns = visibleColumns.length + (this.props.selectRow ? 1 : 0);
        const numberOfPages = Math.ceil(this.state.sortedData.length / this.state.sizePerPage);

        return (
            <>
                {
                    this.props.search && <FormControl
                        className="table-filter-field"
                        placeholder="Search"
                        onInput={(event) => this.onSearch(event.target.value)}
                    ></FormControl>
                }
                <Table bordered striped hover>
                    <thead>
                        <tr>
                            {this.props.selectRow && (
                                <th style={{ width: 0 }} onClick={() => this.onRowSelectAll()}>
                                    <FormCheck
                                        className="m-0"
                                        inline
                                        checked={this.state.allSelected}
                                        onChange={() => this.onRowSelectAll()}
                                        ref={this.selectAllCheckboxRef}
                                    ></FormCheck>
                                </th>
                            )}
                            {visibleColumns.map((column, columnIndex) => {
                                const sort = this.state.columnSort.find(sort => sort.dataField === column.dataField);
                                return (
                                    <th
                                        key={columnIndex}
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
                            !this.props.data?.length ? (
                                <tr>
                                    <td className="text-center" colSpan={numberOfColumns}>{
                                        this.props.noDataIndication || "No data"
                                    }</td>
                                </tr>
                            ) : !this.state.paginatedData.length ? (
                                <tr>
                                    <td className="text-center" colSpan={numberOfColumns}>No search results</td>
                                </tr>
                            ) : (
                                this.state.paginatedData.map((row) => {
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
                                            {visibleColumns.map((column, columnIndex) => {
                                                const cell = getValue(row, column.dataField);
                                                return (
                                                    <td key={columnIndex} style={{
                                                        width: column.width,
                                                        textAlign: column.align,
                                                    }}>
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
                {
                    this.props.pagination && (
                        <div className="d-flex">
                            {
                                this.props.pagination.hideSizePerPage !== true && (
                                    <FormControl
                                        as="select"
                                        className="w-auto mr-auto"
                                        defaultValue={this.state.sizePerPage}
                                        onChange={(event) => this.onSizePerPageChange(+event.target.value)}
                                    >
                                        {
                                            this.state.sizePerPageList.map((size) => (
                                                <option value={size} key={size}>{size}</option>
                                            ))
                                        }
                                    </FormControl>
                                )
                            }
                            <Pagination className="ml-auto">
                                {
                                    Array.from({ length: numberOfPages }).map((_, pageIndex) => (
                                        <Pagination.Item
                                            key={pageIndex}
                                            onClick={() => this.onPageChange(pageIndex)}
                                            active={this.state.currentPageIndex === pageIndex}
                                        >{pageIndex + 1}</Pagination.Item>
                                    ))
                                }
                            </Pagination>
                        </div>
                    )
                }
            </>
        );
    }
}

export default DataTable;
