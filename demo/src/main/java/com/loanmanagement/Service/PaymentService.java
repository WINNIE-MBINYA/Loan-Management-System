package com.loanmanagement.Service;

import com.loanmanagement.Entity.*;
import com.loanmanagement.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final LoanRepository loanRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;

    /**
     * Processes a loan repayment.
     */
    @Transactional
    public void processPayment(Long loanId, BigDecimal amountPaid) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        List<RepaymentSchedule> pendingRepayments = repaymentScheduleRepository.findByLoanAndStatus(loan, PaymentStatus.PENDING);

        BigDecimal remainingAmount = amountPaid;

        for (RepaymentSchedule schedule : pendingRepayments) {
            if (remainingAmount.compareTo(BigDecimal.ZERO) <= 0) break;

            BigDecimal dueAmount = schedule.getAmountDue();

            if (remainingAmount.compareTo(dueAmount) >= 0) {
                schedule.setStatus(PaymentStatus.PAID);
                remainingAmount = remainingAmount.subtract(dueAmount);
            } else {
                schedule.setAmountDue(dueAmount.subtract(remainingAmount));
                remainingAmount = BigDecimal.ZERO;
            }
            repaymentScheduleRepository.save(schedule);
        }

        // Record payment
        Payment payment = Payment.builder()
                .loan(loan)
                .amountPaid(amountPaid)
                .build();
        paymentRepository.save(payment);

        // Check if loan is fully repaid
        if (repaymentScheduleRepository.findByLoanAndStatus(loan, PaymentStatus.PENDING).isEmpty()) {
            loan.setStatus(LoanStatus.PAID);
            loanRepository.save(loan);
        }
    }
}
