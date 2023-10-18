package id.go.lan.ikk.module.policy.service.implementation;

import id.go.lan.ikk.entity.PolicyStatusEnum;
import id.go.lan.ikk.exception.ResourceNotFoundException;
import id.go.lan.ikk.module.policy.entity.PolicyEntity;
import id.go.lan.ikk.module.policy.entity.PolicyProcessEntity;
import id.go.lan.ikk.module.policy.entity.PolicyStatusEntity;
import id.go.lan.ikk.module.policy.repository.PolicyDetailsRepository;
import id.go.lan.ikk.module.policy.repository.PolicyProcessRepository;
import id.go.lan.ikk.module.policy.repository.PolicyRepository;
import id.go.lan.ikk.module.policy.repository.PolicyStatusRepository;
import id.go.lan.ikk.module.policy.service.PolicyMasterService;
import id.go.lan.ikk.module.user.entity.AgencyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PolicyMasterServiceImpl implements PolicyMasterService {

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private PolicyDetailsRepository policyDetailsRepository;

    @Autowired
    private PolicyProcessRepository policyProcessRepository;

    @Autowired
    private PolicyStatusRepository policyStatusRepository;

    @Override
    public void savePolicy(PolicyEntity policyEntity) {
        policyRepository.save(policyEntity);
    }

    @Override
    public PolicyEntity getPolicyByPolicyId(Long id) {
        return policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found"));
    }

    @Override
    public List<PolicyEntity> getAllPoliciesByAgency(AgencyEntity agency) {
        return policyRepository.findByAgency(agency);
    }

    @Override
    public List<PolicyEntity> getAllPoliciesByEnumeratorId(Long id) {
        return policyRepository.findByEnumeratorId(id);
    }

    @Override
    public void savePolicyProcess(PolicyProcessEntity policyProcess) {
        policyProcessRepository.save(policyProcess);
    }

    @Override
    public void savePolicyStatus(PolicyStatusEntity policyStatus) {
        policyStatusRepository.save(policyStatus);
    }

    @Override
    public Integer countPoliciesByAgency(AgencyEntity agency) {
        return policyRepository.countByAgency(agency);
    }

    @Override
    public Date getLatestSentByAdminAtPolicy(AgencyEntity agency) {
        List<PolicyEntity> policyEntityList = policyRepository.findByAgency(agency);

        if (policyEntityList.isEmpty()) {
            return null;
        }

        Date latestSentByAdminDate = new Date(1999, Calendar.DECEMBER, 31);
        for (PolicyEntity policyEntity : policyEntityList) {

            if (policyEntity.getSentByAdminAt() == null) {
                return null;
            }

            if (policyEntity.getSentByAdminAt().before(latestSentByAdminDate)) {
                latestSentByAdminDate = policyEntity.getSentByAdminAt();
            }
        }

        return latestSentByAdminDate;
    }

    @Override
    public Boolean checkIsBeingVerifiedStatus(AgencyEntity agency) {
        List<PolicyEntity> policyEntityList = policyRepository.findByAgency(agency);

        if (policyEntityList.isEmpty()) {
            return false;
        }

        List<PolicyEntity> policyEntitySedangVerifikasiList = new ArrayList<>();
        for (PolicyEntity policyEntity : policyEntityList) {
            if (policyEntity.getPolicyStatusEntity().getName().name().equals(PolicyStatusEnum.SEDANG_VERIFIKASI.name()) ||
                    policyEntity.getPolicyStatusEntity().getName().name().equals(PolicyStatusEnum.SELESAI_VERIFIKASI.name()) ||
                    policyEntity.getPolicyStatusEntity().getName().name().equals(PolicyStatusEnum.MENUNGGU_VALIDASI_AI.name()) ||
                    policyEntity.getPolicyStatusEntity().getName().name().equals(PolicyStatusEnum.MENUNGGU_VALIDASI_KI.name()) ||
                    policyEntity.getPolicyStatusEntity().getName().name().equals(PolicyStatusEnum.SEDANG_VALIDASI_KI.name()) ||
                    policyEntity.getPolicyStatusEntity().getName().name().equals(PolicyStatusEnum.SELESAI_VALIDASI_KI.name()) ||
                    policyEntity.getPolicyStatusEntity().getName().name().equals(PolicyStatusEnum.MENUNGGU_VALIDASI_KU.name()) ||
                    policyEntity.getPolicyStatusEntity().getName().name().equals(PolicyStatusEnum.SEDANG_VALIDASI_KU.name()) ||
                    policyEntity.getPolicyStatusEntity().getName().name().equals(PolicyStatusEnum.SELESAI_VALIDASI_KU.name())) {
                policyEntitySedangVerifikasiList.add(policyEntity);
            }
        }

        return policyEntitySedangVerifikasiList.size() > 0;
    }
}
