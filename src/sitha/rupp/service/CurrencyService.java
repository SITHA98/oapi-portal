package sitha.rupp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import sitha.rupp.model.Currency;
import sitha.rupp.repository.CurrencyRepository;

public class CurrencyService {

	@Autowired
	CurrencyRepository currencyRepository;
	public List<Currency>getCcy(){
		return this.currencyRepository.getAllCcy();
	}
	
}
