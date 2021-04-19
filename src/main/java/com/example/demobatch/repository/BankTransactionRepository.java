/**
 * 
 */
package com.example.demobatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demobatch.entites.BankTransaction;

/**
 * @author tonys
 *
 */
public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {

}
