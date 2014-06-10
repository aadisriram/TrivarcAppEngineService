package com.footballfrenzy.quizapp.dataobjects;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable
public class Poll {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long pollId;
	
	@Persistent
	private String pollName;
	
	@Persistent
	private int count;
	
	@Persistent
	private String category;
	
	public Poll(String pollName, int count, String category ){
		this.pollName=pollName;
		this.count=count;
		this.category=category;
	}
	
	public void setPollName(String name)
	{
		pollName=name;
	}
	
	public String getPollName()
	{
		return pollName;
	}
	
	public void setCategory(String category)
	{
		this.category=category;
	}
	
	public String getCategory()
	{
		return category;
	}
	
	public int addcount()
	{
		return count++;
	}
	
	public int getCount()
	{
		return count;
	}
	

}
