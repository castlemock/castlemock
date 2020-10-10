import { createStore } from "redux";
import authenticationReducer from "./reducers/AuthenticationState"

export default createStore(authenticationReducer);
