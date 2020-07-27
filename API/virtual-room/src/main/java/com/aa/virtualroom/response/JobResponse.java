package com.aa.virtualroom.response;

import java.util.Map;

public final class JobResponse {

	private final String fiuId;
	private final Map<String, JobDetailsResponse> jobs;
	public JobResponse(String fiuId, Map<String, JobDetailsResponse> jobs) {
		super();
		this.fiuId = fiuId;
		this.jobs = jobs;
	}
	
	public String getFiuId() {
		return fiuId;
	}

	public Map<String, JobDetailsResponse> getJobs() {
		return jobs;
	}
	
	
}
