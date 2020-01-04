package com.billgillund.webservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.billgillund.entity.Round;
import com.billgillund.repository.RoundRepository;

@RestController
public class RoundController {
	    @Autowired
	    private RoundRepository roundRepository;

	    	@RequestMapping(value="/rounds", method= RequestMethod.GET)
	    	List<Round> findAll(@RequestParam(required=false) Long id){
	    	List<Round> rooms = new ArrayList<>();
	        if(null==id){
	            Iterable<Round> results = this.roundRepository.findAll();
	            results.forEach(room-> {rooms.add(room);});
	        }else{
	            Round player = (Round) this.roundRepository.findByPlayerId(id.longValue());
	            if(null!=player) {
	               rooms.add(player);
	               }
	        }
	        return rooms;
	    }
	}