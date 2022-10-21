package com.project.loanApplication.controller;

import java.util.List;

import javax.validation.Valid;

import com.project.loanApplication.Exception.UserNotFoundException;
import com.project.loanApplication.dto.NewLoanFormDetails;
import com.project.loanApplication.entity.LoanDetails;
import com.project.loanApplication.entity.PaymentSchedule;
import com.project.loanApplication.service.NewLoanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewLoanController 
{
	@Autowired
	NewLoanService service;
	
	@CrossOrigin
	@PostMapping("newloan")
	public String newLoan(@RequestBody @Valid NewLoanFormDetails details)
	{
		return service.newLoan(details) ;
	}
	
	@CrossOrigin
	@GetMapping("displayloan")
	public List<LoanDetails> DisplayLoan()
	{
		return service.DisplayLoan();
	}
	
	@CrossOrigin
	@GetMapping("displayschedule")
	public List<PaymentSchedule> DisplaySchedule()
	{
		return service.DisplaySchedule();
	}
	
	@CrossOrigin
	@GetMapping("updating/{status}/{id}")
	public String updatingSchedule(@PathVariable String status, @PathVariable int id)
	{
		return service.UpdatingPayment(status,id);
	}
	
	@CrossOrigin
	@GetMapping("displayEachSchedule/{name}")
	public List<PaymentSchedule> displayEachSchedule(@PathVariable String name) throws UserNotFoundException
	{
		return service.displayEachSchedule(name);
	}
}
