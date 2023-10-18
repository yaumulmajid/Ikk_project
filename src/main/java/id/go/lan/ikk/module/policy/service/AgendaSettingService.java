package id.go.lan.ikk.module.policy.service;

import id.go.lan.ikk.module.dashboard.presenter.model.AgendaSettingValidationRequest;
import id.go.lan.ikk.module.policy.entity.AgendaSettingEntity;
import id.go.lan.ikk.module.policy.entity.AgendaSettingFileEntity;
import id.go.lan.ikk.module.policy.entity.PolicyEntity;
import id.go.lan.ikk.module.policy.model.AgendaSettingDto;
import id.go.lan.ikk.module.policy.model.AgendaSettingValidationDto;
import org.springframework.web.multipart.MultipartFile;

public interface AgendaSettingService {
    AgendaSettingDto getAgendaSettingByPolicyId(Long id);
    AgendaSettingDto submitA1A(Long policyId, String answer, MultipartFile file);
    AgendaSettingDto submitA1B(Long policyId, String answer, MultipartFile file);
    AgendaSettingDto submitA1C(Long policyId, String answer, MultipartFile file);
    AgendaSettingDto submitA1D(Long policyId, String answer, MultipartFile file);
    AgendaSettingDto submitA2A(Long policyId, String answer, MultipartFile file);
    AgendaSettingDto submitA2B(Long policyId, String answer, MultipartFile file);
    AgendaSettingDto submitA2C(Long policyId, String answer, MultipartFile file);
    AgendaSettingDto submitInformasiA3(Long policyId, String answer);

    AgendaSettingValidationDto getValidationKI(Long policyId);
    AgendaSettingValidationDto getValidationKU(Long policyId);
    void submitValidasiKI(Long policyId, AgendaSettingValidationRequest agendaSettingValidationRequest);
    void submitValidasiKU(Long policyId, AgendaSettingValidationRequest agendaSettingValidationRequest);

    AgendaSettingDto mapAgendaSettingDto(
            PolicyEntity policyEntity,
            AgendaSettingEntity agendaSettingEntity,
            AgendaSettingFileEntity agendaSettingFileEntity);
}
