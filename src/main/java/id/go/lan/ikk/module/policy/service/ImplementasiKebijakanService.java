package id.go.lan.ikk.module.policy.service;

import id.go.lan.ikk.module.dashboard.presenter.model.ImplementasiKebijakanValidationRequest;
import id.go.lan.ikk.module.policy.entity.ImplementasiKebijakanEntity;
import id.go.lan.ikk.module.policy.entity.ImplementasiKebijakanFileEntity;
import id.go.lan.ikk.module.policy.entity.PolicyEntity;
import id.go.lan.ikk.module.policy.model.ImplementasiKebijakanDto;
import id.go.lan.ikk.module.policy.model.ImplementasiKebijakanValidationDto;
import org.springframework.web.multipart.MultipartFile;

public interface ImplementasiKebijakanService {
    ImplementasiKebijakanDto getImplementasiKebijakanByPolicyId(Long id);
    ImplementasiKebijakanDto submitC1A(Long policyId, String answer, MultipartFile file);
    ImplementasiKebijakanDto submitC1B(Long policyId, String answer, MultipartFile file);
    ImplementasiKebijakanDto submitC1C(Long policyId, String answer, MultipartFile file);
    ImplementasiKebijakanDto submitC1D(Long policyId, String answer, MultipartFile file);
    ImplementasiKebijakanDto submitC2A(Long policyId, String answer, MultipartFile file);
    ImplementasiKebijakanDto submitC2B(Long policyId, String answer, MultipartFile file);
    ImplementasiKebijakanDto submitC2C(Long policyId, String answer, MultipartFile file);
    ImplementasiKebijakanDto submitC3A(Long policyId, String answer, MultipartFile file);
    ImplementasiKebijakanDto submitC3B(Long policyId, String answer, MultipartFile file);
    ImplementasiKebijakanDto submitC3C(Long policyId, String answer, MultipartFile file);
    ImplementasiKebijakanDto submitInformasiC4(Long policyId, String answer);

    ImplementasiKebijakanValidationDto getValidationKI(Long policyId);
    ImplementasiKebijakanValidationDto getValidationKU(Long policyId);
    void submitValidasiKI(Long policyId, ImplementasiKebijakanValidationRequest implementasiKebijakanValidationRequest);
    void submitValidasiKU(Long policyId, ImplementasiKebijakanValidationRequest implementasiKebijakanValidationRequest);

    ImplementasiKebijakanDto mapImplementasiKebijakanDto(
            PolicyEntity policyEntity,
            ImplementasiKebijakanEntity implementasiKebijakanEntity,
            ImplementasiKebijakanFileEntity implementasiKebijakanFileEntity);
}
