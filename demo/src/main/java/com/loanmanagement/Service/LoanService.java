package com.loanmanagement.Service;

import com.loanmanagement.Repository.RepaymentScheduleRepository;
import com.loanmanagement.Dto.LoanIssuanceDTO;
import com.loanmanagement.Dto.LoanResponseDTO;
import com.loanmanagement.Entity.Customer;
import com.loanmanagement.Entity.Loan;
import com.loanmanagement.Entity.LoanStatus;
import com.loanmanagement.Repository.CustomerRepository;
import com.loanmanagement.Repository.LoanRepository;
import com.loanmanagement.Dto.LoanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private final CustomerRepository customerRepository;
    private final LoanMapper loanMapper;

    /**
     * Allows an ADMIN to issue a loan.
     */
    public LoanIssuanceDTO issueLoan(String email, LoanIssuanceDTO loanIssuanceDTO) {
        // Retrieve customer by email
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer with email " + email + " not found"));

        // Convert DTO to Entity
        Loan loan = new Loan();
        loan.setCustomer(customer);
        loan.setPrincipalAmount(loanIssuanceDTO.getPrincipalAmount());
        loan.setInterestRate(loanIssuanceDTO.getInterestRate());
        loan.setRepaymentPeriod(loanIssuanceDTO.getRepaymentPeriod());
        loan.setRepaymentFrequency(loanIssuanceDTO.getRepaymentFrequency());
        loan.setLoanIssuedDate(LocalDate.now());
        loan.setStatus(LoanStatus.PENDING); // Default status

        // Save entity
        loan = loanRepository.save(loan);

        // Convert Entity back to DTO to return
        return new LoanIssuanceDTO(
                loan.getPrincipalAmount(),
                loan.getInterestRate(),
                loan.getRepaymentPeriod(),
                loan.getRepaymentFrequency(),
                loan.getLoanIssuedDate()
        );
    }

    /**
     * Retrieves all loans for a specific customer.
     */
    public List<LoanResponseDTO> getLoansByCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return loanRepository.findByCustomer(customer)
                .stream()
                .map(loanMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all loans with a specific status.
     */
    public List<LoanResponseDTO> getLoansByStatus(LoanStatus status) {
        return loanRepository.findByStatus(status)
                .stream()
                .map(loanMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get a specific loan for a customer
    public LoanResponseDTO getLoanById(Long loanId, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Loan loan = loanRepository.findByIdAndCustomer(loanId, customer)
                .orElseThrow(() -> new RuntimeException("Loan not found or does not belong to this customer"));

        return loanMapper.toDto(loan);
    }

    /**
     * Retrieves all loans in the system.
     */
    public List<LoanResponseDTO> getAllLoans() {
        List<Loan> loans = loanRepository.findAll();
        return loans.stream()
                .map(loanMapper::toDto)
                .collect(Collectors.toList());
    }

    // Update a loan’s status (e.g., mark it as PAID)
    @Transactional
    public LoanResponseDTO updateLoanStatus(Long loanId, LoanStatus newStatus) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setStatus(newStatus);
        loanRepository.save(loan);

        return loanMapper.toDto(loan);
    }

    @Transactional
    public LoanResponseDTO editLoan(Long loanId, LoanIssuanceDTO updatedLoan) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        // Update fields if provided (excluding customer & issued date)
        if (updatedLoan.getPrincipalAmount() != null) {
            loan.setPrincipalAmount(updatedLoan.getPrincipalAmount());
        }
        if (updatedLoan.getInterestRate() != null) {
            loan.setInterestRate(updatedLoan.getInterestRate());
        }
        if (updatedLoan.getRepaymentPeriod() != null) {
            loan.setRepaymentPeriod(updatedLoan.getRepaymentPeriod());
        }
        if (updatedLoan.getRepaymentFrequency() != null) {
            loan.setRepaymentFrequency(updatedLoan.getRepaymentFrequency());
        }

        loan = loanRepository.save(loan);
        return loanMapper.toDto(loan);
    }

    // Delete a loan by ID (if allowed)
    @Transactional
    public void deleteLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loanRepository.delete(loan);
    }
}
