package com.springboot.appbanco.repo;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.springboot.appbanco.model.AtmTransaction;

import reactor.core.publisher.Flux;

@Repository
public interface IAtmInfoRepo extends ReactiveMongoRepository<AtmTransaction,String>{
	public Flux<AtmTransaction> findByAccountNumber(Integer NumAcc);
	
	
}
