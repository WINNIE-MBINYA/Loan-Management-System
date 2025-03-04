package com.loanmanagement.Controller;

import com.loanmanagement.Dto.RepaymentScheduleDTO;
import com.loanmanagement.Entity.RepaymentSchedule;
import com.loanmanagement.Service.RepaymentScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repayments")
@RequiredArgsConstructor
public class RepaymentScheduleController {

    private final RepaymentScheduleService repaymentScheduleService;

    /**
     * Get repayment schedule by Loan ID
     */
    @GetMapping("/getRepaymentSchedule/{loanId}")
    public ResponseEntity<List<RepaymentSchedule>> getRepaymentSchedule(@PathVariable Long loanId) {
        List<RepaymentSchedule> schedule = repaymentScheduleService.getRepaymentScheduleByLoan(loanId);
        return ResponseEntity.ok(schedule);
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
    public ResponseEntity<List<RepaymentSchedule>> getUpcomingRepayments(@PathVariable Long loanId) {
        List<RepaymentSchedule> upcomingPayments = repaymentScheduleService.getUpcomingRepayments(loanId);
        return ResponseEntity.ok(upcomingPayments);
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
