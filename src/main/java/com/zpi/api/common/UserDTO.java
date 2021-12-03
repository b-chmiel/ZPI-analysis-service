package com.zpi.api.common;

import com.zpi.domain.common.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String login;

    public User toDomain() {
        return new User(login, null);
    }
}
