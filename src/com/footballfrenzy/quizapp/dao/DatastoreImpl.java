package com.footballfrenzy.quizapp.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.footballfrenzy.quizapp.dataobjects.Question;
import com.footballfrenzy.quizapp.dataobjects.User;

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
	    
	    Query query = pm.newQuery(Question.class, "questionDate >= :dateLower && questionDate <= :dateUpper");
	    List<Question> question = (List<Question>) query.execute(limitLower, date);
	    
	    if(question.isEmpty())
	    	return null;
	    else
	    	return question.get(0);
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
		return false;
	}

	@Override
	public boolean addUser(User user) {
		try {
			pm = PMF.get().getPersistenceManager();
			pm.makePersistent(user);
			pm.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}

	@Override
	public boolean modifyUserData(String userEmailId, String Name) {

		String filter="eMailId=="+"'"+userEmailId+"'";
		try {
			pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(User.class);
			query.setFilter(filter);
			User result = (User) query.execute();
			result.setName(Name);
			pm.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}

	@Override
	public boolean deleteUser(String userEmailId) {
		String filter="eMailId=="+"'"+userEmailId+"'";
		try {
			pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(User.class);
			query.setFilter(filter);
			query.deletePersistentAll();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return false;
	}

	@Override
	public User getUserData(String userEmailId) {
		String filter="eMailId=="+"'"+userEmailId+"'";
		User result;
		try {
			pm = PMF.get().getPersistenceManager();	    
		    Query query = pm.newQuery(User.class);
		    query.setFilter(filter);
		    result = (User) query.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
		return result;
	}

	@Override
	public boolean addtoQuestionsAnswered(String userEmailId, Long qId) {
		String filter="eMailId=="+"'"+userEmailId+"'";
		boolean isSuccess=false;
		try {
			pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(User.class);
			query.setFilter(filter);
			User result = (User) query.execute();
			isSuccess=result.addtoQuestionsAnswered(qId);
			pm.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
			
		return isSuccess;
	}

	@Override
	public boolean isQuestionAlreadyAnswered(String userEmailId, Long qId) {
		String filter="eMailId=="+"'"+userEmailId+"'";
		try {
			pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(User.class);
			query.setFilter(filter);
			User result = (User) query.execute();
			List<Long> answeredQuestionIds=result.getAnsweredQuestionIds();
			if(answeredQuestionIds.contains(qId))
			{
				return true;
			}
			else
			{
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

	}
}
