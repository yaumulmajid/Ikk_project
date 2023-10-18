package id.go.lan.ikk.module.dashboard.service;

import id.go.lan.ikk.module.dashboard.model.DashboardBarChartDto;
import id.go.lan.ikk.module.dashboard.model.DashboardRadarChartDto;

public interface DashboardChartService {
    DashboardBarChartDto getTopTenAgencies(String agencyCategory);
    DashboardRadarChartDto getAgencyDetailScore(Long agencyId);
}
