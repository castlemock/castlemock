/*
 * Copyright 2024 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.web.mock.soap.utility.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddressLocationConfigurerTest {
	
	private static final String WSDL_WITHOU_ADDRESS_LOCATION = """
            <wsdl:definitions>
              <wsdl:service name="Service">
                <wsdl:port name="ServiceHttpPost" binding="tns:ServiceHttpPost">
                </wsdl:port>
              </wsdl:service>
            </wsdl:definitions>""";
	
	private static final String ORIGINAL_WSDL_WITH_ADDRESS_LOCATION = """
            <wsdl:definitions>
              <wsdl:service name="Service">
                <wsdl:port name="ServiceHttpPost" binding="tns:ServiceHttpPost">
                  <soap:address location="http://www.castlemock.com/services/myservice"/>
                </wsdl:port>
              </wsdl:service>
            </wsdl:definitions>""";
	
	private static final String MODIFIED_WSDL_WITH_ADDRESS_LOCATION = """
            <wsdl:definitions>
              <wsdl:service name="Service">
                <wsdl:port name="ServiceHttpPost" binding="tns:ServiceHttpPost">
                  <soap:address location="http://localhost:8080/other-path"/>
                </wsdl:port>
              </wsdl:service>
            </wsdl:definitions>""";

	@Test
	public void testWsdlWithAddressLocation() {
		String wsdlModified = AddressLocationConfigurer.configureAddressLocation(ORIGINAL_WSDL_WITH_ADDRESS_LOCATION, "http://localhost:8080/other-path");
		Assertions.assertEquals(MODIFIED_WSDL_WITH_ADDRESS_LOCATION, wsdlModified);
	}
	
	@Test
	public void testWsdlWithoutAddressLocation() {
		String wsdlResult = AddressLocationConfigurer.configureAddressLocation(WSDL_WITHOU_ADDRESS_LOCATION, "http://localhost:8080/other-path");
		Assertions.assertEquals(WSDL_WITHOU_ADDRESS_LOCATION, wsdlResult);
	}

}
