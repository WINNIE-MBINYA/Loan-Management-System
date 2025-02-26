package com.loanmanagement.Dto;

import com.loanmanagement.Entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentScheduleDTO {
    private Long scheduleId;
    private Long loanId;
    private LocalDate dueDate;
    private BigDecimal amountDue;
    private PaymentStatus status;
}
