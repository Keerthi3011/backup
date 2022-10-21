package com.project.loanApplication.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewLoanFormDetails 
{
	private String customerName;
	private double loanAmount;
	private int loanStartDate;
	private int paymentFrequency;
	private int interestRate;
	private int noOfMonths;
	private String paymentTerm;
	
}
