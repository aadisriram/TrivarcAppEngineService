package com.footballfrenzy.quizapp.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.footballfrenzy.quizapp.dataobjects.Poll;
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
		cal.add(GregorianCalendar.MINUTE, -59);

		limitLower = cal.getTime();

		Query query = pm.newQuery(Question.class,
				"questionDate >= :dateLower && questionDate <= :dateUpper");
		List<Question> question = (List<Question>) query.execute(limitLower,
				date);

		if (question.isEmpty()) {
			pm.close();
			return null;
		} else {
			limitLower = (Date) date.clone();
			cal.setTime(limitLower);
			cal.add(GregorianCalendar.MINUTE, -119);
			limitLower = cal.getTime();

			Date limitUpper = (Date) date.clone();
			cal.setTime(limitUpper);
			cal.add(GregorianCalendar.MINUTE, -59);
			limitUpper = cal.getTime();
			query = pm.newQuery(Question.class,
					"questionDate >= :dateLower && questionDate <= :dateUpper");

			Question quest = question.get(0);
			question = (List<Question>) query.execute(limitLower, limitUpper);
			if (!question.isEmpty()) {
				Question lastQuestion = question.get(0);
				quest.setLastAnswer(lastQuestion.getAnswer());
				quest.setLastQuestion(lastQuestion.getQuestion());
			}
			pm.close();
			return quest;
		}
	}

	@Override
	public Question getQuestion(Long qId) {
		try {
			pm = PMF.get().getPersistenceManager();
			Query query = pm
					.newQuery(Question.class, ":p.contains(questionId)");
			List<Question> result = (List<Question>) query.execute(qId);
			if (result.size() > 0)
				return result.get(0);
			else
				return null;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			return null;
		} finally {
			pm.close();
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
			User newuser = pm.makePersistent(user);
			System.out.println(newuser.getUserId());
			pm.close();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			return false;
		}

		return true;
	}

	@Override
	public boolean modifyUserData(String userId, String Name) {

		try {
			pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(User.class, ":p.contains(userId)");
			List<User> result = (List<User>) query.execute(userId);
			result.get(0).setName(Name);
			pm.close();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			return false;
		} finally {
			pm.close();
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
		} finally {
			pm.close();
		}

		return false;
	}

	@Override
	public User getUserData(String userId) {
		List<User> result;
		try {
			pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(User.class, ":p.contains(userId)");
			result = (List<User>) query.execute(userId);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			return null;
		} finally {
			pm.close();
		}
		if (result.size() > 0)
			return result.get(0);
		else
			return null;
	}

	@Override
	public boolean isQuestionAlreadyAnswered(String userId, Long qId) {
		try {
			pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(User.class, ":p.contains(userId)");
			List<User> result = (List<User>) query.execute(userId);
			if (result.size() > 0) {
				List<Long> userActivity = result.get(0).getUserActivity();
				pm.close();
				if (userActivity != null) {
					for (int i = 0; i < userActivity.size(); i++) {
						if (userActivity.get(i) != null) {
							Long activityId = userActivity.get(i);
							Long DBqId = getQuestionIdFromActivityId(activityId);
							if (DBqId.equals(qId)) {
								return true;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			return false;
		} finally {
			pm.close();
		}
		return false;
	}

	@Override
	public boolean doesUserExist(String userId) {

		List<User> result = null;
		try {
			pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(User.class, ":p.contains(userId)");
			result = (List<User>) query.execute(userId);
			pm.close();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		if (result == null || result.size() == 0) {
			// user doesn't exist in DB, Hence we will add him now
			User newUser = new User(userId, "Unknown Football freak");
			addUser(newUser);
			return false;
		} else
			// result is not null hence user already exists
			return true;
	}

	@Override
	public boolean addUserActivity(String userId, Long qId, String answer,
			Long time) {
		List<User> result = null;
		boolean hasAttempted = false;
		try {
			pm = PMF.get().getPersistenceManager();

			Query query = pm.newQuery(User.class, ":p.contains(userId)");
			result = (List<User>) query.execute(userId);
			List<Long> userActivity = result.get(0).getUserActivity();
			pm.close();
			if (userActivity != null) {
				for (int i = 0; i < userActivity.size(); i++) {
					Long activityId = userActivity.get(i);
					Long DBqId = getQuestionIdFromActivityId(activityId);
					if (DBqId.equals(qId)) {
						hasAttempted = true;
						break;

					}
				}

			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}

		if (!hasAttempted) {
			long aid = addNewActivity(qId, answer, time);
			pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(User.class, ":p.contains(userId)");
			result = (List<User>) query.execute(userId);
			if (result != null) {
				boolean isSuccess = result.get(0).addUserActivity(aid);
				pm.close();
				return isSuccess;
			}
			return false;
		} else {
			return false;
		}
	}

	public Long getQuestionIdFromActivityId(Long aId) {
		pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(QuestionAttempt.class,
				":p.contains(activityId)");
		List<QuestionAttempt> attempt = (List<QuestionAttempt>) query
				.execute(aId);
		pm.close();
		return attempt.get(0).getQuestionId();

	}

	public Long addNewActivity(Long qId, String answer, Long time) {
		QuestionAttempt savedAttempt = null;
		try {
			QuestionAttempt attempt = new QuestionAttempt(qId, answer, time);
			pm = PMF.get().getPersistenceManager();
			savedAttempt = pm.makePersistent(attempt);
			pm.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (savedAttempt != null)
			return savedAttempt.getActivityId();
		else
			return (long) -1;

	}

	@Override
	public boolean addPollItem(Poll poll) {
		try {
			pm = PMF.get().getPersistenceManager();
			pm.makePersistent(poll);
			pm.close();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean addPUserPollActivity(String userId, String category,
			String name) {
		boolean isSuccess=false;
		try {
			pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(Poll.class, ":p.contains(category)");
			List<Poll> result = (List<Poll>) query.execute(category);
			if (result != null)
				if (result.size() > 0) {
					for (int i = 0; i < result.size(); i++) {
						if (result.get(i).getPollName().equals(name)) {
							result.get(i).addcount();
							pm.close();
							break;
						}

					}
				}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			return false;
		}
		
		//Now adding user attempted status to User DB
		try {
			pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(User.class, ":p.contains(userId)");
			List<User>result = (List<User>) query.execute(userId);
			if(result!=null)
				if(result.size()>0)
				{
					isSuccess=result.get(0).addUserPollActivity(category);
				}
			pm.close();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			return false;
		}
		
		
		return isSuccess;
	}

	@Override
	public boolean hasUserAttemptedPoll(String userId, String category) {
		boolean hasAttempted=false;
		try {
			pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(User.class, ":p.contains(userId)");
			List<User>result = (List<User>) query.execute(userId);
			if(result!=null)
				if(result.size()>0)
				{
					List<String> poll=result.get(0).getUserPollActivity();
					if(poll!=null)
					{
						if(poll.contains(category))
							hasAttempted=true;
					}
				}
			pm.close();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			return false;
		}
	
		return hasAttempted;
	}
}
