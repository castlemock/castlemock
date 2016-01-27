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
    headerTable = document.getElementById("headerTable");
    for (var index = 1, row; row = headerTable.rows[index]; index++) {
        cell = row.cells[1];
        headerNameInputValue = cell.getElementsByTagName("input")[0].value;
        console.log(headerName + " == " + headerNameInputValue)
        if(headerNameInputValue == headerName){
            return index;
        }

    }
    return -1;
}

function alignTableRowValues(){
    headerTable = document.getElementById("headerTable");
    for (var index = 1, row; row = headerTable.rows[index]; index++) {
        nameCell = row.cells[1];
        valueCell = row.cells[2];
        headerNameInputValue = nameCell.getElementsByTagName("input")[0];
        headerValueInputValue = valueCell.getElementsByTagName("input")[0];

        rowIndex = index - 1;
        headerNameInputValue.id = "httpHeaders[" + rowIndex + "].name";
        headerNameInputValue.name = "httpHeaders[" + rowIndex + "].name";

        headerValueInputValue.id = "httpHeaders[" + rowIndex + "].value";
        headerValueInputValue.name = "httpHeaders[" + rowIndex + "].value";
    }
}

function addHeader() {
    headerTable = document.getElementById("headerTable");
    headerName = document.getElementById("headerNameInput").value;
    headerValue = document.getElementById("headerValueInput").value;

    index = findHeader(headerName);
    if(index != -1){
        return;
    }

    insertIndex = headerTable.rows.length - 1;
    row = headerTable.insertRow(-1);
    headerSelected = row.insertCell(0);
    headerNameColumn = row.insertCell(1);
    headerValueColumn = row.insertCell(2);

    headerSelected.innerHTML = "<div class=\"delete\" onclick=\"removeHeader(\'' + headerName + '\')\">";
    headerNameColumn.innerHTML = "<input name=\"httpHeaders[" + insertIndex + "].name\" value=" + headerName + " type=\"hidden\" \> " + headerName;
    headerValueColumn.innerHTML = "<input name=\"httpHeaders[" + insertIndex + "].value\" value=" + headerValue + " type=\"hidden\" \> " + headerValue;
    alignTableRowValues();
}

function removeHeader(deleteHeaderName) {
    index = findHeader(deleteHeaderName);
    headerTable.deleteRow(index);
    alignTableRowValues();
}