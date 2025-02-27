package com.loanmanagement.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private BigDecimal principalAmount;  // The loan amount

    @Column(nullable = false)
    private BigDecimal interestRate;  // Interest rate (e.g., 5% stored as 5.0)

    @Column(nullable = false)
    private Integer repaymentPeriod;  // Repayment duration in months

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RepaymentFrequency repaymentFrequency;  // Weekly, Monthly, Yearly

    @Column(nullable = false)
    private LocalDate loanIssuedDate;  // Date when the loan was issued

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status; // Active, Paid, Defaulted, Approved, Pending, Rejected
}
