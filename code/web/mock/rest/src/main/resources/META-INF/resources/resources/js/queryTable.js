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


function findParameterQuery(parameter, query, matchAny, matchCase, matchRegex){
    var queryTable = document.getElementById("queryTable");
    for (var index = 1, row; row = queryTable.rows[index]; index++) {
        var parameterCell = row.cells[1];
        var queryCell = row.cells[2];
        var matchAnyCell = row.cells[3];
        var matchCaseCell = row.cells[4];
        var matchRegexCell = row.cells[5];
        var parameterInputValue = parameterCell.getElementsByTagName("input")[0].value;
        var queryInputValue = queryCell.getElementsByTagName("input")[0].value;
        var matchAnyInputValue = matchAnyCell.getElementsByTagName("input")[0].value;
        var matchCaseInputValue = matchCaseCell.getElementsByTagName("input")[0].value;
        var matchRegexInputValue = matchRegexCell.getElementsByTagName("input")[0].value;
        if(parameter === parameterInputValue &&
            query === queryInputValue &&
            matchAny === matchAnyInputValue &&
            matchCase === matchCaseInputValue &&
            matchRegex === matchRegexInputValue){
            return index;
        }

    }
    return -1;
}

function alignTableRowValues(){
    var queryTable = document.getElementById("queryTable");
    for (var index = 1, row; row = queryTable.rows[index]; index++) {
        var nameCell = row.cells[1];
        var valueCell = row.cells[2];
        var parameterInputValue = nameCell.getElementsByTagName("input")[0];
        var queryInputValue = valueCell.getElementsByTagName("input")[0];

        var rowIndex = index - 1;
        parameterInputValue.id = "parameterQueries[" + rowIndex + "].parameter";
        parameterInputValue.name = "parameterQueries[" + rowIndex + "].parameter";

        queryInputValue.id = "parameterQueries[" + rowIndex + "].query";
        queryInputValue.name = "parameterQueries[" + rowIndex + "].query";
    }
}

function addParameterQuery() {
    var queryTable = document.getElementById("queryTable");
    var parameterSelect = document.getElementById("parameterSelect").value;
    var queryInput = document.getElementById("queryInput").value;
    var matchAnyInput = document.getElementById("matchAnyInput").checked;
    var matchCaseInput = document.getElementById("matchCaseInput").checked;
    var matchRegexInput = document.getElementById("regexInput").checked;

    var insertIndex = queryTable.rows.length - 1;
    var row = queryTable.insertRow(-1);
    var querySelected = row.insertCell(0);
    var parameterColumn = row.insertCell(1);
    var queryColumn = row.insertCell(2);
    var matchAnyColumn = row.insertCell(3);
    var matchCaseColumn = row.insertCell(4);
    var matchRegexColumn = row.insertCell(5);

    querySelected.innerHTML = "<div class=\"delete\" onclick=\"removeParameterQuery(\'" + parameterSelect + "','" + queryInput + "','" + matchAnyInput + "','" + matchCaseInput + "','" + matchRegexInput + "')\" \>";
    parameterColumn.innerHTML = "<input name=\"parameterQueries[" + insertIndex + "].parameter\" value=\"" + parameterSelect + "\" type=\"hidden\" \> " + parameterSelect;
    queryColumn.innerHTML = "<input name=\"parameterQueries[" + insertIndex + "].query\" value=\"" + queryInput + "\" type=\"hidden\" \> " + queryInput;
    matchAnyColumn.innerHTML = "<input name=\"parameterQueries[" + insertIndex + "].matchAny\" value=\"" + matchAnyInput + "\" type=\"hidden\" \> " + matchAnyInput;
    matchCaseColumn.innerHTML = "<input name=\"parameterQueries[" + insertIndex + "].matchCase\" value=\"" + matchCaseInput + "\" type=\"hidden\" \> " + matchCaseInput;
    matchRegexColumn.innerHTML = "<input name=\"parameterQueries[" + insertIndex + "].matchRegex\" value=\"" + matchRegexInput + "\" type=\"hidden\" \> " + matchRegexInput;
    alignTableRowValues();
}

function removeParameterQuery(parameter, query, matchAny, matchCase, matchRegex) {
    var queryTable = document.getElementById("queryTable");
    var index = findParameterQuery(parameter, query, matchAny, matchCase, matchRegex);
    queryTable.deleteRow(index);
    alignTableRowValues();
}