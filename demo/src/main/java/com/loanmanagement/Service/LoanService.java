package com.loanmanagement.Service;

import com.loanmanagement.Dto.LoanIssuanceDTO;
import com.loanmanagement.Dto.LoanResponseDTO;
import com.loanmanagement.Entity.Customer;
import com.loanmanagement.Entity.Loan;
import com.loanmanagement.Entity.LoanStatus;
import com.loanmanagement.Repository.CustomerRepository;
import com.loanmanagement.Repository.LoanRepository;
import com.loanmanagement.Dto.LoanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private  LoanMapper loanMapper;

    /**
     * Allows an ADMIN to issue a loan.
     */
    public LoanIssuanceDTO issueLoan(LoanIssuanceDTO loanIssuanceDTO) {
        // Retrieve customer
        Customer customer = customerRepository.findById(loanIssuanceDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Convert DTO to Entity
        Loan loan = new Loan();
        loan.setCustomer(customer);
        loan.setPrincipalAmount(loanIssuanceDTO.getPrincipalAmount());
        loan.setInterestRate(loanIssuanceDTO.getInterestRate());
        loan.setRepaymentPeriod(loanIssuanceDTO.getRepaymentPeriod());
        loan.setRepaymentFrequency(loanIssuanceDTO.getRepaymentFrequency());
        loan.setLoanIssuedDate(LocalDate.now());

        // Save entity
        loan = loanRepository.save(loan);

        // Convert Entity back to DTO to return
        LoanIssuanceDTO responseDTO = new LoanIssuanceDTO();
        responseDTO.setCustomerId(loan.getCustomer().getId());
        responseDTO.setPrincipalAmount(loan.getPrincipalAmount());
        responseDTO.setInterestRate(loan.getInterestRate());
        responseDTO.setRepaymentPeriod(loan.getRepaymentPeriod());
        responseDTO.setRepaymentFrequency(loan.getRepaymentFrequency());
        responseDTO.setLoanIssuedDate(loan.getLoanIssuedDate());

        return responseDTO;
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

    // Update a loan’s status (e.g., mark it as PAID)
    @Transactional
    public LoanResponseDTO updateLoanStatus(Long loanId, LoanStatus newStatus) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setStatus(newStatus);
        loanRepository.save(loan);

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
