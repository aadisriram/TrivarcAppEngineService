package com.footballfrenzy.quizapp.dataobjects;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * This is the User object. It Holds all information related to a particular
 * user. PersistanceCapable basically says that the class is to stored in the
 * datastore Every class member that has a @Persistent annotation basically
 * denotes if that class item would be persisted or not.
 * 
 */

// TODO : more members to hold more info about user as and when requirement is
// seen in future (example : total time taken etc, no of correct, no of wrong
// answered..)

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
	private List<QuestionAttempt> quizzingActivityList = new ArrayList<QuestionAttempt>();

	public User(String userId, String Name) {
		super();
		this.setUserId(userId);
		this.setName(Name);

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
	
	public List<QuestionAttempt> getUserActivity()
	{
		return quizzingActivityList;
	}

	public boolean addUserActivity(QuestionAttempt attempt) {
		if (quizzingActivityList != null) {
			quizzingActivityList.add(0,attempt);
			return true;
		}
		return false;
	}

}
