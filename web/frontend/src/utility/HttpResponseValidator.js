function validateErrorResponse(error, setAuthenticationState){
    if (error.response.status === 401 || error.response.status === 403) {
        setAuthenticationState(false);
    }
}

export default validateErrorResponse;