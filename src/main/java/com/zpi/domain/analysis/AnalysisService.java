package com.zpi.domain.analysis;

import com.zpi.domain.analysis.response.AnalysisResponse;
import com.zpi.domain.common.AnalysisRequest;

public interface AnalysisService {
    AnalysisResponse analyse(AnalysisRequest request);
}
