package id.go.lan.ikk.module.dashboard.service;

import id.go.lan.ikk.module.dashboard.model.DashboardKoordinatorUtamaCardPolicyDto;
import id.go.lan.ikk.module.dashboard.model.DashboardPolicyProgressDto;
import id.go.lan.ikk.module.policy.model.PolicyDto;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface DashboardAdminNasionalService {
    List<PolicyDto> getAllPolicyByAgency(Long agencyId);
    DashboardKoordinatorUtamaCardPolicyDto getAdminNasionalPolicyDetail(Long policyId);
    DashboardPolicyProgressDto getPolicyProgressDetailByPolicyId(Long policyId);
    ByteArrayInputStream exportToExcel() throws Exception;
}
