package com.fundtransfer.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.fundtransfer.entity.Transaction;
import com.fundtransfer.repository.TransactionRepository;

class TransactionServiceImplTest {

	
	@InjectMocks
	private TransactionServiceImpl transactionServiceImpl;
	
	@Mock
	private TransactionRepository transactionRepository;
	

	@Test
	void testCreateTransaction() {
		Transaction expected = new Transaction(Math.abs(new Random().nextLong()),Math.abs(new Random().nextLong()),Math.abs(new Random().nextLong()),344.98,"mock summery",44.4,455.54,"sucess",new Date(),new Date(),new Date());

	}

	@Test
	void testSaveTransaction() {
		
	}

	@Test
	void testGetTransactionBetweenDates() {
		
	}

}
