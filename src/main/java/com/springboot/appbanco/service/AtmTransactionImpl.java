package com.springboot.appbanco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.appbanco.model.AtmTransaction;
import com.springboot.appbanco.repo.IAtmInfoRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class AtmTransactionImpl implements IAtmTransactionService{

	@Autowired
	private IAtmInfoRepo repo;
	
	@Override
	public Mono<AtmTransaction> create(AtmTransaction accD) {
		return repo.save(accD);
	}


	@Override
	public Flux<AtmTransaction> findAll() {
		return repo.findAll();
	}

	@Override
	public Mono<AtmTransaction> findById(String id) {
		return repo.findById(id);
	}

	@Override
	public Mono<AtmTransaction> update(AtmTransaction accDueD, String id) {
		// TODO Auto-generated method stub
		return repo.findById(id).flatMap(ac-> {
			ac.setQuantity(accDueD.getQuantity());
			return Mono.just(ac);
		});
	}

	@Override
	public Mono<Void> delete(String id) {
		 return repo.findById(id).flatMap(client -> repo.delete(client));
	}


	@Override
	public Flux<AtmTransaction> getTranByNroAccount(Integer NumberAcc) {
		return repo.findByAccountNumber(NumberAcc);
	}

}
