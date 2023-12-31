package com.codercampus.Assignment11.repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.codercampus.Assignment11.domain.Transaction;

@Repository
public class TransactionRepository {
	private List<Transaction> transactions = new ArrayList<>(100);
	
	public TransactionRepository () {
		super();
		populateData();
	}
	
	public List<Transaction> findAll () {
		Collections.sort(transactions);
		return transactions;
	}
	
	public Transaction findById(Long transactionId) {
		return transactions.stream()
						   .filter(t -> t.getId().equals(transactionId))
						   .findFirst()
						   .orElse(null);
		
	// Initially i used an index based lookup, but it no longer worked once
	// the list was sorted differently. Switching to a stream based look up
	// filters the transactions based on their id's so we can find the 
	// transactions by their id's no matter where they are in the list.
	}

	@SuppressWarnings("unchecked")
	private void populateData() {
		try (FileInputStream fileInputStream = new FileInputStream("transactions.txt");
			 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);) {
			this.transactions = (List<Transaction>) objectInputStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
}
