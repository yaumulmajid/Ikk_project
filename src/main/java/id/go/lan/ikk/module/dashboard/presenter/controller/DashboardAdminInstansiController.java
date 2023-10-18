package id.go.lan.ikk.module.dashboard.presenter.controller;

import id.go.lan.ikk.module.dashboard.model.DashboardAdminInstansiCardDto;
import id.go.lan.ikk.module.dashboard.model.DashboardAdminInstansiCardPolicyDto;
import id.go.lan.ikk.module.dashboard.model.DashboardPolicyProgressDto;
import id.go.lan.ikk.module.dashboard.service.DashboardAdminInstansiService;
import id.go.lan.ikk.module.policy.model.PolicyDto;
import id.go.lan.ikk.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("kebijakan/admininstansi")
public class DashboardAdminInstansiController {

    @Autowired
    private DashboardAdminInstansiService dashboardAdminInstansiService;

    @Autowired
    private UserService userService;

    @GetMapping("/card")
    @PreAuthorize("hasAnyAuthority('role_admin_instansi')")
    public ResponseEntity<Object> getAdminInstansiCardInfo() {
        DashboardAdminInstansiCardDto responseBody = dashboardAdminInstansiService.getDashboardCard();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/card/kebijakan/{idPolicy}")
    @PreAuthorize("hasAnyAuthority('role_admin_instansi')")
    public ResponseEntity<Object> getAdminInstansiPolicyDetail(@PathVariable Long idPolicy) {
        DashboardAdminInstansiCardPolicyDto responseBody = dashboardAdminInstansiService.getDashboardPolicyDetail(idPolicy);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/proses")
    @PreAuthorize("hasAnyAuthority('role_admin_instansi')")
    public ResponseEntity<Object> getAdminInstansiPoliciesProses() {
        List<PolicyDto> responseBody = dashboardAdminInstansiService
                .getAdminInstansiDashboardProgress(userService.getAgencyIdBySignedInUser());
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/proses/{idPolicy}")
    @PreAuthorize("hasAnyAuthority('role_admin_instansi')")
    public ResponseEntity<Object> getAdminInstansiPolicyProgressDetail(@PathVariable Long idPolicy) {
        DashboardPolicyProgressDto responseBody = dashboardAdminInstansiService.getAdminInstansiPolicyProgress(idPolicy);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
