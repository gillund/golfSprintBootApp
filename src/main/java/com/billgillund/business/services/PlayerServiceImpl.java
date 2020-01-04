package com.billgillund.business.services;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billgillund.business.domain.PlayerAverage;
import com.billgillund.business.domain.PlayerHandicap;
import com.billgillund.entity.Players;
import com.billgillund.repository.PlayersRepository;


@Service
public class PlayerServiceImpl implements PlayerService 
{
	/*
	* @author William Gillund
	* @since 11/25/2019 rewrite to springBoot
	* @version 1.0
	*/
	    public static   Hashtable tempPlayers = new Hashtable();
	    private Hashtable<String,Players> playersHash ;
	    Map  <String, Players>  playerCacheByUserId = null;
	    private String filename;
	 
	    PlayerHandicapService playerHandicapService = null;
		@Autowired
		PlayersRepository playerRepository;
		
		@Autowired
	    public PlayerServiceImpl(PlayerHandicapService p_playerHandicapService) {
	    	playerHandicapService = p_playerHandicapService;
	    }
	   

	    public Hashtable getAllPlayers() {
	        return (Hashtable) getPlayers().clone();
	    }
	    
	    public Players getPlayer(String playerName) {
	        return (Players) getPlayers().get(playerName);
	    }

	    public Hashtable addPlayer(String aName,  String aeMail, String aphoneNumber,String auserId, String apassWord) throws Exception {
	        if ( aName == null || aName.equals("") ) {
	            throw new Exception("Illegal Player Name: Player Missing");
	        }
	        if ( aeMail == null || aeMail.equals("") ) {
	            throw new Exception("email address missing");
	        }
	       	if ( aphoneNumber == null || aphoneNumber.equals("") ) {
	            throw new Exception("Pone number required");
	        }
	       	if (aName.contains("<")  || aName.contains(">")){
	       		throw new Exception("No Special characters allowed");
	       	}
	    	if (playersHash == null)
	       	{
	       		getPlayers();
	       	}
	       	
	       	if (playersHash.get(aName) != null)
	       	{
	       	 throw new Exception("Player exists, name must be unique ");
	       	}
	       		       
	       	int playerId=1;
	        Players newPlayer = new Players();
	     
	        newPlayer.setEmail(aeMail);
	        newPlayer.setName(aName);
	        newPlayer.setPassword(apassWord);
	        newPlayer.setPhoneNumber(aphoneNumber);
	        newPlayer.setUserId(auserId);
	        String validLogin = newPlayer.getUserId()+newPlayer.getPassword();
	    	if (playerCacheByUserId.get(validLogin) != null)
	       	{
	       	 throw new Exception("UserId and Password taken ");
	       	}
	    	Players p = playerRepository.save(newPlayer);
	        playersHash.put(newPlayer.getName(), p);
	        // update user Id cache
	        validLogin = p.getUserId()+p.getPassword();
			playerCacheByUserId.put(validLogin, newPlayer);
			
		
	         return getPlayers();
	    }
	    public Hashtable removePlayer(Players p) throws Exception {
	
	    	if (playersHash.containsKey(p.getName()))
	    	{
	    		  playerRepository.delete(p.getPlayerId());
	    		  Players player = (Players) playersHash.get(p.getName());
	    		  playersHash.remove(p);
	    		  String userId = p.getUserId() + p.getPassword();
	    		  playerCacheByUserId.remove(userId);
    		  
	    	}
	    	else
	       {
	    		throw  new Exception("Not Found");
	       }
	          
	    	
	     	 return getPlayers();
	    } 

	    protected String getFilename() {
	         return filename;
	    }
	    
	    protected Hashtable getPlayers() {
	        if ( playersHash == null ) {
	            playersHash = readPlayers(getFilename());
	        }
	        
	        if (playerCacheByUserId == null)
	        {
	        	playerCacheByUserId = new ConcurrentHashMap<String, Players>();
	        	this.buildUserIdMap();
	        }
	        return playersHash;
	    
	    }
	    private void buildUserIdMap()
		 {
	    	
	    	playerCacheByUserId  =   playersHash.values().stream().collect(
	    			Collectors.toMap(s->s.getUserId()+s.getPassword() , s->s));
	    	
	    	/*
		    	Set<String> keys = playersHash.keySet();
				for(String key:keys) {
					
					Players p = (Players) playersHash.get(key);
					String validLogin = p.getUserId()+p.getPassword();
					playerCacheByUserId.put(validLogin, p);
				}
				*/
		 }

	    protected Hashtable readPlayers(String aFilename) {
	      
	        Iterable <Players> al = playerRepository.findAll();

	        al.forEach(player ->{
	        	Players p = new Players();
	        	p.setName(player.getName());
	        	p.setEmail(player.getEmail());
	        	p.setPassword(player.getPassword());
	        	p.setPhoneNumber(player.getPhoneNumber());
	        	p.setPlayerId(player.getPlayerId());
	        	p.setUserId(player.getUserId());
	        	tempPlayers.put(player.getName(), p);
	        }) ;
	        
	        return tempPlayers;
	    }
	   

	    protected BufferedReader getInputStream(String aFilename) throws IOException {
	        return new BufferedReader(new FileReader(aFilename));
	    }

		    
	   
	@Override
	public boolean isValidUserId(String userId) {
		if (playersHash== null)  // any time put also put in playerCacheByUserId
		{
			getAllPlayers();
		}
		
		Players valid = playerCacheByUserId.get(userId);
		if (valid !=null)
		{
			return true;
		}
		return false;
	}
	@Override
	public Players getPlayerByUserId(String userId) {
		
		if (playersHash == null)  // any time put also put in playerCacheByUserId
		{
			getAllPlayers();
		}
		
		Players validPlayer = playerCacheByUserId.get(userId);
		
		return validPlayer;
	}
		
}	
		