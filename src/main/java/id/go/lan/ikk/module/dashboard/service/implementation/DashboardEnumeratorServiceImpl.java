package id.go.lan.ikk.module.dashboard.service.implementation;

import id.go.lan.ikk.entity.PolicyProcessEnum;
import id.go.lan.ikk.entity.PolicyStatusEnum;
import id.go.lan.ikk.module.dashboard.model.DashboardEnumeratorCardDto;
import id.go.lan.ikk.module.dashboard.service.DashboardEnumeratorService;
import id.go.lan.ikk.module.policy.entity.PolicyEntity;
import id.go.lan.ikk.module.policy.entity.PolicyProcessEntity;
import id.go.lan.ikk.module.policy.entity.PolicyStatusEntity;
import id.go.lan.ikk.module.policy.model.PolicyDto;
import id.go.lan.ikk.module.policy.service.PolicyMasterService;
import id.go.lan.ikk.module.user.model.GetUserDetailDto;
import id.go.lan.ikk.module.user.service.UserMasterService;
import id.go.lan.ikk.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DashboardEnumeratorServiceImpl implements DashboardEnumeratorService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMasterService userMasterService;

    @Autowired
    private PolicyMasterService policyMasterService;

    @Override
    public DashboardEnumeratorCardDto getDashboardCard() {
        List<PolicyEntity> policyEntityList = policyMasterService.getAllPoliciesByEnumeratorId(userService.getSignedInUserId());

        int totalPolicyMasuk = 0;
        int totalPolicyProses = 0;
        int totalPolicySelesai = 0;

        for (PolicyEntity policyEntity : policyEntityList) {
            if (policyEntity.getPolicyProcessEntity().getName().name().equals(PolicyProcessEnum.PROSES.name())) {
                totalPolicyProses += 1;
            }

            if (policyEntity.getPolicyProcessEntity().getName().name().equals(PolicyProcessEnum.SELESAI.name())) {
                totalPolicySelesai += 1;
            }

            totalPolicyMasuk += 1;
        }

        DashboardEnumeratorCardDto dashboardEnumeratorCardDto = new DashboardEnumeratorCardDto();
        dashboardEnumeratorCardDto.setKebijakanMasuk(totalPolicyMasuk);
        dashboardEnumeratorCardDto.setKebijakanDiproses(totalPolicyProses);
        dashboardEnumeratorCardDto.setKebijakanSelesai(totalPolicySelesai);

        return dashboardEnumeratorCardDto;
    }

    @Override
    public List<PolicyDto> getAllPolicies() {
        List<PolicyEntity> policyEntityList = policyMasterService.getAllPoliciesByEnumeratorId(userService.getSignedInUserId());

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
    public List<PolicyDto> getAllPoliciesProses() {
        List<PolicyEntity> policyEntityList = policyMasterService.getAllPoliciesByEnumeratorId(userService.getSignedInUserId());

        List<PolicyDto> policyDtoList = new ArrayList<>();
        for (PolicyEntity policyEntity : policyEntityList) {
            if (policyEntity.getPolicyProcessEntity().getName().name().equals(PolicyProcessEnum.PROSES.name())) {
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
            }
        }

        return policyDtoList;
    }

    @Override
    public PolicyDto getEnumeratorPolicyByPolicyId(Long policyId) {
        PolicyEntity policyEntity = policyMasterService.getPolicyByPolicyId(policyId);

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

        return policyDto;
    }

    @Override
    public void startPolicyProgress(Long policyId) {
        PolicyEntity policyEntity = policyMasterService.getPolicyByPolicyId(policyId);

        PolicyProcessEntity policyProcessEntity = policyEntity.getPolicyProcessEntity();
        policyProcessEntity.setName(PolicyProcessEnum.PROSES);
        policyProcessEntity.setModifiedBy(userService.getSignedInUserId());
        policyMasterService.savePolicyProcess(policyProcessEntity);

        policyEntity.setProcessedByEnumeratorAt(new Date());
        policyEntity.setProcessedByEnumeratorId(userService.getSignedInUserId());
        policyEntity.setModifiedBy(userService.getSignedInUserId());
        policyMasterService.savePolicy(policyEntity);
    }

    @Override
    public void sendToAdminForValidation(Long policyId) {
        PolicyEntity policyEntity = policyMasterService.getPolicyByPolicyId(policyId);

        PolicyStatusEntity policyStatusEntity = policyEntity.getPolicyStatusEntity();
        policyStatusEntity.setName(PolicyStatusEnum.MENUNGGU_VALIDASI_AI);
        policyStatusEntity.setModifiedBy(userService.getSignedInUserId());
        policyMasterService.savePolicyStatus(policyStatusEntity);
    }
}
