package id.go.lan.ikk.module.dashboard.service;

import id.go.lan.ikk.module.dashboard.model.*;
import id.go.lan.ikk.module.dashboard.presenter.model.AgendaSettingValidationRequest;
import id.go.lan.ikk.module.dashboard.presenter.model.EvaluasiKebijakanValidationRequest;
import id.go.lan.ikk.module.dashboard.presenter.model.FormulasiKebijakanValidationRequest;
import id.go.lan.ikk.module.dashboard.presenter.model.ImplementasiKebijakanValidationRequest;
import id.go.lan.ikk.module.policy.model.*;

import java.util.List;

public interface DashboardKoordinatorInstansiService {
    List<DashboardPoliciesPerAgencyDto> getAllPoliciesPerAgency();
    DashboardAgencyPoliciesVerificationDto getAllAgencyPoliciesByAgencyId(Long agencyId);

    void verifyAllPoliciesByAgencyId(Long agencyId);
    void cancelAllPoliciesByAgencyId(Long agencyId);
    void approvePolicyByPolicyId(Long policyId);
    void disapprovePolicyByPolicyId(Long policyId);
    void sendVerifiedPoliciesToAdminByAgencyId(Long agencyId);

    void startValidationPolicyByPolicyId(Long policyId);
    void finishValidationPolicyByPolicyId(Long policyId);
    void cancelValidationPolicyByPolicyId(Long policyId);
    void sendValidatedPoliciesToKoordinatorUtamaByPolicyId(Long policyId);

    AgendaSettingValidationDto getAgendaSettingValidationByPolicyId(Long policyId);
    FormulasiKebijakanValidationDto getFormulasiKebijakanValidationByPolicyId(Long policyId);
    ImplementasiKebijakanValidationDto getImplementasiKebijakanValidationByPolicyId(Long policyId);
    EvaluasiKebijakanValidationDto getEvaluasiKebijakanValidationByPolicyId(Long policyId);

    void submitAgendaSettingValidationByPolicyId(Long policyId, AgendaSettingValidationRequest requestBody);
    void submitFormulasiKebijakanValidationByPolicyId(Long policyId, FormulasiKebijakanValidationRequest requestBody);
    void submitImplementasiKebijakanValidationByPolicyId(Long policyId, ImplementasiKebijakanValidationRequest requestBody);
    void submitEvaluasiKebijakanValidationByPolicyId(Long policyId, EvaluasiKebijakanValidationRequest requestBody);

    DashboardKoordinatorInstansiCardDto getDashboardCard();
    DashboardKoordinatorInstansiCardPolicyDto getDashboardCardPolicy(Long policyId);
    List<DashboardKoordinatorInstansiPolicyProcessDto> getDashboardAgencyProcess();
    List<PolicyDto> getKoordinatorInstansiDashboardProgress(Long agencyId);
    DashboardPolicyProgressDto getKoordinatorInstansiPolicyProgress(Long id);
    List<DashboardKoordinatorInstansiPolicyFinishDto> getDashboardAgencyFinish();
    List<PolicyDto> getKoordinatorInstansiDashboardFinish(Long agencyId);
}
