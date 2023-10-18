package id.go.lan.ikk.module.dashboard.service;

import id.go.lan.ikk.module.dashboard.model.DashboardEnumeratorCardDto;
import id.go.lan.ikk.module.policy.model.PolicyDto;

import java.util.List;

public interface DashboardEnumeratorService {
    DashboardEnumeratorCardDto getDashboardCard();
    List<PolicyDto> getAllPolicies();
    List<PolicyDto> getAllPoliciesProses();
    PolicyDto getEnumeratorPolicyByPolicyId(Long policyId);
    void startPolicyProgress(Long policyId);
    void sendToAdminForValidation(Long policyId);
}
