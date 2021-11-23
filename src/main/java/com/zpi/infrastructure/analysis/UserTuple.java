package com.zpi.infrastructure.analysis;

import com.zpi.domain.common.User;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "USER_INFO")
class UserTuple {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<RequestTuple> requests = new ArrayList<>();

    private int failedAttempts = 0;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private Date lockoutTill = null;

    UserTuple(User user) {
        this.username = user.username();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserTuple userTuple = (UserTuple) o;
        return username != null && Objects.equals(username, userTuple.username);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public User toDomain() {
        return new User(username);
    }
}