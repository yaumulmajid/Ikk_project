package id.go.lan.ikk.module.policy.service.implementation;

import id.go.lan.ikk.exception.ResourceNotFoundException;
import id.go.lan.ikk.exception.SubmitFailedException;
import id.go.lan.ikk.module.dashboard.presenter.model.AgendaSettingValidationRequest;
import id.go.lan.ikk.module.policy.entity.*;
import id.go.lan.ikk.module.policy.model.AgendaSettingDto;
import id.go.lan.ikk.module.policy.model.AgendaSettingValidationDto;
import id.go.lan.ikk.module.policy.repository.*;
import id.go.lan.ikk.module.policy.service.AgendaSettingService;
import id.go.lan.ikk.module.policy.service.PolicyDetailsService;
import id.go.lan.ikk.module.policy.service.PolicyService;
import id.go.lan.ikk.module.user.service.UserMasterService;
import id.go.lan.ikk.module.user.service.UserService;
import id.go.lan.ikk.utility.ModelMapperUtility;
import id.go.lan.ikk.utility.UploadFileUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AgendaSettingServiceImpl implements AgendaSettingService {

    @Autowired
    private ModelMapperUtility modelMapperUtility;

    @Autowired
    private UploadFileUtility uploadFileUtility;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private PolicyDetailsService policyDetailsService;

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
    private UserService userService;

    @Autowired
    private UserMasterService userMasterService;

    @Autowired
    private PolicyService policyService;

    @Override
    public AgendaSettingDto getAgendaSettingByPolicyId(Long id) {
        PolicyEntity policyEntity = policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        AgendaSettingEntity agendaSettingEntity = policyEntity.getAgendaSettingEntity();
        AgendaSettingFileEntity agendaSettingFileEntity = policyEntity.getAgendaSettingFileEntity();

        return mapAgendaSettingDto(policyEntity, agendaSettingEntity, agendaSettingFileEntity);
    }

    @Override
    public AgendaSettingDto submitA1A(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

//        if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        AgendaSettingEntity agendaSettingEntity = policyEntity.getAgendaSettingEntity();
        AgendaSettingFileEntity agendaSettingFileEntity = policyEntity.getAgendaSettingFileEntity();
        AgendaSettingBaseScoreEntity agendaSettingBaseScoreEntity = policyEntity.getAgendaSettingBaseScoreEntity();

        switch (answer) {
            case "a": agendaSettingBaseScoreEntity.setA1A(100.0);
            break;
            case "b": agendaSettingBaseScoreEntity.setA1A(75.0);
            break;
            case "c": agendaSettingBaseScoreEntity.setA1A(50.0);
            break;
            case "d": agendaSettingBaseScoreEntity.setA1A(25.0);
            break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        agendaSettingBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingBaseScoreRepository.save(agendaSettingBaseScoreEntity);

        if (file != null) {
            agendaSettingFileEntity.setFilePathA1A(uploadFileUtility.uploadFile(file, nipEnumerator));
            agendaSettingFileEntity.setFileTypeA1A(file.getContentType());
            agendaSettingFileEntity.setFileSizeA1A(String.valueOf(file.getSize() / 1024));
            agendaSettingFileEntity.setFileOriginalNameA1A(file.getOriginalFilename());
            agendaSettingFileEntity.setModifiedBy(userService.getSignedInUserId());
            agendaSettingFileRepository.save(agendaSettingFileEntity);
        }

        agendaSettingEntity.setA1A(answer);
        agendaSettingEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingRepository.save(agendaSettingEntity);

        agendaSettingBaseScoreEntity.setTotalScore(policyDetailsService.getAgendaSettingBaseScoreByPolicyId(policyId));
        agendaSettingBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingBaseScoreRepository.save(agendaSettingBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapAgendaSettingDto(policyEntity, agendaSettingEntity, agendaSettingFileEntity);
    }

    @Override
    public AgendaSettingDto submitA1B(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

//        if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        AgendaSettingEntity agendaSettingEntity = policyEntity.getAgendaSettingEntity();
        AgendaSettingFileEntity agendaSettingFileEntity = policyEntity.getAgendaSettingFileEntity();
        AgendaSettingBaseScoreEntity agendaSettingBaseScoreEntity = policyEntity.getAgendaSettingBaseScoreEntity();

        switch (answer) {
            case "a": agendaSettingBaseScoreEntity.setA1B(100.0);
                break;
            case "b": agendaSettingBaseScoreEntity.setA1B(75.0);
                break;
            case "c": agendaSettingBaseScoreEntity.setA1B(50.0);
                break;
            case "d": agendaSettingBaseScoreEntity.setA1B(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        agendaSettingBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingBaseScoreRepository.save(agendaSettingBaseScoreEntity);

        if (file != null) {
            agendaSettingFileEntity.setFilePathA1B(uploadFileUtility.uploadFile(file, nipEnumerator));
            agendaSettingFileEntity.setFileTypeA1B(file.getContentType());
            agendaSettingFileEntity.setFileSizeA1B(String.valueOf(file.getSize() / 1024));
            agendaSettingFileEntity.setFileOriginalNameA1B(file.getOriginalFilename());
            agendaSettingFileEntity.setModifiedBy(userService.getSignedInUserId());
            agendaSettingFileRepository.save(agendaSettingFileEntity);
        }

        agendaSettingEntity.setA1B(answer);
        agendaSettingEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingRepository.save(agendaSettingEntity);

        agendaSettingBaseScoreEntity.setTotalScore(policyDetailsService.getAgendaSettingBaseScoreByPolicyId(policyId));
        agendaSettingBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingBaseScoreRepository.save(agendaSettingBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapAgendaSettingDto(policyEntity, agendaSettingEntity, agendaSettingFileEntity);
    }

    @Override
    public AgendaSettingDto submitA1C(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

//        if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        AgendaSettingEntity agendaSettingEntity = policyEntity.getAgendaSettingEntity();
        AgendaSettingFileEntity agendaSettingFileEntity = policyEntity.getAgendaSettingFileEntity();
        AgendaSettingBaseScoreEntity agendaSettingBaseScoreEntity = policyEntity.getAgendaSettingBaseScoreEntity();

        switch (answer) {
            case "a": agendaSettingBaseScoreEntity.setA1C(100.0);
                break;
            case "b": agendaSettingBaseScoreEntity.setA1C(75.0);
                break;
            case "c": agendaSettingBaseScoreEntity.setA1C(50.0);
                break;
            case "d": agendaSettingBaseScoreEntity.setA1C(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        agendaSettingBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingBaseScoreRepository.save(agendaSettingBaseScoreEntity);

        if (file != null) {
            agendaSettingFileEntity.setFilePathA1C(uploadFileUtility.uploadFile(file, nipEnumerator));
            agendaSettingFileEntity.setFileTypeA1C(file.getContentType());
            agendaSettingFileEntity.setFileSizeA1C(String.valueOf(file.getSize() / 1024));
            agendaSettingFileEntity.setFileOriginalNameA1C(file.getOriginalFilename());
            agendaSettingFileEntity.setModifiedBy(userService.getSignedInUserId());
            agendaSettingFileRepository.save(agendaSettingFileEntity);
        }

        agendaSettingEntity.setA1C(answer);
        agendaSettingEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingRepository.save(agendaSettingEntity);

        agendaSettingBaseScoreEntity.setTotalScore(policyDetailsService.getAgendaSettingBaseScoreByPolicyId(policyId));
        agendaSettingBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingBaseScoreRepository.save(agendaSettingBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapAgendaSettingDto(policyEntity, agendaSettingEntity, agendaSettingFileEntity);
    }

    @Override
    public AgendaSettingDto submitA1D(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

//        if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        AgendaSettingEntity agendaSettingEntity = policyEntity.getAgendaSettingEntity();
        AgendaSettingFileEntity agendaSettingFileEntity = policyEntity.getAgendaSettingFileEntity();
        AgendaSettingBaseScoreEntity agendaSettingBaseScoreEntity = policyEntity.getAgendaSettingBaseScoreEntity();

        switch (answer) {
            case "a": agendaSettingBaseScoreEntity.setA1D(100.0);
                break;
            case "b": agendaSettingBaseScoreEntity.setA1D(75.0);
                break;
            case "c": agendaSettingBaseScoreEntity.setA1D(50.0);
                break;
            case "d": agendaSettingBaseScoreEntity.setA1D(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        agendaSettingBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingBaseScoreRepository.save(agendaSettingBaseScoreEntity);

        if (file != null) {
            agendaSettingFileEntity.setFilePathA1D(uploadFileUtility.uploadFile(file, nipEnumerator));
            agendaSettingFileEntity.setFileTypeA1D(file.getContentType());
            agendaSettingFileEntity.setFileSizeA1D(String.valueOf(file.getSize() / 1024));
            agendaSettingFileEntity.setFileOriginalNameA1D(file.getOriginalFilename());
            agendaSettingFileEntity.setModifiedBy(userService.getSignedInUserId());
            agendaSettingFileRepository.save(agendaSettingFileEntity);
        }

        agendaSettingEntity.setA1D(answer);
        agendaSettingEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingRepository.save(agendaSettingEntity);

        agendaSettingBaseScoreEntity.setTotalScore(policyDetailsService.getAgendaSettingBaseScoreByPolicyId(policyId));
        agendaSettingBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingBaseScoreRepository.save(agendaSettingBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapAgendaSettingDto(policyEntity, agendaSettingEntity, agendaSettingFileEntity);
    }

    @Override
    public AgendaSettingDto submitA2A(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

//        if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        AgendaSettingEntity agendaSettingEntity = policyEntity.getAgendaSettingEntity();
        AgendaSettingFileEntity agendaSettingFileEntity = policyEntity.getAgendaSettingFileEntity();
        AgendaSettingBaseScoreEntity agendaSettingBaseScoreEntity = policyEntity.getAgendaSettingBaseScoreEntity();

        switch (answer) {
            case "a": agendaSettingBaseScoreEntity.setA2A(100.0);
                break;
            case "b": agendaSettingBaseScoreEntity.setA2A(75.0);
                break;
            case "c": agendaSettingBaseScoreEntity.setA1A(50.0);
                break;
            case "d": agendaSettingBaseScoreEntity.setA2A(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        agendaSettingBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingBaseScoreRepository.save(agendaSettingBaseScoreEntity);

        if (file != null) {
            agendaSettingFileEntity.setFilePathA2A(uploadFileUtility.uploadFile(file, nipEnumerator));
            agendaSettingFileEntity.setFileTypeA2A(file.getContentType());
            agendaSettingFileEntity.setFileSizeA2A(String.valueOf(file.getSize() / 1024));
            agendaSettingFileEntity.setFileOriginalNameA2A(file.getOriginalFilename());
            agendaSettingFileEntity.setModifiedBy(userService.getSignedInUserId());
            agendaSettingFileRepository.save(agendaSettingFileEntity);
        }

        agendaSettingEntity.setA2A(answer);
        agendaSettingEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingRepository.save(agendaSettingEntity);

        agendaSettingBaseScoreEntity.setTotalScore(policyDetailsService.getAgendaSettingBaseScoreByPolicyId(policyId));
        agendaSettingBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingBaseScoreRepository.save(agendaSettingBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapAgendaSettingDto(policyEntity, agendaSettingEntity, agendaSettingFileEntity);
    }

    @Override
    public AgendaSettingDto submitA2B(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

//        if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        AgendaSettingEntity agendaSettingEntity = policyEntity.getAgendaSettingEntity();
        AgendaSettingFileEntity agendaSettingFileEntity = policyEntity.getAgendaSettingFileEntity();
        AgendaSettingBaseScoreEntity agendaSettingBaseScoreEntity = policyEntity.getAgendaSettingBaseScoreEntity();

        switch (answer) {
            case "a": agendaSettingBaseScoreEntity.setA2B(100.0);
                break;
            case "b": agendaSettingBaseScoreEntity.setA2B(75.0);
                break;
            case "c": agendaSettingBaseScoreEntity.setA2B(50.0);
                break;
            case "d": agendaSettingBaseScoreEntity.setA2B(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        agendaSettingBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingBaseScoreRepository.save(agendaSettingBaseScoreEntity);

        if (file != null) {
            agendaSettingFileEntity.setFilePathA2B(uploadFileUtility.uploadFile(file, nipEnumerator));
            agendaSettingFileEntity.setFileTypeA2B(file.getContentType());
            agendaSettingFileEntity.setFileSizeA2B(String.valueOf(file.getSize() / 1024));
            agendaSettingFileEntity.setFileOriginalNameA2B(file.getOriginalFilename());
            agendaSettingFileEntity.setModifiedBy(userService.getSignedInUserId());
            agendaSettingFileRepository.save(agendaSettingFileEntity);
        }

        agendaSettingEntity.setA2B(answer);
        agendaSettingEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingRepository.save(agendaSettingEntity);

        agendaSettingBaseScoreEntity.setTotalScore(policyDetailsService.getAgendaSettingBaseScoreByPolicyId(policyId));
        agendaSettingBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingBaseScoreRepository.save(agendaSettingBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapAgendaSettingDto(policyEntity, agendaSettingEntity, agendaSettingFileEntity);
    }

    @Override
    public AgendaSettingDto submitA2C(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

//        if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        AgendaSettingEntity agendaSettingEntity = policyEntity.getAgendaSettingEntity();
        AgendaSettingFileEntity agendaSettingFileEntity = policyEntity.getAgendaSettingFileEntity();
        AgendaSettingBaseScoreEntity agendaSettingBaseScoreEntity = policyEntity.getAgendaSettingBaseScoreEntity();

        List<String> a2CAnswerList;

        if (answer == null) {
            a2CAnswerList = new ArrayList<>();
        } else {
            a2CAnswerList = Stream.of(answer.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
        }

        agendaSettingBaseScoreEntity.setA2C(0.0);
        for (String a2c : a2CAnswerList) {
            switch (a2c) {
                case "a":
                case "b":
                case "c":
                case "d":
                    agendaSettingBaseScoreEntity.setA2C(agendaSettingBaseScoreEntity.getA2C() + 25.0);
                break;
                default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
            }
        }

        agendaSettingBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingBaseScoreRepository.save(agendaSettingBaseScoreEntity);

        if (file != null) {
            agendaSettingFileEntity.setFilePathA2C(uploadFileUtility.uploadFile(file, nipEnumerator));
            agendaSettingFileEntity.setFileTypeA2C(file.getContentType());
            agendaSettingFileEntity.setFileSizeA2C(String.valueOf(file.getSize() / 1024));
            agendaSettingFileEntity.setFileOriginalNameA2C(file.getOriginalFilename());
            agendaSettingFileEntity.setModifiedBy(userService.getSignedInUserId());
            agendaSettingFileRepository.save(agendaSettingFileEntity);
        }

        agendaSettingEntity.setA2C(answer);
        agendaSettingEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingRepository.save(agendaSettingEntity);

        agendaSettingBaseScoreEntity.setTotalScore(policyDetailsService.getAgendaSettingBaseScoreByPolicyId(policyId));
        agendaSettingBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingBaseScoreRepository.save(agendaSettingBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapAgendaSettingDto(policyEntity, agendaSettingEntity, agendaSettingFileEntity);
    }

    @Override
    public AgendaSettingDto submitInformasiA3(Long policyId, String answer) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

//        if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        AgendaSettingEntity agendaSettingEntity = policyEntity.getAgendaSettingEntity();
        AgendaSettingFileEntity agendaSettingFileEntity = policyEntity.getAgendaSettingFileEntity();

        agendaSettingEntity.setInformasiA3(answer);
        agendaSettingEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingRepository.save(agendaSettingEntity);

        return mapAgendaSettingDto(policyEntity, agendaSettingEntity, agendaSettingFileEntity);
    }

    @Override
    public AgendaSettingValidationDto getValidationKI(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        AgendaSettingKIEntity agendaSettingKIEntity = policyEntity.getAgendaSettingKIEntity();
        return modelMapperUtility.initialize().map(agendaSettingKIEntity, AgendaSettingValidationDto.class);
    }

    @Override
    public AgendaSettingValidationDto getValidationKU(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        AgendaSettingKUEntity agendaSettingKUEntity = policyEntity.getAgendaSettingKUEntity();
        return modelMapperUtility.initialize().map(agendaSettingKUEntity, AgendaSettingValidationDto.class);
    }

    @Override
    public void submitValidasiKI(Long policyId, AgendaSettingValidationRequest agendaSettingValidationRequest) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        AgendaSettingKIEntity agendaSettingKIEntity = policyEntity.getAgendaSettingKIEntity();
        AgendaSettingBaseScoreEntity agendaSettingBaseScoreEntity = policyEntity.getAgendaSettingBaseScoreEntity();
        AgendaSettingKIScoreEntity agendaSettingKIScoreEntity = policyEntity.getAgendaSettingKIScoreEntity();

        switch (agendaSettingValidationRequest.getA1A()) {
            case "1": agendaSettingKIScoreEntity.setA1A(agendaSettingBaseScoreEntity.getA1A() * 0/100);
                break;
            case "2": agendaSettingKIScoreEntity.setA1A(agendaSettingBaseScoreEntity.getA1A() * 25/100);
                break;
            case "3": agendaSettingKIScoreEntity.setA1A(agendaSettingBaseScoreEntity.getA1A() * 50/100);
                break;
            case "4": agendaSettingKIScoreEntity.setA1A(agendaSettingBaseScoreEntity.getA1A() * 75/100);
                break;
            case "5": agendaSettingKIScoreEntity.setA1A(agendaSettingBaseScoreEntity.getA1A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (agendaSettingValidationRequest.getA1B()) {
            case "1": agendaSettingKIScoreEntity.setA1B(agendaSettingBaseScoreEntity.getA1B() * 0/100);
                break;
            case "2": agendaSettingKIScoreEntity.setA1B(agendaSettingBaseScoreEntity.getA1B() * 25/100);
                break;
            case "3": agendaSettingKIScoreEntity.setA1B(agendaSettingBaseScoreEntity.getA1B() * 50/100);
                break;
            case "4": agendaSettingKIScoreEntity.setA1B(agendaSettingBaseScoreEntity.getA1B() * 75/100);
                break;
            case "5": agendaSettingKIScoreEntity.setA1B(agendaSettingBaseScoreEntity.getA1B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (agendaSettingValidationRequest.getA1C()) {
            case "1": agendaSettingKIScoreEntity.setA1C(agendaSettingBaseScoreEntity.getA1C() * 0/100);
                break;
            case "2": agendaSettingKIScoreEntity.setA1C(agendaSettingBaseScoreEntity.getA1C() * 25/100);
                break;
            case "3": agendaSettingKIScoreEntity.setA1C(agendaSettingBaseScoreEntity.getA1C() * 50/100);
                break;
            case "4": agendaSettingKIScoreEntity.setA1C(agendaSettingBaseScoreEntity.getA1C() * 75/100);
                break;
            case "5": agendaSettingKIScoreEntity.setA1C(agendaSettingBaseScoreEntity.getA1C() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (agendaSettingValidationRequest.getA1D()) {
            case "1": agendaSettingKIScoreEntity.setA1D(agendaSettingBaseScoreEntity.getA1D() * 0/100);
                break;
            case "2": agendaSettingKIScoreEntity.setA1D(agendaSettingBaseScoreEntity.getA1D() * 25/100);
                break;
            case "3": agendaSettingKIScoreEntity.setA1D(agendaSettingBaseScoreEntity.getA1D() * 50/100);
                break;
            case "4": agendaSettingKIScoreEntity.setA1D(agendaSettingBaseScoreEntity.getA1D() * 75/100);
                break;
            case "5": agendaSettingKIScoreEntity.setA1D(agendaSettingBaseScoreEntity.getA1D() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (agendaSettingValidationRequest.getA2A()) {
            case "1": agendaSettingKIScoreEntity.setA2A(agendaSettingBaseScoreEntity.getA2A() * 0/100);
                break;
            case "2": agendaSettingKIScoreEntity.setA2A(agendaSettingBaseScoreEntity.getA2A() * 25/100);
                break;
            case "3": agendaSettingKIScoreEntity.setA2A(agendaSettingBaseScoreEntity.getA2A() * 50/100);
                break;
            case "4": agendaSettingKIScoreEntity.setA2A(agendaSettingBaseScoreEntity.getA2A() * 75/100);
                break;
            case "5": agendaSettingKIScoreEntity.setA2A(agendaSettingBaseScoreEntity.getA2A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (agendaSettingValidationRequest.getA2B()) {
            case "1": agendaSettingKIScoreEntity.setA2B(agendaSettingBaseScoreEntity.getA2B() * 0/100);
                break;
            case "2": agendaSettingKIScoreEntity.setA2B(agendaSettingBaseScoreEntity.getA2B() * 25/100);
                break;
            case "3": agendaSettingKIScoreEntity.setA2B(agendaSettingBaseScoreEntity.getA2B() * 50/100);
                break;
            case "4": agendaSettingKIScoreEntity.setA2B(agendaSettingBaseScoreEntity.getA2B() * 75/100);
                break;
            case "5": agendaSettingKIScoreEntity.setA2B(agendaSettingBaseScoreEntity.getA2B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (agendaSettingValidationRequest.getA2C()) {
            case "1": agendaSettingKIScoreEntity.setA2C(agendaSettingBaseScoreEntity.getA2C() * 0/100);
                break;
            case "2": agendaSettingKIScoreEntity.setA2C(agendaSettingBaseScoreEntity.getA2C() * 25/100);
                break;
            case "3": agendaSettingKIScoreEntity.setA2C(agendaSettingBaseScoreEntity.getA2C() * 50/100);
                break;
            case "4": agendaSettingKIScoreEntity.setA2C(agendaSettingBaseScoreEntity.getA2C() * 75/100);
                break;
            case "5": agendaSettingKIScoreEntity.setA2C(agendaSettingBaseScoreEntity.getA2C() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        if (!agendaSettingValidationRequest.getA1A().isEmpty()) {
            agendaSettingKIEntity.setA1A(agendaSettingValidationRequest.getA1A());
        }

        if (!agendaSettingValidationRequest.getA1B().isEmpty()) {
            agendaSettingKIEntity.setA1B(agendaSettingValidationRequest.getA1B());
        }

        if (!agendaSettingValidationRequest.getA1C().isEmpty()) {
            agendaSettingKIEntity.setA1C(agendaSettingValidationRequest.getA1C());
        }

        if (!agendaSettingValidationRequest.getA1D().isEmpty()) {
            agendaSettingKIEntity.setA1D(agendaSettingValidationRequest.getA1D());
        }

        if (!agendaSettingValidationRequest.getA2A().isEmpty()) {
            agendaSettingKIEntity.setA2A(agendaSettingValidationRequest.getA2A());
        }

        if (!agendaSettingValidationRequest.getA2B().isEmpty()) {
            agendaSettingKIEntity.setA2B(agendaSettingValidationRequest.getA2B());
        }

        if (!agendaSettingValidationRequest.getA2C().isEmpty()) {
            agendaSettingKIEntity.setA2C(agendaSettingValidationRequest.getA2C());
        }

        agendaSettingKIEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingKIRepository.save(agendaSettingKIEntity);

        agendaSettingKIScoreEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingKIScoreRepository.save(agendaSettingKIScoreEntity);

        agendaSettingKIScoreEntity.setTotalScore(policyDetailsService.getAgendaSettingValidationKIScoreByPolicyId(policyId));
        agendaSettingKIScoreEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingKIScoreRepository.save(agendaSettingKIScoreEntity);

        policyDetailsService.updatePolicyValidationKIScoreByPolicyId(policyId);
    }

    @Override
    public void submitValidasiKU(Long policyId, AgendaSettingValidationRequest agendaSettingValidationRequest) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        AgendaSettingKUEntity agendaSettingKUEntity = policyEntity.getAgendaSettingKUEntity();
        AgendaSettingBaseScoreEntity agendaSettingBaseScoreEntity = policyEntity.getAgendaSettingBaseScoreEntity();
        AgendaSettingKUScoreEntity agendaSettingKUScoreEntity = policyEntity.getAgendaSettingKUScoreEntity();

        switch (agendaSettingValidationRequest.getA1A()) {
            case "1": agendaSettingKUScoreEntity.setA1A(agendaSettingBaseScoreEntity.getA1A() * 0/100);
                break;
            case "2": agendaSettingKUScoreEntity.setA1A(agendaSettingBaseScoreEntity.getA1A() * 25/100);
                break;
            case "3": agendaSettingKUScoreEntity.setA1A(agendaSettingBaseScoreEntity.getA1A() * 50/100);
                break;
            case "4": agendaSettingKUScoreEntity.setA1A(agendaSettingBaseScoreEntity.getA1A() * 75/100);
                break;
            case "5": agendaSettingKUScoreEntity.setA1A(agendaSettingBaseScoreEntity.getA1A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (agendaSettingValidationRequest.getA1B()) {
            case "1": agendaSettingKUScoreEntity.setA1B(agendaSettingBaseScoreEntity.getA1B() * 0/100);
                break;
            case "2": agendaSettingKUScoreEntity.setA1B(agendaSettingBaseScoreEntity.getA1B() * 25/100);
                break;
            case "3": agendaSettingKUScoreEntity.setA1B(agendaSettingBaseScoreEntity.getA1B() * 50/100);
                break;
            case "4": agendaSettingKUScoreEntity.setA1B(agendaSettingBaseScoreEntity.getA1B() * 75/100);
                break;
            case "5": agendaSettingKUScoreEntity.setA1B(agendaSettingBaseScoreEntity.getA1B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (agendaSettingValidationRequest.getA1C()) {
            case "1": agendaSettingKUScoreEntity.setA1C(agendaSettingBaseScoreEntity.getA1C() * 0/100);
                break;
            case "2": agendaSettingKUScoreEntity.setA1C(agendaSettingBaseScoreEntity.getA1C() * 25/100);
                break;
            case "3": agendaSettingKUScoreEntity.setA1C(agendaSettingBaseScoreEntity.getA1C() * 50/100);
                break;
            case "4": agendaSettingKUScoreEntity.setA1C(agendaSettingBaseScoreEntity.getA1C() * 75/100);
                break;
            case "5": agendaSettingKUScoreEntity.setA1C(agendaSettingBaseScoreEntity.getA1C() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (agendaSettingValidationRequest.getA1D()) {
            case "1": agendaSettingKUScoreEntity.setA1D(agendaSettingBaseScoreEntity.getA1D() * 0/100);
                break;
            case "2": agendaSettingKUScoreEntity.setA1D(agendaSettingBaseScoreEntity.getA1D() * 25/100);
                break;
            case "3": agendaSettingKUScoreEntity.setA1D(agendaSettingBaseScoreEntity.getA1D() * 50/100);
                break;
            case "4": agendaSettingKUScoreEntity.setA1D(agendaSettingBaseScoreEntity.getA1D() * 75/100);
                break;
            case "5": agendaSettingKUScoreEntity.setA1D(agendaSettingBaseScoreEntity.getA1D() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (agendaSettingValidationRequest.getA2A()) {
            case "1": agendaSettingKUScoreEntity.setA2A(agendaSettingBaseScoreEntity.getA2A() * 0/100);
                break;
            case "2": agendaSettingKUScoreEntity.setA2A(agendaSettingBaseScoreEntity.getA2A() * 25/100);
                break;
            case "3": agendaSettingKUScoreEntity.setA2A(agendaSettingBaseScoreEntity.getA2A() * 50/100);
                break;
            case "4": agendaSettingKUScoreEntity.setA2A(agendaSettingBaseScoreEntity.getA2A() * 75/100);
                break;
            case "5": agendaSettingKUScoreEntity.setA2A(agendaSettingBaseScoreEntity.getA2A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (agendaSettingValidationRequest.getA2B()) {
            case "1": agendaSettingKUScoreEntity.setA2B(agendaSettingBaseScoreEntity.getA2B() * 0/100);
                break;
            case "2": agendaSettingKUScoreEntity.setA2B(agendaSettingBaseScoreEntity.getA2B() * 25/100);
                break;
            case "3": agendaSettingKUScoreEntity.setA2B(agendaSettingBaseScoreEntity.getA2B() * 50/100);
                break;
            case "4": agendaSettingKUScoreEntity.setA2B(agendaSettingBaseScoreEntity.getA2B() * 75/100);
                break;
            case "5": agendaSettingKUScoreEntity.setA2B(agendaSettingBaseScoreEntity.getA2B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (agendaSettingValidationRequest.getA2C()) {
            case "1": agendaSettingKUScoreEntity.setA2C(agendaSettingBaseScoreEntity.getA2C() * 0/100);
                break;
            case "2": agendaSettingKUScoreEntity.setA2C(agendaSettingBaseScoreEntity.getA2C() * 25/100);
                break;
            case "3": agendaSettingKUScoreEntity.setA2C(agendaSettingBaseScoreEntity.getA2C() * 50/100);
                break;
            case "4": agendaSettingKUScoreEntity.setA2C(agendaSettingBaseScoreEntity.getA2C() * 75/100);
                break;
            case "5": agendaSettingKUScoreEntity.setA2C(agendaSettingBaseScoreEntity.getA2C() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        if (!agendaSettingValidationRequest.getA1A().isEmpty()) {
            agendaSettingKUEntity.setA1A(agendaSettingValidationRequest.getA1A());
        }

        if (!agendaSettingValidationRequest.getA1B().isEmpty()) {
            agendaSettingKUEntity.setA1B(agendaSettingValidationRequest.getA1B());
        }

        if (!agendaSettingValidationRequest.getA1C().isEmpty()) {
            agendaSettingKUEntity.setA1C(agendaSettingValidationRequest.getA1C());
        }

        if (!agendaSettingValidationRequest.getA1D().isEmpty()) {
            agendaSettingKUEntity.setA1D(agendaSettingValidationRequest.getA1D());
        }

        if (!agendaSettingValidationRequest.getA2A().isEmpty()) {
            agendaSettingKUEntity.setA2A(agendaSettingValidationRequest.getA2A());
        }

        if (!agendaSettingValidationRequest.getA2B().isEmpty()) {
            agendaSettingKUEntity.setA2B(agendaSettingValidationRequest.getA2B());
        }

        if (!agendaSettingValidationRequest.getA2C().isEmpty()) {
            agendaSettingKUEntity.setA2C(agendaSettingValidationRequest.getA2C());
        }

        agendaSettingKUEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingKURepository.save(agendaSettingKUEntity);

        agendaSettingKUScoreEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingKUScoreRepository.save(agendaSettingKUScoreEntity);

        agendaSettingKUScoreEntity.setTotalScore(policyDetailsService.getAgendaSettingValidationKUScoreByPolicyId(policyId));
        agendaSettingKUScoreEntity.setModifiedBy(userService.getSignedInUserId());
        agendaSettingKUScoreRepository.save(agendaSettingKUScoreEntity);

        policyDetailsService.updatePolicyValidationKUScoreByPolicyId(policyId);
    }

    @Override
    public AgendaSettingDto mapAgendaSettingDto(
            PolicyEntity policyEntity,
            AgendaSettingEntity agendaSettingEntity,
            AgendaSettingFileEntity agendaSettingFileEntity) {
        AgendaSettingDto agendaSettingDto = new AgendaSettingDto();

        agendaSettingDto.setIdKebijakan(policyEntity.getId());
        agendaSettingDto.setIdAgendaSetting(agendaSettingEntity.getId());

        agendaSettingDto.setA1A(agendaSettingEntity.getA1A());
        agendaSettingDto.setA1B(agendaSettingEntity.getA1B());
        agendaSettingDto.setA1C(agendaSettingEntity.getA1C());
        agendaSettingDto.setA1D(agendaSettingEntity.getA1D());
        agendaSettingDto.setA2A(agendaSettingEntity.getA2A());
        agendaSettingDto.setA2B(agendaSettingEntity.getA2B());
        agendaSettingDto.setA2C(agendaSettingEntity.getA2C());
        agendaSettingDto.setInformasiA3(agendaSettingEntity.getInformasiA3());

        agendaSettingDto.setFilePathA1A(agendaSettingFileEntity.getFilePathA1A());
        agendaSettingDto.setFileTypeA1A(agendaSettingFileEntity.getFileTypeA1A());
        agendaSettingDto.setFileSizeA1A(agendaSettingFileEntity.getFileSizeA1A());
        agendaSettingDto.setFileOriginalNameA1A(agendaSettingFileEntity.getFileOriginalNameA1A());

        agendaSettingDto.setFilePathA1B(agendaSettingFileEntity.getFilePathA1B());
        agendaSettingDto.setFileTypeA1B(agendaSettingFileEntity.getFileTypeA1B());
        agendaSettingDto.setFileSizeA1B(agendaSettingFileEntity.getFileSizeA1B());
        agendaSettingDto.setFileOriginalNameA1B(agendaSettingFileEntity.getFileOriginalNameA1B());

        agendaSettingDto.setFilePathA1C(agendaSettingFileEntity.getFilePathA1C());
        agendaSettingDto.setFileTypeA1C(agendaSettingFileEntity.getFileTypeA1C());
        agendaSettingDto.setFileSizeA1C(agendaSettingFileEntity.getFileSizeA1C());
        agendaSettingDto.setFileOriginalNameA1C(agendaSettingFileEntity.getFileOriginalNameA1C());

        agendaSettingDto.setFilePathA1D(agendaSettingFileEntity.getFilePathA1D());
        agendaSettingDto.setFileTypeA1D(agendaSettingFileEntity.getFileTypeA1D());
        agendaSettingDto.setFileSizeA1D(agendaSettingFileEntity.getFileSizeA1D());
        agendaSettingDto.setFileOriginalNameA1D(agendaSettingFileEntity.getFileOriginalNameA1D());

        agendaSettingDto.setFilePathA2A(agendaSettingFileEntity.getFilePathA2A());
        agendaSettingDto.setFileTypeA2A(agendaSettingFileEntity.getFileTypeA2A());
        agendaSettingDto.setFileSizeA2A(agendaSettingFileEntity.getFileSizeA2A());
        agendaSettingDto.setFileOriginalNameA2A(agendaSettingFileEntity.getFileOriginalNameA2A());

        agendaSettingDto.setFilePathA2B(agendaSettingFileEntity.getFilePathA2B());
        agendaSettingDto.setFileTypeA2B(agendaSettingFileEntity.getFileTypeA2B());
        agendaSettingDto.setFileSizeA2B(agendaSettingFileEntity.getFileSizeA2B());
        agendaSettingDto.setFileOriginalNameA2B(agendaSettingFileEntity.getFileOriginalNameA2B());

        agendaSettingDto.setFilePathA2C(agendaSettingFileEntity.getFilePathA2C());
        agendaSettingDto.setFileTypeA2C(agendaSettingFileEntity.getFileTypeA2C());
        agendaSettingDto.setFileSizeA2C(agendaSettingFileEntity.getFileSizeA2C());
        agendaSettingDto.setFileOriginalNameA2C(agendaSettingFileEntity.getFileOriginalNameA2C());

        return agendaSettingDto;
    }
}
