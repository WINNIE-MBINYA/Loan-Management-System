package com.loanmanagement.Controller;

import com.loanmanagement.Dto.StatisticsDTO;
import com.loanmanagement.Service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/summary")
    public ResponseEntity<StatisticsDTO> getStatisticsSummary() {
        StatisticsDTO statistics = statisticsService.getLoanStatistics();
        return ResponseEntity.ok(statistics);
    }
}
