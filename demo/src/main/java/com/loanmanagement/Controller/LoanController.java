package com.loanmanagement.Controller;

import com.loanmanagement.Dto.LoanRequestDTO;
import com.loanmanagement.Dto.LoanResponseDTO;
import com.loanmanagement.Entity.LoanStatus;
import com.loanmanagement.Service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    /**
     * Endpoint to issue a new loan.
     */
    @PostMapping("/issueLoan")
    public ResponseEntity<LoanResponseDTO> issueLoan(@RequestBody LoanRequestDTO loanRequest) {
        LoanResponseDTO loanResponse = loanService.issueLoan(loanRequest);
        return ResponseEntity.ok(loanResponse);
    }

    /**
     * Get all loans for a specific customer.
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<LoanResponseDTO>> getLoansByCustomer(@PathVariable Long customerId) {
        List<LoanResponseDTO> loans = loanService.getLoansByCustomer(customerId);
        return ResponseEntity.ok(loans);
    }

    /**
     * Get all loans with a specific status.
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<LoanResponseDTO>> getLoansByStatus(@PathVariable LoanStatus status) {
        List<LoanResponseDTO> loans = loanService.getLoansByStatus(status);
        return ResponseEntity.ok(loans);
    }
}
