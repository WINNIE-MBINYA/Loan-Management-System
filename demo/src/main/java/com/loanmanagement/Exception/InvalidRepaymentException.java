package com.loanmanagement.Exception;

public class InvalidRepaymentException extends RuntimeException {
    public InvalidRepaymentException(String message) {
        super(message);
    }
}
