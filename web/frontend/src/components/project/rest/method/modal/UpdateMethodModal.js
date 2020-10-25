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

class UpdateMethodModal extends PureComponent {

    constructor(props) {
        super(props);
        this.setUpdateMethodName = this.setUpdateMethodName.bind(this);
        this.setUpdateMethodDescription = this.setUpdateMethodDescription.bind(this);

        this.state = {

        };
    }

    setUpdateMethodName() {

    }

    setUpdateMethodDescription() {

    }

    render() {
        return (

            <div className="modal fade" id="updateMethodModal" tabIndex="-1" role="dialog"
                 aria-labelledby="updateMethodModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="updateMethodModalLabel">Update method</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <form>
                                <div className="form-group row">
                                    <label htmlFor="newMethodName" className="col-sm-2 col-form-label">Name</label>
                                    <div className="col-sm-10">
                                        <input className="form-control" type="text" name="updateMethodName" id="updateMethodName" onChange={event => this.setUpdateMethodName(event.target.value)}/>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label htmlFor="newMethodDescription" className="col-sm-2 col-form-label">Description</label>
                                    <div className="col-sm-10">
                                        <textarea className="form-control" name="updateMethodDescription" id="updateMethodDescription" onChange={event => this.setUpdateMethodDescription(event.target.value)}/>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-success" data-dismiss="modal">Update</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default UpdateMethodModal;