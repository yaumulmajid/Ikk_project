package id.go.lan.ikk.module.user.presenter.controller;

import id.go.lan.ikk.module.user.model.CoordinatorTypeDto;
import id.go.lan.ikk.module.user.presenter.model.AddUpdateCoordinateTypeRequest;
import id.go.lan.ikk.module.user.service.CoordinatorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("wilayah-koordinasi")
public class CoordinatorTypeController {

    @Autowired
    private CoordinatorTypeService coordinatorTypeService;

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional', 'role_koordinator_utama')")
    public ResponseEntity<Object> getCoordinatorTypes() {
        List<CoordinatorTypeDto> responseBody = coordinatorTypeService.getAllCoordinatorTypes();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{coordinatorTypeId}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional', 'role_koordinator_utama')")
    public ResponseEntity<Object> getCoordinatorTypeById(@PathVariable Long coordinatorTypeId) {
        CoordinatorTypeDto responseBody = coordinatorTypeService.getCoordinatorTypeById(coordinatorTypeId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> addCoordinatorType(@RequestBody AddUpdateCoordinateTypeRequest request) {
        coordinatorTypeService.addCoordinatorType(request);
        return new ResponseEntity<>("Wilayah koordinasi berhasil ditambahkan", HttpStatus.CREATED);
    }

    @PutMapping("/{coordinatorTypeId}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> updateCoordinatorType(
            @PathVariable Long coordinatorTypeId,
            @RequestBody AddUpdateCoordinateTypeRequest request) {
        coordinatorTypeService.updateCoordinatorType(coordinatorTypeId, request);
        return new ResponseEntity<>("Wilayah koordinasi berhasil diubah", HttpStatus.OK);
    }

    @DeleteMapping("/{coordinatorTypeId}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> deleteCoordinatorType(@PathVariable Long coordinatorTypeId) {
        coordinatorTypeService.deleteCoordinatorType(coordinatorTypeId);
        return new ResponseEntity<>("Wilayah koordinasi berhasil dihapus", HttpStatus.OK);
    }
}
