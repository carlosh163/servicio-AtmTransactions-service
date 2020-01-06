package com.springboot.appbanco.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.springboot.appbanco.model.Account;
import com.springboot.appbanco.model.AtmInfo;
import com.springboot.appbanco.model.AtmTransaction;
import com.springboot.appbanco.model.Client;
import com.springboot.appbanco.model.Person;
import com.springboot.appbanco.service.IAtmTransactionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Api(tags = "ATMInfo")
@RestController
public class AtmTransactionController {
	
	private static Logger log = LoggerFactory.getLogger(AtmTransactionController.class);
	
	@Autowired
	@Qualifier("cuenta")
	WebClient wCAccount;

	@Autowired
	@Qualifier("cliente")
	WebClient wCClient;

	@Autowired
	@Qualifier("persona")
	WebClient wCPerson;
	
	@Autowired
	@Qualifier("atmInfo")
	WebClient wCAtmInfo;

	@Autowired
	IAtmTransactionService service;
	
	@PostMapping("/Create")
	private Mono<AtmTransaction> add (@RequestBody AtmTransaction accD){
		
		return service.create(accD);
	}
	
	@ApiOperation(value = "Search all AccountDue", notes = "Returning AccountDues.")
	  @GetMapping("/SearchAll")
	  public Flux<AtmTransaction> findAll() {
	    return service.findAll();
	  }

	  @GetMapping("/SearchById/{id}")
	  public Mono<AtmTransaction> findById(@PathVariable String id) {
	    return service.findById(id);
	    
	  }

	  @PutMapping("/Edit/{id}")
	  public Mono<AtmTransaction> update(@RequestBody AtmTransaction accD, @PathVariable String id) {
	    return service.update(accD, id);
	  }

	  @DeleteMapping("/Remove/{id}")
	  public Mono<Void> delete(@PathVariable String id) {
	    return service.delete(id);
	  }
	  
	  
	  @PostMapping("/deposit/{red}/{accNumber}/{quantity}")
		public Mono<AtmTransaction> deposit(@PathVariable String red,@PathVariable Integer accNumber, @PathVariable double quantity) {

		  Map<String, Object> paramsa = new HashMap();
		  paramsa.put("accountNumber", accNumber);

			// Cobrar Comision:
			// Validar el num maximo:
			
			return wCAccount.get().uri("/findAccountByNumberAccount/{accountNumber}", paramsa).retrieve()
					.bodyToMono(Account.class).switchIfEmpty(Mono.empty()).flatMap(c -> {
						Map<String, Object> params = new HashMap();
						params.put("bankName", c.getBankName());
						params.put("red", red);
						
						
						
						return wCAtmInfo.get().uri("/getAtmByNameBankAndRed/{bankName}/{red}", params).retrieve()
								.bodyToMono(AtmInfo.class).switchIfEmpty(Mono.empty()).flatMap(atm -> {
									
									log.info("Banco:"+atm.getBankName());
									
									
									
									log.info("numMax:" + atm.getNumMaxDespositAtm());

									
									return service.getTranByNroAccount(accNumber)
											.filter(cb -> cb.getTransactionType().equals("Deposito")).count().flatMap(q -> {

												boolean estadoCommi = false;
												log.info("Cantidad de Depo en la Acc" + q);
												if (q >= atm.getNumMaxDespositAtm()) {
													log.info("Excede la cantidad permitida");
													estadoCommi = true;
												} else {
													log.info("No excede");
													estadoCommi = false;
												}
												return Mono.just(estadoCommi).flatMap(v -> {
													double vCommi = 0.0;
													log.info("Estado Commi" + v);
													if (v) {
														vCommi = 0.10 * quantity;
													}

													return Mono.just(vCommi).flatMap(commi -> {
														log.info("Valor de Comision:" + commi);

														Map<String, Object> paramsC = new HashMap();
														paramsC.put("accountNumber", accNumber);
														paramsC.put("quantity", quantity - commi);
														return wCClient.put()
																.uri("/updateBalanceAccountByAccountNumber/{accountNumber}/{quantity}",
																		paramsC)
																.retrieve().bodyToFlux(Client.class).next().flatMap(o -> {
																	return wCPerson.put().uri(
																			"/updateBalanceAccountByAccountNumber/{accountNumber}/{quantity}",
																			paramsC).retrieve().bodyToFlux(Person.class).next()
																			.flatMap(o2 -> {
																				return wCAccount.put().uri(
																						"/updateBalanceAccountByAccountNumber/{accountNumber}/{quantity}",
																						paramsC).retrieve().bodyToMono(Account.class)
																						.flatMap(account -> {

																							AtmTransaction objT = new AtmTransaction();

																							objT.setDate(new Date());
																							objT.setAccountNumber(accNumber);
																							objT.setQuantity(quantity);
																							objT.setTransactionType("Deposito");
																							objT.setOriginMov("Efectivo");
																							objT.setCommission(commi);
																							return service.create(objT);
																						});
																			});
																});
													});
												});
											});
									
									
									
								});
					});
			
			
			
			

		}
	  
}
