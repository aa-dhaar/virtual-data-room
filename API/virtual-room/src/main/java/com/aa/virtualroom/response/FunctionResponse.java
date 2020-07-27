package com.aa.virtualroom.response;

import java.util.Map;

public final class FunctionResponse {
	
	private final String fiuId;
	private final Map<String, FunctionDetailsResponse> functions;
	public FunctionResponse(String fiuId, Map<String, FunctionDetailsResponse> functions) {
		super();
		this.fiuId = fiuId;
		this.functions = functions;
	}
	public String getFiuId() {
		return fiuId;
	}
	public Map<String, FunctionDetailsResponse> getFunctions() {
		return functions;
	}
	
	
	
	
    

    
}
