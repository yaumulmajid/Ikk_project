package id.go.lan.ikk.module.policy.presenter.controller;

import id.go.lan.ikk.module.policy.model.ActiveYearDto;
import id.go.lan.ikk.module.policy.presenter.model.UpdateActiveYearRequest;
import id.go.lan.ikk.module.policy.service.ActiveYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tahun-aktif")
public class ActiveYearController {

    @Autowired
    private ActiveYearService activeYearService;

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional', 'role_koordinator_utama', 'role_koordinator_instansi', 'role_admin_instansi', 'role_enumerator')")
    public ResponseEntity<Object> getActiveYear() {
        ActiveYearDto responseBody = activeYearService.getActiveYear();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PutMapping("/")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> updateActiveYear(@RequestBody UpdateActiveYearRequest requestBody) {
        try {
            activeYearService.updateActiveYear(requestBody.getStartYear(), requestBody.getEndYear());
            return new ResponseEntity<>("Tahun aktif berhasi diperbarui", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Tahun aktif gagal diperbarui", HttpStatus.BAD_REQUEST);
        }
    }
}
