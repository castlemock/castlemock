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

var httpCodes = new Map();
httpCodes.set("100", "Continue");
httpCodes.set("101", "Switching Protocols");
httpCodes.set("102", "Processing");
httpCodes.set("103", "Checkpoint");

httpCodes.set("200", "OK");
httpCodes.set("201", "Created");
httpCodes.set("202", "Accepted");
httpCodes.set("203", "Non-Authoritative Information");
httpCodes.set("204", "No Content");
httpCodes.set("205", "Reset Content");
httpCodes.set("206", "Partial Content");
httpCodes.set("207", "Multi-Status");
httpCodes.set("208", "Already Reported");
httpCodes.set("226", "IM Used");

httpCodes.set("300", "Multiple Choices");
httpCodes.set("301", "Moved Permanently");
httpCodes.set("302", "Found");
httpCodes.set("303", "See Other");
httpCodes.set("304", "Not Modified");
httpCodes.set("305", "Use Proxy");
httpCodes.set("306", "Switch Proxy");
httpCodes.set("307", "Temporary Redirect");
httpCodes.set("308", "Permanent Redirect");

httpCodes.set("400", "Bad Request");
httpCodes.set("401", "Unauthorized");
httpCodes.set("402", "Payment Required");
httpCodes.set("403", "Forbidden");
httpCodes.set("404", "Not Found");
httpCodes.set("405", "Method Not Allowed");
httpCodes.set("406", "Not Acceptable");
httpCodes.set("407", "Proxy Authentication Required");
httpCodes.set("408", "Request Timeout");
httpCodes.set("409", "Conflict");
httpCodes.set("410", "Gone");
httpCodes.set("411", "Length Required");
httpCodes.set("412", "Precondition Failed");
httpCodes.set("413", "Payload Too Large");
httpCodes.set("414", "URI Too Long");
httpCodes.set("415", "Unsupported Media Type");
httpCodes.set("416", "Range Not Satisfiable");
httpCodes.set("417", "Expectation Failed");
httpCodes.set("418", "I'm a teapot");
httpCodes.set("419", "Authentication Timeout");
httpCodes.set("421", "Misdirected Request");
httpCodes.set("422", "Unprocessable Entity");
httpCodes.set("423", "Locked");
httpCodes.set("424", "Failed Dependency");
httpCodes.set("426", "Upgrade Required");
httpCodes.set("428", "Precondition Required");
httpCodes.set("429", "Too Many Requests");
httpCodes.set("431", "Request Header Fields Too Large");
httpCodes.set("451", "Unavailable For Legal Reasons");

httpCodes.set("500", "Internal Server Error");
httpCodes.set("501", "Not Implemented");
httpCodes.set("502", "Bad Gateway");
httpCodes.set("503", "Service Unavailable");
httpCodes.set("504", "Gateway Timeout");
httpCodes.set("505", "HTTP Version Not Supported");
httpCodes.set("506", "Variant Also Negotiates");
httpCodes.set("507", "Insufficient Storage");
httpCodes.set("508", "Loop Detected");
httpCodes.set("510", "Not Extended");
httpCodes.set("511", "Network Authentication Required");

function enableTab(id) {
    var el = document.getElementById(id);
    el.onkeydown = function(e) {
        if (e.keyCode === 9) {

            var val = this.value,
                start = this.selectionStart,
                end = this.selectionEnd;


            this.value = val.substring(0, start) + '\t' + val.substring(end);
            this.selectionStart = this.selectionEnd = start + 1;
            return false;
        }
    };
}

function registerXmlFormat(buttonId, textAreaId){
    var buttonElement = document.getElementById(buttonId);
    buttonElement.onclick = function(e) {
        var textAreaElement = document.getElementById(textAreaId);
        var xml = textAreaElement.value;
        var formatted = '';
        var reg = /(>)\s*(<)(\/*)/g;;
        xml = xml.replace(reg, '$1\r\n$2$3');
        var pad = 0;
        jQuery.each(xml.split('\r\n'), function(index, node) {
            var indent = 0;
            if (node.match( /.+<\/\w[^>]*>$/ )) {
                indent = 0;
            } else if (node.match( /^<\/\w/ )) {
                if (pad != 0) {
                    pad -= 1;
                }
            } else if (node.match( /^<\w[^>]*[^\/]>.*$/ )) {
                indent = 1;
            } else {
                indent = 0;
            }

            var padding = '';
            for (var i = 0; i < pad; i++) {
                padding += '\t';
            }

            formatted += padding + node + '\r\n';
            pad += indent;
        });

        textAreaElement.value = formatted.trim();
    };
}

function registerJsonFormat(buttonId, textAreaId){
    var buttonElement = document.getElementById(buttonId);
    buttonElement.onclick = function(e) {
        var textAreaElement = document.getElementById(textAreaId);
        var json = textAreaElement.value;
        var formatted = JSON.stringify(JSON.parse(json), null, "\t");
        textAreaElement.value = formatted;
    };
}

function initiateHttpResponseCode(textField, label, labelDefinition){
    setHttpResponseCodeDefinition(textField, label, labelDefinition);
    enableHttpResponseCodeLookup(textField, label, labelDefinition);

}

function enableHttpResponseCodeLookup(textField, label, labelDefinition){
    var textFieldElement = document.getElementById(textField);
    textFieldElement.onblur = function(e) {
        setHttpResponseCodeDefinition(textField, label, labelDefinition);
    };

}

function setHttpResponseCodeDefinition(textField, label, labelDefinition){
    var textFieldElement = document.getElementById(textField);
    var labelElement = document.getElementById(label);
    var labelDefinitionElement = document.getElementById(labelDefinition);
    var textFieldValue = textFieldElement.value;
    var httpCode = httpCodes.get(textFieldValue);
    if(httpCode != null){
        labelElement.innerHTML = httpCode;
        labelElement.style.display = "inherit";
        labelDefinitionElement.style.display = "inherit";
    } else {
        labelElement.style.display = "none";
        labelDefinitionElement.style.display = "none";
    }
}