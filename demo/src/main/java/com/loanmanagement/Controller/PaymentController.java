package com.loanmanagement.Controller;

import com.loanmanagement.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Endpoint to process a loan repayment.
     */
    @PostMapping("/{loanId}/repay")
    public ResponseEntity<String> makePayment(
            @PathVariable Long loanId,
            @RequestParam BigDecimal amountPaid) {

        paymentService.processPayment(loanId, amountPaid);
        return ResponseEntity.ok("Payment successfully recorded.");
    }
}
