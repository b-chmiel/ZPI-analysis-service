package com.zpi.api.analysis.request;

import com.zpi.domain.analysis.request.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String login;

    public User toDomain() {
        return new User(login);
    }
}
