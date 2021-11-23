package com.zpi.api.analysis;

import com.zpi.api.common.AnalysisRequestDTO;
import com.zpi.domain.analysis.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authserver/analyse")
public class AnalysisController {
    private final AnalysisService service;

    @PostMapping
    public ResponseEntity<?> analyse(@Valid @RequestBody AnalysisRequestDTO request) {
        var result = service.analyse(request.toDomain());
        return ResponseEntity.ok(new AnalysisResponseDTO(result));
    }
}
