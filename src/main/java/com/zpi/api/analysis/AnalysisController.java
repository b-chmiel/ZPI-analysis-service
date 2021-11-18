package com.zpi.api.analysis;

import com.zpi.api.analysis.dto.AnalysisResponseDTO;
import com.zpi.api.common.AnalysisRequestDTO;
import com.zpi.domain.analysis.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authserver/analyse")
public class AnalysisController {
    private final AnalysisService service;

    @PostMapping
    public ResponseEntity<?> analyse(@RequestBody AnalysisRequestDTO request) {
        var result = service.analyse(request.toDomain());
        return ResponseEntity.ok(new AnalysisResponseDTO(result));
    }
}
