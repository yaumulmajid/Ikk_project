package id.go.lan.ikk.module.dashboard.presenter.controller;

import id.go.lan.ikk.module.dashboard.model.DashboardKoordinatorUtamaCardPolicyDto;
import id.go.lan.ikk.module.dashboard.model.DashboardPoliciesPerAgencyDto;
import id.go.lan.ikk.module.dashboard.model.DashboardPolicyProgressDto;
import id.go.lan.ikk.module.dashboard.presenter.model.AgendaSettingValidationRequest;
import id.go.lan.ikk.module.dashboard.presenter.model.EvaluasiKebijakanValidationRequest;
import id.go.lan.ikk.module.dashboard.presenter.model.FormulasiKebijakanValidationRequest;
import id.go.lan.ikk.module.dashboard.presenter.model.ImplementasiKebijakanValidationRequest;
import id.go.lan.ikk.module.dashboard.service.DashboardKoordinatorUtamaService;
import id.go.lan.ikk.module.policy.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("kebijakan/koordinatorutama")
public class DashboardKoordinatorUtamaController {

    @Autowired
    private DashboardKoordinatorUtamaService dashboardKoordinatorUtamaService;

    @GetMapping("/card/kebijakan/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> getKoordinatorUtamaCardPolicyInfo(@PathVariable Long policyId) {
        DashboardKoordinatorUtamaCardPolicyDto responseBody = dashboardKoordinatorUtamaService.getDashboardCardPolicy(policyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/kebijakan/detail/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> getPolicyDetailByPolicyId(@PathVariable Long policyId) {
        DashboardPolicyProgressDto responseBody = dashboardKoordinatorUtamaService.getPolicyProgressDetailByPolicyId(policyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/koordinatorinstansi/{idKoordinatorInstansi}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> getAllPoliciesPerAgency(@PathVariable Long idKoordinatorInstansi) {
        List<DashboardPoliciesPerAgencyDto> responseBody = dashboardKoordinatorUtamaService.getAllPoliciesPerAgency(idKoordinatorInstansi);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/koordinatorinstansi/instansi/{agencyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> getAllAgencyPoliciesByAgencyId(@PathVariable Long agencyId) {
        List<PolicyDto> responseBody = dashboardKoordinatorUtamaService.getAllAgencyPoliciesByAgencyId(agencyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("validasi/mulai/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> startValidationKUByPolicyId(@PathVariable Long policyId) {
        dashboardKoordinatorUtamaService.startValidationPolicyByPolicyId(policyId);
        return new ResponseEntity<>("Populasi mulai divalidasi", HttpStatus.OK);
    }

    @PostMapping("validasi/selesai/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> finishValidationKUByPolicyId(@PathVariable Long policyId) {
        dashboardKoordinatorUtamaService.finishValidationPolicyByPolicyId(policyId);
        return new ResponseEntity<>("Populasi selesai divalidasi", HttpStatus.OK);
    }

    @PostMapping("validasi/terbit/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> publishValidationKUByPolicyId(@PathVariable Long policyId) {
        dashboardKoordinatorUtamaService.publishValidationPolicyByPolicyId(policyId);
        return new ResponseEntity<>("Populasi berhasil diterbitkan", HttpStatus.OK);
    }

    @PostMapping("validasi/tunda/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> cancelValidationKUByPolicyId(@PathVariable Long policyId) {
        dashboardKoordinatorUtamaService.cancelValidationPolicyByPolicyId(policyId);
        return new ResponseEntity<>("Populasi tervalidasi berhasil ditunda", HttpStatus.OK);
    }

    @GetMapping("validasi/agendasetting/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> getAgendaSettingValidation(@PathVariable Long policyId) {
        AgendaSettingValidationDto responseBody = dashboardKoordinatorUtamaService.getAgendaSettingValidationByPolicyId(policyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("validasi/formulasikebijakan/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> getFormulasiKebijakanValidation(@PathVariable Long policyId) {
        FormulasiKebijakanValidationDto responseBody = dashboardKoordinatorUtamaService.getFormulasiKebijakanValidationByPolicyId(policyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("validasi/implementasikebijakan/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> getImplementasiKebijakanValidation(@PathVariable Long policyId) {
        ImplementasiKebijakanValidationDto responseBody = dashboardKoordinatorUtamaService.getImplementasiKebijakanValidationByPolicyId(policyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("validasi/evaluasikebijakan/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> getEvaluasiKebijakanValidation(@PathVariable Long policyId) {
        EvaluasiKebijakanValidationDto responseBody = dashboardKoordinatorUtamaService.getEvaluasiKebijakanValidationByPolicyId(policyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("validasi/simpan/agendasetting/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> submitAgendaSettingValidation(
            @PathVariable Long policyId,
            @RequestBody(required = false) AgendaSettingValidationRequest requestBody) {
        dashboardKoordinatorUtamaService.submitAgendaSettingValidationByPolicyId(policyId, requestBody);
        return new ResponseEntity<>("Validasi agenda setting berhasil disimpan", HttpStatus.OK);
    }

    @PostMapping("validasi/simpan/formulasikebijakan/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> submitFormulasiKebijakanValidation(
            @PathVariable Long policyId,
            @RequestBody(required = false) FormulasiKebijakanValidationRequest requestBody) {
        dashboardKoordinatorUtamaService.submitFormulasiKebijakanValidationByPolicyId(policyId, requestBody);
        return new ResponseEntity<>("Validasi formulasi kebijakan berhasil disimpan", HttpStatus.OK);
    }

    @PostMapping("validasi/simpan/implementasikebijakan/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> submitImplementasiKebijakanValidation(
            @PathVariable Long policyId,
            @RequestBody(required = false) ImplementasiKebijakanValidationRequest requestBody) {
        dashboardKoordinatorUtamaService.submitImplementasiKebijakanValidationByPolicyId(policyId, requestBody);
        return new ResponseEntity<>("Validasi implementasi kebijakan berhasil disimpan", HttpStatus.OK);
    }

    @PostMapping("validasi/simpan/evaluasikebijakan/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> submitEvaluasiKebijakanValidation(
            @PathVariable Long policyId,
            @RequestBody(required = false) EvaluasiKebijakanValidationRequest requestBody) {
        dashboardKoordinatorUtamaService.submitEvaluasiKebijakanValidationByPolicyId(policyId, requestBody);
        return new ResponseEntity<>("Validasi evaluasi kebijakan berhasil disimpan", HttpStatus.OK);
    }
}
