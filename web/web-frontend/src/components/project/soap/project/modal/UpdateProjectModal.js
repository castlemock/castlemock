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
import preventEnterEvent from "../../../../../utility/KeyboardUtility";
import {faCheckCircle} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";


class UpdateProjectModal extends PureComponent {

    constructor(props) {
        super(props);
        this.setUpdateProjectName = this.setUpdateProjectName.bind(this);
        this.setUpdateProjectDescription = this.setUpdateProjectDescription.bind(this);
        this.onUpdateProjectClick = this.onUpdateProjectClick.bind(this);
        this.getProject = this.getProject.bind(this);

        this.state = {
            updateProject: {}
        };

        this.getProject()
    }

    getProject(projectId) {
        axios
            .get(process.env.PUBLIC_URL + "/api/rest/soap/project/" + this.props.projectId)
            .then(response => {
                this.setState({
                    updateProject: {
                        name: response.data.name,
                        description: response.data.description
                    }
                });
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }


    setUpdateProjectName(event) {
        this.setState({ updateProject: {
                ...this.state.updateProject,
                name: event.target.value
            }
        });
    }

    setUpdateProjectDescription(event) {
        this.setState({ updateProject: {
                ...this.state.updateProject,
                description: event.target.value
            }
        });
    }

    onUpdateProjectClick() {
        axios
            .put(process.env.PUBLIC_URL + "/api/rest/core/project/soap/" + this.props.projectId, this.state.updateProject)
            .then(response => {
                this.props.getProject();
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    render() {
        return (
            <div className="modal fade" id="updateProjectModal" tabIndex="-1" role="dialog"
                 aria-labelledby="updateProjectModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="newProjectModalLabel">Update project</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <form>
                                <div className="form-group row">
                                    <label htmlFor="updateProjectName" className="col-sm-2 col-form-label">Name</label>
                                    <div className="col-sm-10">
                                        <input className="form-control" type="text" defaultValue={this.props.project.name} onChange={this.setUpdateProjectName} onKeyDown={preventEnterEvent} required/>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label htmlFor="updateProjectDescription" className="col-sm-2 col-form-label">Description</label>
                                    <div className="col-sm-10">
                                        <textarea className="form-control" value={this.state.updateProject.description} onChange={this.setUpdateProjectDescription}/>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-success" data-dismiss="modal" onClick={this.onUpdateProjectClick}><FontAwesomeIcon icon={faCheckCircle} className="button-icon"/>Update</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default UpdateProjectModal;