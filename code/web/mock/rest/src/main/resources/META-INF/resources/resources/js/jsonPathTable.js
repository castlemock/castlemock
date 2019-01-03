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


function findJsonPath(jsonPathId){
    var jsonPathTable = document.getElementById("jsonPathTable");
    for (var index = 1, row; row = jsonPathTable.rows[index]; index++) {
        var cell = row.cells[0];
        var otherJsonPathId = cell.innerHTML;
        if(otherJsonPathId === jsonPathId){
            return index;
        }

    }
    return -1;
}

function alignJsonPathTableRowValues(){
    var jsonPathTable = document.getElementById("jsonPathTable");
    for (var index = 1, row; row = jsonPathTable.rows[index]; index++) {
        var nameCell = row.cells[2];
        var jsonPathNameInputValue = nameCell.getElementsByTagName("input")[0];

        var rowIndex = index - 1;
        jsonPathNameInputValue.id = "jsonPathExpressions[" + rowIndex + "].expression";
        jsonPathNameInputValue.name = "jsonPathExpressions[" + rowIndex + "].expression";
    }
}

function addJsonPath() {
    var jsonPathTable = document.getElementById("jsonPathTable");
    var jsonPath = document.getElementById("jsonPathInput").value;

    var jsonPathId = Math.random().toString(36).substring(7);
    var insertIndex = jsonPathTable.rows.length - 1;
    var row = jsonPathTable.insertRow(-1);
    var jsonPathIdColumn = row.insertCell(0);
    var jsonPathDeleteColumn = row.insertCell(1);
    var jsonPathExpressionColumn = row.insertCell(2);

    jsonPathIdColumn.hidden = true;
    jsonPathIdColumn.innerHTML = jsonPathId;
    jsonPathDeleteColumn.innerHTML = "<div class=\"delete\" onclick=\"removeJsonPath(\'" + jsonPathId + "')\" \>";
    jsonPathExpressionColumn.innerHTML = "<input name=\"jsonPathExpressions[" + insertIndex + "].expression\" value=\"" + jsonPath + "\" type=\"hidden\" \> " + jsonPath;
    alignJsonPathTableRowValues();
}

function removeJsonPath(jsonPathId) {
    var jsonPathTable = document.getElementById("jsonPathTable");
    var index = findJsonPath(jsonPathId);
    jsonPathTable.deleteRow(index);
    alignJsonPathTableRowValues();
}