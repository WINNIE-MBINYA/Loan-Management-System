package com.loanmanagement.Service;

import com.loanmanagement.Dto.StatisticsDTO;
import com.loanmanagement.Repository.LoanRepository;
import com.loanmanagement.Repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final LoanRepository loanRepository;
    private final PaymentRepository paymentRepository;

    public StatisticsDTO getLoanStatistics() {
        double totalDisbursed = loanRepository.sumTotalPrincipalAmount();
        double totalRepaid = paymentRepository.sumTotalPaymentAmount();
        double outstandingBalance = totalDisbursed - totalRepaid;

        return new StatisticsDTO(totalDisbursed, totalRepaid, outstandingBalance);
    }
}
