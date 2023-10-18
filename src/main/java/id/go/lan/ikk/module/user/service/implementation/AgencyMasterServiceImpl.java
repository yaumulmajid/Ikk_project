package id.go.lan.ikk.module.user.service.implementation;

import id.go.lan.ikk.entity.AgencyCategoryEnum;
import id.go.lan.ikk.exception.ResourceNotFoundException;
import id.go.lan.ikk.module.user.entity.AgencyEntity;
import id.go.lan.ikk.module.user.repository.AgencyRepository;
import id.go.lan.ikk.module.user.service.AgencyMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgencyMasterServiceImpl implements AgencyMasterService {

    @Autowired
    private AgencyRepository agencyRepository;

    @Override
    public AgencyEntity getAgencyById(Long id) {
        return agencyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instansi tidak ditemukan"));
    }

    @Override
    public List<AgencyEntity> getAgencyByCategory(String category) {
        AgencyCategoryEnum agencyCategoryEnum = AgencyCategoryEnum.valueOf(category);
        return agencyRepository.findByCategory(agencyCategoryEnum);
    }
}
