/*
 * Copyright 2019 Karl Dahlgren
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


function findHeaderQuery(headerQueryId){
    var queryTable = document.getElementById("headerQueryTable");
    for (var index = 1, row; row = queryTable.rows[index]; index++) {
        var cell = row.cells[0];
        var otherHeaderQueryId = cell.innerHTML;
        if(headerQueryId === otherHeaderQueryId){
            return index;
        }

    }
    return -1;
}

function alignHeaderQueryTableRowValues(){
    var queryTable = document.getElementById("headerQueryTable");
    for (var index = 1, row; row = queryTable.rows[index]; index++) {
        var headerCell = row.cells[2];
        var queryCell = row.cells[3];
        var matchAnyCell = row.cells[4];
        var matchCaseCell = row.cells[5];
        var matchRegexCell = row.cells[6];
        var headerInputValue = headerCell.getElementsByTagName("input")[0];
        var queryInputValue = queryCell.getElementsByTagName("input")[0];
        var matchAnyInputValue = matchAnyCell.getElementsByTagName("input")[0];
        var matchCaseInputValue = matchCaseCell.getElementsByTagName("input")[0];
        var matchRegexInputValue = matchRegexCell.getElementsByTagName("input")[0];

        var rowIndex = index - 1;
        headerInputValue.id = "headerQueries[" + rowIndex + "].header";
        headerInputValue.name = "headerQueries[" + rowIndex + "].header";

        queryInputValue.id = "headerQueries[" + rowIndex + "].query";
        queryInputValue.name = "headerQueries[" + rowIndex + "].query";

        matchAnyInputValue.id = "headerQueries[" + rowIndex + "].matchAny";
        matchAnyInputValue.name = "headerQueries[" + rowIndex + "].matchAny";

        matchCaseInputValue.id = "headerQueries[" + rowIndex + "].matchCase";
        matchCaseInputValue.name = "headerQueries[" + rowIndex + "].matchCase";

        matchRegexInputValue.id = "headerQueries[" + rowIndex + "].matchRegex";
        matchRegexInputValue.name = "headerQueries[" + rowIndex + "].matchRegex";
    }
}

function addHeaderQuery() {
    var queryTable = document.getElementById("headerQueryTable");
    var headerSelect = document.getElementById("headerInput").value;
    var queryInput = document.getElementById("headerQueryInput").value;
    var matchAnyInput = document.getElementById("headerMatchAnyInput").checked;
    var matchCaseInput = document.getElementById("headerMatchCaseInput").checked;
    var matchRegexInput = document.getElementById("headerRegexInput").checked;

    var headerQueryId = Math.random().toString(36).substring(7);
    var insertIndex = queryTable.rows.length - 1;
    var row = queryTable.insertRow(-1);
    var queryIdColumn = row.insertCell(0);
    var queryDeleteColumn = row.insertCell(1);
    var headerColumn = row.insertCell(2);
    var queryColumn = row.insertCell(3);
    var matchAnyColumn = row.insertCell(4);
    var matchCaseColumn = row.insertCell(5);
    var matchRegexColumn = row.insertCell(6);

    queryIdColumn.hidden = true;
    queryIdColumn.innerHTML = headerQueryId;
    queryDeleteColumn.innerHTML = "<div class=\"delete\" onclick=\"removeHeaderQuery(\'" + headerQueryId + "')\" \>";
    headerColumn.innerHTML = "<input name=\"headerQueries[" + insertIndex + "].header\" value=\"" + headerSelect + "\" type=\"hidden\" \> " + headerSelect;
    queryColumn.innerHTML = "<input name=\"headerQueries[" + insertIndex + "].query\" value=\"" + queryInput + "\" type=\"hidden\" \> " + queryInput;
    matchAnyColumn.innerHTML = "<input name=\"headerQueries[" + insertIndex + "].matchAny\" value=\"" + matchAnyInput + "\" type=\"hidden\" \> " + matchAnyInput;
    matchCaseColumn.innerHTML = "<input name=\"headerQueries[" + insertIndex + "].matchCase\" value=\"" + matchCaseInput + "\" type=\"hidden\" \> " + matchCaseInput;
    matchRegexColumn.innerHTML = "<input name=\"headerQueries[" + insertIndex + "].matchRegex\" value=\"" + matchRegexInput + "\" type=\"hidden\" \> " + matchRegexInput;
    alignHeaderQueryTableRowValues();
}

function removeHeaderQuery(headerQueryId) {
    var queryTable = document.getElementById("headerQueryTable");
    var index = findHeaderQuery(headerQueryId);
    queryTable.deleteRow(index);
    alignHeaderQueryTableRowValues();
}