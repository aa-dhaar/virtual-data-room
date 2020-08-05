package com.aa.virtualroom.response;


public final class JobDetailsResponse {

    private final String jobId;
    private final String functionId;
    private final String aaId;
    private final String state;
    private final long created;
    private final long lastUpdated;
    private final String result;
    private final String functionName;

    public JobDetailsResponse(String jobId, String functionId, String aaId, String state, long created,
    		long lastUpdated, String result,String functionName) {
        super();
        this.jobId = jobId;
        this.functionId = functionId;
        this.aaId = aaId;
        this.state = state;
        this.created = created;
        this.lastUpdated = lastUpdated;
        this.result = result;
        this.functionName = functionName;
    }

    public String getJobId() {
        return jobId;
    }

    public String getFunctionId() {
        return functionId;
    }

    public String getAaId() {
        return aaId;
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

    public String getResult() {
        return result;
    }

	public String getFunctionName() {
		return functionName;
	}


}
