package id.go.lan.ikk.module.helpdesk.presenter;

import id.go.lan.ikk.module.helpdesk.service.HelpdeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("helpdesk")
public class HelpdeskController {

    @Autowired
    private HelpdeskService helpdeskService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('role_admin_nasional','role_koordinator_utama','role_koordinator_instansi','role_admin_instansi','role_enumerator')")
    public ResponseEntity<Object> getHelpdeskData() {
        return new ResponseEntity<>(helpdeskService.getHelpdeskData(), HttpStatus.OK);
    }

}
