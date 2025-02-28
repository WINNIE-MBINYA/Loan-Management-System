package com.loanmanagement.Repository;

import com.loanmanagement.Entity.Loan;
import com.loanmanagement.Entity.LoanStatus;
import com.loanmanagement.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    // Find all loans for a specific customer
    List<Loan> findByCustomer(Customer customer);

    // Find loans by status
    List<Loan> findByStatus(LoanStatus status);

    // Find a specific loan by ID and ensure it belongs to a customer
    Optional<Loan> findByIdAndCustomer(Long loanId, Customer customer);

    // Calculate total amount disbursed (sum of all loan amounts)
    @Query("SELECT COALESCE(SUM(l.principalAmount), 0) FROM Loan l")
    double sumTotalPrincipalAmount();
}
