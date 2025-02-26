package com.loanmanagement.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticsDTO {
    private double totalDisbursed;
    private double totalRepaid;
    private double outstandingBalance;
}
