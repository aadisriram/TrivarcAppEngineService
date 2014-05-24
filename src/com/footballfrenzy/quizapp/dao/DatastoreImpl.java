package com.footballfrenzy.quizapp.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.footballfrenzy.quizapp.dataobjects.Question;
import com.footballfrenzy.quizapp.dataobjects.QuestionAttempt;
import com.footballfrenzy.quizapp.dataobjects.User;

/*
 * This is the data store class which interacts with the 
 * persistence manager. The persistence manager does not
 * depend on the data objects or the schema.
 */
public class DatastoreImpl implements Datastore {

	private static PersistenceManager pm;
	private static Logger LOGGER = Logger.getLogger(DatastoreImpl.class
			.getName());

	/*
	 * Call this API to create a new question It accepts a question object as a
	 * parameter.
	 */
	@Override
	public boolean addQuestion(Question question) {
		pm = PMF.get().getPersistenceManager();
		pm.makePersistent(question);
		pm.close();
		return true;
	}

	/*
	 * Call this API to get a question, you pass a date and it will return a
	 * Question that is withing 15 minutes of the date.
	 */
	@Override
	public Question getQuestion(Date date) {
		pm = PMF.get().getPersistenceManager();
		Date limitLower = (Date) date.clone();

		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(limitLower);
		cal.add(GregorianCalendar.MINUTE, -15);

		limitLower = cal.getTime();

		Query query = pm.newQuery(Question.class,
				"questionDate >= :dateLower && questionDate <= :dateUpper");
		List<Question> question = (List<Question>) query.execute(limitLower,
				date);

		if (question.isEmpty())
			return null;
		else
			return question.get(0);
	}

	@Override
	public Question getQuestion(Long qId) {
		try {
			pm = PMF.get().getPersistenceManager();
			String filter = "questionId==" + qId;
			Query query = pm.newQuery(Question.class);
			query.setFilter(filter);
			Question question = (Question) query.execute();
			return question;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			return null;
		}

	}

	/*
	 * Used to update an existing question.
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
			LOGGER.log(Level.SEVERE, e.getMessage());
			return false;
		}

		return true;
	}

	@Override
	public boolean modifyUserData(String userId, String Name) {

		String filter = "userId==" + "'" + userId + "'";
		try {
			pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(User.class);
			query.setFilter(filter);
			User result = (User) query.execute();
			result.setName(Name);
			pm.close();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			return false;
		}

		return true;
	}

	@Override
	public boolean deleteUser(String userId) {
		String filter = "userId==" + "'" + userId + "'";
		try {
			pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(User.class);
			query.setFilter(filter);
			query.deletePersistentAll();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			return false;
		}

		return false;
	}

	@Override
	public User getUserData(String userId) {
		String filter = "userId==" + "'" + userId + "'";
		User result;
		try {
			pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(User.class);
			query.setFilter(filter);
			result = (User) query.execute();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			return null;
		}

		return result;
	}


	@Override
	public boolean isQuestionAlreadyAnswered(String userId, Long qId) {
		String filter = "userId==" + "'" + userId + "'";
		try {
			pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(User.class);
			query.setFilter(filter);
			User result = (User) query.execute();
			List<QuestionAttempt> userActivity=result.getUserActivity();
			if(userActivity!=null)
			{
				for(int i=0;i<userActivity.size();i++)
				{
					if (userActivity.get(i) != null	) {
						QuestionAttempt attempt=userActivity.get(i);
						if(attempt.getQuestionId()==qId){
							return true;							
						}			
					}
				}				
			}		
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			return false;
		}
		return false;
	}

	@Override
	public boolean doesUserExist(String userId) {
		String filter = "userId==" + "'" + userId + "'";
		User result = null;
		try {
			pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(User.class);
			query.setFilter(filter);
			result = (User) query.execute();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		if (result == null) {
			// user doesn't exist in DB, Hence we will add him now
			User newUser = new User(userId, "Unknown Football freak"); // TODO :need to get name also so that i can add to DB																	
			addUser(newUser);
			return false;
		} else 
			// result is not null hence user already exists
			return true;
	}
	
	
	@Override
	public boolean addUserActivity(String userId, QuestionAttempt attempt) {
		String filter = "userId==" + "'" + userId + "'";
		User result=null;
		boolean hasAttempted=false;
		try {
			pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(User.class);
			query.setFilter(filter);
			result = (User) query.execute();
			List<QuestionAttempt> userActivity=result.getUserActivity();
			if(userActivity!=null)
			{
				for(int i=0;i<userActivity.size();i++)
				{
					if (userActivity.get(i) != null	) {
						QuestionAttempt DBAttempt=userActivity.get(i);
						if(DBAttempt.getQuestionId()==attempt.getQuestionId()){
							hasAttempted=true;
							break;
						}			
					}
				}
				
			}			
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		
		if(!hasAttempted){
			if(result!=null){
				return result.addUserActivity(attempt);	
			}
			return false;
		}
		else{
			return false;
		}
	}
}
