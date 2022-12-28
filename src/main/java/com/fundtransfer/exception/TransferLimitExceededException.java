package com.fundtransfer.exception;

import lombok.Data;

@Data
public class TransferLimitExceededException extends RuntimeException {
	private String message;

	public TransferLimitExceededException(String string) {
		// TODO Auto-generated constructor stub
		this.message = string;
	}
}
