package id.go.lan.ikk.module.policy.service.implementation;

import id.go.lan.ikk.entity.PolicyProcessEnum;
import id.go.lan.ikk.entity.PolicyStatusEnum;
import id.go.lan.ikk.exception.RandomizePolicySampleFailedException;
import id.go.lan.ikk.exception.ResourceNotFoundException;
import id.go.lan.ikk.exception.ValidationFailedException;
import id.go.lan.ikk.module.policy.entity.*;
import id.go.lan.ikk.module.policy.model.PolicyDto;
import id.go.lan.ikk.module.policy.model.PolicySampleDto;
import id.go.lan.ikk.module.policy.model.PolicySelesaiDto;
import id.go.lan.ikk.module.policy.repository.*;
import id.go.lan.ikk.module.policy.service.PolicyService;
import id.go.lan.ikk.module.user.entity.AgencyEntity;
import id.go.lan.ikk.module.user.entity.UserEntity;
import id.go.lan.ikk.module.user.model.GetUserDetailDto;
import id.go.lan.ikk.module.user.service.AgencyMasterService;
import id.go.lan.ikk.module.user.service.UserMasterService;
import id.go.lan.ikk.module.user.service.UserService;
import id.go.lan.ikk.utility.ModelMapperUtility;
import id.go.lan.ikk.utility.UploadFileUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.SecureRandom;
import java.util.*;

@Service
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    private ModelMapperUtility modelMapperUtility;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private PolicyDetailsRepository policyDetailsRepository;

    @Autowired
    private PolicyStatusRepository policyStatusRepository;

    @Autowired
    private PolicyProcessRepository policyProcessRepository;

    @Autowired
    private PolicySampleRepository policySampleRepository;

    @Autowired
    private AgendaSettingRepository agendaSettingRepository;

    @Autowired
    private AgendaSettingKIRepository agendaSettingKIRepository;

    @Autowired
    private AgendaSettingKURepository agendaSettingKURepository;

    @Autowired
    private AgendaSettingFileRepository agendaSettingFileRepository;

    @Autowired
    private AgendaSettingBaseScoreRepository agendaSettingBaseScoreRepository;

    @Autowired
    private AgendaSettingKIScoreRepository agendaSettingKIScoreRepository;

    @Autowired
    private AgendaSettingKUScoreRepository agendaSettingKUScoreRepository;

    @Autowired
    private FormulasiKebijakanRepository formulasiKebijakanRepository;

    @Autowired
    private FormulasiKebijakanKIRepository formulasiKebijakanKIRepository;

    @Autowired
    private FormulasiKebijakanKURepository formulasiKebijakanKURepository;

    @Autowired
    private FormulasiKebijakanFileRepository formulasiKebijakanFileRepository;

    @Autowired
    private FormulasiKebijakanBaseScoreRepository formulasiKebijakanBaseScoreRepository;

    @Autowired
    private FormulasiKebijakanKIScoreRepository formulasiKebijakanKIScoreRepository;

    @Autowired
    private FormulasiKebijakanKUScoreRepository formulasiKebijakanKUScoreRepository;

    @Autowired
    private ImplementasiKebijakanRepository implementasiKebijakanRepository;

    @Autowired
    private ImplementasiKebijakanKIRepository implementasiKebijakanKIRepository;

    @Autowired
    private ImplementasiKebijakanKURepository implementasiKebijakanKURepository;

    @Autowired
    private ImplementasiKebijakanFileRepository implementasiKebijakanFileRepository;

    @Autowired
    private ImplementasiKebijakanBaseScoreRepository implementasiKebijakanBaseScoreRepository;

    @Autowired
    private ImplementasiKebijakanKIScoreRepository implementasiKebijakanKIScoreRepository;

    @Autowired
    private ImplementasiKebijakanKUScoreRepository implementasiKebijakanKUScoreRepository;

    @Autowired
    private EvaluasiKebijakanRepository evaluasiKebijakanRepository;

    @Autowired
    private EvaluasiKebijakanKIRepository evaluasiKebijakanKIRepository;

    @Autowired
    private EvaluasiKebijakanKURepository evaluasiKebijakanKURepository;

    @Autowired
    private EvaluasiKebijakanFileRepository evaluasiKebijakanFileRepository;

    @Autowired
    private EvaluasiKebijakanBaseScoreRepository evaluasiKebijakanBaseScoreRepository;

    @Autowired
    private EvaluasiKebijakanKIScoreRepository evaluasiKebijakanKIScoreRepository;

    @Autowired
    private EvaluasiKebijakanKUScoreRepository evaluasiKebijakanKUScoreRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMasterService userMasterService;

    @Autowired
    private AgencyMasterService agencyMasterService;

    @Autowired
    private UploadFileUtility uploadFileUtility;

    @Override
    public List<PolicyDto> getAllAdminPolicy() {
        Long agencyId = userService.getAgencyIdBySignedInUser();
        AgencyEntity agencyEntity = agencyMasterService.getAgencyById(agencyId);
        List<PolicyEntity> policyEntityList = policyRepository.findByAgency(agencyEntity);

        List<PolicyDto> policyDtoList = new ArrayList<>();
        for (PolicyEntity policyEntity : policyEntityList) {

            if (policyEntity.getPolicyStatusEntity().getName().equals(PolicyStatusEnum.SEDANG_VERIFIKASI)) {
                PolicyDto policyDto = new PolicyDto();
                policyDto.setId(policyEntity.getId());
                policyDto.setNama(policyEntity.getName());
                policyDto.setTanggalBerlaku(policyEntity.getPolicyDetailsEntity().getEffectiveDate());
                policyDto.setTanggalVerifikasi(policyEntity.getVerifiedByKoordinatorAt());
                policyDto.setTanggalValidasiKi(policyEntity.getValidatedByKoordinatorAt());
                policyDto.setTanggalValidasiKu(policyEntity.getValidatedByKoordinatorUtamaAt());
                policyDto.setTanggalDiajukan(policyEntity.getSentByAdminAt());
                policyDto.setTanggalAssign(policyEntity.getAssignedByAdminAt());
                policyDto.setTanggalProses(policyEntity.getProcessedByEnumeratorAt());
                policyDto.setJenis(policyEntity.getPolicyDetailsEntity().getSector());
                policyDto.setFilePath(policyEntity.getPolicyDetailsEntity().getFilePath());
                policyDto.setFileSize(policyEntity.getPolicyDetailsEntity().getFileSize());
                policyDto.setFileType(policyEntity.getPolicyDetailsEntity().getFileType());
                policyDto.setFileOriginalName(policyEntity.getPolicyDetailsEntity().getFileOriginalName());
                policyDto.setProses(policyEntity.getPolicyProcessEntity().getName().name());
                policyDto.setStatus(PolicyProcessEnum.DIAJUKAN.name());
                policyDto.setCreateBy(userMasterService.findById(policyEntity.getCreatedBy()).getId());
                policyDto.setInstansi(policyEntity.getAgency().getName());
                policyDto.setProgres(policyEntity.getPolicyDetailsEntity().getProgress());

                policyDtoList.add(policyDto);
            } else {
                PolicyDto policyDto = new PolicyDto();
                policyDto.setId(policyEntity.getId());
                policyDto.setNama(policyEntity.getName());
                policyDto.setTanggalBerlaku(policyEntity.getPolicyDetailsEntity().getEffectiveDate());
                policyDto.setTanggalVerifikasi(policyEntity.getVerifiedByKoordinatorAt());
                policyDto.setTanggalValidasiKi(policyEntity.getValidatedByKoordinatorAt());
                policyDto.setTanggalValidasiKu(policyEntity.getValidatedByKoordinatorUtamaAt());
                policyDto.setTanggalDiajukan(policyEntity.getSentByAdminAt());
                policyDto.setTanggalAssign(policyEntity.getAssignedByAdminAt());
                policyDto.setTanggalProses(policyEntity.getProcessedByEnumeratorAt());
                policyDto.setJenis(policyEntity.getPolicyDetailsEntity().getSector());
                policyDto.setFilePath(policyEntity.getPolicyDetailsEntity().getFilePath());
                policyDto.setFileSize(policyEntity.getPolicyDetailsEntity().getFileSize());
                policyDto.setFileType(policyEntity.getPolicyDetailsEntity().getFileType());
                policyDto.setFileOriginalName(policyEntity.getPolicyDetailsEntity().getFileOriginalName());
                policyDto.setProses(policyEntity.getPolicyStatusEntity().getName().name());
                policyDto.setStatus(policyEntity.getPolicyProcessEntity().getName().name());
                policyDto.setCreateBy(userMasterService.findById(policyEntity.getCreatedBy()).getId());
                policyDto.setInstansi(policyEntity.getAgency().getName());
                policyDto.setProgres(policyEntity.getPolicyDetailsEntity().getProgress());

                policyDtoList.add(policyDto);
            }
        }

        return policyDtoList;
    }

    @Override
    public void addNewPolicy(String name, Date effectiveDate, String sector, MultipartFile file) {
        UserEntity user = userMasterService.findById(userService.getSignedInUserId());
        PolicyEntity policyEntity = new PolicyEntity();
        policyEntity.setName(name);
        policyEntity.setAgency(agencyMasterService.getAgencyById(userService.getAgencyIdBySignedInUser()));

        PolicyDetailsEntity policyDetailsEntity = new PolicyDetailsEntity();
        policyDetailsEntity.setSector(sector);
        policyDetailsEntity.setEffectiveDate(effectiveDate);
        policyDetailsEntity.setProgress(0.0);
        policyDetailsEntity.setCreatedBy(userService.getSignedInUserId());
        policyDetailsEntity.setFilePath(uploadFileUtility.uploadFile(file, user.getUsername()));
        policyDetailsEntity.setFileSize(String.valueOf(file.getSize()));
        policyDetailsEntity.setFileType(file.getContentType());
        policyDetailsEntity.setFileOriginalName(file.getOriginalFilename());
        PolicyDetailsEntity savedPolicyDetails = policyDetailsRepository.save(policyDetailsEntity);

        PolicyStatusEntity policyStatusEntity = new PolicyStatusEntity();
        policyStatusEntity.setName(PolicyStatusEnum.BELUM_TERVERIFIKASI);
        policyStatusEntity.setCreatedBy(userService.getSignedInUserId());
        PolicyStatusEntity savedPolicyStatus = policyStatusRepository.save(policyStatusEntity);

        PolicyProcessEntity policyProcessEntity = new PolicyProcessEntity();
        policyProcessEntity.setName(PolicyProcessEnum.DIAJUKAN);
        policyProcessEntity.setCreatedBy(userService.getSignedInUserId());
        PolicyProcessEntity savedPolicyProcess = policyProcessRepository.save(policyProcessEntity);

        AgendaSettingEntity agendaSettingEntity = new AgendaSettingEntity();
        agendaSettingEntity.setCreatedBy(userService.getSignedInUserId());
        agendaSettingRepository.save(agendaSettingEntity);

        AgendaSettingKIEntity agendaSettingKIEntity = new AgendaSettingKIEntity();
        agendaSettingKIEntity.setCreatedBy(userService.getSignedInUserId());
        agendaSettingKIRepository.save(agendaSettingKIEntity);

        AgendaSettingKUEntity agendaSettingKUEntity = new AgendaSettingKUEntity();
        agendaSettingKUEntity.setCreatedBy(userService.getSignedInUserId());
        agendaSettingKURepository.save(agendaSettingKUEntity);

        AgendaSettingFileEntity agendaSettingFileEntity = new AgendaSettingFileEntity();
        agendaSettingFileEntity.setCreatedBy(userService.getSignedInUserId());
        agendaSettingFileRepository.save(agendaSettingFileEntity);

        AgendaSettingBaseScoreEntity agendaSettingBaseScoreEntity = new AgendaSettingBaseScoreEntity();
        agendaSettingBaseScoreEntity.setCreatedBy(userService.getSignedInUserId());
        agendaSettingBaseScoreRepository.save(agendaSettingBaseScoreEntity);

        AgendaSettingKIScoreEntity agendaSettingKIScoreEntity = new AgendaSettingKIScoreEntity();
        agendaSettingKIScoreEntity.setCreatedBy(userService.getSignedInUserId());
        agendaSettingKIScoreRepository.save(agendaSettingKIScoreEntity);

        AgendaSettingKUScoreEntity agendaSettingKUScoreEntity = new AgendaSettingKUScoreEntity();
        agendaSettingKUScoreEntity.setCreatedBy(userService.getSignedInUserId());
        agendaSettingKUScoreRepository.save(agendaSettingKUScoreEntity);

        FormulasiKebijakanEntity formulasiKebijakanEntity = new FormulasiKebijakanEntity();
        formulasiKebijakanEntity.setCreatedBy(userService.getSignedInUserId());
        formulasiKebijakanRepository.save(formulasiKebijakanEntity);

        FormulasiKebijakanKIEntity formulasiKebijakanKIEntity = new FormulasiKebijakanKIEntity();
        formulasiKebijakanKIEntity.setCreatedBy(userService.getSignedInUserId());
        formulasiKebijakanKIRepository.save(formulasiKebijakanKIEntity);

        FormulasiKebijakanKUEntity formulasiKebijakanKUEntity = new FormulasiKebijakanKUEntity();
        formulasiKebijakanKUEntity.setCreatedBy(userService.getSignedInUserId());
        formulasiKebijakanKURepository.save(formulasiKebijakanKUEntity);

        FormulasiKebijakanFileEntity formulasiKebijakanFileEntity = new FormulasiKebijakanFileEntity();
        formulasiKebijakanFileEntity.setCreatedBy(userService.getSignedInUserId());
        formulasiKebijakanFileRepository.save(formulasiKebijakanFileEntity);

        FormulasiKebijakanBaseScoreEntity formulasiKebijakanBaseScoreEntity = new FormulasiKebijakanBaseScoreEntity();
        formulasiKebijakanBaseScoreEntity.setCreatedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        FormulasiKebijakanKIScoreEntity formulasiKebijakanKIScoreEntity = new FormulasiKebijakanKIScoreEntity();
        formulasiKebijakanKIScoreEntity.setCreatedBy(userService.getSignedInUserId());
        formulasiKebijakanKIScoreRepository.save(formulasiKebijakanKIScoreEntity);

        FormulasiKebijakanKUScoreEntity formulasiKebijakanKUScoreEntity = new FormulasiKebijakanKUScoreEntity();
        formulasiKebijakanKUScoreEntity.setCreatedBy(userService.getSignedInUserId());
        formulasiKebijakanKUScoreRepository.save(formulasiKebijakanKUScoreEntity);

        ImplementasiKebijakanEntity implementasiKebijakanEntity = new ImplementasiKebijakanEntity();
        implementasiKebijakanEntity.setCreatedBy(userService.getSignedInUserId());
        implementasiKebijakanRepository.save(implementasiKebijakanEntity);

        ImplementasiKebijakanKIEntity implementasiKebijakanKIEntity = new ImplementasiKebijakanKIEntity();
        implementasiKebijakanKIEntity.setCreatedBy(userService.getSignedInUserId());
        implementasiKebijakanKIRepository.save(implementasiKebijakanKIEntity);

        ImplementasiKebijakanKUEntity implementasiKebijakanKUEntity = new ImplementasiKebijakanKUEntity();
        implementasiKebijakanKUEntity.setCreatedBy(userService.getSignedInUserId());
        implementasiKebijakanKURepository.save(implementasiKebijakanKUEntity);

        ImplementasiKebijakanFileEntity implementasiKebijakanFileEntity = new ImplementasiKebijakanFileEntity();
        implementasiKebijakanFileEntity.setCreatedBy(userService.getSignedInUserId());
        implementasiKebijakanFileRepository.save(implementasiKebijakanFileEntity);

        ImplementasiKebijakanBaseScoreEntity implementasiKebijakanBaseScoreEntity = new ImplementasiKebijakanBaseScoreEntity();
        implementasiKebijakanBaseScoreEntity.setCreatedBy(userService.getSignedInUserId());
        implementasiKebijakanBaseScoreRepository.save(implementasiKebijakanBaseScoreEntity);

        ImplementasiKebijakanKIScoreEntity implementasiKebijakanKIScoreEntity = new ImplementasiKebijakanKIScoreEntity();
        implementasiKebijakanKIScoreEntity.setCreatedBy(userService.getSignedInUserId());
        implementasiKebijakanKIScoreRepository.save(implementasiKebijakanKIScoreEntity);

        ImplementasiKebijakanKUScoreEntity implementasiKebijakanKUScoreEntity = new ImplementasiKebijakanKUScoreEntity();
        implementasiKebijakanKUScoreEntity.setCreatedBy(userService.getSignedInUserId());
        implementasiKebijakanKUScoreRepository.save(implementasiKebijakanKUScoreEntity);

        EvaluasiKebijakanEntity evaluasiKebijakanEntity = new EvaluasiKebijakanEntity();
        evaluasiKebijakanEntity.setCreatedBy(userService.getSignedInUserId());
        evaluasiKebijakanRepository.save(evaluasiKebijakanEntity);

        EvaluasiKebijakanKIEntity evaluasiKebijakanKIEntity = new EvaluasiKebijakanKIEntity();
        evaluasiKebijakanKIEntity.setCreatedBy(userService.getSignedInUserId());
        evaluasiKebijakanKIRepository.save(evaluasiKebijakanKIEntity);

        EvaluasiKebijakanKUEntity evaluasiKebijakanKUEntity = new EvaluasiKebijakanKUEntity();
        evaluasiKebijakanKUEntity.setCreatedBy(userService.getSignedInUserId());
        evaluasiKebijakanKURepository.save(evaluasiKebijakanKUEntity);

        EvaluasiKebijakanFileEntity evaluasiKebijakanFileEntity = new EvaluasiKebijakanFileEntity();
        evaluasiKebijakanFileEntity.setCreatedBy(userService.getSignedInUserId());
        evaluasiKebijakanFileRepository.save(evaluasiKebijakanFileEntity);

        EvaluasiKebijakanBaseScoreEntity evaluasiKebijakanBaseScoreEntity = new EvaluasiKebijakanBaseScoreEntity();
        evaluasiKebijakanBaseScoreEntity.setCreatedBy(userService.getSignedInUserId());
        evaluasiKebijakanBaseScoreRepository.save(evaluasiKebijakanBaseScoreEntity);

        EvaluasiKebijakanKIScoreEntity evaluasiKebijakanKIScoreEntity = new EvaluasiKebijakanKIScoreEntity();
        evaluasiKebijakanKIScoreEntity.setCreatedBy(userService.getSignedInUserId());
        evaluasiKebijakanKIScoreRepository.save(evaluasiKebijakanKIScoreEntity);

        EvaluasiKebijakanKUScoreEntity evaluasiKebijakanKUScoreEntity = new EvaluasiKebijakanKUScoreEntity();
        evaluasiKebijakanKUScoreEntity.setCreatedBy(userService.getSignedInUserId());
        evaluasiKebijakanKUScoreRepository.save(evaluasiKebijakanKUScoreEntity);

        policyEntity.setPolicyDetailsEntity(savedPolicyDetails);
        policyEntity.setPolicyStatusEntity(savedPolicyStatus);
        policyEntity.setPolicyProcessEntity(savedPolicyProcess);

        policyEntity.setAgendaSettingEntity(agendaSettingEntity);
        policyEntity.setAgendaSettingKIEntity(agendaSettingKIEntity);
        policyEntity.setAgendaSettingKUEntity(agendaSettingKUEntity);
        policyEntity.setAgendaSettingFileEntity(agendaSettingFileEntity);
        policyEntity.setAgendaSettingBaseScoreEntity(agendaSettingBaseScoreEntity);
        policyEntity.setAgendaSettingKIScoreEntity(agendaSettingKIScoreEntity);
        policyEntity.setAgendaSettingKUScoreEntity(agendaSettingKUScoreEntity);

        policyEntity.setFormulasiKebijakanEntity(formulasiKebijakanEntity);
        policyEntity.setFormulasiKebijakanKIEntity(formulasiKebijakanKIEntity);
        policyEntity.setFormulasiKebijakanKUEntity(formulasiKebijakanKUEntity);
        policyEntity.setFormulasiKebijakanFileEntity(formulasiKebijakanFileEntity);
        policyEntity.setFormulasiKebijakanBaseScoreEntity(formulasiKebijakanBaseScoreEntity);
        policyEntity.setFormulasiKebijakanKIScoreEntity(formulasiKebijakanKIScoreEntity);
        policyEntity.setFormulasiKebijakanKUScoreEntity(formulasiKebijakanKUScoreEntity);

        policyEntity.setImplementasiKebijakanEntity(implementasiKebijakanEntity);
        policyEntity.setImplementasiKebijakanKIEntity(implementasiKebijakanKIEntity);
        policyEntity.setImplementasiKebijakanKUEntity(implementasiKebijakanKUEntity);
        policyEntity.setImplementasiKebijakanFileEntity(implementasiKebijakanFileEntity);
        policyEntity.setImplementasiKebijakanBaseScoreEntity(implementasiKebijakanBaseScoreEntity);
        policyEntity.setImplementasiKebijakanKIScoreEntity(implementasiKebijakanKIScoreEntity);
        policyEntity.setImplementasiKebijakanKUScoreEntity(implementasiKebijakanKUScoreEntity);

        policyEntity.setEvaluasiKebijakanEntity(evaluasiKebijakanEntity);
        policyEntity.setEvaluasiKebijakanKIEntity(evaluasiKebijakanKIEntity);
        policyEntity.setEvaluasiKebijakanKUEntity(evaluasiKebijakanKUEntity);
        policyEntity.setEvaluasiKebijakanFileEntity(evaluasiKebijakanFileEntity);
        policyEntity.setEvaluasiKebijakanBaseScoreEntity(evaluasiKebijakanBaseScoreEntity);
        policyEntity.setEvaluasiKebijakanKIScoreEntity(evaluasiKebijakanKIScoreEntity);
        policyEntity.setEvaluasiKebijakanKUScoreEntity(evaluasiKebijakanKUScoreEntity);

        policyEntity.setCreatedBy(userService.getSignedInUserId());

        policyRepository.save(policyEntity);
    }

    @Override
    public void updatePolicyByPolicyId(Long id, String name, Date effectiveDate, String sector, MultipartFile file) {
        UserEntity user = userMasterService.findById(userService.getSignedInUserId());
        PolicyEntity policyEntity = policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Populasi tidak ditemukan"));
        policyEntity.setName(name);

        PolicyDetailsEntity policyDetailsEntity = policyEntity.getPolicyDetailsEntity();
        policyDetailsEntity.setEffectiveDate(effectiveDate);
        policyDetailsEntity.setSector(sector);
        policyDetailsEntity.setModifiedBy(userService.getSignedInUserId());
        if(file != null){
            policyDetailsEntity.setFilePath(uploadFileUtility.uploadFile(file, user.getUsername()));
            policyDetailsEntity.setFileSize(String.valueOf(file.getSize()));
            policyDetailsEntity.setFileType(file.getContentType());
            policyDetailsEntity.setFileOriginalName(file.getOriginalFilename());
        }
        policyDetailsRepository.save(policyDetailsEntity);

        policyEntity.setModifiedBy(userService.getSignedInUserId());
        policyRepository.save(policyEntity);
    }

    @Override
    public  void pindahPolicyByPolicyId(Long idkebijakan,Long idagencytujuan){
        UserEntity user = userMasterService.findById(userService.getSignedInUserId());
        AgencyEntity agencyEntity = agencyMasterService.getAgencyById(idagencytujuan);
        PolicyEntity policyEntity = policyRepository.findById(idkebijakan)
                .orElseThrow(() -> new ResourceNotFoundException("Populasi tidak ditemukan"));
        
        policyEntity.setAgency(agencyEntity);
        policyRepository.save(policyEntity);
    }

    @Override
    public void deletePolicyByPolicyId(Long id) {
        PolicySampleEntity policySample = policySampleRepository.findByPolicyId(id);

        if (policySample != null) {
            policySampleRepository.delete(policySample);
        }

        policyRepository.deleteById(id);
    }

    @Override
    public void sendToKoordinatorInstansi() {
        Long agencyId = userService.getAgencyIdBySignedInUser();
        AgencyEntity agencyEntity = agencyMasterService.getAgencyById(agencyId);
        List<PolicyEntity> policyEntityList = policyRepository.findByAgency(agencyEntity);

        for (PolicyEntity policyEntity : policyEntityList) {
            PolicyStatusEntity policyStatusEntity = policyEntity.getPolicyStatusEntity();
            policyStatusEntity.setName(PolicyStatusEnum.MENUNGGU_VERIFIKASI);
            policyStatusEntity.setModifiedBy(userService.getSignedInUserId());
            policyStatusRepository.save(policyStatusEntity);

            policyEntity.setSentByAdminAt(new Date());
            policyEntity.setSentByAdminId(userService.getSignedInUserId());
            policyEntity.setModifiedBy(userService.getSignedInUserId());
            policyRepository.save(policyEntity);
        }
    }

    @Override
    public void sendToKoordinatorInstansiValidation(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        PolicyStatusEntity policyStatusEntity = policyEntity.getPolicyStatusEntity();
        policyStatusEntity.setName(PolicyStatusEnum.MENUNGGU_VALIDASI_KI);
        policyStatusEntity.setModifiedBy(userService.getSignedInUserId());
        policyStatusRepository.save(policyStatusEntity);
    }

    @Override
    public void startVerificationProcessByAgency(AgencyEntity agency) {
        List<PolicyEntity> policyEntityList = policyRepository.findByAgency(agency);

        for (PolicyEntity policyEntity : policyEntityList) {
            PolicyStatusEntity policyStatusEntity = policyEntity.getPolicyStatusEntity();
            policyStatusEntity.setName(PolicyStatusEnum.SEDANG_VERIFIKASI);
            policyStatusEntity.setModifiedBy(userService.getSignedInUserId());
            policyStatusRepository.save(policyStatusEntity);
        }
    }

    @Override
    public void cancelVerificationByAgency(AgencyEntity agency) {
        List<PolicyEntity> policyEntityList = policyRepository.findByAgency(agency);

        for (PolicyEntity policyEntity : policyEntityList) {
            PolicyStatusEntity policyStatusEntity = policyEntity.getPolicyStatusEntity();
            policyStatusEntity.setName(PolicyStatusEnum.BELUM_TERVERIFIKASI);
            policyStatusEntity.setModifiedBy(userService.getSignedInUserId());
            policyStatusRepository.save(policyStatusEntity);

            policyEntity.setSentByAdminAt(null);
            policyEntity.setSentByAdminId(null);
            policyRepository.save(policyEntity);
        }
    }

    @Override
    public void cancelVerificationPolicyByAgency(AgencyEntity agency) {
        List<PolicyEntity> policyEntityList = policyRepository.findByAgency(agency);

        for (PolicyEntity policyEntity : policyEntityList) {
            PolicyStatusEntity policyStatusEntity = policyEntity.getPolicyStatusEntity();
            policyStatusEntity.setName(PolicyStatusEnum.SELESAI_VERIFIKASI);
            policyStatusEntity.setModifiedBy(userService.getSignedInUserId());
            policyStatusRepository.save(policyStatusEntity);

            policyEntity.setSentByAdminAt(null);
            policyEntity.setSentByAdminId(null);
            policyRepository.save(policyEntity);
        }
    }

    @Override
    public void startValidationKI(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        PolicyStatusEntity policyStatusEntity = policyEntity.getPolicyStatusEntity();

        policyStatusEntity.setName(PolicyStatusEnum.SEDANG_VALIDASI_KI);
        policyStatusEntity.setModifiedBy(userService.getSignedInUserId());
        policyStatusRepository.save(policyStatusEntity);
    }

    @Override
    public void finishValidationKI(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        PolicyStatusEntity policyStatusEntity = policyEntity.getPolicyStatusEntity();

        policyStatusEntity.setName(PolicyStatusEnum.SELESAI_VALIDASI_KI);
        policyStatusEntity.setModifiedBy(userService.getSignedInUserId());
        policyStatusRepository.save(policyStatusEntity);

        policyEntity.setValidatedByKoordinatorAt(new Date());
        policyEntity.setValidatedByKoordinatorId(userService.getSignedInUserId());
        policyEntity.setModifiedBy(userService.getSignedInUserId());
        policyRepository.save(policyEntity);
    }

    @Override
    public void cancelValidationKI(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        PolicyStatusEntity policyStatusEntity = policyEntity.getPolicyStatusEntity();
        policyStatusEntity.setName(PolicyStatusEnum.MENUNGGU_VALIDASI_AI);
        policyStatusEntity.setModifiedBy(userService.getSignedInUserId());

        policyStatusRepository.save(policyStatusEntity);
    }

    @Override
    public void sendToKoordinatorUtamaValidation(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        PolicyStatusEntity policyStatusEntity = policyEntity.getPolicyStatusEntity();
        policyStatusEntity.setName(PolicyStatusEnum.MENUNGGU_VALIDASI_KU);
        policyStatusEntity.setModifiedBy(userService.getSignedInUserId());

        policyStatusRepository.save(policyStatusEntity);
    }

    @Override
    public void startValidationKU(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        PolicyStatusEntity policyStatusEntity = policyEntity.getPolicyStatusEntity();

        policyStatusEntity.setName(PolicyStatusEnum.SEDANG_VALIDASI_KU);
        policyStatusEntity.setModifiedBy(userService.getSignedInUserId());
        policyStatusRepository.save(policyStatusEntity);
    }

    @Override
    public void finishValidationKU(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        PolicyStatusEntity policyStatusEntity = policyEntity.getPolicyStatusEntity();
//        PolicyProcessEntity policyProcessEntity = policyEntity.getPolicyProcessEntity();

        policyStatusEntity.setName(PolicyStatusEnum.SELESAI_VALIDASI_KU);
        policyStatusEntity.setModifiedBy(userService.getSignedInUserId());
        policyStatusRepository.save(policyStatusEntity);

//        policyProcessEntity.setName(PolicyProcessEnum.SELESAI);
//        policyProcessEntity.setModifiedBy(userService.getSignedInUserId());
//        policyProcessRepository.save(policyProcessEntity);

        policyEntity.setValidatedByKoordinatorUtamaAt(new Date());
        policyEntity.setValidatedByKoordinatorId(userService.getSignedInUserId());
        policyEntity.setModifiedBy(userService.getSignedInUserId());
        policyRepository.save(policyEntity);
    }

    @Override
    public void publishValidationKU(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
        PolicyStatusEntity policyStatusEntity = policyEntity.getPolicyStatusEntity();
        PolicyProcessEntity policyProcessEntity = policyEntity.getPolicyProcessEntity();

        policyStatusEntity.setName(PolicyStatusEnum.TELAH_DITERBITKAN);
        policyStatusEntity.setModifiedBy(userService.getSignedInUserId());
        policyStatusRepository.save(policyStatusEntity);

        policyProcessEntity.setName(PolicyProcessEnum.SELESAI);
        policyProcessEntity.setModifiedBy(userService.getSignedInUserId());
        policyProcessRepository.save(policyProcessEntity);

        policyEntity.setValidatedByKoordinatorUtamaAt(new Date());
        policyEntity.setValidatedByKoordinatorId(userService.getSignedInUserId());
        policyEntity.setModifiedBy(userService.getSignedInUserId());
        policyRepository.save(policyEntity);
    }

    @Override
    public void cancelValidationKU(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        if (policyEntity.getPolicyProcessEntity().getName().name().equals(PolicyProcessEnum.SELESAI.name())) {
            throw new ValidationFailedException("Kebijakan yang telah divalidasi tidak dapat ditunda");
        }

        PolicyStatusEntity policyStatusEntity = policyEntity.getPolicyStatusEntity();

        policyStatusEntity.setName(PolicyStatusEnum.SEDANG_VALIDASI_KI);
        policyStatusEntity.setModifiedBy(userService.getSignedInUserId());
        policyStatusRepository.save(policyStatusEntity);
    }

    @Override
    public PolicySampleDto getAllPoliciesDisetujui() {
        AgencyEntity agencyEntity = agencyMasterService.getAgencyById(userService.getAgencyIdBySignedInUser());
        List<PolicyEntity> policyEntityList = policyRepository.findByAgency(agencyEntity);

        int totalPolicyDiajukan = 0;
        int totalPolicyDisetujui = 0;
        int totalPolicySample = 0;

        for (PolicyEntity policyEntity : policyEntityList) {
            if (
                    (
                            policyEntity.getPolicyProcessEntity().getName().name().equals(PolicyProcessEnum.DISETUJUI.name()) ||
                                    policyEntity.getPolicyProcessEntity().getName().name().equals(PolicyProcessEnum.PROSES.name()) ||
                                    policyEntity.getPolicyProcessEntity().getName().name().equals(PolicyProcessEnum.SELESAI.name())
                    )
                            &&
                    (
                            policyEntity.getPolicyStatusEntity().getName().name().equals(PolicyStatusEnum.SELESAI_VERIFIKASI.name()) ||
                                    policyEntity.getPolicyStatusEntity().getName().name().equals(PolicyStatusEnum.MENUNGGU_VALIDASI_AI.name()) ||
                                    policyEntity.getPolicyStatusEntity().getName().name().equals(PolicyStatusEnum.MENUNGGU_VALIDASI_KI.name()) ||
                                    policyEntity.getPolicyStatusEntity().getName().name().equals(PolicyStatusEnum.SEDANG_VALIDASI_KI.name()) ||
                                    policyEntity.getPolicyStatusEntity().getName().name().equals(PolicyStatusEnum.SELESAI_VALIDASI_KI.name()) ||
                                    policyEntity.getPolicyStatusEntity().getName().name().equals(PolicyStatusEnum.MENUNGGU_VALIDASI_KU.name()) ||
                                    policyEntity.getPolicyStatusEntity().getName().name().equals(PolicyStatusEnum.SEDANG_VALIDASI_KU.name()) ||
                                    policyEntity.getPolicyStatusEntity().getName().name().equals(PolicyStatusEnum.SELESAI_VALIDASI_KU.name()) ||
                                    policyEntity.getPolicyStatusEntity().getName().name().equals(PolicyStatusEnum.TELAH_DITERBITKAN.name())
                    ))
            {
                totalPolicyDisetujui += 1;
            }

            totalPolicyDiajukan += 1;
        }

        List<PolicyDto> policyDtoList = new ArrayList<>();
        List<PolicySampleEntity> policySampleEntities = policySampleRepository.findByIdUserAdmin(userService.getSignedInUserId());
        PolicySampleDto policySampleDto = new PolicySampleDto();
        if (policySampleEntities.size() == 0) {
            policySampleDto.setTotalKebijakanDiajukan(totalPolicyDiajukan);
            policySampleDto.setTotalKebijakanDisetujui(totalPolicyDisetujui);
            policySampleDto.setTotalSampleKebijakan(totalPolicySample);
            policySampleDto.setIsRandomized(false);
            policySampleDto.setSampleKebijakanList(policyDtoList);
        } else {
            for (PolicySampleEntity policySampleEntity : policySampleEntities) {
                PolicyEntity policyEntity = policyRepository.findById(policySampleEntity.getPolicyId())
                        .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

                PolicyDto policyDto = new PolicyDto();
                policyDto.setId(policyEntity.getId());
                policyDto.setNama(policyEntity.getName());
                policyDto.setTanggalBerlaku(policyEntity.getPolicyDetailsEntity().getEffectiveDate());
                policyDto.setTanggalVerifikasi(policyEntity.getVerifiedByKoordinatorAt());
                policyDto.setTanggalValidasiKi(policyEntity.getValidatedByKoordinatorAt());
                policyDto.setTanggalValidasiKu(policyEntity.getValidatedByKoordinatorUtamaAt());
                policyDto.setTanggalDiajukan(policyEntity.getSentByAdminAt());
                policyDto.setTanggalAssign(policyEntity.getAssignedByAdminAt());
                policyDto.setTanggalProses(policyEntity.getProcessedByEnumeratorAt());
                policyDto.setJenis(policyEntity.getPolicyDetailsEntity().getSector());
                policyDto.setProses(policyEntity.getPolicyStatusEntity().getName().name());
                policyDto.setStatus(policyEntity.getPolicyProcessEntity().getName().name());
                policyDto.setCreateBy(userMasterService.findById(policyEntity.getCreatedBy()).getId());

                if (policyEntity.getEnumeratorId() == null) {
                    policyDto.setEnumerator(null);
                } else {
                    GetUserDetailDto enumerator = userService.getById(policyEntity.getEnumeratorId());
                    policyDto.setEnumerator(enumerator);
                }

                policyDto.setInstansi(policyEntity.getAgency().getName());
                policyDto.setProgres(policyEntity.getPolicyDetailsEntity().getProgress());

                policyDtoList.add(policyDto);

                totalPolicySample += 1;
            }

            policySampleDto.setTotalKebijakanDiajukan(totalPolicyDiajukan);
            policySampleDto.setTotalKebijakanDisetujui(totalPolicyDisetujui);
            policySampleDto.setTotalSampleKebijakan(totalPolicySample);
            policySampleDto.setIsRandomized(true);
            policySampleDto.setSampleKebijakanList(policyDtoList);
        }

        return policySampleDto;
    }

    @Override
    public List<PolicySelesaiDto> getAllPoliciesSelesai() {
        AgencyEntity agencyEntity = agencyMasterService.getAgencyById(userService.getAgencyIdBySignedInUser());
        List<PolicyEntity> policyEntityList = policyRepository.findByAgency(agencyEntity);

        List<PolicySelesaiDto> policySelesaiDtoList = new ArrayList<>();
        for (PolicyEntity policyEntity : policyEntityList) {
            if (policyEntity.getPolicyProcessEntity().getName().name().equals(PolicyProcessEnum.SELESAI.name()) &&
                    policyEntity.getPolicyStatusEntity().getName().name().equals(PolicyStatusEnum.TELAH_DITERBITKAN.name())) {

                PolicySelesaiDto policySelesaiDto = new PolicySelesaiDto();
                policySelesaiDto.setId(policyEntity.getId());
                policySelesaiDto.setNama(policyEntity.getName());
                policySelesaiDto.setEnumerator(userService.getById(policyEntity.getEnumeratorId()).getNama());
                policySelesaiDto.setProses(policyEntity.getPolicyStatusEntity().getName().name());
                policySelesaiDto.setStatus(policyEntity.getPolicyProcessEntity().getName().name());

                if (policyEntity.getPolicyStatusEntity().getName().name().equals(PolicyStatusEnum.TELAH_DITERBITKAN.name())) {
                    policySelesaiDto.setNilai(policyEntity.getPolicyDetailsEntity().getValidationKUScore());
                } else {
                    policySelesaiDto.setNilai(0.0);
                }

                policySelesaiDtoList.add(policySelesaiDto);
            }
        }

        return policySelesaiDtoList;
    }

    @Override
    public void generatePolicySample() {
        AgencyEntity agencyEntity = agencyMasterService.getAgencyById(userService.getAgencyIdBySignedInUser());
        List<PolicyEntity> policyEntityList = policyRepository.findByAgency(agencyEntity);

        int totalPolicyDisetujui = 0;
        List<PolicyEntity> policyEntityDisetujuiList = new ArrayList<>();
        for (PolicyEntity policyEntity : policyEntityList) {
            if (policyEntity.getPolicyProcessEntity().getName().name().equals(PolicyProcessEnum.DISETUJUI.name())) {
                totalPolicyDisetujui += 1;
                policyEntityDisetujuiList.add(policyEntity);
            }
        }

        List<PolicySampleEntity> policySampleEntities = policySampleRepository.findByIdUserAdmin(userService.getSignedInUserId());

        if (policySampleEntities.size() == 0) {
            if (totalPolicyDisetujui <= 4) {
                for (PolicyEntity policyEntity : policyEntityDisetujuiList) {
                    PolicySampleEntity policySampleEntity = new PolicySampleEntity();
                    policySampleEntity.setIdUserAdmin(userService.getSignedInUserId());
                    policySampleEntity.setPolicyId(policyEntity.getId());
                    policySampleEntity.setCreatedBy(userService.getSignedInUserId());
                    policySampleEntity.setCreatedAt(new Date());
                    policySampleRepository.save(policySampleEntity);
                }
            } else {
                List<PolicyEntity> randomizedPolicySample = randomizePolicySampleProportional(policyEntityDisetujuiList);
                for (PolicyEntity policyEntity : randomizedPolicySample) {
                    PolicySampleEntity policySampleEntity = new PolicySampleEntity();
                    policySampleEntity.setIdUserAdmin(userService.getSignedInUserId());
                    policySampleEntity.setPolicyId(policyEntity.getId());
                    policySampleEntity.setCreatedBy(userService.getSignedInUserId());
                    policySampleEntity.setCreatedAt(new Date());
                    policySampleRepository.save(policySampleEntity);
                }
            }
        } else {
            throw new RandomizePolicySampleFailedException("Populasi sudah pernah disampling");
        }
    }

    @Override
    public void assignEnumerator(Long policyId, Long enumeratorId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        UserEntity userEntity = userMasterService.findById(enumeratorId);

        policyEntity.setAssignedByAdminAt(new Date());
        policyEntity.setAssignedByAdminId(userService.getSignedInUserId());
        policyEntity.setEnumeratorId(userEntity.getId());
        policyEntity.setModifiedBy(userService.getSignedInUserId());
        policyRepository.save(policyEntity);
    }

    @Override
    public Boolean isPolicyBeingValidated(PolicyEntity policy) {
        return policy.getPolicyStatusEntity().getName().equals(PolicyStatusEnum.SEDANG_VALIDASI_KI) ||
                policy.getPolicyStatusEntity().getName().equals(PolicyStatusEnum.SELESAI_VALIDASI_KI);
    }

    private List<PolicyEntity> randomizePolicySample(List<PolicyEntity> policyEntityList) {
        int totalPolicy = policyEntityList.size();
        int totalSample = (int) Math.floor(Math.sqrt(totalPolicy) + 1);

        SecureRandom random = new SecureRandom();
        List<PolicyEntity> randomizedPolicySampleList = new ArrayList<>();
        for (int i = 0; i < totalSample; i++) {
            int randomIndex = random.nextInt(policyEntityList.size());
            randomizedPolicySampleList.add(policyEntityList.get(randomIndex));
            policyEntityList.remove(randomIndex);
        }

        return randomizedPolicySampleList;
    }

    private List<PolicyEntity> randomizePolicySampleProportional(List<PolicyEntity> policyEntityList) {
        int totalPolicy = policyEntityList.size();
        int totalSample = (int) Math.floor(Math.sqrt(totalPolicy) + 1);

        Set<Integer> activeYearSet = new HashSet<>();
        for (PolicyEntity policyEntity : policyEntityList) {
            int effectiveYear = policyEntity.getPolicyDetailsEntity().getEffectiveDate().getYear() + 1900;
            activeYearSet.add(effectiveYear);
        }

        SecureRandom random = new SecureRandom();
        List<PolicyEntity> randomizedPolicySampleList = new ArrayList<>();
        for (int activeYear : activeYearSet) {

            List<PolicyEntity> policyPerYearList = new ArrayList<>();
            for (PolicyEntity policyEntity : policyEntityList) {
                if (policyEntity.getPolicyDetailsEntity().getEffectiveDate().getYear() + 1900 == activeYear) {
                    policyPerYearList.add(policyEntity);
                }
            }

            int totalSamplePerYear = Math.round(((float) policyPerYearList.size() / (float) totalPolicy) * (float) totalSample);
            for (int i = 0; i < totalSamplePerYear; i++) {
                int randomIndex = random.nextInt(policyPerYearList.size());
                randomizedPolicySampleList.add(policyPerYearList.get(randomIndex));
                policyPerYearList.remove(randomIndex);
            }
        }

        return randomizedPolicySampleList;
    }
}
