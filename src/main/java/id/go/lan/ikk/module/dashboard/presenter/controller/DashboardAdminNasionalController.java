package id.go.lan.ikk.module.dashboard.presenter.controller;

import id.go.lan.ikk.module.dashboard.model.DashboardKoordinatorUtamaCardPolicyDto;
import id.go.lan.ikk.module.dashboard.model.DashboardPolicyProgressDto;
import id.go.lan.ikk.module.dashboard.service.DashboardAdminNasionalService;
import id.go.lan.ikk.module.dashboard.service.DashboardKoordinatorUtamaService;
import id.go.lan.ikk.module.policy.model.*;
import id.go.lan.ikk.module.user.model.AgencyDto;
import id.go.lan.ikk.module.user.service.AgencyService;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("kebijakan/adminnasional")
public class DashboardAdminNasionalController {

    @Autowired
    private DashboardAdminNasionalService dashboardAdminNasionalService;

    @Autowired
    private DashboardKoordinatorUtamaService dashboardKoordinatorUtamaService;

    @Autowired
    private AgencyService agencyService;

    @GetMapping("instansi/{agencyId}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> getPoliciesByAgencyId(@PathVariable Long agencyId) {
        List<PolicyDto> responseBody = dashboardAdminNasionalService.getAllPolicyByAgency(agencyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/kebijakan/card/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> getKoordinatorUtamaCardPolicyInfo(@PathVariable Long policyId) {
        DashboardKoordinatorUtamaCardPolicyDto responseBody = dashboardAdminNasionalService.getAdminNasionalPolicyDetail(policyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/kebijakan/detail/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> getPolicyDetailByPolicyId(@PathVariable Long policyId) {
        DashboardPolicyProgressDto responseBody = dashboardAdminNasionalService.getPolicyProgressDetailByPolicyId(policyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("validasi/agendasetting/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> getAgendaSettingValidation(@PathVariable Long policyId) {
        AgendaSettingValidationDto responseBody = dashboardKoordinatorUtamaService.getAgendaSettingValidationByPolicyId(policyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("validasi/formulasikebijakan/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> getFormulasiKebijakanValidation(@PathVariable Long policyId) {
        FormulasiKebijakanValidationDto responseBody = dashboardKoordinatorUtamaService.getFormulasiKebijakanValidationByPolicyId(policyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("validasi/implementasikebijakan/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> getImplementasiKebijakanValidation(@PathVariable Long policyId) {
        ImplementasiKebijakanValidationDto responseBody = dashboardKoordinatorUtamaService.getImplementasiKebijakanValidationByPolicyId(policyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("validasi/evaluasikebijakan/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> getEvaluasiKebijakanValidation(@PathVariable Long policyId) {
        EvaluasiKebijakanValidationDto responseBody = dashboardKoordinatorUtamaService.getEvaluasiKebijakanValidationByPolicyId(policyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("export")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public void exportToExcel(HttpServletResponse response) {
        try {
            ByteArrayInputStream byteArrayInputStream = dashboardAdminNasionalService.exportToExcel();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=ikk-report.xlsx");
            IOUtils.copy(byteArrayInputStream, response.getOutputStream());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Cause: " + e.getCause());
        }
    }
}
