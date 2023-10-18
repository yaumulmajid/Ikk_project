package id.go.lan.ikk.module.dashboard.service.implementation;

import id.go.lan.ikk.module.dashboard.model.*;
import id.go.lan.ikk.module.dashboard.service.DashboardChartService;
import id.go.lan.ikk.module.user.entity.AgencyEntity;
import id.go.lan.ikk.module.user.service.AgencyMasterService;
import id.go.lan.ikk.module.user.service.AgencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class DashboardChartServiceImpl implements DashboardChartService {

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private AgencyMasterService agencyMasterService;

    @Override
    public DashboardBarChartDto getTopTenAgencies(String agencyCategory) {
        log.info(String.format("Starting get top ten agencies service: Category - %s", agencyCategory));
        List<AgencyEntity> agencyEntityList = agencyMasterService.getAgencyByCategory(agencyCategory);

        List<DashboardAgencyScoreDto> dashboardAgencyScoreDtoList = new ArrayList<>();
        for (AgencyEntity agencyEntity : agencyEntityList) {
            log.info(String.format("Processing agency: %s - %s", agencyEntity.getName(), agencyEntity.getId()));
            DashboardAgencyScoreDto dashboardAgencyScoreDto = new DashboardAgencyScoreDto();
            dashboardAgencyScoreDto.setIdInstansi(agencyEntity.getId());
            dashboardAgencyScoreDto.setValue(agencyService.getAgencyScoreInPolicySampleByAgencyId(agencyEntity.getId()));
            dashboardAgencyScoreDtoList.add(dashboardAgencyScoreDto);
        }

        dashboardAgencyScoreDtoList.sort(Comparator.comparing(DashboardAgencyScoreDto::getValue));
        List<DashboardAgencyScoreDto> dashboardAgencyScoreTopTenList = new ArrayList<>(dashboardAgencyScoreDtoList.subList(dashboardAgencyScoreDtoList.size() - 10, dashboardAgencyScoreDtoList.size()));

        DashboardTopTenAgencyNameDto dashboardTopTenAgencyNameDto = new DashboardTopTenAgencyNameDto();
        List<String> topTenAgencyNameList = new ArrayList<>();
        for (DashboardAgencyScoreDto dashboardTopTenAgencyScoreDto : dashboardAgencyScoreTopTenList) {
            topTenAgencyNameList.add(
                    agencyMasterService.getAgencyById(dashboardTopTenAgencyScoreDto.getIdInstansi()).getName());
        }
        dashboardTopTenAgencyNameDto.setData(topTenAgencyNameList);

        DashboardTopTenAgencyScoreDto dashboardTopTenAgencyScoreDto = new DashboardTopTenAgencyScoreDto();
        dashboardTopTenAgencyScoreDto.setData(dashboardAgencyScoreTopTenList);

        DashboardBarChartDto dashboardBarChartDto = new DashboardBarChartDto();
        dashboardBarChartDto.setYAxis(dashboardTopTenAgencyNameDto);
        dashboardBarChartDto.setSeries(dashboardTopTenAgencyScoreDto);
        log.info("Closing get top ten agencies service");
        return dashboardBarChartDto;
    }

    @Override
    public DashboardRadarChartDto getAgencyDetailScore(Long agencyId) {
        double agendaSettingScore = agencyService.getAgencyAgendaSettingScoreInPolicySampleByAgencyId(agencyId);
        double formulasiKebijakanScore = agencyService.getAgencyFormulasiKebijakanScoreInPolicySampleByAgencyId(agencyId);
        double implementasiKebijakanScore = agencyService.getAgencyImplementasiKebijakanScoreInPolicySampleByAgencyId(agencyId);
        double evaluasiKebijakanScore = agencyService.getAgencyEvaluasiKebijakanScoreInPolicySampleByAgencyId(agencyId);

        List<Double> instrumentScore = new ArrayList<>();
        instrumentScore.add(agendaSettingScore);
        instrumentScore.add(evaluasiKebijakanScore);
        instrumentScore.add(formulasiKebijakanScore);
        instrumentScore.add(implementasiKebijakanScore);

        List<DashboardAgencyInstrumentScoreDto> dashboardAgencyInstrumentScoreDtoList = new ArrayList<>();
        DashboardAgencyInstrumentScoreDto dashboardAgencyInstrumentScoreDto = new DashboardAgencyInstrumentScoreDto();
        dashboardAgencyInstrumentScoreDto.setValue(instrumentScore);
        dashboardAgencyInstrumentScoreDtoList.add(dashboardAgencyInstrumentScoreDto);

        DashboardRadarChartDataDto dashboardRadarChartDataDto = new DashboardRadarChartDataDto();
        dashboardRadarChartDataDto.setData(dashboardAgencyInstrumentScoreDtoList);

        DashboardRadarChartDto dashboardRadarChartDto = new DashboardRadarChartDto();
        dashboardRadarChartDto.setSeries(dashboardRadarChartDataDto);

        return dashboardRadarChartDto;
    }
}
