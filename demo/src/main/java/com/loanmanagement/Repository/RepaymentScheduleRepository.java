package com.loanmanagement.Repository;

import com.loanmanagement.Entity.RepaymentSchedule;
import com.loanmanagement.Entity.Loan;
import com.loanmanagement.Entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RepaymentScheduleRepository extends JpaRepository<RepaymentSchedule, Long> {
    List<RepaymentSchedule> findByLoan(Loan loan);
    List<RepaymentSchedule> findByLoanAndStatus(Loan loan, PaymentStatus status);
}
