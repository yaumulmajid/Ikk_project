package id.go.lan.ikk.module.dashboard.model;

import id.go.lan.ikk.module.policy.model.PolicyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DashboardAgencyPoliciesVerificationDto {
    Boolean isReadyToSend;
    List<PolicyDto> policyList;
}
