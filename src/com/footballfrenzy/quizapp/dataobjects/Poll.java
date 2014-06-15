package com.footballfrenzy.quizapp.dataobjects;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;



@PersistenceCapable
public class Poll {
	
	@PrimaryKey
	@Persistent
	private String pollName;
	
	@Persistent
	private List<Integer> voteCount;
	
	@Persistent
	private List<String> options;
	
	public Poll(String pollName, List<String> options ){
		this.pollName=pollName;
		this.options=options;
	}
	
	public void setPollName(String name)
	{
		pollName=name;
	}
	
	public String getPollName()
	{
		return pollName;
	}
	
	public void setOptions(List<String> options)
	{
		this.options=options;
	}
	
	public List<String> getOptions()
	{
		return options;
	}
	
	public void addCount(String option)
	{
		int index=0;
		if(options!=null)
			index=options.indexOf(option);
		voteCount.set(index, voteCount.get(index)+1);
	}
	
	public void setVoteCount(List<Integer> count)
	{	
		voteCount=count;
	}
	
	public List<Integer> getVoteCount()
	{
		return voteCount;
	}
	

}
