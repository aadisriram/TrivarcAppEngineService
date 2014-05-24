package com.footballfrenzy.quizapp.dao;

import java.util.Date;

import com.footballfrenzy.quizapp.dataobjects.Question;
import com.footballfrenzy.quizapp.dataobjects.QuestionAttempt;
import com.footballfrenzy.quizapp.dataobjects.User;

/*
    This is the interface that exposes the methods that the datastore provides
	Any class that wants to access the data store must implement this.
*/
public interface Datastore {
	
	public boolean addQuestion(Question question);
	
	public Question getQuestion(Date date);
	
	public Question getQuestion(Long qId);
	
	public boolean modifyQuestion(Long qId, Question question);
	
	public boolean deleteQuestion(Long qId);
	
	public boolean addUser(User user);
	
	public boolean doesUserExist(String userId);
	
	public boolean modifyUserData(String userId, String Name);
	
	public boolean deleteUser(String userId);
	
	public User getUserData(String userId);
	
	public boolean addUserActivity(String userId, QuestionAttempt attempt);
	
	public boolean isQuestionAlreadyAnswered(String userId, Long qId);
	
	
	
}
