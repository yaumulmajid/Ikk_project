package id.go.lan.ikk.module.policy.service;

import id.go.lan.ikk.module.policy.entity.PolicyEntity;
import id.go.lan.ikk.module.policy.entity.PolicyProcessEntity;
import id.go.lan.ikk.module.policy.entity.PolicyStatusEntity;
import id.go.lan.ikk.module.user.entity.AgencyEntity;

import java.util.Date;
import java.util.List;

public interface PolicyMasterService {
    void savePolicy(PolicyEntity policyEntity);
    PolicyEntity getPolicyByPolicyId(Long id);
    List<PolicyEntity> getAllPoliciesByAgency(AgencyEntity agency);
    List<PolicyEntity> getAllPoliciesByEnumeratorId(Long id);
    void savePolicyProcess(PolicyProcessEntity policyProcess);
    void savePolicyStatus(PolicyStatusEntity policyStatus);

    Integer countPoliciesByAgency(AgencyEntity agency);
    Date getLatestSentByAdminAtPolicy(AgencyEntity agency);
    Boolean checkIsBeingVerifiedStatus(AgencyEntity agency);
}
