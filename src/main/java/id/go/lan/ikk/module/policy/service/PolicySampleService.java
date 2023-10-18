package id.go.lan.ikk.module.policy.service;

import id.go.lan.ikk.module.policy.entity.PolicyEntity;

import java.util.List;

public interface PolicySampleService {
    List<PolicyEntity> getAllSampledPoliciesByAgencyId(Long agencyId);
}
