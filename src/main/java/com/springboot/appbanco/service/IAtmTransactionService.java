package com.springboot.appbanco.service;

import com.springboot.appbanco.model.AtmTransaction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAtmTransactionService {

	public Flux<AtmTransaction> findAll();

	public Mono<AtmTransaction> findById(String id);

	public Mono<AtmTransaction> update(AtmTransaction perso, String id);

	public Mono<Void> delete(String id);

	public Mono<AtmTransaction> create(AtmTransaction accD);
	
	Flux<AtmTransaction> getTranByNroAccount(Integer NumberAcc);

}
