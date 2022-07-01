package com.example.microservices.currencyexchangeservice;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//import com.example.microservices.currencyconversionservice.CurrencyConversionRates;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class CurrencyExchangeController {

	@Autowired
	private Configuration configuration;
	
	@Autowired
	private CurrencyExchangeRepository repository;
	
	@Autowired
	CurrencyExchangeService currencyExchangeService;

	@Autowired
	private Environment environment;

	@GetMapping("/exchange")
	public CurrencyExchange retrieveExchange() {
		return new CurrencyExchange(configuration.getId(), configuration.getFrom(), configuration.getTo(), configuration.getConversionMultiple());
		
	}
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
		if(currencyExchange == null) {
			throw new RuntimeException("Unable to Find data for " + from + " to" + to);
		}
		
		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);

		return currencyExchange;
	}
	@GetMapping("/currency-exchange-public-api/from/{from}/to/{to}/quantity/{quantity}")
	public BigDecimal retrieveExchangeValueFromPublicAPI(@PathVariable String from, @PathVariable String to) {
		BigDecimal responseCurrencyRate=null;
		
		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		
		ResponseEntity<CurrencyExchangeRates> responseEntity = new RestTemplate().getForEntity(
				"https://api.exchangerate.host/latest?base={from}", CurrencyExchangeRates.class, uriVariables);

		CurrencyExchangeRates currencyExchangeRates = responseEntity.getBody();
		if(currencyExchangeRates.getRates().containsKey(to)) {
			 responseCurrencyRate = currencyExchangeRates.getRates().get(to);
		}
		
		System.out.print(responseCurrencyRate); 
		return responseCurrencyRate;
		
		
	}

	@PutMapping("/currency-exchange/from/{from}/to/{to}")
	public Long saveCurrencyExchange(@RequestBody CurrencyExchange currencyExchange) {
		currencyExchangeService.saveOrUpdateCurrencyExchange(currencyExchange);
		return currencyExchange.getId(); 
	}

	@PostMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange updateCurrencyExchange(@RequestBody CurrencyExchange currencyExchange) {
		currencyExchangeService.saveOrUpdateCurrencyExchange(currencyExchange);
		return currencyExchange;
	}

}
