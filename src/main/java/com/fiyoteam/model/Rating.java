package com.fiyoteam.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "rating")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rating implements Serializable{

	private static final long serialVersionUID = 8388803893092057021L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id", unique=true, nullable=false)
	private int id;
	
	@OneToOne
    @JoinColumn(name = "voted_user_id")  
	private User voted;
	
	@OneToOne
    @JoinColumn(name = "voter_user_id")  
	private User voter;

	@Column(name = "rating")
	private Integer rating;
	
	public Rating(){
		
	}

	public Rating(int id, User voted, User voter, Integer rating) {
		super();
		this.id = id;
		this.voted = voted;
		this.voter = voter;
		this.rating = rating;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getVoted() {
		return voted;
	}

	public void setVoted(User voted) {
		this.voted = voted;
	}

	public User getVoter() {
		return voter;
	}

	public void setVoter(User voter) {
		this.voter = voter;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Rating [id=" + id + ", voted=" + voted + ", voter=" + voter + ", rating=" + rating + "]";
	}
	
}

