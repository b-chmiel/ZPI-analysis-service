package com.zpi.api.failedLogin;

import com.zpi.api.common.AnalysisRequestDTO;
import com.zpi.domain.failedLogin.FailedLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authserver/login-fail")
public class FailedLoginController {
    private final FailedLoginService service;

    @PostMapping
    public ResponseEntity<?> loginFail(@RequestBody AnalysisRequestDTO request) {
        service.report(request.toDomain());
        return ResponseEntity.ok(null);
    }
}
