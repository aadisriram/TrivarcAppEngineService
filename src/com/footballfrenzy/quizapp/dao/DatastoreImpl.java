package com.footballfrenzy.quizapp.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.footballfrenzy.quizapp.dataobjects.Question;

public class DatastoreImpl implements Datastore{

	private static PersistenceManager pm;

	@Override
	public boolean addQuestion(Question question) {
		pm = PMF.get().getPersistenceManager();
		pm.makePersistent(question);
		pm.close();
		return true;
	}

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
