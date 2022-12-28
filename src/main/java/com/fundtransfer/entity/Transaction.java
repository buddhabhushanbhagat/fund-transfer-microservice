package com.fundtransfer.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.UpdateTimestamp;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class Transaction {
		@Id
	    private Long transactionId;

	    private Long remitterAccountNumber; //remitterAccNo
	    private Long accountNumber; //BeneAccNo
	    private Double amounTransfered;
	    private String narration;
	    
	    private Double remitterAccountBalance; //BeneAccNo
	    private Double beneficiaryAccountBalance; //BeneAccNo
	    private int responceCode;
	    @CreationTimestamp
		@Temporal(TemporalType.TIMESTAMP)
		@Column(nullable = false, updatable = false)
	    private Date transactionDate;
	    
		@CreationTimestamp
		@Temporal(TemporalType.TIMESTAMP)
		@Column(nullable = false, updatable = false, name = "insert_date")
		private Date insertDate;
		
		@UpdateTimestamp
		@Temporal(TemporalType.TIMESTAMP)
		@Column(nullable = false, updatable = false, name = "update_date")
		private Date updateDate;
}
