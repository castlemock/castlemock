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
// import ToolkitProvider from "react-bootstrap-table2-toolkit";
// import BootstrapTable from "react-bootstrap-table-next";
// import PaginationFactory from "react-bootstrap-table2-paginator";
import axios from "axios";
import validateErrorResponse from "../../../../../utility/HttpResponseValidator";
import {faCheckCircle} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";


class UpdateEndpointModal extends PureComponent {

    constructor(props) {
        super(props);
        this.onUpdateEndpointClick = this.onUpdateEndpointClick.bind(this);
        this.setForwardedEndpoint = this.setForwardedEndpoint.bind(this);

        this.columns = [{
            dataField: 'id',
            text: 'id',
            hidden: true
        }, {
            dataField: 'name',
            text: 'Name',
            sort: true
        }];

        this.defaultSort = [{
            dataField: 'name',
            order: 'asc'
        }];

        this.state = {
            forwardedEndpoint: "",
        };
    }

    setForwardedEndpoint(event) {
        this.setState({ forwardedEndpoint: event.target.value });
    }

    onUpdateEndpointClick() {
        let resourceIds = this.props.selectedResources.map(resource => resource.id);
        axios
            .put(process.env.PUBLIC_URL + "/api/rest/rest/project/" + this.props.projectId + "/application/" + this.props.applicationId + "/resource/endpoint/forwarded", {
                projectId: this.props.projectId,
                applicationId: this.props.applicationId,
                resourceIds: resourceIds,
                forwardedEndpoint: this.state.forwardedEndpoint
            })
            .then(response => {
                this.props.getApplication();
            })
            .catch(error => {
                validateErrorResponse(error)
            });
    }

    render() {
        return (
            <div className="modal fade" id="updateEndpointModal" tabIndex="-1" role="dialog"
                 aria-labelledby="updateEndpointModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="updateEndpointModalLabel">Update endpoint?</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <p>Do you want update the endpoint for the following resources?</p>
                            <div className="table-result">
                                {/* <ToolkitProvider bootstrap4
                                                 columns={ this.columns}
                                                 data={ this.props.selectedResources }
                                                 keyField="id">
                                    {
                                        (props) => (
                                            <div>
                                                <BootstrapTable { ...props.baseProps } bootstrap4 data={this.props.selectedResources} columns={this.columns}
                                                                defaultSorted={ this.defaultSort } keyField='id' hover
                                                                striped
                                                                pagination={ PaginationFactory({hideSizePerPage: true}) }/>
                                            </div>
                                        )}
                                </ToolkitProvider> */}
                            </div>
                            <div className="form-group row">
                                <label className="col-sm-2 col-form-label">Endpoint</label>
                                <div className="col-sm-10">
                                    <input className="form-control" type="text" onChange={this.setForwardedEndpoint} />
                                </div>
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-primary" data-dismiss="modal" onClick={this.onUpdateEndpointClick}><FontAwesomeIcon icon={faCheckCircle} className="button-icon"/>Update</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default UpdateEndpointModal;