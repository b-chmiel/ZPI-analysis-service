package com.zpi.infrastructure.analysis;

import com.zpi.domain.analysis.twoFactor.RequestRepository;
import com.zpi.domain.common.AnalysisRequest;
import com.zpi.domain.common.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class JpaRequestRepositoryImpl implements RequestRepository {
    private final JpaUserRepo userRepo;
    private final JpaIpInfoRepo ipInfoRepo;
    private final JpaDeviceInfoRepo deviceInfoRepo;

    @Override
    public void save(AnalysisRequest request) {
        var userTuple = userRepo.findAll().stream().filter(u -> u.toDomain().equals(request.user())).findFirst();
        if (userTuple.isEmpty()) {
            userTuple = Optional.of(userRepo.save(new UserTuple(request.user())));
        }

        var ipInfoTuple = ipInfoRepo.findAll().stream().filter(u -> u.toDomain().equals(request.ipInfo())).findFirst();
        if (ipInfoTuple.isEmpty()) {
            ipInfoTuple = Optional.of(ipInfoRepo.save(new IpInfoTuple(request.ipInfo())));
        }

        var deviceInfo = deviceInfoRepo.findAll().stream().filter(u -> u.toDomain().equals(request.deviceInfo())).findFirst();
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
        var result = userRepo.findAll().stream().filter(u -> u.toDomain().equals(user)).findFirst();
        if (result.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(RequestTuple.toDomain(result.get().getRequests().get(result.get().getRequests().size() - 1)));
    }

    public interface JpaUserRepo extends JpaRepository<UserTuple, Long> {
    }

    public interface JpaIpInfoRepo extends JpaRepository<IpInfoTuple, Long> {
    }

    public interface JpaDeviceInfoRepo extends JpaRepository<DeviceInfoTuple, Long> {
    }
}
