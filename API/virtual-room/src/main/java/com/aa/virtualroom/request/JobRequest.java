package com.aa.virtualroom.request;

public final class JobRequest {

    private final String fiuId;
    private final String functionId;
    private final String aaId;
    private final String requestParams;

    public JobRequest(String fiuId, String functionId, String aaId, String requestParams) {
        this.fiuId = fiuId;
        this.functionId = functionId;
        this.aaId = aaId;
        this.requestParams = requestParams;
    }

    public String getFiuId() {
        return fiuId;
    }

    public String getFunctionId() {
        return functionId;
    }

    public String getAaId() {
        return aaId;
    }

    public String getRequestParams() {
        return requestParams;
    }
}
