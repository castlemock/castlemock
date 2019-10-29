/*
 * Copyright 2016 Karl Dahlgren
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


function findHeader(headerName){
    var headerTable = document.getElementById("headerTable");
    for (var index = 1, row; row = headerTable.rows[index]; index++) {
        var cell = row.cells[1];
        var headerNameInputValue = cell.getElementsByTagName("input")[0].value;
        if(headerNameInputValue === headerName){
            return index;
        }

    }
    return -1;
}

function alignHeaderTableRowValues(){
    var headerTable = document.getElementById("headerTable");
    for (var index = 1, row; row = headerTable.rows[index]; index++) {
        var nameCell = row.cells[1];
        var valueCell = row.cells[2];
        var headerNameInputValue = nameCell.getElementsByTagName("input")[0];
        var headerValueInputValue = valueCell.getElementsByTagName("input")[0];

        var rowIndex = index - 1;
        headerNameInputValue.id = "httpHeaders[" + rowIndex + "].name";
        headerNameInputValue.name = "httpHeaders[" + rowIndex + "].name";

        headerValueInputValue.id = "httpHeaders[" + rowIndex + "].value";
        headerValueInputValue.name = "httpHeaders[" + rowIndex + "].value";
    }
}

function addHeader() {
    var headerTable = document.getElementById("headerTable");
    var headerName = document.getElementById("headerNameInput").value;
    var headerValue = document.getElementById("headerValueInput").value;

    var index = findHeader(headerName);
    if(index !== -1){
        return;
    }

    var insertIndex = headerTable.rows.length - 1;
    var row = headerTable.insertRow(-1);
    var headerSelected = row.insertCell(0);
    var headerNameColumn = row.insertCell(1);
    var headerValueColumn = row.insertCell(2);

    headerSelected.innerHTML = "<div class=\"delete\" onclick=\"removeHeader(\'" + headerName + "')\" \>";
    headerNameColumn.innerHTML = "<input name=\"httpHeaders[" + insertIndex + "].name\" value=\"" + headerName + "\" type=\"hidden\" \> " + headerName;
    headerValueColumn.innerHTML = "<input name=\"httpHeaders[" + insertIndex + "].value\" value=\"" + headerValue + "\" type=\"hidden\" \> " + headerValue;
    alignHeaderTableRowValues();
}

function removeHeader(deleteHeaderName) {
    var headerTable = document.getElementById("headerTable");
    var index = findHeader(deleteHeaderName);
    headerTable.deleteRow(index);
    alignHeaderTableRowValues();
}