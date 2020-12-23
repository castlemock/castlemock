package com.castlemock.web.mock.soap.utility.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddressLocationConfigurerTest {
	
	private static final String WSDL_WITHOU_ADDRESS_LOCATION = "<wsdl:definitions>\n" +
			"  <wsdl:service name=\"Service\">\n" + 
			"    <wsdl:port name=\"ServiceHttpPost\" binding=\"tns:ServiceHttpPost\">\n" +
			"    </wsdl:port>\n" + 
			"  </wsdl:service>\n" +
			"</wsdl:definitions>";
	
	private static final String ORIGINAL_WSDL_WITH_ADDRESS_LOCATION = "<wsdl:definitions>\n" +
			"  <wsdl:service name=\"Service\">\n" + 
			"    <wsdl:port name=\"ServiceHttpPost\" binding=\"tns:ServiceHttpPost\">\n" +
			"      <soap:address location=\"http://www.castlemock.com/services/myservice\"/>\n" + 
			"    </wsdl:port>\n" + 
			"  </wsdl:service>\n" +
			"</wsdl:definitions>";
	
	private static final String MODIFIED_WSDL_WITH_ADDRESS_LOCATION = "<wsdl:definitions>\n" +
			"  <wsdl:service name=\"Service\">\n" + 
			"    <wsdl:port name=\"ServiceHttpPost\" binding=\"tns:ServiceHttpPost\">\n" +
			"      <soap:address location=\"http://localhost:8080/other-path\"/>\n" + 
			"    </wsdl:port>\n" + 
			"  </wsdl:service>\n" +
			"</wsdl:definitions>";

	@Test
	public void testWsdlWithAddressLocation() {
		String wsdlModified = new AddressLocationConfigurer().configureAddressLocation(ORIGINAL_WSDL_WITH_ADDRESS_LOCATION, "http://localhost:8080/other-path");
		Assertions.assertEquals(MODIFIED_WSDL_WITH_ADDRESS_LOCATION, wsdlModified);
	}
	
	@Test
	public void testWsdlWithoutAddressLocation() {
		String wsdlResult = new AddressLocationConfigurer().configureAddressLocation(WSDL_WITHOU_ADDRESS_LOCATION, "http://localhost:8080/other-path");
		Assertions.assertEquals(WSDL_WITHOU_ADDRESS_LOCATION, wsdlResult);
	}

}
