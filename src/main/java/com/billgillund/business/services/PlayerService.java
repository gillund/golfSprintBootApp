package com.billgillund.business.services;

import java.util.ArrayList;
import java.util.Hashtable;

import com.billgillund.business.domain.PlayerAverage;
import com.billgillund.business.domain.PlayerHandicap;
import com.billgillund.entity.Players;

public interface PlayerService {
	 public Hashtable getAllPlayers();
    
	 public Players getPlayer(String playerName);

	 public Hashtable addPlayer(String afirstName, String alastName,  String aphoneNumber, String auserId, String apassWord) throws Exception;
	 
	 public Hashtable removePlayer(Players players) throws Exception;
	 
	 public boolean isValidUserId(String userId);
	 
	 public Players getPlayerByUserId(String userId);
	 
	
}
