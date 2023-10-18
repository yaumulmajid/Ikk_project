package id.go.lan.ikk.module.policy.service;

public interface PolicyDetailsService {
    void updatePolicyProgressByPolicyId(Long policyId);
    void updatePolicyBaseScoreByPolicyId(Long policyId);
    void updatePolicyValidationKIScoreByPolicyId(Long policyId);
    void updatePolicyValidationKUScoreByPolicyId(Long policyId);
    String getPolicyValidationKINote(Long policyId);
    void updatePolicyValidationKINote(Long policyId, String note);
    Double getAgendaSettingBaseScoreByPolicyId(Long policyId);
    Double getAgendaSettingValidationKIScoreByPolicyId(Long policyId);
    Double getAgendaSettingValidationKUScoreByPolicyId(Long policyId);
    Double getFormulasiKebijakanBaseScoreByPolicyId(Long policyId);
    Double getFormulasiKebijakanValidationKIScoreByPolicyId(Long policyId);
    Double getFormulasiKebijakanValidationKUScoreByPolicyId(Long policyId);
    Double getImplementasiKebijakanBaseScoreByPolicyId(Long policyId);
    Double getImplementasiKebijakanValidationKIScoreByPolicyId(Long policyId);
    Double getImplementasiKebijakanValidationKUScoreByPolicyId(Long policyId);
    Double getEvaluasiKebijakanBaseScoreByPolicyId(Long policyId);
    Double getEvaluasiKebijakanValidationKIScoreByPolicyId(Long policyId);
    Double getEvaluasiKebijakanValidationKUScoreByPolicyId(Long policyId);
}
