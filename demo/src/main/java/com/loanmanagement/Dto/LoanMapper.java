package com.loanmanagement.Dto;

import com.loanmanagement.Entity.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    public LoanResponseDTO toDto(Loan loan) {
        return LoanResponseDTO.builder()
                .id(loan.getId())
                .customerId(loan.getCustomer().getId())
                .principalAmount(loan.getPrincipalAmount())
                .interestRate(loan.getInterestRate())
                .repaymentPeriod(loan.getRepaymentPeriod())
                .repaymentFrequency(loan.getRepaymentFrequency())
                .loanIssuedDate(loan.getLoanIssuedDate())
                .status(loan.getStatus())
                .build();
    }
}
