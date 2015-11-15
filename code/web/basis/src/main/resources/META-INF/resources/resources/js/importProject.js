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
function Output(msg) {
    var messages = $id("messages");
    messages.innerHTML = msg + messages.innerHTML;
}

// file drag hover
function FileDragHover(e) {
    e.stopPropagation();
    e.preventDefault();
    var filedrag = $id("filedrag");
    filedrag.className = (e.type == "dragover" ? "hover" : "");
}


// file selection
function FileSelectHandler(e) {

    // cancel event and hover styling
    FileDragHover(e);

    var messages = $id("messages");
    messages.innerHTML = "";

    // fetch FileList object
    var files = e.target.files || e.dataTransfer.files;

    // process all File objects
    for (var i = 0, file; file = files[i]; i++) {
        ParseFile(file);
    }
}

function ParseFile(file) {
    Output("<p><strong>" + file.name + "</strong> (" + file.size + "bytes)</p>");
}

// initialize
function Init() {

    var fileselect = $id("files"), filedrag = $id("filedrag")

    // file select
    fileselect.addEventListener("change", FileSelectHandler, false);

    // is XHR2 available?
    var xhr = new XMLHttpRequest();
    if (xhr.upload) {

        // file drop
        filedrag.addEventListener("dragover", FileDragHover, false);
        filedrag.addEventListener("dragleave", FileDragHover, false);
        filedrag.addEventListener("drop", FileSelectHandler, false);
        filedrag.style.display = "block";
    }
}

// call initialization file
if (window.File && window.FileList && window.FileReader) {
    Init();
}