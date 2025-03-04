package com.loanmanagement.Service;

import com.loanmanagement.Dto.RepaymentScheduleDTO;
import com.loanmanagement.Entity.Loan;
import com.loanmanagement.Entity.RepaymentFrequency;
import com.loanmanagement.Entity.RepaymentSchedule;
import com.loanmanagement.Entity.PaymentStatus;
import com.loanmanagement.Repository.LoanRepository;
import com.loanmanagement.Repository.RepaymentScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepaymentScheduleService {

    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private final LoanRepository loanRepository;

    /**
     * Get repayment schedule by Loan ID
     */
    public List<RepaymentSchedule> getRepaymentScheduleByLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        return repaymentScheduleRepository.findByLoan(loan);
    }

    /**
     * Generate and save repayment schedule for a loan
     */
    @Transactional
    public List<RepaymentScheduleDTO> createScheduleForLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        List<RepaymentSchedule> scheduleList = generateRepaymentSchedule(loan);

        // Save generated schedule
        repaymentScheduleRepository.saveAll(scheduleList);

        // Convert Entity to DTO
        return scheduleList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert RepaymentSchedule entity to RepaymentScheduleDTO
     */
    private RepaymentScheduleDTO convertToDTO(RepaymentSchedule schedule) {
        return new RepaymentScheduleDTO(
                schedule.getId(),
                schedule.getLoan().getId(),
                schedule.getDueDate(),
                schedule.getAmountDue(),
                schedule.getStatus()
        );
    }

    /**
     * Generate repayment schedule for a given loan
     */
    private List<RepaymentSchedule> generateRepaymentSchedule(Loan loan) {
        List<RepaymentSchedule> scheduleList = new ArrayList<>();
        int numberOfInstallments = calculateInstallments(loan);
        BigDecimal installmentAmount = calculateInstallmentAmount(loan, numberOfInstallments);

        LocalDate dueDate = loan.getLoanIssuedDate();

        for (int i = 1; i <= numberOfInstallments; i++) {
            dueDate = getNextDueDate(dueDate, loan.getRepaymentFrequency());

            RepaymentSchedule schedule = RepaymentSchedule.builder()
                    .loan(loan)
                    .dueDate(dueDate)
                    .amountDue(installmentAmount)
                    .status(PaymentStatus.PENDING)
                    .build();

            scheduleList.add(schedule);
        }
        return scheduleList;
    }

    /**
     * Get upcoming repayments for a loan
     */
    public List<RepaymentSchedule> getUpcomingRepayments(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        return repaymentScheduleRepository.findByLoanAndStatus(loan, PaymentStatus.PENDING);
    }

    /**
     * Mark a repayment installment as paid
     */
    @Transactional
    public void markRepaymentAsPaid(Long repaymentId) {
        RepaymentSchedule schedule = repaymentScheduleRepository.findById(repaymentId)
                .orElseThrow(() -> new RuntimeException("Repayment schedule not found"));
        schedule.setStatus(PaymentStatus.PAID);
        repaymentScheduleRepository.save(schedule);
    }

    /**
     * Calculate number of installments based on repayment frequency
     */
    private int calculateInstallments(Loan loan) {
        return switch (loan.getRepaymentFrequency()) {
            case WEEKLY -> loan.getRepaymentPeriod() * 4;
            case MONTHLY -> loan.getRepaymentPeriod();
            case YEARLY -> Math.max(1, loan.getRepaymentPeriod() / 12);
            default -> throw new IllegalArgumentException("Invalid repayment frequency");
        };
    }

    /**
     * Calculate installment amount (principal divided equally)
     */
    private BigDecimal calculateInstallmentAmount(Loan loan, int numberOfInstallments) {
        return loan.getPrincipalAmount().divide(BigDecimal.valueOf(numberOfInstallments), 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Get next due date based on repayment frequency
     */
    private LocalDate getNextDueDate(LocalDate lastDate, RepaymentFrequency frequency) {
        return switch (frequency) {
            case WEEKLY -> lastDate.plusWeeks(1);
            case MONTHLY -> lastDate.plusMonths(1);
            case YEARLY -> lastDate.plusYears(1);
            default -> throw new IllegalArgumentException("Invalid repayment frequency");
        };
    }
}
