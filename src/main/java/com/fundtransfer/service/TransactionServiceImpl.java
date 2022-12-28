package com.fundtransfer.service;

import java.util.Date;
import java.util.List;

import org.hibernate.internal.util.type.PrimitiveWrapperHelper.IntegerDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fundtransfer.config.ApplicationConstants;
import com.fundtransfer.entity.Beneficiary;
import com.fundtransfer.entity.Transaction;
import com.fundtransfer.exception.TransferLimitExceededException;
import com.fundtransfer.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	TransactionRepository transRepo;
	
	@Override
	public Double getRemitterBalanceByAccountNumber(Long remitterAccountNumber) {
		// TODO Auto-generated method stub
		return restTemplate.getForObject(ApplicationConstants.GET_REMITTER_BALANCE_URL+remitterAccountNumber, Double.class);
	}

	@Override
	public Double getBeneficiaryBalanceByAccountNumber(Long accountNumber) {
		// TODO Auto-generated method stub
		 Beneficiary beneficiary = restTemplate.getForObject(ApplicationConstants.GET_BENEFICIARY_DETAILS_URL+accountNumber, Beneficiary.class);
		 System.out.println("beneficiary Details in trans:" +beneficiary);
		 return beneficiary.getCurrentBalance();
	}

	@Override
	public Transaction createTransaction(Transaction transaction) {
		// TODO Auto-generated method stub
		Boolean isDebited = false;
		Boolean isCredited = false;
		
		int responseCode = ApplicationConstants.RESPONSE_CODE_FAILED;
		
		
		Beneficiary beneficiary = restTemplate.getForObject(ApplicationConstants.GET_BENEFICIARY_DETAILS_URL+transaction.getAccountNumber(), Beneficiary.class);
		if(beneficiary.getMaxTsfrLimit()<0 || beneficiary.getMaxTsfrLimit() == 0)
			throw new TransferLimitExceededException("Beneficiary transaction limit exceeded");
		
		MultiValueMap<String, Double> body = new LinkedMultiValueMap<String, Double>();     
		body.add("amount", transaction.getAmounTransfered());
		HttpEntity<?> httpEntity = new HttpEntity<Object>(body);	
	
		isDebited = restTemplate.exchange(ApplicationConstants.DEBIT_REMITTER_BALANCE_URL+transaction.getRemitterAccountNumber(),HttpMethod.PUT,httpEntity,Boolean.class).getBody();
		
		if(isDebited) {
			isCredited = restTemplate.exchange(ApplicationConstants.CREDIT_BENEFICIARY_BALANCE_URL+transaction.getAccountNumber(),HttpMethod.PUT,httpEntity,Boolean.class).getBody();
			int updatedTransferLimit = restTemplate.exchange(ApplicationConstants.DECREASE_BENEFICIARY_TRANSACTION_LIMIT_URL+transaction.getAccountNumber(),HttpMethod.PUT,null,Integer.class).getBody();
			responseCode = ApplicationConstants.RESPONSE_CODE_SUCESS;
		}
		
		transaction.setResponceCode(responseCode);
		return transaction;
	}

	@Override
	public Transaction saveTransaction(Transaction transactionDetails) {
		// TODO Auto-generated method stub
		return transRepo.save(transactionDetails);
	}

	@Override
	public List<Transaction> getTransactionBetweenDates(Date transactionStartDate, Date transactionEndDate) {
		// TODO Auto-generated method stub
		return transRepo.findByTransactionDateBetween(transactionStartDate,transactionEndDate);
	}

}
