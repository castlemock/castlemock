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
import {faCheckCircle} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";


class UpdateResourceModal extends PureComponent {

    constructor(props) {
        super(props);
        this.onUpdateResourceClick = this.onUpdateResourceClick.bind(this);
        this.setName = this.setName.bind(this);
        this.setUri = this.setUri.bind(this);
        this.getResource = this.getResource.bind(this);

        this.state = {
            updateResource: {
            }
        };

        this.getResource();
    }

    setName(source) {
        this.setState({
            updateResource: {
                ...this.state.updateResource,
                name: source.target.value
            }
        });
    }

    setUri(source) {
        this.setState({
            updateResource: {
                ...this.state.updateResource,
                uri: source.target.value
            }
        });
    }

    onUpdateResourceClick() {
        axios
            .put(process.env.PUBLIC_URL + "/api/rest/rest/project/" + this.props.projectId + "/application/" + this.props.applicationId + "/resource/" + this.props.resourceId, this.state.updateResource)
            .then(response => {
                this.props.getResource();
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    getResource() {
        axios
            .get(process.env.PUBLIC_URL + "/api/rest/rest/project/" + this.props.projectId  + "/application/" + this.props.applicationId + "/resource/" + this.props.resourceId)
            .then(response => {
                this.setState({
                    updateResource: {
                        name: response.data.name,
                        uri: response.data.uri
                    }
                });
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    render() {
        return (
            <div className="modal fade" id="updateResourceModal" tabIndex="-1" role="dialog"
                 aria-labelledby="updateResourceModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="updateResourceModalLabel">Update resource</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <div className="form-group row">
                                <label className="col-sm-2 col-form-label">Name</label>
                                <div className="col-sm-10">
                                    <input className="form-control" type="text" defaultValue={this.state.updateResource.name} onChange={this.setName}/>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-sm-2 col-form-label">Uri</label>
                                <div className="col-sm-10">
                                    <input className="form-control" type="text" defaultValue={this.state.updateResource.uri} onChange={this.setUri}/>
                                </div>
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-success" data-dismiss="modal" onClick={this.onUpdateResourceClick}><FontAwesomeIcon icon={faCheckCircle} className="button-icon"/>Update</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default UpdateResourceModal;