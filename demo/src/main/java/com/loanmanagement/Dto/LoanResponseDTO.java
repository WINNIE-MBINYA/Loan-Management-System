package com.loanmanagement.Dto;

import com.loanmanagement.Entity.LoanStatus;
import com.loanmanagement.Entity.RepaymentFrequency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class LoanResponseDTO {
    private Long id;
    private Long customerId;
    private BigDecimal principalAmount;
    private BigDecimal interestRate;
    private Integer repaymentPeriod;
    private RepaymentFrequency repaymentFrequency;
    private LocalDate loanIssuedDate;
    private LoanStatus status;
}
