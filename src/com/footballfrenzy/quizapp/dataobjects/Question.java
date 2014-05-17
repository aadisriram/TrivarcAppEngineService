package com.footballfrenzy.quizapp.dataobjects;

import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


/*
    This is the question object, the same question object has to be available to the
	Front end as well, so that they can encode to json and send the data through HTTP requests.
	
	PersistanceCapable basically says that the class is to stored in the datastore
	Every class member that has a @Persistent annotation basically dentoes if that class item
	would be persisted or not.
*/
@PersistenceCapable
public class Question {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long questionId;
	
	@Persistent
	private String question;
	
	@Persistent
	private List<String> options;
	
	@Persistent
	private Date questionDate;
	
	@Persistent
	private String answer;

	public Question(String question, List<String> options,
			String answer, Date questionDate) {
		super();
		this.question = question;
		this.options = options;
		this.questionDate = questionDate;
		this.answer = answer;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public Date getQuestionDate() {
		return questionDate;
	}

	public void setQuestionDate(Date questionDate) {
		this.questionDate = questionDate;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
