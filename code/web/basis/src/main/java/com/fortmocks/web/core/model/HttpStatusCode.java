package com.fortmocks.web.core.model;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public enum HttpStatusCode {

    CONTINUE(100, "100 - Continue"),
    SWITCHING_PROTOCOLS(101, "101 - Switching Protocols"),
    PROCESSING(102, "102 - Processing"),
    OK(200, "200 - OK"),
    CREATED(201, "201 - Created"),
    ACCEPTED(202, "202 - Accepted"),
    NON_AUTHORITATIVE_INFORMATION(203, "203 - Non-Authoritative Information"),
    NO_CONTENT(204, "204 - No Content"),
    RESET_CONTENT(205, "205 - Reset Content"),
    PARTIAL_CONTENT(206, "206 - Partial Content"),
    MULTI_STATUS(207, "207 - Multi-Status"),
    ALREADY_REPORTED(208, "208 - Already Reported"),
    IM_USED(226, "226 - IM Used"),
    MULTIPLE_CHOICES(300, "300 - Multiple Choices"),
    MOVED_PERMANENTLY(301, "301 - Moved Permanently"),
    FOUND(302, "302 - Found"),
    SEE_OTHER(303, "303 - See Other"),
    NOT_MODIFIED(304, "304 - Not Modified"),
    USE_PROXY(305, "305 - Use Proxy"),
    SWITCH_PROXY(306, "306 - Switch Proxy"),
    TEMPORARY_REDIRECT(307, "307 - Temporary Redirect"),
    PERMANENT_REDIRECT(308, "308 - Permanent Redirect"),
    RESUME_INCOMPLETE(308, "308 - Resume Incomplete"),
    BAD_REQUEST(400, "400 - Bad Request"),
    UNAUTHORIZED(401, "401 - Unauthorized"),
    PAYMENT_REQUIRED(402, "402 - Payment Required"),
    FORBIDDEN(403, "403 - Forbidden"),
    NOT_FOUND(404, "404 - Not Found"),
    METHOD_NOT_ALLOWED(405, "405 - Method Not Allowed"),
    NOT_ACCEPTABLE(406, "406 - Not Acceptable"),
    PROXY_AUTHENTICATION_REQUIRED(407, "407 - Proxy Authentication Required"),
    REQUEST_TIMEOUT(408, "408 - Request Timeout"),
    CONFLICT(409, "409 - Conflict"),
    GONE(410, "410 - Gone"),
    LENGTH_REQUIRED(411, "411 - Length Required"),
    PRECONDITION_FAILED(412, "412 - Precondition Failed"),
    PAYLOAD_TOO_LARGE(413, "413 - Payload Too Large"),
    REQUEST_URI_TOO_LONG(414, "414 - Request-URI Too Long"),
    UNSUPPORTED_MEDIA_TYPE(415, "415 - Unsupported Media Type"),
    REQUESTED_RANGE_SATISFIABLE(416, "416 - Requested Range Not Satisfiable"),
    EXPECTATION_FAILED(417, "417 - Expectation Failed"),
    IM_A_TEAPOT(418, "418 - I'm a teapot"),
    AUTHENTICATION_TIMEOUT(419, "419 - Authentication Timeout"),
    METHOD_FAILURE(420, "420 - Method Failure"),
    ENHANCE_YOUR_CALM(420, "420 - Enhance Your Calm"),
    MISDIRECTED_REQUEST(421, "421 - Misdirected Request"),
    UNPROCESSABLE_ENTITY(422, "422 - Unprocessable Entity"),
    LOCKED(423, "423 - Locked"),
    FAILED_DEPENDENCY(424, "424 - Failed Dependency"),
    UPGRADE_REQUIRED(426, "426 - Upgrade Required"),
    PRECONDITION_REQUIRED(428, "428 - Precondition Required"),
    TOO_MANY_REQUESTS(429, "429 - Too Many Requests"),
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, "431 - Request Header Fields Too Large"),
    LOGIN_TIMEOUT(440, "440 - Login Timeout"),
    NO_RESPONSE(444, "444 - No Response"),
    RETRY_WITH(449, "449 - Retry With"),
    BLOCKED_BY_WINDOWS_PARENTAL_CONTROLS(450, "450 - Blocked by Windows Parental Controls"),
    UNAVAILABLE_FOR_LEGAL_REASONS(451, "451 - Unavailable For Legal Reasons"),
    REDIRECT(451, "451 - Redirect"),
    REQUEST_HEADER_TOO_LARGE(494, "494 - Request Header Too Large"),
    CERT_ERROR(495, "495 - Cert Error"),
    NO_CERT(496, "496 - No Cert"),
    HTTP_TO_HTTPS(497, "497 - HTTP to HTTPS"),
    TOKEN_EXPIRED_INVALID(498, "498 - Token expired/invalid"),
    CLIENT_CLOSED_REQUEST(499, "499 - Client Closed Request"),
    TOKEN_REQUIRED(499, "499 - Token required"),
    INTERNAL_SERVER_ERROR(500, "500 - Internal Server Error"),
    NOT_IMPLEMENTED(501, "501 - Not Implemented"),
    BAD_GATEWAY(502, "502 - Bad Gateway"),
    SERVICE_UNAVAILABLE(503, "503 - Service Unavailable"),
    GATEWAY_TIMEOUT(504, "504 - Gateway Timeout"),
    HTTP_VERSION_NOT_SUPPORTED(505, "505 - HTTP Version Not Supported"),
    VARIANT_ALSO_NEGOTIATES(506, "506 - Variant Also Negotiates"),
    INSUFFICIENT_STORAGE(507, "507 - Insufficient Storage"),
    LOOP_DETECTED(508, "508 - Loop Detected"),
    BANDWIDTH_LIMIT_EXCEEDED(509, "509 - Bandwidth Limit Exceeded"),
    NOT_EXTENDED(510, "510 - Not Extended"),
    NETWORK_AUTHENTICATION_REQUIRED(511, "511 - Network Authentication Required"),
    UNKNOWN_ERROR(520, "520 - Unknown Error"),
    ORIGIN_CONNECTION_TIME_OUT(522, "522 - Origin Connection Time-out"),
    NETWORK_READ_TIMEOUT_ERROR(598, "598 - Network read timeout error"),
    NETWORK_CONNECT_TIMEOUT_ERROR(599, "599 - Network connect timeout error");

    private int code;
    private String message;

    private HttpStatusCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
