package com.loanmanagement.Service;

import com.loanmanagement.Dto.LoanRequestDTO;
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
    private final CustomerRepository customerRepository;
    private final LoanMapper loanMapper;

    /**
     * Issues a new loan to a customer.
     */
    @Transactional
    public LoanResponseDTO issueLoan(LoanRequestDTO loanRequest) {
        // Retrieve customer
        Customer customer = customerRepository.findById(loanRequest.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Prevent multiple active loans if required
        boolean hasActiveLoan = loanRepository.findByCustomer(customer)
                .stream()
                .anyMatch(loan -> loan.getStatus() == LoanStatus.ACTIVE);

        if (hasActiveLoan) {
            throw new RuntimeException("Customer already has an active loan");
        }

        // Create loan entity
        Loan loan = Loan.builder()
                .customer(customer)
                .principalAmount(loanRequest.getPrincipalAmount())
                .interestRate(loanRequest.getInterestRate())
                .repaymentPeriod(loanRequest.getRepaymentPeriod())
                .repaymentFrequency(loanRequest.getRepaymentFrequency())
                .loanIssuedDate(LocalDate.now())
                .status(LoanStatus.ACTIVE)
                .build();

        loanRepository.save(loan);

        return loanMapper.toDto(loan); // Use LoanMapper for conversion
    }

    /**
     * Retrieves all loans for a specific customer.
     */
    public List<LoanResponseDTO> getLoansByCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return loanRepository.findByCustomer(customer)
                .stream()
                .map(loanMapper::toDto)  // ✅ Use LoanMapper
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all loans with a specific status.
     */
    public List<LoanResponseDTO> getLoansByStatus(LoanStatus status) {
        return loanRepository.findByStatus(status)
                .stream()
                .map(loanMapper::toDto)  // Use LoanMapper
                .collect(Collectors.toList());
    }
}
