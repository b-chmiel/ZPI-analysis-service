package com.zpi.api.report;

import com.zpi.domain.common.User;
import com.zpi.domain.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ReportController {
    private final ReportService service;

    @GetMapping("/incidents")
    public ResponseEntity<?> incidents(@Valid @RequestParam String username) {
        var result = service.incidents(new User(username, null));
        return ResponseEntity.ok(result.stream().map(IncidentsResponseDTO::new));
    }
}
