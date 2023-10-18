package id.go.lan.ikk.module.policy.service.implementation;

import id.go.lan.ikk.exception.ResourceNotFoundException;
import id.go.lan.ikk.exception.SubmitFailedException;
import id.go.lan.ikk.module.dashboard.presenter.model.ImplementasiKebijakanValidationRequest;
import id.go.lan.ikk.module.policy.entity.*;
import id.go.lan.ikk.module.policy.model.ImplementasiKebijakanDto;
import id.go.lan.ikk.module.policy.model.ImplementasiKebijakanValidationDto;
import id.go.lan.ikk.module.policy.repository.*;
import id.go.lan.ikk.module.policy.service.ImplementasiKebijakanService;
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
public class ImplementasiKebijakanServiceImpl implements ImplementasiKebijakanService {

    @Autowired
    private ModelMapperUtility modelMapperUtility;

    @Autowired
    private UploadFileUtility uploadFileUtility;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private PolicyDetailsService policyDetailsService;

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
    private UserService userService;

    @Autowired
    private UserMasterService userMasterService;

    @Autowired
    private PolicyService policyService;

    @Override
    public ImplementasiKebijakanDto getImplementasiKebijakanByPolicyId(Long id) {
        PolicyEntity policyEntity = policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        ImplementasiKebijakanEntity implementasiKebijakanEntity = policyEntity.getImplementasiKebijakanEntity();
        ImplementasiKebijakanFileEntity implementasiKebijakanFileEntity = policyEntity.getImplementasiKebijakanFileEntity();

        return mapImplementasiKebijakanDto(policyEntity, implementasiKebijakanEntity, implementasiKebijakanFileEntity);
    }

    @Override
    public ImplementasiKebijakanDto submitC1A(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        ImplementasiKebijakanEntity implementasiKebijakanEntity = policyEntity.getImplementasiKebijakanEntity();
        ImplementasiKebijakanFileEntity implementasiKebijakanFileEntity = policyEntity.getImplementasiKebijakanFileEntity();
        ImplementasiKebijakanBaseScoreEntity implementasiKebijakanBaseScoreEntity = policyEntity.getImplementasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": implementasiKebijakanBaseScoreEntity.setC1A(100.0);
                break;
            case "b": implementasiKebijakanBaseScoreEntity.setC1A(75.0);
                break;
            case "c": implementasiKebijakanBaseScoreEntity.setC1A(50.0);
                break;
            case "d": implementasiKebijakanBaseScoreEntity.setC1A(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        implementasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanBaseScoreRepository.save(implementasiKebijakanBaseScoreEntity);

        if (file != null) {
            implementasiKebijakanFileEntity.setFilePathC1A(uploadFileUtility.uploadFile(file, nipEnumerator));
            implementasiKebijakanFileEntity.setFileTypeC1A(file.getContentType());
            implementasiKebijakanFileEntity.setFileSizeC1A(String.valueOf(file.getSize() / 1024));
            implementasiKebijakanFileEntity.setFileOriginalNameC1A(file.getOriginalFilename());
            implementasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            implementasiKebijakanFileRepository.save(implementasiKebijakanFileEntity);
        }

        implementasiKebijakanEntity.setC1A(answer);
        implementasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanRepository.save(implementasiKebijakanEntity);

        implementasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getImplementasiKebijakanBaseScoreByPolicyId(policyId));
        implementasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanBaseScoreRepository.save(implementasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapImplementasiKebijakanDto(policyEntity, implementasiKebijakanEntity, implementasiKebijakanFileEntity);
    }

    @Override
    public ImplementasiKebijakanDto submitC1B(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        ImplementasiKebijakanEntity implementasiKebijakanEntity = policyEntity.getImplementasiKebijakanEntity();
        ImplementasiKebijakanFileEntity implementasiKebijakanFileEntity = policyEntity.getImplementasiKebijakanFileEntity();
        ImplementasiKebijakanBaseScoreEntity implementasiKebijakanBaseScoreEntity = policyEntity.getImplementasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": implementasiKebijakanBaseScoreEntity.setC1B(100.0);
                break;
            case "b": implementasiKebijakanBaseScoreEntity.setC1B(75.0);
                break;
            case "c": implementasiKebijakanBaseScoreEntity.setC1B(50.0);
                break;
            case "d": implementasiKebijakanBaseScoreEntity.setC1B(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        implementasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanBaseScoreRepository.save(implementasiKebijakanBaseScoreEntity);

        if (file != null) {
            implementasiKebijakanFileEntity.setFilePathC1B(uploadFileUtility.uploadFile(file, nipEnumerator));
            implementasiKebijakanFileEntity.setFileTypeC1B(file.getContentType());
            implementasiKebijakanFileEntity.setFileSizeC1B(String.valueOf(file.getSize() / 1024));
            implementasiKebijakanFileEntity.setFileOriginalNameC1B(file.getOriginalFilename());
            implementasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            implementasiKebijakanFileRepository.save(implementasiKebijakanFileEntity);
        }

        implementasiKebijakanEntity.setC1B(answer);
        implementasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanRepository.save(implementasiKebijakanEntity);

        implementasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getImplementasiKebijakanBaseScoreByPolicyId(policyId));
        implementasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanBaseScoreRepository.save(implementasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapImplementasiKebijakanDto(policyEntity, implementasiKebijakanEntity, implementasiKebijakanFileEntity);
    }

    @Override
    public ImplementasiKebijakanDto submitC1C(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        ImplementasiKebijakanEntity implementasiKebijakanEntity = policyEntity.getImplementasiKebijakanEntity();
        ImplementasiKebijakanFileEntity implementasiKebijakanFileEntity = policyEntity.getImplementasiKebijakanFileEntity();
        ImplementasiKebijakanBaseScoreEntity implementasiKebijakanBaseScoreEntity = policyEntity.getImplementasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": implementasiKebijakanBaseScoreEntity.setC1C(100.0);
                break;
            case "b": implementasiKebijakanBaseScoreEntity.setC1C(75.0);
                break;
            case "c": implementasiKebijakanBaseScoreEntity.setC1C(50.0);
                break;
            case "d": implementasiKebijakanBaseScoreEntity.setC1C(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        implementasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanBaseScoreRepository.save(implementasiKebijakanBaseScoreEntity);

        if (file != null) {
            implementasiKebijakanFileEntity.setFilePathC1C(uploadFileUtility.uploadFile(file, nipEnumerator));
            implementasiKebijakanFileEntity.setFileTypeC1C(file.getContentType());
            implementasiKebijakanFileEntity.setFileSizeC1C(String.valueOf(file.getSize() / 1024));
            implementasiKebijakanFileEntity.setFileOriginalNameC1C(file.getOriginalFilename());
            implementasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            implementasiKebijakanFileRepository.save(implementasiKebijakanFileEntity);
        }

        implementasiKebijakanEntity.setC1C(answer);
        implementasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanRepository.save(implementasiKebijakanEntity);

        implementasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getImplementasiKebijakanBaseScoreByPolicyId(policyId));
        implementasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanBaseScoreRepository.save(implementasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapImplementasiKebijakanDto(policyEntity, implementasiKebijakanEntity, implementasiKebijakanFileEntity);
    }

    @Override
    public ImplementasiKebijakanDto submitC1D(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        ImplementasiKebijakanEntity implementasiKebijakanEntity = policyEntity.getImplementasiKebijakanEntity();
        ImplementasiKebijakanFileEntity implementasiKebijakanFileEntity = policyEntity.getImplementasiKebijakanFileEntity();
        ImplementasiKebijakanBaseScoreEntity implementasiKebijakanBaseScoreEntity = policyEntity.getImplementasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": implementasiKebijakanBaseScoreEntity.setC1D(100.0);
                break;
            case "b": implementasiKebijakanBaseScoreEntity.setC1D(75.0);
                break;
            case "c": implementasiKebijakanBaseScoreEntity.setC1D(50.0);
                break;
            case "d": implementasiKebijakanBaseScoreEntity.setC1D(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        implementasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanBaseScoreRepository.save(implementasiKebijakanBaseScoreEntity);

        if (file != null) {
            implementasiKebijakanFileEntity.setFilePathC1D(uploadFileUtility.uploadFile(file, nipEnumerator));
            implementasiKebijakanFileEntity.setFileTypeC1D(file.getContentType());
            implementasiKebijakanFileEntity.setFileSizeC1D(String.valueOf(file.getSize() / 1024));
            implementasiKebijakanFileEntity.setFileOriginalNameC1D(file.getOriginalFilename());
            implementasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            implementasiKebijakanFileRepository.save(implementasiKebijakanFileEntity);
        }

        implementasiKebijakanEntity.setC1D(answer);
        implementasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanRepository.save(implementasiKebijakanEntity);

        implementasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getImplementasiKebijakanBaseScoreByPolicyId(policyId));
        implementasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanBaseScoreRepository.save(implementasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapImplementasiKebijakanDto(policyEntity, implementasiKebijakanEntity, implementasiKebijakanFileEntity);
    }

    @Override
    public ImplementasiKebijakanDto submitC2A(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        ImplementasiKebijakanEntity implementasiKebijakanEntity = policyEntity.getImplementasiKebijakanEntity();
        ImplementasiKebijakanFileEntity implementasiKebijakanFileEntity = policyEntity.getImplementasiKebijakanFileEntity();
        ImplementasiKebijakanBaseScoreEntity implementasiKebijakanBaseScoreEntity = policyEntity.getImplementasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": implementasiKebijakanBaseScoreEntity.setC2A(100.0);
                break;
            case "b": implementasiKebijakanBaseScoreEntity.setC2A(75.0);
                break;
            case "c": implementasiKebijakanBaseScoreEntity.setC2A(50.0);
                break;
            case "d": implementasiKebijakanBaseScoreEntity.setC2A(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        implementasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanBaseScoreRepository.save(implementasiKebijakanBaseScoreEntity);

        if (file != null) {
            implementasiKebijakanFileEntity.setFilePathC2A(uploadFileUtility.uploadFile(file, nipEnumerator));
            implementasiKebijakanFileEntity.setFileTypeC2A(file.getContentType());
            implementasiKebijakanFileEntity.setFileSizeC2A(String.valueOf(file.getSize() / 1024));
            implementasiKebijakanFileEntity.setFileOriginalNameC2A(file.getOriginalFilename());
            implementasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            implementasiKebijakanFileRepository.save(implementasiKebijakanFileEntity);
        }

        implementasiKebijakanEntity.setC2A(answer);
        implementasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanRepository.save(implementasiKebijakanEntity);

        implementasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getImplementasiKebijakanBaseScoreByPolicyId(policyId));
        implementasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanBaseScoreRepository.save(implementasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapImplementasiKebijakanDto(policyEntity, implementasiKebijakanEntity, implementasiKebijakanFileEntity);
    }

    @Override
    public ImplementasiKebijakanDto submitC2B(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        ImplementasiKebijakanEntity implementasiKebijakanEntity = policyEntity.getImplementasiKebijakanEntity();
        ImplementasiKebijakanFileEntity implementasiKebijakanFileEntity = policyEntity.getImplementasiKebijakanFileEntity();
        ImplementasiKebijakanBaseScoreEntity implementasiKebijakanBaseScoreEntity = policyEntity.getImplementasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": implementasiKebijakanBaseScoreEntity.setC2B(100.0);
                break;
            case "b": implementasiKebijakanBaseScoreEntity.setC2B(75.0);
                break;
            case "c": implementasiKebijakanBaseScoreEntity.setC2B(50.0);
                break;
            case "d": implementasiKebijakanBaseScoreEntity.setC2B(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        implementasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanBaseScoreRepository.save(implementasiKebijakanBaseScoreEntity);

        if (file != null) {
            implementasiKebijakanFileEntity.setFilePathC2B(uploadFileUtility.uploadFile(file, nipEnumerator));
            implementasiKebijakanFileEntity.setFileTypeC2B(file.getContentType());
            implementasiKebijakanFileEntity.setFileSizeC2B(String.valueOf(file.getSize() / 1024));
            implementasiKebijakanFileEntity.setFileOriginalNameC2B(file.getOriginalFilename());
            implementasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            implementasiKebijakanFileRepository.save(implementasiKebijakanFileEntity);
        }

        implementasiKebijakanEntity.setC2B(answer);
        implementasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanRepository.save(implementasiKebijakanEntity);

        implementasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getImplementasiKebijakanBaseScoreByPolicyId(policyId));
        implementasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanBaseScoreRepository.save(implementasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapImplementasiKebijakanDto(policyEntity, implementasiKebijakanEntity, implementasiKebijakanFileEntity);
    }

    @Override
    public ImplementasiKebijakanDto submitC2C(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        ImplementasiKebijakanEntity implementasiKebijakanEntity = policyEntity.getImplementasiKebijakanEntity();
        ImplementasiKebijakanFileEntity implementasiKebijakanFileEntity = policyEntity.getImplementasiKebijakanFileEntity();
        ImplementasiKebijakanBaseScoreEntity implementasiKebijakanBaseScoreEntity = policyEntity.getImplementasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": implementasiKebijakanBaseScoreEntity.setC2C(100.0);
                break;
            case "b": implementasiKebijakanBaseScoreEntity.setC2C(75.0);
                break;
            case "c": implementasiKebijakanBaseScoreEntity.setC2C(50.0);
                break;
            case "d": implementasiKebijakanBaseScoreEntity.setC2C(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        implementasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanBaseScoreRepository.save(implementasiKebijakanBaseScoreEntity);

        if (file != null) {
            implementasiKebijakanFileEntity.setFilePathC2C(uploadFileUtility.uploadFile(file, nipEnumerator));
            implementasiKebijakanFileEntity.setFileTypeC2C(file.getContentType());
            implementasiKebijakanFileEntity.setFileSizeC2C(String.valueOf(file.getSize() / 1024));
            implementasiKebijakanFileEntity.setFileOriginalNameC2C(file.getOriginalFilename());
            implementasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            implementasiKebijakanFileRepository.save(implementasiKebijakanFileEntity);
        }

        implementasiKebijakanEntity.setC2C(answer);
        implementasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanRepository.save(implementasiKebijakanEntity);

        implementasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getImplementasiKebijakanBaseScoreByPolicyId(policyId));
        implementasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanBaseScoreRepository.save(implementasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapImplementasiKebijakanDto(policyEntity, implementasiKebijakanEntity, implementasiKebijakanFileEntity);
    }

    @Override
    public ImplementasiKebijakanDto submitC3A(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        ImplementasiKebijakanEntity implementasiKebijakanEntity = policyEntity.getImplementasiKebijakanEntity();
        ImplementasiKebijakanFileEntity implementasiKebijakanFileEntity = policyEntity.getImplementasiKebijakanFileEntity();
        ImplementasiKebijakanBaseScoreEntity implementasiKebijakanBaseScoreEntity = policyEntity.getImplementasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": implementasiKebijakanBaseScoreEntity.setC3A(100.0);
                break;
            case "b": implementasiKebijakanBaseScoreEntity.setC3A(75.0);
                break;
            case "c": implementasiKebijakanBaseScoreEntity.setC3A(50.0);
                break;
            case "d": implementasiKebijakanBaseScoreEntity.setC3A(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        implementasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanBaseScoreRepository.save(implementasiKebijakanBaseScoreEntity);

        if (file != null) {
            implementasiKebijakanFileEntity.setFilePathC3A(uploadFileUtility.uploadFile(file, nipEnumerator));
            implementasiKebijakanFileEntity.setFileTypeC3A(file.getContentType());
            implementasiKebijakanFileEntity.setFileSizeC3A(String.valueOf(file.getSize() / 1024));
            implementasiKebijakanFileEntity.setFileOriginalNameC3A(file.getOriginalFilename());
            implementasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            implementasiKebijakanFileRepository.save(implementasiKebijakanFileEntity);
        }

        implementasiKebijakanEntity.setC3A(answer);
        implementasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanRepository.save(implementasiKebijakanEntity);

        implementasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getImplementasiKebijakanBaseScoreByPolicyId(policyId));
        implementasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanBaseScoreRepository.save(implementasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapImplementasiKebijakanDto(policyEntity, implementasiKebijakanEntity, implementasiKebijakanFileEntity);
    }

    @Override
    public ImplementasiKebijakanDto submitC3B(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        ImplementasiKebijakanEntity implementasiKebijakanEntity = policyEntity.getImplementasiKebijakanEntity();
        ImplementasiKebijakanFileEntity implementasiKebijakanFileEntity = policyEntity.getImplementasiKebijakanFileEntity();
        ImplementasiKebijakanBaseScoreEntity implementasiKebijakanBaseScoreEntity = policyEntity.getImplementasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": implementasiKebijakanBaseScoreEntity.setC3B(100.0);
                break;
            case "b": implementasiKebijakanBaseScoreEntity.setC3B(75.0);
                break;
            case "c": implementasiKebijakanBaseScoreEntity.setC3B(50.0);
                break;
            case "d": implementasiKebijakanBaseScoreEntity.setC3B(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        implementasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanBaseScoreRepository.save(implementasiKebijakanBaseScoreEntity);

        if (file != null) {
            implementasiKebijakanFileEntity.setFilePathC3B(uploadFileUtility.uploadFile(file, nipEnumerator));
            implementasiKebijakanFileEntity.setFileTypeC3B(file.getContentType());
            implementasiKebijakanFileEntity.setFileSizeC3B(String.valueOf(file.getSize() / 1024));
            implementasiKebijakanFileEntity.setFileOriginalNameC3B(file.getOriginalFilename());
            implementasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            implementasiKebijakanFileRepository.save(implementasiKebijakanFileEntity);
        }

        implementasiKebijakanEntity.setC3B(answer);
        implementasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanRepository.save(implementasiKebijakanEntity);

        implementasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getImplementasiKebijakanBaseScoreByPolicyId(policyId));
        implementasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanBaseScoreRepository.save(implementasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapImplementasiKebijakanDto(policyEntity, implementasiKebijakanEntity, implementasiKebijakanFileEntity);
    }

    @Override
    public ImplementasiKebijakanDto submitC3C(Long policyId, String answer, MultipartFile file) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        String nipEnumerator = userMasterService.findById(userService.getSignedInUserId()).getUsername();

        ImplementasiKebijakanEntity implementasiKebijakanEntity = policyEntity.getImplementasiKebijakanEntity();
        ImplementasiKebijakanFileEntity implementasiKebijakanFileEntity = policyEntity.getImplementasiKebijakanFileEntity();
        ImplementasiKebijakanBaseScoreEntity implementasiKebijakanBaseScoreEntity = policyEntity.getImplementasiKebijakanBaseScoreEntity();

        switch (answer) {
            case "a": implementasiKebijakanBaseScoreEntity.setC3C(100.0);
                break;
            case "b": implementasiKebijakanBaseScoreEntity.setC3C(75.0);
                break;
            case "c": implementasiKebijakanBaseScoreEntity.setC3C(50.0);
                break;
            case "d": implementasiKebijakanBaseScoreEntity.setC3C(25.0);
                break;
            default: throw new SubmitFailedException("Pilihan jawaban tidak tersedia");
        }

        implementasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanBaseScoreRepository.save(implementasiKebijakanBaseScoreEntity);

        if (file != null) {
            implementasiKebijakanFileEntity.setFilePathC3C(uploadFileUtility.uploadFile(file, nipEnumerator));
            implementasiKebijakanFileEntity.setFileTypeC3C(file.getContentType());
            implementasiKebijakanFileEntity.setFileSizeC3C(String.valueOf(file.getSize() / 1024));
            implementasiKebijakanFileEntity.setFileOriginalNameC3C(file.getOriginalFilename());
            implementasiKebijakanFileEntity.setModifiedBy(userService.getSignedInUserId());
            implementasiKebijakanFileRepository.save(implementasiKebijakanFileEntity);
        }

        implementasiKebijakanEntity.setC3C(answer);
        implementasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanRepository.save(implementasiKebijakanEntity);

        implementasiKebijakanBaseScoreEntity.setTotalScore(policyDetailsService.getImplementasiKebijakanBaseScoreByPolicyId(policyId));
        implementasiKebijakanBaseScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanBaseScoreRepository.save(implementasiKebijakanBaseScoreEntity);

        policyDetailsService.updatePolicyProgressByPolicyId(policyId);
        policyDetailsService.updatePolicyBaseScoreByPolicyId(policyId);

        return mapImplementasiKebijakanDto(policyEntity, implementasiKebijakanEntity, implementasiKebijakanFileEntity);
    }

    @Override
    public ImplementasiKebijakanDto submitInformasiC4(Long policyId, String answer) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        // if (policyService.isPolicyBeingValidated(policyEntity)) {
//            throw new SubmitFailedException("Kebijakan sedang divalidasi");
//        }

        ImplementasiKebijakanEntity implementasiKebijakanEntity = policyEntity.getImplementasiKebijakanEntity();
        ImplementasiKebijakanFileEntity implementasiKebijakanFileEntity = policyEntity.getImplementasiKebijakanFileEntity();

        implementasiKebijakanEntity.setInformasiC4(answer);
        implementasiKebijakanEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanRepository.save(implementasiKebijakanEntity);

        return mapImplementasiKebijakanDto(policyEntity, implementasiKebijakanEntity, implementasiKebijakanFileEntity);
    }

    @Override
    public ImplementasiKebijakanValidationDto getValidationKI(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        ImplementasiKebijakanKIEntity implementasiKebijakanKIEntity = policyEntity.getImplementasiKebijakanKIEntity();
        return modelMapperUtility.initialize().map(implementasiKebijakanKIEntity, ImplementasiKebijakanValidationDto.class);
    }

    @Override
    public ImplementasiKebijakanValidationDto getValidationKU(Long policyId) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        ImplementasiKebijakanKUEntity implementasiKebijakanKUEntity = policyEntity.getImplementasiKebijakanKUEntity();
        return modelMapperUtility.initialize().map(implementasiKebijakanKUEntity, ImplementasiKebijakanValidationDto.class);
    }

    @Override
    public void submitValidasiKI(Long policyId, ImplementasiKebijakanValidationRequest implementasiKebijakanValidationRequest) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        ImplementasiKebijakanKIEntity implementasiKebijakanKIEntity = policyEntity.getImplementasiKebijakanKIEntity();
        ImplementasiKebijakanBaseScoreEntity implementasiKebijakanBaseScoreEntity = policyEntity.getImplementasiKebijakanBaseScoreEntity();
        ImplementasiKebijakanKIScoreEntity implementasiKebijakanKIScoreEntity = policyEntity.getImplementasiKebijakanKIScoreEntity();

        switch (implementasiKebijakanValidationRequest.getC1A()) {
            case "1": implementasiKebijakanKIScoreEntity.setC1A(implementasiKebijakanBaseScoreEntity.getC1A() * 0/100);
                break;
            case "2": implementasiKebijakanKIScoreEntity.setC1A(implementasiKebijakanBaseScoreEntity.getC1A() * 25/100);
                break;
            case "3": implementasiKebijakanKIScoreEntity.setC1A(implementasiKebijakanBaseScoreEntity.getC1A() * 50/100);
                break;
            case "4": implementasiKebijakanKIScoreEntity.setC1A(implementasiKebijakanBaseScoreEntity.getC1A() * 75/100);
                break;
            case "5": implementasiKebijakanKIScoreEntity.setC1A(implementasiKebijakanBaseScoreEntity.getC1A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (implementasiKebijakanValidationRequest.getC1B()) {
            case "1": implementasiKebijakanKIScoreEntity.setC1B(implementasiKebijakanBaseScoreEntity.getC1B() * 0/100);
                break;
            case "2": implementasiKebijakanKIScoreEntity.setC1B(implementasiKebijakanBaseScoreEntity.getC1B() * 25/100);
                break;
            case "3": implementasiKebijakanKIScoreEntity.setC1B(implementasiKebijakanBaseScoreEntity.getC1B() * 50/100);
                break;
            case "4": implementasiKebijakanKIScoreEntity.setC1B(implementasiKebijakanBaseScoreEntity.getC1B() * 75/100);
                break;
            case "5": implementasiKebijakanKIScoreEntity.setC1B(implementasiKebijakanBaseScoreEntity.getC1B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (implementasiKebijakanValidationRequest.getC1C()) {
            case "1": implementasiKebijakanKIScoreEntity.setC1C(implementasiKebijakanBaseScoreEntity.getC1C() * 0/100);
                break;
            case "2": implementasiKebijakanKIScoreEntity.setC1C(implementasiKebijakanBaseScoreEntity.getC1C() * 25/100);
                break;
            case "3": implementasiKebijakanKIScoreEntity.setC1C(implementasiKebijakanBaseScoreEntity.getC1C() * 50/100);
                break;
            case "4": implementasiKebijakanKIScoreEntity.setC1C(implementasiKebijakanBaseScoreEntity.getC1C() * 75/100);
                break;
            case "5": implementasiKebijakanKIScoreEntity.setC1C(implementasiKebijakanBaseScoreEntity.getC1C() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (implementasiKebijakanValidationRequest.getC1D()) {
            case "1": implementasiKebijakanKIScoreEntity.setC1D(implementasiKebijakanBaseScoreEntity.getC1D() * 0/100);
                break;
            case "2": implementasiKebijakanKIScoreEntity.setC1D(implementasiKebijakanBaseScoreEntity.getC1D() * 25/100);
                break;
            case "3": implementasiKebijakanKIScoreEntity.setC1D(implementasiKebijakanBaseScoreEntity.getC1D() * 50/100);
                break;
            case "4": implementasiKebijakanKIScoreEntity.setC1D(implementasiKebijakanBaseScoreEntity.getC1D() * 75/100);
                break;
            case "5": implementasiKebijakanKIScoreEntity.setC1D(implementasiKebijakanBaseScoreEntity.getC1D() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (implementasiKebijakanValidationRequest.getC2A()) {
            case "1": implementasiKebijakanKIScoreEntity.setC2A(implementasiKebijakanBaseScoreEntity.getC2A() * 0/100);
                break;
            case "2": implementasiKebijakanKIScoreEntity.setC2A(implementasiKebijakanBaseScoreEntity.getC2A() * 25/100);
                break;
            case "3": implementasiKebijakanKIScoreEntity.setC2A(implementasiKebijakanBaseScoreEntity.getC2A() * 50/100);
                break;
            case "4": implementasiKebijakanKIScoreEntity.setC2A(implementasiKebijakanBaseScoreEntity.getC2A() * 75/100);
                break;
            case "5": implementasiKebijakanKIScoreEntity.setC2A(implementasiKebijakanBaseScoreEntity.getC2A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (implementasiKebijakanValidationRequest.getC2B()) {
            case "1": implementasiKebijakanKIScoreEntity.setC2B(implementasiKebijakanBaseScoreEntity.getC2B() * 0/100);
                break;
            case "2": implementasiKebijakanKIScoreEntity.setC2B(implementasiKebijakanBaseScoreEntity.getC2B() * 25/100);
                break;
            case "3": implementasiKebijakanKIScoreEntity.setC2B(implementasiKebijakanBaseScoreEntity.getC2B() * 50/100);
                break;
            case "4": implementasiKebijakanKIScoreEntity.setC2B(implementasiKebijakanBaseScoreEntity.getC2B() * 75/100);
                break;
            case "5": implementasiKebijakanKIScoreEntity.setC2B(implementasiKebijakanBaseScoreEntity.getC2B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (implementasiKebijakanValidationRequest.getC2C()) {
            case "1": implementasiKebijakanKIScoreEntity.setC2C(implementasiKebijakanBaseScoreEntity.getC2C() * 0/100);
                break;
            case "2": implementasiKebijakanKIScoreEntity.setC2C(implementasiKebijakanBaseScoreEntity.getC2C() * 25/100);
                break;
            case "3": implementasiKebijakanKIScoreEntity.setC2C(implementasiKebijakanBaseScoreEntity.getC2C() * 50/100);
                break;
            case "4": implementasiKebijakanKIScoreEntity.setC2C(implementasiKebijakanBaseScoreEntity.getC2C() * 75/100);
                break;
            case "5": implementasiKebijakanKIScoreEntity.setC2C(implementasiKebijakanBaseScoreEntity.getC2C() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (implementasiKebijakanValidationRequest.getC3A()) {
            case "1": implementasiKebijakanKIScoreEntity.setC3A(implementasiKebijakanBaseScoreEntity.getC3A() * 0/100);
                break;
            case "2": implementasiKebijakanKIScoreEntity.setC3A(implementasiKebijakanBaseScoreEntity.getC3A() * 25/100);
                break;
            case "3": implementasiKebijakanKIScoreEntity.setC3A(implementasiKebijakanBaseScoreEntity.getC3A() * 50/100);
                break;
            case "4": implementasiKebijakanKIScoreEntity.setC3A(implementasiKebijakanBaseScoreEntity.getC3A() * 75/100);
                break;
            case "5": implementasiKebijakanKIScoreEntity.setC3A(implementasiKebijakanBaseScoreEntity.getC3A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (implementasiKebijakanValidationRequest.getC3B()) {
            case "1": implementasiKebijakanKIScoreEntity.setC3B(implementasiKebijakanBaseScoreEntity.getC3B() * 0/100);
                break;
            case "2": implementasiKebijakanKIScoreEntity.setC3B(implementasiKebijakanBaseScoreEntity.getC3B() * 25/100);
                break;
            case "3": implementasiKebijakanKIScoreEntity.setC3B(implementasiKebijakanBaseScoreEntity.getC3B() * 50/100);
                break;
            case "4": implementasiKebijakanKIScoreEntity.setC3B(implementasiKebijakanBaseScoreEntity.getC3B() * 75/100);
                break;
            case "5": implementasiKebijakanKIScoreEntity.setC3B(implementasiKebijakanBaseScoreEntity.getC3B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (implementasiKebijakanValidationRequest.getC3C()) {
            case "1": implementasiKebijakanKIScoreEntity.setC3C(implementasiKebijakanBaseScoreEntity.getC3C() * 0/100);
                break;
            case "2": implementasiKebijakanKIScoreEntity.setC3C(implementasiKebijakanBaseScoreEntity.getC3C() * 25/100);
                break;
            case "3": implementasiKebijakanKIScoreEntity.setC3C(implementasiKebijakanBaseScoreEntity.getC3C() * 50/100);
                break;
            case "4": implementasiKebijakanKIScoreEntity.setC3C(implementasiKebijakanBaseScoreEntity.getC3C() * 75/100);
                break;
            case "5": implementasiKebijakanKIScoreEntity.setC3C(implementasiKebijakanBaseScoreEntity.getC3C() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        if (!implementasiKebijakanValidationRequest.getC1A().isEmpty()) {
            implementasiKebijakanKIEntity.setC1A(implementasiKebijakanValidationRequest.getC1A());
        }

        if (!implementasiKebijakanValidationRequest.getC1B().isEmpty()) {
            implementasiKebijakanKIEntity.setC1B(implementasiKebijakanValidationRequest.getC1B());
        }

        if (!implementasiKebijakanValidationRequest.getC1C().isEmpty()) {
            implementasiKebijakanKIEntity.setC1C(implementasiKebijakanValidationRequest.getC1C());
        }

        if (!implementasiKebijakanValidationRequest.getC1D().isEmpty()) {
            implementasiKebijakanKIEntity.setC1D(implementasiKebijakanValidationRequest.getC1D());
        }

        if (!implementasiKebijakanValidationRequest.getC2A().isEmpty()) {
            implementasiKebijakanKIEntity.setC2A(implementasiKebijakanValidationRequest.getC2A());
        }

        if (!implementasiKebijakanValidationRequest.getC2B().isEmpty()) {
            implementasiKebijakanKIEntity.setC2B(implementasiKebijakanValidationRequest.getC2B());
        }

        if (!implementasiKebijakanValidationRequest.getC2C().isEmpty()) {
            implementasiKebijakanKIEntity.setC2C(implementasiKebijakanValidationRequest.getC2C());
        }

        if (!implementasiKebijakanValidationRequest.getC3A().isEmpty()) {
            implementasiKebijakanKIEntity.setC3A(implementasiKebijakanValidationRequest.getC3A());
        }

        if (!implementasiKebijakanValidationRequest.getC3B().isEmpty()) {
            implementasiKebijakanKIEntity.setC3B(implementasiKebijakanValidationRequest.getC3B());
        }

        if (!implementasiKebijakanValidationRequest.getC3C().isEmpty()) {
            implementasiKebijakanKIEntity.setC3C(implementasiKebijakanValidationRequest.getC3C());
        }

        implementasiKebijakanKIEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanKIRepository.save(implementasiKebijakanKIEntity);

        implementasiKebijakanKIScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanKIScoreRepository.save(implementasiKebijakanKIScoreEntity);

        implementasiKebijakanKIScoreEntity.setTotalScore(policyDetailsService.getImplementasiKebijakanValidationKIScoreByPolicyId(policyId));
        implementasiKebijakanKIScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanKIScoreRepository.save(implementasiKebijakanKIScoreEntity);

        policyDetailsService.updatePolicyValidationKIScoreByPolicyId(policyId);
    }

    @Override
    public void submitValidasiKU(Long policyId, ImplementasiKebijakanValidationRequest implementasiKebijakanValidationRequest) {
        PolicyEntity policyEntity = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));

        ImplementasiKebijakanKUEntity implementasiKebijakanKUEntity = policyEntity.getImplementasiKebijakanKUEntity();
        ImplementasiKebijakanBaseScoreEntity implementasiKebijakanBaseScoreEntity = policyEntity.getImplementasiKebijakanBaseScoreEntity();
        ImplementasiKebijakanKUScoreEntity implementasiKebijakanKUScoreEntity = policyEntity.getImplementasiKebijakanKUScoreEntity();

        switch (implementasiKebijakanValidationRequest.getC1A()) {
            case "1": implementasiKebijakanKUScoreEntity.setC1A(implementasiKebijakanBaseScoreEntity.getC1A() * 0/100);
                break;
            case "2": implementasiKebijakanKUScoreEntity.setC1A(implementasiKebijakanBaseScoreEntity.getC1A() * 25/100);
                break;
            case "3": implementasiKebijakanKUScoreEntity.setC1A(implementasiKebijakanBaseScoreEntity.getC1A() * 50/100);
                break;
            case "4": implementasiKebijakanKUScoreEntity.setC1A(implementasiKebijakanBaseScoreEntity.getC1A() * 75/100);
                break;
            case "5": implementasiKebijakanKUScoreEntity.setC1A(implementasiKebijakanBaseScoreEntity.getC1A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (implementasiKebijakanValidationRequest.getC1B()) {
            case "1": implementasiKebijakanKUScoreEntity.setC1B(implementasiKebijakanBaseScoreEntity.getC1B() * 0/100);
                break;
            case "2": implementasiKebijakanKUScoreEntity.setC1B(implementasiKebijakanBaseScoreEntity.getC1B() * 25/100);
                break;
            case "3": implementasiKebijakanKUScoreEntity.setC1B(implementasiKebijakanBaseScoreEntity.getC1B() * 50/100);
                break;
            case "4": implementasiKebijakanKUScoreEntity.setC1B(implementasiKebijakanBaseScoreEntity.getC1B() * 75/100);
                break;
            case "5": implementasiKebijakanKUScoreEntity.setC1B(implementasiKebijakanBaseScoreEntity.getC1B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (implementasiKebijakanValidationRequest.getC1C()) {
            case "1": implementasiKebijakanKUScoreEntity.setC1C(implementasiKebijakanBaseScoreEntity.getC1C() * 0/100);
                break;
            case "2": implementasiKebijakanKUScoreEntity.setC1C(implementasiKebijakanBaseScoreEntity.getC1C() * 25/100);
                break;
            case "3": implementasiKebijakanKUScoreEntity.setC1C(implementasiKebijakanBaseScoreEntity.getC1C() * 50/100);
                break;
            case "4": implementasiKebijakanKUScoreEntity.setC1C(implementasiKebijakanBaseScoreEntity.getC1C() * 75/100);
                break;
            case "5": implementasiKebijakanKUScoreEntity.setC1C(implementasiKebijakanBaseScoreEntity.getC1C() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (implementasiKebijakanValidationRequest.getC1D()) {
            case "1": implementasiKebijakanKUScoreEntity.setC1D(implementasiKebijakanBaseScoreEntity.getC1D() * 0/100);
                break;
            case "2": implementasiKebijakanKUScoreEntity.setC1D(implementasiKebijakanBaseScoreEntity.getC1D() * 25/100);
                break;
            case "3": implementasiKebijakanKUScoreEntity.setC1D(implementasiKebijakanBaseScoreEntity.getC1D() * 50/100);
                break;
            case "4": implementasiKebijakanKUScoreEntity.setC1D(implementasiKebijakanBaseScoreEntity.getC1D() * 75/100);
                break;
            case "5": implementasiKebijakanKUScoreEntity.setC1D(implementasiKebijakanBaseScoreEntity.getC1D() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (implementasiKebijakanValidationRequest.getC2A()) {
            case "1": implementasiKebijakanKUScoreEntity.setC2A(implementasiKebijakanBaseScoreEntity.getC2A() * 0/100);
                break;
            case "2": implementasiKebijakanKUScoreEntity.setC2A(implementasiKebijakanBaseScoreEntity.getC2A() * 25/100);
                break;
            case "3": implementasiKebijakanKUScoreEntity.setC2A(implementasiKebijakanBaseScoreEntity.getC2A() * 50/100);
                break;
            case "4": implementasiKebijakanKUScoreEntity.setC2A(implementasiKebijakanBaseScoreEntity.getC2A() * 75/100);
                break;
            case "5": implementasiKebijakanKUScoreEntity.setC2A(implementasiKebijakanBaseScoreEntity.getC2A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (implementasiKebijakanValidationRequest.getC2B()) {
            case "1": implementasiKebijakanKUScoreEntity.setC2B(implementasiKebijakanBaseScoreEntity.getC2B() * 0/100);
                break;
            case "2": implementasiKebijakanKUScoreEntity.setC2B(implementasiKebijakanBaseScoreEntity.getC2B() * 25/100);
                break;
            case "3": implementasiKebijakanKUScoreEntity.setC2B(implementasiKebijakanBaseScoreEntity.getC2B() * 50/100);
                break;
            case "4": implementasiKebijakanKUScoreEntity.setC2B(implementasiKebijakanBaseScoreEntity.getC2B() * 75/100);
                break;
            case "5": implementasiKebijakanKUScoreEntity.setC2B(implementasiKebijakanBaseScoreEntity.getC2B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (implementasiKebijakanValidationRequest.getC2C()) {
            case "1": implementasiKebijakanKUScoreEntity.setC2C(implementasiKebijakanBaseScoreEntity.getC2C() * 0/100);
                break;
            case "2": implementasiKebijakanKUScoreEntity.setC2C(implementasiKebijakanBaseScoreEntity.getC2C() * 25/100);
                break;
            case "3": implementasiKebijakanKUScoreEntity.setC2C(implementasiKebijakanBaseScoreEntity.getC2C() * 50/100);
                break;
            case "4": implementasiKebijakanKUScoreEntity.setC2C(implementasiKebijakanBaseScoreEntity.getC2C() * 75/100);
                break;
            case "5": implementasiKebijakanKUScoreEntity.setC2C(implementasiKebijakanBaseScoreEntity.getC2C() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (implementasiKebijakanValidationRequest.getC3A()) {
            case "1": implementasiKebijakanKUScoreEntity.setC3A(implementasiKebijakanBaseScoreEntity.getC3A() * 0/100);
                break;
            case "2": implementasiKebijakanKUScoreEntity.setC3A(implementasiKebijakanBaseScoreEntity.getC3A() * 25/100);
                break;
            case "3": implementasiKebijakanKUScoreEntity.setC3A(implementasiKebijakanBaseScoreEntity.getC3A() * 50/100);
                break;
            case "4": implementasiKebijakanKUScoreEntity.setC3A(implementasiKebijakanBaseScoreEntity.getC3A() * 75/100);
                break;
            case "5": implementasiKebijakanKUScoreEntity.setC3A(implementasiKebijakanBaseScoreEntity.getC3A() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (implementasiKebijakanValidationRequest.getC3B()) {
            case "1": implementasiKebijakanKUScoreEntity.setC3B(implementasiKebijakanBaseScoreEntity.getC3B() * 0/100);
                break;
            case "2": implementasiKebijakanKUScoreEntity.setC3B(implementasiKebijakanBaseScoreEntity.getC3B() * 25/100);
                break;
            case "3": implementasiKebijakanKUScoreEntity.setC3B(implementasiKebijakanBaseScoreEntity.getC3B() * 50/100);
                break;
            case "4": implementasiKebijakanKUScoreEntity.setC3B(implementasiKebijakanBaseScoreEntity.getC3B() * 75/100);
                break;
            case "5": implementasiKebijakanKUScoreEntity.setC3B(implementasiKebijakanBaseScoreEntity.getC3B() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        switch (implementasiKebijakanValidationRequest.getC3C()) {
            case "1": implementasiKebijakanKUScoreEntity.setC3C(implementasiKebijakanBaseScoreEntity.getC3C() * 0/100);
                break;
            case "2": implementasiKebijakanKUScoreEntity.setC3C(implementasiKebijakanBaseScoreEntity.getC3C() * 25/100);
                break;
            case "3": implementasiKebijakanKUScoreEntity.setC3C(implementasiKebijakanBaseScoreEntity.getC3C() * 50/100);
                break;
            case "4": implementasiKebijakanKUScoreEntity.setC3C(implementasiKebijakanBaseScoreEntity.getC3C() * 75/100);
                break;
            case "5": implementasiKebijakanKUScoreEntity.setC3C(implementasiKebijakanBaseScoreEntity.getC3C() * 100/100);
                break;
            case "":
                break;
            default: throw new SubmitFailedException("Input nilai salah");
        }

        if (!implementasiKebijakanValidationRequest.getC1A().isEmpty()) {
            implementasiKebijakanKUEntity.setC1A(implementasiKebijakanValidationRequest.getC1A());
        }

        if (!implementasiKebijakanValidationRequest.getC1B().isEmpty()) {
            implementasiKebijakanKUEntity.setC1B(implementasiKebijakanValidationRequest.getC1B());
        }

        if (!implementasiKebijakanValidationRequest.getC1C().isEmpty()) {
            implementasiKebijakanKUEntity.setC1C(implementasiKebijakanValidationRequest.getC1C());
        }

        if (!implementasiKebijakanValidationRequest.getC1D().isEmpty()) {
            implementasiKebijakanKUEntity.setC1D(implementasiKebijakanValidationRequest.getC1D());
        }

        if (!implementasiKebijakanValidationRequest.getC2A().isEmpty()) {
            implementasiKebijakanKUEntity.setC2A(implementasiKebijakanValidationRequest.getC2A());
        }

        if (!implementasiKebijakanValidationRequest.getC2B().isEmpty()) {
            implementasiKebijakanKUEntity.setC2B(implementasiKebijakanValidationRequest.getC2B());
        }

        if (!implementasiKebijakanValidationRequest.getC2C().isEmpty()) {
            implementasiKebijakanKUEntity.setC2C(implementasiKebijakanValidationRequest.getC2C());
        }

        if (!implementasiKebijakanValidationRequest.getC3A().isEmpty()) {
            implementasiKebijakanKUEntity.setC3A(implementasiKebijakanValidationRequest.getC3A());
        }

        if (!implementasiKebijakanValidationRequest.getC3B().isEmpty()) {
            implementasiKebijakanKUEntity.setC3B(implementasiKebijakanValidationRequest.getC3B());
        }

        if (!implementasiKebijakanValidationRequest.getC3C().isEmpty()) {
            implementasiKebijakanKUEntity.setC3C(implementasiKebijakanValidationRequest.getC3C());
        }

        implementasiKebijakanKUEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanKURepository.save(implementasiKebijakanKUEntity);

        implementasiKebijakanKUScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanKUScoreRepository.save(implementasiKebijakanKUScoreEntity);

        implementasiKebijakanKUScoreEntity.setTotalScore(policyDetailsService.getImplementasiKebijakanValidationKUScoreByPolicyId(policyId));
        implementasiKebijakanKUScoreEntity.setModifiedBy(userService.getSignedInUserId());
        implementasiKebijakanKUScoreRepository.save(implementasiKebijakanKUScoreEntity);

        policyDetailsService.updatePolicyValidationKUScoreByPolicyId(policyId);
    }

    @Override
    public ImplementasiKebijakanDto mapImplementasiKebijakanDto(
            PolicyEntity policyEntity,
            ImplementasiKebijakanEntity implementasiKebijakanEntity,
            ImplementasiKebijakanFileEntity implementasiKebijakanFileEntity) {
        ImplementasiKebijakanDto implementasiKebijakanDto = new ImplementasiKebijakanDto();

        implementasiKebijakanDto.setIdKebijakan(policyEntity.getId());
        implementasiKebijakanDto.setIdImplementasiKebijakan(implementasiKebijakanEntity.getId());

        implementasiKebijakanDto.setC1A(implementasiKebijakanEntity.getC1A());
        implementasiKebijakanDto.setC1B(implementasiKebijakanEntity.getC1B());
        implementasiKebijakanDto.setC1C(implementasiKebijakanEntity.getC1C());
        implementasiKebijakanDto.setC1D(implementasiKebijakanEntity.getC1D());
        implementasiKebijakanDto.setC2A(implementasiKebijakanEntity.getC2A());
        implementasiKebijakanDto.setC2B(implementasiKebijakanEntity.getC2B());
        implementasiKebijakanDto.setC2C(implementasiKebijakanEntity.getC2C());
        implementasiKebijakanDto.setC3A(implementasiKebijakanEntity.getC3A());
        implementasiKebijakanDto.setC3B(implementasiKebijakanEntity.getC3B());
        implementasiKebijakanDto.setC3C(implementasiKebijakanEntity.getC3C());
        implementasiKebijakanDto.setInformasiC4(implementasiKebijakanEntity.getInformasiC4());

        implementasiKebijakanDto.setFilePathC1A(implementasiKebijakanFileEntity.getFilePathC1A());
        implementasiKebijakanDto.setFileTypeC1A(implementasiKebijakanFileEntity.getFileTypeC1A());
        implementasiKebijakanDto.setFileSizeC1A(implementasiKebijakanFileEntity.getFileSizeC1A());
        implementasiKebijakanDto.setFileOriginalNameC1A(implementasiKebijakanFileEntity.getFileOriginalNameC1A());

        implementasiKebijakanDto.setFilePathC1B(implementasiKebijakanFileEntity.getFilePathC1B());
        implementasiKebijakanDto.setFileTypeC1B(implementasiKebijakanFileEntity.getFileTypeC1B());
        implementasiKebijakanDto.setFileSizeC1B(implementasiKebijakanFileEntity.getFileSizeC1B());
        implementasiKebijakanDto.setFileOriginalNameC1B(implementasiKebijakanFileEntity.getFileOriginalNameC1B());

        implementasiKebijakanDto.setFilePathC1C(implementasiKebijakanFileEntity.getFilePathC1C());
        implementasiKebijakanDto.setFileTypeC1C(implementasiKebijakanFileEntity.getFileTypeC1C());
        implementasiKebijakanDto.setFileSizeC1C(implementasiKebijakanFileEntity.getFileSizeC1C());
        implementasiKebijakanDto.setFileOriginalNameC1C(implementasiKebijakanFileEntity.getFileOriginalNameC1C());

        implementasiKebijakanDto.setFilePathC1D(implementasiKebijakanFileEntity.getFilePathC1D());
        implementasiKebijakanDto.setFileTypeC1D(implementasiKebijakanFileEntity.getFileTypeC1D());
        implementasiKebijakanDto.setFileSizeC1D(implementasiKebijakanFileEntity.getFileSizeC1D());
        implementasiKebijakanDto.setFileOriginalNameC1D(implementasiKebijakanFileEntity.getFileOriginalNameC1D());

        implementasiKebijakanDto.setFilePathC2A(implementasiKebijakanFileEntity.getFilePathC2A());
        implementasiKebijakanDto.setFileTypeC2A(implementasiKebijakanFileEntity.getFileTypeC2A());
        implementasiKebijakanDto.setFileSizeC2A(implementasiKebijakanFileEntity.getFileSizeC2A());
        implementasiKebijakanDto.setFileOriginalNameC2A(implementasiKebijakanFileEntity.getFileOriginalNameC2A());

        implementasiKebijakanDto.setFilePathC2B(implementasiKebijakanFileEntity.getFilePathC2B());
        implementasiKebijakanDto.setFileTypeC2B(implementasiKebijakanFileEntity.getFileTypeC2B());
        implementasiKebijakanDto.setFileSizeC2B(implementasiKebijakanFileEntity.getFileSizeC2B());
        implementasiKebijakanDto.setFileOriginalNameC2B(implementasiKebijakanFileEntity.getFileOriginalNameC2B());

        implementasiKebijakanDto.setFilePathC2C(implementasiKebijakanFileEntity.getFilePathC2C());
        implementasiKebijakanDto.setFileTypeC2C(implementasiKebijakanFileEntity.getFileTypeC2C());
        implementasiKebijakanDto.setFileSizeC2C(implementasiKebijakanFileEntity.getFileSizeC2C());
        implementasiKebijakanDto.setFileOriginalNameC2C(implementasiKebijakanFileEntity.getFileOriginalNameC2C());

        implementasiKebijakanDto.setFilePathC3A(implementasiKebijakanFileEntity.getFilePathC3A());
        implementasiKebijakanDto.setFileTypeC3A(implementasiKebijakanFileEntity.getFileTypeC3A());
        implementasiKebijakanDto.setFileSizeC3A(implementasiKebijakanFileEntity.getFileSizeC3A());
        implementasiKebijakanDto.setFileOriginalNameC3A(implementasiKebijakanFileEntity.getFileOriginalNameC3A());

        implementasiKebijakanDto.setFilePathC3B(implementasiKebijakanFileEntity.getFilePathC3B());
        implementasiKebijakanDto.setFileTypeC3B(implementasiKebijakanFileEntity.getFileTypeC3B());
        implementasiKebijakanDto.setFileSizeC3B(implementasiKebijakanFileEntity.getFileSizeC3B());
        implementasiKebijakanDto.setFileOriginalNameC3B(implementasiKebijakanFileEntity.getFileOriginalNameC3B());

        implementasiKebijakanDto.setFilePathC3C(implementasiKebijakanFileEntity.getFilePathC3C());
        implementasiKebijakanDto.setFileTypeC3C(implementasiKebijakanFileEntity.getFileTypeC3C());
        implementasiKebijakanDto.setFileSizeC3C(implementasiKebijakanFileEntity.getFileSizeC3C());
        implementasiKebijakanDto.setFileOriginalNameC3C(implementasiKebijakanFileEntity.getFileOriginalNameC3C());

        return implementasiKebijakanDto;
    }
}
