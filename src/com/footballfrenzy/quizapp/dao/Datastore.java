package com.footballfrenzy.quizapp.dao;

import java.util.Date;

import com.footballfrenzy.quizapp.dataobjects.Question;
import com.footballfrenzy.quizapp.dataobjects.User;

/*
    This is the interface that exposes the methods that the datastore provides
	Any class that wants to access the data store must implement this.
*/
public interface Datastore {
	
	public boolean addQuestion(Question question);
	
	public Question getQuestion(Date date);
	
	public boolean modifyQuestion(Long qId, Question question);
	
	public boolean deleteQuestion(Long qId);
	
	public boolean addUser(User user);
	
	public boolean modifyUserData(String eMailId, String Name);
	
	public boolean deleteUser(String emailId);
	
	public User getUserData(String emailId);
	
	public boolean addtoQuestionsAnswered(String emailId, Long qId);
	
	public boolean isQuestionAlreadyAnswered(String emailId, Long qId);
	
	
	
}
