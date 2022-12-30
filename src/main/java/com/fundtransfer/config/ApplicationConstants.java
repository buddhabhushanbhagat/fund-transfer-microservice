package com.fundtransfer.config;

public class ApplicationConstants {
	public static final String RESPONSE_STATUS_SUCESS = "sucess";
	public static final String RESPONSE_STATUS_FAILED = "failed";
	
	//rest template URLs
	public static final String GET_REMITTER_BALANCE_URL ="http://127.0.0.1:8001/remitter/remitterBalance/";
	public static final String GET_BENEFICIARY_DETAILS_URL ="http://127.0.0.1:8002/beneficiary/accountnumber/";
	public static final String DEBIT_REMITTER_BALANCE_URL ="http://127.0.0.1:8001/remitter/updateBalanceByAccountNumber/";
	public static final String CREDIT_BENEFICIARY_BALANCE_URL ="http://127.0.0.1:8002/beneficiary/creditAmount/";
	public static final String DECREASE_BENEFICIARY_TRANSACTION_LIMIT_URL ="http://127.0.0.1:8002/beneficiary/debitTransactionLimit/";
}

