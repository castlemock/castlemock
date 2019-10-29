/*
 * Copyright 2015 Karl Dahlgren
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


// getElementById
function $id(id) {
    return document.getElementById(id);
}

// output information
function output(msg) {
    var messages = $id("messages");
    messages.innerHTML = msg + messages.innerHTML;
}

function parseFile(file) {
    output("<p><strong>" + file.name + "</strong> (" + file.size + "bytes)</p>");
}

// file selection
function fileSelectHandler(e) {

    var messages = $id("messages");
    messages.innerHTML = "";

    // fetch FileList object
    var files = e.target.files || e.dataTransfer.files;

    // process all File objects
    for (var i = 0, file; file = files[i]; i++) {
        parseFile(file);
    }
}


// initialize
function init() {

    var fileselect = $id("files");

    // file select
    fileselect.addEventListener("change", fileSelectHandler, false);
}

// call initialization file
if (window.File && window.FileList && window.FileReader) {
    init();
}