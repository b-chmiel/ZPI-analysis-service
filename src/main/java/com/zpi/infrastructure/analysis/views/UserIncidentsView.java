package com.zpi.infrastructure.analysis.views;

import lombok.Getter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Entity
@Immutable
@Subselect("""
        select user_info.id as id, count(*) as count
        from incident, request_metadata, user_info
        where incident.request_id = request_metadata.id
        and request_metadata.user_id = user_info.id
        group by user_info.id
        """
)
public class UserIncidentsView {
    @Id
    private Long id;

    private int count;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserIncidentsView that = (UserIncidentsView) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
