package com.project.loanApplication.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.project.loanApplication.Exception.UserNotFoundException;
import com.project.loanApplication.dto.NewLoanFormDetails;
import com.project.loanApplication.entity.LoanDetails;
import com.project.loanApplication.entity.PaymentSchedule;
import com.project.loanApplication.entityEnum.PaymentStatus;
import com.project.loanApplication.interfaces.NewLoanInterface;
import com.project.loanApplication.interfaces.PaymentScheduleInterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class NewLoanService 
{
	Logger log =LoggerFactory.getLogger(NewLoanService.class);
	PaymentSchedule payment = new PaymentSchedule();
	@Autowired
	NewLoanInterface newLoan;
	@Autowired
	PaymentScheduleInterface schedule;
	
int paymentSchedule ;
	
	public String newLoan(NewLoanFormDetails details) 
	{
		LoanDetails loan = new LoanDetails();
	// creating the new loan for a customer
		loan.setLoanAmount(details.getLoanAmount());
		loan.setCustomerName(details.getCustomerName());
		loan.setTradeDate(LocalDate.now());
		loan.setLoanStartDate(loan.getTradeDate().plusDays( details.getLoanStartDate()));
		loan.setMaturityDate(loan.getLoanStartDate().plusMonths(details.getNoOfMonths()));
		loan.setPaymentFrequency(details.getPaymentFrequency());
		loan.setInterestRate(details.getInterestRate());
		
	//	generatePaymentSchedule(details);
		
		payment.setCustomerName(details.getCustomerName());
		String name = payment.getCustomerName();
		double principal = details.getLoanAmount(), interest = 0;
		PaymentSchedule data1 = null;
		LocalDate paymentDate = loan.getLoanStartDate();
		paymentSchedule = (details.getNoOfMonths()/details.getPaymentFrequency());
		payment.setPaymentDate(loan.getTradeDate());
		interest = (principal*(details.getNoOfMonths()/12)*details.getInterestRate())/100;
		double  amount =interest;
		double evenPrincipal = details.getLoanAmount()/paymentSchedule;
		
		while(loan.getMaturityDate().compareTo(paymentDate)>0) 
		{
			if(details.getPaymentTerm().equalsIgnoreCase("Even principal"))
			{
				paymentDate = paymentDate.plusMonths(details.getPaymentFrequency());
				data1 = new PaymentSchedule(name,paymentDate, principal, interest, PaymentStatus.Projected, evenPrincipal+interest);
				principal-=evenPrincipal;
				interest = (principal*(details.getNoOfMonths()/12)*details.getInterestRate())/100;
				loan.getPaymentSchedule().add(data1);
				continue;
				
			}
			else if(details.getPaymentTerm().equalsIgnoreCase("Interest only"))
			{
				paymentDate = paymentDate.plusMonths(details.getPaymentFrequency());
				if(loan.getMaturityDate().compareTo(paymentDate)==0) amount =principal+interest;
				data1 = new PaymentSchedule(name,paymentDate, principal, interest, PaymentStatus.Projected, amount);
				loan.getPaymentSchedule().add(data1);
				continue;
			}
			else return "check the payment term";
		}
		newLoan.save(loan);
		return "Added for the new loan";
		
	}
	
	
//Updating the payment schedule (to awaiting payment)
	@Scheduled(cron = "0/60 * * * * *")
	public void UpdatePaymentSchedule() {
		LoanDetails loan = new LoanDetails();
		List<PaymentSchedule> data = schedule.findAll();
		
		for(PaymentSchedule i:data) 
		{
			 if((i.getPaymentDate().compareTo(LocalDate.now())<=0 ) &&(i.getPaymentStatus().equals("Projected")))
			{
				i.setPaymentStatus(PaymentStatus.AwaitingPayment);
				schedule.save(i);
			}
		}
		
		log.info("Payment Schedule updated at: "+LocalDateTime.now());	
	}
//Updating the payment schedule (to paid payment)	
	public String UpdatingPayment(String status,int id) 
	{
		PaymentSchedule data = schedule.getById(id);
			if(status.equalsIgnoreCase("Paid"))
			{
				data.setPaymentStatus(PaymentStatus.Paid);
				schedule.save(data);
			}
		return "paid";
	}
	
//Display all loans for customers
	public List<LoanDetails> DisplayLoan()
	{
		List<LoanDetails> tempLoanDetails =newLoan.findAll();
		return tempLoanDetails;
	}
//Display all the payment schedule of all the customers
	public List<PaymentSchedule> DisplaySchedule() 
	{
		List<PaymentSchedule> data = schedule.findAll();
		return data; 
	}
	
//Display all the payment schedule of each customer
	public List<PaymentSchedule> displayEachSchedule(String name) throws UserNotFoundException
	{
		List<PaymentSchedule> data = schedule.findByCustomerName(name);
		return data;
	}
	
}
