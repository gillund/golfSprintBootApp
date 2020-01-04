package com.billgillund.repository;

import org.springframework.data.repository.CrudRepository;

import com.billgillund.entity.Players;


public interface PlayersRepository extends CrudRepository<Players,Long>{
	Players findByname(final String playerName);
	
}
