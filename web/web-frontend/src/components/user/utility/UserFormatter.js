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

const userStatusFormatter = (status) => {
    if(status === "ACTIVE"){
        return "Active";
    } else if(status === "INACTIVE"){
        return "Inactive"
    } else if(status === "LOCKED"){
        return "Locked"
    }

    return status;
};

const userRoleFormatter = (role) => {
    if(role === "READER"){
        return "Reader";
    } else if(role === "MODIFIER"){
        return "Modifier"
    } else if(role === "ADMIN"){
        return "Admin"
    }

    return role;
};

export {
    userStatusFormatter,
    userRoleFormatter
};