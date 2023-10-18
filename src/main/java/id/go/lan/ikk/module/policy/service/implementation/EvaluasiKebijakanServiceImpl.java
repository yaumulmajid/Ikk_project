package id.go.lan.ikk.module.policy.service.implementation;

import id.go.lan.ikk.exception.ResourceNotFoundException;
import id.go.lan.ikk.exception.SubmitFailedException;
import id.go.lan.ikk.module.dashboard.presenter.model.EvaluasiKebijakanValidationRequest;
import id.go.lan.ikk.module.policy.entity.*;
import id.go.lan.ikk.module.policy.model.EvaluasiKebijakanDto;
import id.go.lan.ikk.module.policy.model.EvaluasiKebijakanValidationDto;
import id.go.lan.ikk.module.policy.repository.*;
import id.go.lan.ikk.module.policy.service.EvaluasiKebijakanService;
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
public class EvaluasiKebijakanServiceImpl implements EvaluasiKebijakanService {

    @Autowired
    private ModelMapperUtility modelMapperUtility;

    @Autowired
    private UploadFileUtility uploadFileUtility;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private PolicyDetailsService policyDetailsService;

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
    private PolicyService policyService;

    @Override
    public EvaluasiKebijakanDto getEvaluasiKebijakanByPolicyId(Long id) {
        PolicyEntity policyEntity = policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        EvaluasiKebijakanEntity evaluasiKebijakanEntity = policyEntity.getEvaluasiKebijakanEntity();
        EvaluasiKebijakanFileEntity evaluasiKebijakanFileEntity = policyEntity.getEvaluasiKebijakanFileEntity();

        return mapEvaluasiKebijakanDto(policyEntity, evaluasiKebijakanEntity, evaluasiKebijakanFileEntity);
    }

    @Override
    public EvaluasiKebijakanDto submitD1A(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        EvaluasiKebijakanEntity evaluasiKebijakanEntity = policyEntity.getEvaluasiKebijakanEntity();
        EvaluasiKebijakanFileEntity evaluasiKebijakanFileEntity = policyEntity.getEvaluasiKebijakanFileEntity();
        EvaluasiKebijakanBaseScoreEntity evaluasiKebijakanBaseScoreEntity = policyEntity.getEvaluasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": evaluasiKebijakanBaseScoreEntity.setD1A(100.0);
                break;
            case "b": evaluasiKebijakanBaseScoreEntity.setD1A(75.0);
                break;
            case "c": evaluasiKebijakanBaseScoreEntity.setD1A(50.0);
                break;
            case "d": evaluasiKebijakanBaseScoreEntity.setD1A(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        evaluasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanBaseScoreRepository.save(evaluasiKebijakanBaseScoreEntity);

        if (file != null) {
            evaluasiKebijakanFileEntity.setFilePathD1A(uploadFileUtility.uploadFile(file, nipEnumerator));
            evaluasiKebijakanFileEntity.setFileTypeD1A(file.getContentType());
            evaluasiKebijakanFileEntity.setFileSizeD1A(String.valueOf(file.getSize() / 1024));
            evaluasiKebijakanFileEntity.setFileOriginalNameD1A(file.getOriginalFilename());
            evaluasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            evaluasiKebijakanFileRepository.save(evaluasiKebijakanFileEntity);
        }

        evaluasiKebijakanEntity.setD1A(answer);
        evaluasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanRepository.save(evaluasiKebijakanEntity);

        evaluasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getEvaluasiKebijakanBaseScoreByPolicyId(policyId));
        evaluasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanBaseScoreRepository.save(evaluasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapEvaluasiKebijakanDto(policyEntity, evaluasiKebijakanEntity, evaluasiKebijakanFileEntity);
    }

    @Override
    public EvaluasiKebijakanDto submitD1B(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        EvaluasiKebijakanEntity evaluasiKebijakanEntity = policyEntity.getEvaluasiKebijakanEntity();
        EvaluasiKebijakanFileEntity evaluasiKebijakanFileEntity = policyEntity.getEvaluasiKebijakanFileEntity();
        EvaluasiKebijakanBaseScoreEntity evaluasiKebijakanBaseScoreEntity = policyEntity.getEvaluasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": evaluasiKebijakanBaseScoreEntity.setD1B(100.0);
                break;
            case "b": evaluasiKebijakanBaseScoreEntity.setD1B(75.0);
                break;
            case "c": evaluasiKebijakanBaseScoreEntity.setD1B(50.0);
                break;
            case "d": evaluasiKebijakanBaseScoreEntity.setD1B(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        evaluasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanBaseScoreRepository.save(evaluasiKebijakanBaseScoreEntity);

        if (file != null) {
            evaluasiKebijakanFileEntity.setFilePathD1B(uploadFileUtility.uploadFile(file, nipEnumerator));
            evaluasiKebijakanFileEntity.setFileTypeD1B(file.getContentType());
            evaluasiKebijakanFileEntity.setFileSizeD1B(String.valueOf(file.getSize() / 1024));
            evaluasiKebijakanFileEntity.setFileOriginalNameD1B(file.getOriginalFilename());
            evaluasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            evaluasiKebijakanFileRepository.save(evaluasiKebijakanFileEntity);
        }

        evaluasiKebijakanEntity.setD1B(answer);
        evaluasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanRepository.save(evaluasiKebijakanEntity);

        evaluasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getEvaluasiKebijakanBaseScoreByPolicyId(policyId));
        evaluasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanBaseScoreRepository.save(evaluasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapEvaluasiKebijakanDto(policyEntity, evaluasiKebijakanEntity, evaluasiKebijakanFileEntity);
    }

    @Override
    public EvaluasiKebijakanDto submitD2A(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        EvaluasiKebijakanEntity evaluasiKebijakanEntity = policyEntity.getEvaluasiKebijakanEntity();
        EvaluasiKebijakanFileEntity evaluasiKebijakanFileEntity = policyEntity.getEvaluasiKebijakanFileEntity();
        EvaluasiKebijakanBaseScoreEntity evaluasiKebijakanBaseScoreEntity = policyEntity.getEvaluasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": evaluasiKebijakanBaseScoreEntity.setD2A(100.0);
                break;
            case "b": evaluasiKebijakanBaseScoreEntity.setD2A(75.0);
                break;
            case "c": evaluasiKebijakanBaseScoreEntity.setD2A(50.0);
                break;
            case "d": evaluasiKebijakanBaseScoreEntity.setD2A(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        evaluasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanBaseScoreRepository.save(evaluasiKebijakanBaseScoreEntity);

        if (file != null) {
            evaluasiKebijakanFileEntity.setFilePathD2A(uploadFileUtility.uploadFile(file, nipEnumerator));
            evaluasiKebijakanFileEntity.setFileTypeD2A(file.getContentType());
            evaluasiKebijakanFileEntity.setFileSizeD2A(String.valueOf(file.getSize() / 1024));
            evaluasiKebijakanFileEntity.setFileOriginalNameD2A(file.getOriginalFilename());
            evaluasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            evaluasiKebijakanFileRepository.save(evaluasiKebijakanFileEntity);
        }

        evaluasiKebijakanEntity.setD2A(answer);
        evaluasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanRepository.save(evaluasiKebijakanEntity);

        evaluasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getEvaluasiKebijakanBaseScoreByPolicyId(policyId));
        evaluasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanBaseScoreRepository.save(evaluasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapEvaluasiKebijakanDto(policyEntity, evaluasiKebijakanEntity, evaluasiKebijakanFileEntity);
    }

    @Override
    public EvaluasiKebijakanDto submitD2B(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        EvaluasiKebijakanEntity evaluasiKebijakanEntity = policyEntity.getEvaluasiKebijakanEntity();
        EvaluasiKebijakanFileEntity evaluasiKebijakanFileEntity = policyEntity.getEvaluasiKebijakanFileEntity();
        EvaluasiKebijakanBaseScoreEntity evaluasiKebijakanBaseScoreEntity = policyEntity.getEvaluasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": evaluasiKebijakanBaseScoreEntity.setD2B(100.0);
                break;
            case "b": evaluasiKebijakanBaseScoreEntity.setD2B(75.0);
                break;
            case "c": evaluasiKebijakanBaseScoreEntity.setD2B(50.0);
                break;
            case "d": evaluasiKebijakanBaseScoreEntity.setD2B(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        evaluasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanBaseScoreRepository.save(evaluasiKebijakanBaseScoreEntity);

        if (file != null) {
            evaluasiKebijakanFileEntity.setFilePathD2B(uploadFileUtility.uploadFile(file, nipEnumerator));
            evaluasiKebijakanFileEntity.setFileTypeD2B(file.getContentType());
            evaluasiKebijakanFileEntity.setFileSizeD2B(String.valueOf(file.getSize() / 1024));
            evaluasiKebijakanFileEntity.setFileOriginalNameD2B(file.getOriginalFilename());
            evaluasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            evaluasiKebijakanFileRepository.save(evaluasiKebijakanFileEntity);
        }

        evaluasiKebijakanEntity.setD2B(answer);
        evaluasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanRepository.save(evaluasiKebijakanEntity);

        evaluasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getEvaluasiKebijakanBaseScoreByPolicyId(policyId));
        evaluasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanBaseScoreRepository.save(evaluasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapEvaluasiKebijakanDto(policyEntity, evaluasiKebijakanEntity, evaluasiKebijakanFileEntity);
    }

    @Override
    public EvaluasiKebijakanDto submitD3A(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        EvaluasiKebijakanEntity evaluasiKebijakanEntity = policyEntity.getEvaluasiKebijakanEntity();
        EvaluasiKebijakanFileEntity evaluasiKebijakanFileEntity = policyEntity.getEvaluasiKebijakanFileEntity();
        EvaluasiKebijakanBaseScoreEntity evaluasiKebijakanBaseScoreEntity = policyEntity.getEvaluasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": evaluasiKebijakanBaseScoreEntity.setD3A(100.0);
                break;
            case "b": evaluasiKebijakanBaseScoreEntity.setD3A(75.0);
                break;
            case "c": evaluasiKebijakanBaseScoreEntity.setD3A(50.0);
                break;
            case "d": evaluasiKebijakanBaseScoreEntity.setD3A(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        evaluasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanBaseScoreRepository.save(evaluasiKebijakanBaseScoreEntity);

        if (file != null) {
            evaluasiKebijakanFileEntity.setFilePathD3A(uploadFileUtility.uploadFile(file, nipEnumerator));
            evaluasiKebijakanFileEntity.setFileTypeD3A(file.getContentType());
            evaluasiKebijakanFileEntity.setFileSizeD3A(String.valueOf(file.getSize() / 1024));
            evaluasiKebijakanFileEntity.setFileOriginalNameD3A(file.getOriginalFilename());
            evaluasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            evaluasiKebijakanFileRepository.save(evaluasiKebijakanFileEntity);
        }

        evaluasiKebijakanEntity.setD3A(answer);
        evaluasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanRepository.save(evaluasiKebijakanEntity);

        evaluasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getEvaluasiKebijakanBaseScoreByPolicyId(policyId));
        evaluasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanBaseScoreRepository.save(evaluasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapEvaluasiKebijakanDto(policyEntity, evaluasiKebijakanEntity, evaluasiKebijakanFileEntity);
    }

    @Override
    public EvaluasiKebijakanDto submitD3B(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        EvaluasiKebijakanEntity evaluasiKebijakanEntity = policyEntity.getEvaluasiKebijakanEntity();
        EvaluasiKebijakanFileEntity evaluasiKebijakanFileEntity = policyEntity.getEvaluasiKebijakanFileEntity();
        EvaluasiKebijakanBaseScoreEntity evaluasiKebijakanBaseScoreEntity = policyEntity.getEvaluasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": evaluasiKebijakanBaseScoreEntity.setD3B(100.0);
                break;
            case "b": evaluasiKebijakanBaseScoreEntity.setD3B(75.0);
                break;
            case "c": evaluasiKebijakanBaseScoreEntity.setD3B(50.0);
                break;
            case "d": evaluasiKebijakanBaseScoreEntity.setD3B(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        evaluasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanBaseScoreRepository.save(evaluasiKebijakanBaseScoreEntity);

        if (file != null) {
            evaluasiKebijakanFileEntity.setFilePathD3B(uploadFileUtility.uploadFile(file, nipEnumerator));
            evaluasiKebijakanFileEntity.setFileTypeD3B(file.getContentType());
            evaluasiKebijakanFileEntity.setFileSizeD3B(String.valueOf(file.getSize() / 1024));
            evaluasiKebijakanFileEntity.setFileOriginalNameD3B(file.getOriginalFilename());
            evaluasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            evaluasiKebijakanFileRepository.save(evaluasiKebijakanFileEntity);
        }

        evaluasiKebijakanEntity.setD3B(answer);
        evaluasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanRepository.save(evaluasiKebijakanEntity);

        evaluasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getEvaluasiKebijakanBaseScoreByPolicyId(policyId));
        evaluasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanBaseScoreRepository.save(evaluasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapEvaluasiKebijakanDto(policyEntity, evaluasiKebijakanEntity, evaluasiKebijakanFileEntity);
    }

    @Override
    public EvaluasiKebijakanDto submitD3C(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        EvaluasiKebijakanEntity evaluasiKebijakanEntity = policyEntity.getEvaluasiKebijakanEntity();
        EvaluasiKebijakanFileEntity evaluasiKebijakanFileEntity = policyEntity.getEvaluasiKebijakanFileEntity();
        EvaluasiKebijakanBaseScoreEntity evaluasiKebijakanBaseScoreEntity = policyEntity.getEvaluasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": evaluasiKebijakanBaseScoreEntity.setD3C(100.0);
                break;
            case "b": evaluasiKebijakanBaseScoreEntity.setD3C(75.0);
                break;
            case "c": evaluasiKebijakanBaseScoreEntity.setD3C(50.0);
                break;
            case "d": evaluasiKebijakanBaseScoreEntity.setD3C(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        evaluasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanBaseScoreRepository.save(evaluasiKebijakanBaseScoreEntity);

        if (file != null) {
            evaluasiKebijakanFileEntity.setFilePathD3C(uploadFileUtility.uploadFile(file, nipEnumerator));
            evaluasiKebijakanFileEntity.setFileTypeD3C(file.getContentType());
            evaluasiKebijakanFileEntity.setFileSizeD3C(String.valueOf(file.getSize() / 1024));
            evaluasiKebijakanFileEntity.setFileOriginalNameD3C(file.getOriginalFilename());
            evaluasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            evaluasiKebijakanFileRepository.save(evaluasiKebijakanFileEntity);
        }

        evaluasiKebijakanEntity.setD3C(answer);
        evaluasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanRepository.save(evaluasiKebijakanEntity);

        evaluasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getEvaluasiKebijakanBaseScoreByPolicyId(policyId));
        evaluasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanBaseScoreRepository.save(evaluasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapEvaluasiKebijakanDto(policyEntity, evaluasiKebijakanEntity, evaluasiKebijakanFileEntity);
    }

    @Override
    public EvaluasiKebijakanDto submitD3D(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        EvaluasiKebijakanEntity evaluasiKebijakanEntity = policyEntity.getEvaluasiKebijakanEntity();
        EvaluasiKebijakanFileEntity evaluasiKebijakanFileEntity = policyEntity.getEvaluasiKebijakanFileEntity();
        EvaluasiKebijakanBaseScoreEntity evaluasiKebijakanBaseScoreEntity = policyEntity.getEvaluasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": evaluasiKebijakanBaseScoreEntity.setD3D(100.0);
                break;
            case "b": evaluasiKebijakanBaseScoreEntity.setD3D(75.0);
                break;
            case "c": evaluasiKebijakanBaseScoreEntity.setD3D(50.0);
                break;
            case "d": evaluasiKebijakanBaseScoreEntity.setD3D(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        evaluasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanBaseScoreRepository.save(evaluasiKebijakanBaseScoreEntity);

        if (file != null) {
            evaluasiKebijakanFileEntity.setFilePathD3D(uploadFileUtility.uploadFile(file, nipEnumerator));
            evaluasiKebijakanFileEntity.setFileTypeD3D(file.getContentType());
            evaluasiKebijakanFileEntity.setFileSizeD3D(String.valueOf(file.getSize() / 1024));
            evaluasiKebijakanFileEntity.setFileOriginalNameD3D(file.getOriginalFilename());
            evaluasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            evaluasiKebijakanFileRepository.save(evaluasiKebijakanFileEntity);
        }

        evaluasiKebijakanEntity.setD3D(answer);
        evaluasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanRepository.save(evaluasiKebijakanEntity);

        evaluasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getEvaluasiKebijakanBaseScoreByPolicyId(policyId));
        evaluasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanBaseScoreRepository.save(evaluasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapEvaluasiKebijakanDto(policyEntity, evaluasiKebijakanEntity, evaluasiKebijakanFileEntity);
    }

    @Override
    public EvaluasiKebijakanDto submitD3E(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        EvaluasiKebijakanEntity evaluasiKebijakanEntity = policyEntity.getEvaluasiKebijakanEntity();
        EvaluasiKebijakanFileEntity evaluasiKebijakanFileEntity = policyEntity.getEvaluasiKebijakanFileEntity();
        EvaluasiKebijakanBaseScoreEntity evaluasiKebijakanBaseScoreEntity = policyEntity.getEvaluasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": evaluasiKebijakanBaseScoreEntity.setD3E(100.0);
                break;
            case "b": evaluasiKebijakanBaseScoreEntity.setD3E(75.0);
                break;
            case "c": evaluasiKebijakanBaseScoreEntity.setD3E(50.0);
                break;
            case "d": evaluasiKebijakanBaseScoreEntity.setD3E(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        evaluasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanBaseScoreRepository.save(evaluasiKebijakanBaseScoreEntity);

        if (file != null) {
            evaluasiKebijakanFileEntity.setFilePathD3E(uploadFileUtility.uploadFile(file, nipEnumerator));
            evaluasiKebijakanFileEntity.setFileTypeD3E(file.getContentType());
            evaluasiKebijakanFileEntity.setFileSizeD3E(String.valueOf(file.getSize() / 1024));
            evaluasiKebijakanFileEntity.setFileOriginalNameD3E(file.getOriginalFilename());
            evaluasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            evaluasiKebijakanFileRepository.save(evaluasiKebijakanFileEntity);
        }

        evaluasiKebijakanEntity.setD3E(answer);
        evaluasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanRepository.save(evaluasiKebijakanEntity);

        evaluasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getEvaluasiKebijakanBaseScoreByPolicyId(policyId));
        evaluasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanBaseScoreRepository.save(evaluasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapEvaluasiKebijakanDto(policyEntity, evaluasiKebijakanEntity, evaluasiKebijakanFileEntity);
    }

    @Override
    public EvaluasiKebijakanDto submitInformasiD4(Long policyId, String answer) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        EvaluasiKebijakanEntity evaluasiKebijakanEntity = policyEntity.getEvaluasiKebijakanEntity();
        EvaluasiKebijakanFileEntity evaluasiKebijakanFileEntity = policyEntity.getEvaluasiKebijakanFileEntity();

        evaluasiKebijakanEntity.setInformasiD4(answer);
        evaluasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanRepository.save(evaluasiKebijakanEntity);

        return mapEvaluasiKebijakanDto(policyEntity, evaluasiKebijakanEntity, evaluasiKebijakanFileEntity);
    }

    @Override
    public EvaluasiKebijakanValidationDto getValidationKI(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        EvaluasiKebijakanKIEntity evaluasiKebijakanKIEntity = policyEntity.getEvaluasiKebijakanKIEntity();
        return modelMapperUtility.initialize().map(evaluasiKebijakanKIEntity, EvaluasiKebijakanValidationDto.class);
    }

    @Override
    public EvaluasiKebijakanValidationDto getValidationKU(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        EvaluasiKebijakanKUEntity evaluasiKebijakanKUEntity = policyEntity.getEvaluasiKebijakanKUEntity();
        return modelMapperUtility.initialize().map(evaluasiKebijakanKUEntity, EvaluasiKebijakanValidationDto.class);
    }

    @Override
    public void submitValidasiKI(Long policyId, EvaluasiKebijakanValidationRequest evaluasiKebijakanValidationRequest) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        EvaluasiKebijakanKIEntity evaluasiKebijakanKIEntity = policyEntity.getEvaluasiKebijakanKIEntity();
        EvaluasiKebijakanBaseScoreEntity evaluasiKebijakanBaseScoreEntity = policyEntity.getEvaluasiKebijakanBaseScoreEntity();
        EvaluasiKebijakanKIScoreEntity evaluasiKebijakanKIScoreEntity = policyEntity.getEvaluasiKebijakanKIScoreEntity();

        switch (evaluasiKebijakanValidationRequest.getD1A()) {
            case "1": evaluasiKebijakanKIScoreEntity.setD1A(evaluasiKebijakanBaseScoreEntity.getD1A() * 0/100);
                break;
            case "2": evaluasiKebijakanKIScoreEntity.setD1A(evaluasiKebijakanBaseScoreEntity.getD1A() * 25/100);
                break;
            case "3": evaluasiKebijakanKIScoreEntity.setD1A(evaluasiKebijakanBaseScoreEntity.getD1A() * 50/100);
                break;
            case "4": evaluasiKebijakanKIScoreEntity.setD1A(evaluasiKebijakanBaseScoreEntity.getD1A() * 75/100);
                break;
            case "5": evaluasiKebijakanKIScoreEntity.setD1A(evaluasiKebijakanBaseScoreEntity.getD1A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (evaluasiKebijakanValidationRequest.getD1B()) {
            case "1": evaluasiKebijakanKIScoreEntity.setD1B(evaluasiKebijakanBaseScoreEntity.getD1B() * 0/100);
                break;
            case "2": evaluasiKebijakanKIScoreEntity.setD1B(evaluasiKebijakanBaseScoreEntity.getD1B() * 25/100);
                break;
            case "3": evaluasiKebijakanKIScoreEntity.setD1B(evaluasiKebijakanBaseScoreEntity.getD1B() * 50/100);
                break;
            case "4": evaluasiKebijakanKIScoreEntity.setD1B(evaluasiKebijakanBaseScoreEntity.getD1B() * 75/100);
                break;
            case "5": evaluasiKebijakanKIScoreEntity.setD1B(evaluasiKebijakanBaseScoreEntity.getD1B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (evaluasiKebijakanValidationRequest.getD2A()) {
            case "1": evaluasiKebijakanKIScoreEntity.setD2A(evaluasiKebijakanBaseScoreEntity.getD2A() * 0/100);
                break;
            case "2": evaluasiKebijakanKIScoreEntity.setD2A(evaluasiKebijakanBaseScoreEntity.getD2A() * 25/100);
                break;
            case "3": evaluasiKebijakanKIScoreEntity.setD2A(evaluasiKebijakanBaseScoreEntity.getD2A() * 50/100);
                break;
            case "4": evaluasiKebijakanKIScoreEntity.setD2A(evaluasiKebijakanBaseScoreEntity.getD2A() * 75/100);
                break;
            case "5": evaluasiKebijakanKIScoreEntity.setD2A(evaluasiKebijakanBaseScoreEntity.getD2A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (evaluasiKebijakanValidationRequest.getD2B()) {
            case "1": evaluasiKebijakanKIScoreEntity.setD2B(evaluasiKebijakanBaseScoreEntity.getD2B() * 0/100);
                break;
            case "2": evaluasiKebijakanKIScoreEntity.setD2B(evaluasiKebijakanBaseScoreEntity.getD2B() * 25/100);
                break;
            case "3": evaluasiKebijakanKIScoreEntity.setD2B(evaluasiKebijakanBaseScoreEntity.getD2B() * 50/100);
                break;
            case "4": evaluasiKebijakanKIScoreEntity.setD2B(evaluasiKebijakanBaseScoreEntity.getD2B() * 75/100);
                break;
            case "5": evaluasiKebijakanKIScoreEntity.setD2B(evaluasiKebijakanBaseScoreEntity.getD2B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (evaluasiKebijakanValidationRequest.getD3A()) {
            case "1": evaluasiKebijakanKIScoreEntity.setD3A(evaluasiKebijakanBaseScoreEntity.getD3A() * 0/100);
                break;
            case "2": evaluasiKebijakanKIScoreEntity.setD3A(evaluasiKebijakanBaseScoreEntity.getD3A() * 25/100);
                break;
            case "3": evaluasiKebijakanKIScoreEntity.setD3A(evaluasiKebijakanBaseScoreEntity.getD3A() * 50/100);
                break;
            case "4": evaluasiKebijakanKIScoreEntity.setD3A(evaluasiKebijakanBaseScoreEntity.getD3A() * 75/100);
                break;
            case "5": evaluasiKebijakanKIScoreEntity.setD3A(evaluasiKebijakanBaseScoreEntity.getD3A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (evaluasiKebijakanValidationRequest.getD3B()) {
            case "1": evaluasiKebijakanKIScoreEntity.setD3B(evaluasiKebijakanBaseScoreEntity.getD3B() * 0/100);
                break;
            case "2": evaluasiKebijakanKIScoreEntity.setD3B(evaluasiKebijakanBaseScoreEntity.getD3B() * 25/100);
                break;
            case "3": evaluasiKebijakanKIScoreEntity.setD3B(evaluasiKebijakanBaseScoreEntity.getD3B() * 50/100);
                break;
            case "4": evaluasiKebijakanKIScoreEntity.setD3B(evaluasiKebijakanBaseScoreEntity.getD3B() * 75/100);
                break;
            case "5": evaluasiKebijakanKIScoreEntity.setD3B(evaluasiKebijakanBaseScoreEntity.getD3B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (evaluasiKebijakanValidationRequest.getD3C()) {
            case "1": evaluasiKebijakanKIScoreEntity.setD3C(evaluasiKebijakanBaseScoreEntity.getD3C() * 0/100);
                break;
            case "2": evaluasiKebijakanKIScoreEntity.setD3C(evaluasiKebijakanBaseScoreEntity.getD3C() * 25/100);
                break;
            case "3": evaluasiKebijakanKIScoreEntity.setD3C(evaluasiKebijakanBaseScoreEntity.getD3C() * 50/100);
                break;
            case "4": evaluasiKebijakanKIScoreEntity.setD3C(evaluasiKebijakanBaseScoreEntity.getD3C() * 75/100);
                break;
            case "5": evaluasiKebijakanKIScoreEntity.setD3C(evaluasiKebijakanBaseScoreEntity.getD3C() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (evaluasiKebijakanValidationRequest.getD3D()) {
            case "1": evaluasiKebijakanKIScoreEntity.setD3D(evaluasiKebijakanBaseScoreEntity.getD3D() * 0/100);
                break;
            case "2": evaluasiKebijakanKIScoreEntity.setD3D(evaluasiKebijakanBaseScoreEntity.getD3D() * 25/100);
                break;
            case "3": evaluasiKebijakanKIScoreEntity.setD3D(evaluasiKebijakanBaseScoreEntity.getD3D() * 50/100);
                break;
            case "4": evaluasiKebijakanKIScoreEntity.setD3D(evaluasiKebijakanBaseScoreEntity.getD3D() * 75/100);
                break;
            case "5": evaluasiKebijakanKIScoreEntity.setD3D(evaluasiKebijakanBaseScoreEntity.getD1A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (evaluasiKebijakanValidationRequest.getD3E()) {
            case "1": evaluasiKebijakanKIScoreEntity.setD3E(evaluasiKebijakanBaseScoreEntity.getD3E() * 0/100);
                break;
            case "2": evaluasiKebijakanKIScoreEntity.setD3E(evaluasiKebijakanBaseScoreEntity.getD3E() * 25/100);
                break;
            case "3": evaluasiKebijakanKIScoreEntity.setD3E(evaluasiKebijakanBaseScoreEntity.getD3E() * 50/100);
                break;
            case "4": evaluasiKebijakanKIScoreEntity.setD3E(evaluasiKebijakanBaseScoreEntity.getD3E() * 75/100);
                break;
            case "5": evaluasiKebijakanKIScoreEntity.setD3E(evaluasiKebijakanBaseScoreEntity.getD3E() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        if (!evaluasiKebijakanValidationRequest.getD1A().isEmpty()) {
            evaluasiKebijakanKIEntity.setD1A(evaluasiKebijakanValidationRequest.getD1A());
        }

        if (!evaluasiKebijakanValidationRequest.getD1B().isEmpty()) {
            evaluasiKebijakanKIEntity.setD1B(evaluasiKebijakanValidationRequest.getD1B());
        }

        if (!evaluasiKebijakanValidationRequest.getD2A().isEmpty()) {
            evaluasiKebijakanKIEntity.setD2A(evaluasiKebijakanValidationRequest.getD2A());
        }

        if (!evaluasiKebijakanValidationRequest.getD2B().isEmpty()) {
            evaluasiKebijakanKIEntity.setD2B(evaluasiKebijakanValidationRequest.getD2B());
        }

        if (!evaluasiKebijakanValidationRequest.getD3A().isEmpty()) {
            evaluasiKebijakanKIEntity.setD3A(evaluasiKebijakanValidationRequest.getD3A());
        }

        if (!evaluasiKebijakanValidationRequest.getD3B().isEmpty()) {
            evaluasiKebijakanKIEntity.setD3B(evaluasiKebijakanValidationRequest.getD3B());
        }

        if (!evaluasiKebijakanValidationRequest.getD3C().isEmpty()) {
            evaluasiKebijakanKIEntity.setD3C(evaluasiKebijakanValidationRequest.getD3C());
        }

        if (!evaluasiKebijakanValidationRequest.getD3D().isEmpty()) {
            evaluasiKebijakanKIEntity.setD3D(evaluasiKebijakanValidationRequest.getD3D());
        }

        if (!evaluasiKebijakanValidationRequest.getD3E().isEmpty()) {
            evaluasiKebijakanKIEntity.setD3E(evaluasiKebijakanValidationRequest.getD3E());
        }

        evaluasiKebijakanKIEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanKIRepository.save(evaluasiKebijakanKIEntity);

        evaluasiKebijakanKIScoreEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanKIScoreRepository.save(evaluasiKebijakanKIScoreEntity);

        evaluasiKebijakanKIScoreEntity.setTotalScore(policyDetailsService.getEvaluasiKebijakanValidationKIScoreByPolicyId(policyId));
        evaluasiKebijakanKIScoreEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanKIScoreRepository.save(evaluasiKebijakanKIScoreEntity);

        policyDetailsService.updatePolicyValidationKIScoreByPolicyId(policyId);
    }

    @Override
    public void submitValidasiKU(Long policyId, EvaluasiKebijakanValidationRequest evaluasiKebijakanValidationRequest) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        EvaluasiKebijakanKUEntity evaluasiKebijakanKUEntity = policyEntity.getEvaluasiKebijakanKUEntity();
        EvaluasiKebijakanBaseScoreEntity evaluasiKebijakanBaseScoreEntity = policyEntity.getEvaluasiKebijakanBaseScoreEntity();
        EvaluasiKebijakanKUScoreEntity evaluasiKebijakanKUScoreEntity = policyEntity.getEvaluasiKebijakanKUScoreEntity();

        switch (evaluasiKebijakanValidationRequest.getD1A()) {
            case "1": evaluasiKebijakanKUScoreEntity.setD1A(evaluasiKebijakanBaseScoreEntity.getD1A() * 0/100);
                break;
            case "2": evaluasiKebijakanKUScoreEntity.setD1A(evaluasiKebijakanBaseScoreEntity.getD1A() * 25/100);
                break;
            case "3": evaluasiKebijakanKUScoreEntity.setD1A(evaluasiKebijakanBaseScoreEntity.getD1A() * 50/100);
                break;
            case "4": evaluasiKebijakanKUScoreEntity.setD1A(evaluasiKebijakanBaseScoreEntity.getD1A() * 75/100);
                break;
            case "5": evaluasiKebijakanKUScoreEntity.setD1A(evaluasiKebijakanBaseScoreEntity.getD1A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (evaluasiKebijakanValidationRequest.getD1B()) {
            case "1": evaluasiKebijakanKUScoreEntity.setD1B(evaluasiKebijakanBaseScoreEntity.getD1B() * 0/100);
                break;
            case "2": evaluasiKebijakanKUScoreEntity.setD1B(evaluasiKebijakanBaseScoreEntity.getD1B() * 25/100);
                break;
            case "3": evaluasiKebijakanKUScoreEntity.setD1B(evaluasiKebijakanBaseScoreEntity.getD1B() * 50/100);
                break;
            case "4": evaluasiKebijakanKUScoreEntity.setD1B(evaluasiKebijakanBaseScoreEntity.getD1B() * 75/100);
                break;
            case "5": evaluasiKebijakanKUScoreEntity.setD1B(evaluasiKebijakanBaseScoreEntity.getD1B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (evaluasiKebijakanValidationRequest.getD2A()) {
            case "1": evaluasiKebijakanKUScoreEntity.setD2A(evaluasiKebijakanBaseScoreEntity.getD2A() * 0/100);
                break;
            case "2": evaluasiKebijakanKUScoreEntity.setD2A(evaluasiKebijakanBaseScoreEntity.getD2A() * 25/100);
                break;
            case "3": evaluasiKebijakanKUScoreEntity.setD2A(evaluasiKebijakanBaseScoreEntity.getD2A() * 50/100);
                break;
            case "4": evaluasiKebijakanKUScoreEntity.setD2A(evaluasiKebijakanBaseScoreEntity.getD2A() * 75/100);
                break;
            case "5": evaluasiKebijakanKUScoreEntity.setD2A(evaluasiKebijakanBaseScoreEntity.getD2A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (evaluasiKebijakanValidationRequest.getD2B()) {
            case "1": evaluasiKebijakanKUScoreEntity.setD2B(evaluasiKebijakanBaseScoreEntity.getD2B() * 0/100);
                break;
            case "2": evaluasiKebijakanKUScoreEntity.setD2B(evaluasiKebijakanBaseScoreEntity.getD2B() * 25/100);
                break;
            case "3": evaluasiKebijakanKUScoreEntity.setD2B(evaluasiKebijakanBaseScoreEntity.getD2B() * 50/100);
                break;
            case "4": evaluasiKebijakanKUScoreEntity.setD2B(evaluasiKebijakanBaseScoreEntity.getD2B() * 75/100);
                break;
            case "5": evaluasiKebijakanKUScoreEntity.setD2B(evaluasiKebijakanBaseScoreEntity.getD2B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (evaluasiKebijakanValidationRequest.getD3A()) {
            case "1": evaluasiKebijakanKUScoreEntity.setD3A(evaluasiKebijakanBaseScoreEntity.getD3A() * 0/100);
                break;
            case "2": evaluasiKebijakanKUScoreEntity.setD3A(evaluasiKebijakanBaseScoreEntity.getD3A() * 25/100);
                break;
            case "3": evaluasiKebijakanKUScoreEntity.setD3A(evaluasiKebijakanBaseScoreEntity.getD3A() * 50/100);
                break;
            case "4": evaluasiKebijakanKUScoreEntity.setD3A(evaluasiKebijakanBaseScoreEntity.getD3A() * 75/100);
                break;
            case "5": evaluasiKebijakanKUScoreEntity.setD3A(evaluasiKebijakanBaseScoreEntity.getD3A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (evaluasiKebijakanValidationRequest.getD3B()) {
            case "1": evaluasiKebijakanKUScoreEntity.setD3B(evaluasiKebijakanBaseScoreEntity.getD3B() * 0/100);
                break;
            case "2": evaluasiKebijakanKUScoreEntity.setD3B(evaluasiKebijakanBaseScoreEntity.getD3B() * 25/100);
                break;
            case "3": evaluasiKebijakanKUScoreEntity.setD3B(evaluasiKebijakanBaseScoreEntity.getD3B() * 50/100);
                break;
            case "4": evaluasiKebijakanKUScoreEntity.setD3B(evaluasiKebijakanBaseScoreEntity.getD3B() * 75/100);
                break;
            case "5": evaluasiKebijakanKUScoreEntity.setD3B(evaluasiKebijakanBaseScoreEntity.getD3B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (evaluasiKebijakanValidationRequest.getD3C()) {
            case "1": evaluasiKebijakanKUScoreEntity.setD3C(evaluasiKebijakanBaseScoreEntity.getD3C() * 0/100);
                break;
            case "2": evaluasiKebijakanKUScoreEntity.setD3C(evaluasiKebijakanBaseScoreEntity.getD3C() * 25/100);
                break;
            case "3": evaluasiKebijakanKUScoreEntity.setD3C(evaluasiKebijakanBaseScoreEntity.getD3C() * 50/100);
                break;
            case "4": evaluasiKebijakanKUScoreEntity.setD3C(evaluasiKebijakanBaseScoreEntity.getD3C() * 75/100);
                break;
            case "5": evaluasiKebijakanKUScoreEntity.setD3C(evaluasiKebijakanBaseScoreEntity.getD3C() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (evaluasiKebijakanValidationRequest.getD3D()) {
            case "1": evaluasiKebijakanKUScoreEntity.setD3D(evaluasiKebijakanBaseScoreEntity.getD3D() * 0/100);
                break;
            case "2": evaluasiKebijakanKUScoreEntity.setD3D(evaluasiKebijakanBaseScoreEntity.getD3D() * 25/100);
                break;
            case "3": evaluasiKebijakanKUScoreEntity.setD3D(evaluasiKebijakanBaseScoreEntity.getD3D() * 50/100);
                break;
            case "4": evaluasiKebijakanKUScoreEntity.setD3D(evaluasiKebijakanBaseScoreEntity.getD3D() * 75/100);
                break;
            case "5": evaluasiKebijakanKUScoreEntity.setD3D(evaluasiKebijakanBaseScoreEntity.getD1A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (evaluasiKebijakanValidationRequest.getD3E()) {
            case "1": evaluasiKebijakanKUScoreEntity.setD3E(evaluasiKebijakanBaseScoreEntity.getD3E() * 0/100);
                break;
            case "2": evaluasiKebijakanKUScoreEntity.setD3E(evaluasiKebijakanBaseScoreEntity.getD3E() * 25/100);
                break;
            case "3": evaluasiKebijakanKUScoreEntity.setD3E(evaluasiKebijakanBaseScoreEntity.getD3E() * 50/100);
                break;
            case "4": evaluasiKebijakanKUScoreEntity.setD3E(evaluasiKebijakanBaseScoreEntity.getD3E() * 75/100);
                break;
            case "5": evaluasiKebijakanKUScoreEntity.setD3E(evaluasiKebijakanBaseScoreEntity.getD3E() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        if (!evaluasiKebijakanValidationRequest.getD1A().isEmpty()) {
            evaluasiKebijakanKUEntity.setD1A(evaluasiKebijakanValidationRequest.getD1A());
        }

        if (!evaluasiKebijakanValidationRequest.getD1B().isEmpty()) {
            evaluasiKebijakanKUEntity.setD1B(evaluasiKebijakanValidationRequest.getD1B());
        }

        if (!evaluasiKebijakanValidationRequest.getD2A().isEmpty()) {
            evaluasiKebijakanKUEntity.setD2A(evaluasiKebijakanValidationRequest.getD2A());
        }

        if (!evaluasiKebijakanValidationRequest.getD2B().isEmpty()) {
            evaluasiKebijakanKUEntity.setD2B(evaluasiKebijakanValidationRequest.getD2B());
        }

        if (!evaluasiKebijakanValidationRequest.getD3A().isEmpty()) {
            evaluasiKebijakanKUEntity.setD3A(evaluasiKebijakanValidationRequest.getD3A());
        }

        if (!evaluasiKebijakanValidationRequest.getD3B().isEmpty()) {
            evaluasiKebijakanKUEntity.setD3B(evaluasiKebijakanValidationRequest.getD3B());
        }

        if (!evaluasiKebijakanValidationRequest.getD3C().isEmpty()) {
            evaluasiKebijakanKUEntity.setD3C(evaluasiKebijakanValidationRequest.getD3C());
        }

        if (!evaluasiKebijakanValidationRequest.getD3D().isEmpty()) {
            evaluasiKebijakanKUEntity.setD3D(evaluasiKebijakanValidationRequest.getD3D());
        }

        if (!evaluasiKebijakanValidationRequest.getD3E().isEmpty()) {
            evaluasiKebijakanKUEntity.setD3E(evaluasiKebijakanValidationRequest.getD3E());
        }

        evaluasiKebijakanKUEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanKURepository.save(evaluasiKebijakanKUEntity);

        evaluasiKebijakanKUScoreEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanKUScoreRepository.save(evaluasiKebijakanKUScoreEntity);

        evaluasiKebijakanKUScoreEntity.setTotalScore(policyDetailsService.getEvaluasiKebijakanValidationKUScoreByPolicyId(policyId));
        evaluasiKebijakanKUScoreEntity.setModifiedBy(userService.getSignedInUserId());
        evaluasiKebijakanKUScoreRepository.save(evaluasiKebijakanKUScoreEntity);

        policyDetailsService.updatePolicyValidationKUScoreByPolicyId(policyId);
    }

    @Override
    public EvaluasiKebijakanDto mapEvaluasiKebijakanDto(
            PolicyEntity policyEntity,
            EvaluasiKebijakanEntity evaluasiKebijakanEntity,
            EvaluasiKebijakanFileEntity evaluasiKebijakanFileEntity) {
        EvaluasiKebijakanDto evaluasiKebijakanDto = new EvaluasiKebijakanDto();

        evaluasiKebijakanDto.setIdKebijakan(policyEntity.getId());
        evaluasiKebijakanDto.setIdEvaluasiKebijakan(evaluasiKebijakanEntity.getId());

        evaluasiKebijakanDto.setD1A(evaluasiKebijakanEntity.getD1A());
        evaluasiKebijakanDto.setD1B(evaluasiKebijakanEntity.getD1B());
        evaluasiKebijakanDto.setD2A(evaluasiKebijakanEntity.getD2A());
        evaluasiKebijakanDto.setD2B(evaluasiKebijakanEntity.getD2B());
        evaluasiKebijakanDto.setD3A(evaluasiKebijakanEntity.getD3A());
        evaluasiKebijakanDto.setD3B(evaluasiKebijakanEntity.getD3B());
        evaluasiKebijakanDto.setD3C(evaluasiKebijakanEntity.getD3C());
        evaluasiKebijakanDto.setD3D(evaluasiKebijakanEntity.getD3D());
        evaluasiKebijakanDto.setD3E(evaluasiKebijakanEntity.getD3E());
        evaluasiKebijakanDto.setInformasiD4(evaluasiKebijakanEntity.getInformasiD4());

        evaluasiKebijakanDto.setFilePathD1A(evaluasiKebijakanFileEntity.getFilePathD1A());
        evaluasiKebijakanDto.setFileTypeD1A(evaluasiKebijakanFileEntity.getFileTypeD1A());
        evaluasiKebijakanDto.setFileSizeD1A(evaluasiKebijakanFileEntity.getFileSizeD1A());
        evaluasiKebijakanDto.setFileOriginalNameD1A(evaluasiKebijakanFileEntity.getFileOriginalNameD1A());

        evaluasiKebijakanDto.setFilePathD1B(evaluasiKebijakanFileEntity.getFilePathD1B());
        evaluasiKebijakanDto.setFileTypeD1B(evaluasiKebijakanFileEntity.getFileTypeD1B());
        evaluasiKebijakanDto.setFileSizeD1B(evaluasiKebijakanFileEntity.getFileSizeD1B());
        evaluasiKebijakanDto.setFileOriginalNameD1B(evaluasiKebijakanFileEntity.getFileOriginalNameD1B());

        evaluasiKebijakanDto.setFilePathD2A(evaluasiKebijakanFileEntity.getFilePathD2A());
        evaluasiKebijakanDto.setFileTypeD2A(evaluasiKebijakanFileEntity.getFileTypeD2A());
        evaluasiKebijakanDto.setFileSizeD2A(evaluasiKebijakanFileEntity.getFileSizeD2A());
        evaluasiKebijakanDto.setFileOriginalNameD2A(evaluasiKebijakanFileEntity.getFileOriginalNameD2A());

        evaluasiKebijakanDto.setFilePathD2B(evaluasiKebijakanFileEntity.getFilePathD2B());
        evaluasiKebijakanDto.setFileTypeD2B(evaluasiKebijakanFileEntity.getFileTypeD2B());
        evaluasiKebijakanDto.setFileSizeD2B(evaluasiKebijakanFileEntity.getFileSizeD2B());
        evaluasiKebijakanDto.setFileOriginalNameD2B(evaluasiKebijakanFileEntity.getFileOriginalNameD2B());

        evaluasiKebijakanDto.setFilePathD3A(evaluasiKebijakanFileEntity.getFilePathD3A());
        evaluasiKebijakanDto.setFileTypeD3A(evaluasiKebijakanFileEntity.getFileTypeD3A());
        evaluasiKebijakanDto.setFileSizeD3A(evaluasiKebijakanFileEntity.getFileSizeD3A());
        evaluasiKebijakanDto.setFileOriginalNameD3A(evaluasiKebijakanFileEntity.getFileOriginalNameD3A());

        evaluasiKebijakanDto.setFilePathD3B(evaluasiKebijakanFileEntity.getFilePathD3B());
        evaluasiKebijakanDto.setFileTypeD3B(evaluasiKebijakanFileEntity.getFileTypeD3B());
        evaluasiKebijakanDto.setFileSizeD3B(evaluasiKebijakanFileEntity.getFileSizeD3B());
        evaluasiKebijakanDto.setFileOriginalNameD3B(evaluasiKebijakanFileEntity.getFileOriginalNameD3B());

        evaluasiKebijakanDto.setFilePathD3C(evaluasiKebijakanFileEntity.getFilePathD3C());
        evaluasiKebijakanDto.setFileTypeD3C(evaluasiKebijakanFileEntity.getFileTypeD3C());
        evaluasiKebijakanDto.setFileSizeD3C(evaluasiKebijakanFileEntity.getFileSizeD3C());
        evaluasiKebijakanDto.setFileOriginalNameD3C(evaluasiKebijakanFileEntity.getFileOriginalNameD3C());

        evaluasiKebijakanDto.setFilePathD3D(evaluasiKebijakanFileEntity.getFilePathD3D());
        evaluasiKebijakanDto.setFileTypeD3D(evaluasiKebijakanFileEntity.getFileTypeD3D());
        evaluasiKebijakanDto.setFileSizeD3D(evaluasiKebijakanFileEntity.getFileSizeD3D());
        evaluasiKebijakanDto.setFileOriginalNameD3D(evaluasiKebijakanFileEntity.getFileOriginalNameD3D());

        evaluasiKebijakanDto.setFilePathD3E(evaluasiKebijakanFileEntity.getFilePathD3E());
        evaluasiKebijakanDto.setFileTypeD3E(evaluasiKebijakanFileEntity.getFileTypeD3E());
        evaluasiKebijakanDto.setFileSizeD3E(evaluasiKebijakanFileEntity.getFileSizeD3E());
        evaluasiKebijakanDto.setFileOriginalNameD3E(evaluasiKebijakanFileEntity.getFileOriginalNameD3E());

        return evaluasiKebijakanDto;
    }
}
