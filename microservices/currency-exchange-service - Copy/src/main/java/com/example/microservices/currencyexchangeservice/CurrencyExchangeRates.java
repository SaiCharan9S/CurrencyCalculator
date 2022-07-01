package com.example.microservices.currencyexchangeservice;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class CurrencyExchangeRates {
	
	
	private boolean success;
	private String base;
	private HashMap<String, BigDecimal> rates = new HashMap<>();
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public HashMap<String, BigDecimal> getRates() {
		return rates;
	}
	public void setRates(HashMap<String, BigDecimal> rates) {
		this.rates = rates;
	}
	@Override
	public String toString() {
		return "CurrencyExchangeRates [success=" + success + ", base=" + base + ", rates=" + rates + "]";
	}
	public void setEnvironment(String port) {
		// TODO Auto-generated method stub
		
	}
	

	
}
