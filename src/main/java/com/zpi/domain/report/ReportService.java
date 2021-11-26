package com.zpi.domain.report;

import com.zpi.domain.common.User;

import java.util.List;

public interface ReportService {
    List<RequestIncident> incidents(User user);
}
