package com.zpi.infrastructure.analysis;

import com.zpi.domain.common.AnalysisRequest;
import com.zpi.domain.analysis.twoFactor.Incident;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "INCIDENT")
class IncidentTuple {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private RequestTuple request;

    @Enumerated
    private IncidentTypeTuple type;

    @Enumerated
    private IncidentSeverityTuple severity;

    IncidentTuple(Incident incident, RequestTuple request) {
        this.request = request;
        this.type = IncidentTypeTuple.from(incident.type());
        this.severity = IncidentSeverityTuple.from(incident.severity());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        IncidentTuple that = (IncidentTuple) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
