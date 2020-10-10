import { SET_AUTHENTICATION_STATE } from "./ActionStates";

export const setAuthenticationState = authenticationState => ({ type: SET_AUTHENTICATION_STATE, payload: { authenticationState } });

