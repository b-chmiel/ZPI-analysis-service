package com.zpi.infrastructure.analysis;

import com.zpi.domain.analysis.request.IpInfo;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "IP_INFO")
class IpInfoTuple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @Column(unique = true, nullable = false)
    private String fingerprint;
    private String city;
    private String continentCode;
    private String continentName;
    private String countryCode;
    private String countryName;
    private String ipAddress;
    private String stateProv;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @ToString.Exclude
    private Set<RequestTuple> requests;

    IpInfoTuple(IpInfo info) {
        this.fingerprint = info.getFingerprint();
        this.city = info.getCity();
        this.continentCode = info.getContinentCode();
        this.continentName = info.getContinentName();
        this.countryCode = info.getCountryCode();
        this.countryName = info.getCountryName();
        this.ipAddress = info.getIpAddress();
        this.stateProv = info.getStateProv();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        IpInfoTuple that = (IpInfoTuple) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public IpInfo toDomain() {
        return new IpInfo(city, continentCode, continentName, countryCode, countryName, ipAddress, stateProv);
    }
}