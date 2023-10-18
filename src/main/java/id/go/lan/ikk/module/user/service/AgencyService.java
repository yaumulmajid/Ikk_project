package id.go.lan.ikk.module.user.service;

import id.go.lan.ikk.module.user.model.AgencyDto;
import id.go.lan.ikk.module.user.presenter.model.AddUpdateAgencyRequest;

import java.util.List;

public interface AgencyService {
    List<AgencyDto> getAllAgencies();
    AgencyDto getAgencyById(Long agencyId);
    void addAgency(AddUpdateAgencyRequest request);
    void updateAgency(Long agencyId, AddUpdateAgencyRequest request);

    Double getAgencyScoreInPolicySampleByAgencyId(Long agencyId);
    Double getAgencyAgendaSettingScoreInPolicySampleByAgencyId(Long agencyId);
    Double getAgencyFormulasiKebijakanScoreInPolicySampleByAgencyId(Long agencyId);
    Double getAgencyImplementasiKebijakanScoreInPolicySampleByAgencyId(Long agencyId);
    Double getAgencyEvaluasiKebijakanScoreInPolicySampleByAgencyId(Long agencyId);

    List<AgencyDto> getAgenciesWithPolicies();
}
