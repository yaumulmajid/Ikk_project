package id.go.lan.ikk.module.policy.service.implementation;

import id.go.lan.ikk.exception.ResourceNotFoundException;
import id.go.lan.ikk.exception.SubmitFailedException;
import id.go.lan.ikk.module.dashboard.presenter.model.FormulasiKebijakanValidationRequest;
import id.go.lan.ikk.module.policy.entity.*;
import id.go.lan.ikk.module.policy.model.FormulasiKebijakanDto;
import id.go.lan.ikk.module.policy.model.FormulasiKebijakanValidationDto;
import id.go.lan.ikk.module.policy.repository.*;
import id.go.lan.ikk.module.policy.service.FormulasiKebijakanService;
import id.go.lan.ikk.module.policy.service.PolicyDetailsService;
import id.go.lan.ikk.module.policy.service.PolicyService;
import id.go.lan.ikk.module.user.service.UserMasterService;
import id.go.lan.ikk.module.user.service.UserService;
import id.go.lan.ikk.utility.ModelMapperUtility;
import id.go.lan.ikk.utility.UploadFileUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FormulasiKebijakanServiceImpl implements FormulasiKebijakanService {

    @Autowired
    private ModelMapperUtility modelMapperUtility;

    @Autowired
    private UploadFileUtility uploadFileUtility;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private PolicyDetailsService policyDetailsService;

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
    private UserService userService;

    @Autowired
    private UserMasterService userMasterService;

    @Autowired
    private PolicyService policyService;

    @Override
    public FormulasiKebijakanDto getFormulasiKebijakanByPolicyId(Long id) {
        PolicyEntity policyEntity = policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        FormulasiKebijakanEntity formulasiKebijakanEntity = policyEntity.getFormulasiKebijakanEntity();
        FormulasiKebijakanFileEntity formulasiKebijakanFileEntity = policyEntity.getFormulasiKebijakanFileEntity();

        return mapFormulasiKebijakanDto(policyEntity, formulasiKebijakanEntity, formulasiKebijakanFileEntity);
    }

    @Override
    public FormulasiKebijakanDto submitB1A(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        FormulasiKebijakanEntity formulasiKebijakanEntity = policyEntity.getFormulasiKebijakanEntity();
        FormulasiKebijakanFileEntity formulasiKebijakanFileEntity = policyEntity.getFormulasiKebijakanFileEntity();
        FormulasiKebijakanBaseScoreEntity formulasiKebijakanBaseScoreEntity = policyEntity.getFormulasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": formulasiKebijakanBaseScoreEntity.setB1A(100.0);
                break;
            case "b": formulasiKebijakanBaseScoreEntity.setB1A(75.0);
                break;
            case "c": formulasiKebijakanBaseScoreEntity.setB1A(50.0);
                break;
            case "d": formulasiKebijakanBaseScoreEntity.setB1A(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        if (file != null) {
            formulasiKebijakanFileEntity.setFilePathB1A(uploadFileUtility.uploadFile(file, nipEnumerator));
            formulasiKebijakanFileEntity.setFileTypeB1A(file.getContentType());
            formulasiKebijakanFileEntity.setFileSizeB1A(String.valueOf(file.getSize() / 1024));
            formulasiKebijakanFileEntity.setFileOriginalNameB1A(file.getOriginalFilename());
            formulasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            formulasiKebijakanFileRepository.save(formulasiKebijakanFileEntity);
        }

        formulasiKebijakanEntity.setB1A(answer);
        formulasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanRepository.save(formulasiKebijakanEntity);

        formulasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getFormulasiKebijakanBaseScoreByPolicyId(policyId));
        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapFormulasiKebijakanDto(policyEntity, formulasiKebijakanEntity, formulasiKebijakanFileEntity);
    }

    @Override
    public FormulasiKebijakanDto submitB1B(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        FormulasiKebijakanEntity formulasiKebijakanEntity = policyEntity.getFormulasiKebijakanEntity();
        FormulasiKebijakanFileEntity formulasiKebijakanFileEntity = policyEntity.getFormulasiKebijakanFileEntity();
        FormulasiKebijakanBaseScoreEntity formulasiKebijakanBaseScoreEntity = policyEntity.getFormulasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": formulasiKebijakanBaseScoreEntity.setB1B(100.0);
                break;
            case "b": formulasiKebijakanBaseScoreEntity.setB1B(75.0);
                break;
            case "c": formulasiKebijakanBaseScoreEntity.setB1B(50.0);
                break;
            case "d": formulasiKebijakanBaseScoreEntity.setB1B(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        if (file != null) {
            formulasiKebijakanFileEntity.setFilePathB1B(uploadFileUtility.uploadFile(file, nipEnumerator));
            formulasiKebijakanFileEntity.setFileTypeB1B(file.getContentType());
            formulasiKebijakanFileEntity.setFileSizeB1B(String.valueOf(file.getSize() / 1024));
            formulasiKebijakanFileEntity.setFileOriginalNameB1B(file.getOriginalFilename());
            formulasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            formulasiKebijakanFileRepository.save(formulasiKebijakanFileEntity);
        }

        formulasiKebijakanEntity.setB1B(answer);
        formulasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanRepository.save(formulasiKebijakanEntity);

        formulasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getFormulasiKebijakanBaseScoreByPolicyId(policyId));
        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapFormulasiKebijakanDto(policyEntity, formulasiKebijakanEntity, formulasiKebijakanFileEntity);
    }

    @Override
    public FormulasiKebijakanDto submitB2A(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        FormulasiKebijakanEntity formulasiKebijakanEntity = policyEntity.getFormulasiKebijakanEntity();
        FormulasiKebijakanFileEntity formulasiKebijakanFileEntity = policyEntity.getFormulasiKebijakanFileEntity();
        FormulasiKebijakanBaseScoreEntity formulasiKebijakanBaseScoreEntity = policyEntity.getFormulasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": formulasiKebijakanBaseScoreEntity.setB2A(100.0);
                break;
            case "b": formulasiKebijakanBaseScoreEntity.setB2A(75.0);
                break;
            case "c": formulasiKebijakanBaseScoreEntity.setB2A(50.0);
                break;
            case "d": formulasiKebijakanBaseScoreEntity.setB2A(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        if (file != null) {
            formulasiKebijakanFileEntity.setFilePathB2A(uploadFileUtility.uploadFile(file, nipEnumerator));
            formulasiKebijakanFileEntity.setFileTypeB2A(file.getContentType());
            formulasiKebijakanFileEntity.setFileSizeB2A(String.valueOf(file.getSize() / 1024));
            formulasiKebijakanFileEntity.setFileOriginalNameB2A(file.getOriginalFilename());
            formulasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            formulasiKebijakanFileRepository.save(formulasiKebijakanFileEntity);
        }

        formulasiKebijakanEntity.setB2A(answer);
        formulasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanRepository.save(formulasiKebijakanEntity);

        formulasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getFormulasiKebijakanBaseScoreByPolicyId(policyId));
        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapFormulasiKebijakanDto(policyEntity, formulasiKebijakanEntity, formulasiKebijakanFileEntity);
    }

    @Override
    public FormulasiKebijakanDto submitB2B(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        FormulasiKebijakanEntity formulasiKebijakanEntity = policyEntity.getFormulasiKebijakanEntity();
        FormulasiKebijakanFileEntity formulasiKebijakanFileEntity = policyEntity.getFormulasiKebijakanFileEntity();
        FormulasiKebijakanBaseScoreEntity formulasiKebijakanBaseScoreEntity = policyEntity.getFormulasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": formulasiKebijakanBaseScoreEntity.setB2B(100.0);
                break;
            case "b": formulasiKebijakanBaseScoreEntity.setB2B(75.0);
                break;
            case "c": formulasiKebijakanBaseScoreEntity.setB2B(50.0);
                break;
            case "d": formulasiKebijakanBaseScoreEntity.setB2B(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        if (file != null) {
            formulasiKebijakanFileEntity.setFilePathB2B(uploadFileUtility.uploadFile(file, nipEnumerator));
            formulasiKebijakanFileEntity.setFileTypeB2B(file.getContentType());
            formulasiKebijakanFileEntity.setFileSizeB2B(String.valueOf(file.getSize() / 1024));
            formulasiKebijakanFileEntity.setFileOriginalNameB2B(file.getOriginalFilename());
            formulasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            formulasiKebijakanFileRepository.save(formulasiKebijakanFileEntity);
        }

        formulasiKebijakanEntity.setB2B(answer);
        formulasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanRepository.save(formulasiKebijakanEntity);

        formulasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getFormulasiKebijakanBaseScoreByPolicyId(policyId));
        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapFormulasiKebijakanDto(policyEntity, formulasiKebijakanEntity, formulasiKebijakanFileEntity);
    }

    @Override
    public FormulasiKebijakanDto submitB3A(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        FormulasiKebijakanEntity formulasiKebijakanEntity = policyEntity.getFormulasiKebijakanEntity();
        FormulasiKebijakanFileEntity formulasiKebijakanFileEntity = policyEntity.getFormulasiKebijakanFileEntity();
        FormulasiKebijakanBaseScoreEntity formulasiKebijakanBaseScoreEntity = policyEntity.getFormulasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": formulasiKebijakanBaseScoreEntity.setB3A(100.0);
                break;
            case "b": formulasiKebijakanBaseScoreEntity.setB3A(75.0);
                break;
            case "c": formulasiKebijakanBaseScoreEntity.setB3A(50.0);
                break;
            case "d": formulasiKebijakanBaseScoreEntity.setB3A(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        if (file != null) {
            formulasiKebijakanFileEntity.setFilePathB3A(uploadFileUtility.uploadFile(file, nipEnumerator));
            formulasiKebijakanFileEntity.setFileTypeB3A(file.getContentType());
            formulasiKebijakanFileEntity.setFileSizeB3A(String.valueOf(file.getSize() / 1024));
            formulasiKebijakanFileEntity.setFileOriginalNameB3A(file.getOriginalFilename());
            formulasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            formulasiKebijakanFileRepository.save(formulasiKebijakanFileEntity);
        }

        formulasiKebijakanEntity.setB3A(answer);
        formulasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanRepository.save(formulasiKebijakanEntity);

        formulasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getFormulasiKebijakanBaseScoreByPolicyId(policyId));
        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapFormulasiKebijakanDto(policyEntity, formulasiKebijakanEntity, formulasiKebijakanFileEntity);
    }

    @Override
    public FormulasiKebijakanDto submitB3B(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        FormulasiKebijakanEntity formulasiKebijakanEntity = policyEntity.getFormulasiKebijakanEntity();
        FormulasiKebijakanFileEntity formulasiKebijakanFileEntity = policyEntity.getFormulasiKebijakanFileEntity();
        FormulasiKebijakanBaseScoreEntity formulasiKebijakanBaseScoreEntity = policyEntity.getFormulasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": formulasiKebijakanBaseScoreEntity.setB3B(100.0);
                break;
            case "b": formulasiKebijakanBaseScoreEntity.setB3B(75.0);
                break;
            case "c": formulasiKebijakanBaseScoreEntity.setB3B(50.0);
                break;
            case "d": formulasiKebijakanBaseScoreEntity.setB3B(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        if (file != null) {
            formulasiKebijakanFileEntity.setFilePathB3B(uploadFileUtility.uploadFile(file, nipEnumerator));
            formulasiKebijakanFileEntity.setFileTypeB3B(file.getContentType());
            formulasiKebijakanFileEntity.setFileSizeB3B(String.valueOf(file.getSize() / 1024));
            formulasiKebijakanFileEntity.setFileOriginalNameB3B(file.getOriginalFilename());
            formulasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            formulasiKebijakanFileRepository.save(formulasiKebijakanFileEntity);
        }

        formulasiKebijakanEntity.setB3B(answer);
        formulasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanRepository.save(formulasiKebijakanEntity);

        formulasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getFormulasiKebijakanBaseScoreByPolicyId(policyId));
        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapFormulasiKebijakanDto(policyEntity, formulasiKebijakanEntity, formulasiKebijakanFileEntity);
    }

    @Override
    public FormulasiKebijakanDto submitB3C(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        FormulasiKebijakanEntity formulasiKebijakanEntity = policyEntity.getFormulasiKebijakanEntity();
        FormulasiKebijakanFileEntity formulasiKebijakanFileEntity = policyEntity.getFormulasiKebijakanFileEntity();
        FormulasiKebijakanBaseScoreEntity formulasiKebijakanBaseScoreEntity = policyEntity.getFormulasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": formulasiKebijakanBaseScoreEntity.setB3C(100.0);
                break;
            case "b": formulasiKebijakanBaseScoreEntity.setB3C(75.0);
                break;
            case "c": formulasiKebijakanBaseScoreEntity.setB3C(50.0);
                break;
            case "d": formulasiKebijakanBaseScoreEntity.setB3C(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        if (file != null) {
            formulasiKebijakanFileEntity.setFilePathB3C(uploadFileUtility.uploadFile(file, nipEnumerator));
            formulasiKebijakanFileEntity.setFileTypeB3C(file.getContentType());
            formulasiKebijakanFileEntity.setFileSizeB3C(String.valueOf(file.getSize() / 1024));
            formulasiKebijakanFileEntity.setFileOriginalNameB3C(file.getOriginalFilename());
            formulasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            formulasiKebijakanFileRepository.save(formulasiKebijakanFileEntity);
        }

        formulasiKebijakanEntity.setB3C(answer);
        formulasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanRepository.save(formulasiKebijakanEntity);

        formulasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getFormulasiKebijakanBaseScoreByPolicyId(policyId));
        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapFormulasiKebijakanDto(policyEntity, formulasiKebijakanEntity, formulasiKebijakanFileEntity);
    }

    @Override
    public FormulasiKebijakanDto submitB4A(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        FormulasiKebijakanEntity formulasiKebijakanEntity = policyEntity.getFormulasiKebijakanEntity();
        FormulasiKebijakanFileEntity formulasiKebijakanFileEntity = policyEntity.getFormulasiKebijakanFileEntity();
        FormulasiKebijakanBaseScoreEntity formulasiKebijakanBaseScoreEntity = policyEntity.getFormulasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": formulasiKebijakanBaseScoreEntity.setB4A(100.0);
                break;
            case "b": formulasiKebijakanBaseScoreEntity.setB4A(75.0);
                break;
            case "c": formulasiKebijakanBaseScoreEntity.setB4A(50.0);
                break;
            case "d": formulasiKebijakanBaseScoreEntity.setB4A(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        if (file != null) {
            formulasiKebijakanFileEntity.setFilePathB4A(uploadFileUtility.uploadFile(file, nipEnumerator));
            formulasiKebijakanFileEntity.setFileTypeB4A(file.getContentType());
            formulasiKebijakanFileEntity.setFileSizeB4A(String.valueOf(file.getSize() / 1024));
            formulasiKebijakanFileEntity.setFileOriginalNameB4A(file.getOriginalFilename());
            formulasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            formulasiKebijakanFileRepository.save(formulasiKebijakanFileEntity);
        }

        formulasiKebijakanEntity.setB4A(answer);
        formulasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanRepository.save(formulasiKebijakanEntity);

        formulasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getFormulasiKebijakanBaseScoreByPolicyId(policyId));
        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapFormulasiKebijakanDto(policyEntity, formulasiKebijakanEntity, formulasiKebijakanFileEntity);
    }

    @Override
    public FormulasiKebijakanDto submitB4B(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        FormulasiKebijakanEntity formulasiKebijakanEntity = policyEntity.getFormulasiKebijakanEntity();
        FormulasiKebijakanFileEntity formulasiKebijakanFileEntity = policyEntity.getFormulasiKebijakanFileEntity();
        FormulasiKebijakanBaseScoreEntity formulasiKebijakanBaseScoreEntity = policyEntity.getFormulasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": formulasiKebijakanBaseScoreEntity.setB4B(100.0);
                break;
            case "b": formulasiKebijakanBaseScoreEntity.setB4B(75.0);
                break;
            case "c": formulasiKebijakanBaseScoreEntity.setB4B(50.0);
                break;
            case "d": formulasiKebijakanBaseScoreEntity.setB4B(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        if (file != null) {
            formulasiKebijakanFileEntity.setFilePathB4B(uploadFileUtility.uploadFile(file, nipEnumerator));
            formulasiKebijakanFileEntity.setFileTypeB4B(file.getContentType());
            formulasiKebijakanFileEntity.setFileSizeB4B(String.valueOf(file.getSize() / 1024));
            formulasiKebijakanFileEntity.setFileOriginalNameB4B(file.getOriginalFilename());
            formulasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            formulasiKebijakanFileRepository.save(formulasiKebijakanFileEntity);
        }

        formulasiKebijakanEntity.setB4B(answer);
        formulasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanRepository.save(formulasiKebijakanEntity);

        formulasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getFormulasiKebijakanBaseScoreByPolicyId(policyId));
        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapFormulasiKebijakanDto(policyEntity, formulasiKebijakanEntity, formulasiKebijakanFileEntity);
    }

    @Override
    public FormulasiKebijakanDto submitB4C(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        FormulasiKebijakanEntity formulasiKebijakanEntity = policyEntity.getFormulasiKebijakanEntity();
        FormulasiKebijakanFileEntity formulasiKebijakanFileEntity = policyEntity.getFormulasiKebijakanFileEntity();
        FormulasiKebijakanBaseScoreEntity formulasiKebijakanBaseScoreEntity = policyEntity.getFormulasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": formulasiKebijakanBaseScoreEntity.setB4C(100.0);
                break;
            case "b": formulasiKebijakanBaseScoreEntity.setB4C(75.0);
                break;
            case "c": formulasiKebijakanBaseScoreEntity.setB4C(50.0);
                break;
            case "d": formulasiKebijakanBaseScoreEntity.setB4C(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        if (file != null) {
            formulasiKebijakanFileEntity.setFilePathB4C(uploadFileUtility.uploadFile(file, nipEnumerator));
            formulasiKebijakanFileEntity.setFileTypeB4C(file.getContentType());
            formulasiKebijakanFileEntity.setFileSizeB4C(String.valueOf(file.getSize() / 1024));
            formulasiKebijakanFileEntity.setFileOriginalNameB4C(file.getOriginalFilename());
            formulasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            formulasiKebijakanFileRepository.save(formulasiKebijakanFileEntity);
        }

        formulasiKebijakanEntity.setB4C(answer);
        formulasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanRepository.save(formulasiKebijakanEntity);

        formulasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getFormulasiKebijakanBaseScoreByPolicyId(policyId));
        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapFormulasiKebijakanDto(policyEntity, formulasiKebijakanEntity, formulasiKebijakanFileEntity);
    }

    @Override
    public FormulasiKebijakanDto submitB5A(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        FormulasiKebijakanEntity formulasiKebijakanEntity = policyEntity.getFormulasiKebijakanEntity();
        FormulasiKebijakanFileEntity formulasiKebijakanFileEntity = policyEntity.getFormulasiKebijakanFileEntity();
        FormulasiKebijakanBaseScoreEntity formulasiKebijakanBaseScoreEntity = policyEntity.getFormulasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": formulasiKebijakanBaseScoreEntity.setB5A(100.0);
                break;
            case "b": formulasiKebijakanBaseScoreEntity.setB5A(75.0);
                break;
            case "c": formulasiKebijakanBaseScoreEntity.setB5A(50.0);
                break;
            case "d": formulasiKebijakanBaseScoreEntity.setB5A(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        if (file != null) {
            formulasiKebijakanFileEntity.setFilePathB5A(uploadFileUtility.uploadFile(file, nipEnumerator));
            formulasiKebijakanFileEntity.setFileTypeB5A(file.getContentType());
            formulasiKebijakanFileEntity.setFileSizeB5A(String.valueOf(file.getSize() / 1024));
            formulasiKebijakanFileEntity.setFileOriginalNameB5A(file.getOriginalFilename());
            formulasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            formulasiKebijakanFileRepository.save(formulasiKebijakanFileEntity);
        }

        formulasiKebijakanEntity.setB5A(answer);
        formulasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanRepository.save(formulasiKebijakanEntity);

        formulasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getFormulasiKebijakanBaseScoreByPolicyId(policyId));
        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapFormulasiKebijakanDto(policyEntity, formulasiKebijakanEntity, formulasiKebijakanFileEntity);
    }

    @Override
    public FormulasiKebijakanDto submitB5B(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        FormulasiKebijakanEntity formulasiKebijakanEntity = policyEntity.getFormulasiKebijakanEntity();
        FormulasiKebijakanFileEntity formulasiKebijakanFileEntity = policyEntity.getFormulasiKebijakanFileEntity();
        FormulasiKebijakanBaseScoreEntity formulasiKebijakanBaseScoreEntity = policyEntity.getFormulasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": formulasiKebijakanBaseScoreEntity.setB5B(100.0);
                break;
            case "b": formulasiKebijakanBaseScoreEntity.setB5B(75.0);
                break;
            case "c": formulasiKebijakanBaseScoreEntity.setB5B(50.0);
                break;
            case "d": formulasiKebijakanBaseScoreEntity.setB5B(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        if (file != null) {
            formulasiKebijakanFileEntity.setFilePathB5B(uploadFileUtility.uploadFile(file, nipEnumerator));
            formulasiKebijakanFileEntity.setFileTypeB5B(file.getContentType());
            formulasiKebijakanFileEntity.setFileSizeB5B(String.valueOf(file.getSize() / 1024));
            formulasiKebijakanFileEntity.setFileOriginalNameB5B(file.getOriginalFilename());
            formulasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            formulasiKebijakanFileRepository.save(formulasiKebijakanFileEntity);
        }

        formulasiKebijakanEntity.setB5B(answer);
        formulasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanRepository.save(formulasiKebijakanEntity);

        formulasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getFormulasiKebijakanBaseScoreByPolicyId(policyId));
        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapFormulasiKebijakanDto(policyEntity, formulasiKebijakanEntity, formulasiKebijakanFileEntity);
    }

    @Override
    public FormulasiKebijakanDto submitB5C(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        FormulasiKebijakanEntity formulasiKebijakanEntity = policyEntity.getFormulasiKebijakanEntity();
        FormulasiKebijakanFileEntity formulasiKebijakanFileEntity = policyEntity.getFormulasiKebijakanFileEntity();
        FormulasiKebijakanBaseScoreEntity formulasiKebijakanBaseScoreEntity = policyEntity.getFormulasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": formulasiKebijakanBaseScoreEntity.setB5C(100.0);
                break;
            case "b": formulasiKebijakanBaseScoreEntity.setB5C(75.0);
                break;
            case "c": formulasiKebijakanBaseScoreEntity.setB5C(50.0);
                break;
            case "d": formulasiKebijakanBaseScoreEntity.setB5C(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        if (file != null) {
            formulasiKebijakanFileEntity.setFilePathB5C(uploadFileUtility.uploadFile(file, nipEnumerator));
            formulasiKebijakanFileEntity.setFileTypeB5C(file.getContentType());
            formulasiKebijakanFileEntity.setFileSizeB5C(String.valueOf(file.getSize() / 1024));
            formulasiKebijakanFileEntity.setFileOriginalNameB5C(file.getOriginalFilename());
            formulasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            formulasiKebijakanFileRepository.save(formulasiKebijakanFileEntity);
        }

        formulasiKebijakanEntity.setB5C(answer);
        formulasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanRepository.save(formulasiKebijakanEntity);

        formulasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getFormulasiKebijakanBaseScoreByPolicyId(policyId));
        formulasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanBaseScoreRepository.save(formulasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapFormulasiKebijakanDto(policyEntity, formulasiKebijakanEntity, formulasiKebijakanFileEntity);
    }

    @Override
    public FormulasiKebijakanDto submitInformasiB6(Long policyId, String answer) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        FormulasiKebijakanEntity formulasiKebijakanEntity = policyEntity.getFormulasiKebijakanEntity();
        FormulasiKebijakanFileEntity formulasiKebijakanFileEntity = policyEntity.getFormulasiKebijakanFileEntity();

        formulasiKebijakanEntity.setInformasiB6(answer);
        formulasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanRepository.save(formulasiKebijakanEntity);

        return mapFormulasiKebijakanDto(policyEntity, formulasiKebijakanEntity, formulasiKebijakanFileEntity);
    }

    @Override
    public FormulasiKebijakanValidationDto geValidationKI(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        FormulasiKebijakanKIEntity formulasiKebijakanKIEntity = policyEntity.getFormulasiKebijakanKIEntity();
        return modelMapperUtility.initialize().map(formulasiKebijakanKIEntity, FormulasiKebijakanValidationDto.class);
    }

    @Override
    public FormulasiKebijakanValidationDto getValidationKU(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        FormulasiKebijakanKUEntity formulasiKebijakanKUEntity = policyEntity.getFormulasiKebijakanKUEntity();
        return modelMapperUtility.initialize().map(formulasiKebijakanKUEntity, FormulasiKebijakanValidationDto.class);
    }

    @Override
    public void submitValidasiKI(Long policyId, FormulasiKebijakanValidationRequest formulasiKebijakanValidationRequest) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        FormulasiKebijakanKIEntity formulasiKebijakanKIEntity = policyEntity.getFormulasiKebijakanKIEntity();
        FormulasiKebijakanBaseScoreEntity formulasiKebijakanBaseScoreEntity = policyEntity.getFormulasiKebijakanBaseScoreEntity();
        FormulasiKebijakanKIScoreEntity formulasiKebijakanKIScoreEntity = policyEntity.getFormulasiKebijakanKIScoreEntity();

        switch (formulasiKebijakanValidationRequest.getB1A()) {
            case "1": formulasiKebijakanKIScoreEntity.setB1A(formulasiKebijakanBaseScoreEntity.getB1A() * 0/100);
                break;
            case "2": formulasiKebijakanKIScoreEntity.setB1A(formulasiKebijakanBaseScoreEntity.getB1A() * 25/200);
                break;
            case "3": formulasiKebijakanKIScoreEntity.setB1A(formulasiKebijakanBaseScoreEntity.getB1A() * 50/100);
                break;
            case "4": formulasiKebijakanKIScoreEntity.setB1A(formulasiKebijakanBaseScoreEntity.getB1A() * 75/100);
                break;
            case "5": formulasiKebijakanKIScoreEntity.setB1A(formulasiKebijakanBaseScoreEntity.getB1A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (formulasiKebijakanValidationRequest.getB1B()) {
            case "1": formulasiKebijakanKIScoreEntity.setB1B(formulasiKebijakanBaseScoreEntity.getB1B() * 0/100);
                break;
            case "2": formulasiKebijakanKIScoreEntity.setB1B(formulasiKebijakanBaseScoreEntity.getB1B() * 25/200);
                break;
            case "3": formulasiKebijakanKIScoreEntity.setB1B(formulasiKebijakanBaseScoreEntity.getB1B() * 50/100);
                break;
            case "4": formulasiKebijakanKIScoreEntity.setB1B(formulasiKebijakanBaseScoreEntity.getB1B() * 75/100);
                break;
            case "5": formulasiKebijakanKIScoreEntity.setB1B(formulasiKebijakanBaseScoreEntity.getB1B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (formulasiKebijakanValidationRequest.getB2A()) {
            case "1": formulasiKebijakanKIScoreEntity.setB2A(formulasiKebijakanBaseScoreEntity.getB2A() * 0/100);
                break;
            case "2": formulasiKebijakanKIScoreEntity.setB2A(formulasiKebijakanBaseScoreEntity.getB2A() * 25/200);
                break;
            case "3": formulasiKebijakanKIScoreEntity.setB2A(formulasiKebijakanBaseScoreEntity.getB2A() * 50/100);
                break;
            case "4": formulasiKebijakanKIScoreEntity.setB2A(formulasiKebijakanBaseScoreEntity.getB2A() * 75/100);
                break;
            case "5": formulasiKebijakanKIScoreEntity.setB2A(formulasiKebijakanBaseScoreEntity.getB2A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (formulasiKebijakanValidationRequest.getB2B()) {
            case "1": formulasiKebijakanKIScoreEntity.setB2B(formulasiKebijakanBaseScoreEntity.getB2B() * 0/100);
                break;
            case "2": formulasiKebijakanKIScoreEntity.setB2B(formulasiKebijakanBaseScoreEntity.getB2B() * 25/200);
                break;
            case "3": formulasiKebijakanKIScoreEntity.setB2B(formulasiKebijakanBaseScoreEntity.getB2B() * 50/100);
                break;
            case "4": formulasiKebijakanKIScoreEntity.setB2B(formulasiKebijakanBaseScoreEntity.getB2B() * 75/100);
                break;
            case "5": formulasiKebijakanKIScoreEntity.setB2B(formulasiKebijakanBaseScoreEntity.getB2B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (formulasiKebijakanValidationRequest.getB3A()) {
            case "1": formulasiKebijakanKIScoreEntity.setB3A(formulasiKebijakanBaseScoreEntity.getB3A() * 0/100);
                break;
            case "2": formulasiKebijakanKIScoreEntity.setB3A(formulasiKebijakanBaseScoreEntity.getB3A() * 25/200);
                break;
            case "3": formulasiKebijakanKIScoreEntity.setB3A(formulasiKebijakanBaseScoreEntity.getB3A() * 50/100);
                break;
            case "4": formulasiKebijakanKIScoreEntity.setB3A(formulasiKebijakanBaseScoreEntity.getB3A() * 75/100);
                break;
            case "5": formulasiKebijakanKIScoreEntity.setB3A(formulasiKebijakanBaseScoreEntity.getB3A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (formulasiKebijakanValidationRequest.getB3B()) {
            case "1": formulasiKebijakanKIScoreEntity.setB3B(formulasiKebijakanBaseScoreEntity.getB3B() * 0/100);
                break;
            case "2": formulasiKebijakanKIScoreEntity.setB3B(formulasiKebijakanBaseScoreEntity.getB3B() * 25/200);
                break;
            case "3": formulasiKebijakanKIScoreEntity.setB3B(formulasiKebijakanBaseScoreEntity.getB3B() * 50/100);
                break;
            case "4": formulasiKebijakanKIScoreEntity.setB3B(formulasiKebijakanBaseScoreEntity.getB3B() * 75/100);
                break;
            case "5": formulasiKebijakanKIScoreEntity.setB3B(formulasiKebijakanBaseScoreEntity.getB3B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (formulasiKebijakanValidationRequest.getB3C()) {
            case "1": formulasiKebijakanKIScoreEntity.setB3C(formulasiKebijakanBaseScoreEntity.getB3C() * 0/100);
                break;
            case "2": formulasiKebijakanKIScoreEntity.setB3C(formulasiKebijakanBaseScoreEntity.getB3C() * 25/200);
                break;
            case "3": formulasiKebijakanKIScoreEntity.setB3C(formulasiKebijakanBaseScoreEntity.getB3C() * 50/100);
                break;
            case "4": formulasiKebijakanKIScoreEntity.setB3C(formulasiKebijakanBaseScoreEntity.getB3C() * 75/100);
                break;
            case "5": formulasiKebijakanKIScoreEntity.setB3C(formulasiKebijakanBaseScoreEntity.getB3C() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (formulasiKebijakanValidationRequest.getB4A()) {
            case "1": formulasiKebijakanKIScoreEntity.setB4A(formulasiKebijakanBaseScoreEntity.getB4A() * 0/100);
                break;
            case "2": formulasiKebijakanKIScoreEntity.setB4A(formulasiKebijakanBaseScoreEntity.getB4A() * 25/200);
                break;
            case "3": formulasiKebijakanKIScoreEntity.setB4A(formulasiKebijakanBaseScoreEntity.getB4A() * 50/100);
                break;
            case "4": formulasiKebijakanKIScoreEntity.setB4A(formulasiKebijakanBaseScoreEntity.getB4A() * 75/100);
                break;
            case "5": formulasiKebijakanKIScoreEntity.setB4A(formulasiKebijakanBaseScoreEntity.getB4A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (formulasiKebijakanValidationRequest.getB4B()) {
            case "1": formulasiKebijakanKIScoreEntity.setB4B(formulasiKebijakanBaseScoreEntity.getB4B() * 0/100);
                break;
            case "2": formulasiKebijakanKIScoreEntity.setB4B(formulasiKebijakanBaseScoreEntity.getB4B() * 25/200);
                break;
            case "3": formulasiKebijakanKIScoreEntity.setB4B(formulasiKebijakanBaseScoreEntity.getB4B() * 50/100);
                break;
            case "4": formulasiKebijakanKIScoreEntity.setB4B(formulasiKebijakanBaseScoreEntity.getB4B() * 75/100);
                break;
            case "5": formulasiKebijakanKIScoreEntity.setB4B(formulasiKebijakanBaseScoreEntity.getB4B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (formulasiKebijakanValidationRequest.getB4C()) {
            case "1": formulasiKebijakanKIScoreEntity.setB4C(formulasiKebijakanBaseScoreEntity.getB4C() * 0/100);
                break;
            case "2": formulasiKebijakanKIScoreEntity.setB4C(formulasiKebijakanBaseScoreEntity.getB4C() * 25/200);
                break;
            case "3": formulasiKebijakanKIScoreEntity.setB4C(formulasiKebijakanBaseScoreEntity.getB4C() * 50/100);
                break;
            case "4": formulasiKebijakanKIScoreEntity.setB4C(formulasiKebijakanBaseScoreEntity.getB4C() * 75/100);
                break;
            case "5": formulasiKebijakanKIScoreEntity.setB4C(formulasiKebijakanBaseScoreEntity.getB4C() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (formulasiKebijakanValidationRequest.getB5A()) {
            case "1": formulasiKebijakanKIScoreEntity.setB5A(formulasiKebijakanBaseScoreEntity.getB5A() * 0/100);
                break;
            case "2": formulasiKebijakanKIScoreEntity.setB5A(formulasiKebijakanBaseScoreEntity.getB5A() * 25/200);
                break;
            case "3": formulasiKebijakanKIScoreEntity.setB5A(formulasiKebijakanBaseScoreEntity.getB5A() * 50/100);
                break;
            case "4": formulasiKebijakanKIScoreEntity.setB5A(formulasiKebijakanBaseScoreEntity.getB5A() * 75/100);
                break;
            case "5": formulasiKebijakanKIScoreEntity.setB5A(formulasiKebijakanBaseScoreEntity.getB5A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }
        switch (formulasiKebijakanValidationRequest.getB5B()) {
            case "1": formulasiKebijakanKIScoreEntity.setB5B(formulasiKebijakanBaseScoreEntity.getB5B() * 0/100);
                break;
            case "2": formulasiKebijakanKIScoreEntity.setB5B(formulasiKebijakanBaseScoreEntity.getB5B() * 25/200);
                break;
            case "3": formulasiKebijakanKIScoreEntity.setB5B(formulasiKebijakanBaseScoreEntity.getB5B() * 50/100);
                break;
            case "4": formulasiKebijakanKIScoreEntity.setB5B(formulasiKebijakanBaseScoreEntity.getB5B() * 75/100);
                break;
            case "5": formulasiKebijakanKIScoreEntity.setB5B(formulasiKebijakanBaseScoreEntity.getB5B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (formulasiKebijakanValidationRequest.getB5C()) {
            case "1": formulasiKebijakanKIScoreEntity.setB5C(formulasiKebijakanBaseScoreEntity.getB5C() * 0/100);
                break;
            case "2": formulasiKebijakanKIScoreEntity.setB5C(formulasiKebijakanBaseScoreEntity.getB5C() * 25/200);
                break;
            case "3": formulasiKebijakanKIScoreEntity.setB5C(formulasiKebijakanBaseScoreEntity.getB5C() * 50/100);
                break;
            case "4": formulasiKebijakanKIScoreEntity.setB5C(formulasiKebijakanBaseScoreEntity.getB5C() * 75/100);
                break;
            case "5": formulasiKebijakanKIScoreEntity.setB5C(formulasiKebijakanBaseScoreEntity.getB5C() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        if (!formulasiKebijakanValidationRequest.getB1A().isEmpty()) {
            formulasiKebijakanKIEntity.setB1A(formulasiKebijakanValidationRequest.getB1A());
        }

        if (!formulasiKebijakanValidationRequest.getB1B().isEmpty()) {
            formulasiKebijakanKIEntity.setB1B(formulasiKebijakanValidationRequest.getB1B());
        }

        if (!formulasiKebijakanValidationRequest.getB2A().isEmpty()) {
            formulasiKebijakanKIEntity.setB2A(formulasiKebijakanValidationRequest.getB2A());
        }

        if (!formulasiKebijakanValidationRequest.getB2B().isEmpty()) {
            formulasiKebijakanKIEntity.setB2B(formulasiKebijakanValidationRequest.getB2B());
        }

        if (!formulasiKebijakanValidationRequest.getB3A().isEmpty()) {
            formulasiKebijakanKIEntity.setB3A(formulasiKebijakanValidationRequest.getB3A());
        }

        if (!formulasiKebijakanValidationRequest.getB3B().isEmpty()) {
            formulasiKebijakanKIEntity.setB3B(formulasiKebijakanValidationRequest.getB3B());
        }

        if (!formulasiKebijakanValidationRequest.getB3C().isEmpty()) {
            formulasiKebijakanKIEntity.setB3C(formulasiKebijakanValidationRequest.getB3C());
        }

        if (!formulasiKebijakanValidationRequest.getB4A().isEmpty()) {
            formulasiKebijakanKIEntity.setB4A(formulasiKebijakanValidationRequest.getB4A());
        }

        if (!formulasiKebijakanValidationRequest.getB4B().isEmpty()) {
            formulasiKebijakanKIEntity.setB4B(formulasiKebijakanValidationRequest.getB4B());
        }

        if (!formulasiKebijakanValidationRequest.getB4C().isEmpty()) {
            formulasiKebijakanKIEntity.setB4C(formulasiKebijakanValidationRequest.getB4C());
        }

        if (!formulasiKebijakanValidationRequest.getB5A().isEmpty()) {
            formulasiKebijakanKIEntity.setB5A(formulasiKebijakanValidationRequest.getB5A());
        }

        if (!formulasiKebijakanValidationRequest.getB5B().isEmpty()) {
            formulasiKebijakanKIEntity.setB5B(formulasiKebijakanValidationRequest.getB5B());
        }

        if (!formulasiKebijakanValidationRequest.getB5C().isEmpty()) {
            formulasiKebijakanKIEntity.setB5C(formulasiKebijakanValidationRequest.getB5C());
        }

        formulasiKebijakanKIEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanKIRepository.save(formulasiKebijakanKIEntity);

        formulasiKebijakanKIScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanKIScoreRepository.save(formulasiKebijakanKIScoreEntity);

        formulasiKebijakanKIScoreEntity.setTotalScore(policyDetailsService.getFormulasiKebijakanValidationKIScoreByPolicyId(policyId));
        formulasiKebijakanKIScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanKIScoreRepository.save(formulasiKebijakanKIScoreEntity);

        policyDetailsService.updatePolicyValidationKIScoreByPolicyId(policyId);
    }

    @Override
    public void submitValidasiKU(Long policyId, FormulasiKebijakanValidationRequest formulasiKebijakanValidationRequest) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        FormulasiKebijakanKUEntity formulasiKebijakanKUEntity = policyEntity.getFormulasiKebijakanKUEntity();
        FormulasiKebijakanBaseScoreEntity formulasiKebijakanBaseScoreEntity = policyEntity.getFormulasiKebijakanBaseScoreEntity();
        FormulasiKebijakanKUScoreEntity formulasiKebijakanKUScoreEntity = policyEntity.getFormulasiKebijakanKUScoreEntity();

        switch (formulasiKebijakanValidationRequest.getB1A()) {
            case "1": formulasiKebijakanKUScoreEntity.setB1A(formulasiKebijakanBaseScoreEntity.getB1A() * 0/100);
                break;
            case "2": formulasiKebijakanKUScoreEntity.setB1A(formulasiKebijakanBaseScoreEntity.getB1A() * 25/200);
                break;
            case "3": formulasiKebijakanKUScoreEntity.setB1A(formulasiKebijakanBaseScoreEntity.getB1A() * 50/100);
                break;
            case "4": formulasiKebijakanKUScoreEntity.setB1A(formulasiKebijakanBaseScoreEntity.getB1A() * 75/100);
                break;
            case "5": formulasiKebijakanKUScoreEntity.setB1A(formulasiKebijakanBaseScoreEntity.getB1A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (formulasiKebijakanValidationRequest.getB1B()) {
            case "1": formulasiKebijakanKUScoreEntity.setB1B(formulasiKebijakanBaseScoreEntity.getB1B() * 0/100);
                break;
            case "2": formulasiKebijakanKUScoreEntity.setB1B(formulasiKebijakanBaseScoreEntity.getB1B() * 25/200);
                break;
            case "3": formulasiKebijakanKUScoreEntity.setB1B(formulasiKebijakanBaseScoreEntity.getB1B() * 50/100);
                break;
            case "4": formulasiKebijakanKUScoreEntity.setB1B(formulasiKebijakanBaseScoreEntity.getB1B() * 75/100);
                break;
            case "5": formulasiKebijakanKUScoreEntity.setB1B(formulasiKebijakanBaseScoreEntity.getB1B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (formulasiKebijakanValidationRequest.getB2A()) {
            case "1": formulasiKebijakanKUScoreEntity.setB2A(formulasiKebijakanBaseScoreEntity.getB2A() * 0/100);
                break;
            case "2": formulasiKebijakanKUScoreEntity.setB2A(formulasiKebijakanBaseScoreEntity.getB2A() * 25/200);
                break;
            case "3": formulasiKebijakanKUScoreEntity.setB2A(formulasiKebijakanBaseScoreEntity.getB2A() * 50/100);
                break;
            case "4": formulasiKebijakanKUScoreEntity.setB2A(formulasiKebijakanBaseScoreEntity.getB2A() * 75/100);
                break;
            case "5": formulasiKebijakanKUScoreEntity.setB2A(formulasiKebijakanBaseScoreEntity.getB2A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (formulasiKebijakanValidationRequest.getB2B()) {
            case "1": formulasiKebijakanKUScoreEntity.setB2B(formulasiKebijakanBaseScoreEntity.getB2B() * 0/100);
                break;
            case "2": formulasiKebijakanKUScoreEntity.setB2B(formulasiKebijakanBaseScoreEntity.getB2B() * 25/200);
                break;
            case "3": formulasiKebijakanKUScoreEntity.setB2B(formulasiKebijakanBaseScoreEntity.getB2B() * 50/100);
                break;
            case "4": formulasiKebijakanKUScoreEntity.setB2B(formulasiKebijakanBaseScoreEntity.getB2B() * 75/100);
                break;
            case "5": formulasiKebijakanKUScoreEntity.setB2B(formulasiKebijakanBaseScoreEntity.getB2B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (formulasiKebijakanValidationRequest.getB3A()) {
            case "1": formulasiKebijakanKUScoreEntity.setB3A(formulasiKebijakanBaseScoreEntity.getB3A() * 0/100);
                break;
            case "2": formulasiKebijakanKUScoreEntity.setB3A(formulasiKebijakanBaseScoreEntity.getB3A() * 25/200);
                break;
            case "3": formulasiKebijakanKUScoreEntity.setB3A(formulasiKebijakanBaseScoreEntity.getB3A() * 50/100);
                break;
            case "4": formulasiKebijakanKUScoreEntity.setB3A(formulasiKebijakanBaseScoreEntity.getB3A() * 75/100);
                break;
            case "5": formulasiKebijakanKUScoreEntity.setB3A(formulasiKebijakanBaseScoreEntity.getB3A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (formulasiKebijakanValidationRequest.getB3B()) {
            case "1": formulasiKebijakanKUScoreEntity.setB3B(formulasiKebijakanBaseScoreEntity.getB3B() * 0/100);
                break;
            case "2": formulasiKebijakanKUScoreEntity.setB3B(formulasiKebijakanBaseScoreEntity.getB3B() * 25/200);
                break;
            case "3": formulasiKebijakanKUScoreEntity.setB3B(formulasiKebijakanBaseScoreEntity.getB3B() * 50/100);
                break;
            case "4": formulasiKebijakanKUScoreEntity.setB3B(formulasiKebijakanBaseScoreEntity.getB3B() * 75/100);
                break;
            case "5": formulasiKebijakanKUScoreEntity.setB3B(formulasiKebijakanBaseScoreEntity.getB3B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (formulasiKebijakanValidationRequest.getB3C()) {
            case "1": formulasiKebijakanKUScoreEntity.setB3C(formulasiKebijakanBaseScoreEntity.getB3C() * 0/100);
                break;
            case "2": formulasiKebijakanKUScoreEntity.setB3C(formulasiKebijakanBaseScoreEntity.getB3C() * 25/200);
                break;
            case "3": formulasiKebijakanKUScoreEntity.setB3C(formulasiKebijakanBaseScoreEntity.getB3C() * 50/100);
                break;
            case "4": formulasiKebijakanKUScoreEntity.setB3C(formulasiKebijakanBaseScoreEntity.getB3C() * 75/100);
                break;
            case "5": formulasiKebijakanKUScoreEntity.setB3C(formulasiKebijakanBaseScoreEntity.getB3C() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (formulasiKebijakanValidationRequest.getB4A()) {
            case "1": formulasiKebijakanKUScoreEntity.setB4A(formulasiKebijakanBaseScoreEntity.getB4A() * 0/100);
                break;
            case "2": formulasiKebijakanKUScoreEntity.setB4A(formulasiKebijakanBaseScoreEntity.getB4A() * 25/200);
                break;
            case "3": formulasiKebijakanKUScoreEntity.setB4A(formulasiKebijakanBaseScoreEntity.getB4A() * 50/100);
                break;
            case "4": formulasiKebijakanKUScoreEntity.setB4A(formulasiKebijakanBaseScoreEntity.getB4A() * 75/100);
                break;
            case "5": formulasiKebijakanKUScoreEntity.setB4A(formulasiKebijakanBaseScoreEntity.getB4A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (formulasiKebijakanValidationRequest.getB4B()) {
            case "1": formulasiKebijakanKUScoreEntity.setB4B(formulasiKebijakanBaseScoreEntity.getB4B() * 0/100);
                break;
            case "2": formulasiKebijakanKUScoreEntity.setB4B(formulasiKebijakanBaseScoreEntity.getB4B() * 25/200);
                break;
            case "3": formulasiKebijakanKUScoreEntity.setB4B(formulasiKebijakanBaseScoreEntity.getB4B() * 50/100);
                break;
            case "4": formulasiKebijakanKUScoreEntity.setB4B(formulasiKebijakanBaseScoreEntity.getB4B() * 75/100);
                break;
            case "5": formulasiKebijakanKUScoreEntity.setB4B(formulasiKebijakanBaseScoreEntity.getB4B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (formulasiKebijakanValidationRequest.getB4C()) {
            case "1": formulasiKebijakanKUScoreEntity.setB4C(formulasiKebijakanBaseScoreEntity.getB4C() * 0/100);
                break;
            case "2": formulasiKebijakanKUScoreEntity.setB4C(formulasiKebijakanBaseScoreEntity.getB4C() * 25/200);
                break;
            case "3": formulasiKebijakanKUScoreEntity.setB4C(formulasiKebijakanBaseScoreEntity.getB4C() * 50/100);
                break;
            case "4": formulasiKebijakanKUScoreEntity.setB4C(formulasiKebijakanBaseScoreEntity.getB4C() * 75/100);
                break;
            case "5": formulasiKebijakanKUScoreEntity.setB4C(formulasiKebijakanBaseScoreEntity.getB4C() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (formulasiKebijakanValidationRequest.getB5A()) {
            case "1": formulasiKebijakanKUScoreEntity.setB5A(formulasiKebijakanBaseScoreEntity.getB5A() * 0/100);
                break;
            case "2": formulasiKebijakanKUScoreEntity.setB5A(formulasiKebijakanBaseScoreEntity.getB5A() * 25/200);
                break;
            case "3": formulasiKebijakanKUScoreEntity.setB5A(formulasiKebijakanBaseScoreEntity.getB5A() * 50/100);
                break;
            case "4": formulasiKebijakanKUScoreEntity.setB5A(formulasiKebijakanBaseScoreEntity.getB5A() * 75/100);
                break;
            case "5": formulasiKebijakanKUScoreEntity.setB5A(formulasiKebijakanBaseScoreEntity.getB5A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }
        switch (formulasiKebijakanValidationRequest.getB5B()) {
            case "1": formulasiKebijakanKUScoreEntity.setB5B(formulasiKebijakanBaseScoreEntity.getB5B() * 0/100);
                break;
            case "2": formulasiKebijakanKUScoreEntity.setB5B(formulasiKebijakanBaseScoreEntity.getB5B() * 25/200);
                break;
            case "3": formulasiKebijakanKUScoreEntity.setB5B(formulasiKebijakanBaseScoreEntity.getB5B() * 50/100);
                break;
            case "4": formulasiKebijakanKUScoreEntity.setB5B(formulasiKebijakanBaseScoreEntity.getB5B() * 75/100);
                break;
            case "5": formulasiKebijakanKUScoreEntity.setB5B(formulasiKebijakanBaseScoreEntity.getB5B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (formulasiKebijakanValidationRequest.getB5C()) {
            case "1": formulasiKebijakanKUScoreEntity.setB5C(formulasiKebijakanBaseScoreEntity.getB5C() * 0/100);
                break;
            case "2": formulasiKebijakanKUScoreEntity.setB5C(formulasiKebijakanBaseScoreEntity.getB5C() * 25/200);
                break;
            case "3": formulasiKebijakanKUScoreEntity.setB5C(formulasiKebijakanBaseScoreEntity.getB5C() * 50/100);
                break;
            case "4": formulasiKebijakanKUScoreEntity.setB5C(formulasiKebijakanBaseScoreEntity.getB5C() * 75/100);
                break;
            case "5": formulasiKebijakanKUScoreEntity.setB5C(formulasiKebijakanBaseScoreEntity.getB5C() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        if (!formulasiKebijakanValidationRequest.getB1A().isEmpty()) {
            formulasiKebijakanKUEntity.setB1A(formulasiKebijakanValidationRequest.getB1A());
        }

        if (!formulasiKebijakanValidationRequest.getB1B().isEmpty()) {
            formulasiKebijakanKUEntity.setB1B(formulasiKebijakanValidationRequest.getB1B());
        }

        if (!formulasiKebijakanValidationRequest.getB2A().isEmpty()) {
            formulasiKebijakanKUEntity.setB2A(formulasiKebijakanValidationRequest.getB2A());
        }

        if (!formulasiKebijakanValidationRequest.getB2B().isEmpty()) {
            formulasiKebijakanKUEntity.setB2B(formulasiKebijakanValidationRequest.getB2B());
        }

        if (!formulasiKebijakanValidationRequest.getB3A().isEmpty()) {
            formulasiKebijakanKUEntity.setB3A(formulasiKebijakanValidationRequest.getB3A());
        }

        if (!formulasiKebijakanValidationRequest.getB3B().isEmpty()) {
            formulasiKebijakanKUEntity.setB3B(formulasiKebijakanValidationRequest.getB3B());
        }

        if (!formulasiKebijakanValidationRequest.getB3C().isEmpty()) {
            formulasiKebijakanKUEntity.setB3C(formulasiKebijakanValidationRequest.getB3C());
        }

        if (!formulasiKebijakanValidationRequest.getB4A().isEmpty()) {
            formulasiKebijakanKUEntity.setB4A(formulasiKebijakanValidationRequest.getB4A());
        }

        if (!formulasiKebijakanValidationRequest.getB4B().isEmpty()) {
            formulasiKebijakanKUEntity.setB4B(formulasiKebijakanValidationRequest.getB4B());
        }

        if (!formulasiKebijakanValidationRequest.getB4C().isEmpty()) {
            formulasiKebijakanKUEntity.setB4C(formulasiKebijakanValidationRequest.getB4C());
        }

        if (!formulasiKebijakanValidationRequest.getB5A().isEmpty()) {
            formulasiKebijakanKUEntity.setB5A(formulasiKebijakanValidationRequest.getB5A());
        }

        if (!formulasiKebijakanValidationRequest.getB5B().isEmpty()) {
            formulasiKebijakanKUEntity.setB5B(formulasiKebijakanValidationRequest.getB5B());
        }

        if (!formulasiKebijakanValidationRequest.getB5C().isEmpty()) {
            formulasiKebijakanKUEntity.setB5C(formulasiKebijakanValidationRequest.getB5C());
        }

        formulasiKebijakanKUEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanKURepository.save(formulasiKebijakanKUEntity);

        formulasiKebijakanKUScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanKUScoreRepository.save(formulasiKebijakanKUScoreEntity);

        formulasiKebijakanKUScoreEntity.setTotalScore(policyDetailsService.getFormulasiKebijakanValidationKUScoreByPolicyId(policyId));
        formulasiKebijakanKUScoreEntity.setModifiedBy(userService.getSignedInUserId());
        formulasiKebijakanKUScoreRepository.save(formulasiKebijakanKUScoreEntity);

        policyDetailsService.updatePolicyValidationKUScoreByPolicyId(policyId);
    }

    @Override
    public FormulasiKebijakanDto mapFormulasiKebijakanDto(
            PolicyEntity policyEntity,
            FormulasiKebijakanEntity formulasiKebijakanEntity,
            FormulasiKebijakanFileEntity formulasiKebijakanFileEntity) {
        FormulasiKebijakanDto formulasiKebijakanDto = new FormulasiKebijakanDto();

        formulasiKebijakanDto.setIdKebijakan(policyEntity.getId());
        formulasiKebijakanDto.setIdFormulasiKebijakan(formulasiKebijakanEntity.getId());

        formulasiKebijakanDto.setB1A(formulasiKebijakanEntity.getB1A());
        formulasiKebijakanDto.setB1B(formulasiKebijakanEntity.getB1B());
        formulasiKebijakanDto.setB2A(formulasiKebijakanEntity.getB2A());
        formulasiKebijakanDto.setB2B(formulasiKebijakanEntity.getB2B());
        formulasiKebijakanDto.setB3A(formulasiKebijakanEntity.getB3A());
        formulasiKebijakanDto.setB3B(formulasiKebijakanEntity.getB3B());
        formulasiKebijakanDto.setB3C(formulasiKebijakanEntity.getB3C());
        formulasiKebijakanDto.setB4A(formulasiKebijakanEntity.getB4A());
        formulasiKebijakanDto.setB4B(formulasiKebijakanEntity.getB4B());
        formulasiKebijakanDto.setB4C(formulasiKebijakanEntity.getB4C());
        formulasiKebijakanDto.setB5A(formulasiKebijakanEntity.getB5A());
        formulasiKebijakanDto.setB5B(formulasiKebijakanEntity.getB5B());
        formulasiKebijakanDto.setB5C(formulasiKebijakanEntity.getB5C());
        formulasiKebijakanDto.setInformasiB6(formulasiKebijakanEntity.getInformasiB6());

        formulasiKebijakanDto.setFilePathB1A(formulasiKebijakanFileEntity.getFilePathB1A());
        formulasiKebijakanDto.setFileTypeB1A(formulasiKebijakanFileEntity.getFileTypeB1A());
        formulasiKebijakanDto.setFileSizeB1A(formulasiKebijakanFileEntity.getFileSizeB1A());
        formulasiKebijakanDto.setFileOriginalNameB1A(formulasiKebijakanFileEntity.getFileOriginalNameB1A());

        formulasiKebijakanDto.setFilePathB1B(formulasiKebijakanFileEntity.getFilePathB1B());
        formulasiKebijakanDto.setFileTypeB1B(formulasiKebijakanFileEntity.getFileTypeB1B());
        formulasiKebijakanDto.setFileSizeB1B(formulasiKebijakanFileEntity.getFileSizeB1B());
        formulasiKebijakanDto.setFileOriginalNameB1B(formulasiKebijakanFileEntity.getFileOriginalNameB1B());

        formulasiKebijakanDto.setFilePathB2A(formulasiKebijakanFileEntity.getFilePathB2A());
        formulasiKebijakanDto.setFileTypeB2A(formulasiKebijakanFileEntity.getFileTypeB2A());
        formulasiKebijakanDto.setFileSizeB2A(formulasiKebijakanFileEntity.getFileSizeB2A());
        formulasiKebijakanDto.setFileOriginalNameB2A(formulasiKebijakanFileEntity.getFileOriginalNameB2A());

        formulasiKebijakanDto.setFilePathB2B(formulasiKebijakanFileEntity.getFilePathB2B());
        formulasiKebijakanDto.setFileTypeB2B(formulasiKebijakanFileEntity.getFileTypeB2B());
        formulasiKebijakanDto.setFileSizeB2B(formulasiKebijakanFileEntity.getFileSizeB2B());
        formulasiKebijakanDto.setFileOriginalNameB2B(formulasiKebijakanFileEntity.getFileOriginalNameB2B());

        formulasiKebijakanDto.setFilePathB3A(formulasiKebijakanFileEntity.getFilePathB3A());
        formulasiKebijakanDto.setFileTypeB3A(formulasiKebijakanFileEntity.getFileTypeB3A());
        formulasiKebijakanDto.setFileSizeB3A(formulasiKebijakanFileEntity.getFileSizeB3A());
        formulasiKebijakanDto.setFileOriginalNameB3A(formulasiKebijakanFileEntity.getFileOriginalNameB3A());

        formulasiKebijakanDto.setFilePathB3B(formulasiKebijakanFileEntity.getFilePathB3B());
        formulasiKebijakanDto.setFileTypeB3B(formulasiKebijakanFileEntity.getFileTypeB3B());
        formulasiKebijakanDto.setFileSizeB3B(formulasiKebijakanFileEntity.getFileSizeB3B());
        formulasiKebijakanDto.setFileOriginalNameB3B(formulasiKebijakanFileEntity.getFileOriginalNameB3B());

        formulasiKebijakanDto.setFilePathB3C(formulasiKebijakanFileEntity.getFilePathB3C());
        formulasiKebijakanDto.setFileTypeB3C(formulasiKebijakanFileEntity.getFileTypeB3C());
        formulasiKebijakanDto.setFileSizeB3C(formulasiKebijakanFileEntity.getFileSizeB3C());
        formulasiKebijakanDto.setFileOriginalNameB3C(formulasiKebijakanFileEntity.getFileOriginalNameB3C());

        formulasiKebijakanDto.setFilePathB4A(formulasiKebijakanFileEntity.getFilePathB4A());
        formulasiKebijakanDto.setFileTypeB4A(formulasiKebijakanFileEntity.getFileTypeB4A());
        formulasiKebijakanDto.setFileSizeB4A(formulasiKebijakanFileEntity.getFileSizeB4A());
        formulasiKebijakanDto.setFileOriginalNameB4A(formulasiKebijakanFileEntity.getFileOriginalNameB4A());

        formulasiKebijakanDto.setFilePathB4B(formulasiKebijakanFileEntity.getFilePathB4B());
        formulasiKebijakanDto.setFileTypeB4B(formulasiKebijakanFileEntity.getFileTypeB4B());
        formulasiKebijakanDto.setFileSizeB4B(formulasiKebijakanFileEntity.getFileSizeB4B());
        formulasiKebijakanDto.setFileOriginalNameB4B(formulasiKebijakanFileEntity.getFileOriginalNameB4B());

        formulasiKebijakanDto.setFilePathB4C(formulasiKebijakanFileEntity.getFilePathB4C());
        formulasiKebijakanDto.setFileTypeB4C(formulasiKebijakanFileEntity.getFileTypeB4C());
        formulasiKebijakanDto.setFileSizeB4C(formulasiKebijakanFileEntity.getFileSizeB4C());
        formulasiKebijakanDto.setFileOriginalNameB4C(formulasiKebijakanFileEntity.getFileOriginalNameB4C());

        formulasiKebijakanDto.setFilePathB5A(formulasiKebijakanFileEntity.getFilePathB5A());
        formulasiKebijakanDto.setFileTypeB5A(formulasiKebijakanFileEntity.getFileTypeB5A());
        formulasiKebijakanDto.setFileSizeB5A(formulasiKebijakanFileEntity.getFileSizeB5A());
        formulasiKebijakanDto.setFileOriginalNameB5A(formulasiKebijakanFileEntity.getFileOriginalNameB5A());

        formulasiKebijakanDto.setFilePathB5B(formulasiKebijakanFileEntity.getFilePathB5B());
        formulasiKebijakanDto.setFileTypeB5B(formulasiKebijakanFileEntity.getFileTypeB5B());
        formulasiKebijakanDto.setFileSizeB5B(formulasiKebijakanFileEntity.getFileSizeB5B());
        formulasiKebijakanDto.setFileOriginalNameB5B(formulasiKebijakanFileEntity.getFileOriginalNameB5B());

        formulasiKebijakanDto.setFilePathB5C(formulasiKebijakanFileEntity.getFilePathB5C());
        formulasiKebijakanDto.setFileTypeB5C(formulasiKebijakanFileEntity.getFileTypeB5C());
        formulasiKebijakanDto.setFileSizeB5C(formulasiKebijakanFileEntity.getFileSizeB5C());
        formulasiKebijakanDto.setFileOriginalNameB5C(formulasiKebijakanFileEntity.getFileOriginalNameB5C());

        return formulasiKebijakanDto;
    }
}
