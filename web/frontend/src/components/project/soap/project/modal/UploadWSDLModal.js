/*
 Copyright 2020 Karl Dahlgren

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

import React, {PureComponent} from "react";
import axios from "axios";
import validateErrorResponse from "../../../../../utility/HttpResponseValidator";


class UploadWSDLModal extends PureComponent {

    constructor(props){
        super(props);
        this.setFile = this.setFile.bind(this);
        this.uploadWsdl = this.uploadWsdl.bind(this);
        this.linkWsdl = this.linkWsdl.bind(this);
        this.setLinkWsdlUrl = this.setLinkWsdlUrl.bind(this);
        this.setGenerateResponseLink = this.setGenerateResponseLink.bind(this);
        this.setGenerateResponseUpload = this.setGenerateResponseUpload.bind(this);
        this.setIncludeImports = this.setIncludeImports.bind(this);

        this.state = {
            selectedFile: null,
            selectedFileName: "",
            generateResponseLink: "true",
            generateResponseUpload: "true",
            includeImports: "false",
            linkWsdlUrl: ""
        }
    }

    setLinkWsdlUrl(event) {
        this.setState({
            linkWsdlUrl: event.target.value
        });
    }

    setGenerateResponseLink(event) {
        this.setState({
            generateResponseLink: event.target.checked
        });
    }

    setGenerateResponseUpload(event) {
        this.setState({
            generateResponseUpload: event.target.checked
        });
    }

    setIncludeImports(event) {
        this.setState({
            includeImports: event.target.checked
        });
    }

    setFile(event) {
        let selectedFile = event.target.files[0];
        this.setState({
            selectedFile: selectedFile,
            selectedFileName: selectedFile.name
        });
    }


    uploadWsdl(){
        let data = new FormData();
        data.append('file', this.state.selectedFile);
        data.append('generateResponse', this.state.generateResponseUpload);
        axios
            .post("/castlemock/api/rest/soap/project/" + this.props.projectId + "/wsdl/file", data, {})
            .then(response => {
                this.props.getProject();
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    linkWsdl(){
        axios
            .post("/castlemock/api/rest/soap/project/" + this.props.projectId + "/wsdl/link", {
                url: this.state.linkWsdlUrl,
                generateResponse: this.state.generateResponseLink,
                includeImports: this.state.includeImports
            }, {})
            .then(response => {
                this.props.getProject();
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    render() {
        return (
            <div className="modal fade" id="uploadWSDLModal" tabIndex="-1" role="dialog"
                 aria-labelledby="uploadWSDLModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="uploadWSDLModalLabel">Upload WSDL</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <div>
                                <div className="upload-modal-title">Link</div>
                                <div className="form-group row">
                                    <label htmlFor="updateProjectName" className="col-sm-2 col-form-label">URL</label>
                                    <div className="col-sm-10">
                                        <input className="form-control" type="text" onChange={this.setLinkWsdlUrl}/>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label className="col-sm-5 col-form-label">Import linked WSDLs</label>
                                    <div className="col-sm-5">
                                        <input type="checkbox" onChange={this.setIncludeImports}/>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label className="col-sm-5 col-form-label">Generate responses for each operation</label>
                                    <div className="col-sm-5">
                                        <input type="checkbox" onChange={this.setGenerateResponseLink}/>
                                    </div>
                                </div>
                                <div className="upload-modal-button">
                                    <button className="btn btn-success" data-dismiss="modal"
                                            disabled={this.state.linkWsdlUrl === ""}
                                            onClick={this.linkWsdl}>Link</button>
                                </div>
                            </div>

                            <div className="separator">Or</div>

                            <div>
                                <div className="upload-modal-title">Upload</div>
                                <div className="form-group row">
                                    <div className="input-group mb-3">
                                        <div className="custom-file">
                                            <input type="file" className="custom-file-input" onChange={this.setFile}/>
                                            <label className="custom-file-label" >{this.state.selectedFileName}</label>
                                        </div>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label className="col-sm-5 col-form-label">Generate responses for each operation</label>
                                    <div className="col-sm-5">
                                        <input type="checkbox" onChange={this.setGenerateResponseUpload}/>
                                    </div>
                                </div>
                                <div className="upload-modal-button">
                                    <button className="btn btn-success" data-dismiss="modal"
                                            disabled={this.state.selectedFile === null}
                                            onClick={this.uploadWsdl}>Upload</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default UploadWSDLModal;