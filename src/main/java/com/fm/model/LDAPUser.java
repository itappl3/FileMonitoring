package com.fm.model;

public class LDAPUser {
	
	
	private String username;
    private String displayName;
    private String email;
    private String department;
    
    public LDAPUser() {
    }

    public LDAPUser(String username, String displayName, String email, String department) {
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.department = department;
    }
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}

    
    
    

}
