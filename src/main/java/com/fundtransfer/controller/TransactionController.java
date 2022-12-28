package com.fundtransfer.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fundtransfer.entity.Transaction;
import com.fundtransfer.repository.TransactionRepository;
import com.fundtransfer.service.TransactionService;

@RestController
@CrossOrigin(value = "http://localhost:4200/")
public class TransactionController {
	
	@Autowired
	TransactionService transService;
	
	@PostMapping("/transaction")
	public Transaction createTransaction(@RequestBody Transaction transaction) {
		System.out.println("Inn Transaction COntroller:"+transaction);
		transaction.setTransactionId(Math.abs(new Random().nextLong()));
		System.out.println("Inn Transaction COntroller:"+transaction);

		Transaction transactionDetails = transService.createTransaction(transaction);
		//save below in service layer
		Double remitterBalance = transService.getRemitterBalanceByAccountNumber(transaction.getRemitterAccountNumber());
		System.out.println("remitterBalance:"+remitterBalance);
		Double beneficiaryBalance = transService.getBeneficiaryBalanceByAccountNumber(transaction.getAccountNumber());
		transactionDetails.setRemitterAccountBalance(remitterBalance);
		transactionDetails.setBeneficiaryAccountBalance(beneficiaryBalance);
		Transaction createdTransaction = transService.saveTransaction(transactionDetails);
		System.out.println("createdTransaction:"+createdTransaction);
		return createdTransaction;
	}
	
//	@GetMapping("/transaction")  
//	public List<Transaction> getTransactionBetweenDates(@RequestBody Transaction transaction) {
////		Date sellDate = new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01");
//		Tue Dec 27 11:57:39 IST 2022
//		System.out.println("startDate:" +transactionStartDate);
//		System.out.println("endDate:" +transactionEndDate);
//		List<Transaction> transactionList = transService.getTransactionBetweenDates(transactionStartDate,transactionEndDate);
//		return transactionList;
//	}
	
	@GetMapping("/transaction")  
	public List<Transaction> getTransactionBetweenDates(@RequestParam String transactionStartDate,@RequestParam String transactionEndDate) throws ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date transactionStartDateConverted = sdf1.parse(transactionStartDate);
		Date transactionEndDateConverted = sdf1.parse(transactionEndDate);
		
		List<Transaction> transactionList = transService.getTransactionBetweenDates(transactionStartDateConverted,transactionEndDateConverted);
		return transactionList;
	}
	
}
