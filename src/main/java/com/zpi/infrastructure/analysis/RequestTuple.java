package com.zpi.infrastructure.analysis;

import com.zpi.domain.common.AnalysisRequest;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Transactional
@Table(name = "REQUEST_METADATA")
class RequestTuple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private IpInfoTuple ipInfo;
    @ManyToOne(cascade = CascadeType.ALL)
    private DeviceInfoTuple deviceInfo;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private UserTuple user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;

    RequestTuple(IpInfoTuple ipInfo, DeviceInfoTuple deviceInfo, UserTuple user, Date date) {
        this.ipInfo = ipInfo;
        this.deviceInfo = deviceInfo;
        this.user = user;
        this.datetime = date;
    }

    public static AnalysisRequest toDomain(RequestTuple requestTuple) {
        var date = requestTuple.getDatetime();
        var ipInfo = requestTuple.getIpInfo().toDomain();
        var deviceInfo = requestTuple.getDeviceInfo().toDomain();
        var user = requestTuple.getUser().toDomain();
        return new AnalysisRequest(date, ipInfo, deviceInfo, user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RequestTuple that = (RequestTuple) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

