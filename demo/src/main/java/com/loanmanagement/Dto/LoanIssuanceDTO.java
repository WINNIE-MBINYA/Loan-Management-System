package com.loanmanagement.Dto;

import com.loanmanagement.Entity.RepaymentFrequency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor  // Ensures there's a no-args constructor for frameworks like Jackson
@AllArgsConstructor // Adds a constructor that takes all fields as parameters
public class LoanIssuanceDTO {
    private BigDecimal principalAmount;
    private BigDecimal interestRate;
    private int repaymentPeriod;
    private RepaymentFrequency repaymentFrequency;
    private LocalDate loanIssuedDate;
}
