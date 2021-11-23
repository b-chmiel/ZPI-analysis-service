package com.zpi.api.analysis;

import com.zpi.domain.analysis.AnalysisService;
import com.zpi.domain.common.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authserver/lockout")
public class LockoutController {
    private final AnalysisService service;

    @GetMapping
    public ResponseEntity<?> lockoutInfo(@Valid @RequestParam String username) {
        var result = service.lockoutInfo(new User(username));
        return ResponseEntity.ok(new LockoutDTO(result));
    }
}
