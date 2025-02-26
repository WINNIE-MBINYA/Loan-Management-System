package com.loanmanagement.Repository;

import com.loanmanagement.Entity.Payment;
import com.loanmanagement.Entity.Loan;
import com.loanmanagement.Entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Find payments by loan
    List<Payment> findByLoan(Loan loan);

    // Find payments by status (e.g., PENDING, PAID, LATE, etc.)
    List<Payment> findByStatus(PaymentStatus status);

    // Retrieve payments for a loan based on status
    List<Payment> findByLoanAndStatus(Loan loan, PaymentStatus status);

    // Sum of all payments made
    @Query("SELECT COALESCE(SUM(p.amountPaid), 0) FROM Payment p")
    double sumTotalPaymentAmount();
}
