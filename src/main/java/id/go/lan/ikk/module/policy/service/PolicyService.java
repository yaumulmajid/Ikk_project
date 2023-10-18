package id.go.lan.ikk.module.policy.service;

import id.go.lan.ikk.module.policy.entity.PolicyEntity;
import id.go.lan.ikk.module.policy.model.PolicyDto;
import id.go.lan.ikk.module.policy.model.PolicySampleDto;
import id.go.lan.ikk.module.policy.model.PolicySelesaiDto;
import id.go.lan.ikk.module.user.entity.AgencyEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface PolicyService {
    List<PolicyDto> getAllAdminPolicy();
    void addNewPolicy(String name, Date effectiveDate, String sector, MultipartFile file);
    void updatePolicyByPolicyId(Long id, String name, Date effectiveDate, String sector, MultipartFile file);
    void deletePolicyByPolicyId(Long id);
    void pindahPolicyByPolicyId(Long idkebijakan,Long idagencytujuan);

    void sendToKoordinatorInstansi();
    void sendToKoordinatorInstansiValidation(Long policyId);
    void startVerificationProcessByAgency(AgencyEntity agency);
    void cancelVerificationByAgency(AgencyEntity agency);
    void cancelVerificationPolicyByAgency(AgencyEntity agency);

    void startValidationKI(Long policyId);
    void finishValidationKI(Long policyId);
    void cancelValidationKI(Long policyId);
    void sendToKoordinatorUtamaValidation(Long policyId);

    void startValidationKU(Long policyId);
    void finishValidationKU(Long policyId);
    void publishValidationKU(Long policyId);
    void cancelValidationKU(Long policyId);

    PolicySampleDto getAllPoliciesDisetujui();
    List<PolicySelesaiDto> getAllPoliciesSelesai();
    void generatePolicySample();

    void assignEnumerator(Long policyId, Long enumeratorId);

    Boolean isPolicyBeingValidated(PolicyEntity policy);
}
