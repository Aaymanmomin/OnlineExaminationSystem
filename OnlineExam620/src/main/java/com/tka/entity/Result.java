package com.tka.entity;



	import jakarta.persistence.Entity;
	import jakarta.persistence.Id;

	@Entity
	public class Result 
	{
			@Id
			String username;
			@Id 
			String subject;
			
			int score;

			public String getUsername() {
				return username;
			}

			public void setUsername(String username) {
				this.username = username;
			}

			public String getSubject() {
				return subject;
			}

			public void setSubject(String subject) {
				this.subject = subject;
			}

			public int getScore() {
				return score;
			}

			public void setScore(int score) {
				this.score = score;
			}

			@Override
			public String toString() {
				return "Result [username=" + username + ", subject=" + subject + ", score=" + score + "]";
			}
			
			

	}



