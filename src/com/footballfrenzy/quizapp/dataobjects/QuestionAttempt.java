package com.footballfrenzy.quizapp.dataobjects;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class QuestionAttempt {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long activityId;
	
	@Persistent
	private Long qId;
	
	@Persistent
	private String answer;
	
	@Persistent
	private Long timeTaken;
	
	public QuestionAttempt(Long qId, String answer, Long timeTaken) {
		this.qId=qId;
		this.answer=answer;
		this.timeTaken=timeTaken;
	}

	public Long getQuestionId() {
		return qId;
	}

	public void setQuestionId(Long qId) {
		this.qId = qId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Long getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(Long timeTaken) {
		this.timeTaken = timeTaken;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	
}
