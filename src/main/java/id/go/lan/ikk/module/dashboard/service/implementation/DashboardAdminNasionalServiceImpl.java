package id.go.lan.ikk.module.dashboard.service.implementation;

import id.go.lan.ikk.exception.ResourceNotFoundException;
import id.go.lan.ikk.module.dashboard.model.DashboardKoordinatorUtamaCardPolicyDto;
import id.go.lan.ikk.module.dashboard.model.DashboardPolicyProgressDto;
import id.go.lan.ikk.module.dashboard.service.DashboardAdminNasionalService;
import id.go.lan.ikk.module.dashboard.service.DashboardKoordinatorUtamaService;
import id.go.lan.ikk.module.policy.entity.PolicyEntity;
import id.go.lan.ikk.module.policy.entity.PolicySampleEntity;
import id.go.lan.ikk.module.policy.model.PolicyDto;
import id.go.lan.ikk.module.policy.repository.PolicyRepository;
import id.go.lan.ikk.module.policy.repository.PolicySampleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DashboardAdminNasionalServiceImpl implements DashboardAdminNasionalService {

    @Autowired
    private DashboardKoordinatorUtamaService dashboardKoordinatorUtamaService;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private PolicySampleRepository policySampleRepository;

    @Override
    public List<PolicyDto> getAllPolicyByAgency(Long agencyId) {
        return dashboardKoordinatorUtamaService.getAllAgencyPoliciesByAgencyId(agencyId);
    }

    @Override
    public DashboardKoordinatorUtamaCardPolicyDto getAdminNasionalPolicyDetail(Long policyId) {
        return dashboardKoordinatorUtamaService.getDashboardCardPolicy(policyId);
    }

    @Override
    public DashboardPolicyProgressDto getPolicyProgressDetailByPolicyId(Long policyId) {
        return dashboardKoordinatorUtamaService.getPolicyProgressDetailByPolicyId(policyId);
    }

    @Override
    public ByteArrayInputStream exportToExcel() {
        List<PolicyEntity> filteredPolicyEntities = new ArrayList<>();
        List<PolicySampleEntity> policySampleEntities = policySampleRepository.findAll();

        for (PolicySampleEntity policySample : policySampleEntities) {
            new PolicyEntity();
            PolicyEntity policy;
            if (policyRepository.findById(policySample.getPolicyId()).isPresent()) {
                policy = policyRepository.findById(policySample.getPolicyId())
                        .orElseThrow(() -> new ResourceNotFoundException("Kebijakan tidak ditemukan"));
            } else {
                continue;
            }

            filteredPolicyEntities.add(policy);
        }

        try {
            InputStream template = getClass().getResourceAsStream("/template.xlsx");
            assert template != null;
            Workbook workbook = new XSSFWorkbook(template);

            // Initialize sheets
            Sheet generalSheet = workbook.getSheet("general");
            Sheet nilai1Sheet = workbook.getSheet("nilai1");
            Sheet nilai2Sheet = workbook.getSheet("nilai2");
            Sheet nilai3Sheet = workbook.getSheet("nilai3");

            // insert data to general sheet
            for (int i = 0; i < filteredPolicyEntities.size(); i++) {
                Row dataRow = generalSheet.createRow(i + 1);
                dataRow.createCell(0).setCellValue(filteredPolicyEntities.get(i).getAgency().getName());
                dataRow.createCell(1).setCellValue(filteredPolicyEntities.get(i).getName());

                dataRow.createCell(2).setCellValue(filteredPolicyEntities.get(i)
                        .getPolicyDetailsEntity().getBaseScore() != null ? filteredPolicyEntities.get(i)
                        .getPolicyDetailsEntity().getBaseScore() : 0.0);
                dataRow.createCell(3).setCellValue(filteredPolicyEntities.get(i)
                        .getPolicyDetailsEntity().getValidationKIScore() != null ? filteredPolicyEntities.get(i)
                        .getPolicyDetailsEntity().getValidationKIScore() : 0.0);
                dataRow.createCell(4).setCellValue(filteredPolicyEntities.get(i)
                        .getPolicyDetailsEntity().getValidationKUScore() != null ? filteredPolicyEntities.get(i)
                        .getPolicyDetailsEntity().getValidationKUScore() : 0.0);
            }

            // insert data to nilai 1 sheet
            for (int i = 0; i < filteredPolicyEntities.size(); i++) {
                Row dataRow = nilai1Sheet.createRow(i + 1);
                dataRow.createCell(0).setCellValue(filteredPolicyEntities.get(i).getAgency().getName());
                dataRow.createCell(1).setCellValue(filteredPolicyEntities.get(i).getName());

                // Agenda setting base score
                Method[] agendaSettingNilai1Methods = filteredPolicyEntities.get(i).getAgendaSettingBaseScoreEntity().getClass().getMethods();
                int agendaSettingCellIndex = 2;
                for (Method agendaSettingNilai1Method : agendaSettingNilai1Methods) {
                    if (agendaSettingNilai1Method.getName().startsWith("getA")) {
                        dataRow.createCell(agendaSettingCellIndex).setCellValue((Double) (agendaSettingNilai1Method
                                .invoke(filteredPolicyEntities.get(i).getAgendaSettingBaseScoreEntity()) != null ?
                                agendaSettingNilai1Method
                                        .invoke(filteredPolicyEntities.get(i).getAgendaSettingBaseScoreEntity()) : 0.0));
                        agendaSettingCellIndex++;
                    }
                }

                // Formulasi kebijakan base score
                Method[] formulasiKebijakanNilai1Methods = filteredPolicyEntities.get(i).getFormulasiKebijakanBaseScoreEntity().getClass().getMethods();
                int formulasiKebijakanCellIndex = agendaSettingCellIndex;
                for (Method formulasiKebijakanNilai1Method : formulasiKebijakanNilai1Methods) {
                    if (formulasiKebijakanNilai1Method.getName().startsWith("getB")) {
                        dataRow.createCell(formulasiKebijakanCellIndex).setCellValue((Double) (formulasiKebijakanNilai1Method
                                .invoke(filteredPolicyEntities.get(i).getFormulasiKebijakanBaseScoreEntity()) != null ?
                                formulasiKebijakanNilai1Method
                                        .invoke(filteredPolicyEntities.get(i).getFormulasiKebijakanBaseScoreEntity()) : 0.0));
                        formulasiKebijakanCellIndex++;
                    }
                }

                // Implementasi kebijakan base score
                Method[] implementasiKebijakanNilai1Methods = filteredPolicyEntities.get(i).getImplementasiKebijakanBaseScoreEntity().getClass().getMethods();
                int implementasiKebijakanCellIndex = formulasiKebijakanCellIndex;
                for (Method implementasiKebijakanNilai1Method : implementasiKebijakanNilai1Methods) {
                    if (implementasiKebijakanNilai1Method.getName().startsWith("getC1") ||
                            implementasiKebijakanNilai1Method.getName().startsWith("getC2") ||
                            implementasiKebijakanNilai1Method.getName().startsWith("getC3")) {
                        dataRow.createCell(implementasiKebijakanCellIndex).setCellValue((Double) (implementasiKebijakanNilai1Method
                                .invoke(filteredPolicyEntities.get(i).getImplementasiKebijakanBaseScoreEntity()) != null ?
                                implementasiKebijakanNilai1Method
                                        .invoke(filteredPolicyEntities.get(i).getImplementasiKebijakanBaseScoreEntity()) : 0.0));
                        implementasiKebijakanCellIndex++;
                    }
                }

                // Evaluasi kebijakan base score
                Method[] evaluasiKebijakanNilai1Methods = filteredPolicyEntities.get(i).getEvaluasiKebijakanBaseScoreEntity().getClass().getMethods();
                int evaluasiKebijakanCellIndex = implementasiKebijakanCellIndex;
                for (Method evaluasiKebijakanNilai1Method : evaluasiKebijakanNilai1Methods) {
                    if (evaluasiKebijakanNilai1Method.getName().startsWith("getD")) {
                        dataRow.createCell(evaluasiKebijakanCellIndex).setCellValue((Double) (evaluasiKebijakanNilai1Method
                                .invoke(filteredPolicyEntities.get(i).getEvaluasiKebijakanBaseScoreEntity()) != null ?
                                evaluasiKebijakanNilai1Method
                                        .invoke(filteredPolicyEntities.get(i).getEvaluasiKebijakanBaseScoreEntity()) : 0.0));
                        evaluasiKebijakanCellIndex++;
                    }
                }
            }

            // insert data to nilai 2 sheet
            for (int i = 0; i < filteredPolicyEntities.size(); i++) {
                Row dataRow = nilai2Sheet.createRow(i + 1);
                dataRow.createCell(0).setCellValue(filteredPolicyEntities.get(i).getAgency().getName());
                dataRow.createCell(1).setCellValue(filteredPolicyEntities.get(i).getName());

                // Agenda setting KI score
                Method[] agendaSettingNilai2Methods = filteredPolicyEntities.get(i).getAgendaSettingKIScoreEntity().getClass().getMethods();
                int agendaSettingCellIndex = 2;
                for (Method agendaSettingNilai2Method : agendaSettingNilai2Methods) {
                    if (agendaSettingNilai2Method.getName().startsWith("getA")) {
                        dataRow.createCell(agendaSettingCellIndex).setCellValue((Double) (agendaSettingNilai2Method
                                .invoke(filteredPolicyEntities.get(i).getAgendaSettingKIScoreEntity()) != null ?
                                agendaSettingNilai2Method
                                        .invoke(filteredPolicyEntities.get(i).getAgendaSettingKIScoreEntity()) : 0.0));
                        agendaSettingCellIndex++;
                    }
                }

                // Formulasi kebijakan KI score
                Method[] formulasiKebijakanNilai2Methods = filteredPolicyEntities.get(i).getFormulasiKebijakanKIScoreEntity().getClass().getMethods();
                int formulasiKebijakanCellIndex = agendaSettingCellIndex;
                for (Method formulasiKebijakanNilai2Method : formulasiKebijakanNilai2Methods) {
                    if (formulasiKebijakanNilai2Method.getName().startsWith("getB")) {
                        dataRow.createCell(formulasiKebijakanCellIndex).setCellValue((Double) (formulasiKebijakanNilai2Method
                                .invoke(filteredPolicyEntities.get(i).getFormulasiKebijakanKIScoreEntity()) != null ?
                                formulasiKebijakanNilai2Method
                                        .invoke(filteredPolicyEntities.get(i).getFormulasiKebijakanKIScoreEntity()) : 0.0));
                        formulasiKebijakanCellIndex++;
                    }
                }

                // Implementasi kebijakan KI score
                Method[] implementasiKebijakanNilai2Methods = filteredPolicyEntities.get(i).getImplementasiKebijakanKIScoreEntity().getClass().getMethods();
                int implementasiKebijakanCellIndex = formulasiKebijakanCellIndex;
                for (Method implementasiKebijakanNilai2Method : implementasiKebijakanNilai2Methods) {
                    if (implementasiKebijakanNilai2Method.getName().startsWith("getC1") ||
                            implementasiKebijakanNilai2Method.getName().startsWith("getC2") ||
                            implementasiKebijakanNilai2Method.getName().startsWith("getC3")) {
                        dataRow.createCell(implementasiKebijakanCellIndex).setCellValue((Double) (implementasiKebijakanNilai2Method
                                .invoke(filteredPolicyEntities.get(i).getImplementasiKebijakanKIScoreEntity()) != null ?
                                implementasiKebijakanNilai2Method
                                        .invoke(filteredPolicyEntities.get(i).getImplementasiKebijakanKIScoreEntity()) : 0.0));
                        implementasiKebijakanCellIndex++;
                    }
                }

                // Evaluasi kebijakan KI score
                Method[] evaluasiKebijakanNilai2Methods = filteredPolicyEntities.get(i).getEvaluasiKebijakanKIScoreEntity().getClass().getMethods();
                int evaluasiKebijakanCellIndex = implementasiKebijakanCellIndex;
                for (Method evaluasiKebijakanNilai2Method : evaluasiKebijakanNilai2Methods) {
                    if (evaluasiKebijakanNilai2Method.getName().startsWith("getD")) {
                        dataRow.createCell(evaluasiKebijakanCellIndex).setCellValue((Double) (evaluasiKebijakanNilai2Method
                                .invoke(filteredPolicyEntities.get(i).getEvaluasiKebijakanKIScoreEntity()) != null ?
                                evaluasiKebijakanNilai2Method
                                        .invoke(filteredPolicyEntities.get(i).getEvaluasiKebijakanKIScoreEntity()) : 0.0));
                        evaluasiKebijakanCellIndex++;
                    }
                    dataRow.createCell(evaluasiKebijakanCellIndex)
                            .setCellValue(filteredPolicyEntities.get(i)
                                    .getPolicyDetailsEntity().getValidationKINote());
                }
            }

            // insert data to nilai 3 sheet
            for (int i = 0; i < filteredPolicyEntities.size(); i++) {
                Row dataRow = nilai3Sheet.createRow(i + 1);
                dataRow.createCell(0).setCellValue(filteredPolicyEntities.get(i).getAgency().getName());
                dataRow.createCell(1).setCellValue(filteredPolicyEntities.get(i).getName());

                // Agenda setting KU score
                Method[] agendaSettingNilai3Methods = filteredPolicyEntities.get(i).getAgendaSettingKUScoreEntity().getClass().getMethods();
                int agendaSettingCellIndex = 2;
                for (Method agendaSettingNilai3Method : agendaSettingNilai3Methods) {
                    if (agendaSettingNilai3Method.getName().startsWith("getA")) {
                        dataRow.createCell(agendaSettingCellIndex).setCellValue((Double) (agendaSettingNilai3Method
                                .invoke(filteredPolicyEntities.get(i).getAgendaSettingKUScoreEntity()) != null ?
                                agendaSettingNilai3Method
                                        .invoke(filteredPolicyEntities.get(i).getAgendaSettingKUScoreEntity()) : 0.0));
                        agendaSettingCellIndex++;
                    }
                }

                // Formulasi kebijakan KU score
                Method[] formulasiKebijakanNilai3Methods = filteredPolicyEntities.get(i).getFormulasiKebijakanKUScoreEntity().getClass().getMethods();
                int formulasiKebijakanCellIndex = agendaSettingCellIndex;
                for (Method formulasiKebijakanNilai3Method : formulasiKebijakanNilai3Methods) {
                    if (formulasiKebijakanNilai3Method.getName().startsWith("getB")) {
                        dataRow.createCell(formulasiKebijakanCellIndex).setCellValue((Double) (formulasiKebijakanNilai3Method
                                .invoke(filteredPolicyEntities.get(i).getFormulasiKebijakanKUScoreEntity()) != null ?
                                formulasiKebijakanNilai3Method
                                        .invoke(filteredPolicyEntities.get(i).getFormulasiKebijakanKUScoreEntity()) : 0.0));
                        formulasiKebijakanCellIndex++;
                    }
                }

                // Implementasi kebijakan KU score
                Method[] implementasiKebijakanNilai3Methods = filteredPolicyEntities.get(i).getImplementasiKebijakanKUScoreEntity().getClass().getMethods();
                int implementasiKebijakanCellIndex = formulasiKebijakanCellIndex;
                for (Method implementasiKebijakanNilai3Method : implementasiKebijakanNilai3Methods) {
                    if (implementasiKebijakanNilai3Method.getName().startsWith("getC1") ||
                            implementasiKebijakanNilai3Method.getName().startsWith("getC2") ||
                            implementasiKebijakanNilai3Method.getName().startsWith("getC3")) {
                        dataRow.createCell(implementasiKebijakanCellIndex).setCellValue((Double) (implementasiKebijakanNilai3Method
                                .invoke(filteredPolicyEntities.get(i).getImplementasiKebijakanKUScoreEntity()) != null ?
                                implementasiKebijakanNilai3Method
                                        .invoke(filteredPolicyEntities.get(i).getImplementasiKebijakanKUScoreEntity()) : 0.0));
                        implementasiKebijakanCellIndex++;
                    }
                }

                // Evaluasi kebijakan KU score
                Method[] evaluasiKebijakanNilai3Methods = filteredPolicyEntities.get(i).getEvaluasiKebijakanKUScoreEntity().getClass().getMethods();
                int evaluasiKebijakanCellIndex = implementasiKebijakanCellIndex;
                for (Method evaluasiKebijakanNilai3Method : evaluasiKebijakanNilai3Methods) {
                    if (evaluasiKebijakanNilai3Method.getName().startsWith("getD")) {
                        dataRow.createCell(evaluasiKebijakanCellIndex).setCellValue((Double) (evaluasiKebijakanNilai3Method
                                .invoke(filteredPolicyEntities.get(i).getEvaluasiKebijakanKUScoreEntity()) != null ?
                                evaluasiKebijakanNilai3Method
                                        .invoke(filteredPolicyEntities.get(i).getEvaluasiKebijakanKUScoreEntity()) : 0.0));
                        evaluasiKebijakanCellIndex++;
                    }
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
            return null;
        }
    }
}
