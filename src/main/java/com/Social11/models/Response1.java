package com.Social11.models;

public class Response1 {

	String error;
    Boolean is_success;
    String message;
	Boolean is_exists;
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Boolean getIs_success() {
		return is_success;
	}
	public void setIs_success(Boolean is_success) {
		this.is_success = is_success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean getIs_exists() {
		return is_exists;
	}
	public void setIs_exists(Boolean is_exists) {
		this.is_exists = is_exists;
	}
	@Override
	public String toString() {
		return "Response1 [error=" + error + ", is_success=" + is_success + ", message=" + message + ", is_exists="
				+ is_exists + "]";
	}
	public Response1() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
