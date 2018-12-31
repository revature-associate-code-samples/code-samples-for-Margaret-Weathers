package com.revature.bankingapp.pojos;



public class User {
	
	private int usrId;
	private String username;
	private String pw;
	private String first;
	private String last;
	
	public User() {}
	
	public User(String username, String pw, String first, String last) {
		super();
		this.username = username;
		this.pw = pw;
		this.first = first;
		this.last = last;
	}

	// For future implementation...
	public boolean changePw(String p) {
		if(p.equals(pw)) {
			System.out.println("You provided the same password");
			return false;
		}
		pw = p;
		return true;
	}
	
	// Getters and Setters

	public int getUsrId() {
		return usrId;
	}

	public void setUsrId(int usrId) {
		this.usrId = usrId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}
	
	
	@Override
	public String toString() {
		return "{ID: " + usrId + ", Username: " + username + ", Password: " + pw
				+ ", First: " + first + ", Last: " + last + "}\n";
	}
	
	
	
	
	
	
	
	
	
	
	
// Would remove an Account if they were stored in an array list...	
//	public boolean removeAccount(Account a) {
//		// Validate that account exists? Probably do that elsewhere...
//		// Validate that the user still has accounts? 
//		
//		Iterator<Account> itr = accounts.iterator();
//		while(itr.hasNext()) {
//			if (itr.next().equals(a)) {
//				itr.remove();
//				return true;
//			}
//		}
//		
//		return false;
//	}
	
	

}
