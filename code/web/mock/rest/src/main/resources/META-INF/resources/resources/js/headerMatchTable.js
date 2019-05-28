/*
 * Copyright 2018 Karl Dahlgren
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


function findParameterHeader(parameterHeaderId){
    var headerMatchTable = document.getElementById("headerMatchTable");
    for (var index = 1, row; row = headerMatchTable.rows[index]; index++) {
        var cell = row.cells[0];
        var otherParameterHeaderId = cell.innerHTML;
        if(otherParameterHeaderId === parameterHeaderId){
            return index;
        }

    }
    return -1;
}

function alignHeaderMatchTableRowValues(){
    var headerMatchTable = document.getElementById("headerMatchTable");
    for (var index = 1, row; row = headerMatchTable.rows[index]; index++) {
        var nameCell = row.cells[2];
        var parameterHeaderInputValue = nameCell.getElementsByTagName("input")[0];

        var rowIndex = index - 1;
        parameterHeaderInputValue.id = "parameterHeaderExpressions[" + rowIndex + "].expression";
        parameterHeaderInputValue.name = "parameterHeaderExpressions[" + rowIndex + "].expression";
    }
}

function addParameterHeader() {
    var headerMatchTable = document.getElementById("headerMatchTable");
    var parameterHeader = document.getElementById("parameterHeaderInput").value;

    var parameterHeaderId = Math.random().toString(36).substring(7);
    var insertIndex = headerMatchTable.rows.length - 1;
    var row = headerMatchTable.insertRow(-1);
    var parameterHeaderIdColumn = row.insertCell(0);
    var parameterHeaderDeleteColumn = row.insertCell(1);
    var parameterHeaderExpressionColumn = row.insertCell(2);

    parameterHeaderIdColumn.hidden = true;
    parameterHeaderIdColumn.innerHTML = parameterHeaderId;
    parameterHeaderDeleteColumn.innerHTML = "<div class=\"delete\" onclick=\"removeParameterHeader(\'" + parameterHeaderId + "')\" \>";
    parameterHeaderExpressionColumn.innerHTML = "<input name=\"parameterHeaderExpressions[" + insertIndex + "].expression\" value=\"" + parameterHeader + "\" type=\"hidden\" \> " + parameterHeader;
    alignHeaderMatchTableRowValues();
}

function removeParameterHeader(parameterHeaderId) {
    var headerMatchTable = document.getElementById("headerMatchTable");
    var index = findParameterHeader(parameterHeaderId);
    headerMatchTable.deleteRow(index);
    alignHeaderMatchTableRowValues();
}