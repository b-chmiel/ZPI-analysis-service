package com.zpi.infrastructure.analysis;

import com.zpi.domain.analysis.twoFactor.RequestRepository;
import com.zpi.domain.common.AnalysisRequest;
import com.zpi.domain.common.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

import java.util.Optional;

@RequiredArgsConstructor
public class JpaRequestRepositoryImpl implements RequestRepository {
    private final JpaUserRepo userRepo;
    private final JpaIpInfoRepo ipInfoRepo;
    private final JpaDeviceInfoRepo deviceInfoRepo;
    private final JpaRequestRepo requestRepo;

    @Override
    public void save(AnalysisRequest request) {
        var userTuple = userRepo.findByUsername(request.user().username());
        if (userTuple.isEmpty()) {
            userTuple = Optional.of(userRepo.save(new UserTuple(request.user())));
        }

        var ipInfoTuple = ipInfoRepo.findByFingerprint(request.ipInfo().getFingerprint());
        if (ipInfoTuple.isEmpty()) {
            ipInfoTuple = Optional.of(ipInfoRepo.save(new IpInfoTuple(request.ipInfo())));
        }

        var deviceInfo = deviceInfoRepo.findByFingerprint(request.deviceInfo().fingerprint());
        if (deviceInfo.isEmpty()) {
            deviceInfo = Optional.of(deviceInfoRepo.save(new DeviceInfoTuple(request.deviceInfo())));
        }

        var tuple = new RequestTuple(ipInfoTuple.get(), deviceInfo.get(), userTuple.get(), request.date());
        userTuple.get().getRequests().add(tuple);
        ipInfoTuple.get().getRequests().add(tuple);
        deviceInfo.get().getRequests().add(tuple);
        userRepo.save(userTuple.get());
    }

    @Override
    public Optional<AnalysisRequest> retrieveLastEntry(User user) {
        var userTuple = userRepo.findByUsername(user.username());

        if (userTuple.isEmpty()) {
            return Optional.empty();
        }

        return requestRepo.findFirstByUserOrderByIdDesc(userTuple.get())
                .map(RequestTuple::toDomain).stream().findFirst();
    }

    public interface JpaUserRepo extends JpaRepository<UserTuple, Long> {
        Optional<UserTuple> findByUsername(String username);
    }

    public interface JpaIpInfoRepo extends JpaRepository<IpInfoTuple, Long> {
        Optional<IpInfoTuple> findByFingerprint(String fingerprint);
    }

    public interface JpaDeviceInfoRepo extends JpaRepository<DeviceInfoTuple, Long> {
        Optional<DeviceInfoTuple> findByFingerprint(String fingerprint);
    }

    public interface JpaRequestRepo extends JpaRepository<RequestTuple, Long> {
        Streamable<RequestTuple> findFirstByUserOrderByIdDesc(UserTuple user);
    }
}
