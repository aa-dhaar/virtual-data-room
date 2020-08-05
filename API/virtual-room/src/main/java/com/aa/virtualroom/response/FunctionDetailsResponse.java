package com.aa.virtualroom.response;

public final class FunctionDetailsResponse {

    private final String functionId;
    private final String jsonSchema;
    private final String state;
    private final long created;
    private final long lastUpdated;
    private final String functionName;
    private final String functionDescription;
    private final String handler;
    private final String runtime;

    public FunctionDetailsResponse(String functionId, String jsonSchema, String state, long created,
                                   long lastUpdated,
                                   String functionName, String functionDescription, String handler,
                                   String runtime) {
        super();
        this.functionId = functionId;
        this.jsonSchema = jsonSchema;
        this.state = state;
        this.created = created;
        this.lastUpdated = lastUpdated;
        this.functionName = functionName;
        this.functionDescription = functionDescription;
        this.handler = handler;
        this.runtime = runtime;
    }

    public String getFunctionDescription() {
        return functionDescription;
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

    public long getCreated() {
        return created;
    }

    public long getLastUpdated() {
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
