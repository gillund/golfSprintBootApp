package com.billgillund.business.domain;

public class SelectedPlayer {
	
		String playerName;
		float handicap;
		float avgDelta;
		float avgScore;
		public SelectedPlayer (String aPlayerName, float aHandicap,  float aAvgScore, float aAvgDelta){
			 playerName = aPlayerName;
			 handicap   = aHandicap;
			 avgDelta   = aAvgDelta;
			 avgScore   = aAvgScore;
			}
		public String getPlayerName() {
			return playerName;
		}
		public void setPlayerName(String playerName) {
			this.playerName = playerName;
		}
		public float getAvgDelta() {
			return avgDelta;
		}
		public void setAvgDelta(float avgDelta) {
			this.avgDelta = avgDelta;
		}
		public void setHandicap(float handicap) {
			this.handicap = handicap;
		}
		public void setAvgScore(float avgScore) {
			this.avgScore = avgScore;
		}
		
		
		public String getName (){
			return playerName;
		}
		public float getHandicap (){
			return handicap;
		}
		public float getDelta (){
			return avgDelta;
		}
		public float getAvgScore(){
			return avgScore;
		}
}




