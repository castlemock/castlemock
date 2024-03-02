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

package com.castlemock.web.mock.soap.utility;

public class SoapUtility {

	private SoapUtility() {

	}
	
	public static String getWsdlAddress(final String wsdl, final String correctAddressLocation) {
		final String addressLocationTag = "<soap:address location=\"";
		final int beginIndexAddressLocationTag = wsdl.indexOf(addressLocationTag);
		if (beginIndexAddressLocationTag < 0) {
			return wsdl;
		}

		final int beginIndexUrl = beginIndexAddressLocationTag + addressLocationTag.length();
		final int endIndexUrl = wsdl.indexOf("\"", beginIndexUrl);

		return wsdl.substring(0, beginIndexUrl) +
				correctAddressLocation +
				wsdl.substring(endIndexUrl);
	}

}
