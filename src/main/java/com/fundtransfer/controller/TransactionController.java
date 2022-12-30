package com.fundtransfer.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fundtransfer.entity.Transaction;
import com.fundtransfer.service.TransactionService;

@RestController
@CrossOrigin(value = "http://localhost:4200/")
@RequestMapping("/transaction")
public class TransactionController {
	
	@Autowired
	TransactionService transService;
	 
	@PostMapping("")
	public Transaction createTransaction(@RequestBody Transaction transaction) {
		transaction.setTransactionId(Math.abs(new Random().nextLong()));
		Transaction transactionDetails = transService.createTransaction(transaction);
		Double remitterBalance = transService.getRemitterBalanceByAccountNumber(transaction.getRemitterAccountNumber());
		System.out.println("remitterBalance:"+remitterBalance);
		Double beneficiaryBalance = transService.getBeneficiaryBalanceByAccountNumber(transaction.getAccountNumber());
		transactionDetails.setRemitterAccountBalance(remitterBalance);
		transactionDetails.setBeneficiaryAccountBalance(beneficiaryBalance);
		Transaction createdTransaction = transService.saveTransaction(transactionDetails);
		System.out.println("createdTransaction:"+createdTransaction);
		return createdTransaction;
	}
	
	@GetMapping("")  
	public List<Transaction> getTransactionBetweenDates(@RequestParam String transactionStartDate,@RequestParam String transactionEndDate) throws ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date transactionStartDateConverted = sdf1.parse(transactionStartDate);
		Date transactionEndDateConverted = sdf1.parse(transactionEndDate);
		
		List<Transaction> transactionList = transService.getTransactionBetweenDates(transactionStartDateConverted,transactionEndDateConverted);
		return transactionList;
	}
	
}
