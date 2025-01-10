package sitha.rupp.service;

public class UserListModel {
	private int userId;
	private String userName;
	private String user_Name;
	private String userRole;
	private String userBranch;
	private String userBranchName;
	private String userDisable;
	
	
	public String getUser_Name() {
		return user_Name;
	}
	public void setUser_Name(String user_Name) {
		this.user_Name = user_Name;
	}
	public String getUserDisable() {
		return userDisable;
	}
	public void setUserDisable(String userDisable) {
		this.userDisable = userDisable;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getUserBranch() {
		return userBranch;
	}
	public void setUserBranch(String userBranch) {
		this.userBranch = userBranch;
	}
	public String getUserBranchName() {
		return userBranchName;
	}
	public void setUserBranchName(String userBranchName) {
		this.userBranchName = userBranchName;
	}
	
}
