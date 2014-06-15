package com.footballfrenzy.quizapp.dao;

import java.util.Date;

import com.footballfrenzy.quizapp.dataobjects.Poll;
import com.footballfrenzy.quizapp.dataobjects.Question;
import com.footballfrenzy.quizapp.dataobjects.User;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

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

	public JSONObject doesUserExist(String userId, String name, String pollName) throws JSONException;

	public boolean modifyUserData(String userId, String Name);

	public boolean deleteUser(String userId);

	public User getUserData(String userId);

	public boolean addUserActivity(String userId,Long qId,String answer,Long time);

	public boolean isQuestionAlreadyAnswered(String userId, Long qId);
	
	public boolean addPollItem(Poll poll);
	
	public JSONObject addPUserPollActivity(String userId, String name, String option) throws JSONException;
	
	public boolean hasUserAttemptedPoll(String userId, String category);
	
	public JSONObject getPollStatus(String pollName) throws JSONException;

}
