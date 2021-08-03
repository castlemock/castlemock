package com.castlemock.web.core.utility;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

public class BaseUrlInfoTest {

	@Test
	public void testBuildUrl() {
		final HttpServletRequest httpServletRequest = mockHttpServletRequest("http", "localhost", 8080, "/castlemock");
		BaseUrlInfo baseUrlInfo = BaseUrlInfo.builder(httpServletRequest).build();

		Assert.assertEquals("http", baseUrlInfo.getProtocol());
		Assert.assertEquals("localhost", baseUrlInfo.getServerName());
		Assert.assertEquals(8080, baseUrlInfo.getServerPort());
		Assert.assertEquals("/castlemock", baseUrlInfo.getContextPath());
		Assert.assertEquals("http://localhost:8080", baseUrlInfo.getServerUrl());
		Assert.assertEquals("http://localhost:8080/castlemock", baseUrlInfo.getBaseUrl());
	}

	@Test
	public void testShouldNotAppendPort80OnUrl() {
		final HttpServletRequest httpServletRequest = mockHttpServletRequest("http", "www.castlemock.com", 80, "/castlemock");
		final BaseUrlInfo baseUrlInfo = BaseUrlInfo.builder(httpServletRequest).build();
		
		Assert.assertEquals("http", baseUrlInfo.getProtocol());
		Assert.assertEquals("www.castlemock.com", baseUrlInfo.getServerName());
		Assert.assertEquals(80, baseUrlInfo.getServerPort());
		Assert.assertEquals("/castlemock", baseUrlInfo.getContextPath());
		Assert.assertEquals("http://www.castlemock.com", baseUrlInfo.getServerUrl());
		Assert.assertEquals("http://www.castlemock.com/castlemock", baseUrlInfo.getBaseUrl());
	}

	@Test
	public void testShouldNotAppendPort443OnUrl() {
		final HttpServletRequest httpServletRequest = mockHttpServletRequest("https", "www.castlemock.com", 443, "/castlemock");
		BaseUrlInfo baseUrlInfo = BaseUrlInfo.builder(httpServletRequest).build();

		Assert.assertEquals("https", baseUrlInfo.getProtocol());
		Assert.assertEquals("www.castlemock.com", baseUrlInfo.getServerName());
		Assert.assertEquals(443, baseUrlInfo.getServerPort());
		Assert.assertEquals("/castlemock", baseUrlInfo.getContextPath());
		Assert.assertEquals("https://www.castlemock.com", baseUrlInfo.getServerUrl());
		Assert.assertEquals("https://www.castlemock.com/castlemock", baseUrlInfo.getBaseUrl());
	}
	
	@Test
	public void testPreferedServerName() {
		final HttpServletRequest httpServletRequest = mockHttpServletRequest("http", "localhost", 8080, "/castlemock");
		BaseUrlInfo baseUrlInfo = BaseUrlInfo.builder(httpServletRequest).preferedServerName("my-prefered-server-name").build();

		Assert.assertEquals("http", baseUrlInfo.getProtocol());
		Assert.assertEquals("my-prefered-server-name", baseUrlInfo.getServerName());
		Assert.assertEquals(8080, baseUrlInfo.getServerPort());
		Assert.assertEquals("/castlemock", baseUrlInfo.getContextPath());
		Assert.assertEquals("http://my-prefered-server-name:8080", baseUrlInfo.getServerUrl());
		Assert.assertEquals("http://my-prefered-server-name:8080/castlemock", baseUrlInfo.getBaseUrl());
	}
	

	@Test
	public void givenXForwardedProtoEqualhttps_schemaEqualHttp_shouldReturnProtocolhttps() {
		final HttpServletRequest httpServletRequest = mockHttpServletRequest("http", "www.castlemock.com", 443, "/castlemock");
		Mockito.when(httpServletRequest.getHeader("X-Forwarded-Proto")).thenReturn("https");
		
		BaseUrlInfo baseUrlInfo = BaseUrlInfo.builder(httpServletRequest).build();

		Assert.assertEquals("https", baseUrlInfo.getProtocol());
		Assert.assertEquals("https://www.castlemock.com", baseUrlInfo.getServerUrl());
		Assert.assertEquals("https://www.castlemock.com/castlemock", baseUrlInfo.getBaseUrl());
	}

	private HttpServletRequest mockHttpServletRequest(final String protocol, final String serverName,
			final int serverPort, final String contextPath) {
		final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
		Mockito.when(httpServletRequest.getScheme()).thenReturn(protocol);
		Mockito.when(httpServletRequest.getServerName()).thenReturn(serverName);
		Mockito.when(httpServletRequest.getServerPort()).thenReturn(serverPort);
		Mockito.when(httpServletRequest.getContextPath()).thenReturn(contextPath);
		return httpServletRequest;
	}

}
