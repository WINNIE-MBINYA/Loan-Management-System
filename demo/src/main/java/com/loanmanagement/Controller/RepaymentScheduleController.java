package com.loanmanagement.Controller;

import com.loanmanagement.Dto.RepaymentScheduleDTO;
import com.loanmanagement.Entity.RepaymentSchedule;
import com.loanmanagement.Service.RepaymentScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/repayments")
@RequiredArgsConstructor
public class RepaymentScheduleController {

    private final RepaymentScheduleService repaymentScheduleService;

    /**
     * Get repayment schedule by Loan ID
     */
    @GetMapping("/getRepaymentSchedule/{loanId}")
    public ResponseEntity<List<RepaymentScheduleDTO>> getRepaymentSchedule(@PathVariable Long loanId) {
        List<RepaymentSchedule> schedules = repaymentScheduleService.getRepaymentScheduleByLoan(loanId);

        List<RepaymentScheduleDTO> scheduleDTOs = schedules.stream()
                .map(schedule -> new RepaymentScheduleDTO(
                        schedule.getId(),
                        schedule.getLoan().getId(),
                        schedule.getDueDate(),
                        schedule.getAmountDue(),
                        schedule.getStatus()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(scheduleDTOs);
    }

    /**
     * Generate a repayment schedule for a new loan
     */
    @PostMapping("/generateRepaymentSchedule/{loanId}")
    public ResponseEntity<List<RepaymentScheduleDTO>> generateRepaymentSchedule(@PathVariable Long loanId) {
        List<RepaymentScheduleDTO> schedule = repaymentScheduleService.createScheduleForLoan(loanId);
        return ResponseEntity.ok(schedule);
    }

    /**
     * Get upcoming repayments for a loan
     */
    @GetMapping("/getUpcomingRepayments/{loanId}")
    public ResponseEntity<List<RepaymentScheduleDTO>> getUpcomingRepayments(@PathVariable Long loanId) {
        List<RepaymentSchedule> upcomingPayments = repaymentScheduleService.getUpcomingRepayments(loanId);

        // Convert to DTOs
        List<RepaymentScheduleDTO> scheduleDTOs = upcomingPayments.stream()
                .map(schedule -> new RepaymentScheduleDTO(
                        schedule.getId(),
                        schedule.getLoan().getId(),
                        schedule.getDueDate(),
                        schedule.getAmountDue(),
                        schedule.getStatus()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(scheduleDTOs);
    }

    /**
     * Mark a repayment as paid
     */
    @PutMapping("/markRepaymentAsPaid/{repaymentId}")
    public ResponseEntity<String> markRepaymentAsPaid(@PathVariable Long repaymentId) {
        repaymentScheduleService.markRepaymentAsPaid(repaymentId);
        return ResponseEntity.ok("Repayment marked as paid");
    }
}
