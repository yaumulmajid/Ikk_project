package id.go.lan.ikk.module.policy.service.implementation;

import id.go.lan.ikk.module.policy.entity.PolicyEntity;
import id.go.lan.ikk.module.policy.entity.PolicySampleEntity;
import id.go.lan.ikk.module.policy.repository.PolicySampleRepository;
import id.go.lan.ikk.module.policy.service.PolicyMasterService;
import id.go.lan.ikk.module.policy.service.PolicySampleService;
import id.go.lan.ikk.module.user.entity.AgencyEntity;
import id.go.lan.ikk.module.user.entity.UserEntity;
import id.go.lan.ikk.module.user.service.AgencyMasterService;
import id.go.lan.ikk.module.user.service.UserMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PolicySampleServiceImpl implements PolicySampleService {

    @Autowired
    private UserMasterService userMasterService;

    @Autowired
    private AgencyMasterService agencyMasterService;

    @Autowired
    private PolicyMasterService policyMasterService;

    @Autowired
    private PolicySampleRepository policySampleRepository;

    @Override
    public List<PolicyEntity> getAllSampledPoliciesByAgencyId(Long agencyId) {
        List<UserEntity> adminEntityList = userMasterService.findByRole("role_admin_instansi");
        AgencyEntity agencyEntity = agencyMasterService.getAgencyById(agencyId);

        Long adminId = 0L;
        for (UserEntity userEntity : adminEntityList) {
            if (userEntity.getAgency() == agencyEntity) {
                adminId = userEntity.getId();
            }
        }

        List<PolicyEntity> policyEntityList = new ArrayList<>();
        List<PolicySampleEntity> policySampleEntityList = policySampleRepository.findByIdUserAdmin(adminId);
        if (!policySampleEntityList.isEmpty()) {
            for (PolicySampleEntity policySampleEntity : policySampleEntityList) {
                PolicyEntity policyEntity = policyMasterService.getPolicyByPolicyId(policySampleEntity.getPolicyId());
                policyEntityList.add(policyEntity);
            }
        }

        return policyEntityList;
    }
}
