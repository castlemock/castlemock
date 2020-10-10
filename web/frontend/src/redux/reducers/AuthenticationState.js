import { SET_AUTHENTICATION_STATE } from "../ActionStates";

const initialState = {
    authenticationState: true
}

export default function (state = initialState, action) {
    switch (action.type) {
        case SET_AUTHENTICATION_STATE: {
            return {
                authenticationState: action.payload.authenticationState
            };
        }
        default:
            return state;
    }
}