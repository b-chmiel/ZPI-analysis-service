package com.zpi.api.analysis.request;

import com.zpi.domain.analysis.request.AuditUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuditUserDTO {
    private String login;

    public AuditUser toDomain() {
        return new AuditUser(login);
    }
}
