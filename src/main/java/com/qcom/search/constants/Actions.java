package com.qcom.search.constants;

public enum Actions {
	
	QUERY("query"),
	BOOKMARK("bookmark"),
	LIKE("like");
	
	
	private Actions(String action) {
		this.action = action;
	}

	String action;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
