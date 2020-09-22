package com.castlemock.web.basis.utility;

import javax.servlet.http.HttpServletRequest;

public class BaseUrlInfo {

	private final String protocol;
	private final String serverName;
	private final int serverPort;
	private final String contextPath;
	private final String serverUrl;
	private final String baseUrl;

	public BaseUrlInfo(final String protocol, final String serverName, int serverPort, final String contextPath) {
		this.protocol = protocol;
		this.serverName = serverName;
		this.serverPort = serverPort;
		this.contextPath = contextPath;
		this.serverUrl = buildServerUrl(protocol, serverName, serverPort);
		this.baseUrl = new StringBuilder().append(this.serverUrl).append(contextPath).toString();
	}

	private String buildServerUrl(String protocol, String hostname, int port) {
		StringBuilder baseUrl = new StringBuilder();
		baseUrl.append(protocol).append("://").append(hostname);
		if (port != 80 && port != 443) {
			baseUrl.append(':').append(port);
		}
		return baseUrl.toString();
	}

	public static Builder builder(final HttpServletRequest request) {
		return new Builder(request);
	}

	/**
	 * Returns the name of the protocol used to make this request, http or https.
     * 
	 * @return a String containing the name of the protocol used to make this request
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * Returns the host name of the server to which the request was sent. It is the value of the part before ":" in the Host header value,
	 * if any, or the resolved server name, or the server IP address.
	 * 
	 * @return a String containing the name of the server
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * Returns the port number to which the request was sent. It is the value of the part after ":" in the Host header value,
	 * if any, or the server port where the client connection was accepted on.
	 * 
	 * @return an integer specifying the port number
	 */
	public int getServerPort() {
		return serverPort;
	}

	/**
     * Returns the current application context path. For example the /castlemock in http://localhost:8080/castlemock
     * @return The current application context path
     */
	public String getContextPath() {
		return contextPath;
	}

	/**
	 * Returns the server url, without the application context path. For example http://localhost:8080 or https://www.castlemock.com
	 * 
	 * @return The application server url
	 */

	public String getServerUrl() {
		return serverUrl;
	}

	/**
	 * Returns the application base url, with the application context path, if any. For example http://localhost:8080/castlemock or https://www.castlemock.com/castlemock
	 * 
	 * @return The application base url
	 */
	public String getBaseUrl() {
		return baseUrl;
	}
	
	@Override
	public String toString() {
		return "BaseUrlInfo{protocol=" + protocol + ", serverName=" + serverName + ", serverPort=" + serverPort
				+ ", contextPath=" + contextPath + ", serverUrl=" + serverUrl + ", baseUrl=" + baseUrl + "}";
	}



	public static final class Builder {
		private final HttpServletRequest request;
		private String preferedServerName;

		private Builder(final HttpServletRequest request) {
			this.request = request;
		}

		/**
		 * If <code>preferedServerName</code> is non-null and non-empty, it will be used as the server name.
         * Otherwise, the server name will be obtained from </code>request.getServerName()</code>
         * 
		 * @param preferedServerName the server name to be used, if non-null and non-empty. 
		 * @return The {@link Builder} instance  
		 */
		public Builder preferedServerName(final String preferedServerName) {
			this.preferedServerName = preferedServerName;
			return this;
		}

		public BaseUrlInfo build() {
			return new BaseUrlInfo(determineProtocol(), determineServerName(), request.getServerPort(), request.getContextPath());
		}

		private String determineProtocol() {
			Object xForwardedProto = this.request.getHeader("X-Forwarded-Proto");
			if (xForwardedProto != null) {
				return xForwardedProto.toString();
			}
			return this.request.getScheme();
		}

		private String determineServerName() {
			if (preferedServerName != null && !preferedServerName.isEmpty()) {
				return preferedServerName;
			}
			return request.getServerName();
		}

	}

}
