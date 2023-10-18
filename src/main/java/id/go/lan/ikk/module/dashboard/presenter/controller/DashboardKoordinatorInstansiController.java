package id.go.lan.ikk.module.dashboard.presenter.controller;

import id.go.lan.ikk.module.dashboard.model.*;
import id.go.lan.ikk.module.dashboard.presenter.model.*;
import id.go.lan.ikk.module.dashboard.service.DashboardKoordinatorInstansiService;
import id.go.lan.ikk.module.policy.model.*;
import id.go.lan.ikk.module.policy.service.PolicyDetailsService;
import id.go.lan.ikk.module.policy.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("kebijakan/koordinatorinstansi")
public class DashboardKoordinatorInstansiController {

    @Autowired
    private DashboardKoordinatorInstansiService dashboardKoordinatorInstansiService;

    @Autowired
    private PolicyDetailsService policyDetailsService;

    @GetMapping("/card")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> getKoordinatorInstansiCardInfo() {
        DashboardKoordinatorInstansiCardDto responseBody = dashboardKoordinatorInstansiService.getDashboardCard();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/card/kebijakan/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> getKoordinatorIntansiCardPolicyInfo(@PathVariable Long policyId) {
        DashboardKoordinatorInstansiCardPolicyDto responseBody = dashboardKoordinatorInstansiService.getDashboardCardPolicy(policyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> getAllPoliciesPerAgency() {
        List<DashboardPoliciesPerAgencyDto> responseBody = dashboardKoordinatorInstansiService.getAllPoliciesPerAgency();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/instansi/{agencyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> getAllAgencyPoliciesBy(@PathVariable Long agencyId) {
        DashboardAgencyPoliciesVerificationDto responseBody = dashboardKoordinatorInstansiService.getAllAgencyPoliciesByAgencyId(agencyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/verifikasi/{agencyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> verifyAllPoliciesByAgencyId(@PathVariable Long agencyId) {
        dashboardKoordinatorInstansiService.verifyAllPoliciesByAgencyId(agencyId);
        return new ResponseEntity<>("Populasi mulai diverifikasi", HttpStatus.OK);
    }

    @PostMapping("/tunda/{agencyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> cancelAllPoliciesByAgencyId(@PathVariable Long agencyId) {
        dashboardKoordinatorInstansiService.cancelAllPoliciesByAgencyId(agencyId);
        return new ResponseEntity<>("Populasi ditunda", HttpStatus.OK);
    }

    @PostMapping("/approve/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> approvePolicyByPolicyId(@PathVariable Long policyId) {
        dashboardKoordinatorInstansiService.approvePolicyByPolicyId(policyId);
        return new ResponseEntity<>("Populasi disetujui", HttpStatus.OK);
    }

    @PostMapping("/disapprove/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> disaprovePolicyByPolicyId(@PathVariable Long policyId) {
        dashboardKoordinatorInstansiService.disapprovePolicyByPolicyId(policyId);
        return new ResponseEntity<>("Populasi ditolak", HttpStatus.OK);
    }

    @PostMapping("/kirim/admin/{agencyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> sendVerifiedPoliciesToAdmin(@PathVariable Long agencyId) {
        dashboardKoordinatorInstansiService.sendVerifiedPoliciesToAdminByAgencyId(agencyId);
        return new ResponseEntity<>("Populasi berhasi dikirim ke admin", HttpStatus.OK);
    }

    @GetMapping("/proses")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> getAdminInstansiPoliciesProses() {
        List<DashboardKoordinatorInstansiPolicyProcessDto> responseBody = dashboardKoordinatorInstansiService
                .getDashboardAgencyProcess();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/proses/instansi/{agencyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> getAdminInstansiPoliciesProses(@PathVariable Long agencyId) {
        List<PolicyDto> responseBody = dashboardKoordinatorInstansiService
                .getKoordinatorInstansiDashboardProgress(agencyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/proses/detail/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> getAdminInstansiPolicyProgressDetail(@PathVariable Long policyId) {
        DashboardPolicyProgressDto responseBody = dashboardKoordinatorInstansiService
                .getKoordinatorInstansiPolicyProgress(policyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/selesai")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> getAdminInstansiPoliciesSelesai() {
        List<DashboardKoordinatorInstansiPolicyFinishDto> responseBody = dashboardKoordinatorInstansiService
                .getDashboardAgencyFinish();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/selesai/instansi/{agencyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> getAdminInstansiPoliciesSelesai(@PathVariable Long agencyId) {
        List<PolicyDto> responseBody = dashboardKoordinatorInstansiService
                .getKoordinatorInstansiDashboardFinish(agencyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
      
    @PostMapping("validasi/mulai/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> startValidationKIByPolicyId(@PathVariable Long policyId) {
        dashboardKoordinatorInstansiService.startValidationPolicyByPolicyId(policyId);
        return new ResponseEntity<>("Populasi mulai divalidasi", HttpStatus.OK);
    }

    @PostMapping("validasi/selesai/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> finishValidationKIByPolicyId(@PathVariable Long policyId) {
        dashboardKoordinatorInstansiService.finishValidationPolicyByPolicyId(policyId);
        return new ResponseEntity<>("Populasi selesai divalidasi", HttpStatus.OK);
    }

    @PostMapping("validasi/tunda/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> cancelValidationKIByPolicyId(@PathVariable Long policyId) {
        dashboardKoordinatorInstansiService.cancelValidationPolicyByPolicyId(policyId);
        return new ResponseEntity<>("Populasi berhasil ditunda", HttpStatus.OK);
    }

    @PostMapping("validasi/kirim/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> sendValidatedPoliciesToKuByPolicyId(@PathVariable Long policyId) {
        dashboardKoordinatorInstansiService.sendValidatedPoliciesToKoordinatorUtamaByPolicyId(policyId);
        return new ResponseEntity<>("Populasi tervalidasi berhasil dikirim", HttpStatus.OK);
    }

    @GetMapping("validasi/agendasetting/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> getAgendaSettingValidation(@PathVariable Long policyId) {
        AgendaSettingValidationDto responseBody = dashboardKoordinatorInstansiService.getAgendaSettingValidationByPolicyId(policyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("validasi/formulasikebijakan/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> getFormulasiKebijakanValidation(@PathVariable Long policyId) {
        FormulasiKebijakanValidationDto responseBody = dashboardKoordinatorInstansiService.getFormulasiKebijakanValidationByPolicyId(policyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("validasi/implementasikebijakan/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> getImplementasiKebijakanValidation(@PathVariable Long policyId) {
        ImplementasiKebijakanValidationDto responseBody = dashboardKoordinatorInstansiService.getImplementasiKebijakanValidationByPolicyId(policyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("validasi/evaluasikebijakan/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> getEvaluasiKebijakanValidation(@PathVariable Long policyId) {
        EvaluasiKebijakanValidationDto responseBody = dashboardKoordinatorInstansiService.getEvaluasiKebijakanValidationByPolicyId(policyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("validasi/simpan/agendasetting/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> submitAgendaSettingValidation(
            @PathVariable Long policyId,
            @RequestBody(required = false) AgendaSettingValidationRequest requestBody) {
        dashboardKoordinatorInstansiService.submitAgendaSettingValidationByPolicyId(policyId, requestBody);
        return new ResponseEntity<>("Validasi agenda setting berhasil disimpan", HttpStatus.OK);
    }

    @PostMapping("validasi/simpan/formulasikebijakan/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> submitFormulasiKebijakanValidation(
            @PathVariable Long policyId,
            @RequestBody(required = false) FormulasiKebijakanValidationRequest requestBody) {
        dashboardKoordinatorInstansiService.submitFormulasiKebijakanValidationByPolicyId(policyId, requestBody);
        return new ResponseEntity<>("Validasi formulasi kebijakan berhasil disimpan", HttpStatus.OK);
    }

    @PostMapping("validasi/simpan/implementasikebijakan/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> submitImplementasiKebijakanValidation(
            @PathVariable Long policyId,
            @RequestBody(required = false) ImplementasiKebijakanValidationRequest requestBody) {
        dashboardKoordinatorInstansiService.submitImplementasiKebijakanValidationByPolicyId(policyId, requestBody);
        return new ResponseEntity<>("Validasi implementasi kebijakan berhasil disimpan", HttpStatus.OK);
    }

    @PostMapping("validasi/simpan/evaluasikebijakan/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> submitEvaluasiKebijakanValidation(
            @PathVariable Long policyId,
            @RequestBody(required = false) EvaluasiKebijakanValidationRequest requestBody) {
        dashboardKoordinatorInstansiService.submitEvaluasiKebijakanValidationByPolicyId(policyId, requestBody);
        return new ResponseEntity<>("Validasi evaluasi kebijakan berhasil disimpan", HttpStatus.OK);
    }

    @GetMapping("validasi/catatan/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional', 'role_koordinator_utama', 'role_koordinator_instansi')")
    public ResponseEntity<Object> getValidationNote(@PathVariable Long policyId) {
        String validationNote = policyDetailsService.getPolicyValidationKINote(policyId);
        return new ResponseEntity<>(validationNote, HttpStatus.OK);
    }

    @PutMapping("validasi/catatan/{policyId}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> updateValidationNote(
            @PathVariable Long policyId,
            @RequestBody PolicyValidationNoteRequest validationNote) {
        policyDetailsService.updatePolicyValidationKINote(policyId, validationNote.getValidationNote());
        return new ResponseEntity<>("Catatan validasi berhasil disimpan", HttpStatus.OK);
    }
}
