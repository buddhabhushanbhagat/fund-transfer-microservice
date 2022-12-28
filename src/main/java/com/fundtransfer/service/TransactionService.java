package com.fundtransfer.service;

import java.util.Date;
import java.util.List;

import com.fundtransfer.entity.Beneficiary;
import com.fundtransfer.entity.Transaction;

public interface TransactionService {

	Double getRemitterBalanceByAccountNumber(Long remitterAccountNumber);
	Double getBeneficiaryBalanceByAccountNumber(Long accountNumber);
	Transaction createTransaction(Transaction transaction);
	Transaction saveTransaction(Transaction transactionDetails);
	List<Transaction> getTransactionBetweenDates(Date transactionStartDate, Date transactionEndDate);

}
