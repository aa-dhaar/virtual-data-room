package com.aa.virtualroom.response;

import java.util.List;

public final class ErrorResponse {

    private final String error;
    private final List<String> details;
    
    
    

    public ErrorResponse(String error, List<String> details) {
		super();
		this.error = error;
		this.details = details;
	}

    public ErrorResponse(String error) {
    	super();
    	this.details = null;
		this.error = error;
    }


	public List<String> getDetails() {
		return details;
	}




	public String getError() {
        return error;
    }
}
