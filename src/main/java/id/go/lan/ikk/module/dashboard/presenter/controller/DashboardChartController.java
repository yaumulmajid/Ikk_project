package id.go.lan.ikk.module.dashboard.presenter.controller;

import id.go.lan.ikk.module.dashboard.model.DashboardBarChartDto;
import id.go.lan.ikk.module.dashboard.model.DashboardRadarChartDto;
import id.go.lan.ikk.module.dashboard.service.DashboardChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dashboard")
public class DashboardChartController {

    @Autowired
    private DashboardChartService dashboardChartService;

    @GetMapping("/instansi/top-ten")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional', 'role_koordinator_utama')")
    public ResponseEntity<Object> getAgencyTopTenByCategory(@RequestParam String kategori) {
        DashboardBarChartDto responseBody = dashboardChartService.getTopTenAgencies(kategori);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("instansi/{agencyId}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional', 'role_koordinator_utama')")
    public ResponseEntity<Object> getAgencyInstrumentScoreByAgencyId(@PathVariable Long agencyId) {
        DashboardRadarChartDto responseBody = dashboardChartService.getAgencyDetailScore(agencyId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
