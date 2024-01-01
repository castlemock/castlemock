package com.castlemock.web.mock.soap.utility.config;

public class AddressLocationConfigurer {
	
	public String configureAddressLocation(String wsdl, String correctAddressLocation) {
		final String adressLocationTag = "<soap:address location=\"";
		final int beginIndexAdressLocationTag = wsdl.indexOf(adressLocationTag);
		if (beginIndexAdressLocationTag >= 0) {
			final int beginIndexUrl = beginIndexAdressLocationTag + adressLocationTag.length();
			final int endIndexUrl = wsdl.indexOf("\"", beginIndexUrl);

            return wsdl.substring(0, beginIndexUrl) +
                    correctAddressLocation +
                    wsdl.substring(endIndexUrl);
		} else {
			return wsdl;
		}
	}

}
