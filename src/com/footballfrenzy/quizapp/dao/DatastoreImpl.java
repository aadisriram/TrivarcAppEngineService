package com.footballfrenzy.quizapp.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.footballfrenzy.quizapp.dataobjects.Question;

public class DatastoreImpl implements Datastore{

	private static PersistenceManager pm;

	/*
		Call this API to create a new question
		It accepts a question object as a parameter.
	*/
	@Override
	public boolean addQuestion(Question question) {
		pm = PMF.get().getPersistenceManager();
		pm.makePersistent(question);
		pm.close();
		return true;
	}

	/*
		Call this API to get a question, you pass a date and it
		will return a Question that is withing 15 minutes of the date.
	*/
	@Override
	public Question getQuestion(Date date) {
		pm = PMF.get().getPersistenceManager();
		Date limitLower = (Date) date.clone();
		
		Calendar cal = GregorianCalendar.getInstance();
	    cal.setTime(limitLower);
	    cal.add( GregorianCalendar.MINUTE, -15);
	    
	    limitLower = cal.getTime();
	    
	    Query query = pm.newQuery(Question.class, " questionDate >= :1 ");
	    Question question = (Question) query.execute(limitLower);
	    
		return question;
	}

	/*
		Used to update an existing question.
	*/
	@Override
	public boolean modifyQuestion(Long qId, Question question) {
		pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Question.class, " questionId == :1 ");
		Question result = (Question) query.execute(qId);
		
		result.setAnswer(question.getAnswer());
		result.setOptions(question.getOptions());
		result.setQuestion(question.getQuestion());
		result.setQuestionDate(question.getQuestionDate());
		
		pm.close();
		return true;
	}

	@Override
	public boolean deleteQuestion(Long qId) {
		// TODO Auto-generated method stub
		return false;
	}
}
