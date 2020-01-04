package com.billgillund.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.billgillund.entity.Round;


public interface RoundRepository extends CrudRepository<Round, Long> {
	   List<Round> findByPlayerId(final long playerId);
	}
