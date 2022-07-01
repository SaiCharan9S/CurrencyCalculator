package com.example.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class CurrencyConversionController {

	@Autowired
	private Configuration configuration;
	
	@Autowired
	private CurrencyExchangeProxy proxy;

	@GetMapping("/exchange")
	public CurrencyConversion retrieveConversion() {
		return new CurrencyConversion(configuration.getId(), configuration.getFrom(),configuration.getTo(), configuration.getQuantity(),configuration.getConversionMultiple(), configuration.getTotalCalculatedAmount(), configuration.getEnvironment());
		
	}
	
//	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
//	public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to,
//			@PathVariable BigDecimal quantity) {
//
//		HashMap<String, String> uriVariables = new HashMap<>();
//		uriVariables.put("from", from);
//		uriVariables.put("to", to);
//
//		ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(
//				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);
//
//		CurrencyConversion currencyConversion = responseEntity.getBody();
//		System.out.print(currencyConversion); 
//
//		return new CurrencyConversion(currencyConversion.getId(), from, to, quantity,
//				currencyConversion.getConversionMultiple(),
//				quantity.multiply(currencyConversion.getConversionMultiple()), 
//				currencyConversion.getEnvironment() + " " + "rest template");
//
//	}

	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		CurrencyConversion currencyConversion = proxy.retrieveExchangeValue(from, to);

		return new CurrencyConversion(currencyConversion.getId(), from, to, quantity,
				currencyConversion.getConversionMultiple(),
				quantity.multiply(currencyConversion.getConversionMultiple()), 
				currencyConversion.getEnvironment() + " " + "feign");

	}
	@GetMapping("/currency-conversion-public-api/from/{from}/to/{to}")
	public CurrencyConversionRates retrieveExchangeValueFromPublicAPI(@PathVariable String from, @PathVariable String to) {
		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		
		ResponseEntity<CurrencyConversionRates> responseEntity = new RestTemplate().getForEntity(
				"https://api.exchangerate.host/latest?base={from}", CurrencyConversionRates.class, uriVariables);

		CurrencyConversionRates currencyExchangeRates = responseEntity.getBody();
		System.out.print(currencyExchangeRates); 
		return currencyExchangeRates;
		
		
	}

	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public BigDecimal calculateCurrencyConversion(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
	HashMap<String, String> uriVariables = new HashMap<>();
	uriVariables.put("from", from);
	uriVariables.put("to", to);
	uriVariables.put("quantity", quantity.toString());

	ResponseEntity<BigDecimal> responseEntity = new RestTemplate().getForEntity(
			"http://localhost:8000/currency-exchange-public-api/from/{from}/to/{to}/quantity/{quantity}", BigDecimal.class, uriVariables);

	BigDecimal currencyConversionRate = responseEntity.getBody();
	System.out.print(currencyConversionRate); 
	
	return currencyConversionRate.multiply(quantity);
	
//	return new CurrencyConversion(currencyConversion.getId(), from, to, quantity,
//	currencyConversion.getConversionMultiple(),
//	quantity.multiply(currencyConversion.getConversionMultiple()), 
//	currencyConversion.getEnvironment() + " " + "rest template");

	}
}
