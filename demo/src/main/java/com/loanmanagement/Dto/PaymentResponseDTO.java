package com.loanmanagement.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private Long paymentId;
    private Long loanId;
    private Long repaymentScheduleId;
    private BigDecimal amountPaid;
    private LocalDate paymentDate;
}
