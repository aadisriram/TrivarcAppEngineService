package com.footballfrenzy.quizapp.dataobjects;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * This is the User object. It Holds all information related to a particular user.
	PersistanceCapable basically says that the class is to stored in the datastore
	Every class member that has a @Persistent annotation basically denotes if that class item
	would be persisted or not.
 *
 */

//TODO : more members to hold more info about user as and when requirement is seen in future (example : total time taken etc, no of correct, no of wrong answered..)

@PersistenceCapable
public class User {
	
//	emailId can be primary key.... no need for a separate ID
//	@PrimaryKey
//	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
//	private Long userId;
	
	@PrimaryKey
	@Persistent
	private String eMailId;
	
	@Persistent
	private String Name;
	
	@Persistent
	private List<Long> answeredQuestionIds=new ArrayList<Long>();

	public User(String eMailId,
			String Name) {
		super();
		this.seteMailId(eMailId);
		this.setName(Name);
		
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String geteMailId() {
		return eMailId;
	}

	public void seteMailId(String eMailId) {
		this.eMailId = eMailId;
	}

	
	public List<Long> getAnsweredQuestionIds() {
		return answeredQuestionIds;
	}

	
	public boolean addtoQuestionsAnswered(Long questionId) {
		if(answeredQuestionIds.contains(questionId))
		{
			System.out.println("This question is already in the answered List, Check Question ID passed again!");
			return false;
		}
		else
		{
			answeredQuestionIds.add(questionId);
			return true;
		}
	}
	
}
