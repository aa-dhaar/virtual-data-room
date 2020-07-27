package com.aa.virtualroom.response;

import java.util.Date;

public final class JobDetailsResponse {

	private final String jobId;
	private final String functionId;
	private final String aaId;
	private final String state;
	private final Date created;
	private final Date lastUpdated;
	public JobDetailsResponse(String jobId, String functionId, String aaId, String state, Date created,
			Date lastUpdated) {
		super();
		this.jobId = jobId;
		this.functionId = functionId;
		this.aaId = aaId;
		this.state = state;
		this.created = created;
		this.lastUpdated = lastUpdated;
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
	public Date getCreated() {
		return created;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	
	
	
}
