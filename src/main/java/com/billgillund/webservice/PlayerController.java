package com.billgillund.webservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.billgillund.entity.Players;
import com.billgillund.entity.Round;
import com.billgillund.repository.PlayersRepository;

@RestController
public class PlayerController {
	@Autowired
    private PlayersRepository playerRepository;

    		@RequestMapping(value="/players", method= RequestMethod.GET)
    		List<Players> findAll(@RequestParam(required=false) String playerName){
	    	List<Players> players = new ArrayList<>();
	        if(null==playerName){
	            Iterable<Players> results = this.playerRepository.findAll();
	            results.forEach(p-> {players.add(p);});
	        }else{
	            Players player = this.playerRepository.findByname(playerName);
	            if(null!=player) {
	               players.add(player);
	               }
	        }
	        return players;


    	}
}
