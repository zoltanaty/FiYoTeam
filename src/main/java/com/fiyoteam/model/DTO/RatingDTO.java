package com.fiyoteam.model.DTO;

import java.io.Serializable;

public class RatingDTO implements Serializable{

	private static final long serialVersionUID = 4296965428448386506L;
	
	private Integer voted;
	private Integer voter;
	private Double avgRating;
	private Integer percentage5Star;
	private Integer percentage4Star;
	private Integer percentage3Star;
	private Integer percentage2Star;
	private Integer percentage1Star;
	
	public RatingDTO(){
		
	}

	public RatingDTO(Integer voted, Integer voter, Double avgRating, Integer percentage5Star, Integer percentage4Star,
			Integer percentage3Star, Integer percentage2Star, Integer percentage1Star) {
		super();
		this.voted = voted;
		this.voter = voter;
		this.avgRating = avgRating;
		this.percentage5Star = percentage5Star;
		this.percentage4Star = percentage4Star;
		this.percentage3Star = percentage3Star;
		this.percentage2Star = percentage2Star;
		this.percentage1Star = percentage1Star;
	}

	public Integer getVoted() {
		return voted;
	}

	public void setVoted(Integer voted) {
		this.voted = voted;
	}

	public Integer getVoter() {
		return voter;
	}

	public void setVoter(Integer voter) {
		this.voter = voter;
	}

	public Double getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(Double avgRating) {
		this.avgRating = avgRating;
	}

	public Integer getPercentage5Star() {
		return percentage5Star;
	}

	public void setPercentage5Star(Integer percentage5Star) {
		this.percentage5Star = percentage5Star;
	}

	public Integer getPercentage4Star() {
		return percentage4Star;
	}

	public void setPercentage4Star(Integer percentage4Star) {
		this.percentage4Star = percentage4Star;
	}

	public Integer getPercentage3Star() {
		return percentage3Star;
	}

	public void setPercentage3Star(Integer percentage3Star) {
		this.percentage3Star = percentage3Star;
	}

	public Integer getPercentage2Star() {
		return percentage2Star;
	}

	public void setPercentage2Star(Integer percentage2Star) {
		this.percentage2Star = percentage2Star;
	}

	public Integer getPercentage1Star() {
		return percentage1Star;
	}

	public void setPercentage1Star(Integer percentage1Star) {
		this.percentage1Star = percentage1Star;
	}

	@Override
	public String toString() {
		return "RatingDTO [voted=" + voted + ", voter=" + voter + ", avgRating=" + avgRating
				+ ", percentage5Star=" + percentage5Star + ", percentage4Star=" + percentage4Star + ", percentage3Star="
				+ percentage3Star + ", percentage2Star=" + percentage2Star + ", percentage1Star=" + percentage1Star
				+ "]";
	}
	
}
