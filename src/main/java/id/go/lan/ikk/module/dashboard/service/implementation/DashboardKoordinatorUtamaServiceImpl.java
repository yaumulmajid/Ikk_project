package id.go.lan.ikk.module.dashboard.service.implementation;

import id.go.lan.ikk.module.dashboard.model.DashboardKoordinatorUtamaCardPolicyDto;
import id.go.lan.ikk.module.dashboard.model.DashboardPoliciesPerAgencyDto;
import id.go.lan.ikk.module.dashboard.model.DashboardPolicyProgressDto;
import id.go.lan.ikk.module.dashboard.presenter.model.AgendaSettingValidationRequest;
import id.go.lan.ikk.module.dashboard.presenter.model.EvaluasiKebijakanValidationRequest;
import id.go.lan.ikk.module.dashboard.presenter.model.FormulasiKebijakanValidationRequest;
import id.go.lan.ikk.module.dashboard.presenter.model.ImplementasiKebijakanValidationRequest;
import id.go.lan.ikk.module.dashboard.service.DashboardKoordinatorUtamaService;
import id.go.lan.ikk.module.policy.entity.*;
import id.go.lan.ikk.module.policy.model.*;
import id.go.lan.ikk.module.policy.service.*;
import id.go.lan.ikk.module.user.entity.AgencyEntity;
import id.go.lan.ikk.module.user.entity.UserEntity;
import id.go.lan.ikk.module.user.model.GetUserDetailDto;
import id.go.lan.ikk.module.user.service.AgencyMasterService;
import id.go.lan.ikk.module.user.service.UserMasterService;
import id.go.lan.ikk.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DashboardKoordinatorUtamaServiceImpl implements DashboardKoordinatorUtamaService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMasterService userMasterService;

    @Autowired
    private AgencyMasterService agencyMasterService;

    @Autowired
    private PolicyService policyService;

    @Autowired
    private PolicyMasterService policyMasterService;

    @Autowired
    private AgendaSettingService agendaSettingService;

    @Autowired
    private FormulasiKebijakanService formulasiKebijakanService;

    @Autowired
    private ImplementasiKebijakanService implementasiKebijakanService;

    @Autowired
    private EvaluasiKebijakanService evaluasiKebijakanService;

    @Autowired
    private InstrumentAnswerService instrumentAnswerService;

    @Override
    public List<DashboardPoliciesPerAgencyDto> getAllPoliciesPerAgency(Long koordinatorInstansiId) {
        List<UserEntity> userEntityList = userMasterService.findByCreatedBy(koordinatorInstansiId);

        List<DashboardPoliciesPerAgencyDto> dashboardPoliciesPerAgencyDtoList = new ArrayList<>();
        for (UserEntity userEntity : userEntityList) {
            if (policyMasterService.getLatestSentByAdminAtPolicy(userEntity.getAgency()) != null) {
                DashboardPoliciesPerAgencyDto dashboardPoliciesPerAgencyDto = new DashboardPoliciesPerAgencyDto();
                dashboardPoliciesPerAgencyDto.setIdInstansi(userEntity.getAgency().getId());
                dashboardPoliciesPerAgencyDto.setNamaInstansi(userEntity.getAgency().getName());
                dashboardPoliciesPerAgencyDto.setTotalKebijakan(policyMasterService.countPoliciesByAgency(userEntity.getAgency()));
                dashboardPoliciesPerAgencyDto.setTanggal(policyMasterService.getLatestSentByAdminAtPolicy(userEntity.getAgency()));
                dashboardPoliciesPerAgencyDto.setIsBeingVerified(policyMasterService.checkIsBeingVerifiedStatus(userEntity.getAgency()));
                dashboardPoliciesPerAgencyDtoList.add(dashboardPoliciesPerAgencyDto);
            }
        }

        return dashboardPoliciesPerAgencyDtoList;
    }

    @Override
    public List<PolicyDto> getAllAgencyPoliciesByAgencyId(Long agencyId) {
        AgencyEntity agencyEntity = agencyMasterService.getAgencyById(agencyId);
        List<PolicyEntity> policyEntityList = policyMasterService.getAllPoliciesByAgency(agencyEntity);

        List<PolicyDto> policyDtoList = new ArrayList<>();
        for (PolicyEntity policyEntity : policyEntityList) {
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

            if (policyEntity.getEnumeratorId() == null) {
                policyDto.setEnumerator(null);
            } else {
                GetUserDetailDto enumerator = userService.getById(policyEntity.getEnumeratorId());
                policyDto.setEnumerator(enumerator);
            }

            policyDto.setInstansi(policyEntity.getAgency().getName());
            policyDto.setProgres(policyEntity.getPolicyDetailsEntity().getProgress());

            policyDtoList.add(policyDto);
        }

        return policyDtoList;
    }

    @Override
    public void startValidationPolicyByPolicyId(Long policyId) {
        policyService.startValidationKU(policyId);
    }

    @Override
    public void finishValidationPolicyByPolicyId(Long policyId) {
        policyService.finishValidationKU(policyId);
    }

    @Override
    public void publishValidationPolicyByPolicyId(Long policyId) {
        policyService.publishValidationKU(policyId);
    }

    @Override
    public void cancelValidationPolicyByPolicyId(Long policyId) {
        policyService.cancelValidationKU(policyId);
    }

    @Override
    public AgendaSettingValidationDto getAgendaSettingValidationByPolicyId(Long policyId) {
        return agendaSettingService.getValidationKU(policyId);
    }

    @Override
    public FormulasiKebijakanValidationDto getFormulasiKebijakanValidationByPolicyId(Long policyId) {
        return formulasiKebijakanService.getValidationKU(policyId);
    }

    @Override
    public ImplementasiKebijakanValidationDto getImplementasiKebijakanValidationByPolicyId(Long policyId) {
        return implementasiKebijakanService.getValidationKU(policyId);
    }

    @Override
    public EvaluasiKebijakanValidationDto getEvaluasiKebijakanValidationByPolicyId(Long policyId) {
        return evaluasiKebijakanService.getValidationKU(policyId);
    }

    @Override
    public void submitAgendaSettingValidationByPolicyId(Long policyId, AgendaSettingValidationRequest requestBody) {
        agendaSettingService.submitValidasiKU(policyId, requestBody);
    }

    @Override
    public void submitFormulasiKebijakanValidationByPolicyId(Long policyId, FormulasiKebijakanValidationRequest requestBody) {
        formulasiKebijakanService.submitValidasiKU(policyId, requestBody);
    }

    @Override
    public void submitImplementasiKebijakanValidationByPolicyId(Long policyId, ImplementasiKebijakanValidationRequest requestBody) {
        implementasiKebijakanService.submitValidasiKU(policyId, requestBody);
    }

    @Override
    public void submitEvaluasiKebijakanValidationByPolicyId(Long policyId, EvaluasiKebijakanValidationRequest requestBody) {
        evaluasiKebijakanService.submitValidasiKU(policyId, requestBody);
    }

    @Override
    public DashboardKoordinatorUtamaCardPolicyDto getDashboardCardPolicy(Long policyId) {
        PolicyEntity policyEntity = policyMasterService.getPolicyByPolicyId(policyId);

        DashboardKoordinatorUtamaCardPolicyDto dashboardKoordinatorUtamaCardPolicyDto = new DashboardKoordinatorUtamaCardPolicyDto();
        dashboardKoordinatorUtamaCardPolicyDto.setIdKebijakan(policyEntity.getId());
        dashboardKoordinatorUtamaCardPolicyDto.setInstansi(policyEntity.getAgency().getName());
        dashboardKoordinatorUtamaCardPolicyDto.setNamaKebijakan(policyEntity.getName());
        dashboardKoordinatorUtamaCardPolicyDto.setProsesKebijakan(policyEntity.getPolicyStatusEntity().getName().name());
        dashboardKoordinatorUtamaCardPolicyDto.setStatusKebijakan(policyEntity.getPolicyProcessEntity().getName().name());
        dashboardKoordinatorUtamaCardPolicyDto.setTanggalPengesahan(policyEntity.getPolicyDetailsEntity().getEffectiveDate());
        dashboardKoordinatorUtamaCardPolicyDto.setProgres(policyEntity.getPolicyDetailsEntity().getProgress());
        dashboardKoordinatorUtamaCardPolicyDto.setTotalNilaiSatu(policyEntity.getPolicyDetailsEntity().getBaseScore());
        dashboardKoordinatorUtamaCardPolicyDto.setTotalNilaiDua(policyEntity.getPolicyDetailsEntity().getValidationKIScore());
        dashboardKoordinatorUtamaCardPolicyDto.setTotalNilaiTiga(policyEntity.getPolicyDetailsEntity().getValidationKUScore());
        dashboardKoordinatorUtamaCardPolicyDto.setNilaiSatuAgendaSetting(policyEntity.getAgendaSettingBaseScoreEntity().getTotalScore());
        dashboardKoordinatorUtamaCardPolicyDto.setNilaiSatuFormulasiKebijakan(policyEntity.getFormulasiKebijakanBaseScoreEntity().getTotalScore());
        dashboardKoordinatorUtamaCardPolicyDto.setNilaiSatuImplementasiKebijakan(policyEntity.getImplementasiKebijakanBaseScoreEntity().getTotalScore());
        dashboardKoordinatorUtamaCardPolicyDto.setNilaiSatuEvaluasiKebijakan(policyEntity.getEvaluasiKebijakanBaseScoreEntity().getTotalScore());
        dashboardKoordinatorUtamaCardPolicyDto.setNilaiDuaAgendaSetting(policyEntity.getAgendaSettingKIScoreEntity().getTotalScore());
        dashboardKoordinatorUtamaCardPolicyDto.setNilaiDuaFormulasiKebijakan(policyEntity.getFormulasiKebijakanKIScoreEntity().getTotalScore());
        dashboardKoordinatorUtamaCardPolicyDto.setNilaiDuaImplementasiKebijakan(policyEntity.getImplementasiKebijakanKIScoreEntity().getTotalScore());
        dashboardKoordinatorUtamaCardPolicyDto.setNilaiDuaEvaluasiKebijakan(policyEntity.getEvaluasiKebijakanKIScoreEntity().getTotalScore());
        dashboardKoordinatorUtamaCardPolicyDto.setNilaiTigaAgendaSetting(policyEntity.getAgendaSettingKUScoreEntity().getTotalScore());
        dashboardKoordinatorUtamaCardPolicyDto.setNilaiTigaFormulasiKebijakan(policyEntity.getFormulasiKebijakanKUScoreEntity().getTotalScore());
        dashboardKoordinatorUtamaCardPolicyDto.setNilaiTigaImplementasiKebijakan(policyEntity.getImplementasiKebijakanKUScoreEntity().getTotalScore());
        dashboardKoordinatorUtamaCardPolicyDto.setNilaiTigaEvaluasiKebijakan(policyEntity.getEvaluasiKebijakanKUScoreEntity().getTotalScore());

        return dashboardKoordinatorUtamaCardPolicyDto;
    }

    @Override
    public DashboardPolicyProgressDto getPolicyProgressDetailByPolicyId(Long id) {
        PolicyEntity policyEntity = policyMasterService.getPolicyByPolicyId(id);
        DashboardPolicyProgressDto dashboardPolicyProgressDto = new DashboardPolicyProgressDto();

        dashboardPolicyProgressDto.setAgendaSetting(convertAgendaSettingValueToText(policyEntity));
        dashboardPolicyProgressDto.setFormulasiKebijakan(convertFormulasiKebijakanValueToText(policyEntity));
        dashboardPolicyProgressDto.setImplementasiKebijakan(convertImplementasiKebijakanValueToText(policyEntity));
        dashboardPolicyProgressDto.setEvaluasiKebijakan(convertEvaluasiKebijakanValueToText(policyEntity));

        return dashboardPolicyProgressDto;
    }

    private AgendaSettingDto convertAgendaSettingValueToText(PolicyEntity policyEntity) {
        AgendaSettingEntity agendaSettingEntity = policyEntity.getAgendaSettingEntity();
        AgendaSettingFileEntity agendaSettingFileEntity = policyEntity.getAgendaSettingFileEntity();

        AgendaSettingDto agendaSettingDto = agendaSettingService.mapAgendaSettingDto(
                policyEntity,
                agendaSettingEntity,
                agendaSettingFileEntity);

        switch ((agendaSettingEntity.getA1A() != null) ? agendaSettingEntity.getA1A() : "") {
            case "a": agendaSettingDto.setA1A(instrumentAnswerService.getInstrumentAnswerById("a1aa"));
                break;
            case "b": agendaSettingDto.setA1A(instrumentAnswerService.getInstrumentAnswerById("a1ab"));
                break;
            case "c": agendaSettingDto.setA1A(instrumentAnswerService.getInstrumentAnswerById("a1ac"));
                break;
            case "d": agendaSettingDto.setA1A(instrumentAnswerService.getInstrumentAnswerById("a1ad"));
                break;
            default: agendaSettingDto.setA1A(null);
        }

        switch ((agendaSettingEntity.getA1B() != null) ? agendaSettingEntity.getA1B() : "") {
            case "a": agendaSettingDto.setA1B(instrumentAnswerService.getInstrumentAnswerById("a1ba"));
                break;
            case "b": agendaSettingDto.setA1B(instrumentAnswerService.getInstrumentAnswerById("a1bb"));
                break;
            case "c": agendaSettingDto.setA1B(instrumentAnswerService.getInstrumentAnswerById("a1bc"));
                break;
            case "d": agendaSettingDto.setA1B(instrumentAnswerService.getInstrumentAnswerById("a1bd"));
                break;
            default: agendaSettingDto.setA1B(null);
        }

        switch ((agendaSettingEntity.getA1C() != null) ? agendaSettingEntity.getA1C() : "") {
            case "a": agendaSettingDto.setA1C(instrumentAnswerService.getInstrumentAnswerById("a1ca"));
                break;
            case "b": agendaSettingDto.setA1C(instrumentAnswerService.getInstrumentAnswerById("a1cb"));
                break;
            case "c": agendaSettingDto.setA1C(instrumentAnswerService.getInstrumentAnswerById("a1cc"));
                break;
            case "d": agendaSettingDto.setA1C(instrumentAnswerService.getInstrumentAnswerById("a1cd"));
                break;
            default: agendaSettingDto.setA1C(null);
        }

        switch ((agendaSettingEntity.getA1D() != null) ? agendaSettingEntity.getA1D() : "") {
            case "a": agendaSettingDto.setA1D(instrumentAnswerService.getInstrumentAnswerById("a1da"));
                break;
            case "b": agendaSettingDto.setA1D(instrumentAnswerService.getInstrumentAnswerById("a1db"));
                break;
            case "c": agendaSettingDto.setA1D(instrumentAnswerService.getInstrumentAnswerById("a1dc"));
                break;
            case "d": agendaSettingDto.setA1D(instrumentAnswerService.getInstrumentAnswerById("a1dd"));
                break;
            default: agendaSettingDto.setA1D(null);
        }

        switch ((agendaSettingEntity.getA2A() != null) ? agendaSettingEntity.getA2A() : "") {
            case "a": agendaSettingDto.setA2A(instrumentAnswerService.getInstrumentAnswerById("a2aa"));
                break;
            case "b": agendaSettingDto.setA2A(instrumentAnswerService.getInstrumentAnswerById("a2ab"));
                break;
            case "c": agendaSettingDto.setA2A(instrumentAnswerService.getInstrumentAnswerById("a2ac"));
                break;
            case "d": agendaSettingDto.setA2A(instrumentAnswerService.getInstrumentAnswerById("a2ad"));
                break;
            default: agendaSettingDto.setA2A(null);
        }

        switch ((agendaSettingEntity.getA2B() != null) ? agendaSettingEntity.getA2B() : "") {
            case "a": agendaSettingDto.setA2B(instrumentAnswerService.getInstrumentAnswerById("a2ba"));
                break;
            case "b": agendaSettingDto.setA2B(instrumentAnswerService.getInstrumentAnswerById("a2bb"));
                break;
            case "c": agendaSettingDto.setA2B(instrumentAnswerService.getInstrumentAnswerById("a2bc"));
                break;
            case "d": agendaSettingDto.setA2B(instrumentAnswerService.getInstrumentAnswerById("a2bd"));
                break;
            default: agendaSettingDto.setA2B(null);
        }

        String a2c = agendaSettingEntity.getA2C();
        if (a2c == null) {
            agendaSettingDto.setA2C("");
        } else {
            List<String> a2cList = Stream.of(a2c.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());

            List<String> a2cTextList = new ArrayList<>();

            for (String jawabanA2C : a2cList) {
                switch (jawabanA2C) {
                    case "a": a2cTextList.add(instrumentAnswerService.getInstrumentAnswerById("a2ca"));
                        break;
                    case "b": a2cTextList.add(instrumentAnswerService.getInstrumentAnswerById("a2cb"));
                        break;
                    case "c": a2cTextList.add(instrumentAnswerService.getInstrumentAnswerById("a2cc"));
                        break;
                    case "d": a2cTextList.add(instrumentAnswerService.getInstrumentAnswerById("a2cd"));
                        break;
                }
            }
            agendaSettingDto.setA2C(a2cTextList);
        }

        return agendaSettingDto;
    }

    private FormulasiKebijakanDto convertFormulasiKebijakanValueToText(PolicyEntity policyEntity) {
        FormulasiKebijakanEntity formulasiKebijakanEntity = policyEntity.getFormulasiKebijakanEntity();
        FormulasiKebijakanFileEntity formulasiKebijakanFileEntity = policyEntity.getFormulasiKebijakanFileEntity();

        FormulasiKebijakanDto formulasiKebijakanDto = formulasiKebijakanService.mapFormulasiKebijakanDto(
                policyEntity,
                formulasiKebijakanEntity,
                formulasiKebijakanFileEntity);

        switch ((formulasiKebijakanEntity.getB1A() != null) ? formulasiKebijakanEntity.getB1A() : "") {
            case "a": formulasiKebijakanDto.setB1A(instrumentAnswerService.getInstrumentAnswerById("b1aa"));
                break;
            case "b": formulasiKebijakanDto.setB1A(instrumentAnswerService.getInstrumentAnswerById("b1ab"));
                break;
            case "c": formulasiKebijakanDto.setB1A(instrumentAnswerService.getInstrumentAnswerById("b1ac"));
                break;
            case "d": formulasiKebijakanDto.setB1A(instrumentAnswerService.getInstrumentAnswerById("b1ad"));
                break;
            default: formulasiKebijakanDto.setB1A(null);
        }

        switch ((formulasiKebijakanEntity.getB1B() != null) ? formulasiKebijakanEntity.getB1B() : "") {
            case "a": formulasiKebijakanDto.setB1B(instrumentAnswerService.getInstrumentAnswerById("b1ba"));
                break;
            case "b": formulasiKebijakanDto.setB1B(instrumentAnswerService.getInstrumentAnswerById("b1bb"));
                break;
            case "c": formulasiKebijakanDto.setB1B(instrumentAnswerService.getInstrumentAnswerById("b1bc"));
                break;
            case "d": formulasiKebijakanDto.setB1B(instrumentAnswerService.getInstrumentAnswerById("b1bd"));
                break;
            default: formulasiKebijakanDto.setB1B(null);
        }

        switch ((formulasiKebijakanEntity.getB2A() != null) ? formulasiKebijakanEntity.getB2A() : "") {
            case "a": formulasiKebijakanDto.setB2A(instrumentAnswerService.getInstrumentAnswerById("b2aa"));
                break;
            case "b": formulasiKebijakanDto.setB2A(instrumentAnswerService.getInstrumentAnswerById("b2ab"));
                break;
            case "c": formulasiKebijakanDto.setB2A(instrumentAnswerService.getInstrumentAnswerById("b2ac"));
                break;
            case "d": formulasiKebijakanDto.setB2A(instrumentAnswerService.getInstrumentAnswerById("b2ad"));
                break;
            default: formulasiKebijakanDto.setB2A(null);
        }

        switch ((formulasiKebijakanEntity.getB2B() != null) ? formulasiKebijakanEntity.getB2B() : "") {
            case "a": formulasiKebijakanDto.setB2B(instrumentAnswerService.getInstrumentAnswerById("b2ba"));
                break;
            case "b": formulasiKebijakanDto.setB2B(instrumentAnswerService.getInstrumentAnswerById("b2bb"));
                break;
            case "c": formulasiKebijakanDto.setB2B(instrumentAnswerService.getInstrumentAnswerById("b2bc"));
                break;
            case "d": formulasiKebijakanDto.setB2B(instrumentAnswerService.getInstrumentAnswerById("b2bd"));
                break;
            default: formulasiKebijakanDto.setB2B(null);
        }

        switch ((formulasiKebijakanEntity.getB3A() != null) ? formulasiKebijakanEntity.getB3A() : "") {
            case "a": formulasiKebijakanDto.setB3A(instrumentAnswerService.getInstrumentAnswerById("b3aa"));
                break;
            case "b": formulasiKebijakanDto.setB3A(instrumentAnswerService.getInstrumentAnswerById("b3ab"));
                break;
            case "c": formulasiKebijakanDto.setB3A(instrumentAnswerService.getInstrumentAnswerById("b3ac"));
                break;
            case "d": formulasiKebijakanDto.setB3A(instrumentAnswerService.getInstrumentAnswerById("b3ad"));
                break;
            default: formulasiKebijakanDto.setB3A(null);
        }

        switch ((formulasiKebijakanEntity.getB3B() != null) ? formulasiKebijakanEntity.getB3B() : "") {
            case "a": formulasiKebijakanDto.setB3B(instrumentAnswerService.getInstrumentAnswerById("b3ba"));
                break;
            case "b": formulasiKebijakanDto.setB3B(instrumentAnswerService.getInstrumentAnswerById("b3bb"));
                break;
            case "c": formulasiKebijakanDto.setB3B(instrumentAnswerService.getInstrumentAnswerById("b3bc"));
                break;
            case "d": formulasiKebijakanDto.setB3B(instrumentAnswerService.getInstrumentAnswerById("b3bd"));
                break;
            default: formulasiKebijakanDto.setB3B(null);
        }

        switch ((formulasiKebijakanEntity.getB3C() != null) ? formulasiKebijakanEntity.getB3C() : "") {
            case "a": formulasiKebijakanDto.setB3C(instrumentAnswerService.getInstrumentAnswerById("b3ca"));
                break;
            case "b": formulasiKebijakanDto.setB3C(instrumentAnswerService.getInstrumentAnswerById("b3cb"));
                break;
            case "c": formulasiKebijakanDto.setB3C(instrumentAnswerService.getInstrumentAnswerById("b3cc"));
                break;
            case "d": formulasiKebijakanDto.setB3C(instrumentAnswerService.getInstrumentAnswerById("b3cd"));
                break;
            default: formulasiKebijakanDto.setB3C(null);
        }

        switch ((formulasiKebijakanEntity.getB4A() != null) ? formulasiKebijakanEntity.getB4A() : "") {
            case "a": formulasiKebijakanDto.setB4A(instrumentAnswerService.getInstrumentAnswerById("b4aa"));
                break;
            case "b": formulasiKebijakanDto.setB4A(instrumentAnswerService.getInstrumentAnswerById("b4ab"));
                break;
            case "c": formulasiKebijakanDto.setB4A(instrumentAnswerService.getInstrumentAnswerById("b4ac"));
                break;
            case "d": formulasiKebijakanDto.setB4A(instrumentAnswerService.getInstrumentAnswerById("b4ad"));
                break;
            default: formulasiKebijakanDto.setB4A(null);
        }

        switch ((formulasiKebijakanEntity.getB4B() != null) ? formulasiKebijakanEntity.getB4B() : "") {
            case "a": formulasiKebijakanDto.setB4B(instrumentAnswerService.getInstrumentAnswerById("b4ba"));
                break;
            case "b": formulasiKebijakanDto.setB4B(instrumentAnswerService.getInstrumentAnswerById("b4bb"));
                break;
            case "c": formulasiKebijakanDto.setB4B(instrumentAnswerService.getInstrumentAnswerById("b4bc"));
                break;
            case "d": formulasiKebijakanDto.setB4B(instrumentAnswerService.getInstrumentAnswerById("b4bd"));
                break;
            default: formulasiKebijakanDto.setB4B(null);
        }

        switch ((formulasiKebijakanEntity.getB4C() != null) ? formulasiKebijakanEntity.getB4C() : "") {
            case "a": formulasiKebijakanDto.setB4C(instrumentAnswerService.getInstrumentAnswerById("b4ca"));
                break;
            case "b": formulasiKebijakanDto.setB4C(instrumentAnswerService.getInstrumentAnswerById("b4cb"));
                break;
            case "c": formulasiKebijakanDto.setB4C(instrumentAnswerService.getInstrumentAnswerById("b4cc"));
                break;
            case "d": formulasiKebijakanDto.setB4C(instrumentAnswerService.getInstrumentAnswerById("b4cd"));
                break;
            default: formulasiKebijakanDto.setB4C(null);
        }

        switch ((formulasiKebijakanEntity.getB5A() != null) ? formulasiKebijakanEntity.getB5A() : "") {
            case "a": formulasiKebijakanDto.setB5A(instrumentAnswerService.getInstrumentAnswerById("b5aa"));
                break;
            case "b": formulasiKebijakanDto.setB5A(instrumentAnswerService.getInstrumentAnswerById("b5ab"));
                break;
            case "c": formulasiKebijakanDto.setB5A(instrumentAnswerService.getInstrumentAnswerById("b5ac"));
                break;
            case "d": formulasiKebijakanDto.setB5A(instrumentAnswerService.getInstrumentAnswerById("b5ad"));
                break;
            default: formulasiKebijakanDto.setB5A(null);
        }

        switch ((formulasiKebijakanEntity.getB5B() != null) ? formulasiKebijakanEntity.getB5B() : "") {
            case "a": formulasiKebijakanDto.setB5B(instrumentAnswerService.getInstrumentAnswerById("b5ba"));
                break;
            case "b": formulasiKebijakanDto.setB5B(instrumentAnswerService.getInstrumentAnswerById("b5bb"));
                break;
            case "c": formulasiKebijakanDto.setB5B(instrumentAnswerService.getInstrumentAnswerById("b5bc"));
                break;
            case "d": formulasiKebijakanDto.setB5B(instrumentAnswerService.getInstrumentAnswerById("b5bd"));
                break;
            default: formulasiKebijakanDto.setB5B(null);
        }

        switch ((formulasiKebijakanEntity.getB5C() != null) ? formulasiKebijakanEntity.getB5C() : "") {
            case "a": formulasiKebijakanDto.setB5C(instrumentAnswerService.getInstrumentAnswerById("b5ca"));
                break;
            case "b": formulasiKebijakanDto.setB5C(instrumentAnswerService.getInstrumentAnswerById("b5cb"));
                break;
            case "c": formulasiKebijakanDto.setB5C(instrumentAnswerService.getInstrumentAnswerById("b5cc"));
                break;
            case "d": formulasiKebijakanDto.setB5C(instrumentAnswerService.getInstrumentAnswerById("b5cd"));
                break;
            default: formulasiKebijakanDto.setB5C(null);
        }

        return formulasiKebijakanDto;
    }

    private ImplementasiKebijakanDto convertImplementasiKebijakanValueToText(PolicyEntity policyEntity) {
        ImplementasiKebijakanEntity implementasiKebijakanEntity = policyEntity.getImplementasiKebijakanEntity();
        ImplementasiKebijakanFileEntity implementasiKebijakanFileEntity = policyEntity.getImplementasiKebijakanFileEntity();

        ImplementasiKebijakanDto implementasiKebijakanDto = implementasiKebijakanService.mapImplementasiKebijakanDto(
                policyEntity,
                implementasiKebijakanEntity,
                implementasiKebijakanFileEntity);

        switch ((implementasiKebijakanEntity.getC1A() != null) ? implementasiKebijakanEntity.getC1A() : "") {
            case "a": implementasiKebijakanDto.setC1A(instrumentAnswerService.getInstrumentAnswerById("c1aa"));
                break;
            case "b": implementasiKebijakanDto.setC1A(instrumentAnswerService.getInstrumentAnswerById("c1ab"));
                break;
            case "c": implementasiKebijakanDto.setC1A(instrumentAnswerService.getInstrumentAnswerById("c1ac"));
                break;
            case "d": implementasiKebijakanDto.setC1A(instrumentAnswerService.getInstrumentAnswerById("c1ad"));
                break;
            default: implementasiKebijakanDto.setC1A(null);
        }

        switch ((implementasiKebijakanEntity.getC1B() != null) ? implementasiKebijakanEntity.getC1B() : "") {
            case "a": implementasiKebijakanDto.setC1B(instrumentAnswerService.getInstrumentAnswerById("c1ba"));
                break;
            case "b": implementasiKebijakanDto.setC1B(instrumentAnswerService.getInstrumentAnswerById("c1bb"));
                break;
            case "c": implementasiKebijakanDto.setC1B(instrumentAnswerService.getInstrumentAnswerById("c1bc"));
                break;
            case "d": implementasiKebijakanDto.setC1B(instrumentAnswerService.getInstrumentAnswerById("c1bd"));
                break;
            default: implementasiKebijakanDto.setC1B(null);
        }

        switch ((implementasiKebijakanEntity.getC1C() != null) ? implementasiKebijakanEntity.getC1C() : "") {
            case "a": implementasiKebijakanDto.setC1C(instrumentAnswerService.getInstrumentAnswerById("c1ca"));
                break;
            case "b": implementasiKebijakanDto.setC1C(instrumentAnswerService.getInstrumentAnswerById("c1cb"));
                break;
            case "c": implementasiKebijakanDto.setC1C(instrumentAnswerService.getInstrumentAnswerById("c1cc"));
                break;
            case "d": implementasiKebijakanDto.setC1C(instrumentAnswerService.getInstrumentAnswerById("c1cd"));
                break;
            default: implementasiKebijakanDto.setC1C(null);
        }

        switch ((implementasiKebijakanEntity.getC1D() != null) ? implementasiKebijakanEntity.getC1D() : "") {
            case "a": implementasiKebijakanDto.setC1D(instrumentAnswerService.getInstrumentAnswerById("c1da"));
                break;
            case "b": implementasiKebijakanDto.setC1D(instrumentAnswerService.getInstrumentAnswerById("c1db"));
                break;
            case "c": implementasiKebijakanDto.setC1D(instrumentAnswerService.getInstrumentAnswerById("c1dc"));
                break;
            case "d": implementasiKebijakanDto.setC1D(instrumentAnswerService.getInstrumentAnswerById("c1dd"));
                break;
            default: implementasiKebijakanDto.setC1D(null);
        }

        switch ((implementasiKebijakanEntity.getC2A() != null) ? implementasiKebijakanEntity.getC2A() : "") {
            case "a": implementasiKebijakanDto.setC2A(instrumentAnswerService.getInstrumentAnswerById("c2aa"));
                break;
            case "b": implementasiKebijakanDto.setC2A(instrumentAnswerService.getInstrumentAnswerById("c2ab"));
                break;
            case "c": implementasiKebijakanDto.setC2A(instrumentAnswerService.getInstrumentAnswerById("c2ac"));
                break;
            case "d": implementasiKebijakanDto.setC2A(instrumentAnswerService.getInstrumentAnswerById("c2ad"));
                break;
            default: implementasiKebijakanDto.setC2A(null);
        }

        switch ((implementasiKebijakanEntity.getC2B() != null) ? implementasiKebijakanEntity.getC2B() : "") {
            case "a": implementasiKebijakanDto.setC2B(instrumentAnswerService.getInstrumentAnswerById("c2ba"));
                break;
            case "b": implementasiKebijakanDto.setC2B(instrumentAnswerService.getInstrumentAnswerById("c2bb"));
                break;
            case "c": implementasiKebijakanDto.setC2B(instrumentAnswerService.getInstrumentAnswerById("c2bc"));
                break;
            case "d": implementasiKebijakanDto.setC2B(instrumentAnswerService.getInstrumentAnswerById("c2bd"));
                break;
            default: implementasiKebijakanDto.setC2B(null);
        }

        switch ((implementasiKebijakanEntity.getC2C() != null) ? implementasiKebijakanEntity.getC2C() : "") {
            case "a": implementasiKebijakanDto.setC2C(instrumentAnswerService.getInstrumentAnswerById("c2ca"));
                break;
            case "b": implementasiKebijakanDto.setC2C(instrumentAnswerService.getInstrumentAnswerById("c2cb"));
                break;
            case "c": implementasiKebijakanDto.setC2C(instrumentAnswerService.getInstrumentAnswerById("c2cc"));
                break;
            case "d": implementasiKebijakanDto.setC2C(instrumentAnswerService.getInstrumentAnswerById("c2cd"));
                break;
            default: implementasiKebijakanDto.setC2C(null);
        }

        switch ((implementasiKebijakanEntity.getC3A() != null) ? implementasiKebijakanEntity.getC3A() : "") {
            case "a": implementasiKebijakanDto.setC3A(instrumentAnswerService.getInstrumentAnswerById("c3aa"));
                break;
            case "b": implementasiKebijakanDto.setC3A(instrumentAnswerService.getInstrumentAnswerById("c3ab"));
                break;
            case "c": implementasiKebijakanDto.setC3A(instrumentAnswerService.getInstrumentAnswerById("c3ac"));
                break;
            case "d": implementasiKebijakanDto.setC3A(instrumentAnswerService.getInstrumentAnswerById("c3ad"));
                break;
            default: implementasiKebijakanDto.setC3A(null);
        }

        switch ((implementasiKebijakanEntity.getC3B() != null) ? implementasiKebijakanEntity.getC3B() : "") {
            case "a": implementasiKebijakanDto.setC3B(instrumentAnswerService.getInstrumentAnswerById("c3ba"));
                break;
            case "b": implementasiKebijakanDto.setC3B(instrumentAnswerService.getInstrumentAnswerById("c3bb"));
                break;
            case "c": implementasiKebijakanDto.setC3B(instrumentAnswerService.getInstrumentAnswerById("c3bc"));
                break;
            case "d": implementasiKebijakanDto.setC3B(instrumentAnswerService.getInstrumentAnswerById("c3bd"));
                break;
            default: implementasiKebijakanDto.setC3B(null);
        }

        switch ((implementasiKebijakanEntity.getC3C() != null) ? implementasiKebijakanEntity.getC3C() : "") {
            case "a": implementasiKebijakanDto.setC3C(instrumentAnswerService.getInstrumentAnswerById("c3ca"));
                break;
            case "b": implementasiKebijakanDto.setC3C(instrumentAnswerService.getInstrumentAnswerById("c3cb"));
                break;
            case "c": implementasiKebijakanDto.setC3C(instrumentAnswerService.getInstrumentAnswerById("c3cc"));
                break;
            case "d": implementasiKebijakanDto.setC3C(instrumentAnswerService.getInstrumentAnswerById("c3cd"));
                break;
            default: implementasiKebijakanDto.setC3C(null);
        }

        return implementasiKebijakanDto;
    }

    private EvaluasiKebijakanDto convertEvaluasiKebijakanValueToText(PolicyEntity policyEntity) {
        EvaluasiKebijakanEntity evaluasiKebijakanEntity = policyEntity.getEvaluasiKebijakanEntity();
        EvaluasiKebijakanFileEntity evaluasiKebijakanFileEntity = policyEntity.getEvaluasiKebijakanFileEntity();

        EvaluasiKebijakanDto evaluasiKebijakanDto = evaluasiKebijakanService.mapEvaluasiKebijakanDto(
                policyEntity,
                evaluasiKebijakanEntity,
                evaluasiKebijakanFileEntity);

        switch ((evaluasiKebijakanEntity.getD1A() != null) ? evaluasiKebijakanEntity.getD1A() : "") {
            case "a": evaluasiKebijakanDto.setD1A(instrumentAnswerService.getInstrumentAnswerById("d1aa"));
                break;
            case "b": evaluasiKebijakanDto.setD1A(instrumentAnswerService.getInstrumentAnswerById("d1ab"));
                break;
            case "c": evaluasiKebijakanDto.setD1A(instrumentAnswerService.getInstrumentAnswerById("d1ac"));
                break;
            case "d": evaluasiKebijakanDto.setD1A(instrumentAnswerService.getInstrumentAnswerById("d1ad"));
                break;
            default: evaluasiKebijakanDto.setD1A(null);
        }

        switch ((evaluasiKebijakanEntity.getD1B() != null) ? evaluasiKebijakanEntity.getD1B() : "") {
            case "a": evaluasiKebijakanDto.setD1B(instrumentAnswerService.getInstrumentAnswerById("d1ba"));
                break;
            case "b": evaluasiKebijakanDto.setD1B(instrumentAnswerService.getInstrumentAnswerById("d1bb"));
                break;
            case "c": evaluasiKebijakanDto.setD1B(instrumentAnswerService.getInstrumentAnswerById("d1bc"));
                break;
            case "d": evaluasiKebijakanDto.setD1B(instrumentAnswerService.getInstrumentAnswerById("d1bd"));
                break;
            default: evaluasiKebijakanDto.setD1B(null);
        }

        switch ((evaluasiKebijakanEntity.getD2A() != null) ? evaluasiKebijakanEntity.getD2A() : "") {
            case "a": evaluasiKebijakanDto.setD2A(instrumentAnswerService.getInstrumentAnswerById("d2aa"));
                break;
            case "b": evaluasiKebijakanDto.setD2A(instrumentAnswerService.getInstrumentAnswerById("d2ab"));
                break;
            case "c": evaluasiKebijakanDto.setD2A(instrumentAnswerService.getInstrumentAnswerById("d2ac"));
                break;
            case "d": evaluasiKebijakanDto.setD2A(instrumentAnswerService.getInstrumentAnswerById("d2ad"));
                break;
            default: evaluasiKebijakanDto.setD2A(null);
        }

        switch ((evaluasiKebijakanEntity.getD2B() != null) ? evaluasiKebijakanEntity.getD2B() : "") {
            case "a": evaluasiKebijakanDto.setD2B(instrumentAnswerService.getInstrumentAnswerById("d2ba"));
                break;
            case "b": evaluasiKebijakanDto.setD2B(instrumentAnswerService.getInstrumentAnswerById("d2bb"));
                break;
            case "c": evaluasiKebijakanDto.setD2B(instrumentAnswerService.getInstrumentAnswerById("d2bc"));
                break;
            case "d": evaluasiKebijakanDto.setD2B(instrumentAnswerService.getInstrumentAnswerById("d2bd"));
                break;
            default: evaluasiKebijakanDto.setD2B(null);
        }

        switch ((evaluasiKebijakanEntity.getD3A() != null) ? evaluasiKebijakanEntity.getD3A() : "") {
            case "a": evaluasiKebijakanDto.setD3A(instrumentAnswerService.getInstrumentAnswerById("d3aa"));
                break;
            case "b": evaluasiKebijakanDto.setD3A(instrumentAnswerService.getInstrumentAnswerById("d3ab"));
                break;
            case "c": evaluasiKebijakanDto.setD3A(instrumentAnswerService.getInstrumentAnswerById("d3ac"));
                break;
            case "d": evaluasiKebijakanDto.setD3A(instrumentAnswerService.getInstrumentAnswerById("d3ad"));
                break;
            default: evaluasiKebijakanDto.setD3A(null);
        }

        switch ((evaluasiKebijakanEntity.getD3B() != null) ? evaluasiKebijakanEntity.getD3B() : "") {
            case "a": evaluasiKebijakanDto.setD3B(instrumentAnswerService.getInstrumentAnswerById("d3ba"));
                break;
            case "b": evaluasiKebijakanDto.setD3B(instrumentAnswerService.getInstrumentAnswerById("d3bb"));
                break;
            case "c": evaluasiKebijakanDto.setD3B(instrumentAnswerService.getInstrumentAnswerById("d3bc"));
                break;
            case "d": evaluasiKebijakanDto.setD3B(instrumentAnswerService.getInstrumentAnswerById("d3bd"));
                break;
            default: evaluasiKebijakanDto.setD3B(null);
        }

        switch ((evaluasiKebijakanEntity.getD3C() != null) ? evaluasiKebijakanEntity.getD3C() : "") {
            case "a": evaluasiKebijakanDto.setD3C(instrumentAnswerService.getInstrumentAnswerById("d3ca"));
                break;
            case "b": evaluasiKebijakanDto.setD3C(instrumentAnswerService.getInstrumentAnswerById("d3cb"));
                break;
            case "c": evaluasiKebijakanDto.setD3C(instrumentAnswerService.getInstrumentAnswerById("d3cc"));
                break;
            case "d": evaluasiKebijakanDto.setD3C(instrumentAnswerService.getInstrumentAnswerById("d3cd"));
                break;
            default: evaluasiKebijakanDto.setD3C(null);
        }

        switch ((evaluasiKebijakanEntity.getD3D() != null) ? evaluasiKebijakanEntity.getD3D() : "") {
            case "a": evaluasiKebijakanDto.setD3D(instrumentAnswerService.getInstrumentAnswerById("d3da"));
                break;
            case "b": evaluasiKebijakanDto.setD3D(instrumentAnswerService.getInstrumentAnswerById("d3db"));
                break;
            case "c": evaluasiKebijakanDto.setD3D(instrumentAnswerService.getInstrumentAnswerById("d3dc"));
                break;
            case "d": evaluasiKebijakanDto.setD3D(instrumentAnswerService.getInstrumentAnswerById("d3dd"));
                break;
            default: evaluasiKebijakanDto.setD3D(null);
        }

        switch ((evaluasiKebijakanEntity.getD3E() != null) ? evaluasiKebijakanEntity.getD3E() : "") {
            case "a": evaluasiKebijakanDto.setD3E(instrumentAnswerService.getInstrumentAnswerById("d3ea"));
                break;
            case "b": evaluasiKebijakanDto.setD3E(instrumentAnswerService.getInstrumentAnswerById("d3eb"));
                break;
            case "c": evaluasiKebijakanDto.setD3E(instrumentAnswerService.getInstrumentAnswerById("d3ec"));
                break;
            case "d": evaluasiKebijakanDto.setD3E(instrumentAnswerService.getInstrumentAnswerById("d3ed"));
                break;
            default: evaluasiKebijakanDto.setD3E(null);
        }

        return evaluasiKebijakanDto;
    }
}
