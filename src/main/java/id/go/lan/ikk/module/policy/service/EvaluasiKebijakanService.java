package id.go.lan.ikk.module.policy.service;

import id.go.lan.ikk.module.dashboard.presenter.model.EvaluasiKebijakanValidationRequest;
import id.go.lan.ikk.module.policy.entity.EvaluasiKebijakanEntity;
import id.go.lan.ikk.module.policy.entity.EvaluasiKebijakanFileEntity;
import id.go.lan.ikk.module.policy.entity.PolicyEntity;
import id.go.lan.ikk.module.policy.model.EvaluasiKebijakanDto;
import id.go.lan.ikk.module.policy.model.EvaluasiKebijakanValidationDto;
import org.springframework.web.multipart.MultipartFile;

public interface EvaluasiKebijakanService {
    EvaluasiKebijakanDto getEvaluasiKebijakanByPolicyId(Long id);
    EvaluasiKebijakanDto submitD1A(Long policyId, String answer, MultipartFile file);
    EvaluasiKebijakanDto submitD1B(Long policyId, String answer, MultipartFile file);
    EvaluasiKebijakanDto submitD2A(Long policyId, String answer, MultipartFile file);
    EvaluasiKebijakanDto submitD2B(Long policyId, String answer, MultipartFile file);
    EvaluasiKebijakanDto submitD3A(Long policyId, String answer, MultipartFile file);
    EvaluasiKebijakanDto submitD3B(Long policyId, String answer, MultipartFile file);
    EvaluasiKebijakanDto submitD3C(Long policyId, String answer, MultipartFile file);
    EvaluasiKebijakanDto submitD3D(Long policyId, String answer, MultipartFile file);
    EvaluasiKebijakanDto submitD3E(Long policyId, String answer, MultipartFile file);
    EvaluasiKebijakanDto submitInformasiD4(Long policyId, String answer);

    EvaluasiKebijakanValidationDto getValidationKI(Long policyId);
    EvaluasiKebijakanValidationDto getValidationKU(Long policyId);
    void submitValidasiKI(Long policyId, EvaluasiKebijakanValidationRequest evaluasiKebijakanValidationRequest);
    void submitValidasiKU(Long policyId, EvaluasiKebijakanValidationRequest evaluasiKebijakanValidationRequest);

    EvaluasiKebijakanDto mapEvaluasiKebijakanDto(
            PolicyEntity policyEntity,
            EvaluasiKebijakanEntity evaluasiKebijakanEntity,
            EvaluasiKebijakanFileEntity evaluasiKebijakanFileEntity);
}
