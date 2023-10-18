package id.go.lan.ikk.module.dashboard.service;

import id.go.lan.ikk.module.dashboard.model.DashboardKoordinatorUtamaCardPolicyDto;
import id.go.lan.ikk.module.dashboard.model.DashboardPoliciesPerAgencyDto;
import id.go.lan.ikk.module.dashboard.model.DashboardPolicyProgressDto;
import id.go.lan.ikk.module.dashboard.presenter.model.AgendaSettingValidationRequest;
import id.go.lan.ikk.module.dashboard.presenter.model.EvaluasiKebijakanValidationRequest;
import id.go.lan.ikk.module.dashboard.presenter.model.FormulasiKebijakanValidationRequest;
import id.go.lan.ikk.module.dashboard.presenter.model.ImplementasiKebijakanValidationRequest;
import id.go.lan.ikk.module.policy.model.*;

import java.util.List;

public interface DashboardKoordinatorUtamaService {
    List<DashboardPoliciesPerAgencyDto> getAllPoliciesPerAgency(Long koordinatorInstansiId);
    List<PolicyDto> getAllAgencyPoliciesByAgencyId(Long agencyId);

    void startValidationPolicyByPolicyId(Long policyId);
    void finishValidationPolicyByPolicyId(Long policyId);
    void publishValidationPolicyByPolicyId(Long policyId);
    void cancelValidationPolicyByPolicyId(Long policyId);

    AgendaSettingValidationDto getAgendaSettingValidationByPolicyId(Long policyId);
    FormulasiKebijakanValidationDto getFormulasiKebijakanValidationByPolicyId(Long policyId);
    ImplementasiKebijakanValidationDto getImplementasiKebijakanValidationByPolicyId(Long policyId);
    EvaluasiKebijakanValidationDto getEvaluasiKebijakanValidationByPolicyId(Long policyId);

    void submitAgendaSettingValidationByPolicyId(Long policyId, AgendaSettingValidationRequest requestBody);
    void submitFormulasiKebijakanValidationByPolicyId(Long policyId, FormulasiKebijakanValidationRequest requestBody);
    void submitImplementasiKebijakanValidationByPolicyId(Long policyId, ImplementasiKebijakanValidationRequest requestBody);
    void submitEvaluasiKebijakanValidationByPolicyId(Long policyId, EvaluasiKebijakanValidationRequest requestBody);

    DashboardKoordinatorUtamaCardPolicyDto getDashboardCardPolicy(Long policyId);
    DashboardPolicyProgressDto getPolicyProgressDetailByPolicyId(Long id);
}
