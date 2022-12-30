package com.fundtransfer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Temporal;
//import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
		@Id
	    private Long transactionId;

	    private Long remitterAccountNumber; //remitterAccNo
	    private Long accountNumber; //BeneAccNo
	    private Double amounTransfered;
	    
	    private String narration;
	    
	    private Double remitterAccountBalance; //BeneAccNo
	    private Double beneficiaryAccountBalance; //BeneAccNo
	    private String responseStatus;
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
