package com.zpi.api.analysis;

import com.zpi.domain.analysis.AuditUser;
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
