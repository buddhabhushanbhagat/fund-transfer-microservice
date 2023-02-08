package com.fundtransfer.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fundtransfer.entity.Transaction;
import com.fundtransfer.service.FileExporter;
import com.fundtransfer.service.TransactionService;

@RestController
@CrossOrigin(value = "http://localhost:4200/")
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	TransactionService transService;

	@Autowired
	private FileExporter fileExporter;

	String transactionDetails = "";
	String fileContent = "";

	@PostMapping("")
	public Transaction createTransaction(@RequestBody Transaction transaction) {
		transaction.setTransactionId(Math.abs(new Random().nextLong()));
		Transaction transactionDetails = transService.createTransaction(transaction);
		Double remitterBalance = transService.getRemitterBalanceByAccountNumber(transaction.getRemitterAccountNumber());
		System.out.println("remitterBalance:" + remitterBalance);
		Double beneficiaryBalance = transService.getBeneficiaryBalanceByAccountNumber(transaction.getAccountNumber());
		transactionDetails.setRemitterAccountBalance(remitterBalance);
		transactionDetails.setBeneficiaryAccountBalance(beneficiaryBalance);
		Transaction createdTransaction = transService.saveTransaction(transactionDetails);
		System.out.println("createdTransaction:" + createdTransaction);
		return createdTransaction;
	}

	@GetMapping("")
	public List<Transaction> getTransactionBetweenDates(@RequestParam String transactionStartDate,
			@RequestParam String transactionEndDate) throws ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date transactionStartDateConverted = sdf1.parse(transactionStartDate);
		Date transactionEndDateConverted = sdf1.parse(transactionEndDate);

		List<Transaction> transactionList = transService.getTransactionBetweenDates(transactionStartDateConverted,
				transactionEndDateConverted);
		return transactionList;
	}

	@GetMapping("/download")
	public ResponseEntity<List<Transaction>> downloadTransactionBetweenDates(@RequestParam String transactionStartDate,
			@RequestParam String transactionEndDate) throws IOException, ParseException {
		List<Transaction> transactionList = getTransactionBetweenDates(transactionStartDate, transactionEndDate);
		String fileName = "Reports" + transactionStartDate + "-" + transactionEndDate + ".txt";
		transactionList.forEach(transaction -> {
			transactionDetails = "\ntransactionId:" + transaction.getTransactionId() + "\n" + "remitterAccountNumber:"
					+ transaction.getRemitterAccountNumber() + "\n" + "accountNumber:" + transaction.getAccountNumber()
					+ "\n" + "amounTransfered:" + transaction.getAmounTransfered() + "\n" + "narration:"
					+ transaction.getNarration() + "\n" + "remitterAccountBalance:"
					+ transaction.getRemitterAccountBalance() + "\n" + "beneficiaryAccountBalance:"
					+ transaction.getBeneficiaryAccountBalance() + "\n" + "responseStatus:"
					+ transaction.getResponseStatus() + "\n" + "transactionDate:" + transaction.getTransactionDate()
					+ "\n";
			// append all transaction details in fileContent
			fileContent = fileContent + transactionDetails;
		});
		if(fileContent == null || fileContent.isBlank())
			throw new FileNotFoundException("Transactions not found between these dates");
	
	// Create text file
		Path exportedPath = fileExporter.export(fileContent, fileName);
		File exportedFile = exportedPath.toFile();
	//Reading file data
		FileInputStream fis = new FileInputStream(exportedFile);
		String readFileContent = "";
		int data = 0;
		while ((data = fis.read()) != -1) {
			readFileContent = readFileContent + (char) data;
		}
		data = 0;
		fis.close();
		return ResponseEntity.status(HttpStatus.OK).body(transactionList);
         
	      
	}
}
