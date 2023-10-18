package id.go.lan.ikk.module.user.service;

import id.go.lan.ikk.module.user.entity.AgencyEntity;

import java.util.List;

public interface AgencyMasterService {
    AgencyEntity getAgencyById(Long id);
    List<AgencyEntity> getAgencyByCategory(String category);
}
