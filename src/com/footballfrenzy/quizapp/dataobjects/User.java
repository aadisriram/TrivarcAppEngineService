package com.footballfrenzy.quizapp.dataobjects;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Entity;

import com.google.api.server.spi.config.DefaultValue;

/**
 * This is the User object. It Holds all information related to a particular
 * user. PersistanceCapable basically says that the class is to stored in the
 * datastore Every class member that has a @Persistent annotation basically
 * denotes if that class item would be persisted or not.
 * 
 */

@PersistenceCapable
public class User {

	// emailId can be primary key.... no need for a separate ID
	// @PrimaryKey
	// @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	// private Long userId;

	@PrimaryKey
	@Persistent
	private String userId;

	@Persistent
	private String name;

	@Persistent
	private List<Long> quizzingActivityList;

	@Persistent
	private List<String> pollActivityList;

	public User(String userId, String Name) {
		super();
		this.setUserId(userId);
		this.setName(Name);
		quizzingActivityList = new ArrayList<Long>();
		pollActivityList = new ArrayList<String>();

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Long> getUserActivity() {
		return quizzingActivityList;
	}

	public boolean addUserActivity(Long attempt) {
		if (quizzingActivityList != null) {
			quizzingActivityList.add(0, attempt);
			return true;
		}
		return false;
	}

	public boolean addUserPollActivity(String name){
		if(pollActivityList!=null && !pollActivityList.contains(name)){
			pollActivityList.add(0,name);
			return true;
		}
		return false;
	}
	
	public List<String> getUserPollActivity() {
		return pollActivityList;
	}
}
