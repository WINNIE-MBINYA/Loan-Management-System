package com.loanmanagement.Controller;

import com.loanmanagement.Dto.LoanIssuanceDTO;
import com.loanmanagement.Dto.LoanResponseDTO;
import com.loanmanagement.Dto.LoanResponsesDto;
import com.loanmanagement.Entity.LoanStatus;
import com.loanmanagement.Service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    /**
     * Endpoint for ADMINS to issue loans.
     */
    @PostMapping("/issueLoan")
    public ResponseEntity<?> issueLoan(@RequestParam String email, @RequestBody LoanIssuanceDTO loanIssuanceDTO) {
        try {
            LoanIssuanceDTO loan = loanService.issueLoan(email, loanIssuanceDTO);
            return ResponseEntity.ok().body(new LoanResponsesDto<>("Loan issued successfully", HttpStatus.CREATED.value(), loan));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new LoanResponsesDto<>("Loan issuance failed", HttpStatus.BAD_REQUEST.value(), null));
        }
    }

    /**
     * Get all loans for a specific customer.
     */
    @GetMapping("/getLoans/{customerId}")
    public ResponseEntity<List<LoanResponseDTO>> getLoansByCustomer(@PathVariable Long customerId) {
        List<LoanResponseDTO> loans = loanService.getLoansByCustomer(customerId);
        return ResponseEntity.ok(loans);
    }

    /**
     * Get all loans with a specific status.
     */
    @GetMapping("/getLoansByStatus/{status}")
    public ResponseEntity<List<LoanResponseDTO>> getLoansByStatus(@PathVariable LoanStatus status) {
        List<LoanResponseDTO> loans = loanService.getLoansByStatus(status);
        return ResponseEntity.ok(loans);
    }

    /**
     * Get a single loan by ID (customer validation included)
     */
    @GetMapping("/getLoan/{loanId}/{customerId}")
    public ResponseEntity<LoanResponseDTO> getLoanById(@PathVariable Long loanId, @PathVariable Long customerId) {
        LoanResponseDTO loan = loanService.getLoanById(loanId, customerId);
        return ResponseEntity.ok(loan);
    }

    /**
     * Get all loans.
     */
    @GetMapping("/getAllLoans")
    public ResponseEntity<List<LoanResponseDTO>> getAllLoans() {
        List<LoanResponseDTO> loans = loanService.getAllLoans();
        return ResponseEntity.ok(loans);
    }

    /**
     * Update loan status (e.g., mark as PAID)
     */
    @PutMapping("/updateLoanStatus/{loanId}")
    public ResponseEntity<LoanResponseDTO> updateLoanStatus(@PathVariable Long loanId, @RequestParam LoanStatus status) {
        LoanResponseDTO updatedLoan = loanService.updateLoanStatus(loanId, status);
        return ResponseEntity.ok(updatedLoan);
    }

    /**
     * Edit loan details (Admin only)
     */
    @PutMapping("/editLoan/{loanId}")
    public ResponseEntity<?> editLoan(@PathVariable Long loanId, @RequestBody LoanIssuanceDTO updatedLoan) {
        try {
            LoanResponseDTO loan = loanService.editLoan(loanId, updatedLoan);
            return ResponseEntity.ok(new LoanResponsesDto<>("Loan updated successfully", HttpStatus.OK.value(), loan));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new LoanResponsesDto<>("Loan update failed", HttpStatus.BAD_REQUEST.value(), null));
        }
    }

    /**
     * Delete a loan by ID
     */
    @DeleteMapping("deleteLoan/{loanId}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long loanId) {
        loanService.deleteLoan(loanId);
        return ResponseEntity.noContent().build();
    }
}
