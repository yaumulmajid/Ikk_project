package id.go.lan.ikk.module.user.presenter.controller;

import id.go.lan.ikk.module.user.model.AgencyDto;
import id.go.lan.ikk.module.user.presenter.model.AddUpdateAgencyRequest;
import id.go.lan.ikk.module.user.service.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("instansi")
public class AgencyController {

    @Autowired
    private AgencyService agencyService;

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional', 'role_koordinator_utama', 'role_koordinator_instansi', 'role_admin_instansi')")
    public ResponseEntity<Object> getAllAgencies() {
        List<AgencyDto> responseBody = agencyService.getAllAgencies();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/filtered")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> getAllAgenciesWithPolicies() {
        List<AgencyDto> responseBody = agencyService.getAgenciesWithPolicies();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{agencyId}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional', 'role_koordinator_utama', 'role_koordinator_instansi', 'role_admin_instansi')")
    public ResponseEntity<Object> getAgencyById(@PathVariable Long agencyId) {
        AgencyDto responseBody = agencyService.getAgencyById(agencyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> addAgency(@RequestBody AddUpdateAgencyRequest request) {
        agencyService.addAgency(request);
        return new ResponseEntity<>("Instansi berhasil ditambahkan", HttpStatus.CREATED);
    }

    @PutMapping("/{agencyId}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> updateAgency(@PathVariable Long agencyId, @RequestBody AddUpdateAgencyRequest request) {
        agencyService.updateAgency(agencyId, request);
        return new ResponseEntity<>("Instansi berhasil diubah", HttpStatus.OK);
    }
}
