package com.zpi.infrastructure.analysis;

import com.zpi.domain.analysis.request.AnalysisRequest;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REQUEST_METADATA")
class RequestTuple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private IpInfoTuple ipInfo;
    @ManyToOne(cascade = CascadeType.ALL)
    private DeviceInfoTuple deviceInfo;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    private UserTuple user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;

    RequestTuple(AnalysisRequest request) {
        ipInfo = new IpInfoTuple(request.ipInfo());
        deviceInfo = new DeviceInfoTuple(request.deviceInfo());
        user = new UserTuple(request.user());
        datetime = new Date();
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

    public static AnalysisRequest toDomain(RequestTuple requestTuple) {
        var ipInfo = requestTuple.getIpInfo().toDomain();
        var deviceInfo = requestTuple.getDeviceInfo().toDomain();
        var user = requestTuple.getUser().toDomain();
        return new AnalysisRequest(ipInfo, deviceInfo, user);
    }
}

