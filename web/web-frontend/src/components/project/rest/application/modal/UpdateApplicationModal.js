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

class UpdateApplicationModal extends PureComponent {

    constructor(props) {
        super(props);
        this.setName = this.setName.bind(this);
        this.onUpdateClick = this.onUpdateClick.bind(this);
        this.getApplication = this.getApplication.bind(this);

        this.state = {
            updateApplication: {}
        };

        this.getApplication();
    }

    getApplication() {
        axios
            .get(process.env.PUBLIC_URL + "/api/rest/rest/project/" + this.props.projectId + "/application/" + this.props.applicationId)
            .then(response => {
                this.setState({
                    updateApplication: {
                        name: response.data.name
                    }
                });
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    setName(source) {
        this.setState({ updateApplication: {
                ...this.state.updateApplication,
                name: source.target.value
            }
        });
    }

    onUpdateClick() {
        axios
            .put(process.env.PUBLIC_URL + "/api/rest/rest/project/" + this.props.projectId + "/application/" + this.props.applicationId, this.state.updateApplication)
            .then(response => {
                this.props.getApplication();
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    render() {
        return (
            <div className="modal fade" id="updateApplicationModal" tabIndex="-1" role="dialog"
                 aria-labelledby="updateApplicationModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="updateApplicationModalLabel">Update application</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <div className="form-group row">
                                <label className="col-sm-2 col-form-label">Name</label>
                                <div className="col-sm-10">
                                    <input className="form-control" type="text" defaultValue={this.state.updateApplication.name} onChange={this.setName}/>
                                </div>
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-success" data-dismiss="modal" onClick={this.onUpdateClick}>Update</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default UpdateApplicationModal;