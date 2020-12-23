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
import preventEnterEvent from "../../../../../utility/KeyboardUtility"
import { withRouter } from "react-router";

class CreateApplicationModal extends PureComponent {

    constructor(props) {
        super(props);
        this.onCreateApplicationClick = this.onCreateApplicationClick.bind(this);
        this.setNewApplicationName = this.setNewApplicationName.bind(this);

        this.state = {
            newApplication: {
                name: ""
            }
        };
    }

    setNewApplicationName(name) {
        this.setState({
            newApplication: {
                name: name
            }
        });
    }

    onCreateApplicationClick() {
        axios
            .post(process.env.PUBLIC_URL + "/api/rest/rest/project/" + this.props.projectId + "/application", this.state.newApplication)
            .then(response => {
                this.props.history.push("/web/rest/project/" + this.props.projectId + "/application/" + response.data.id);
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    render() {
        return (
            <div className="modal fade" id="createApplicationModal" tabIndex="-1" role="dialog"
                 aria-labelledby="createApplicationModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="createApplicationModalLabel">Create application</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <form>
                                <div className="form-group row">
                                    <label className="col-sm-2 col-form-label">Name</label>
                                    <div className="col-sm-10">
                                        <input className="form-control" type="text" value={this.state.newApplication.name} onChange={event => this.setNewApplicationName(event.target.value)} onKeyDown={preventEnterEvent}/>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-success" data-dismiss="modal" onClick={this.onCreateApplicationClick} onKeyDown={this.onCreateApplication}>Create</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(CreateApplicationModal);