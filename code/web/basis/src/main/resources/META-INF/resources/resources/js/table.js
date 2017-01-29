/*
 * Copyright 2017 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

const ASCENDING = "asc";
const DESCENDING = "desc";
const TABLE_ROW = "TR";
const TABLE_CELL= "TD";

/**
 * The method register the sorting actions for all tables with class entityTable.
 * @since 1.9
 */
window.onload = function() {
    var tables = document.getElementsByClassName('sortable');
    for(var tableIndex = 0; tableIndex < tables.length; tableIndex++){
        var table = tables[tableIndex];
        var row = table.rows[0]; // Get the first row
        var columns = row.cells;
        for(var columnIndex = 0; columnIndex < columns.length; columnIndex++){
            var column = columns[columnIndex];
            sortTable(table, column);
        }
    }
};


/**
 * The sortTable method is used to register the action <code>onclick<code>.
 * The <code>onclick<code> action will sort a table either ascending or descending.
 * The sorting will be based on the provided column.
 * @param table The table that will be sorted.
 * @param column The column which will be used as sorting value.
 * @since 1.9
 */
function sortTable(table, column){
    column.onclick = function () {
        var x,y;
        var switchCount = 0;
        var dir = ASCENDING;
        var switching = true;
        var shouldSwitch = false;
        var columnIndex = column.cellIndex;
        while (switching) {
            switching = false;
            var rows = table.getElementsByTagName(TABLE_ROW);
            for (var index = 1; index < (rows.length - 1); index++) {
                shouldSwitch = false;
                x = rows[index].getElementsByTagName(TABLE_CELL)[columnIndex];
                y = rows[index + 1].getElementsByTagName(TABLE_CELL)[columnIndex];

                if (dir == ASCENDING) {
                    if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                        shouldSwitch= true;
                        break;
                    }
                } else if (dir == DESCENDING) {
                    if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                        shouldSwitch= true;
                        break;
                    }
                }
            }
            if (shouldSwitch) {
                rows[index].parentNode.insertBefore(rows[index + 1], rows[index]);
                switching = true;
                switchCount++;
            } else {
                if (switchCount == 0 && dir == ASCENDING) {
                    dir = DESCENDING;
                    switching = true;
                }
            }
        }
    };
};