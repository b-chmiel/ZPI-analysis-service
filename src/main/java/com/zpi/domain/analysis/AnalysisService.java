package com.zpi.domain.analysis;

import com.zpi.domain.analysis.request.AnalysisRequest;
import com.zpi.domain.analysis.response.AnalysisResponse;

public interface AnalysisService {
    AnalysisResponse analyse(AnalysisRequest request);
}
