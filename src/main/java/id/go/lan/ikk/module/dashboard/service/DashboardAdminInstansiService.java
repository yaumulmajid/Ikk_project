package id.go.lan.ikk.module.dashboard.service;

import id.go.lan.ikk.module.dashboard.model.DashboardAdminInstansiCardDto;
import id.go.lan.ikk.module.dashboard.model.DashboardAdminInstansiCardPolicyDto;
import id.go.lan.ikk.module.dashboard.model.DashboardPolicyProgressDto;
import id.go.lan.ikk.module.policy.model.PolicyDto;

import java.util.List;

public interface DashboardAdminInstansiService {
    DashboardAdminInstansiCardDto getDashboardCard();
    List<PolicyDto> getAdminInstansiDashboardProgress(Long agencyId);
    DashboardPolicyProgressDto getAdminInstansiPolicyProgress(Long id);
    DashboardAdminInstansiCardPolicyDto getDashboardPolicyDetail(Long policyId);
    List<PolicyDto> getAdminInstansiDashboardFinish(Long agencyId);
}
