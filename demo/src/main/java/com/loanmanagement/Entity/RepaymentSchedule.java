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
@Builder(toBuilder = true) // Allows modifying existing objects using toBuilder()
public class RepaymentSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;

    @Column(nullable = false)
    private LocalDate dueDate; // When payment is due

    @Column(nullable = false)
    private BigDecimal amountDue; // Installment amount (renamed from installmentAmount)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status; // Tracks payment status (Pending, Paid, etc.)
}
