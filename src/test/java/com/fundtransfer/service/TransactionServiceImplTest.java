package com.fundtransfer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fundtransfer.entity.Transaction;
import com.fundtransfer.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
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
		Transaction transaction = new Transaction(Math.abs(new Random().nextLong()),Math.abs(new Random().nextLong()),Math.abs(new Random().nextLong()),344.98,"mock summery",44.4,455.54,"sucess",new Date(),new Date(),new Date());
		Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
		Transaction actual = transactionServiceImpl.saveTransaction(transaction);
		assertEquals(transaction, actual);
		}

	@Test
	void testGetTransactionBetweenDates() {
		Transaction transaction1 = new Transaction(Math.abs(new Random().nextLong()),Math.abs(new Random().nextLong()),Math.abs(new Random().nextLong()),344.98,"mock summery",44.4,455.54,"sucess",new Date(),new Date(),new Date());
		Transaction transaction2 = new Transaction(Math.abs(new Random().nextLong()),Math.abs(new Random().nextLong()),Math.abs(new Random().nextLong()),344.98,"mock summery",44.4,455.54,"sucess",new Date(),new Date(),new Date());
		Transaction transaction3 = new Transaction(Math.abs(new Random().nextLong()),Math.abs(new Random().nextLong()),Math.abs(new Random().nextLong()),344.98,"mock summery",44.4,455.54,"sucess",new Date(),new Date(),new Date());
		List<Transaction> transactionList = new ArrayList<>();
		transactionList.add(transaction1);
		transactionList.add(transaction2);
		transactionList.add(transaction3);
		
        final Date date = Mockito.mock(Date.class);
		Mockito.when(transactionRepository.findByTransactionDateBetween(date,date)).thenReturn(transactionList);
		List<Transaction> actualTransactionList = transactionServiceImpl.getTransactionBetweenDates(date,date);
	    assertEquals(transactionList, actualTransactionList);

	}

}
