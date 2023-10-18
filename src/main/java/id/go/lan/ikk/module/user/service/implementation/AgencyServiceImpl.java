package id.go.lan.ikk.module.user.service.implementation;

import id.go.lan.ikk.entity.AgencyCategoryEnum;
import id.go.lan.ikk.exception.ResourceNotFoundException;
import id.go.lan.ikk.module.policy.entity.PolicyEntity;
import id.go.lan.ikk.module.policy.repository.PolicyRepository;
import id.go.lan.ikk.module.policy.service.PolicySampleService;
import id.go.lan.ikk.module.user.entity.AgencyEntity;
import id.go.lan.ikk.module.user.model.AgencyDto;
import id.go.lan.ikk.module.user.presenter.model.AddUpdateAgencyRequest;
import id.go.lan.ikk.module.user.repository.AgencyRepository;
import id.go.lan.ikk.module.user.service.AgencyService;
import id.go.lan.ikk.utility.ModelMapperUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AgencyServiceImpl implements AgencyService {

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private PolicySampleService policySampleService;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private ModelMapperUtility modelMapperUtility;

    @Override
    public List<AgencyDto> getAllAgencies() {
        List<AgencyEntity> agencyEntityList = agencyRepository.findAll();

        List<AgencyDto> agencyDtoList = new ArrayList<>();
        for (AgencyEntity agencyEntity : agencyEntityList) {
            AgencyDto agencyDto = new AgencyDto();
            agencyDto.setId(agencyEntity.getId());
            agencyDto.setName(agencyEntity.getName());
            agencyDto.setCategory(agencyEntity.getCategory().name());
            agencyDtoList.add(agencyDto);
        }

        return agencyDtoList;
    }

    @Override
    public AgencyDto getAgencyById(Long agencyId) {
        AgencyEntity agency = agencyRepository.findById(agencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Instansi tidak ditemukan"));

        AgencyDto agencyDto = new AgencyDto();
        agencyDto.setId(agency.getId());
        agencyDto.setName(agency.getName());
        agencyDto.setCategory(agency.getCategory().name());

        return agencyDto;
    }

    @Override
    public void addAgency(AddUpdateAgencyRequest request) {
        AgencyEntity agency = new AgencyEntity();
        agency.setName(request.getNamaInstansi());
        agency.setCategory(AgencyCategoryEnum.valueOf(request.getKategoriInstansi()));
        agencyRepository.save(agency);
    }

    @Override
    public void updateAgency(Long agencyId, AddUpdateAgencyRequest request) {
        AgencyEntity agency = agencyRepository.findById(agencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Instansi tidak ditemukan"));

        agency.setName(request.getNamaInstansi());
        agency.setCategory(AgencyCategoryEnum.valueOf(request.getKategoriInstansi()));
        agencyRepository.save(agency);
    }

    @Override
    public Double getAgencyScoreInPolicySampleByAgencyId(Long agencyId) {
        log.info(String.format("Starting get agency score in policy sample service: ID - %s", agencyId));

        List<PolicyEntity> policyEntitySampleList = policySampleService.getAllSampledPoliciesByAgencyId(agencyId);
        if (policyEntitySampleList.isEmpty()) {
            log.error(String.format("Agency with ID - %s has empty sample", agencyId));
            return 0.0;
        } else {
            int totalSample;
            double totalNilaiSample = 0.0;
            double averageNilaiSample;

            for (PolicyEntity policyEntity : policyEntitySampleList) {
                totalNilaiSample += (
                        policyEntity.getPolicyDetailsEntity().getValidationKUScore() == null ?
                                0.0 :
                                policyEntity.getPolicyDetailsEntity().getValidationKUScore());
            }

            totalSample = policyEntitySampleList.size();
            averageNilaiSample = totalNilaiSample / totalSample;

            log.info(String.format("Total sample: %d", totalSample));
            log.info(String.format("Total sample score: %f", totalNilaiSample));
            log.info(String.format("Sample average score: %f", averageNilaiSample));

            log.info("Closing get agency score in policy sample service");
            return averageNilaiSample;
        }
    }

    @Override
    public Double getAgencyAgendaSettingScoreInPolicySampleByAgencyId(Long agencyId) {
        List<PolicyEntity> policyEntitySampleList = policySampleService.getAllSampledPoliciesByAgencyId(agencyId);
        if (policyEntitySampleList.isEmpty()) {
            return 0.0;
        } else {
            int totalSample;
            double totalAgendaSetting = 0.0;
            double averageAgendaSettingSample;

            for (PolicyEntity policyEntity : policyEntitySampleList) {
                totalAgendaSetting += (
                        policyEntity.getAgendaSettingKUScoreEntity().getTotalScore() == null ?
                                0.0 :
                                policyEntity.getAgendaSettingKUScoreEntity().getTotalScore());
            }

            totalSample = policyEntitySampleList.size();
            averageAgendaSettingSample = totalAgendaSetting / totalSample;

            return averageAgendaSettingSample;
        }
    }

    @Override
    public Double getAgencyFormulasiKebijakanScoreInPolicySampleByAgencyId(Long agencyId) {
        List<PolicyEntity> policyEntitySampleList = policySampleService.getAllSampledPoliciesByAgencyId(agencyId);
        if (policyEntitySampleList.isEmpty()) {
            return 0.0;
        } else {
            int totalSample;
            double totalFormulasiKebijakan = 0.0;
            double averageFormulasiKebijakanSample;

            for (PolicyEntity policyEntity : policyEntitySampleList) {
                totalFormulasiKebijakan += (
                        policyEntity.getFormulasiKebijakanKUScoreEntity().getTotalScore() == null ?
                                0.0 :
                                policyEntity.getFormulasiKebijakanKUScoreEntity().getTotalScore());
            }

            totalSample = policyEntitySampleList.size();
            averageFormulasiKebijakanSample = totalFormulasiKebijakan / totalSample;

            return averageFormulasiKebijakanSample;
        }
    }

    @Override
    public Double getAgencyImplementasiKebijakanScoreInPolicySampleByAgencyId(Long agencyId) {
        List<PolicyEntity> policyEntitySampleList = policySampleService.getAllSampledPoliciesByAgencyId(agencyId);
        if (policyEntitySampleList.isEmpty()) {
            return 0.0;
        } else {
            int totalSample;
            double totalImplementasiKebijakan = 0.0;
            double averageImplementasiKebijakanSample;

            for (PolicyEntity policyEntity : policyEntitySampleList) {
                totalImplementasiKebijakan += (
                        policyEntity.getImplementasiKebijakanKUScoreEntity().getTotalScore() == null ?
                                0.0 :
                                policyEntity.getImplementasiKebijakanKUScoreEntity().getTotalScore());
            }

            totalSample = policyEntitySampleList.size();
            averageImplementasiKebijakanSample = totalImplementasiKebijakan / totalSample;

            return averageImplementasiKebijakanSample;
        }
    }

    @Override
    public Double getAgencyEvaluasiKebijakanScoreInPolicySampleByAgencyId(Long agencyId) {
        List<PolicyEntity> policyEntitySampleList = policySampleService.getAllSampledPoliciesByAgencyId(agencyId);
        if (policyEntitySampleList.isEmpty()) {
            return 0.0;
        } else {
            int totalSample;
            double totalEvaluasiKebijakan = 0.0;
            double averageEvaluasiKebijakanSample;

            for (PolicyEntity policyEntity : policyEntitySampleList) {
                totalEvaluasiKebijakan += (
                        policyEntity.getEvaluasiKebijakanKUScoreEntity().getTotalScore() == null ?
                                0.0 :
                                policyEntity.getEvaluasiKebijakanKUScoreEntity().getTotalScore());
            }

            totalSample = policyEntitySampleList.size();
            averageEvaluasiKebijakanSample = totalEvaluasiKebijakan / totalSample;

            return averageEvaluasiKebijakanSample;
        }
    }

    @Override
    public List<AgencyDto> getAgenciesWithPolicies() {
        List<AgencyDto> agencyDtos = new ArrayList<>();
        List<AgencyEntity> agencyEntities = policyRepository.getAgenciesWithPolicies();

        for (AgencyEntity agency : agencyEntities) {
            AgencyDto agencyDto = modelMapperUtility.initialize().map(agency, AgencyDto.class);
            agencyDtos.add(agencyDto);
        }

        return agencyDtos;
    }
}
