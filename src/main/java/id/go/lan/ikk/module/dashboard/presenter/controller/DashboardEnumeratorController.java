package id.go.lan.ikk.module.dashboard.presenter.controller;

import id.go.lan.ikk.module.dashboard.model.DashboardEnumeratorCardDto;
import id.go.lan.ikk.module.dashboard.service.DashboardEnumeratorService;
import id.go.lan.ikk.module.policy.model.PolicyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("kebijakan/enumerator")
public class DashboardEnumeratorController {

    @Autowired
    private DashboardEnumeratorService dashboardEnumeratorService;

    @GetMapping("/card")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> getEnumeratorCardInfo() {
        DashboardEnumeratorCardDto responseBody = dashboardEnumeratorService.getDashboardCard();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> getAllPolicies() {
        List<PolicyDto> responseBody = dashboardEnumeratorService.getAllPolicies();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/proses")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> getAllPoliciesProses() {
        List<PolicyDto> responseBody = dashboardEnumeratorService.getAllPoliciesProses();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/detail/{idKebijakan}")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> getEnumeratorPolicyByPolicyId(@PathVariable Long idKebijakan) {
        PolicyDto responseBody = dashboardEnumeratorService.getEnumeratorPolicyByPolicyId(idKebijakan);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/mulai/{idKebijakan}")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> startPolicyProgress(@PathVariable Long idKebijakan) {
        dashboardEnumeratorService.startPolicyProgress(idKebijakan);
        return new ResponseEntity<>("Populasi mulai diinput", HttpStatus.OK);
    }

    @PostMapping("/kirim/{idKebijakan}")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> sendProcessedPolicyToAdmin(@PathVariable Long idKebijakan) {
        dashboardEnumeratorService.sendToAdminForValidation(idKebijakan);
        return new ResponseEntity<>("Populasi berhasil dikirim ke admin", HttpStatus.OK);
    }
}
