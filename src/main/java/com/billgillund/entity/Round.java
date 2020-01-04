package com.billgillund.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ROUND")
public class Round {
	@Id
	@Column(name="ROUND_ID")
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;
	@Column(name="PLAYER_ID")
	private long  playerId; 
	@Column(name="ROUND_DATE")
	private String roundDate;
	@Column(name="NAME")
	private String name;
	@Column(name="COURSE")
	private String course;
	@Column(name="SCORE")
	private int score;
	@Column(name="SLOPE")
	private int slope;
	@Column(name="RATING")
	private float rating;
	@Column(name="DELTA")
	private float delta;
	@Column(name = "INUSE", nullable = false)
	private boolean inUse = false;

	public boolean isInUse() {
		return inUse;
	}
	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
	public String getRoundDate() {
		return roundDate;
	}
	public void setRoundDate(String roundDate) {
		this.roundDate = roundDate;
	}
	public String getPlayer() {
		return name;
	}
	public void setPlayer(String name) {
		this.name = name;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getSlope() {
		return slope;
	}
	public void setSlope(int slope) {
		this.slope = slope;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public float getDelta() {
		return delta;
	}
	public void setDelta(float delta) {
		this.delta = delta;
	}
		
}
