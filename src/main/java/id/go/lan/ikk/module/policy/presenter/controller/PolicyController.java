package id.go.lan.ikk.module.policy.presenter.controller;

import id.go.lan.ikk.module.policy.model.PolicyDto;
import id.go.lan.ikk.module.policy.model.PolicySampleDto;
import id.go.lan.ikk.module.policy.model.PolicySelesaiDto;
import id.go.lan.ikk.module.policy.presenter.model.AddUpdatePolicyRequest;
import id.go.lan.ikk.module.policy.presenter.model.AssignEnumeratorRequest;
import id.go.lan.ikk.module.policy.service.PolicyService;
import id.go.lan.ikk.module.user.entity.AgencyEntity;
import id.go.lan.ikk.module.user.service.AgencyMasterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/kebijakan")
public class PolicyController {

    @Autowired
    private PolicyService policyService;
    @Autowired
    private AgencyMasterService agencyMasterService;

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('role_admin_instansi')")
    public ResponseEntity<Object> getAllPoliciesByAdmin() {
        List<PolicyDto> responseBody = policyService.getAllAdminPolicy();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PutMapping("/pindah_instansi/{idkebijakan}/{id_agency_tujuan}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> pindahKebijakan(@PathVariable Long idkebijakan,@PathVariable Long id_agency_tujuan){
        policyService.pindahPolicyByPolicyId(idkebijakan,id_agency_tujuan);
        return new ResponseEntity<>("Populasi Berhasil Dipindah", HttpStatus.OK);
    }

    @GetMapping("/reset/{id_agency}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> resetPopulasiKebijakan(@PathVariable Long id_agency) {
        AgencyEntity agency=agencyMasterService.getAgencyById(id_agency);
        policyService.cancelVerificationByAgency(agency);
        return new ResponseEntity<>("Populasi Berhasil di reset", HttpStatus.OK);
    }

    @GetMapping("/resetverifikasi/{id_agency}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> resetKebijakan(@PathVariable Long id_agency) {
        AgencyEntity agency=agencyMasterService.getAgencyById(id_agency);
        policyService.cancelVerificationPolicyByAgency(agency);
        return new ResponseEntity<>("Populasi Berhasil di reset", HttpStatus.OK);
    }

    @GetMapping("/disetujui")
    @PreAuthorize("hasAnyAuthority('role_admin_instansi')")
    public ResponseEntity<Object> getAllPoliciesDisetujuiByAdmin() {
        PolicySampleDto responseBody = policyService.getAllPoliciesDisetujui();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/disetujui/sampling")
    @PreAuthorize("hasAnyAuthority('role_admin_instansi')")
    public ResponseEntity<Object> generatePolicySample() {
        policyService.generatePolicySample();
        return new ResponseEntity<>("Populasi berhasi disampling", HttpStatus.OK);
    }

    @GetMapping("/selesai")
    @PreAuthorize("hasAnyAuthority('role_admin_instansi')")
    public ResponseEntity<Object> getAllPoliciesSelesaiByAdmin() {
        List<PolicySelesaiDto> responseBody = policyService.getAllPoliciesSelesai();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('role_admin_instansi')")
    public ResponseEntity<Object> addNewPolicy(@ModelAttribute AddUpdatePolicyRequest requestBody) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date effectiveDate = format.parse(requestBody.getTanggal());
        policyService.addNewPolicy(requestBody.getNama(), effectiveDate, requestBody.getJenis(), requestBody.getFile());
        return new ResponseEntity<>("Populasi berhasil ditambah", HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{idKebijakan}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('role_admin_nasional','role_admin_instansi')")
    public ResponseEntity<Object> updatePolicyById(
            @PathVariable Long idKebijakan,
            @ModelAttribute AddUpdatePolicyRequest requestBody) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date effectiveDate = format.parse(requestBody.getTanggal());
        policyService.updatePolicyByPolicyId(idKebijakan,
                requestBody.getNama(),
                effectiveDate,
                requestBody.getJenis(),
                requestBody.getFile());
        return new ResponseEntity<>("Populasi berhasi diperbarui", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{idKebijakan}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional','role_admin_instansi')")
    public ResponseEntity<Object> deletePolicyById(@PathVariable Long idKebijakan) {
        policyService.deletePolicyByPolicyId(idKebijakan);
        return new ResponseEntity<>("Populasi berhasil dihapus", HttpStatus.OK);
    }

    @PostMapping("/kirim")
    @PreAuthorize("hasAnyAuthority('role_admin_instansi')")
    public ResponseEntity<Object> sendPoliciesToKoordinatorInstansi() {
        policyService.sendToKoordinatorInstansi();
        return new ResponseEntity<>("Populasi berhasil dikirim ke Koordinator Instansi", HttpStatus.OK);
    }

    @PostMapping("/assign/{idKebijakan}")
    @PreAuthorize("hasAnyAuthority('role_admin_instansi')")
    public ResponseEntity<Object> assignPolicyToEnumeratorById(
            @PathVariable Long idKebijakan,
            @RequestBody AssignEnumeratorRequest requestBody) {
        policyService.assignEnumerator(idKebijakan, requestBody.getEnumeratorId());
        return new ResponseEntity<>("Enumerator berhasil di-assign ke populasi", HttpStatus.OK);
    }

    @PostMapping("/kirim/validasi/{idKebijakan}")
    @PreAuthorize("hasAnyAuthority('role_admin_instansi')")
    public ResponseEntity<Object> sendPolicyToKoordinatorInstansiValidation(@PathVariable Long idKebijakan) {
        policyService.sendToKoordinatorInstansiValidation(idKebijakan);
        return new ResponseEntity<>("Populasi berhasil dikirim ke Koordinator Instansi", HttpStatus.OK);
    }
}
