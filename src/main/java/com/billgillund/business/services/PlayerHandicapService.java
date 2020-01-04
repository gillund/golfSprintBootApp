package com.billgillund.business.services;

import java.util.List;

import com.billgillund.entity.Players;
import com.billgillund.entity.Round;

public interface PlayerHandicapService {
	 public List 	addScore(String aDate, String aPlayer, String aCourse, int aScore, int aCourseId, long playerId) throws Exception;
	 public void 	deleteScore(Players aPlayer, Round aRound) throws Exception;
	 public List 	getScoresForPlayer(long aPlayer);
	 public float 	getHandicap(Players aPlayer) ;
     public float 	getAverageDelta(Players aPlayer) ;
     public float 	getAverage(Players aPlayer) ;

}
