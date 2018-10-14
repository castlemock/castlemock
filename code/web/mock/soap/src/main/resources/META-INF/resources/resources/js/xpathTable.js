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


function findXpath(xpathName){
    var xpathTable = document.getElementById("xpathTable");
    for (var index = 1, row; row = xpathTable.rows[index]; index++) {
        var cell = row.cells[1];
        var xpathNameInputValue = cell.getElementsByTagName("input")[0].value;
        if(xpathNameInputValue === xpathName){
            return index;
        }

    }
    return -1;
}

function alignXpathTableRowValues(){
    var xpathTable = document.getElementById("xpathTable");
    for (var index = 1, row; row = xpathTable.rows[index]; index++) {
        var nameCell = row.cells[1];
        var xpathNameInputValue = nameCell.getElementsByTagName("input")[0];

        var rowIndex = index - 1;
        xpathNameInputValue.id = "xpathExpressions[" + rowIndex + "].expression";
        xpathNameInputValue.name = "xpathExpressions[" + rowIndex + "].expression";
    }
}

function addXpath() {
    var xpathTable = document.getElementById("xpathTable");
    var xpath = document.getElementById("xpathInput").value;

    var index = findXpath(xpath);
    if(index !== -1){
        return;
    }

    var insertIndex = xpathTable.rows.length - 1;
    var row = xpathTable.insertRow(-1);
    var xpathSelected = row.insertCell(0);
    var xpathColumn = row.insertCell(1);

    xpathSelected.innerHTML = "<div class=\"delete\" onclick=\"removeXpath(\'" + xpath + "')\" \>";
    xpathColumn.innerHTML = "<input name=\"xpathExpressions[" + insertIndex + "].expression\" value=\"" + xpath + "\" type=\"hidden\" \> " + xpath;
    alignXpathTableRowValues();
}

function removeXpath(deleteXPath) {
    var xpathTable = document.getElementById("xpathTable");
    var index = findXpath(deleteXPath);
    xpathTable.deleteRow(index);
    alignXpathTableRowValues();
}