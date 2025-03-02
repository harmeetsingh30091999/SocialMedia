package com.Social11.models;

public class Response {

	String error;
    Boolean is_success;
    String message;
    Integer totalpost;

    public Boolean getIs_success() {
        return is_success;
    }

    public void setIs_success(Boolean is_success) {
        this.is_success = is_success;
    }


    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }

	public Response() {
		System.out.println("Reponse");
	}

	public Integer getTotalpost() {
		return totalpost;
	}

	public void setTotalpost(Integer totalpost) {
		this.totalpost = totalpost;
	}
    
    
    
    
	
}
