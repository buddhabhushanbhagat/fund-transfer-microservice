package com.fundtransfer.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Beneficiary {
	
	private int beneficiaryId;
	private String email;
	private String address;
	private String name;
	private Long accountNumber;	
	private String ifscCode;
	private int accountStatus;
	private Double currentBalance;
	private int maxTsfrLimit;
	private Date insertDate; 
	private Date updateDate; 
	private int customerId;
}
