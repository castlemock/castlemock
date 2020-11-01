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

const mockResponseStatusFormatter = (status) => {
    if(status === "ENABLED"){
        return "Enabled";
    } else if(status === "DISABLED"){
        return "Disabled"
    }

    return status;
};

const operationIdentifyStrategy = (identifyStrategy) => {
    if(identifyStrategy === "ELEMENT"){
        return "Element";
    } else if(identifyStrategy === "ELEMENT_NAMESPACE"){
        return "Element and namespace"
    }

    return identifyStrategy;
};

const operationResponseStrategy = (responseStrategy) => {
    if(responseStrategy === "RANDOM"){
        return "Random";
    } else if(responseStrategy === "SEQUENCE"){
        return "Sequence"
    } else if(responseStrategy === "XPATH_INPUT"){
        return "XPath"
    }
    return responseStrategy;
};

const operationSoapVersionFormatter = (soapVersion) => {
    if(soapVersion === "SOAP11"){
        return "SOAP 1.1";
    } else if(soapVersion === "SOAP12"){
        return "SOAP 1.2"
    }

    return soapVersion;
};

const operationStatusFormatter = (status) => {
    if(status === "MOCKED"){
        return "Mocked";
    } else if(status === "DISABLED"){
        return "Disabled"
    } else if(status === "FORWARDED"){
        return "Forwarded"
    } else if(status === "RECORDING"){
        return "Recording"
    } else if(status === "RECORD_ONCE"){
        return "Record once"
    } else if(status === "ECHO"){
        return "Echo"
    }

    return status;
};

export {
    mockResponseStatusFormatter,
    operationIdentifyStrategy,
    operationResponseStrategy,
    operationSoapVersionFormatter,
    operationStatusFormatter
};