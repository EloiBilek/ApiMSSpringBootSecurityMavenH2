/**
 * 
 */
package com.apimsspringbootsecuritymavenh2.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author eloi
 *
 */
@Entity
@Table(name = "account_credentials")
public class AccountCredentials implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1108524756536151993L;

	@Id
	@GeneratedValue
	private Long id;
	private String userName;
	private String passWord;

	public AccountCredentials() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("AccountCredentials: [userName=").append(userName).append("]");
		return builder.toString();
	}

}
