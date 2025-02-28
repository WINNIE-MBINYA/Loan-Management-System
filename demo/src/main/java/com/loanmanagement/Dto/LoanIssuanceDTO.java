package com.loanmanagement.Dto;

import com.loanmanagement.Entity.RepaymentFrequency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class LoanIssuanceDTO {
    private Long customerId;
    private BigDecimal principalAmount;
    private BigDecimal interestRate;
    private int repaymentPeriod;
    private RepaymentFrequency repaymentFrequency;
    private LocalDate loanIssuedDate;;
}
