package com.loanmanagement.Dto;

import com.loanmanagement.Entity.Loan;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Component
public class LoanMapper {

    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public LoanResponseDTO toDto(Loan loan) {
        return LoanResponseDTO.builder()
                .id(loan.getId())
                .customerId(loan.getCustomer().getId())
                .principalAmount(loan.getPrincipalAmount())
                .interestRate(formatInterestRate(loan.getInterestRate())) // Format as percentage
                .repaymentPeriodMonths(loan.getRepaymentPeriod())
                .repaymentFrequency(loan.getRepaymentFrequency())
                .startDate(loan.getLoanIssuedDate())
                .endDate(loan.getLoanIssuedDate().plusMonths(loan.getRepaymentPeriod())) // Calculate end date
                .status(loan.getStatus())
                .remainingAmount(loan.getPrincipalAmount()) // Placeholder logic for remaining amount
                .totalRepayableAmount(calculateTotalRepayableAmount(loan.getPrincipalAmount(), loan.getInterestRate()))
                .build();
    }

    private String formatInterestRate(BigDecimal interestRate) {
        return decimalFormat.format(interestRate) + "%"; // Convert to percentage string
    }

    private BigDecimal calculateTotalRepayableAmount(BigDecimal principal, BigDecimal interestRate) {
        BigDecimal interestAmount = principal.multiply(interestRate).divide(BigDecimal.valueOf(100)); // Convert to percentage
        return principal.add(interestAmount);
    }
}
