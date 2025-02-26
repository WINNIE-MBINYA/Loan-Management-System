package com.loanmanagement.Dto;

import com.loanmanagement.Entity.RepaymentFrequency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LoanRequestDTO {
    private Long customerId;
    private BigDecimal principalAmount;
    private BigDecimal interestRate;
    private Integer repaymentPeriod;
    private RepaymentFrequency repaymentFrequency;
}
