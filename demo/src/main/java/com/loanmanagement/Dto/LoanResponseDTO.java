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
    private String interestRate;
    private Integer repaymentPeriodMonths;
    private RepaymentFrequency repaymentFrequency;
    private LocalDate startDate;
    private LocalDate endDate;
    private LoanStatus status;
    private BigDecimal remainingAmount;
    private BigDecimal totalRepayableAmount;
}
