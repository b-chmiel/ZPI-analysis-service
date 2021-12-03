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
                select ip_info.country_name as country, count(*) as count
                from incident, request_metadata, ip_info
                where incident.request_id = request_metadata.id
                and request_metadata.ip_info_id = ip_info.id
                group by ip_info.country_name
        """
)
class CountryIncidentsView {
    @Id
    private String country;

    private int count;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CountryIncidentsView that = (CountryIncidentsView) o;
        return country != null && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
