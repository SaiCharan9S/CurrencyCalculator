package com.example.microservices.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class CurrencyExchangeService {

	

		@Autowired
		private CurrencyExchangeRepository currencyExchangeRepository;
		
		public CurrencyExchange addCurrencyExchange(CurrencyExchange currencyExchange) {
			
			return currencyExchangeRepository.save(currencyExchange);
		}

		public CurrencyExchange saveOrUpdateCurrencyExchange(CurrencyExchange currencyExchange) {
			return currencyExchangeRepository.save(currencyExchange);
			
		}
		public CurrencyExchange update(CurrencyExchange currencyExchange) {
			return currencyExchangeRepository.save(currencyExchange);
			
		}
		

}
