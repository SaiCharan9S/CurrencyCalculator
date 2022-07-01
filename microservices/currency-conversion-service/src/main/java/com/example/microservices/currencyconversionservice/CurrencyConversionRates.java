package com.example.microservices.currencyconversionservice;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class CurrencyConversionRates {
	
	
	private boolean success;
	private String base;
	private HashMap<String, Double> rates = new HashMap<>();
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
	public HashMap<String, Double> getRates() {
		return rates;
	}
	public void setRates(HashMap<String, Double> rates) {
		this.rates = rates;
	}
	@Override
	public String toString() {
		return "CurrencyConversionRates [success=" + success + ", base=" + base + ", rates=" + rates + "]";
	}
	

	
}
