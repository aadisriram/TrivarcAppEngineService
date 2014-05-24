package com.footballfrenzy.quizapp.dataobjects;


public class QuestionAttempt {

	private Long questionId;
	
	private String answer;
	
	private Long timeTaken;
	
	public QuestionAttempt(Long questionId, String answer, Long timeTaken) {
		this.questionId=questionId;
		this.answer=answer;
		this.timeTaken=timeTaken;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
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
	
}
