package com.aa.virtualroom.response;

import java.util.Date;

public final class FunctionDetailsResponse {

	private final String functionId;
	private final String jsonSchema;
	private final String state;
	private final String created;
	private final String lastUpdated;
	private final String functionName;
	private final String handler;
	private final String runtime;
	public FunctionDetailsResponse(String functionId, String jsonSchema, String state, String created, String lastUpdated,
			 String functionName, String handler, String runtime) {
		super();
		this.functionId = functionId;
		this.jsonSchema = jsonSchema;
		this.state = state;
		this.created = created;
		this.lastUpdated = lastUpdated;
		this.functionName = functionName;
		this.handler = handler;
		this.runtime = runtime;
	}
	public String getFunctionId() {
		return functionId;
	}
	public String getJsonSchema() {
		return jsonSchema;
	}
	public String getState() {
		return state;
	}
	public String getCreated() {
		return created;
	}
	public String getLastUpdated() {
		return lastUpdated;
	}
	
	public String getFunctionName() {
		return functionName;
	}
	public String getHandler() {
		return handler;
	}
	public String getRuntime() {
		return runtime;
	}
	
	
}
