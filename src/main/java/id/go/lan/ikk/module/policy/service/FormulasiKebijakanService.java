package id.go.lan.ikk.module.policy.service;

import id.go.lan.ikk.module.dashboard.presenter.model.FormulasiKebijakanValidationRequest;
import id.go.lan.ikk.module.policy.entity.FormulasiKebijakanEntity;
import id.go.lan.ikk.module.policy.entity.FormulasiKebijakanFileEntity;
import id.go.lan.ikk.module.policy.entity.PolicyEntity;
import id.go.lan.ikk.module.policy.model.FormulasiKebijakanDto;
import id.go.lan.ikk.module.policy.model.FormulasiKebijakanValidationDto;
import org.springframework.web.multipart.MultipartFile;

public interface FormulasiKebijakanService {
    FormulasiKebijakanDto getFormulasiKebijakanByPolicyId(Long id);
    FormulasiKebijakanDto submitB1A(Long policyId, String answer, MultipartFile file);
    FormulasiKebijakanDto submitB1B(Long policyId, String answer, MultipartFile file);
    FormulasiKebijakanDto submitB2A(Long policyId, String answer, MultipartFile file);
    FormulasiKebijakanDto submitB2B(Long policyId, String answer, MultipartFile file);
    FormulasiKebijakanDto submitB3A(Long policyId, String answer, MultipartFile file);
    FormulasiKebijakanDto submitB3B(Long policyId, String answer, MultipartFile file);
    FormulasiKebijakanDto submitB3C(Long policyId, String answer, MultipartFile file);
    FormulasiKebijakanDto submitB4A(Long policyId, String answer, MultipartFile file);
    FormulasiKebijakanDto submitB4B(Long policyId, String answer, MultipartFile file);
    FormulasiKebijakanDto submitB4C(Long policyId, String answer, MultipartFile file);
    FormulasiKebijakanDto submitB5A(Long policyId, String answer, MultipartFile file);
    FormulasiKebijakanDto submitB5B(Long policyId, String answer, MultipartFile file);
    FormulasiKebijakanDto submitB5C(Long policyId, String answer, MultipartFile file);
    FormulasiKebijakanDto submitInformasiB6(Long policyId, String answer);

    FormulasiKebijakanValidationDto geValidationKI(Long policyId);
    FormulasiKebijakanValidationDto getValidationKU(Long policyId);
    void submitValidasiKI(Long policyId, FormulasiKebijakanValidationRequest formulasiKebijakanValidationRequest);
    void submitValidasiKU(Long policyId, FormulasiKebijakanValidationRequest formulasiKebijakanValidationRequest);

    FormulasiKebijakanDto mapFormulasiKebijakanDto(
            PolicyEntity policyEntity,
            FormulasiKebijakanEntity formulasiKebijakanEntity,
            FormulasiKebijakanFileEntity formulasiKebijakanFileEntity);
}
