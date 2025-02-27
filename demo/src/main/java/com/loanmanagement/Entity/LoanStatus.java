package com.loanmanagement.Entity;

public enum LoanStatus {
    PENDING,    // Loan application submitted, awaiting review
    APPROVED,   // Loan has been approved, awaiting disbursement
    ACTIVE,     // Loan is disbursed and being repaid
    PAID,       // Loan has been fully repaid
    DEFAULTED,  // Loan payments were missed and the loan defaulted
    REJECTED    // Loan application was denied
}
