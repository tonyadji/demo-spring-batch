/**
 * 
 */
package com.example.demobatch.entites;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author tonys
 *
 */
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class BankTransaction {

	@Id
	private Long id;
	private Long accountID;
	private LocalDateTime transactionDate;
	@Transient
	private String strDate;
	private String transactionType;
	private double amount;
}
