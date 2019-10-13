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


function findParameterQuery(parameterQueryId){
    var queryTable = document.getElementById("queryTable");
    for (var index = 1, row; row = queryTable.rows[index]; index++) {
        var cell = row.cells[0];
        var otherParameterQueryId = cell.innerHTML;
        if(parameterQueryId === otherParameterQueryId){
            return index;
        }

    }
    return -1;
}

function alignParameterQueryTableRowValues(){
    var queryTable = document.getElementById("queryTable");
    for (var index = 1, row; row = queryTable.rows[index]; index++) {
        var parameterCell = row.cells[2];
        var queryCell = row.cells[3];
        var matchAnyCell = row.cells[4];
        var matchCaseCell = row.cells[5];
        var matchRegexCell = row.cells[6];
        var parameterInputValue = parameterCell.getElementsByTagName("input")[0];
        var queryInputValue = queryCell.getElementsByTagName("input")[0];
        var matchAnyInputValue = matchAnyCell.getElementsByTagName("input")[0];
        var matchCaseInputValue = matchCaseCell.getElementsByTagName("input")[0];
        var matchRegexInputValue = matchRegexCell.getElementsByTagName("input")[0];

        var rowIndex = index - 1;
        parameterInputValue.id = "parameterQueries[" + rowIndex + "].parameter";
        parameterInputValue.name = "parameterQueries[" + rowIndex + "].parameter";

        queryInputValue.id = "parameterQueries[" + rowIndex + "].query";
        queryInputValue.name = "parameterQueries[" + rowIndex + "].query";

        matchAnyInputValue.id = "parameterQueries[" + rowIndex + "].matchAny";
        matchAnyInputValue.name = "parameterQueries[" + rowIndex + "].matchAny";

        matchCaseInputValue.id = "parameterQueries[" + rowIndex + "].matchCase";
        matchCaseInputValue.name = "parameterQueries[" + rowIndex + "].matchCase";

        matchRegexInputValue.id = "parameterQueries[" + rowIndex + "].matchRegex";
        matchRegexInputValue.name = "parameterQueries[" + rowIndex + "].matchRegex";
    }
}

function addParameterQuery() {
    var queryTable = document.getElementById("queryTable");
    var parameterSelect = document.getElementById("queryParameterSelect").value;
    var queryInput = document.getElementById("queryInput").value;
    var matchAnyInput = document.getElementById("matchAnyInput").checked;
    var matchCaseInput = document.getElementById("matchCaseInput").checked;
    var matchRegexInput = document.getElementById("regexInput").checked;

    var parameterQueryId = Math.random().toString(36).substring(7);
    var insertIndex = queryTable.rows.length - 1;
    var row = queryTable.insertRow(-1);
    var queryIdColumn = row.insertCell(0);
    var queryDeleteColumn = row.insertCell(1);
    var parameterColumn = row.insertCell(2);
    var queryColumn = row.insertCell(3);
    var matchAnyColumn = row.insertCell(4);
    var matchCaseColumn = row.insertCell(5);
    var matchRegexColumn = row.insertCell(6);

    queryIdColumn.hidden = true;
    queryIdColumn.innerHTML = parameterQueryId;
    queryDeleteColumn.innerHTML = "<div class=\"delete\" onclick=\"removeParameterQuery(\'" + parameterQueryId + "')\" \>";
    parameterColumn.innerHTML = "<input name=\"parameterQueries[" + insertIndex + "].parameter\" value=\"" + parameterSelect + "\" type=\"hidden\" \> " + parameterSelect;
    queryColumn.innerHTML = "<input name=\"parameterQueries[" + insertIndex + "].query\" value=\"" + queryInput + "\" type=\"hidden\" \> " + queryInput;
    matchAnyColumn.innerHTML = "<input name=\"parameterQueries[" + insertIndex + "].matchAny\" value=\"" + matchAnyInput + "\" type=\"hidden\" \> " + matchAnyInput;
    matchCaseColumn.innerHTML = "<input name=\"parameterQueries[" + insertIndex + "].matchCase\" value=\"" + matchCaseInput + "\" type=\"hidden\" \> " + matchCaseInput;
    matchRegexColumn.innerHTML = "<input name=\"parameterQueries[" + insertIndex + "].matchRegex\" value=\"" + matchRegexInput + "\" type=\"hidden\" \> " + matchRegexInput;
    alignParameterQueryTableRowValues();
}

function removeParameterQuery(parameterQueryId) {
    var queryTable = document.getElementById("queryTable");
    var index = findParameterQuery(parameterQueryId);
    queryTable.deleteRow(index);
    alignParameterQueryTableRowValues();
}