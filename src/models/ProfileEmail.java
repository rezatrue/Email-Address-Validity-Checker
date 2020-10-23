package models;

public class ProfileEmail {

	String userId;
	String emailAddress;  
	String profileLink; 
	String emailStatus;

	public ProfileEmail() {
	}

	public ProfileEmail(String userId, String emailAddress, String profileLink, String emailStatus) {
		super();
		this.userId = userId;
		this.emailAddress = emailAddress;
		this.profileLink = profileLink;
		this.emailStatus = emailStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getProfileLink() {
		return profileLink;
	}

	public void setProfileLink(String profileLink) {
		this.profileLink = profileLink;
	}

	public String getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	} 
	
	
	
	
}
