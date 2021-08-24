package com.hcl.ecommerce.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {
	
	private Map<String,String> errorMaps = new HashMap<String,String>();

	public Map<String, String> getErrorMaps() {
		return errorMaps;
	}

	public void setErrorMaps(Map<String, String> errorMaps) {
		this.errorMaps = errorMaps;
	}
	

}
