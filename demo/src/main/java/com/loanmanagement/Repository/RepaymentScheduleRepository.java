package com.loanmanagement.Repository;

import com.loanmanagement.Entity.PaymentStatus;
import com.loanmanagement.Entity.RepaymentSchedule;
import com.loanmanagement.Entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDate;

@Repository
public interface RepaymentScheduleRepository extends JpaRepository<RepaymentSchedule, Long> {

    // Get the repayment schedule for a specific loan
    List<RepaymentSchedule> findByLoan(Loan loan);

    List<RepaymentSchedule> findByLoanId(Long loanId);

    List<RepaymentSchedule> findByLoanIdAndDueDateAfterAndStatus(Long loanId, LocalDate today, String status);

    List<RepaymentSchedule> findByLoanAndStatus(Loan loan, PaymentStatus paymentStatus);
}
