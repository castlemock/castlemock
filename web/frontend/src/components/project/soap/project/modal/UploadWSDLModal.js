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
import Dropzone from "react-dropzone";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faUpload} from "@fortawesome/free-solid-svg-icons";


class UploadWSDLModal extends PureComponent {

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
                            <div className="dropzone-content">
                                <Dropzone styles={{ dropzone: { minHeight: 200, maxHeight: 250 } }}>
                                    {({getRootProps, getInputProps}) => (
                                        <div {...getRootProps()}>
                                            <input {...getInputProps()} />
                                            <div className="dropzone-icon-content">
                                                <FontAwesomeIcon icon={faUpload} className="dropzone-icon" />
                                            </div>
                                            <p>Drag 'n' drop some files here, or click to select files</p>
                                        </div>
                                    )}
                                </Dropzone>
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-success" data-dismiss="modal" onClick={this.onCreateProjectClick}>Upload</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default UploadWSDLModal;