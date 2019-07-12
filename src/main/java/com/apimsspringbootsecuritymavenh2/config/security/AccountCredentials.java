/**
 * 
 */
package com.apimsspringbootsecuritymavenh2.config.security;

import java.io.Serializable;

/**
 * @author eloi
 *
 */
public class AccountCredentials implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2786100615512953370L;

	private String email;
	private String password;
	private String newPassword;

	public AccountCredentials() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("AccountCredentials: [email=").append(email).append("]");
		return builder.toString();
	}

}
