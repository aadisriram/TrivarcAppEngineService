package com.footballfrenzy.quizapp.dao;

import java.util.Date;

import com.footballfrenzy.quizapp.dataobjects.Question;

public interface Datastore {
	
	public boolean addQuestion(Question question);
	
	public Question getQuestion(Date date);
	
	public boolean modifyQuestion(Long qId, Question question);
	
	public boolean deleteQuestion(Long qId);
	
}
