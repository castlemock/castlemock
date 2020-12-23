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
import {definitionTypeFormatter} from "../../utility/RestFormatter"

class UploadDefinitionModal extends PureComponent {

    constructor(props){
        super(props);
        this.setFile = this.setFile.bind(this);
        this.uploadDefinition = this.uploadDefinition.bind(this);
        this.linkDefinition = this.linkDefinition.bind(this);
        this.setLinkDefinitionUrl = this.setLinkDefinitionUrl.bind(this);
        this.setGenerateResponseLink = this.setGenerateResponseLink.bind(this);
        this.setGenerateResponseUpload = this.setGenerateResponseUpload.bind(this);

        this.state = {
            selectedFile: null,
            selectedFileName: "",
            generateResponseLink: "true",
            generateResponseUpload: "true",
            linkDefinitionUrl: ""
        }
    }

    setLinkDefinitionUrl(event) {
        this.setState({
            linkDefinitionUrl: event.target.value
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

    setFile(event) {
        let selectedFile = event.target.files[0];
        this.setState({
            selectedFile: selectedFile,
            selectedFileName: selectedFile.name
        });
    }


    uploadDefinition(){
        let data = new FormData();
        data.append('file', this.state.selectedFile);
        data.append('generateResponse', this.state.generateResponseUpload);
        data.append('definitionType', this.props.definitionType)

        axios
            .post(process.env.PUBLIC_URL + "/api/rest/rest/project/" + this.props.projectId + "/definition/file", data, {})
            .then(response => {
                this.props.getProject();
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    linkDefinition(){
        axios
            .post(process.env.PUBLIC_URL + "/api/rest/rest/project/" + this.props.projectId + "/definition/link", {
                url: this.state.linkDefinitionUrl,
                generateResponse: this.state.generateResponseLink,
                definitionType: this.props.definitionType
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
            <div className="modal fade" id={"upload" + this.props.definitionType + "DefinitionModal"} tabIndex="-1" role="dialog"
                 aria-labelledby="uploadDefinitionModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="uploadDefinitionModalLabel">Upload {definitionTypeFormatter(this.props.definitionType)}</h5>
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
                                        <input className="form-control" type="text" onChange={this.setLinkDefinitionUrl}/>
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
                                            disabled={this.state.linkDefinitionUrl === ""}
                                            onClick={this.linkDefinition}>Link</button>
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
                                            onClick={this.uploadDefinition}>Upload</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default UploadDefinitionModal;