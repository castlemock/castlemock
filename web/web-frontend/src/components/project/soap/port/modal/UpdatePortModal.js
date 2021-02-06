/*
 Copyright 2021 Karl Dahlgren

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
import preventEnterEvent from "../../../../../utility/KeyboardUtility";
import {faCheckCircle} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

class UpdatePortModal extends PureComponent {

    constructor(props) {
        super(props);
        this.onUriChange = this.onUriChange.bind(this);
        this.onUpdatePortClick = this.onUpdatePortClick.bind(this);
        this.getPort = this.getPort.bind(this);
        this.state = {
            updatePort: {}
        };

        this.getPort(props.projectId, props.portId)
    }

    getPort(projectId, portId) {
        axios
            .get(process.env.PUBLIC_URL + "/api/rest/soap/project/" + projectId + "/port/" + portId)
            .then(response => {
                this.setState({
                    updatePort: {
                        uri: response.data.uri
                    }
                });
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    onUriChange(uri){
        this.setState({ updatePort: {
                ...this.state.updatePort,
                uri: uri
            }
        });
    }

    onUpdatePortClick(){
        axios
            .put(process.env.PUBLIC_URL + "/api/rest/soap/project/" + this.props.projectId + "/port/" +
                this.props.portId, this.state.updatePort)
            .then(response => {
                this.props.getPort();
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    render() {
        return (
            <div className="modal fade" id="updatePortModal" tabIndex="-1" role="dialog"
                 aria-labelledby="updatePortModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="updatePortModalLabel">Update port</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <div className="form-group row">
                                <label className="col-sm-3 col-form-label">URI</label>
                                <div className="col-sm-9">
                                    <input className="form-control" type="text"
                                           defaultValue={this.state.updatePort.uri}
                                           onChange={event => this.onUriChange(event.target.value)} onKeyDown={preventEnterEvent}/>
                                </div>
                            </div>

                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-success" data-dismiss="modal" onClick={this.onUpdatePortClick}><FontAwesomeIcon icon={faCheckCircle} className="button-icon"/>Update</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default UpdatePortModal;