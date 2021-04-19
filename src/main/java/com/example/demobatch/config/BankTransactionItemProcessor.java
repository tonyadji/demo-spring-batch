package com.example.demobatch.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.demobatch.entites.BankTransaction;

@Component
public class BankTransactionItemProcessor implements ItemProcessor<BankTransaction, BankTransaction> {

	private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyy-HH:mm");
	@Override
	public BankTransaction process(BankTransaction item) throws Exception {
		item.setTransactionDate(LocalDateTime.parse(item.getStrDate(), dateFormat));
		return item;
	}

}		